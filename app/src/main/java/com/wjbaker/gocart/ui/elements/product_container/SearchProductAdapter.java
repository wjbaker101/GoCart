package com.wjbaker.gocart.ui.elements.product_container;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.wjbaker.gocart.R;
import com.wjbaker.gocart.shopping.Product;
import com.wjbaker.gocart.ui.elements.ProductSearchItem;

import java.util.List;

/**
 * Created by William on 15/03/2018.
 */
public class SearchProductAdapter extends RecyclerView.Adapter<SearchProductViewHolder>
{
    private List<Product> dataset;

    public SearchProductAdapter(List<Product> dataset)
    {
        this.dataset = dataset;
    }

    @Override
    public SearchProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        ProductSearchItem newShoppingListItem = new ProductSearchItem(parent.getContext(), null);

        final int scale = (int)(parent.getResources().getDisplayMetrics().density);

        newShoppingListItem.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int)(58 * scale)));

        SearchProductViewHolder viewHolder = new SearchProductViewHolder(newShoppingListItem);

        return viewHolder;
    }

    /**
     * Updates the contents of the View within the Recycler View.
     *
     * @param holder Holder of the View.
     * @param position Position of the View within the dataset.
     */
    @Override
    public void onBindViewHolder(SearchProductViewHolder holder, int position)
    {
        holder.searchProductView.bindProduct(dataset.get(position));
    }

    public void addItem(Product product)
    {
        this.dataset.add(product);
        this.notifyItemInserted(this.dataset.size() - 1);
    }

    public void removeAll()
    {
        this.dataset.clear();
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount()
    {
        return dataset.size();
    }
}
