package de.test.antennapod.fontStyleTest;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Map;

import de.danoeh.antennapod.R;
import de.danoeh.antennapod.activity.PreferenceActivity;
import de.danoeh.antennapod.core.preferences.UserPreferences;

import static de.danoeh.antennapod.core.preferences.UserPreferences.PREF_THEME;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

@RunWith(AndroidJUnit4.class)
public class GetNoTitleThemeTest {
    @Rule
    public ActivityTestRule<PreferenceActivity> mActivityRule = new ActivityTestRule<>(PreferenceActivity.class);

    @Before
    public void setUp(){
        UserPreferences.init(mActivityRule.getActivity());
    }

    @Test
    public void testGetNoTitleTheme() {

        int themeReturned = 0;
        int themeExpected = 1;

        themeReturned = UserPreferences.getNoTitleTheme();

        Map<Object, Integer> themeMatch = new HashMap<Object, Integer>();
        themeMatch.put(R.style.Theme_AntennaPod_Dark, R.style.Theme_AntennaPod_Dark_NoTitle);
        themeMatch.put(R.style.DarkWithLobster, R.style.DarkWithLobster);
        themeMatch.put(R.style.DarkWithUbuntu, R.style.DarkWithUbuntu);
        themeMatch.put(R.style.Theme_AntennaPod_TrueBlack, R.style.Theme_AntennaPod_TrueBlack_NoTitle);
        themeMatch.put(R.style.BlackWithLobster, R.style.BlackWithLobster);
        themeMatch.put(R.style.BlackWithUbuntu, R.style.BlackWithUbuntu);
        themeMatch.put(R.style.LightWithLobster, R.style.LightWithLobster);
        themeMatch.put(R.style.LightWithUbuntu, R.style.LightWithUbuntu);
        themeMatch.put(R.style.Theme_AntennaPod_Light, R.style.Theme_AntennaPod_Light_NoTitle);
        themeMatch.put(R.style.Theme_AntennaPod_Pink, R.style.Theme_AntennaPod_Pink);
        themeMatch.put(R.style.PinkWithLobster, R.style.PinkWithLobster);
        themeMatch.put(R.style.PinkWithUbuntu, R.style.PinkWithUbuntu);
        themeMatch.put(R.style.Theme_AntennaPod_Blue, R.style.Theme_AntennaPod_Blue);
        themeMatch.put(R.style.BlueWithLobster, R.style.BlueWithLobster);
        themeMatch.put(R.style.BlueWithUbuntu, R.style.BlueWithUbuntu);

        themeExpected = themeMatch.get(UserPreferences.getTheme());

        assertEquals(themeExpected, themeReturned);
    }
}
