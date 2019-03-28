package de.danoeh.antennapod.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.danoeh.antennapod.R;

import static java.security.AccessController.getContext;

public class SimilarPodcastAdapter extends RecyclerView.Adapter<SimilarPodcastAdapter.ViewHolder>{
    private static final String TAG = "SimilarPodcastAdapter";

    private ArrayList<String> mImages =  new ArrayList<>();
    private ArrayList<String> mPodcastNames =  new ArrayList<>();
    private Context mContext;

    public SimilarPodcastAdapter(ArrayList<String> mImages, ArrayList<String> mPodcastNames, Context mContext) {
        this.mImages = mImages;
        this.mPodcastNames = mPodcastNames;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.similar_itemview,parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.podcastName.setText(mPodcastNames.get(position));
        Glide.with(mContext).asBitmap().load(mImages.get(position)).into(holder.podcastImage);
        //Picasso.get().load("https://d3sv2eduhewoas.cloudfront.net/channel/image/b7c71eae106646e8b1310e53bb2730c8.jpeg").into(holder.podcastImage);
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mPodcastNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView podcastImage;
        TextView podcastName;
        RelativeLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            podcastImage = itemView.findViewById(R.id.podcast_image);
            podcastName = itemView.findViewById(R.id.podcast_name);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
