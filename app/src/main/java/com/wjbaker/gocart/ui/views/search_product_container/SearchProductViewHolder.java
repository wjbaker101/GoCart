package com.wjbaker.gocart.ui.views.search_product_container;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wjbaker.gocart.R;
import com.wjbaker.gocart.shopping.Product;
import com.wjbaker.gocart.shopping.ShoppingList;
import com.wjbaker.gocart.ui.views.product_item.ProductItemSearchView;

/**
 * Created by William on 15/03/2018.
 */
public class SearchProductViewHolder extends RecyclerView.ViewHolder
{
    public View searchProductView;

    public SearchProductViewHolder(View searchProductView)
    {
        super(searchProductView);

        this.searchProductView = searchProductView;
    }

    /**
     * Updates the content of the View.
     *
     * @param product The Product associated with the View.
     */
    public void bind(Product product)
    {
        TextView productName = this.searchProductView.findViewById(R.id.product_item_search_name);
        TextView productCost = this.searchProductView.findViewById(R.id.product_item_search_cost);
        final ImageView image = this.searchProductView.findViewById(R.id.product_item_search_image);
        CheckBox checkBox = this.searchProductView.findViewById(R.id.product_item_search_checked);

        ProductItemSearchView productItemView = this.searchProductView.findViewById(R.id.product_item_search_content);

        productName.setText(product.getName());
        productCost.setText(String.format("£%.2f", product.getCost()));
        Picasso.get().load(product.getImageURL()).into(image);

        // Remove any existing listeners so they do not prematurely fire
        // Then set initial checkbox state
        checkBox.setOnCheckedChangeListener(null);
        checkBox.setChecked(false);

        productItemView.setProduct(product);

        if (ShoppingList.getInstance(this.searchProductView.getContext()).get(product.getTPNB()) != null)
        {
            checkBox.setChecked(true);
        }

        checkBox.setOnCheckedChangeListener(this.getOnCheckBoxChangeListener(product));
    }

    /**
     * Adds the event listener for when the CheckBox checked state changes.<br>
     * Will add or remove the associated Product from the shopping list.
     */
    public CompoundButton.OnCheckedChangeListener getOnCheckBoxChangeListener(final Product product)
    {
        return new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked)
            {
                if (checked)
                {
                    ShoppingList.getInstance(searchProductView.getContext()).addItem(product);
                }
                else
                {
                    ShoppingList.getInstance(searchProductView.getContext()).removeItem(product.getTPNB());
                }
            }
        };
    }
}
