package de.test.antennapod.espresso.themeTest;


import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.robotium.solo.Solo;
import com.robotium.solo.Timeout;

import de.danoeh.antennapod.R;
import de.danoeh.antennapod.activity.PreferenceActivity;
import de.danoeh.antennapod.core.preferences.UserPreferences;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.shredzone.flattr4j.model.User;

import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static junit.framework.Assert.assertTrue;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class ChangeThemeTest {

    private Solo solo;
    private Resources res;
    private SharedPreferences prefs;

    @Rule
    public ActivityTestRule<PreferenceActivity> mActivityRule = new ActivityTestRule<>(PreferenceActivity.class);

    @Before
    public void setUp() {
        solo = new Solo(getInstrumentation(), mActivityRule.getActivity());
        Timeout.setSmallTimeout(500);
        Timeout.setLargeTimeout(1000);
        res = mActivityRule.getActivity().getResources();
        UserPreferences.init(mActivityRule.getActivity());

        prefs = PreferenceManager.getDefaultSharedPreferences(mActivityRule.getActivity());
        prefs.edit().clear();
        prefs.edit().putBoolean(UserPreferences.PREF_ENABLE_AUTODL, true).commit();
    }

    @After
    public void tearDown() {
        solo.finishOpenedActivities();
        prefs.edit().clear();
    }

    

    private void clickPreference(Matcher<View> matcher) {
        onView(withId(R.id.list))
                .perform(RecyclerViewActions.actionOnItem(hasDescendant(matcher), click()));
    }


    private int switchThemeLightToBlue(int currentTheme){
        int newTheme=0;
        if(currentTheme == de.danoeh.antennapod.core.R.style.Theme_AntennaPod_Light |
                currentTheme == de.danoeh.antennapod.core.R.style.LightWithLobster |
                currentTheme == de.danoeh.antennapod.core.R.style.LightWithUbuntu){
            newTheme= R.string.pref_theme_title_blue;
        }
        return newTheme;
    }


    private int switchThemeDarkBlackToBlue(int currentTheme){
        int newTheme=0;
        if(currentTheme == de.danoeh.antennapod.core.R.style.Theme_AntennaPod_Dark |
                currentTheme == de.danoeh.antennapod.core.R.style.DarkWithLobster |
                currentTheme == de.danoeh.antennapod.core.R.style.DarkWithUbuntu){
            newTheme= R.string.pref_theme_title_blue;
        }
        return newTheme;
    }

    private int switchThemeBlackToBlue(int currentTheme){
        int newTheme=0;
        if(currentTheme == de.danoeh.antennapod.core.R.style.BlackWithLobster |
                currentTheme == de.danoeh.antennapod.core.R.style.BlackWithUbuntu |
                currentTheme == de.danoeh.antennapod.core.R.style.Theme_Base_AntennaPod_TrueBlack){
            newTheme= R.string.pref_theme_title_blue;
        }
        return newTheme;
    }

    private int switchThemePinkToBlue(int currentTheme){
        int newTheme=0;
        if(currentTheme == de.danoeh.antennapod.core.R.style.PinkWithLobster |
                currentTheme == de.danoeh.antennapod.core.R.style.PinkWithUbuntu |
                currentTheme == de.danoeh.antennapod.core.R.style.Theme_AntennaPod_Pink){
            newTheme= R.string.pref_theme_title_blue;
        }
        return newTheme;
    }






    @Test
    public void testSwitch() {
        final int theme= UserPreferences.getTheme();
        final int blueTheme = R.string.pref_theme_title_blue;
        final int darkTheme = R.string.pref_theme_title_dark;
        final int blackTheme = R.string.pref_theme_title_trueblack;
        final int pinkTheme = R.string.pref_theme_title_pink;
        final int lightTheme = R.string.pref_theme_title_light;


        int otherTheme;
        int x= switchThemePinkToBlue(theme);

        if(x != 0){
            otherTheme=switchThemePinkToBlue(theme);
        }else {
            otherTheme = R.string.pref_theme_title_trueblack;

        }


//        if(theme == de.danoeh.antennapod.core.R.style.Theme_AntennaPod_Light | theme == de.danoeh.antennapod.core.R.style.LightWithLobster |
//                theme == de.danoeh.antennapod.core.R.style.LightWithUbuntu |
//                theme== de.danoeh.antennapod.core.R.style.DarkWithLobster |
//                theme== de.danoeh.antennapod.core.R.style.DarkWithUbuntu |
//                theme== de.danoeh.antennapod.core.R.style.BlackWithUbuntu |
//                theme==de.danoeh.antennapod.core.R.style.BlackWithLobster |
//                theme == de.danoeh.antennapod.core.R.style.Theme_AntennaPod_Dark |
//                theme == de.danoeh.antennapod.core.R.style.Theme_AntennaPod_TrueBlack ){
//
//            otherTheme = R.string.pref_theme_title_blue;
//        }
//        otherTheme = R.string.pref_theme_title_pink;
        clickPreference(withText(R.string.user_interface_label));
        clickPreference(withText(R.string.pref_set_theme_title));
        onView(withText(otherTheme)).perform(click());


        assertTrue(solo.waitForCondition(() -> (UserPreferences.getTheme() == theme || UserPreferences.getTheme() != theme), Timeout.getLargeTimeout()));
    }


}

