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

    public String podcastTitleFromCommentList;
    private EditText mComment;
    private Button mSubmitButton;
    private DatabaseReference mPostDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comment);

        Intent i= getIntent();
        podcastTitleFromCommentList= i.getStringExtra("podcastTitle");
        mAuth= FirebaseAuth.getInstance();
        mUser= mAuth.getCurrentUser();

        mPostDatabase= FirebaseDatabase.getInstance().getReference().child("Comment");

        mComment= (EditText) findViewById(R.id.commentContent);
        mSubmitButton= (Button) findViewById(R.id.submitComment);


        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //posting to our database
                String content= mComment.getText().toString().trim();
                Map<String, String> dataToSave= new HashMap<>();
                DatabaseReference newComment = mPostDatabase.push();
                String id= mAuth.getUid();
                String email= mUser.getEmail();
                String time=String.valueOf(java.lang.System.currentTimeMillis());

                startPosting(content,dataToSave,newComment,id,email,time,podcastTitleFromCommentList);

            }
        });
    }


    public void startPosting(String content, Map<String, String> dataToSave,DatabaseReference newComment,
                             String id, String email, String time, String podcastTitleFromCommentList
                             ){

        if(content.length() != 0){
            //start uplodaing

            dataToSave.put("userid", id);
            dataToSave.put("userEmail", email);
            dataToSave.put("comment", content);
            dataToSave.put("timestamp", time);
            dataToSave.put("podcast", podcastTitleFromCommentList);

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
