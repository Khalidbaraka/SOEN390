package de.danoeh.antennapod.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import de.danoeh.antennapod.R;
import de.danoeh.antennapod.activity.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.AdListener;


import android.os.Bundle;

import static android.view.View.GONE;

//Uncomment for later use
//import android.widget.Toast;
//import de.danoeh.antennapod.activity.MainActivity;

/**
 * Shows Categories, Top Podcast, & More.
 * Discover your podcast here!
 *
 *
 * Links to categories pages
 */

public class DiscoveryPageFragment extends Fragment {

    private AdView mAdView;
    private View DiscoveryView;
    private InterstitialAd mInterstitialAd;
    private TextView txtHome;
    public static final String TAG = "DiscoveryPageFragment";
    private FirebaseAuth auth;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        auth = FirebaseAuth.getInstance();



        DiscoveryView = inflater.inflate(R.layout.discovery_page, container, false);

        mAdView = DiscoveryView.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);




        Button categoriesButton = DiscoveryView.findViewById(R.id.categories_button);

        Button luckyBtn = DiscoveryView.findViewById(R.id.luckyBtn);
        
        luckyBtn.setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick(View view) {

                final MainActivity activity = (MainActivity) getActivity();

                //Replaces current Fragment with CategoriesListFragment
                activity.loadChildFragment(new FeelingLuckyFragment());
            }
        });

        //Button onClick opens CategoryListFragment

        // Categories Button
        categoriesButton.setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick(View view) {

                loadInterstitialAd();

                final MainActivity activity = (MainActivity) getActivity();

                //Replaces current Fragment with CategoriesListFragment
                activity.loadChildFragment(new Categories());
            }
        });



        //Where user writes text to Search
        EditText searchText = DiscoveryView.findViewById(R.id.editText);

        //Button used to submit Search
        Button searchButton = DiscoveryView.findViewById(R.id.button6);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view){

                final MainActivity activity = (MainActivity) getActivity();

                //Create new iTunesSearchFragment
                Fragment myItunesSearchFragment = new ItunesSearchFragment();

                //setCategoryName to the text written in the search box (EditText).Search it in the iTunesAPI.
                ((ItunesSearchFragment) myItunesSearchFragment).setCategoryName(searchText.getText().toString());

                //Loads iTunesSearchFragment with the podcasts related to the search result
                activity.loadChildFragment(myItunesSearchFragment);
            }

        });


        // Inflate the layout for this fragment
        return DiscoveryView;
    }

    public void loadInterstitialAd() {
        mInterstitialAd = new InterstitialAd(getContext());
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build());

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                mInterstitialAd.show();
            }
        });
    }


}