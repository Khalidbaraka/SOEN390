package de.test.antennapod.unitTest;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;

import java.util.HashMap;
import java.util.Map;

import de.danoeh.antennapod.activity.AddCommentActivity;
import de.danoeh.antennapod.activity.CommentListActivity;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

    //Unit Test for testing the posting method for comment and reply
    // verifying the size of the hashmap plus the add comment object into db
    @Test
    public void startPostingMethodTest() {
        Map<String, String> dataToSave= new HashMap<>();
        mActivity= new AddCommentActivity();
        DatabaseReference mPostDatabase= mock(DatabaseReference.class);
        DatabaseReference newComment = mock(DatabaseReference.class, Mockito.RETURNS_DEEP_STUBS);
        String content= "Testing";
        String id= "123";
        String email= "testing@hotmail.com";
        String time= "April 15";
        String podcast = "lord of The Ring";
        mActivity.startPosting(content,dataToSave,newComment,id,email,time,podcast);
        verify(newComment).setValue(dataToSave);
        assertEquals(5, dataToSave.size());
    }


}
