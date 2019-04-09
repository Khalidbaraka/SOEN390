package de.danoeh.antennapod.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;


import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import de.danoeh.antennapod.R;
import de.danoeh.antennapod.activity.AddReplyActivity;
import de.danoeh.antennapod.activity.ReplyListActivity;
import de.danoeh.antennapod.model.Comment;
import de.danoeh.antennapod.model.User;
import de.danoeh.antennapod.model.Reply;


public class ReplyRecyclerAdapter extends RecyclerView.Adapter<ReplyRecyclerAdapter.ViewHolder>{

    private Context context;

    private List<Reply> replyList;
    private List<Reply> UpdatedreplyList;

    private List<User> userList;
    private List <Comment> commentList;

    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseReference;
    private DatabaseReference userReference;
    private DatabaseReference commentReference;
    private DatabaseReference replyReference;


    private FirebaseDatabase mDatabase;

    public ReplyRecyclerAdapter(Context context, List<Reply> replyList) {
        this.context = context;
        this.replyList = replyList;
        this.UpdatedreplyList=new ArrayList<>();
        this.userList = new ArrayList<>();
        this.commentList= new ArrayList<>();
        mDatabase=FirebaseDatabase.getInstance();
        userReference=mDatabase.getReference().child("users");
        commentReference=mDatabase.getReference().child("Comment");
        replyReference= mDatabase.getReference().child("Reply");


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reply_row,parent,false);

        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ReplyRecyclerAdapter.ViewHolder holder, int position) {
        Reply reply= replyList.get(position);

//        commentReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//               Iterator<DataSnapshot> comments = dataSnapshot.getChildren().iterator();
//                while (comments.hasNext()){
//                    DataSnapshot comment = comments.next();
//                    Log.d("TESTING!!","COMMENT ID IS "+comment.getKey());
//                    String concatCommentID= "-"+reply.commentID;
//                    if(comment.getKey().equals(reply.commentID)){
//                        Log.d("TESTING!!", "onDataChange: INSIDE THE IF STATMENT! ITS WORKING!");
//                        UpdatedreplyList.add(reply);
//                    }
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });


        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot d: dataSnapshot.getChildren()){
                    User user= new User();
                    user.setEmail(d.child("email").getValue(String.class));
                    user.setFullName(d.child("fullName").getValue(String.class));

                    userList.add(user);
                }

                for(User u: userList){
                    Log.d("user name is :", u.getFullName());
                    if(u.getEmail().equals(reply.senderEmail)){
                        Log.d("user name is :", u.getFullName());
                        holder.userName.setText(u.getFullName());
                        holder.userEmail= u.getEmail();
                        //for timestamp
                        java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
                        String formattedDate = dateFormat.format(new Date(Long.valueOf(reply.timestamp)).getTime());
                        //The formatted date looks like: Match 31st, 2019
                        holder.timestamp.setText(formattedDate);
                        holder.reply.setText(reply.getReply());
                        break;
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    @Override
    public int getItemCount() {
        return replyList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView reply;
        public TextView timestamp;
        public TextView userName;
        public String userId;
        public String userEmail;
        public String podcast;


        public ViewHolder(View view, Context ctx) {
            super(view);
            context = ctx;
            reply = (TextView)view.findViewById(R.id.replyTitleList);
            timestamp = (TextView)view.findViewById(R.id.timestampList1);
            userName= (TextView)view.findViewById(R.id.userName1);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // we can go to nxt activity

                }
            });
        }


    }
}
