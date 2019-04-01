package de.danoeh.antennapod.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import de.danoeh.antennapod.R;
import de.danoeh.antennapod.fragment.EditProfileFragment;
import de.danoeh.antennapod.fragment.MyCommentsFragment;
import de.danoeh.antennapod.model.ProfileItem;

public class ProfileItemAdapter extends RecyclerView.Adapter<ProfileItemAdapter.ProfileItemViewHolder> {

    private ArrayList<ProfileItem> profileItems;
    View view;
    Context context;
    LayoutInflater inflater;


    public static class ProfileItemViewHolder extends RecyclerView.ViewHolder  {

        public ImageView icon;
        public TextView title;
        public CardView cardView;


        public ProfileItemViewHolder(View itemView) {
            super(itemView);

            icon = itemView.findViewById(R.id.profile_card_item_image);
            title = itemView.findViewById(R.id.profile_card_item_title);
            cardView = itemView.findViewById(R.id.profile_card_view);

        }
    }

    public ProfileItemAdapter(Context context, ArrayList<ProfileItem> profileItems) {
        this.profileItems = profileItems;
        this.context = context;
        inflater = LayoutInflater.from(this.context);

    }

    @NonNull
    @Override
    public ProfileItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_card_item, parent, false);
        ProfileItemViewHolder profileItemViewHolder = new ProfileItemViewHolder(view);
        return profileItemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ProfileItemViewHolder holder, int position) {
        ProfileItem currentItem = profileItems.get(position);

        holder.icon.setImageResource(currentItem.getImageResource());
        holder.title.setText(currentItem.getTitle());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int pos = position;
                Context context = view.getContext();
                Intent intent = new Intent();
                AppCompatActivity activity;
                switch (pos) {
                    case 0:
                        activity = (AppCompatActivity) view.getContext();
                        EditProfileFragment editFragment = new EditProfileFragment();
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.main_view, editFragment).addToBackStack(null).commit();
                        break;
                    case 1:
                        activity = (AppCompatActivity) view.getContext();
                        MyCommentsFragment myCommentFragment = new MyCommentsFragment();
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.main_view, myCommentFragment).addToBackStack(null).commit();
                        break;
                    default:
                        break;
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return profileItems.size();
    }

}
