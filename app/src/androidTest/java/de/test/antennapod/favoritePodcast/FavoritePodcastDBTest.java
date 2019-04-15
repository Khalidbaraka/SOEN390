package de.test.antennapod.favoritePodcast;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.InstrumentationTestCase;

import de.danoeh.antennapod.core.storage.PodDBAdapter;

import java.util.ArrayList;
import java.util.Date;
import de.danoeh.antennapod.core.feed.Feed;
import de.danoeh.antennapod.core.feed.FeedItem;
import de.danoeh.antennapod.core.storage.DBReader;
import de.danoeh.antennapod.core.storage.DBWriter;
import de.danoeh.antennapod.core.storage.Toggles;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


/**
 * Test class for DBWriter and DBReader
 */
@RunWith(AndroidJUnit4.class)
public class FavoritePodcastDBTest extends InstrumentationTestCase{

    private Feed feed;
    @After
    public void tearDown() throws Exception {
        super.tearDown();
        Toggles.TEST_DB = true;
        assertTrue(PodDBAdapter.deleteDatabase());
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();

        //In order to add a favorite podcast, we first need to have subcriptions , i.e have feeds in the database.
        //The following code creates a feed(Podcast) with 10 episodes ("feedItems")
        final int NUM_ITEMS = 10;
        feed = new Feed("url", null, "title");
        feed.setItems(new ArrayList<>());
        for (int i = 0; i < NUM_ITEMS; i++) {
            FeedItem item = new FeedItem(0, "title " + i, "id " + i, "link " + i, new Date(), FeedItem.UNPLAYED, feed);
            feed.getItems().add(item);
        }
        Toggles.TEST_DB = true;
        // create new database
        //Reminder this will create a new custom TestAntennapod.db and not use the apk's Antennapod.db.
        PodDBAdapter.init(InstrumentationRegistry.getInstrumentation().getTargetContext());
        assertTrue(PodDBAdapter.deleteDatabase());
        PodDBAdapter adapter = PodDBAdapter.getInstance();
        adapter.open();
        //Inserts new feed (Podcast) and feedItem(Episodes) to db.
        adapter.setCompleteFeed(feed);
        adapter.close();
        assertTrue(feed.getId() != 0);
        for (FeedItem item : feed.getItems()) {
            assertTrue(item.getId() != 0);
        }
    }
    @Test
    public void addFavoritePodcastThenRemoveTest() throws InterruptedException {
        //Make sure there are no favorite podcast in the db yet.
        assertTrue(DBReader.getFeedListFavorites().size() == 0);

        //Add feed to favorite podcast.
        DBWriter.addFavoritePodcastItem(feed);
        //Giving time (in ms) for DBWriter to finish addFavoritePodcastItem
        Thread.sleep(50);

        //Assert that the feed was added to the DB
        assertFalse(DBReader.getFeedListFavorites().size() == 0);

        //Assert that the data from favorite podcast table is not null.
        assertNotNull(DBReader.getFavoritePodcastsData());

        assertTrue(DBReader.getFeedListFavorites().get(0).equals(feed));

        //Remove from favorite podcast.
        DBWriter.removeFavoritePodcastItem(feed);
        //Giving time (in ms) for DBWriter to finish removeFavoritePodcastItem
        Thread.sleep(50);

        //Assert that the feed was removed from the DB
        assertTrue(DBReader.getFeedListFavorites().size() == 0);

    }
}