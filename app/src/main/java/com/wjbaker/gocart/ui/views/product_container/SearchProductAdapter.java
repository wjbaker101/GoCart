package com.wjbaker.gocart.ui.views.product_container;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.wjbaker.gocart.R;
import com.wjbaker.gocart.shopping.Product;
import com.wjbaker.gocart.shopping.ShoppingList;
import com.wjbaker.gocart.ui.views.product_item.ProductItemSearchView;
import com.wjbaker.gocart.ui.views.product_item.ProductItemView;

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
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.product_item_search, parent, false);

        SearchProductViewHolder viewHolder = new SearchProductViewHolder(view);

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
        final Product product = this.dataset.get(position);

        TextView productName = holder.searchProductView.findViewById(R.id.product_item_search_name);
        TextView productCost = holder.searchProductView.findViewById(R.id.product_item_search_cost);
        final ImageView image = holder.searchProductView.findViewById(R.id.product_item_search_image);

        ProductItemSearchView productItemView = holder.searchProductView.findViewById(R.id.product_item_search_content);

        productName.setText(product.getName());
        productCost.setText(String.format("£%.2f", product.getCost()));

        productItemView.setProduct(product);

        if (ShoppingList.getInstance(holder.searchProductView.getContext()).get(product.getTPNB()) != null)
        {
            CheckBox checkBox = holder.searchProductView.findViewById(R.id.product_item_search_checked);

            checkBox.setChecked(true);
        }

        productItemView.addCheckBoxListener();
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
