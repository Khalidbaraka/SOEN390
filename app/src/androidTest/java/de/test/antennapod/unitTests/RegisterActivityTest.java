package de.test.antennapod.unitTests;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import android.util.Log;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

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
            public void print(String message) {
                Log.d(TAG, "testInvalidFullName: " + message);
                assertEquals(message,"Full Name is required!");
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
            public void print(String message) {
                Log.d(TAG, "testInvalidEmail: " + message);
                assertEquals(message,"Enter email address!");
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
            public void print(String message) {
                Log.d(TAG, "testInvalidPassword: " + message);
                assertEquals(message,"Enter password!");
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
            public void print(String message) {
                Log.d(TAG, "testShortPassword: " + message);
                assertEquals(message,"Password too short, enter minimum 6 characters!");
            }
        }));
    }
}
