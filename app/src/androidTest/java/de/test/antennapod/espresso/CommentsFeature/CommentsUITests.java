package de.test.antennapod.espresso.CommentsFeature;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.test.runner.lifecycle.Stage;
import android.view.View;

import com.robotium.solo.Solo;
import com.robotium.solo.Timeout;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.util.Collection;
import java.util.Iterator;

import androidx.test.espresso.core.internal.deps.guava.collect.Iterables;
import de.danoeh.antennapod.R;
import de.danoeh.antennapod.activity.CommentListActivity;
import de.danoeh.antennapod.activity.MainActivity;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static org.hamcrest.CoreMatchers.anything;
import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class CommentsUITests {
    private Solo solo;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);
    public ActivityTestRule<CommentListActivity> mActivityTestRule = new ActivityTestRule<>(CommentListActivity.class);


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
        assertEquals(solo.getString(R.string.add_feed_label), getActionbarTitle());


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
        assertEquals(CommentListActivity.class, getActivityInstance().getClass());
    }

    private Activity getActivityInstance(){
        final Activity[] currentActivity = {null};

        getInstrumentation().runOnMainSync(new Runnable(){
            public void run(){
                Collection<Activity> resumedActivity = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED);
                Iterator<Activity> it = resumedActivity.iterator();
                currentActivity[0] = it.next();
            }
        });

        return currentActivity[0];
    }

    private String getActionbarTitle() {
        return ((MainActivity) solo.getCurrentActivity()).getSupportActionBar().getTitle().toString();
    }
}
