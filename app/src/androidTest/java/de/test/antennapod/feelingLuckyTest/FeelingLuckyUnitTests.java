package de.test.antennapod.feelingLuckyTest;
import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.json.JSONException;
import org.json.JSONObject;

import de.danoeh.antennapod.activity.MainActivity;
import de.danoeh.antennapod.fragment.FeelingLuckyFragment;
import de.danoeh.antennapod.model.RandomPodcast;

@RunWith(MockitoJUnitRunner.class)
public class FeelingLuckyUnitTests {
    private JSONObject jsn;
    @Before
    public void setup(){
		jsn = mock(JSONObject.class);
	}

	@Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);


    @Test
    public void getPodcastDetailsTest(){

        FeelingLuckyFragment f= new FeelingLuckyFragment();
        try{
            RandomPodcast r1 = f.getPodcastDetails("image:yolo,podcast_title:yoyo, description:test,publisher:keke ");
            assertNotNull(r1);
        }
        catch (Exception e) {
            System.out.print("error");
        }
    }
}
