package de.test.antennapod.unitTest;

import android.os.Build;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;

import de.danoeh.antennapod.activity.CommentListActivity;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class CommentListActivityTest {

    private ActivityController<CommentListActivity> controller;

    @Before
    public void setUp() {
        controller = Robolectric.buildActivity(CommentListActivity.class);
    }

    @Test
    public void checkActivityLaunch() {
        assertNotNull(controller);
    }

}
