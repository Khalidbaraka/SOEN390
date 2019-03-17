package de.danoeh.antennapod.fragment;

import de.danoeh.antennapod.adapter.CategoriesAdapter;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.danoeh.antennapod.R;
import de.danoeh.antennapod.core.feed.CategoryItem;

//This Fragment is used to display the cards/list of iTunes Categories with their names & images
public class CategoriesListFragment extends android.support.v4.app.Fragment {

    public static final String TAG = "CategoriesListFragment";
    private List<CategoryItem> categoriesList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        ArrayList<String> itunesCategories;
        categoriesList = new ArrayList<>();

        Resources res = getResources();

        //Adding all the names for our categories list in a string array
        String[] catgoriesName = res.getStringArray(R.array.itunes_podcast_category_list);
        itunesCategories = new ArrayList<>(Arrays.asList(catgoriesName));

        //Adding all icons for categories list into an integer array
        int[] icons = new int[]{
                R.drawable.arts_icon,
                R.drawable.business_icon,
                R.drawable.comedy_icon,
                R.drawable.education_icon,
                R.drawable.games_icon,
                R.drawable.government_icon,
                R.drawable.health_icon,
                R.drawable.music_icon,
                R.drawable.news_icon,
                R.drawable.religion_icon,
                R.drawable.science_icon,
                R.drawable.society_icon,
                R.drawable.sports_icon,
                R.drawable.technology_icon
        };

        //Creating CategoryItems one at a time for every itunesCategories available.
        //Setting title and icon for each CategoryItem.
        //Adding CategoryItem to the CategoriesList
        for (int i = 0; i < itunesCategories.size(); i++) {
            CategoryItem category = new CategoryItem();
            category.setName(itunesCategories.get(i));
            category.setImage(icons[i]);
            categoriesList.add(category);
        }
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        RecyclerView myRecyclerView;

        View myView = inflater.inflate(R.layout.categories_list, container, false);

        myRecyclerView = myView.findViewById(R.id.category_list_id);

        CategoriesAdapter categoriesAdapter = new CategoriesAdapter(getContext(), categoriesList);

        //Layout into Grid with 2 columns
        myRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        myRecyclerView.setAdapter(categoriesAdapter);

        return myView;
    }
}




