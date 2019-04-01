package de.danoeh.antennapod.activity;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import de.danoeh.antennapod.R;
import de.danoeh.antennapod.adapter.CommentRecyclerAdapter;
import de.danoeh.antennapod.fragment.DiscoveryPageFragment;
import de.danoeh.antennapod.model.Comment;
import de.danoeh.antennapod.model.User;

import android.content.Intent;
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


public class CommentListActivity extends Activity {

    private List<Comment> commentList;
    private List<User> userList;

    private EditText mComment;
    private Button mSubmitButton;
    private DatabaseReference mPostDatabase;

    private DatabaseReference mDatabaseReference;
    private DatabaseReference userReference;
    private FirebaseDatabase mDatabase;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;

    private RecyclerView recyclerView;
    private CommentRecyclerAdapter commentRecyclerAdapter;

//  public String podcastTitleFromOnlineFeed;
    public static String targetPodcastTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_list);

        mComment= (EditText) findViewById(R.id.commentContent_1);
        mSubmitButton= (Button) findViewById(R.id.submitComment_1);

        mAuth = FirebaseAuth.getInstance();
        mUser= mAuth.getCurrentUser();

        mDatabase= FirebaseDatabase.getInstance();
        mPostDatabase= FirebaseDatabase.getInstance().getReference().child("Comment");
        mDatabaseReference= mDatabase.getReference().child("Comment");
        userReference= mDatabase.getReference().child("users");
        mDatabaseReference.keepSynced(true);

        commentList = new ArrayList<Comment>();
        userList= new ArrayList<>();
        Intent i = getIntent();

        targetPodcastTitle= i.getStringExtra("podcastTitle");


        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        commentRecyclerAdapter= new CommentRecyclerAdapter(CommentListActivity.this,commentList);

        if(mAuth == null || mUser == null){
            mSubmitButton.setVisibility(View.GONE);
            mComment.setVisibility(View.GONE);
        }

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //posting to our database
                if(mUser != null && mAuth != null) {
                startPosting();}
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.comment_menu, menu);
        if(mUser!= null & mAuth != null){
            menu.findItem(R.id.action_signIn).setVisible(false);
        }else {
            menu.findItem(R.id.action_signout).setVisible(false);
            menu.findItem(R.id.action_add).setVisible(false);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.action_add:
                if(mUser != null && mAuth != null) {
                    Intent intent= new Intent(CommentListActivity.this, AddCommentActivity.class);
                    intent.putExtra("podcastTitle",targetPodcastTitle);
                    startActivity(intent);
                    finish();
                }
                break;

            case R.id.action_signIn:
                    startActivity(new Intent(CommentListActivity.this, LoginActivity.class));
                    finish();

                break;

            case R.id.action_signout:
                    mAuth.signOut();
                    startActivity(new Intent(CommentListActivity.this, MainActivity.class));
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
                Comment comment = dataSnapshot.getValue(Comment.class);
                if(comment.getPodcast().equalsIgnoreCase(targetPodcastTitle))
                {
                    commentList.add(comment);
                }

                recyclerView.setAdapter(commentRecyclerAdapter);
                commentRecyclerAdapter.notifyDataSetChanged();
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
//        mProgress.setMessage("Posting to blog");
//        mProgress.show();
        String content = mComment.getText().toString().trim();

        Log.d("PODCAST TITLE", targetPodcastTitle);
        Log.d("im HERE!", "im here");
        if (!TextUtils.isEmpty(content)) {
            //start uplodaing
            DatabaseReference newComment = mPostDatabase.push();
            Map<String, String> dataToSave = new HashMap<>();
            dataToSave.put("userid", mAuth.getUid());
            dataToSave.put("comment", content);
            dataToSave.put("timestamp", String.valueOf(java.lang.System.currentTimeMillis()));
            dataToSave.put("podcast", targetPodcastTitle);
            dataToSave.put("useremail", mUser.getEmail());

            newComment.setValue(dataToSave).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(getApplicationContext(), "Item added", Toast.LENGTH_LONG).show();
                    finish();
                }
            });

        }

    }

}
