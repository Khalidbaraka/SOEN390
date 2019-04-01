
package de.test.antennapod.espresso.registerLogin;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.rule.ActivityTestRule;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
import de.danoeh.antennapod.activity.RegisterActivity;
import de.danoeh.antennapod.activity.RegisterAndLoginActivity;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertEquals;

/*
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SwitchBetweenLoginRegister {

    private Solo solo;
    private Solo soloRegisterAndLogin;
    private Solo soloRegister;
    private Solo soloLogin;
    private FirebaseUser user;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);
    public ActivityTestRule<RegisterAndLoginActivity> mActivityRuleRegisterLogin = new ActivityTestRule<>(RegisterAndLoginActivity.class);
    public ActivityTestRule<RegisterActivity> mActivityRuleRegister = new ActivityTestRule<>(RegisterActivity.class);
    public ActivityTestRule<LoginActivity> mActivityRuleLogin = new ActivityTestRule<>(LoginActivity.class);

    @Before
    public void setUp() {
        solo = new Solo(getInstrumentation(), mActivityRule.getActivity());
        soloRegisterAndLogin = new Solo(getInstrumentation(), mActivityRuleRegisterLogin.getActivity());
        soloRegister = new Solo(getInstrumentation(), mActivityRuleRegister.getActivity());
        soloLogin = new Solo(getInstrumentation(), mActivityRuleLogin.getActivity());
        Timeout.setSmallTimeout(100);
        Timeout.setLargeTimeout(200);

    }

    @After
    public void tearDown() {
        solo.finishOpenedActivities();
        //Delete Registered email
    }

    @Test
    public void test1GoFirstToDiscoveryPage() {
        solo = new Solo(getInstrumentation(), mActivityRule.getActivity());
        // queue page
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        String currentPage = getActionbarTitle();
        if (("Discovery Page").equals(currentPage)){
            assertEquals(solo.getString(R.string.discovery_page_label), getActionbarTitle());

        }else{
            solo.clickOnText(solo.getString(R.string.discovery_page_label));
            solo.waitForView(android.R.id.list);
            assertEquals(solo.getString(R.string.discovery_page_label), getActionbarTitle());
        }
    }

    @Test
    public void test2SwitchBetweenLoginRegister() {

        //Create User Based on Registered User
        user = FirebaseAuth.getInstance().getCurrentUser();
        solo.waitForView(android.R.id.list);

        //if user is already logged in, then start by logging out
        if(user != null && user.isEmailVerified())
        {
            solo.waitForView(android.R.id.list);

            //Assert current activity ActionBar is "Authentication".
            assertEquals("Discovery Page", getActionbarTitle());

            //Checks button is there
            onView(withId(R.id.logout)).check(matches(notNullValue()));

            //Checks button name matches
            onView(withId(R.id.logout)).check(matches(withText("Logout")));
            assertEquals("Logout", solo.getString(R.string.logout_button));

            //Press the Register And Login button in the Discovery Page
            onView(withId(R.id.logout)).perform(click());

            solo.waitForView(android.R.id.list);
            solo.waitForView(android.R.id.list);

        }

        //Checks button is there
        onView(withId(R.id.register_and_login_main_layout_button)).check(matches(notNullValue()));

        //Checks button name matches
        onView(withId(R.id.register_and_login_main_layout_button)).check(matches(withText("Register and Login")));
        assertEquals("Register and Login", solo.getString(R.string.register_and_login));

        //Press the Register And Login button in the Discovery Page
        onView(withId(R.id.register_and_login_main_layout_button)).perform(click());

        //--------Now in RegisterLogin Activity ----------
        soloRegisterAndLogin.waitForView(0);

        //Assert current activity ActionBar is "Authentication".
        assertEquals(soloRegisterAndLogin.getString(R.string.title_activity_register_and_login), getActionbarTitleRegisterLogin());

        //Checks button / EditText is there
        onView(withId(R.id.login_main_layout_button)).check(matches(notNullValue()));

        //Checks button / EditText name matches
        onView(withId(R.id.login_main_layout_button)).check(matches(withText("Login")));
        assertEquals("Login", soloRegisterAndLogin.getString(R.string.login));

        //Press the Register And Login button in the Discovery Page
        onView(withId(R.id.login_main_layout_button)).perform(click());

        //--------Now in Login Activity ----------
        soloLogin.waitForView(0);
        Espresso.closeSoftKeyboard();

        //Assert current activity ActionBar is "Register".
        assertEquals(soloLogin.getString(R.string.title_activity_login), getActionbarTitleLogin());

        //Checks button / EditText is there
        onView(withId(R.id.dontHaveAccount)).check(matches(notNullValue()));

        //Checks button / EditText name matches
        onView(withId(R.id.dontHaveAccount)).check(matches(withText("Don't have an account? Sign Up")));
        assertEquals("Don't have an account? Sign Up", soloLogin.getString(R.string.DontHaveAccount));

        soloLogin.waitForView(0);

        //Press the Register And Login button in the Discovery Page
        onView(withId(R.id.dontHaveAccount)).perform(click());

        //--------Now in Register Activity ----------
        soloRegister.waitForView(0);
        Espresso.closeSoftKeyboard();

        //Assert current activity ActionBar is "Register".
        assertEquals(soloRegister.getString(R.string.title_activity_register), getActionbarTitleRegister());

        //Checks button / EditText is there
        onView(withId(R.id.alreadHaveAccount)).check(matches(notNullValue()));

        //Checks button / EditText name matches
        onView(withId(R.id.alreadHaveAccount)).check(matches(withText("Already have an account? Login")));
        assertEquals("Already have an account? Login", soloRegister.getString(R.string.alreadHaveAccount));

        soloRegister.waitForView(0);

        //Press the Register And Login button in the Discovery Page
        onView(withId(R.id.alreadHaveAccount)).perform(click());

        //--------Now back in Login Activity ----------
        soloLogin.waitForView(0);
        Espresso.closeSoftKeyboard();

        //Assert current activity ActionBar is "Register".
        assertEquals(soloLogin.getString(R.string.title_activity_login), getActionbarTitleLogin());
        soloLogin.waitForView(0);
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

    private String getActionbarTitleLogin() {
        return ((LoginActivity) soloLogin.getCurrentActivity()).getSupportActionBar().getTitle().toString();
    }
}
*/
