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

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class CommentListActivity extends Activity {

    private DatabaseReference mDatabaseReference;
    private FirebaseDatabase mDatabase;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;

    private RecyclerView recyclerView;
    private CommentRecyclerAdapter commentRecyclerAdapter;
    private List<Comment> commentList;

    public static String targetPodcastTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_list);

        mAuth = FirebaseAuth.getInstance();
        mUser= mAuth.getCurrentUser();

        mDatabase= FirebaseDatabase.getInstance();
        mDatabaseReference= mDatabase.getReference().child("Comment");
        mDatabaseReference.keepSynced(true);

        commentList = new ArrayList<Comment>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent i = getIntent();
        targetPodcastTitle = i.getStringExtra("podcastTitle");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.comment_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.action_add:
                startActivity(new Intent(CommentListActivity.this, AddCommentActivity.class));
                finish();
                break;

            case R.id.action_signout:
                if(mUser != null && mAuth != null) {
                    mAuth.signOut();
                    startActivity(new Intent(CommentListActivity.this, DiscoveryPageFragment.class));
                    finish();
                }
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
                commentRecyclerAdapter = new CommentRecyclerAdapter(CommentListActivity.this, commentList);
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
}
