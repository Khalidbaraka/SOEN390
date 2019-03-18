package de.danoeh.antennapod.fragment;

        import android.os.Bundle;
        import android.support.annotation.Nullable;
        import android.support.design.widget.TabLayout;
        import android.support.v4.app.Fragment;
        import android.support.v4.view.ViewPager;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;

        import de.danoeh.antennapod.R;
        import de.danoeh.antennapod.adapter.SubscriptionFavoritePodcastsAdapter;

public class SubscriptionFavoritePodcastsFragment extends Fragment {

    public static final String TAG = "SubscriptionFavoritePodcastsFragment";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        TabLayout tabLayout;
        ViewPager viewPager;
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.subscriptionfavoritepodcasts, container, false);
        tabLayout = myView.findViewById(R.id.subscriptionfavoritepodcasts_tablayout_id);
        viewPager = myView.findViewById(R.id.subscriptionfavoritepodcasts_viewpager_id);

        SubscriptionFavoritePodcastsAdapter adapter = new SubscriptionFavoritePodcastsAdapter(getFragmentManager());
        adapter.addFragment(new SubscriptionFragment(), "Subscription");
        adapter.addFragment(new FavoritePodcastsFragment(), "Favorite Podcasts");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        return myView;
    }




}