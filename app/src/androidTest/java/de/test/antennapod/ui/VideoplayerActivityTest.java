package de.test.antennapod.ui;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import org.junit.Ignore;

import de.danoeh.antennapod.activity.VideoplayerActivity;

/**
 * Test class for VideoplayerActivity
 */
@Ignore
public class VideoplayerActivityTest extends ActivityInstrumentationTestCase2<VideoplayerActivity> {

    private Solo solo;

    public VideoplayerActivityTest() {
        super(VideoplayerActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
        super.setUp();
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
    }

    /**
     * Test if activity can be started.
     */
    public void testStartActivity() throws Exception {
        solo.waitForActivity(VideoplayerActivity.class);
    }
}
