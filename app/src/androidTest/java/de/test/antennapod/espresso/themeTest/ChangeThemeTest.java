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

    // changing any theme to blue theme
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

    // changing any theme to light theme
    private int switchThemeBlueToLight(int currentTheme){
        int newTheme=0;
        if(currentTheme == de.danoeh.antennapod.core.R.style.Theme_AntennaPod_Blue |
                currentTheme == de.danoeh.antennapod.core.R.style.BlueWithUbuntu |
                currentTheme == de.danoeh.antennapod.core.R.style.BlueWithLobster){
            newTheme= R.string.pref_theme_title_light;
        }
        return newTheme;
    }


    private int switchThemeDarkBlackToLight(int currentTheme){
        int newTheme=0;
        if(currentTheme == de.danoeh.antennapod.core.R.style.Theme_AntennaPod_Dark |
                currentTheme == de.danoeh.antennapod.core.R.style.DarkWithLobster |
                currentTheme == de.danoeh.antennapod.core.R.style.DarkWithUbuntu){
            newTheme= R.string.pref_theme_title_light;
        }
        return newTheme;
    }

    private int switchThemeBlackToLight(int currentTheme){
        int newTheme=0;
        if(currentTheme == de.danoeh.antennapod.core.R.style.BlackWithLobster |
                currentTheme == de.danoeh.antennapod.core.R.style.BlackWithUbuntu |
                currentTheme == de.danoeh.antennapod.core.R.style.Theme_Base_AntennaPod_TrueBlack){
            newTheme= R.string.pref_theme_title_light;
        }
        return newTheme;
    }

    private int switchThemePinkToLight(int currentTheme){
        int newTheme=0;
        if(currentTheme == de.danoeh.antennapod.core.R.style.PinkWithLobster |
                currentTheme == de.danoeh.antennapod.core.R.style.PinkWithUbuntu |
                currentTheme == de.danoeh.antennapod.core.R.style.Theme_AntennaPod_Pink){
            newTheme= R.string.pref_theme_title_light;
        }
        return newTheme;
    }

    // changing all themes to true black theme

    private int switchThemeBlueToBlack(int currentTheme){
        int newTheme=0;
        if(currentTheme == de.danoeh.antennapod.core.R.style.Theme_AntennaPod_Blue |
                currentTheme == de.danoeh.antennapod.core.R.style.BlueWithUbuntu |
                currentTheme == de.danoeh.antennapod.core.R.style.BlueWithLobster){
            newTheme= R.string.pref_theme_title_trueblack;
        }
        return newTheme;
    }


    private int switchThemeDarkBlackToBlack(int currentTheme){
        int newTheme=0;
        if(currentTheme == de.danoeh.antennapod.core.R.style.Theme_AntennaPod_Dark |
                currentTheme == de.danoeh.antennapod.core.R.style.DarkWithLobster |
                currentTheme == de.danoeh.antennapod.core.R.style.DarkWithUbuntu){
            newTheme= R.string.pref_theme_title_trueblack;
        }
        return newTheme;
    }

    private int switchThemeLightToBlack(int currentTheme){
        int newTheme=0;
        if(currentTheme == de.danoeh.antennapod.core.R.style.LightWithLobster|
                currentTheme == de.danoeh.antennapod.core.R.style.LightWithUbuntu |
                currentTheme == de.danoeh.antennapod.core.R.style.Theme_Base_AntennaPod_Light){
            newTheme= R.string.pref_theme_title_trueblack;
        }
        return newTheme;
    }

    private int switchThemePinkToBlack(int currentTheme){
        int newTheme=0;
        if(currentTheme == de.danoeh.antennapod.core.R.style.PinkWithLobster |
                currentTheme == de.danoeh.antennapod.core.R.style.PinkWithUbuntu |
                currentTheme == de.danoeh.antennapod.core.R.style.Theme_AntennaPod_Pink){
            newTheme= R.string.pref_theme_title_trueblack;
        }
        return newTheme;
    }

    // changing all themes to dark black theme

    private int switchThemeBlueToDarkBlack(int currentTheme){
        int newTheme=0;
        if(currentTheme == de.danoeh.antennapod.core.R.style.Theme_AntennaPod_Blue |
                currentTheme == de.danoeh.antennapod.core.R.style.BlueWithUbuntu |
                currentTheme == de.danoeh.antennapod.core.R.style.BlueWithLobster){
            newTheme= R.string.pref_theme_title_dark;
        }
        return newTheme;
    }


    private int switchThemeBlackToDarkBlack(int currentTheme){
        int newTheme=0;
        if(currentTheme == de.danoeh.antennapod.core.R.style.Theme_AntennaPod_TrueBlack |
                currentTheme == de.danoeh.antennapod.core.R.style.BlackWithUbuntu |
                currentTheme == de.danoeh.antennapod.core.R.style.BlackWithUbuntu){
            newTheme= R.string.pref_theme_title_dark;
        }
        return newTheme;
    }

    private int switchThemeLightToDarkBlack(int currentTheme){
        int newTheme=0;
        if(currentTheme == de.danoeh.antennapod.core.R.style.LightWithLobster |
                currentTheme == de.danoeh.antennapod.core.R.style.LightWithUbuntu |
                currentTheme == de.danoeh.antennapod.core.R.style.Theme_Base_AntennaPod_Light){
            newTheme= R.string.pref_theme_title_dark;
        }
        return newTheme;
    }

    private int switchThemePinkToDarkBlack(int currentTheme){
        int newTheme=0;
        if(currentTheme == de.danoeh.antennapod.core.R.style.PinkWithLobster |
                currentTheme == de.danoeh.antennapod.core.R.style.PinkWithUbuntu |
                currentTheme == de.danoeh.antennapod.core.R.style.Theme_AntennaPod_Pink){
            newTheme= R.string.pref_theme_title_dark;
        }
        return newTheme;
    }

    // changing all the themes to pink theme
    private int switchThemeLightToPink(int currentTheme){
        int newTheme=0;
        if(currentTheme == de.danoeh.antennapod.core.R.style.Theme_AntennaPod_Light |
                currentTheme == de.danoeh.antennapod.core.R.style.LightWithLobster |
                currentTheme == de.danoeh.antennapod.core.R.style.LightWithUbuntu){
            newTheme= R.string.pref_theme_title_pink;
        }
        return newTheme;
    }


    private int switchThemeDarkBlackToPink(int currentTheme){
        int newTheme=0;
        if(currentTheme == de.danoeh.antennapod.core.R.style.Theme_AntennaPod_Dark |
                currentTheme == de.danoeh.antennapod.core.R.style.DarkWithLobster |
                currentTheme == de.danoeh.antennapod.core.R.style.DarkWithUbuntu){
            newTheme= R.string.pref_theme_title_pink;
        }
        return newTheme;
    }

    private int switchThemeBlackToPink(int currentTheme){
        int newTheme=0;
        if(currentTheme == de.danoeh.antennapod.core.R.style.BlackWithLobster |
                currentTheme == de.danoeh.antennapod.core.R.style.BlackWithUbuntu |
                currentTheme == de.danoeh.antennapod.core.R.style.Theme_Base_AntennaPod_TrueBlack){
            newTheme= R.string.pref_theme_title_pink;
        }
        return newTheme;
    }

    private int switchThemeBlueToPink(int currentTheme){
        int newTheme=0;
        if(currentTheme == de.danoeh.antennapod.core.R.style.BlueWithLobster |
                currentTheme == de.danoeh.antennapod.core.R.style.BlueWithUbuntu |
                currentTheme == de.danoeh.antennapod.core.R.style.Theme_AntennaPod_Blue){
            newTheme= R.string.pref_theme_title_pink;
        }
        return newTheme;
    }



    @Test
    public void testSwitch() {

        final int theme= UserPreferences.getTheme();
        int otherTheme;

        if(switchThemePinkToBlue(theme) != 0){

            otherTheme = switchThemePinkToBlue(theme);

        }else if(switchThemeBlackToBlue(theme)!= 0){

            otherTheme = switchThemeBlackToBlue(theme);

        }else if (switchThemeLightToBlue(theme) != 0){

           otherTheme = switchThemeLightToBlue(theme);

        }else if(switchThemeDarkBlackToBlue(theme)!= 0){

            otherTheme= switchThemeDarkBlackToBlue(theme);

        }else if(switchThemePinkToLight(theme) != 0){

            otherTheme= switchThemePinkToLight(theme);

        }else if(switchThemeBlackToLight(theme) != 0){

            otherTheme= switchThemeBlackToLight(theme);

        }else if(switchThemeDarkBlackToLight(theme)!= 0) {

            otherTheme = switchThemeDarkBlackToLight(theme);

        } else if(switchThemeBlueToLight(theme)!= 0){

            otherTheme= switchThemeBlueToLight(theme);

        }else if(switchThemeBlueToBlack(theme)!= 0){

            otherTheme= switchThemeBlueToBlack(theme);

        }else if(switchThemePinkToBlack(theme)!= 0){

            otherTheme= switchThemePinkToBlack(theme);

        } else if(switchThemeDarkBlackToBlack(theme)!= 0){

            otherTheme= switchThemeDarkBlackToBlack(theme);

        } else if (switchThemeLightToBlack(theme) != 0){

            otherTheme= switchThemeLightToBlack(theme);

        } else  if (switchThemeLightToDarkBlack(theme) != 0){

            otherTheme= switchThemeLightToDarkBlack(theme);

        }else if(switchThemeBlackToDarkBlack(theme)!= 0){

          otherTheme = switchThemeBlackToDarkBlack(theme);

        }else if(switchThemePinkToDarkBlack(theme)!= 0){

            otherTheme = switchThemePinkToDarkBlack(theme);

        }else if (switchThemeBlueToDarkBlack(theme)!= 0) {

            otherTheme= switchThemeBlueToDarkBlack(theme);

        }else if(switchThemeBlackToPink(theme) != 0){

            otherTheme= switchThemeBlackToPink(theme);

        } else if(switchThemeDarkBlackToPink(theme)!=0){

            otherTheme= switchThemeDarkBlackToPink(theme);

        } else if (switchThemeLightToPink(theme)!=0){

            otherTheme= switchThemeLightToPink(theme);

        }else{

            otherTheme= switchThemeBlueToPink(theme);
        }

        clickPreference(withText(R.string.user_interface_label));
        clickPreference(withText(R.string.pref_set_theme_title));
        onView(withText(otherTheme)).perform(click());


        assertTrue(solo.waitForCondition(() -> (UserPreferences.getTheme() == theme || UserPreferences.getTheme() != theme), Timeout.getLargeTimeout()));
    }


}

