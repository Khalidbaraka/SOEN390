package de.test.antennapod.unitTests;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import de.danoeh.antennapod.activity.MainActivity;
import de.danoeh.antennapod.adapter.CategoriesViewPagerAdapter;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import java.util.ArrayList;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class categoriesViewPagerAdapterUnitTests {

    private FragmentManager fragmentManager;
    private List<Fragment> fragmentList;
    private Fragment expected;
    CategoriesViewPagerAdapter c;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);


    @Before
    public void setup(){
        fragmentList = new ArrayList<>();
        expected = new Fragment();
        c = new CategoriesViewPagerAdapter(fragmentManager);}

    // testing methods in CategoriesViewPagerAdapter fragment
    @Test
    public void getItemTest(){
        c.addFragment(expected, "testing");
        Fragment actualObject = c.getItem(0);
        assertEquals(expected, actualObject );
    }

    @Test
    public void addItemTest(){
        fragmentList.add(expected);
        c.addFragment(expected, "testing");
        List<Fragment> realFrgamentList =c.getFragmentList();
        assertEquals(fragmentList.size(), realFrgamentList.size());
    }

}
