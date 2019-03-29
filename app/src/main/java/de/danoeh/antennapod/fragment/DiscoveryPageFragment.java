

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
import de.danoeh.antennapod.activity.RegisterAndLoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import android.widget.Toast;


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


    private View DiscoveryView;
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

        Button categoriesButton = DiscoveryView.findViewById(R.id.categories_button);

        Button luckyBtn = DiscoveryView.findViewById(R.id.luckyBtn);

        Button registerAndLoginButton = DiscoveryView.findViewById(R.id.register_and_login_main_layout_button);

        Button logoutButton = DiscoveryView.findViewById(R.id.logout);

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

        registerAndLoginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final MainActivity activity = (MainActivity) getActivity();
                Intent intent = new Intent(getActivity(), RegisterAndLoginActivity.class);
                activity.startActivity(intent);
                activity.finish();

            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getContext(), "Successfully Logged Out", Toast.LENGTH_SHORT).show();

            }
        });


            // Inflate the layout for this fragment
        return DiscoveryView;
        }


    }
