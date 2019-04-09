package de.danoeh.antennapod.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
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
import java.util.Iterator;
import java.util.List;

import de.danoeh.antennapod.R;
import de.danoeh.antennapod.activity.AddCommentActivity;
import de.danoeh.antennapod.activity.AddReplyActivity;
import de.danoeh.antennapod.activity.CommentListActivity;
import de.danoeh.antennapod.activity.ReplyListActivity;
import de.danoeh.antennapod.model.Comment;
import de.danoeh.antennapod.model.User;


// this will be used to recyceler view adpater bind our row, with listview and data (comment Model)
public class CommentRecyclerAdapter extends RecyclerView.Adapter<CommentRecyclerAdapter.ViewHolder>{

    private Context context;
    private List <Comment> commentList;
    private List<User> userList;

    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseReference;
    private DatabaseReference userReference;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mCommentDatabase;



    public CommentRecyclerAdapter(Context context, List<Comment> commentList) {
        this.context = context;
        this.commentList = commentList;
        this.userList = new ArrayList<>();
        mDatabase=FirebaseDatabase.getInstance();
        userReference=mDatabase.getReference().child("users");
        mCommentDatabase=mDatabase.getReference().child("Comment");

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
        public ViewHolder(@NonNull View view, Context ctx ) {
            super(view);
            context = ctx;
            comment = (TextView)view.findViewById(R.id.commentTitleList);
            timestamp = (TextView)view.findViewById(R.id.timestampList);
            userName= (TextView)view.findViewById(R.id.userName);
            addReplyBtn = (Button)view.findViewById(R.id.addReplyBtn);
            image= (ImageButton)view.findViewById(R.id.imageButton3);
            userId = null;
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

                            Intent intent= new Intent(context.getApplicationContext(), AddReplyActivity.class);
                            intent.putExtra("comment",comment.getText().toString());
                            intent.putExtra("commentID", commentIDFROMDB);
                            intent.putExtra("userEmail",userEmail);
                            intent.putExtra("commentOwner", userName.getText().toString());
                            intent.putExtra("podcast",podcast);
                            context.startActivity(intent);
                            ((Activity)context).finish();

                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });



                }
            });

        }
    }
}
