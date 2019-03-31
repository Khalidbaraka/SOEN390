package de.danoeh.antennapod.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.danoeh.antennapod.R;
import de.danoeh.antennapod.activity.MainActivity;
import de.danoeh.antennapod.activity.RegisterActivity;
import de.danoeh.antennapod.model.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditProfileFragment extends Fragment {

    private FirebaseAuth auth;
    private View editProfileView;
    private EditText editFullName, editPassword;
    private Button submitBtn;
    private ProgressBar progressBar;


    public EditProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        auth = FirebaseAuth.getInstance();

        // Prevent the user access if he's not logged in
        if (auth.getCurrentUser() == null) {

            Toast.makeText(getActivity(), "Please log in first.  ",
                    Toast.LENGTH_SHORT).show();

            Fragment mFragment = new ProfilePageFragment();
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_view, mFragment )
                    .commit();
        }

        // Inflate the layout for this fragment
        editProfileView =  inflater.inflate(R.layout.fragment_edit_profile, container, false);

        editFullName = (EditText) editProfileView.findViewById(R.id.edit_full_name);
        editPassword = (EditText) editProfileView.findViewById(R.id.edit_password);
        submitBtn = (Button) editProfileView.findViewById(R.id.reset_password_btn);
        progressBar = (ProgressBar) editProfileView.findViewById(R.id.progressBar);

        // Load current user information: full name
        loadUserInformation();

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                editUserInformation();

                // Release the keyboard after submit
                InputMethodManager inputManager = (InputMethodManager)
                        getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow((null == getActivity().getCurrentFocus()) ? null : getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

//                getFragmentManager().popBackStack();
                Fragment mFragment = new ProfilePageFragment();
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, mFragment )
                        .commit();
            }
        });

        return editProfileView;
    }

    private void loadUserInformation() {
        auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("users");

        if (currentUser != null) {
            progressBar.setVisibility(View.VISIBLE);
            ref.child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    progressBar.setVisibility(View.GONE);
                    User user = dataSnapshot.getValue(User.class);
                    editFullName.setHint(user.getFullName());
                    System.out.println(user);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            });

        }
    }

    private void editUserInformation() {
        auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("users");

        // Get user information
        String email = currentUser.getEmail();
        String fullName = editFullName.getText().toString().trim();

        User updatedUser = new User(email, fullName);
        String updatedPassword = editPassword.getText().toString().trim();

        if (currentUser != null) {

            if (editFullName.getText().toString().trim().length() > 0) {
                progressBar.setVisibility(View.VISIBLE);

                ref.child(auth.getCurrentUser().getUid()).setValue(updatedUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressBar.setVisibility(View.GONE);

                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(), "Profile successfully updated.  ",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "Profile update failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }


            if (editPassword.getText().toString().trim().length() >= 6) {
                progressBar.setVisibility(View.VISIBLE);

                currentUser.updatePassword(updatedPassword)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            progressBar.setVisibility(View.GONE);

                            if (task.isSuccessful()) {
                                Toast.makeText(getActivity(), "Password is updated!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "Failed to update password!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            }
        }
    }

}
