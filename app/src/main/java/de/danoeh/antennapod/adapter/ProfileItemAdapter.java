package de.danoeh.antennapod.adapter;

import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import de.danoeh.antennapod.R;
import de.danoeh.antennapod.fragment.EditProfileFragment;
import de.danoeh.antennapod.model.ProfileItem;

public class ProfileItemAdapter extends RecyclerView.Adapter<ProfileItemAdapter.ProfileItemViewHolder> {

    private ArrayList<ProfileItem> profileItems;

    public static class ProfileItemViewHolder extends RecyclerView.ViewHolder {

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

    public ProfileItemAdapter(ArrayList<ProfileItem> profileItems) {
        this.profileItems = profileItems;
    }

    @NonNull
    @Override
    public ProfileItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v  = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_card_item, parent, false);
        ProfileItemViewHolder profileItemViewHolder = new ProfileItemViewHolder(v);
        return profileItemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileItemViewHolder holder, int position) {
        ProfileItem currentItem = profileItems.get(position);

        holder.icon.setImageResource(currentItem.getImageResource());
        holder.title.setText(currentItem.getTitle());

        holder.cardView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                EditProfileFragment editProfileFragment = new EditProfileFragment();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.main_view, editProfileFragment).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return profileItems.size();
    }

}
