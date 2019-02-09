package de.test.antennapod.espresso.fontStyle;

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
public class FontStyleEspressoTest {
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

    @Test
    public void testSwitchFont(){
        final String font = UserPreferences.getFont();
        String otherFont="";
        if(font == "Default"){
            otherFont = "Lobster";
        }
        else if(font == "Lobster"){
            otherFont = "Default";
        }
        else{
            otherFont = "Default";
        }
        clickPreference(withText(R.string.user_interface_label));
        clickPreference(withText(R.string.pref_set_font_title));
        onView(withText(otherFont)).perform(click());
        assertTrue(solo.waitForCondition(() -> UserPreferences.getFont() != font, Timeout.getLargeTimeout()));
    }

    private void clickPreference(Matcher<View> matcher) {
        onView(withId(R.id.list))
                .perform(RecyclerViewActions.actionOnItem(hasDescendant(matcher), click()));
    }
}