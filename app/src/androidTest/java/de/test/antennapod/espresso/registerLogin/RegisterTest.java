package de.test.antennapod.espresso.registerLogin;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.rule.ActivityTestRule;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
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
import de.danoeh.antennapod.activity.RegisterActivity;
import de.danoeh.antennapod.activity.RegisterAndLoginActivity;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertEquals;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RegisterTest {

    private Solo solo;
    private Solo soloRegisterAndLogin;
    private Solo soloRegister;
    private FirebaseUser user;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);
    public ActivityTestRule<RegisterAndLoginActivity> mActivityRuleRegisterLogin = new ActivityTestRule<>(RegisterAndLoginActivity.class);
    public ActivityTestRule<RegisterActivity> mActivityRuleRegister = new ActivityTestRule<>(RegisterActivity.class);

    @Before
    public void setUp() {
        solo = new Solo(getInstrumentation(), mActivityRule.getActivity());
        soloRegisterAndLogin = new Solo(getInstrumentation(), mActivityRuleRegisterLogin.getActivity());
        soloRegister = new Solo(getInstrumentation(), mActivityRuleRegister.getActivity());
        Timeout.setSmallTimeout(100);
        Timeout.setLargeTimeout(200);

        //Make sure you are logged out
    }

    @After
    public void tearDown() {
        solo.finishOpenedActivities();
        //Delete Registered email
    }

    @Test
    public void test1GoFirstToProfilePage() {
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
    public void test2LogoutIfLoggedIn() {

        //Create User Based on Registered User
        getUser();
        solo.waitForView(android.R.id.list);

        //Assert current activity ActionBar is "Authentication".
        assertEquals("Profile Page", getActionbarTitle());

        //if user is already logged in, then start by logging out
        if(user != null && user.isEmailVerified())
        {
            //Checks button is there
            onView(withId(R.id.profile_logout_btn)).check(matches(notNullValue()));

            //Checks button name matches
            onView(withId(R.id.profile_logout_btn)).check(matches(withText("Logout")));
            assertEquals("Logout", solo.getString(R.string.logout_button));

            //Press the logout button in the Profile Page
            onView(withId(R.id.profile_logout_btn)).perform(click());

            solo.waitForView(android.R.id.list);
            solo.waitForView(android.R.id.list);
        }

    }


    @Test
    public void test3RegisterTest() {

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
        onView(withId(R.id.register_main_layout_button)).check(matches(notNullValue()));

        //Checks button / EditText name matches
        onView(withId(R.id.register_main_layout_button)).check(matches(withText("Register")));
        assertEquals("Register", soloRegisterAndLogin.getString(R.string.register));

        //Press the Authentication button in the Profile Page
        onView(withId(R.id.register_main_layout_button)).perform(click());

        //--------Now in Register Activity ----------
        soloRegister.waitForView(0);
        Espresso.closeSoftKeyboard();

        //Assert current activity ActionBar is "Register".
        assertEquals(soloRegister.getString(R.string.title_activity_register), getActionbarTitleRegister());

        soloRegister.waitForView(0);

        //Write user info in editTexts
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.fullNameRegister)).perform(clearText(),typeText("Johnny Doe"));


        Espresso.closeSoftKeyboard();
        onView(withId(R.id.emailRegister)).perform(clearText(),typeText("John.Doe.Soen390_10@mail.com"));


        Espresso.closeSoftKeyboard();
        onView(withId(R.id.passwordRegister)).perform(clearText(),typeText("password"));


        Espresso.closeSoftKeyboard();

        //Checks button / EditText is there
        onView(withId(R.id.registerButton)).check(matches(notNullValue()));

        //Checks button / EditText name matches
        onView(withId(R.id.registerButton)).check(matches(withText("Sign Up")));
        assertEquals("Sign Up", soloRegister.getString(R.string.registerButton));

        //Press the Authentication button in the Profile Page
        onView(withId(R.id.registerButton)).perform(click());

        soloRegister.waitForView(android.R.id.list);

        //Create User Based on Registered User
        getUser();
        solo.waitForView(android.R.id.list);

        //Successfully Registered
        //--------Now in Main Activity ----------
        solo.waitForView(android.R.id.list);
        solo.waitForView(android.R.id.list);
        solo.waitForView(android.R.id.list);
        solo.waitForView(android.R.id.list);

        //Assert current activity ActionBar is "Authentication".
        assertEquals("Profile Page", getActionbarTitle());

        //Delete User Account of Registered User
        deleteUser();

        //Checks button is there after you register
        onView(withId(R.id.profile_register_and_login_btn)).check(matches(notNullValue()));

    }


    private String getActionbarTitle() {
        return ((MainActivity) solo.getCurrentActivity()).getSupportActionBar().getTitle().toString();
    }

    private String getActionbarTitleRegisterLogin() {
        return ((RegisterAndLoginActivity) soloRegisterAndLogin.getCurrentActivity()).getSupportActionBar().getTitle().toString();
    }

    private String getActionbarTitleRegister() {
        return ((RegisterActivity) soloRegister.getCurrentActivity()).getSupportActionBar().getTitle().toString();
    }

    private void getUser() {
        //Create User Based on Registered User
        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    private void deleteUser() {
        FirebaseDatabase.getInstance().getReference("users").child(user.getUid()).removeValue();
        user.delete();
    }
}
