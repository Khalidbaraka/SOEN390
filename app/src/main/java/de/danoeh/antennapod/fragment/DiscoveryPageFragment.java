package de.danoeh.antennapod.fragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import de.danoeh.antennapod.R;
import android.os.Bundle;

/**
 * Shows Categories, Top Podcast, & More.
 * Discover your podcast here!
 */

public class DiscoveryPageFragment extends Fragment {

    private TextView txtHome;
    public static final String TAG = "DiscoveryPageFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.discovery_page, container, false);
    }

    @Override
    public void onPause() {
        super.onPause();

    }
}