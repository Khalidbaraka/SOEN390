package de.test.antennapod.espresso.discoveryPage;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.FlakyTest;
import android.support.test.rule.ActivityTestRule;
import android.util.Log;
import android.view.View;

import com.robotium.solo.Solo;
import com.robotium.solo.Timeout;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import de.danoeh.antennapod.R;
import de.danoeh.antennapod.activity.MainActivity;
import de.danoeh.antennapod.activity.PreferenceActivity;
import de.danoeh.antennapod.core.preferences.UserPreferences;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class DiscoveryPageEspressoTest {

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
    public void testGoToDiscover() {
        // discovery page
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        String currentPage = getActionbarTitle();
        if (currentPage.equals("Queue")){
            solo.clickOnText(solo.getString(R.string.discovery_page_label));
            solo.waitForView(android.R.id.list);
            assertEquals(solo.getString(R.string.discovery_page_label), getActionbarTitle());

        }else{
            assertEquals(solo.getString(R.string.discovery_page_label), getActionbarTitle());
        }
    }
    private String getActionbarTitle() {
        return ((MainActivity) solo.getCurrentActivity()).getSupportActionBar().getTitle().toString();
    }

}
