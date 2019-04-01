package de.test.antennapod.unitTests;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import android.util.Log;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.danoeh.antennapod.R;
import de.danoeh.antennapod.activity.RegisterActivity;
import de.danoeh.antennapod.model.Printer;

import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class RegisterActivityTest {
    private static final String TAG = "RegisterActivityTest";

    @Rule
    public ActivityTestRule<RegisterActivity> mActivityRule = new ActivityTestRule<>(RegisterActivity.class);

    private String fullName;
    private String email;
    private String password;

    //Unit Testing with Toast dependency :  from https://stackoverflow.com/questions/52266797/nullpointerexception-from-toast-while-unit-testing
    @Test
    public void testInvalidFullName(){
        fullName = "";
        email = "test@gmail.com";
        password="password";
        assertFalse(mActivityRule.getActivity().checkFieldsValidation(fullName,email,password, new Printer() {
            @Override
            public void print(int messageId) {
                Log.d(TAG, "testInvalidFullName: " + messageId);
                assertEquals(messageId, R.string.require_fulll_name);
            }
        }));
    }
    @Test
    public void testInvalidEmail(){
        fullName = "Full Name";
        email = "";
        password="password";
        assertFalse(mActivityRule.getActivity().checkFieldsValidation(fullName,email,password, new Printer() {
            @Override
            public void print(int messageId) {
                Log.d(TAG, "testInvalidEmail: " + messageId);
                assertEquals(messageId,R.string.require_email);
            }
        }));
    }
    @Test
    public void testInvalidPassword(){
        fullName = "Full Name";
        email = "test@gmail.com";
        password="";
        assertFalse(mActivityRule.getActivity().checkFieldsValidation(fullName,email,password, new Printer() {
            @Override
            public void print(int messageId) {
                Log.d(TAG, "testInvalidPassword: " + messageId);
                assertEquals(messageId,R.string.require_password);
            }
        }));
    }
    @Test
    public void testShortPassword(){
        fullName = "Full Name";
        email = "test@gmail.com";
        password="12345";
        assertFalse(mActivityRule.getActivity().checkFieldsValidation(fullName,email,password, new Printer() {
            @Override
            public void print(int messageId) {
                Log.d(TAG, "testShortPassword: " + messageId);
                assertEquals(messageId,R.string.warn_short_password);
            }
        }));
    }
}
