package com.wjbaker.gocart.ui.views.shopping_list_product_container.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wjbaker.gocart.R;
import com.wjbaker.gocart.shopping.Product;
import com.wjbaker.gocart.shopping.ShoppingList;
import com.wjbaker.gocart.ui.activities.MainActivity;
import com.wjbaker.gocart.ui.dialogs.ProductInfoDialog;
import com.wjbaker.gocart.ui.views.shopping_list_product_container.ProductMovementHelper;
import com.wjbaker.gocart.ui.views.shopping_list_product_container.ShoppingListProductViewHolder;

import java.util.List;

/**
 * Created by William on 04/04/2018.<br>
 *
 * Generic class for the adapters for the shopping list.
 */
public abstract class ShoppingListProductAdapter extends RecyclerView.Adapter<ShoppingListProductViewHolder> implements ProductMovementHelper.ActionCompletedContract
{
    /**
     * Stores the list of Products to be displayed onto the RecyclerView.
     */
    private List<Product> dataset;

    /**
     * Adapter of the RecyclerView where the Product will be moved to.
     */
    private ShoppingListProductAdapter newItemContainer;

    /**
     * The MainActivity.
     */
    protected MainActivity mainActivity;

    /**
     * Creates the Adapter.
     *
     * @param dataset Initial products in the RecyclerView.
     * @param mainActivity The MainActivity.
     */
    public ShoppingListProductAdapter(List<Product> dataset, MainActivity mainActivity)
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

        ShoppingListProductViewHolder viewHolder = new ShoppingListProductViewHolder(view, this, this.newItemContainer, this.mainActivity);

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
     * Removes all Products from the RecyclerView.
     */
    public void removeAll()
    {
        this.dataset.clear();

        this.notifyDataSetChanged();
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
     * Updates the given product.
     *
     * @param product The Product that has possibly been changed.
     */
    public void updateItem(Product product)
    {
        this.notifyItemChanged(this.dataset.indexOf(product));
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
    public void setNewItemContainer(ShoppingListProductAdapter newItemContainer)
    {
        this.newItemContainer = newItemContainer;
    }

    /**
     * Calculates the total cost of the products in the list.
     *
     * @return Value of the total cost (in GBP).
     */
    public float getTotalCost()
    {
        float total = 0.0F;

        for (Product product : this.dataset)
        {
            // Calculates total cost for the current product
            // then adds it to the cumulative total for all products
            total += (product.getCost() * product.getAmount());
        }

        return total;
    }

    /**
     * Called whenever a Product is added or removed from the RecyclerView.<br>
     *
     * Allows instances to implement this method so that they have the
     * correct functionality when adding or removing products.
     *
     * @param product The Product.
     * @param isAdded Whether the Product has just been added or removed.
     */
    protected abstract void onItemChange(Product product, boolean isAdded);

    /**
     * Called when the position of the Product changes whilst it is being dragged.
     *
     * @param oldPosition Index of the position of the Product before being dragged.
     * @param newPosition Index of the current position of the Product.
     */
    @Override
    public void onViewMoved(int oldPosition, int newPosition)
    {
        // The Product being dragged
        Product product = this.dataset.get(oldPosition);

        // Move the Product
        this.dataset.remove(oldPosition);
        this.dataset.add(newPosition, product);

        // Update the position of the Product
        product.setPosition(newPosition);

        this.updateProductPositions(oldPosition, newPosition);
    }

    /**
     * Updates the positions of all Products in the RecyclerView.
     *
     * @param oldPosition Index of the position of the Product before being dragged.
     * @param newPosition Index of the current position of the Product.
     */
    public void updateProductPositions(int oldPosition, int newPosition)
    {
        ShoppingList shoppingList = ShoppingList.getInstance(this.mainActivity.getBaseContext());

        // Update the positions of the swapped Products
        shoppingList.setProductPosition(this.dataset.get(newPosition).getTPNB(), newPosition);
        shoppingList.setProductPosition(this.dataset.get(oldPosition).getTPNB(), oldPosition);

        // Update the adapter
        this.notifyItemMoved(oldPosition, newPosition);
    }
}
