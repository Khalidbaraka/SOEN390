package de.danoeh.antennapod.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import de.danoeh.antennapod.R;
import de.danoeh.antennapod.fragment.DiscoveryPageFragment;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class CommentListActivity extends Activity {

    private DatabaseReference mDatabaseReference;
    private FirebaseDatabase mDatabase;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_list);
        mAuth = FirebaseAuth.getInstance();
        mUser= mAuth.getCurrentUser();

        mDatabase= FirebaseDatabase.getInstance();
        mDatabaseReference= mDatabase.getReference().child("Blog");
        mDatabaseReference.keepSynced(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.comment_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.action_add:
             startActivity(new Intent(CommentListActivity.this, AddCommentActivity.class));
              finish();

                break;
            case R.id.action_singout:
                if(mUser != null && mAuth != null) {
                    startActivity(new Intent(CommentListActivity.this, DiscoveryPageFragment.class));
                    finish();
                }


        }







        return super.onOptionsItemSelected(item);
    }
}
