package com.wjbaker.gocart.ui.views.shopping_list_product_container;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wjbaker.gocart.R;
import com.wjbaker.gocart.shopping.Product;
import com.wjbaker.gocart.shopping.ShoppingList;
import com.wjbaker.gocart.ui.activities.MainActivity;
import com.wjbaker.gocart.ui.dialogs.ProductInfoDialog;
import com.wjbaker.gocart.ui.views.product_item.ProductItemShoppingView;
import com.wjbaker.gocart.ui.views.shopping_list_product_container.adapter.ShoppingListProductAdapter;

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
    private ShoppingListProductAdapter oldItemContainer;

    /**
     * Adapter of the RecyclerView where the Product will be moved to.
     */
    private ShoppingListProductAdapter newItemContainer;

    private MainActivity mainActivity;

    private final int ANIMATION_DURATION;

    /**
     * Creates the ViewHolder.
     *
     * @param view View for the Product.
     * @param oldItemContainer Adapter of the RecyclerView the Product is currently within.
     * @param newItemContainer Adapter of the RecyclerView where the Product will be moved to.
     */
    public ShoppingListProductViewHolder(View view, ShoppingListProductAdapter oldItemContainer, ShoppingListProductAdapter newItemContainer, MainActivity mainActivity)
    {
        super(view);

        this.view = view;

        this.oldItemContainer = oldItemContainer;

        this.newItemContainer = newItemContainer;

        this.ANIMATION_DURATION = 200;

        this.mainActivity = mainActivity;
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
        TextView amountTextView = this.view.findViewById(R.id.product_item_shopping_amount);

        String costText = this.view.getContext().getResources().getString(R.string.product_item_shopping_cost);
        String amountText = this.view.getContext().getResources().getString(R.string.product_item_shopping_amount);

        // Checks whether the user wishes to purchase more than 1 Product
        // If they do, display the amount so it's visible whilst they are shopping
        if (product.getAmount() > 1)
        {
            amountTextView.setVisibility(View.VISIBLE);
            amountTextView.setText(amountText.replace("{amount}", "" + product.getAmount()));
        }
        else
        {
            amountTextView.setVisibility(View.GONE);
        }

        nameTextView.setText(product.getName());
        costTextView.setText(costText.replace("{cost}", String.format("%.2f", product.getCost())));
        Picasso.get().load(product.getImageURL()).into(imageView);

        ProductItemShoppingView productItemView = this.view.findViewById(R.id.product_item_shopping_content);
        productItemView.setProduct(product);

        productItemView.setOnClickListener(this.onProductClick(product));

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
                        newItemContainer.addItem(product);
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

    /**
     * Creates an OnClick listener for when the View is clicked.<br>
     * It will display a new dialog, showing information about the Product.
     *
     * @param product The relevant Product to display information about.
     * @return The newly created OnClick listener.
     */
    private View.OnClickListener onProductClick(final Product product)
    {
        return new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ProductInfoDialog
                    .create(mainActivity, product, view, oldItemContainer)
                    .show(mainActivity.getFragmentManager(), "product_info");
            }
        };
    }
}
