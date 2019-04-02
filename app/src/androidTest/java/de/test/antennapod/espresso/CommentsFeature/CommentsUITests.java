package de.test.antennapod.espresso.CommentsFeature;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.robotium.solo.Solo;
import com.robotium.solo.Timeout;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import de.danoeh.antennapod.R;
import de.danoeh.antennapod.activity.MainActivity;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.anything;
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

    @Test
    public void test1() {
        // Add Podcast page
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
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
    public void test2() {
        // in podcast we want to click on Itunes btn
        onView(withId(R.id.butSearchItunes)).perform(click());
        solo.waitForView(R.id.layout_1);
    }

    @Test
    public void test3() {
        onView(withId(R.id.butSearchItunes)).perform(click());
        solo.waitForView(R.id.layout_1);
        onData(anything()).inAdapterView(withId(R.id.gridView)).atPosition(0).
                onChildView(withId(R.id.imgvCover)).perform(click());
        //clicks on podcast
        solo.waitForView(R.id.relativeLayout);
        onView(withId(R.id.viewCommentsBtn)).perform(click());
        solo.waitForView(R.id.constraintLayout);

    }

    private String getActionbarTitle() {
        return ((MainActivity) solo.getCurrentActivity()).getSupportActionBar().getTitle().toString();
    }
}
