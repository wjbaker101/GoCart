package com.wjbaker.gocart.ui.views.shopping_list_product_container.adapter;

import com.wjbaker.gocart.shopping.Product;
import com.wjbaker.gocart.ui.activities.MainActivity;

import java.util.List;

/**
 * Created by William on 15/03/2018.
 *
 * Allows activities to interact with the RecyclerView.<br>
 * Adds and removes views which are related to products.
 */
public class CheckedShoppingListProductAdapter extends ShoppingListProductAdapter
{
    public CheckedShoppingListProductAdapter(List<Product> dataset, MainActivity mainActivity)
    {
        super(dataset, mainActivity);
    }
    /**
     * Called whenever a Product is added or removed from the RecyclerView.
     *
     * @param product The Product.
     * @param isAdded Whether the Product has just been added or removed.
     */
    protected void onItemChange(Product product, boolean isAdded)
    {
        this.notifyDataSetChanged();

        if (isAdded)
        {
            this.mainActivity.incrementCheckedItemsCount(1);
        }
        else
        {
            this.mainActivity.incrementCheckedItemsCount(-1);
        }
    }
}
