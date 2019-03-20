package de.test.antennapod.espresso.discoveryPage;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;
import com.robotium.solo.Timeout;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import de.danoeh.antennapod.R;
import de.danoeh.antennapod.activity.MainActivity;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SearchTest {

    private Solo solo;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() {
        solo = new Solo(getInstrumentation(), mActivityRule.getActivity());

        Timeout.setSmallTimeout(500);
        Timeout.setLargeTimeout(1000);
    }

    @After
    public void tearDown() {
        solo.finishOpenedActivities();
    }

    @Test
    public void test1GoFirstToDiscoveryPage() {
        // queue page
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        String currentPage = getActionbarTitle();
        if (("Discovery Page").equals(currentPage)){
            assertEquals(solo.getString(R.string.discovery_page_label), getActionbarTitle());

        }else{
            solo.clickOnText(solo.getString(R.string.discovery_page_label));
            solo.waitForView(android.R.id.list);
            assertEquals(solo.getString(R.string.discovery_page_label), getActionbarTitle());
        }
    }

    @Test
    public void test2ButtonsAndNames() {

        //Checks EditText is there
        onView(withId(R.id.editText)).check(matches(notNullValue() ));

        //Checks "Search" button is there
        onView(withId(R.id.button6)).check(matches(notNullValue() ));

        //Checks button name matches
        onView(withId(R.id.button6)).check(matches(withText("Search")));
        assertEquals("Search", solo.getString(R.string.discover_page_search));

        //Perform a first search

        //Write search info in editText
        onView(withId(R.id.editText)).perform(clearText(),typeText("Joe Rogan"));

        Espresso.closeSoftKeyboard();
        solo.waitForView(android.R.id.list);

        //Press the Search button in the Discovery Page
        onView(withId(R.id.button6)).perform(click());

        solo.waitForView(android.R.id.list);
        Espresso.pressBack();
        solo.waitForView(android.R.id.list);

        //Perform a second search

        //Write search info in editText
        onView(withId(R.id.editText)).perform(clearText(),typeText("Ted Talk"));
        Espresso.closeSoftKeyboard();

        solo.waitForView(android.R.id.list);

        //Press the Search button in the Discovery Page
        onView(withId(R.id.button6)).perform(click());

        solo.waitForView(android.R.id.list);
        Espresso.pressBack();
        solo.waitForView(android.R.id.list);

    }


    private String getActionbarTitle() {
        return ((MainActivity) solo.getCurrentActivity()).getSupportActionBar().getTitle().toString();
    }

}