package de.test.antennapod.espresso.feelingLuckyFeature;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

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

@RunWith(AndroidJUnit4.class)
public class FeelingLuckyFunctionalityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testGettingARandomPodcast() {

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
        onView(withId(R.id.reroll_button)).perform(click());
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
}
