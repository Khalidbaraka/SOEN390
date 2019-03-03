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
import de.danoeh.antennapod.core.feed.CategoryItem;

/**
 *
 */

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.MyViewHolder> {

    private Context myContext ;
    private List<CategoryItem> categoryItemList ;


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

        holder.category_name.setText(categoryItemList.get(position).getName());
        holder.category_image.setImageResource(categoryItemList.get(position).getImage());

    }

    @Override
    public int getItemCount() {
        return categoryItemList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private CardView myCardView ;
        private TextView category_name;
        private ImageView category_image;

        public MyViewHolder(View itemView) {

            super(itemView);

            myCardView = itemView.findViewById(R.id.categories_itemview_id);
            category_name = itemView.findViewById(R.id.category_name_id) ;
            category_image = itemView.findViewById(R.id.category_image_id);
        }
    }

}