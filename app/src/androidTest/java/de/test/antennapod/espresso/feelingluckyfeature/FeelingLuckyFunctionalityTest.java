package de.test.antennapod.espresso.feelingluckyfeature;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import de.danoeh.antennapod.R;
import de.danoeh.antennapod.activity.MainActivity;
import de.danoeh.antennapod.fragment.FeelingLuckyFragment;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

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
public class FeelingLuckyFunctionalityTest {
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
    public void test1GoFirstToDiscoveryPage() {
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
    public void test2ButtonsNamesAndFindInItunes() {

        //Checks "I'm Feeling Lucky" button is there
        onView(withId(R.id.luckyBtn)).check(matches(notNullValue() ));

        //Checks button name matches
        onView(withId(R.id.luckyBtn)).check(matches(withText("I'm Feeling Lucky")));
        assertEquals("I'm Feeling Lucky", solo.getString(R.string.feeling_lucky_Im_feeling_lucky));

        //Press the I'm feeling Lucky button in the Discovery Page
        onView(withId(R.id.luckyBtn)).perform(click());

        solo.waitForView(android.R.id.list);

        //Checks page text name matches
        onView(withId(R.id.lucky_title)).check(matches(withText("I'm Feeling Lucky")));
        assertEquals("I'm Feeling Lucky", solo.getString(R.string.feeling_lucky_Im_feeling_lucky));

        //Checks "Re-Roll" button is there
        onView(withId(R.id.reroll_button)).check(matches(notNullValue() ));

        //Checks button name matches
        onView(withId(R.id.reroll_button)).check(matches(withText("Re-roll")));
        assertEquals("Re-roll", solo.getString(R.string.feeling_lucky_reroll));

        //Press the Re-roll button
        onView(withId(R.id.reroll_button)).perform(click());
        solo.waitForView(android.R.id.list);

        solo.waitForView(android.R.id.list);

        //Checks "Find in iTunes" button is there
        onView(withId(R.id.add_button)).check(matches(notNullValue() ));

        //Checks button name matches
        onView(withId(R.id.add_button)).check(matches(withText("Find in iTunes")));
        assertEquals("Find in iTunes", solo.getString(R.string.feeling_lucky_find_itunes));

        //Press the Find in iTunes button in the Discovery Page
        onView(withId(R.id.add_button)).perform(click());

        solo.waitForView(android.R.id.list);
        Espresso.pressBack();
        solo.waitForView(android.R.id.list);

    }



    @Test
    public void test3GettingARandomPodcast() {

        String titleBefore;
        String titleAfter;
        String publisherBefore;
        String publisherAfter;
        String descriptionBefore;
        String descriptionAfter;

        titleBefore = FeelingLuckyFragment.podcast_title;
        publisherBefore = FeelingLuckyFragment.podcast_publisher;
        descriptionBefore = FeelingLuckyFragment.podcast_description;

        onView(withId(R.id.luckyBtn)).perform(click());
        solo.waitForView(android.R.id.list);

        onView(withId(R.id.reroll_button)).perform(click());
        solo.waitForView(android.R.id.list);

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        titleAfter = FeelingLuckyFragment.podcast_title;
        publisherAfter = FeelingLuckyFragment.podcast_publisher;
        descriptionAfter = FeelingLuckyFragment.podcast_description;

        //assert that a new random podcast was loaded
        assertNotEquals(titleBefore, titleAfter);
        assertNotEquals(publisherBefore, publisherAfter);
        assertNotEquals(descriptionBefore, descriptionAfter);
    }

    private String getActionbarTitle() {
        return ((MainActivity) solo.getCurrentActivity()).getSupportActionBar().getTitle().toString();
    }
}
