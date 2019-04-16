package de.test.antennapod.espresso.discoveryPage;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.FlakyTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.test.runner.lifecycle.Stage;
import android.util.Log;
import android.view.View;

import com.robotium.solo.Solo;
import com.robotium.solo.Timeout;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.internal.matchers.Null;

import java.security.spec.ECField;
import java.util.Collection;
import java.util.Iterator;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.action.ViewActions;
import de.danoeh.antennapod.R;
import de.danoeh.antennapod.activity.CommentListActivity;
import de.danoeh.antennapod.activity.MainActivity;
import de.danoeh.antennapod.activity.PreferenceActivity;
import de.danoeh.antennapod.core.preferences.UserPreferences;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static java.util.EnumSet.allOf;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertEquals;

public class DiscoveryPageEspressoTest {

    private Solo solo;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() {
        solo = new Solo(getInstrumentation(), mActivityRule.getActivity());
        Timeout.setSmallTimeout(5000);
        Timeout.setLargeTimeout(10000);
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


    @Test
    public void testInterstitialAd()  {

        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        String currentPage = getActionbarTitle();
        if (currentPage.equals("Queue") || currentPage.equals("Downloads")){
            solo.clickOnText(solo.getString(R.string.discovery_page_label));
            solo.waitForView(android.R.id.list);
            assertEquals(solo.getString(R.string.discovery_page_label), getActionbarTitle());
        }else{
            solo.clickOnText(solo.getString(R.string.discovery_page_label));
            solo.waitForView(R.id.constraintLayout);
            assertEquals(solo.getString(R.string.discovery_page_label), getActionbarTitle());
        }

        onView(withId(R.id.categories_button)).perform(click());
        solo.waitForView(R.id.categories_button);
        Espresso.pressBack();
        assertEquals(solo.getString(R.string.discovery_page_label), getActionbarTitle());

    }


}




