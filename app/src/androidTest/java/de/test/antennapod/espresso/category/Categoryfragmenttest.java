package de.test.antennapod.espresso.category;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
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
        public void test1GoFirstToDiscoveryPageVerifyItunesCategoryButton() {
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

            //Checks button is there
            onView(withId(R.id.categories_button)).check(matches(notNullValue() ));

            //Checks button name matches
            onView(withId(R.id.categories_button)).check(matches(withText("iTunes Categories")));
            assertEquals("iTunes Categories", solo.getString(R.string.itunes_categories));
        }

        @Test
        public void test2FragmentAndTestListItem() {

            //selects & opens the iTunes Categories button in the Discovery Page
            onView(withId(R.id.categories_button)).perform(click());
            solo.waitForView(android.R.id.list);

            //Check page text name matches
//            onView(withId(R.id.itunesCategoriesTextView1)).check(matches(withText("iTunes Podcast")));
//            assertEquals("iTunes Podcast", solo.getString(R.string.itunes_podcast));

            //selects & opens the category list item Arts
            onView(withId(R.id.category_list_id)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("Arts")), click()));
            solo.waitForView(android.R.id.list);
            pressBack();
            solo.waitForView(android.R.id.list);

            //selects & opens the category list item Business
            onView(withId(R.id.category_list_id)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("Business")), click()));
            solo.waitForView(android.R.id.list);
            pressBack();
            solo.waitForView(android.R.id.list);

            //selects & opens the category list item Comedy
            onView(withId(R.id.category_list_id)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("Comedy")), click()));
            solo.waitForView(android.R.id.list);
            pressBack();
            solo.waitForView(android.R.id.list);

            //selects & opens the category list item Education
            onView(withId(R.id.category_list_id)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("Education")), click()));
            solo.waitForView(android.R.id.list);
            pressBack();
            solo.waitForView(android.R.id.list);

            //selects & opens the category list item Games & Hobbies
            onView(withId(R.id.category_list_id)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("Games & Hobbies")), click()));
            solo.waitForView(android.R.id.list);
            pressBack();
            solo.waitForView(android.R.id.list);

            //selects & opens the category list item Government & Organization
            onView(withId(R.id.category_list_id)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("Government & Organization")), click()));
            solo.waitForView(android.R.id.list);
            pressBack();
            solo.waitForView(android.R.id.list);

            //selects & opens the category list item Health
            onView(withId(R.id.category_list_id)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("Health")), click()));
            solo.waitForView(android.R.id.list);
            pressBack();
            solo.waitForView(android.R.id.list);

            //selects & opens the category list item Music
            onView(withId(R.id.category_list_id)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("Music")), click()));
            solo.waitForView(android.R.id.list);
            pressBack();
            solo.waitForView(android.R.id.list);

            //selects & opens the category list item News & Politics
            onView(withId(R.id.category_list_id)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("News & Politics")), click()));
            solo.waitForView(android.R.id.list);
            pressBack();
            solo.waitForView(android.R.id.list);

            //selects & opens the category list item Religion & Spirituality
            onView(withId(R.id.category_list_id)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("Religion & Spirituality")), click()));
            solo.waitForView(android.R.id.list);
            pressBack();
            solo.waitForView(android.R.id.list);

            //selects & opens the category list item Science & Medicine
            onView(withId(R.id.category_list_id)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("Science & Medicine")), click()));
            solo.waitForView(android.R.id.list);
            pressBack();
            solo.waitForView(android.R.id.list);

            //selects & opens the category list item Society & Culture
            onView(withId(R.id.category_list_id)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("Society & Culture")), click()));
            solo.waitForView(android.R.id.list);
            pressBack();
            solo.waitForView(android.R.id.list);

            //selects & opens the category list item Sports & Recreation
            onView(withId(R.id.category_list_id)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("Sports & Recreation")), click()));
            solo.waitForView(android.R.id.list);
            pressBack();
            solo.waitForView(android.R.id.list);

            //selects & opens the category list item Technology
            onView(withId(R.id.category_list_id)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("Technology")), click()));
            solo.waitForView(android.R.id.list);

        }

        //Method used to press on the back button
        public static void pressBack() {
            onView(isRoot()).perform(ViewActions.pressBack());
        }

        private String getActionbarTitle() {
            return ((MainActivity) solo.getCurrentActivity()).getSupportActionBar().getTitle().toString();
        }

    }

