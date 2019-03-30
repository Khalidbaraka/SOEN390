package de.danoeh.antennapod.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class ProfileItemAdapter extends RecyclerView.Adapter<ProfileItemAdapter.ProfileItemViewHolder> {

    public static class ProfileItemViewHolder extends RecyclerView.ViewHolder {

        public ProfileItemViewHolder(View itemView) {
            super(itemView);
        }
    }

    public ProfileItemAdapter() {
        super();
    }

    @NonNull
    @Override
    public ProfileItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileItemViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
