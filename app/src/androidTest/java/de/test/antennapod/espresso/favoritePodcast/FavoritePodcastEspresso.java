package de.test.antennapod.espresso.favoritePodcast;

import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
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

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class FavoritePodcastEspresso {
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
    public void test2AddToFavoritePodcasts() {
        //LongClick on first podcast in subscription
        onData(anything()).inAdapterView(withId(R.id.subscriptions_grid)).atPosition(0).
                onChildView(withId(R.id.imgvCover)).perform(longClick());

        //Assert the "Add to favorites" menuItem is actually there
        assertEquals("Add to favorites Podcasts", solo.getString(R.string.add_to_favorites_podcasts));

        //Click on add to favorite
        onView(anyOf(withText(R.string.add_to_favorites_podcasts), withId(R.id.add_to_favorites_podcasts))).perform(click());
        solo.waitForView(android.R.id.list);

        //Switch tab to favorite podcast to see Podcast has been added
        Matcher<View> matcher1 = allOf(withText("Favorite Podcasts"), isDescendantOfA(withId(R.id.subscriptionfavoritepodcasts_tablayout_id)));
        onView(matcher1).perform(click());
        solo.waitForView(android.R.id.list);
    }

    @Test
    public void test3RemoveFromFavoritePodcasts() {
        //Switch tab to Favorite Podcasts
        Matcher<View> matcher2 = allOf(withText("Favorite Podcasts"), isDescendantOfA(withId(R.id.subscriptionfavoritepodcasts_tablayout_id)));
        onView(matcher2).perform(click());
        solo.waitForView(android.R.id.list);

        //LongClick on first podcast in Favorite Podcasts
        onData(anything()).inAdapterView(withId(R.id.favorite_podcasts_grid)).atPosition(0).
                onChildView(withId(R.id.imgvCover)).perform(longClick());

        //Assert the the "Remove From Favorite Podcast" menuItem is actually there
        assertEquals("Remove From Favorite Podcasts",solo.getString(R.string.remove_from_favorite_podcast));
        solo.waitForView(android.R.id.list);

        //Click on remove from favorite podcast
        onView(anyOf(withText(R.string.remove_from_favorite_podcast), withId(R.id.remove_from_favorite_podcasts))).perform(click());
        solo.waitForView(android.R.id.list);
        solo.waitForView(android.R.id.list);

        //Switch back tab to Favorite Podcasts to see item has been deleted
        onView(matcher2).perform(click());
        solo.waitForView(android.R.id.list);
    }

    private String getActionbarTitle() {
        return ((MainActivity) solo.getCurrentActivity()).getSupportActionBar().getTitle().toString();
    }

}
