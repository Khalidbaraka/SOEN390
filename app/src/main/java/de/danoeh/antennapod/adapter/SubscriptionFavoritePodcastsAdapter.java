package de.danoeh.antennapod.adapter;

import android.support.v4.app.FragmentStatePagerAdapter;

        import android.os.Parcelable;
        import android.support.annotation.Nullable;
        import android.support.v4.app.Fragment;
        import android.support.v4.app.FragmentManager;
        import android.support.v4.app.FragmentStatePagerAdapter;
        import android.view.ViewGroup;
        import java.util.ArrayList;
        import java.util.List;

public class SubscriptionFavoritePodcastsAdapter extends FragmentStatePagerAdapter {

    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> fragmentListTitle = new ArrayList<>();

    private FragmentManager fragmentManager;


    public SubscriptionFavoritePodcastsAdapter(FragmentManager fm) {
        super(fm);
        this.fragmentManager = fm;
    }

    @Override
    public Fragment getItem(int position) {
        return this.fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentListTitle.size();
    }

    @Override
    public int getItemPosition(Object object) {
        // Causes adapter to reload all Fragments when
        // notifyDataSetChanged is called
        return POSITION_UNCHANGED;
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        fragmentManager.beginTransaction().remove((Fragment)object).commitNowAllowingStateLoss();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentListTitle.get(position);
    }

    public void addFragment(Fragment fragment, String title) {
        fragmentList.add(fragment);
        fragmentListTitle.add(title);
    }


    @Override
    public Parcelable saveState() {
        return null;
    }
}
