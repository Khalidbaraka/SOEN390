package de.danoeh.antennapod.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

    private View profilePageView;
    public static final String TAG = "ProfilePageFragment";
    private Button registerAndLoginBtn, logoutBtn;
    private TextView profileName, profileEmail;
    private FirebaseAuth auth;

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
        auth = FirebaseAuth.getInstance();

        // Inflate the layout for this fragment
        profilePageView = inflater.inflate(R.layout.fragment_user_profile, container, false);

        registerAndLoginBtn = (Button) profilePageView.findViewById(R.id.profile_register_and_login_btn);
        logoutBtn = (Button) profilePageView.findViewById(R.id.profile_logout_btn);
        profileName = (TextView) profilePageView.findViewById(R.id.profile_name);
        profileEmail = (TextView) profilePageView.findViewById(R.id.profile_email);

        if (auth.getCurrentUser() == null || !(auth.getCurrentUser().isEmailVerified())) {
            registerAndLoginBtn.setVisibility(View.VISIBLE);
            logoutBtn.setVisibility(View.GONE);
            profileName.setVisibility(View.GONE);
            profileEmail.setVisibility(View.GONE);
        } else {
            registerAndLoginBtn.setVisibility(View.GONE);
            logoutBtn.setVisibility(View.VISIBLE);
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
                FirebaseAuth.getInstance().signOut();
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
        auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        final  FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("users");

        if (currentUser != null) {
            ref.child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    User user = dataSnapshot.getValue(User.class);
                    profileName.setText(user.getFullName());
                    profileEmail.setText(user.getEmail());
                    System.out.println(user);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            });

        }
    }

}

