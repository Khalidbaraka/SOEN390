package de.danoeh.antennapod.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import de.danoeh.antennapod.R;
import de.danoeh.antennapod.model.Comment;


// this will be used to recyceler view adpater bind our row, with listview and data (blog Model)
public class CommentRecyclerAdapter extends RecyclerView.Adapter<CommentRecyclerAdapter.ViewHolder>{

    private Context context;
    private List <Comment> commentList;

    public CommentRecyclerAdapter(Context context, List<Comment> commentList) {
        this.context = context;
        this.commentList = commentList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comment_row,parent,false);
        return new  ViewHolder(view, context);

    }
    // binding everything together
    // taking the info from blog object and setting the attributes of holder
    // which are the widgets inside the xml of post_row
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Comment comment= commentList.get(position);
        String imageUrl= null;
        holder.title.setText(comment.getComment());

        java.text.DateFormat dateFormat= java.text.DateFormat.getDateInstance();
        String formattedDate= dateFormat.format(new Date(Long.valueOf(comment.getTimestamp())).getTime());
        //April 17th, 2018
        holder.timestamp.setText(formattedDate);


    }

    @Override
    public int getItemCount() {
        return commentList.size() ;
    }
    // is used to setup the widgets inside the post_row xml
    // can access the xml thru view
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView timestamp;
        String userId;
        public ViewHolder(@NonNull View view, Context ctx ) {
            super(view);
            context= ctx;
            title=(TextView)view.findViewById(R.id.postTitleList);
            timestamp= (TextView)view.findViewById(R.id.timestamplist);
            userId= null;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // we can go to nxt activity
                }
            });
        }
    }
}
