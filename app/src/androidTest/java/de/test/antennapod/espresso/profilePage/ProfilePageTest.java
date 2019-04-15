package de.test.antennapod.espresso.profilePage;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.robotium.solo.Solo;
import com.robotium.solo.Timeout;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import de.danoeh.antennapod.R;
import de.danoeh.antennapod.activity.MainActivity;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProfilePageTest {

    private Solo solo;
    private static final int IMAGE_REQUEST = 1;
    private FirebaseAuth auth;
    private FirebaseUser currentUser;
    private DatabaseReference reference;


    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() {
        solo = new Solo(getInstrumentation(), mActivityRule.getActivity());
        Timeout.setSmallTimeout(500);
        Timeout.setLargeTimeout(1000);
    }

    @After
    public void tearDown() {
        solo.finishOpenedActivities();
    }

    @Test
    public void test1GoFirstToQueue() {
        // queue page
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        String currentPage = getActionbarTitle();
        if (currentPage.equals("Queue")){
            assertEquals(solo.getString(R.string.queue_label), getActionbarTitle());

        }else{
            solo.clickOnText(solo.getString(R.string.queue_label));
            solo.waitForView(android.R.id.list);
            assertEquals(solo.getString(R.string.queue_label), getActionbarTitle());
        }
    }

    @Test
    public void test2GoToProfilePage() {
        // profile page
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        String currentPage = getActionbarTitle();
        if (currentPage.equals("Queue")){
            solo.clickOnText(solo.getString(R.string.profile_page_label));
            solo.waitForView(android.R.id.list);
            assertEquals(solo.getString(R.string.profile_page_label), getActionbarTitle());

        }else{
            assertEquals(solo.getString(R.string.profile_page_label), getActionbarTitle());
        }
    }

    @Test
    public void test3GoToEditProfile() {

        String currentPage = getActionbarTitle();

        //selects & opens the Edit Profile page
        onView(withId(R.id.profile_option_list)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("Edit Profile")), click()));
        solo.waitForView(android.R.id.list);
        assertEquals("Profile Page", currentPage);

        // Verify the input components - Edit Text and Button
        onView(withId(R.id.edit_full_name)).check(matches(notNullValue()));
        onView(withId(R.id.edit_password)).check(matches(notNullValue()));
        onView(withId(R.id.reset_password_btn)).check(matches(notNullValue()));

        onView(withId(R.id.reset_password_btn)).check(matches(withText("Proceed")));
        assertEquals("Proceed", solo.getString(R.string.proceed));

        // Change the user's name
        onView(withId(R.id.edit_full_name)).perform(clearText(),typeText("Goose"));

        Espresso.closeSoftKeyboard();
        solo.waitForView(android.R.id.list);

        // Press on Process
        onView(withId(R.id.reset_password_btn)).perform(click());
        solo.waitForView(android.R.id.list);

        //selects & opens the Edit Profile page again
        onView(withId(R.id.profile_option_list)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("Edit Profile")), click()));
        solo.waitForView(android.R.id.list);

        // Change the user's password
        onView(withId(R.id.edit_password)).perform(clearText(),typeText("password"));

        Espresso.closeSoftKeyboard();
        solo.waitForView(android.R.id.list);

        // Press on Process
        onView(withId(R.id.reset_password_btn)).perform(click());
        solo.waitForView(android.R.id.list);
    }

    @Test
    public void test4EditProfilePicture() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityRule.getActivity().startActivityForResult(intent, IMAGE_REQUEST);

    }

    private String getActionbarTitle() {
        return ((MainActivity) solo.getCurrentActivity()).getSupportActionBar().getTitle().toString();
    }

}
