package de.test.antennapod.unitTest;

import android.content.Intent;
import android.os.Build;
import android.view.MenuItem;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.android.controller.ActivityController;

import de.danoeh.antennapod.R;
import de.danoeh.antennapod.activity.CommentListActivity;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.mockito.junit.*;

@RunWith(RobolectricTestRunner.class)
public class CommentListActivityTest {

    private ActivityController<CommentListActivity> controller;
    private CommentListActivity cListActivity;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Before
    public void setUp() {
        controller = Robolectric.buildActivity(CommentListActivity.class);
    }

    @Test
    public void checkActivityControllerLaunch() {
        assertNotNull(controller);
    }

    @Test
    public void checkActivityLaunch() {
        cListActivity = controller.get();
        assertNotNull(cListActivity);
    }

//    @Test
//    public void checkMenuItemSelection() {
//        MenuItem mockMenuItem = mock(MenuItem.class);
//        when(mockMenuItem.getItemId()).thenReturn(R.id.action_add);
//        cListActivity.onOptionsItemSelected(mockMenuItem);
//    }

//    @Test
//    public void testOnActivity() {
//        cListActivity = controller
//                .create()
//                .start()
//                .resume()
//                .visible()
//                .get();

//        /MenuItem mockMenuItem = mock()
//        cListActivity.onOptionsItemSelected()

}


