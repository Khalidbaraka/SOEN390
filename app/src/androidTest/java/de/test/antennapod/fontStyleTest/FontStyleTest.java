package de.test.antennapod.fontStyleTest;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.danoeh.antennapod.activity.PreferenceActivity;
import de.danoeh.antennapod.core.preferences.UserPreferences;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

@RunWith(AndroidJUnit4.class)
public class FontStyleTest {

    @Rule
    public ActivityTestRule<PreferenceActivity> mActivityRule = new ActivityTestRule<>(PreferenceActivity.class);

    @Before
    public void setUp(){
        UserPreferences.init(mActivityRule.getActivity());
    }

    @Test
    public void testReadFontValue() {
        String fontValue = UserPreferences.readFontValue("0");
        assertEquals(fontValue, "Default");
        fontValue = UserPreferences.readFontValue("1");
        assertEquals(fontValue, "Lobster");
        fontValue = UserPreferences.readFontValue("2");
        assertEquals(fontValue, "Ubuntu");
        fontValue = UserPreferences.readFontValue("3000");
        assertEquals(fontValue, "Default");
    }
}
