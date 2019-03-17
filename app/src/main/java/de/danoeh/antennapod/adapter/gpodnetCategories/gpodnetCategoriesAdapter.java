package de.danoeh.antennapod.adapter.gpodnetCategories;

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
import de.danoeh.antennapod.core.feed.CategoryItem;

public class gpodnetCategoriesAdapter extends RecyclerView.Adapter<gpodnetCategoriesAdapter.MyViewHolder> {

    private Context myContext ;
    private List<CategoryItem> categoryItemList ;

    public gpodnetCategoriesAdapter(Context mycontext, List categoryItemList) {
        this.myContext = mycontext;
        this.categoryItemList = categoryItemList;
    }

    @Override
    public gpodnetCategoriesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(myContext);
        view = mInflater.inflate(R.layout.gpodnet_categories_items,parent,false);
        return new gpodnetCategoriesAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        //Binds name & image to category in categoies_itemView Layout.
        holder.categoryName.setText(categoryItemList.get(position).getName());
        holder.categoryImage.setImageResource(categoryItemList.get(position).getImage());

    }

    protected static class MyViewHolder extends RecyclerView.ViewHolder {

        private CardView myCardView ;
        private TextView categoryName;
        private ImageView categoryImage;

        private MyViewHolder(View categoryItemView) {

            super(categoryItemView);

            myCardView = categoryItemView.findViewById(R.id.gpodnet_categories_items_id);
            categoryName = categoryItemView.findViewById(R.id.gpodnet_category_text_id) ;
            categoryImage = categoryItemView.findViewById(R.id.gpodnet_category_image_id);
        }
    }

    @Override
    public int getItemCount() {
        return categoryItemList.size();
    }
}
