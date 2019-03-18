package de.test.antennapod.favoritePodcast;

import android.content.Context;
import android.support.test.runner.AndroidJUnit4;
import android.test.InstrumentationTestCase;

import java.io.File;

import de.danoeh.antennapod.core.storage.PodDBAdapter;

import android.content.Context;
import android.database.Cursor;
import android.test.InstrumentationTestCase;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import de.danoeh.antennapod.core.feed.Feed;
import de.danoeh.antennapod.core.feed.FeedItem;
import de.danoeh.antennapod.core.feed.FeedMedia;
import de.danoeh.antennapod.core.storage.DBReader;
import de.danoeh.antennapod.core.storage.DBWriter;
import de.danoeh.antennapod.core.storage.PodDBAdapter;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Test class for DBWriter
 */
@RunWith(AndroidJUnit4.class)
public class FavoritePodcastDBWriterTest extends InstrumentationTestCase{
    private static final String TAG = "DBWriterTest";
    private static final String TEST_FOLDER = "testDBWriter";
    private static final long TIMEOUT = 5L;

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();

        assertTrue(PodDBAdapter.deleteDatabase());

        final Context context = getInstrumentation().getTargetContext();
        File testDir = context.getExternalFilesDir(TEST_FOLDER);
        assertNotNull(testDir);
        for (File f : testDir.listFiles()) {
            f.delete();
        }
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        // create new database
        PodDBAdapter.init(getInstrumentation().getTargetContext());
        PodDBAdapter.deleteDatabase();
        PodDBAdapter adapter = PodDBAdapter.getInstance();
        adapter.open();
        adapter.close();
    }
    @Test
    public void addFavoritePodcastItemTest(){
        final int NUM_ITEMS = 10;
        Feed feed = new Feed("url", null, "title");
        feed.setItems(new ArrayList<>());
        for (int i = 0; i < NUM_ITEMS; i++) {
            FeedItem item = new FeedItem(0, "title " + i, "id " + i, "link " + i, new Date(), FeedItem.UNPLAYED, feed);
            feed.getItems().add(item);
        }

        PodDBAdapter adapter = PodDBAdapter.getInstance();
        adapter.open();
        adapter.setCompleteFeed(feed);
        adapter.close();
        assertTrue(feed.getId() != 0);
        for (FeedItem item : feed.getItems()) {
            assertTrue(item.getId() > 1);
        }

        DBWriter.addFavoritePodcastItem(feed);
        assertFalse(DBReader.getFavoritePodcastItemsList().size() == 0);

    }

}


