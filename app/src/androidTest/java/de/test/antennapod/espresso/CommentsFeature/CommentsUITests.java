package de.test.antennapod.espresso.CommentsFeature;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.contrib.NavigationViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.FlakyTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import android.view.View;

import com.robotium.solo.Solo;
import com.robotium.solo.Timeout;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import de.danoeh.antennapod.R;
import de.danoeh.antennapod.activity.MainActivity;
import de.danoeh.antennapod.activity.PreferenceActivity;
import de.danoeh.antennapod.core.preferences.UserPreferences;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class CommentsUITests {
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

    //    <string name="add_feed_label">Add Podcast</string>
    @Test
    public void test1GoFirstt() {
        // podcast page
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
//        onView(withId(R.id.nav_layout))
//                .perform(NavigationViewActions.navigateTo());
        String currentPage = getActionbarTitle();
        if (("Add Podcast").equals(currentPage)){

            assertEquals(solo.getString(R.string.add_feed_label), getActionbarTitle());


        }else{

            solo.clickOnText(solo.getString(R.string.add_feed_label));
            solo.waitForView(R.id.list);
            assertEquals(solo.getString(R.string.add_feed_label), getActionbarTitle());


        }

    }

    @Test
    public void test2GoSecond() {
        // in podcast we want to click on Itunes btn
        onView(withId(R.id.butSearchItunes)).perform(click());
        solo.waitForView(R.id.layout_1);
    }

    @Test
    public void test3GoThird() {
        onView(withId(R.id.butSearchItunes)).perform(click());
        solo.waitForView(R.id.layout_1);
        onData(anything()).inAdapterView(withId(R.id.gridView)).atPosition(0).
                onChildView(withId(R.id.imgvCover)).perform(click());
        //clicks on a podcast
        solo.waitForView(R.id.relativeLayout);
        onView(withId(R.id.viewCommentsBtn)).perform(click());
        solo.waitForView(R.id.constraintLayout);
        solo.waitForView(R.id.constraintLayout);


    }













    private String getActionbarTitle() {
        return ((MainActivity) solo.getCurrentActivity()).getSupportActionBar().getTitle().toString();
    }


}
