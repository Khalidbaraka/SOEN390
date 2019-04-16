package de.test.antennapod.unitTests;

import android.support.annotation.NonNull;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import de.danoeh.antennapod.R;
import de.danoeh.antennapod.activity.MainActivity;
import de.danoeh.antennapod.fragment.EditProfileFragment;
import de.danoeh.antennapod.model.Printer;
import de.danoeh.antennapod.model.User;

import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class EditProfileTests {

    public static final String TAG = "EditProfileFragment";

    @Rule
    public ActivityTestRule<MainActivity> activityActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    AppCompatActivity activity;

    private FirebaseAuth auth;
    private FirebaseUser currentUser;
    private DatabaseReference reference;

    private StorageReference storageReference;

    private String email;
    private String fullName;
    private String password;
    private User currentUserInfo;

    @Test
    public void test1InvalidEmail(){
        email = "";
        fullName="Goose";

        EditProfileFragment editFragment = new EditProfileFragment();

        assertFalse(editFragment.checkEmailandFullNameValidationOnEditProfile(email, fullName, new Printer() {
            @Override
            public void print(int messageId) {
                Log.d(TAG, "testInvalidEmail: " + messageId);
                assertEquals(messageId, R.string.require_email);
            }
        }));
    }

    @Test
    public void test2ShortPassword(){
        password="Test";

        EditProfileFragment editFragment = new EditProfileFragment();

        assertFalse(editFragment.checkPasswordValidationOnEditProfile(password, new Printer() {
            @Override
            public void print(int messageId) {
                Log.d(TAG, "testShortPassword: " + messageId);
                assertEquals(messageId, R.string.warn_short_password);
            }
        }));
    }
}
