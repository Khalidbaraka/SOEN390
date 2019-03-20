package de.test.antennapod.unittests;
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
public class CategoriesViewPagerAdapterUnitTests {

    private FragmentManager fragmentManager;
    private List<Fragment> fragmentList;
    private Fragment expected;



    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);


    @Before
    public void setUp(){
        fragmentList = new ArrayList<>();
        expected = new Fragment();
    }

    // testing methods in CategoriesViewPagerAdapter fragment
    @Test
    public void getItemTest(){
        CategoriesViewPagerAdapter c = new CategoriesViewPagerAdapter(fragmentManager);
        c.addFragment(expected, "testing");
        Fragment actualObject = c.getItem(0);
        assertEquals(expected, actualObject );
    }

    @Test
    public void addItemTest(){
        CategoriesViewPagerAdapter c = new CategoriesViewPagerAdapter(fragmentManager);
        fragmentList.add(expected);
        c.addFragment(expected, "testing");
        List<Fragment> realFrgamentList =c.getFragmentList();
        assertEquals(fragmentList.size(), realFrgamentList.size());
    }
    /* public void destroyItem(ViewGroup container, int position, Object object) {
        fragmentManager.beginTransaction().remove((Fragment)object).commitNowAllowingStateLoss();
    }*/
    // wanna check if the method beginTransaction.remove is being called inside destroyItem
//    @Test
//    public void destroyItemTest(){
//
//        CategoriesViewPagerAdapter c1 = new CategoriesViewPagerAdapter(fm1);
//
//        c1.addFragment(expected,"testing");
//        c1.destroyItem(vg,0,expected);
////        when(fm1.beginTransaction().remove(expected).commitAllowingStateLoss()).thenReturn(1);
//        verify(fm1.beginTransaction().remove(expected));
//
//
//    }

}
