package de.danoeh.antennapod.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import de.danoeh.antennapod.R;
import de.danoeh.antennapod.activity.MainActivity;
import de.danoeh.antennapod.core.feed.CategoryItem;
import de.danoeh.antennapod.fragment.ItunesSearchFragment;
import android.support.v4.app.Fragment;

//Adapter used for iTunes Podcasts Categories
public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.MyViewHolder> {

    private Context myContext ;
    private List<CategoryItem> categoryItemList ;
    private Fragment myItunesSearchFragment = new ItunesSearchFragment();

    public CategoriesAdapter(Context myContext, List<CategoryItem> categoryItemList) {
        this.myContext = myContext;
        this.categoryItemList = categoryItemList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(myContext);
        view = mInflater.inflate(R.layout.categories_itemview,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        //Binds name & image to category in categoies_itemView Layout.
        holder.categoryName.setText(categoryItemList.get(position).getName());
        holder.categoryImage.setImageResource(categoryItemList.get(position).getImage());

        holder.myCardView.setOnClickListener(new View.OnClickListener() {

            //When you click in one of the category, returns iTunes Podcasts from this category.
            @Override
            public void onClick(View iTunesPodcastView) {
                String name = (String)holder.categoryName.getText();
                ((ItunesSearchFragment) myItunesSearchFragment).setCategoryName(name);
                ((MainActivity)myContext).loadChildFragment(myItunesSearchFragment);
            }
        });
    }

    protected static class MyViewHolder extends RecyclerView.ViewHolder {

        private CardView myCardView ;
        private TextView categoryName;
        private ImageView categoryImage;

        private MyViewHolder(View categoryItemView) {

            super(categoryItemView);

            myCardView = categoryItemView.findViewById(R.id.categories_itemview_id);
            categoryName = categoryItemView.findViewById(R.id.category_name_id) ;
            categoryImage = categoryItemView.findViewById(R.id.category_image_id);
        }
    }

    @Override
    public int getItemCount() {
        return categoryItemList.size();
    }
}