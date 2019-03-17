package de.danoeh.antennapod.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.danoeh.antennapod.R;
import de.danoeh.antennapod.core.feed.CategoryItem;

/**
 *
 */
public class GpodnetCategoriesFragment extends android.support.v4.app.Fragment {

    public static final String TAG = "GpodnetCategoriesFragment";


    public GpodnetCategoriesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.gpodnet_categories_items, container, false);
    }

}
