package com.wjbaker.gocart.ui.views.shopping_list_product_container;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
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
import com.wjbaker.gocart.ui.views.product_item.ProductItemShoppingView;

/**
 * Created by William on 15/03/2018.
 */
public class ShoppingListProductViewHolder extends RecyclerView.ViewHolder
{
    /**
     * Stores the View to be displayed in the RecyclerView.
     */
    private View view;

    /**
     * Adapter of the RecyclerView the Product is currently within.
     */
    private CheckedShoppingListProductAdapter oldItemContainer;

    /**
     * Adapter of the RecyclerView where the Product will be moved to.
     */
    private CheckedShoppingListProductAdapter newItemContainer;

    private final int ANIMATION_DURATION;

    /**
     * Creates the ViewHolder.
     *
     * @param view View for the Product.
     * @param oldItemContainer Adapter of the RecyclerView the Product is currently within.
     * @param newItemContainer Adapter of the RecyclerView where the Product will be moved to.
     */
    public ShoppingListProductViewHolder(View view, CheckedShoppingListProductAdapter oldItemContainer, CheckedShoppingListProductAdapter newItemContainer)
    {
        super(view);

        this.view = view;

        this.oldItemContainer = oldItemContainer;

        this.newItemContainer = newItemContainer;

        this.ANIMATION_DURATION = 200;
    }

    /**
     * Sets the content of the View.
     *
     * @param product Information about the Product of the View.
     */
    public void bind(final Product product)
    {
        TextView nameTextView = this.view.findViewById(R.id.product_item_shopping_name);
        TextView costTextView = this.view.findViewById(R.id.product_item_shopping_cost);
        ImageView imageView = this.view.findViewById(R.id.product_item_shopping_image);

        nameTextView.setText(product.getName());
        costTextView.setText(String.format("£%.2f", product.getCost()));
        Picasso.get().load(product.getImageURL()).into(imageView);

        ProductItemShoppingView productItemView = this.view.findViewById(R.id.product_item_shopping_content);
        productItemView.setProduct(product);

        this.createCheckBox(product);
    }

    /**
     * Adds the OnCheckedChange listener to the CheckBox.
     *
     * @param product The Product.
     */
    private void createCheckBox(Product product)
    {
        CheckBox checkBox = this.view.findViewById(R.id.product_item_shopping_checked);

        // Removes any existing listeners
        // Prevents the event from firing multiple times
        checkBox.setOnCheckedChangeListener(null);

        checkBox.setChecked(product.isChecked());

        checkBox.setOnCheckedChangeListener(this.getOnCheckBoxChangeListener(product));
    }

    /**
     * Creates the listener to add to the CheckBox.
     *
     * @param product The Product.
     * @return The newly created listener.
     */
    private CompoundButton.OnCheckedChangeListener getOnCheckBoxChangeListener(final Product product)
    {
        return new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, final boolean checked)
            {
                ShoppingList.getInstance(view.getContext()).setProductChecked(product.getTPNB(), checked);

                // Adds the fading out animation
                view.animate().setDuration(ANIMATION_DURATION).alpha(0).setListener(new AnimatorListenerAdapter()
                {
                    @Override
                    public void onAnimationEnd(Animator animation)
                    {
                        // Gets the product direct from the shopping list again
                        // So that when the product points to the correct object
                        Product newProduct = ShoppingList.getInstance(view.getContext()).get(product.getTPNB());

                        newItemContainer.addItem(newProduct);
                        oldItemContainer.removeItem(product);

                        // Hides the View
                        // But keeps its layout
                        view.setVisibility(View.VISIBLE);
                        view.setAlpha(0);

                        // Adds the fading in animation
                        view.animate().setDuration(ANIMATION_DURATION).alpha(1).setListener(null);
                    }
                });
            }
        };
    }
}
