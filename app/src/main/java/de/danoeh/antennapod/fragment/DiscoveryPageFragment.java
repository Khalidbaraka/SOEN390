package de.danoeh.antennapod.fragment;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.view.ViewGroup;
import android.widget.TextView;
import de.danoeh.antennapod.R;
import de.danoeh.antennapod.activity.MainActivity;

import android.os.Bundle;

/**
 * Shows Categories, Top Podcast, & More.
 * Discover your podcast here!
 *
 *
 * Links to categories pages
 */

public class DiscoveryPageFragment extends Fragment implements View.OnClickListener {

    Button categoriesBtn;
    private TextView txtHome;
    public static final String TAG = "DiscoveryPageFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View DiscoveryView = inflater.inflate(R.layout.discovery_page, container, false);

        categoriesBtn = (Button) getView().findViewById(R.id.CategoriesBtn);
        categoriesBtn.setOnClickListener(this);

        // Inflate the layout for this fragment
        return DiscoveryView;


    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onClick(View v) {
        // implements your things
    }
}