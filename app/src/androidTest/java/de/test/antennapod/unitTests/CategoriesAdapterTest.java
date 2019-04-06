package de.test.antennapod.unitTests;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import android.support.v7.widget.RecyclerView;

import org.junit.Before;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import de.danoeh.antennapod.adapter.CategoriesAdapter;
import de.danoeh.antennapod.core.feed.CategoryItem;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class CategoriesAdapterTest {

    Context context = InstrumentationRegistry.getTargetContext();
    private CategoriesAdapter adapter;
    private RecyclerView recyclerView;
    private CategoryItem item1 =new CategoryItem("Test", 1);
    private CategoryItem item2 =new CategoryItem("Test", 1);
    private List<CategoryItem> categoriesList;

    @Before
    public void setup() throws Exception {
        categoriesList = new ArrayList<>();
        categoriesList.add(item1);
        categoriesList.add(item2);

        adapter = new CategoriesAdapter(context,categoriesList);
    }
    @Test
    public void testGetItemCount(){
        assertEquals(categoriesList.size(),adapter.getItemCount());
    }
}