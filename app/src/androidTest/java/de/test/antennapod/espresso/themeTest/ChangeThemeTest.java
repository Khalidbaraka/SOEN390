package de.test.antennapod.espresso.themeTest;


import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
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

    // allow to change between themes
    private void switchThemes(int newTheme){
        clickPreference(withText(R.string.user_interface_label));
        clickPreference(withText(R.string.pref_set_theme_title));
        onView(withText(newTheme)).perform(click());
    }

    //switch any theme to light theme
    @Test
    public void testSwitch() {

        final int theme= UserPreferences.getTheme();
        int otherTheme=0;
        if(theme >0){

            otherTheme=R.string.pref_theme_title_light;

        }
        switchThemes(otherTheme);
        assertTrue(solo.waitForCondition(() -> (UserPreferences.getTheme() == theme || UserPreferences.getTheme() != theme), Timeout.getLargeTimeout()));
    }
    // switch between light to black
    @Test
    public void switchThemeLightToBlackTest(){
        final int theme= UserPreferences.getTheme();
        int otherTheme=R.string.pref_theme_title_pink;

        if(theme == de.danoeh.antennapod.core.R.style.Theme_AntennaPod_Light | theme == de.danoeh.antennapod.core.R.style.LightWithLobster |
                theme == de.danoeh.antennapod.core.R.style.LightWithUbuntu) {

            otherTheme= R.string.pref_theme_title_trueblack;
        }
        switchThemes(otherTheme);
        assertTrue(solo.waitForCondition(() -> ( UserPreferences.getTheme() != theme), Timeout.getLargeTimeout()));
    }

    // switch between black to pink
    @Test
    public void switchThemeBlackToPinkTest(){
        final int theme= UserPreferences.getTheme();
        int otherTheme=R.string.pref_theme_title_blue;

        if(theme == de.danoeh.antennapod.core.R.style.BlackWithLobster | theme == de.danoeh.antennapod.core.R.style.BlackWithUbuntu |
                theme == de.danoeh.antennapod.core.R.style.Theme_Base_AntennaPod_TrueBlack){

            otherTheme= R.string.pref_theme_title_pink;
        }
        switchThemes(otherTheme);
        assertTrue(solo.waitForCondition(() -> ( UserPreferences.getTheme() != theme), Timeout.getLargeTimeout()));
    }

    // switch between pink to blue
    @Test
    public void switchThemPinktoBlueTest(){
        final int theme= UserPreferences.getTheme();
        int otherTheme=R.string.pref_theme_title_light;

        if(theme == de.danoeh.antennapod.core.R.style.PinkWithLobster | theme == de.danoeh.antennapod.core.R.style.PinkWithUbuntu |
                theme == de.danoeh.antennapod.core.R.style.Theme_AntennaPod_Pink){

            otherTheme= R.string.pref_theme_title_blue;
        }
        switchThemes(otherTheme);
        assertTrue(solo.waitForCondition(() -> ( UserPreferences.getTheme() != theme), Timeout.getLargeTimeout()));
    }


    // switch between blue to dark
    @Test
    public void switchThemBluetoDarkTest(){
        final int theme= UserPreferences.getTheme();
        int otherTheme=R.string.pref_theme_title_light;

        if(theme == de.danoeh.antennapod.core.R.style.BlueWithLobster | theme == de.danoeh.antennapod.core.R.style.BlueWithUbuntu |
                theme == de.danoeh.antennapod.core.R.style.Theme_AntennaPod_Blue){

            otherTheme= R.string.pref_theme_title_dark;
        }
        switchThemes(otherTheme);
        assertTrue(solo.waitForCondition(() -> ( UserPreferences.getTheme() != theme), Timeout.getLargeTimeout()));
    }

}

