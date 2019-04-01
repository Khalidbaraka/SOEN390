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


import java.util.HashMap;
import java.util.Map;

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

        String content= mComment.getText().toString().trim();

        if(!TextUtils.isEmpty(content)){
            //start uplodaing


            DatabaseReference newComment = mPostDatabase.push();
            Map<String, String> dataToSave= new HashMap<>();
            dataToSave.put("userid", mAuth.getUid());
            dataToSave.put("userEmail", mUser.getEmail());
            dataToSave.put("comment", content);
            dataToSave.put("timestamp", String.valueOf(java.lang.System.currentTimeMillis()));
            dataToSave.put("podcast", CommentListActivity.targetPodcastTitle);

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
