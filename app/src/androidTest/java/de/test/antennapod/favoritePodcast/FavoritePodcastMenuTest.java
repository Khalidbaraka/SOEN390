package de.test.antennapod.favoritePodcast;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.junit.Assert.*;

import de.danoeh.antennapod.activity.MainActivity;
import de.danoeh.antennapod.adapter.NavListAdapter;
import de.danoeh.antennapod.core.feed.Feed;
import de.danoeh.antennapod.core.storage.DBReader;
import de.danoeh.antennapod.core.storage.DBWriter;
import android.view.MenuItem;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

@RunWith(AndroidJUnit4.class)
public class FavoritePodcastMenuTest {
    @Mock
    private DBWriter dbWriter;
    @Mock
    private MenuItem menuItem;
    @Mock
    private NavListAdapter navAdapter;
    private int position = 8;
    @Mock
    private Feed feed;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testOnContextItemSelected(){
        mActivityRule.getActivity().setmPosition(position);
        when(menuItem.getItemId()).thenReturn(2131427361);
        when(navAdapter.getSubscriptionOffset()).thenReturn(3);
        boolean result = mActivityRule.getActivity().onContextItemSelected(menuItem);
        verify(dbWriter).addFavoritePodcastItem(feed);
        assertTrue(result);

    }

}
