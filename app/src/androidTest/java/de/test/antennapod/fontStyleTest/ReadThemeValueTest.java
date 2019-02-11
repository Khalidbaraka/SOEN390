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

import de.danoeh.antennapod.core.R;

@RunWith(AndroidJUnit4.class)
public class ReadThemeValueTest {

    @Rule
    public ActivityTestRule<PreferenceActivity> mActivityRule = new ActivityTestRule<>(PreferenceActivity.class);

    @Before
    public void setUp(){
        UserPreferences.init(mActivityRule.getActivity());
    }

    @Test
    public void testReadThemeValue() {

        String font = UserPreferences.getFont();
        int themeValue;

        if(font == "Default") {
            themeValue = UserPreferences.readThemeValue("0");
            assertEquals(themeValue, R.style.Theme_AntennaPod_Light);
            themeValue = UserPreferences.readThemeValue("1");
            assertEquals(themeValue, R.style.Theme_AntennaPod_Dark);
            themeValue = UserPreferences.readThemeValue("2");
            assertEquals(themeValue, R.style.Theme_AntennaPod_TrueBlack);
            themeValue = UserPreferences.readThemeValue("3");
            assertEquals(themeValue, R.style.Theme_AntennaPod_Blue);
            themeValue = UserPreferences.readThemeValue("4");
            assertEquals(themeValue, R.style.Theme_AntennaPod_Pink);
            themeValue = UserPreferences.readThemeValue(Integer.toString((int)(Math.random()*100)));
            assertEquals(themeValue, R.style.Theme_AntennaPod_Light);
        } else if(font == "Lobster") {
            themeValue = UserPreferences.readThemeValue("0");
            assertEquals(themeValue, R.style.LightWithLobster);
            themeValue = UserPreferences.readThemeValue("1");
            assertEquals(themeValue, R.style.DarkWithLobster);
            themeValue = UserPreferences.readThemeValue("2");
            assertEquals(themeValue, R.style.BlackWithLobster);
            themeValue = UserPreferences.readThemeValue("3");
            assertEquals(themeValue, R.style.BlueWithLobster);
            themeValue = UserPreferences.readThemeValue("4");
            assertEquals(themeValue, R.style.PinkWithLobster);
            themeValue = UserPreferences.readThemeValue(Integer.toString((int)(Math.random()*100)));
            assertEquals(themeValue, R.style.Theme_AntennaPod_Light);
        } else if(font == "Ubuntu") {
            themeValue = UserPreferences.readThemeValue("0");
            assertEquals(themeValue, R.style.LightWithUbuntu);
            themeValue = UserPreferences.readThemeValue("1");
            assertEquals(themeValue, R.style.DarkWithUbuntu);
            themeValue = UserPreferences.readThemeValue("2");
            assertEquals(themeValue, R.style.BlackWithUbuntu);
            themeValue = UserPreferences.readThemeValue("3");
            assertEquals(themeValue, R.style.BlueWithUbuntu);
            themeValue = UserPreferences.readThemeValue("4");
            assertEquals(themeValue, R.style.PinkWithUbuntu);
            themeValue = UserPreferences.readThemeValue(Integer.toString((int)(Math.random()*100)));
            assertEquals(themeValue, R.style.Theme_AntennaPod_Light);
        } else {
            themeValue = UserPreferences.readThemeValue(Integer.toString((int)(Math.random()*100)));
            assertEquals(themeValue, R.style.Theme_AntennaPod_Light);
        }
    }
}
