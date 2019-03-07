package de.test.antennapod.espresso.category;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
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
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertEquals;

    @FixMethodOrder(MethodSorters.NAME_ASCENDING)
    public class Categoryfragmenttest{

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
            if ("Discovery Page".equals(currentPage)){
                assertEquals(solo.getString(R.string.discovery_page_label), getActionbarTitle());

            }else{
                solo.clickOnText(solo.getString(R.string.discovery_page_label));
                solo.waitForView(android.R.id.list);
                assertEquals(solo.getString(R.string.discovery_page_label), getActionbarTitle());
            }
        }

        @Test
        public void test2ButtonItunesCategories() {
            //Checks button is there
            onView(withId(R.id.itunes_categories_button)).check(matches(notNullValue() ));

            //Checks button name matches
            onView(withId(R.id.itunes_categories_button)).check(matches(withText("iTunes Categories")));
            assertEquals("iTunes Categories", solo.getString(R.string.itunes_categories));

        }



        private String getActionbarTitle() {
            return ((MainActivity) solo.getCurrentActivity()).getSupportActionBar().getTitle().toString();
        }


    }

