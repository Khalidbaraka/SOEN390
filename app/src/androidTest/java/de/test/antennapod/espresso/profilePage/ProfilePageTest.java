package de.test.antennapod.espresso.profilePage;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;
import com.robotium.solo.Timeout;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import de.danoeh.antennapod.R;
import de.danoeh.antennapod.activity.MainActivity;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

public class ProfilePageTest {

    private Solo solo;

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
    public void testGoFirstToQueue() {
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
    public void testGoToProfilePage() {
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
    public void testGoToEditProfile() {

        String currentPage = getActionbarTitle();

        //selects & opens the category list item Business
        onView(withId(R.id.profile_option_list)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("Edit Profile")), click()));
        solo.waitForView(android.R.id.list);
        assertEquals("Profile Page", currentPage);


    }

    private String getActionbarTitle() {
        return ((MainActivity) solo.getCurrentActivity()).getSupportActionBar().getTitle().toString();
    }

}
