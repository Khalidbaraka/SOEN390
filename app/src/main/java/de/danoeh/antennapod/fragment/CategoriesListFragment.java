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

        String[] catgoriesName;
        categoriesList = new ArrayList<>();

        Resources res = getResources();

        //Adding all the names for our categories list in a string array
        catgoriesName = res.getStringArray(R.array.itunes_podcast_category_list);

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

        //Creating CategoryItems one at a time (
        //(size equals number of categories available determined by length of names in categoryName).
        //Setting title and icon for CategoryItem.
        //Adding each CategoryItem to the CategoriesList which holds list of all categoryItem
        for (int i = 0; i < catgoriesName.length; i++) {
            CategoryItem category = new CategoryItem();
            category.setName(catgoriesName[i]);
            category.setImage(icons[i]);
            categoriesList.add(category);
        }
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        RecyclerView myRecyclerView;

        View myView = inflater.inflate(R.layout.categories_list, container, false);

        myRecyclerView = myView.findViewById(R.id.category_list_id);

        //Create adapter using current context and list of category Item.
        CategoriesAdapter categoriesAdapter = new CategoriesAdapter(getContext(), categoriesList);

        //Set layout manager to Grid with 2 columns
        myRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        //Set the adapter into our recycler view
        //i.e. Set the UI row of categories_itemview.xml into our recyclerView category_list.xml
        myRecyclerView.setAdapter(categoriesAdapter);

        return myView;
    }
}




