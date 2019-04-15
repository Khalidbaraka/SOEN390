package de.test.antennapod.espresso.find_similar;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import org.hamcrest.Matcher;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import de.danoeh.antennapod.R;
import de.danoeh.antennapod.activity.MainActivity;
import de.danoeh.antennapod.fragment.FindSimilarFragment;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.assertNotEquals;

import com.robotium.solo.Solo;
import com.robotium.solo.Timeout;

import org.junit.After;
import org.junit.Before;
import org.junit.runners.MethodSorters;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertEquals;


    @FixMethodOrder(MethodSorters.NAME_ASCENDING)
    @RunWith(AndroidJUnit4.class)
    public class findSimiliarUI {
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
        public void test1GoFirstToSubscriptionsPage() {
            // queue page
            onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
            String currentPage = getActionbarTitle();
            if (("Subscriptions").equals(currentPage)){
                assertEquals(solo.getString(R.string.subscriptions_label), getActionbarTitle());
            }else{
                solo.clickOnText(solo.getString(R.string.subscriptions_label));
                solo.waitForView(android.R.id.list);
                assertEquals(solo.getString(R.string.subscriptions_label), getActionbarTitle());
            }
        }

        @Test
        public void test2GoSimilarPodcastList() {
            onData(anything()).inAdapterView(withId(R.id.subscriptions_grid)).atPosition(0).
                    onChildView(withId(R.id.imgvCover)).perform(longClick());

            onView(anyOf(withText(R.string.find_similar), withId(R.id.find_similar))).perform(click());
            pressBack();
        }

        private String getActionbarTitle() {
            return ((MainActivity) solo.getCurrentActivity()).getSupportActionBar().getTitle().toString();
        }

    }



