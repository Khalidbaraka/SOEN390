package de.danoeh.antennapod.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
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
import de.danoeh.antennapod.activity.LoginActivity;
import de.danoeh.antennapod.activity.MainActivity;
import de.danoeh.antennapod.activity.RegisterAndLoginActivity;
import de.danoeh.antennapod.model.ProfileItem;
import de.danoeh.antennapod.model.User;

public class ProfilePageFragment extends Fragment {

    private View profilePageView;
    public static final String TAG = "ProfilePageFragment";
    private Button registerAndLoginBtn, logoutBtn;
    private TextView profileName;
    private FirebaseAuth auth;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;


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

        if (auth.getCurrentUser() == null) {
            registerAndLoginBtn.setVisibility(View.VISIBLE);
            logoutBtn.setVisibility(View.GONE);
        } else {
            registerAndLoginBtn.setVisibility(View.GONE);
            logoutBtn.setVisibility(View.VISIBLE);
        }

        loadUserInformation();

        ArrayList<ProfileItem> profileItems = new ArrayList<>();
        profileItems.add(new ProfileItem(R.drawable.common_google_signin_btn_icon_dark_normal_background, "Line 1"));
        profileItems.add(new ProfileItem(R.drawable.ic_mr_button_connected_03_dark, "Line 2"));

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

                final MainActivity activity = (MainActivity) getActivity();
                Intent intent = new Intent(getActivity(), ProfilePageFragment.class);
                activity.startActivity(intent);
                activity.loadChildFragment(new DiscoveryPageFragment());
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
                    System.out.println(user);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            });

        }
    }




//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }


}

