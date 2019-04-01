package de.test.antennapod.unitTests.findSimilarUnitTests;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import de.danoeh.antennapod.activity.MainActivity;
import de.danoeh.antennapod.fragment.FeelingLuckyFragment;
import de.danoeh.antennapod.fragment.FindSimilarFragment;
import de.danoeh.antennapod.model.Podcast;

@RunWith(AndroidJUnit4.class)
public class FindSmilarUnitTests {



    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void getPodcastTest(){
        Podcast randomPodcast = new Podcast();
        randomPodcast.setPodcastDescription("test");
        randomPodcast.setPodcastImage("yolo");
        randomPodcast.setPodcastPublisher("keke");
        randomPodcast.setPodcastTitle("yoyo");

        FeelingLuckyFragment f= new FeelingLuckyFragment();
        try{

            Podcast r1 = f.getPodcastDetails("image:yolo,podcast_title:yoyo, description:test,publisher:keke ");
            assertNotNull(r1);
            assertEquals(randomPodcast.getPodcastImage(),r1.getPodcastImage());
            assertEquals(randomPodcast.getPodcastDescription(),r1.getPodcastDescription());
            assertEquals(randomPodcast.getPodcastPublisher(),r1.getPodcastPublisher());
            assertEquals(randomPodcast.getPodcastTitle(),r1.getPodcastTitle());

        }
        catch (Exception e){
            e.getMessage();

        }
    }












}
