package de.danoeh.antennapod.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.ProgressDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import de.danoeh.antennapod.R;
import de.danoeh.antennapod.model.Comment;

public class AddCommentActivity extends Activity {
    private EditText mComment;
    private Button mSubmitButton;
    private DatabaseReference mPostDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comment);


        mAuth= FirebaseAuth.getInstance();
        mUser= mAuth.getCurrentUser();

        mPostDatabase= FirebaseDatabase.getInstance().getReference().child("Comment");

//        mProgress= new ProgressDialog(this);

        mComment= (EditText) findViewById(R.id.commentContent);
        mSubmitButton= (Button) findViewById(R.id.submitComment);


        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //posting to our database
                startPosting();
            }
        });


    }

    private void startPosting(){

//        mProgress.setMessage("Posting to blog");
//        mProgress.show();

        String content= mComment.getText().toString().trim();
        Log.d("im HERE!","im here");
        if(!TextUtils.isEmpty(content)){
            //start uplodaing
            Log.d("im inside if statment!","insdie if statement");

            Comment comment= new Comment("khalid", "CommentID","2019-03-29: 8:09",content,"podcastTitle");
            mPostDatabase.setValue(comment).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(getApplicationContext(),"Item added",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(AddCommentActivity.this,OnlineFeedViewActivity.class));
                    Toast.makeText(getApplicationContext(),"going back to onlineFeedback Class",Toast.LENGTH_LONG).show();

//                    mProgress.dismiss();
                }
            });
        }

    }
}
