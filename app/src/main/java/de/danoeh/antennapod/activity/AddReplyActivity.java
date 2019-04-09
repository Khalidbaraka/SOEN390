package de.danoeh.antennapod.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class AddReplyActivity extends Activity {

    public String userEmailFromPrevActivtiy;
    public String commentFromPrevActivity;
    public String podcastFromPrevActivity;

    private EditText replyContent;
    private Button mSubmitButton;
    private DatabaseReference mPostDatabase;
    private DatabaseReference mReplyDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    public String commentID;
    public String commentOwner;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reply);

        Intent i = getIntent();
        userEmailFromPrevActivtiy= i.getStringExtra("userEmail");
        commentFromPrevActivity= i.getStringExtra("comment");
        commentID= i.getStringExtra("commentID");
        commentOwner= i.getStringExtra("commentOwner");
        podcastFromPrevActivity= i.getStringExtra("podcast");

        mAuth= FirebaseAuth.getInstance();
        mUser= mAuth.getCurrentUser();
        mPostDatabase= FirebaseDatabase.getInstance().getReference().child("Reply");

        replyContent = (EditText) findViewById(R.id.replyContent);
        mSubmitButton= (Button) findViewById(R.id.replyBtn);


        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //posting to our database
                startPosting();
            }
        });
    }


    private void startPosting() {
//        mProgress.setMessage("Posting to blog");
//        mProgress.show();
        String content = replyContent.getText().toString().trim();

        if(!TextUtils.isEmpty(content)){

            DatabaseReference newReply = mPostDatabase.push();
            Map<String, String> dataToSave= new HashMap<>();
            dataToSave.put("sender", mUser.getUid());
            dataToSave.put("senderEmail", mUser.getEmail());
            dataToSave.put("senderName", mUser.getDisplayName());
            dataToSave.put("receiverName", commentOwner);
            dataToSave.put("receiverEmail", userEmailFromPrevActivtiy);
            dataToSave.put("reply", content);
            dataToSave.put("commentID",String.valueOf(commentID));
            dataToSave.put("timestamp", String.valueOf(java.lang.System.currentTimeMillis()));
            dataToSave.put("podcastTitle", podcastFromPrevActivity);

            newReply.setValue(dataToSave).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(getApplicationContext(), "Item added", Toast.LENGTH_LONG).show();
                    Intent intent= new Intent(AddReplyActivity.this, ReplyListActivity.class);
                    intent.putExtra("commentID", commentID);
                    intent.putExtra("podcast",podcastFromPrevActivity);
                    intent.putExtra("receiverEmail",userEmailFromPrevActivtiy);
                    intent.putExtra("receiverName", commentOwner);
                    startActivity(intent);
                    finish();
                }
            });
        }

    }


}
