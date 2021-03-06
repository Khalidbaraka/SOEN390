package de.danoeh.antennapod.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.danoeh.antennapod.R;
import de.danoeh.antennapod.activity.MainActivity;
import de.danoeh.antennapod.activity.RegisterAndLoginActivity;
import de.danoeh.antennapod.adapter.ProfileItemAdapter;
import de.danoeh.antennapod.model.ProfileItem;
import de.danoeh.antennapod.model.User;

public class ProfilePageFragment extends Fragment {

    public static final String TAG = "ProfilePageFragment";

    private FirebaseAuth auth;
    private FirebaseUser currentUser;
    private DatabaseReference reference;

    private View profilePageView;
    private Button registerAndLoginBtn, logoutBtn;
    private TextView profileName, profileEmail;
    private ImageView profileImage;
    private ProgressBar progressBar;

    private RecyclerView recyclerView;
    private ProfileItemAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    Context context;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Instantiate Firebase variables
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("users");

        // Inflate the layout for this fragment
        profilePageView = inflater.inflate(R.layout.fragment_user_profile, container, false);

        registerAndLoginBtn = (Button) profilePageView.findViewById(R.id.profile_register_and_login_btn);
        logoutBtn = (Button) profilePageView.findViewById(R.id.profile_logout_btn);
        profileName = (TextView) profilePageView.findViewById(R.id.profile_name);
        profileEmail = (TextView) profilePageView.findViewById(R.id.profile_email);
        profileImage = (ImageView) profilePageView.findViewById(R.id.profile_image);
        progressBar = (ProgressBar) profilePageView.findViewById(R.id.progressBar);

        // Set components visibility depending on the user's authentication
        if (currentUser != null && (currentUser.isEmailVerified())) {
            registerAndLoginBtn.setVisibility(View.GONE);
            logoutBtn.setVisibility(View.VISIBLE);
        } else {
            registerAndLoginBtn.setVisibility(View.VISIBLE);
            logoutBtn.setVisibility(View.GONE);
            profileName.setVisibility(View.GONE);
            profileEmail.setVisibility(View.GONE);
        }

        loadUserInformation();

        recyclerView =  (RecyclerView) profilePageView.findViewById(R.id.profile_option_list);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ArrayList<ProfileItem> profileItems = new ArrayList<>();
        profileItems.add(new ProfileItem(R.drawable.outline_edit_black_18dp, "Edit Profile"));
        profileItems.add(new ProfileItem(R.drawable.outline_mode_comment_black_18dp, "My Comments"));
        recyclerView.setHasFixedSize(true);

        adapter = new ProfileItemAdapter(getActivity(), profileItems);
        recyclerView.setAdapter(adapter);

        registerAndLoginBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final MainActivity activity = (MainActivity) getActivity();
                Intent intent = new Intent(getActivity(), RegisterAndLoginActivity.class);
                activity.startActivity(intent);
                activity.finish();
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                auth.signOut();
                Toast.makeText(getContext(), "Successfully Logged Out", Toast.LENGTH_SHORT).show();

                Fragment mFragment = new ProfilePageFragment();
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, mFragment )
                        .commit();
            }
        });

        return profilePageView;
    }

    private void loadUserInformation() {

        if (currentUser != null && (currentUser.isEmailVerified())) {

            progressBar.setVisibility(View.VISIBLE);

            reference.child(currentUser.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    progressBar.setVisibility(View.GONE);

                    User user = dataSnapshot.getValue(User.class);
                    profileName.setText(user.getFullName());
                    profileEmail.setText(user.getEmail());

                    if (user.getImageURL().equals("default")) {
                        profileImage.setImageResource(R.drawable.register_and_login_icon);
                    } else {
                        Glide.with(getActivity()).load(user.getImageURL()).into(profileImage);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d(TAG,"The read failed: " + databaseError.getCode());
                }
            });

        }
    }

}

