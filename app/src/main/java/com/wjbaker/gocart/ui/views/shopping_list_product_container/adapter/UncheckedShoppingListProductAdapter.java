package com.wjbaker.gocart.ui.views.shopping_list_product_container;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wjbaker.gocart.R;
import com.wjbaker.gocart.shopping.Product;
import com.wjbaker.gocart.ui.activities.MainActivity;

import java.util.List;

/**
 * Created by William on 15/03/2018.
 *
 * Allows activities to interact with the RecyclerView.<br>
 * Adds and removes views which are related to products.
 */
public class UncheckedShoppingListProductAdapter extends RecyclerView.Adapter<ShoppingListProductViewHolder>
{
    /**
     * Stores the list of Products to be displayed onto the RecyclerView.
     */
    private List<Product> dataset;

    /**
     * Adapter of the RecyclerView where the Product will be moved to.
     */
    private UncheckedShoppingListProductAdapter newItemContainer;

    /**
     * The MainActivity.
     */
    private MainActivity mainActivity;

    /**
     * Creates the Adapter.
     *
     * @param dataset Initial products in the RecyclerView.
     * @param mainActivity The MainActivity.
     */
    public UncheckedShoppingListProductAdapter(List<Product> dataset, MainActivity mainActivity)
    {
        this.dataset = dataset;

        this.mainActivity = mainActivity;
    }

    /**
     * Initialises the holder for the Product.
     */
    @Override
    public ShoppingListProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_shopping, parent, false);

        ShoppingListProductViewHolder viewHolder = new ShoppingListProductViewHolder(view, this, this.newItemContainer);

        return viewHolder;
    }

    /**
     * Updates the contents of the View within the Recycler View.
     *
     * @param holder Holder of the View.
     * @param position Position of the View within the dataset.
     */
    @Override
    public void onBindViewHolder(ShoppingListProductViewHolder holder, int position)
    {
        holder.bind(this.dataset.get(position));
    }

    /**
     * Adds a Product to the RecyclerView.
     *
     * @param product Product to be added.
     */
    public void addItem(Product product)
    {
        this.dataset.add(product);

        this.onItemChange(product, true);
    }

    /**
     * Removes a Product from the RecyclerView.
     *
     * @param product Product to be removed.
     */
    public void removeItem(Product product)
    {
        this.dataset.remove(product);

        this.onItemChange(product, false);
    }

    /**
     * Called whenever a Product is added or removed from the RecyclerView.
     *
     * @param product The Product.
     * @param isAdded Whether the Product has just been added or removed.
     */
    private void onItemChange(Product product, boolean isAdded)
    {
        this.notifyDataSetChanged();

        if (isAdded)
        {
            this.mainActivity.incrementCheckedItemsCount();
        }
        else
        {
            this.mainActivity.incrementUncheckedItemsCount();
        }
    }

    /**
     * Gets the number of products currently in the RecyclerView.
     *
     * @return Number of products.
     */
    @Override
    public int getItemCount()
    {
        return dataset.size();
    }

    /**
     * Sets the container where the Product will be moved to when the user toggles the CheckBox.
     *
     * @param newItemContainer Adapter of the new container.
     */
    public void setNewItemContainer(UncheckedShoppingListProductAdapter newItemContainer)
    {
        this.newItemContainer = newItemContainer;
    }
}
