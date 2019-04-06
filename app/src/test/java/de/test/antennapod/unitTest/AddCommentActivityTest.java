package de.test.antennapod.unitTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;

import de.danoeh.antennapod.activity.AddCommentActivity;
import de.danoeh.antennapod.activity.CommentListActivity;

import static junit.framework.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
public class AddCommentActivityTest {

    private ActivityController<AddCommentActivity> controller;
    private AddCommentActivity mActivity;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Before
    public void setUp() {
        controller = Robolectric.buildActivity(AddCommentActivity.class);
    }

    @Test
    public void checkActivityControllerLaunch() {
        assertNotNull(controller);
    }

    @Test
    public void checkActivityLaunch() {
        mActivity = controller.get();
        assertNotNull(mActivity);
    }

}
