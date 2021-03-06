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
import de.danoeh.antennapod.activity.LoginActivity;
import de.danoeh.antennapod.activity.MainActivity;
import de.danoeh.antennapod.activity.RegisterAndLoginActivity;

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
    private Solo soloRegisterAndLogin;
    private Solo soloLogin;
    private FirebaseUser user;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);
    public ActivityTestRule<RegisterAndLoginActivity> mActivityRuleRegisterLogin = new ActivityTestRule<>(RegisterAndLoginActivity.class);
    public ActivityTestRule<LoginActivity> mActivityRuleLogin = new ActivityTestRule<>(LoginActivity.class);

    @Before
    public void setUp() {
        solo = new Solo(getInstrumentation(), mActivityRule.getActivity());
        soloRegisterAndLogin = new Solo(getInstrumentation(), mActivityRuleRegisterLogin.getActivity());
        soloLogin = new Solo(getInstrumentation(), mActivityRuleLogin.getActivity());
        Timeout.setSmallTimeout(500);
        Timeout.setLargeTimeout(1000);
    }

    @After
    public void tearDown() {
        solo.finishOpenedActivities();
    }

    @Test
    public void test01GoFirstToProfilePage() {
        solo = new Solo(getInstrumentation(), mActivityRule.getActivity());
        // queue page
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        String currentPage = getActionbarTitle();
        if (("Profile Page").equals(currentPage)){
            assertEquals(solo.getString(R.string.profile_page_label), getActionbarTitle());

        }else{
            solo.clickOnText(solo.getString(R.string.profile_page_label));
            solo.waitForView(android.R.id.list);
            assertEquals(solo.getString(R.string.profile_page_label), getActionbarTitle());
        }
    }

    @Test
    public void test02fLogInIfNotLoggedIn() {
        //Code added from Login UI test

        //Create User Based on Registered User
        getUser();
        solo.waitForView(android.R.id.list);

        //Assert current activity ActionBar is "Authentication".
        assertEquals("Profile Page", getActionbarTitle());

        //if user is already logged do nothing
        if (user != null && user.isEmailVerified()) {
        }

        //Else login
        else {
            //Checks button is there
            onView(withId(R.id.profile_register_and_login_btn)).check(matches(notNullValue()));

            //Checks button name matches
            onView(withId(R.id.profile_register_and_login_btn)).check(matches(withText("Authentication")));
            assertEquals("Authentication", solo.getString(R.string.authentication));

            //Press the Authentication button in the profile Page
            onView(withId(R.id.profile_register_and_login_btn)).perform(click());

            //--------Now in Authentication Activity ----------
            soloRegisterAndLogin.waitForView(0);

            //Assert current activity ActionBar is "Authentication".
            assertEquals(soloRegisterAndLogin.getString(R.string.title_activity_register_and_login), getActionbarTitleRegisterLogin());

            //Checks button / EditText is there
            onView(withId(R.id.login_main_layout_button)).check(matches(notNullValue()));

            //Checks button / EditText name matches
            onView(withId(R.id.login_main_layout_button)).check(matches(withText("Login")));
            assertEquals("Login", soloRegisterAndLogin.getString(R.string.login));

            //Press the Login button  in the Authentication Page
            onView(withId(R.id.login_main_layout_button)).perform(click());

            //--------Now in Login Activity ----------
            soloLogin.waitForView(0);
            Espresso.closeSoftKeyboard();

            //Assert current activity ActionBar is "Login".
            assertEquals(soloLogin.getString(R.string.title_activity_login), getActionbarTitleLogin());

            //Write user info in editTexts
            soloLogin.waitForView(0);

            onView(withId(R.id.input_email_login)).perform(clearText(),typeText("safi@maillink.top"));

            Espresso.closeSoftKeyboard();
            onView(withId(R.id.input_password_login)).perform(clearText(),typeText("password"));

            Espresso.closeSoftKeyboard();

            //Checks button / EditText is there
            onView(withId(R.id.btn_login)).check(matches(notNullValue()));

            //Checks button / EditText name matches
            onView(withId(R.id.btn_login)).check(matches(withText("Login")));
            assertEquals("Login", soloLogin.getString(R.string.login));

            //Press the Login button in the Login Page
            onView(withId(R.id.btn_login)).perform(click());

            solo.waitForView(android.R.id.list);

            //--------Now in Main Activity ----------
            solo.waitForView(android.R.id.list);
            solo.waitForView(android.R.id.list);

            //Assert current activity ActionBar is "Profile Page".
            assertEquals("Profile Page", getActionbarTitle());
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
        solo.waitForView(android.R.id.list);
        solo.waitForView(android.R.id.list);
        solo.waitForView(android.R.id.list);
        solo.waitForView(android.R.id.list);

        assertEquals("Profile Page", currentPage);

        // Verify the input components - Edit Text and Button
        onView(withId(R.id.edit_full_name)).check(matches(notNullValue()));
        onView(withId(R.id.edit_password)).check(matches(notNullValue()));
        onView(withId(R.id.reset_password_btn)).check(matches(notNullValue()));

        onView(withId(R.id.reset_password_btn)).check(matches(withText("Proceed")));
        assertEquals("Proceed", solo.getString(R.string.proceed));
        solo.waitForView(android.R.id.list);

        // Change the user's name - FIRST Try
        onView(withId(R.id.edit_full_name)).perform(clearText(),typeText("Captain Marvel"));
        solo.waitForView(android.R.id.list);
        solo.waitForView(android.R.id.list);

        Espresso.closeSoftKeyboard();
        solo.waitForView(android.R.id.list);
        solo.waitForView(android.R.id.list);
        solo.waitForView(android.R.id.list);

    }

    private String getActionbarTitle() {
        return ((MainActivity) solo.getCurrentActivity()).getSupportActionBar().getTitle().toString();
    }

    private String getActionbarTitleRegisterLogin() {
        return ((RegisterAndLoginActivity) soloRegisterAndLogin.getCurrentActivity()).getSupportActionBar().getTitle().toString();
    }

    private String getActionbarTitleLogin() {
        return ((LoginActivity) soloLogin.getCurrentActivity()).getSupportActionBar().getTitle().toString();
    }

    private void getUser() {
        //Create User Based on Registered User
        user = FirebaseAuth.getInstance().getCurrentUser();
    }

}
