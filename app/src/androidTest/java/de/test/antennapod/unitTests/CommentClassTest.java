package de.test.antennapod.unitTests;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import de.danoeh.antennapod.model.Comment;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class CommentClassTest {

    @Test
    public void testParameterizedConstructor() {

        String uid = "testUserId";
        String email = "testUserEmail";
        String timestamp = "testTimestamp";
        String comment = "testComment";
        String podcast = "testPodcast";

        Comment testComment = new Comment(uid, email, timestamp, comment, podcast);
        assertEquals(testComment.getUser(), uid);
        assertEquals(testComment.getUseremail(), email);
        assertEquals(testComment.getTimestamp(), timestamp);
        assertEquals(testComment.getComment(), comment);
        assertEquals(testComment.getPodcast(), podcast);

    }
}

