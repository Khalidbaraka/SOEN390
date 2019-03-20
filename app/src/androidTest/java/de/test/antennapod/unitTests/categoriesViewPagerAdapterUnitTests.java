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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;
import android.view.ViewGroup;

import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(AndroidJUnit4.class)
public class categoriesViewPagerAdapterUnitTests {

    private FragmentManager fragmentManager;
    private FragmentManager fm1;
    private List<Fragment> fragmentList;
    private Fragment expected;
    CategoriesViewPagerAdapter c;
    ViewGroup vg;


    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);


    @Before
    public void setup(){
        fm1 = Mockito.mock(FragmentManager.class);
        fragmentList = new ArrayList<>();
        expected = new Fragment();
        c = new CategoriesViewPagerAdapter(fragmentManager);
    }

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
