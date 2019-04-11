package de.danoeh.antennapod.fragment;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

import de.danoeh.antennapod.R;
import de.danoeh.antennapod.model.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditProfileFragment extends Fragment {

    public static final String TAG = "EditProfileFragment";

    private FirebaseAuth auth;
    private FirebaseUser currentUser;
    private DatabaseReference reference;

    private StorageReference storageReference;
    private static final int IMAGE_REQUEST = 1;
    private Uri imageUri;
    private StorageTask uploadTask;

    private View editProfileView;
    private EditText editFullName, editPassword;
    private TextView profileName, profileEmail;
    private ImageView editProfileImage;
    private Button submitBtn;
    private ProgressBar progressBar;

    private User currentUserInfo;

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

        // Instantiate Firebase variables
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("users");
        storageReference = FirebaseStorage.getInstance().getReference("profile-images");

        // Prevent the user access if he's not logged in
        if (currentUser != null && currentUser.isEmailVerified()) {
            // Continue
        } else {
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

        profileName = (TextView) editProfileView.findViewById(R.id.profile_name);
        profileEmail = (TextView) editProfileView.findViewById(R.id.profile_email);

        editFullName = (EditText) editProfileView.findViewById(R.id.edit_full_name);
        editPassword = (EditText) editProfileView.findViewById(R.id.edit_password);
        editProfileImage = (ImageView) editProfileView.findViewById(R.id.edit_profile_image);
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
            }
        });

        editProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImage();
            }
        });

        return editProfileView;
    }

    private void loadUserInformation() {

        if (currentUser != null && currentUser.isEmailVerified()) {
            progressBar.setVisibility(View.VISIBLE);
            reference.child(currentUser.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    progressBar.setVisibility(View.GONE);
                    currentUserInfo = dataSnapshot.getValue(User.class);

                    profileName.setText(currentUserInfo.getFullName());
                    profileEmail.setText(currentUserInfo.getEmail());
                    editFullName.setHint(currentUserInfo.getFullName());

                    if (currentUserInfo.getImageURL().equals("default")) {
                        editProfileImage.setImageResource(R.drawable.register_and_login_icon);
                    } else {
                        Glide.with(getContext()).load(currentUserInfo.getImageURL()).into(editProfileImage);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d(TAG,"The read failed: " + databaseError.getCode());
                }
            });

        }
    }

    private void editUserInformation() {

        User updatedUser;

        // Get user information
        String email = currentUser.getEmail();
        String mUri = currentUserInfo.getImageURL();

        String fullName = editFullName.getText().toString().trim();

        String updatedPassword;

        if (currentUser != null && currentUser.isEmailVerified()) {

            if (editFullName.getText().toString().trim().length() > 0) {
                progressBar.setVisibility(View.VISIBLE);

                updatedUser = new User(email, fullName, mUri);

                reference.child(currentUser.getUid()).setValue(updatedUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressBar.setVisibility(View.GONE);

                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(), "Profile successfully updated.  ",
                                    Toast.LENGTH_SHORT).show();
                            getFragmentManager().popBackStack();
                        } else {
                            Toast.makeText(getActivity(), "Profile update failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                            getFragmentManager().popBackStack();
                        }
                    }
                });
            }

            else if (editPassword.getText().toString().trim().length() >= 6) {
                progressBar.setVisibility(View.VISIBLE);

                updatedPassword = editPassword.getText().toString().trim();

                currentUser.updatePassword(updatedPassword)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            progressBar.setVisibility(View.GONE);

                            if (task.isSuccessful()) {
                                Toast.makeText(getActivity(), "Password is updated!", Toast.LENGTH_SHORT).show();
                                getFragmentManager().popBackStack();

                            } else {
                                Toast.makeText(getActivity(), "Failed to update password!", Toast.LENGTH_SHORT).show();
                                getFragmentManager().popBackStack();

                            }
                        }
                    });
            }

            else {
                Toast.makeText(getActivity(), "No input. Returning..  ",
                        Toast.LENGTH_SHORT).show();
                getFragmentManager().popBackStack();
            }
        }
    }

    private void openImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getMimeTypeFromExtension(contentResolver.getType(uri));
    }

    private void uploadImage() {
        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setMessage("Uploading.. ");
        pd.show();

        if (imageUri != null) {
            final StorageReference fileReference = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));

            uploadTask = fileReference.putFile(imageUri);

            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        String mUri = downloadUri.toString();
                        String email = currentUser.getEmail();
                        String fullName = currentUserInfo.getFullName();

                        User updatedUser = new User(email, fullName, mUri);

                        reference.child(currentUser.getUid()).setValue(updatedUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                progressBar.setVisibility(View.GONE);

                                if (task.isSuccessful()) {
                                    Toast.makeText(getActivity(), "Profile successfully updated.  ",
                                            Toast.LENGTH_SHORT).show();
                                    getFragmentManager().popBackStack();
                                } else {
                                    Toast.makeText(getActivity(), "Profile update failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                    getFragmentManager().popBackStack();
                                }
                            }
                        });

                        pd.dismiss();
                    } else {
                        Toast.makeText(getContext(), "Failed ", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    pd.dismiss();
                }
            });
        } else {
            Toast.makeText(getContext(), "No image selected.", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == IMAGE_REQUEST && resultCode == Activity.RESULT_OK
            && data != null && data.getData() != null) {

            imageUri = data.getData();

            if (uploadTask != null && uploadTask.isInProgress()) {
                Toast.makeText(getContext(), "Upload is in progress.. ", Toast.LENGTH_SHORT).show();
            } else {
                uploadImage();
            }
        }
    }
}
