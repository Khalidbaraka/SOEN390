package de.danoeh.antennapod.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.danoeh.antennapod.R;
import de.danoeh.antennapod.adapter.CommentRecyclerAdapter;
import de.danoeh.antennapod.adapter.ReplyRecyclerAdapter;
import de.danoeh.antennapod.model.Comment;
import de.danoeh.antennapod.model.Reply;
import de.danoeh.antennapod.model.User;

public class ReplyListActivity extends Activity {
// to post a reply widgets
    private EditText replyContent_1;
    private Button addReplybtn_1;
    public List<Reply> replyArrayList;
    public ArrayList<User>  userList;
    public String userEmailFromPrevActivtiy;
    public String commentFromPrevActivity;
    public String podcastFromPrevActivity;
    public String commentID;
    public String commentOwner;

    private DatabaseReference mPostDatabase;
    private DatabaseReference mDatabaseReference;
    private DatabaseReference userReference;
    private FirebaseDatabase mDatabase;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;

    private RecyclerView recyclerView_2;
    private ReplyRecyclerAdapter replyRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply_list);


        // getting the infromation needed to post a reply and populate required replies
        Intent i = getIntent();
        userEmailFromPrevActivtiy= i.getStringExtra("userEmail");
        commentFromPrevActivity= i.getStringExtra("comment");
        commentID= i.getStringExtra("commentID");
        commentOwner= i.getStringExtra("commentOwner");
        podcastFromPrevActivity= i.getStringExtra("podcast");


        mAuth = FirebaseAuth.getInstance();
        mUser= mAuth.getCurrentUser();
        mDatabase= FirebaseDatabase.getInstance();
        mDatabaseReference= mDatabase.getReference().child("Reply");
        userReference= mDatabase.getReference().child("users");
        mDatabaseReference.keepSynced(true);
        mPostDatabase= FirebaseDatabase.getInstance().getReference().child("Reply");
        // for posting a reply
        replyContent_1 = (EditText)findViewById(R.id.replyContent_1);
        addReplybtn_1= (Button)findViewById(R.id.submitReply_1);

        replyArrayList = new ArrayList<Reply>();
        userList= new ArrayList<>();


        recyclerView_2 = (RecyclerView) findViewById(R.id.recyclerView_2);
        recyclerView_2.setHasFixedSize(true);
        recyclerView_2.setLayoutManager(new LinearLayoutManager(this));
        replyRecyclerAdapter= new ReplyRecyclerAdapter(ReplyListActivity.this,replyArrayList);

        if(mAuth == null || mUser == null){
            addReplybtn_1.setVisibility(View.GONE);
            replyContent_1.setVisibility(View.GONE);
            Toast.makeText(ReplyListActivity.this,"You Cant Reply!,Please Register/Login",Toast.LENGTH_LONG)
                    .show();
        }

        addReplybtn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mAuth != null && mUser != null){
                    startPosting();
                }else{
                    Toast.makeText(getApplicationContext(),"You are not Signed in.Please do",Toast.LENGTH_LONG)
                    .show();
                }

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.comment_menu, menu);
        menu.findItem(R.id.action_add).setVisible(false);
        if(mUser!= null & mAuth != null){
            menu.findItem(R.id.action_signIn).setVisible(false);

        }else {
            menu.findItem(R.id.action_signout).setVisible(false);
            menu.findItem(R.id.action_add_reply).setVisible(false);

        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.action_add_reply:
                if(mUser != null && mAuth != null) {
                    Intent intent= new Intent(ReplyListActivity.this, AddReplyActivity.class);
                    intent.putExtra("comment",commentFromPrevActivity);
                    intent.putExtra("commentID", commentID);
                    intent.putExtra("userEmail",userEmailFromPrevActivtiy);
                    intent.putExtra("commentOwner", commentOwner);
                    intent.putExtra("podcast",podcastFromPrevActivity);
                    startActivity(intent);
                    finish();
                }
                break;

            case R.id.action_signIn:
                startActivity(new Intent(ReplyListActivity.this, LoginActivity.class));
                finish();

                break;

            case R.id.action_signout:
                mAuth.signOut();
                startActivity(new Intent(ReplyListActivity.this, MainActivity.class));
                finish();

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();

        mDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Reply reply = dataSnapshot.getValue(Reply.class);
                if(reply.commentID.equals(commentID)){
                replyArrayList.add(reply);}

                recyclerView_2.setAdapter(replyRecyclerAdapter);
                replyRecyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void startPosting() {

        String content_1 = replyContent_1.getText().toString().trim();
        if(!TextUtils.isEmpty(content_1)){

            DatabaseReference newReply = mPostDatabase.push();
            Map<String, String> dataToSave= new HashMap<>();
            dataToSave.put("sender", mUser.getUid());
            dataToSave.put("senderEmail", mUser.getEmail());
            dataToSave.put("senderName", mUser.getDisplayName());
            dataToSave.put("receiverName", commentOwner);
            dataToSave.put("receiverEmail", userEmailFromPrevActivtiy);
            dataToSave.put("reply", content_1);
            dataToSave.put("commentID",commentID);
            dataToSave.put("timestamp", String.valueOf(java.lang.System.currentTimeMillis()));
            dataToSave.put("podcastTitle", podcastFromPrevActivity);

            newReply.setValue(dataToSave).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(getApplicationContext(), "Item added!!!", Toast.LENGTH_LONG).show();
//                    Intent intent= new Intent(ReplyListActivity.this, ReplyListActivity.class);
//                    startActivity(intent);
                    finish();
                }
            });
        }

    }
}
