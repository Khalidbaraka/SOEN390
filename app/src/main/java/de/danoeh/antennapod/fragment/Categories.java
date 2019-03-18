package de.danoeh.antennapod.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.danoeh.antennapod.R;
import de.danoeh.antennapod.adapter.CategoriesViewPagerAdapter;


public class Categories extends Fragment {


    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;

    public Categories() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_categories, container, false);
        tabLayout = myView.findViewById(R.id.categories_tablayout_id);
        appBarLayout = myView.findViewById(R.id.categories_appbar_id);
        viewPager = myView.findViewById(R.id.categories_viewpager_id);

        CategoriesViewPagerAdapter adapter = new CategoriesViewPagerAdapter(getFragmentManager());
        adapter.addFragment(new GpodnetCategoriesFragment(), "Gpodnet");
        adapter.addFragment(new CategoriesListFragment(), "iTunes");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        return myView;
    }




}
