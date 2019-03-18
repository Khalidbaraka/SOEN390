

package de.danoeh.antennapod.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.view.ViewGroup;
import android.widget.TextView;
import de.danoeh.antennapod.R;
import de.danoeh.antennapod.activity.CategoriesActivity;

import de.danoeh.antennapod.activity.FeelingLuckyActivity;

import de.danoeh.antennapod.activity.MainActivity;


import android.os.Bundle;

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

public class DiscoveryPageFragment extends Fragment implements View.OnClickListener {


    private View DiscoveryView;
    private TextView txtHome;
    public static final String TAG = "DiscoveryPageFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        DiscoveryView = inflater.inflate(R.layout.discovery_page, container, false);

        Button categoriesButton = DiscoveryView.findViewById(R.id.categories_button);

        Button gpodnetCategoriesButton = DiscoveryView.findViewById(R.id.gpodnet_categories_button);
        // categoriesBtn.setOnClickListener(this);


        Button luckyBtn = DiscoveryView.findViewById(R.id.luckyBtn);
        luckyBtn.setOnClickListener(this);

        Button itunesCategoriesButton = DiscoveryView.findViewById(R.id.itunes_categories_button);

        // Categories Button
        categoriesButton.setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick(View view) {

                final MainActivity activity = (MainActivity) getActivity();

                //Replaces current Fragment with CategoriesListFragment
                activity.loadChildFragment(new Categories());

            }
        });

        //Button onClick opens CategoryListFragment
        itunesCategoriesButton.setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick(View view) {

                final MainActivity activity = (MainActivity) getActivity();

                //Replaces current Fragment with CategoriesListFragment
                activity.loadChildFragment(new CategoriesListFragment());

            }
        });

        // Gpodnet Categories Button
        gpodnetCategoriesButton.setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick(View view) {

                final MainActivity activity = (MainActivity) getActivity();

                //Replaces current Fragment with CategoriesListFragment
                activity.loadChildFragment(new GpodnetCategoriesFragment());

            }
        });




        // Inflate the layout for this fragment
        return DiscoveryView;


    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.categoriesBtn:
//                startActivity(new Intent(DiscoveryPageFragment.this.getActivity(), CategoriesActivity.class));
//                break;

            case R.id.luckyBtn:
                Intent i = new Intent(DiscoveryPageFragment.this.getActivity(), FeelingLuckyActivity.class);
                startActivity(i);
               break;


//FOR FUTURE BUTTONS - test with Toast
//                Toast.makeText(getActivity(),"Categories!",Toast.LENGTH_SHORT).show();
//            break;
//            case R.id.recentBtn:
//                Intent i = new Intent(DiscoveryPageFragment.this.getActivity(), CategoriesActivity.class));
//                startActivity(i);
//                break;
//            case R.id.newBtn:
//                Intent i = new Intent(DiscoveryPageFragment.this.getActivity(), CategoriesActivity.class));
//                startActivity(i);
//                break;
//            case R.id.top10Btn:
//                Intent i = new Intent(DiscoveryPageFragment.this.getActivity(), CategoriesActivity.class));
//                startActivity(i);
//                break;
            default:
                break;
        }
    }
}