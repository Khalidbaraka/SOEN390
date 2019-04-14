package de.danoeh.antennapod.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.squareup.picasso.Picasso;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import de.danoeh.antennapod.R;
import de.danoeh.antennapod.activity.ReplyListActivity;
import de.danoeh.antennapod.model.Comment;
import de.danoeh.antennapod.model.Like;
import de.danoeh.antennapod.model.Reply;
import de.danoeh.antennapod.model.User;


// this will be used to recyceler view adpater bind our row, with listview and data (comment Model)
public class CommentRecyclerAdapter extends RecyclerView.Adapter<CommentRecyclerAdapter.ViewHolder>{

    private Context context;
    private List <Comment> commentList;
    private List<String> commentIDList;
    private List<User> userList;
    private List<Reply>replies;
    private List<Reply>likes;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseReference;
    private DatabaseReference userReference;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mLikeDatabase;
    private DatabaseReference mCommentDatabase;
    private boolean mProcessLike = false;


    public CommentRecyclerAdapter(Context context, List<Comment> commentList) {
        this.context = context;
        this.commentList = commentList;
        this.userList = new ArrayList<>();
        this.replies= new ArrayList<>();
        this.commentIDList=new ArrayList<>();
        this.likes = new ArrayList<>();
        mDatabase=FirebaseDatabase.getInstance();
        userReference=mDatabase.getReference().child("users");
        mCommentDatabase=mDatabase.getReference().child("Comment");
        mDatabaseReference= mDatabase.getReference().child("Reply");
        mLikeDatabase = mDatabase.getReference().child("Like");
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comment_row,parent,false);

        return new ViewHolder(view, context);

    }
    // binding everything together
    // taking the info from comment object and setting the attributes of holder
    // which are the widgets inside the xml of post_row
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Comment comment = commentList.get(position);
        holder.comment.setText(comment.getComment());
        java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
        String formattedDate = dateFormat.format(new Date(Long.valueOf(comment.getTimestamp())).getTime());
        //The formatted date looks like: Match 31st, 2019
        holder.timestamp.setText(formattedDate);


        mLikeDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int numberOfLikes = 0;

                Iterator<DataSnapshot> likesObj = dataSnapshot.getChildren().iterator();
                int i=0;
                while (likesObj.hasNext()){
                    DataSnapshot item = likesObj.next();
                    //removing duplicates by the if statement
                    if(comment.getCommentid().equals(item.getKey())){
                        for(DataSnapshot a :item.getChildren()){
                            numberOfLikes++;
                        }
                    }
                }
                holder.likesNum.setText(String.valueOf(numberOfLikes)+" likes");
                likes.clear();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        userReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot d: dataSnapshot.getChildren()){
                    User user= new User();
                    user.setEmail(d.child("email").getValue(String.class));
                    user.setFullName(d.child("fullName").getValue(String.class));
                    user.setImageURL(d.child("imageURL").getValue(String.class));
                    userList.add(user);
                }

                for(User u: userList){
                    Log.d("user name is :", u.getFullName());
                    if(u.getEmail().equals(comment.useremail)){
                        Log.d("user name is :", u.getFullName());
                        holder.userName.setText(u.getFullName());
                        holder.userEmail= u.getEmail();
                        holder.podcast= comment.getPodcast();
                        holder.commentID= comment.toString();
                        holder.imageURL = u.getImageURL();
                        Picasso.get().load(holder.imageURL).resize(200,200).into(holder.image);
                        break;
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //gives me the comment id with no duplicates
        mCommentDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> comments = dataSnapshot.getChildren().iterator();
               int i=0;
                while (comments.hasNext()){
                    DataSnapshot item = comments.next();
                    //removing duplicates by the if statement
                    if(!(commentIDList.contains(item.getKey())))
                    {commentIDList.add(item.getKey().toString());
                    Log.d("key "+i,item.getKey());}
                    i++;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // for displaying total number of replies
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot d: dataSnapshot.getChildren()){
                    Reply reply = new Reply();
                    reply.commentID=(d.child("commentID").getValue(String.class));
                    reply.reply=(d.child("reply").getValue(String.class));
                    replies.add(reply);
                }
                int sum = 0;
                for(Reply i : replies){
                    if(i.commentID.equals(comment.getCommentid())){
                        sum++;
                    }
                }
                holder.repliesNum.setText(String.valueOf(sum)+" replies");
                replies.clear();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        holder.changeLikeBtn(comment.getCommentid());

        holder.likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mProcessLike= true;
                mLikeDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(mProcessLike){

                            if(dataSnapshot.child(comment.getCommentid()).hasChild(mAuth.getCurrentUser().getUid())){
                                mLikeDatabase.child(comment.getCommentid()).child(mAuth.getCurrentUser().getUid()).removeValue();
                                mProcessLike=false;
                            }else {
                                mLikeDatabase.child(comment.getCommentid()).child(mAuth.getCurrentUser().getUid()).setValue("true");
                                mProcessLike=false;

                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }




    @Override
    public int getItemCount() {
        return commentList.size() ;
    }
    // is used to setup the widgets inside the post_row xml
    // can access the xml through view
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView comment;
        public TextView timestamp;
        public TextView userName;
        public Button addReplyBtn;
        public String userId;
        public String userEmail;
        public String podcast;
        public String commentID;
        public String imageURL;
        public String commentIDFROMDB;
        public ImageButton image;
        public int totalReplies;
        public TextView repliesNum;
        public TextView likesNum;
        public Button likeButton;
        private FirebaseAuth mAuth;
        private DatabaseReference mLikeDatabase;
        public ViewHolder(@NonNull View view, Context ctx ) {
            super(view);
            context = ctx;
            comment = (TextView)view.findViewById(R.id.commentTitleList);
            timestamp = (TextView)view.findViewById(R.id.timestampList);
            userName= (TextView)view.findViewById(R.id.userName);
            addReplyBtn = (Button)view.findViewById(R.id.addReplyBtn);
            image= (ImageButton)view.findViewById(R.id.imageButton2);
            repliesNum = (TextView)view.findViewById(R.id.repliesNum);
            likesNum = (TextView)view.findViewById(R.id.likesNum);
            userId = null;
            mLikeDatabase = mDatabase.getReference().child("Like");
            mAuth = FirebaseAuth.getInstance();
            likeButton = view.findViewById(R.id.like_btn);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // we can go to nxt activity

                }
            });

            addReplyBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mCommentDatabase.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Iterator<DataSnapshot> comments = dataSnapshot.getChildren().iterator();
                            while (comments.hasNext()){
                                DataSnapshot item = comments.next();

                                if( (item.child("comment").getValue().equals(comment.getText().toString())) &&
                                        (item.child("podcast").getValue().equals(podcast)) &&
                                        (item.child("useremail").getValue().equals(userEmail))
                                ){
                                    commentIDFROMDB= item.getKey().toString();
                                    Log.d("the comment id from DB ", commentIDFROMDB);
                                    break;

                                }
                            }

                            Intent intent= new Intent(context.getApplicationContext(), ReplyListActivity.class);
                            intent.putExtra("comment",comment.getText().toString());
                            intent.putExtra("commentID", commentIDFROMDB);
                            intent.putExtra("userEmail",userEmail);
                            intent.putExtra("commentOwner", userName.getText().toString());
                            intent.putExtra("podcast",podcast);
                            context.startActivity(intent);
                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            });

        }

        public void changeLikeBtn(String commentID){

            mLikeDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.child(commentID).hasChild(mAuth.getCurrentUser().getUid())){
                        likeButton.setText("Unlike");

                    }else {
                        likeButton.setText("Like");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });



        }
    }
}
