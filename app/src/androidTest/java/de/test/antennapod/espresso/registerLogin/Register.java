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
        import de.danoeh.antennapod.activity.ForgotPasswordActivity;
        import de.danoeh.antennapod.activity.LoginActivity;
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
public class Register {

    private Solo solo;
    private Solo soloRegisterAndLogin;
    private Solo soloRegister;
    private Solo soloLogin;
    private Solo soloForgotPassword;
    private FirebaseUser user;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);
    public ActivityTestRule<RegisterAndLoginActivity> mActivityRuleRegisterLogin = new ActivityTestRule<>(RegisterAndLoginActivity.class);
    public ActivityTestRule<RegisterActivity> mActivityRuleRegister = new ActivityTestRule<>(RegisterActivity.class);
    public ActivityTestRule<LoginActivity> mActivityRuleLogin = new ActivityTestRule<>(LoginActivity.class);
    public ActivityTestRule<ForgotPasswordActivity> mActivityRuleForgotPassword = new ActivityTestRule<>(ForgotPasswordActivity.class);

    @Before
    public void setUp() {
        solo = new Solo(getInstrumentation(), mActivityRule.getActivity());
        soloRegisterAndLogin = new Solo(getInstrumentation(), mActivityRuleRegisterLogin.getActivity());
        soloRegister = new Solo(getInstrumentation(), mActivityRuleRegister.getActivity());
        soloLogin = new Solo(getInstrumentation(), mActivityRuleLogin.getActivity());
        soloForgotPassword = new Solo(getInstrumentation(), mActivityRuleForgotPassword.getActivity());
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

    private String getActionbarTitleForgotPassword() {
        return ((ForgotPasswordActivity) soloForgotPassword.getCurrentActivity()).getSupportActionBar().getTitle().toString();
    }

    private void deleteUser() {
            user.delete();
    }
}
