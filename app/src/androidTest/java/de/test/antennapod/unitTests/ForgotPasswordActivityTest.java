package de.test.antennapod.unitTests;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import android.util.Log;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.danoeh.antennapod.R;
import de.danoeh.antennapod.activity.ForgotPasswordActivity;
import de.danoeh.antennapod.model.Printer;

import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class ForgotPasswordActivityTest {
    private static final String TAG = "RegisterActivityTest";

    @Rule
    public ActivityTestRule<ForgotPasswordActivity> mActivityRule = new ActivityTestRule<>(ForgotPasswordActivity.class);

    private String email;
    private String password;

    //Unit Testing with Toast dependency :  from https://stackoverflow.com/questions/52266797/nullpointerexception-from-toast-while-unit-testing
    @Test
    public void testInvalidEmail(){
        email = "";
        assertFalse(mActivityRule.getActivity().checkFieldsValidation(email, new Printer() {
            @Override
            public void print(int messageId) {
                Log.d(TAG, "testInvalidEmail: " + messageId);
                assertEquals(messageId,R.string.require_email);
            }
        }));
    }
    @Test
    public void testEmailShouldContainChar(){
        email = "test";
        assertFalse(mActivityRule.getActivity().checkFieldsValidation(email, new Printer() {
            @Override
            public void print(int messageId) {
                Log.d(TAG, "testEmailShouldContainChar: " + messageId);
                assertEquals(messageId,R.string.email_bad_format);
            }
        }));
    }
}
