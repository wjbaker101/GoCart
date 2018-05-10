package com.wjbaker.gocart.ui.views.search_product_container;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wjbaker.gocart.R;
import com.wjbaker.gocart.shopping.Product;
import com.wjbaker.gocart.shopping.ShoppingList;
import com.wjbaker.gocart.ui.activities.SearchActivity;
import com.wjbaker.gocart.ui.views.product_item.ProductItemSearchView;

import java.util.List;

/**
 * Created by William on 15/03/2018.
 */
public class SearchProductAdapter extends RecyclerView.Adapter<SearchProductViewHolder>
{
    /**
     * Stores the products to display.
     */
    private List<Product> dataset;

    /**
     * Stores the SearchActivity so that it can be passed through to the ViewHolder.
     */
    private SearchActivity searchActivity;

    public SearchProductAdapter(List<Product> dataset, SearchActivity searchActivity)
    {
        this.dataset = dataset;

        this.searchActivity = searchActivity;
    }

    @Override
    public SearchProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_search, parent, false);

        SearchProductViewHolder viewHolder = new SearchProductViewHolder(view, this.searchActivity);

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
        holder.bind(this.dataset.get(position));
    }

    /**
     * Adds a Product to the RecyclerView.
     *
     * @param product The Product to add.
     */
    public void addItem(Product product)
    {
        this.dataset.add(product);
        this.notifyItemInserted(this.dataset.size() - 1);
    }

    /**
     * Removes a Product from the RecyclerView.
     *
     * @param product The Product to remove.
     */
    public void removeItem(Product product)
    {
        this.dataset.remove(product);
        this.notifyDataSetChanged();
    }

    /**
     * Removes all products from the RecyclerView.
     */
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

    /**
     * Gets the list of products in the RecyclerView.
     *
     * @return List of Product.
     */
    public List<Product> getProducts()
    {
        return this.dataset;
    }
}
