package com.wjbaker.gocart.ui.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wjbaker.gocart.R;
import com.wjbaker.gocart.shopping.Product;
import com.wjbaker.gocart.shopping.ShoppingList;
import com.wjbaker.gocart.ui.views.shopping_list_product_container.adapter.ShoppingListProductAdapter;

/**
 * Created by William on 07/04/2018.
 */
public class ProductInfoDialog extends DialogFragment
{
    /**
     * The Product to be displayed.
     */
    private Product product;

    /**
     * The View that was clicked in order to show this dialog.
     */
    private View view;

    private ShoppingListProductAdapter shoppingListProductAdapter;

    /**
     * Sets the Product.
     *
     * @param product The Product.
     */
    public void setProduct(Product product)
    {
        this.product = product;
    }

    /**
     * Sets the View.
     *
     * @param view The View.
     */
    public void setView(View view)
    {
        this.view = view;
    }

    public void setShoppingListProductAdapter(ShoppingListProductAdapter shoppingListProductAdapter)
    {
        this.shoppingListProductAdapter = shoppingListProductAdapter;
    }

    /**
     * Creates a new dialog, specifying the information to pass into it.
     *
     * @param product The Product associated with the dialog.
     * @param view The View that was clicked in order to show this dialog.
     * @return The new Dialog.
     */
    public static ProductInfoDialog create(Product product, View view)
    {
        return create(product, view, null);
    }

    public static ProductInfoDialog create(Product product, View view, ShoppingListProductAdapter shoppingListProductAdapter)
    {
        ProductInfoDialog dialog = new ProductInfoDialog();

        dialog.setProduct(product);
        dialog.setView(view);
        dialog.setShoppingListProductAdapter(shoppingListProductAdapter);

        return dialog;
    }

    /**
     * Creates the Dialog, adding the Product information into it.
     *
     * @return The newly created Dialog.
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Initialises the builder for the dialog
        // Inflates the pre-created layout to make the Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());

        LayoutInflater inflater = this.getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.product_info_dialog, null);

        TextView nameTextView = dialogView.findViewById(R.id.product_info_dialog_name);
        TextView costTextView = dialogView.findViewById(R.id.product_info_dialog_cost);
        ImageView imageView = dialogView.findViewById(R.id.product_info_dialog_image);
        CheckBox addedCheckBox = dialogView.findViewById(R.id.product_info_dialog_added_checkbox);
        final TextView amountTextView = dialogView.findViewById(R.id.product_info_dialog_amount);
        TextView editAmountTextView = dialogView.findViewById(R.id.product_info_dialog_edit_amount);

        String nameText = getString(R.string.product_info_dialog_heading);
        String costText = getString(R.string.product_info_dialog_cost);
        String amountText = getString(R.string.product_info_amount_count);

        // Gets a larger image size by replacing the size in the URL
        String largerImage = this.product.getImageURL().replace("90x90", "225x225");

        Product productFromShoppingList = ShoppingList.getInstance(dialogView.getContext()).get(product.getTPNB());

        addedCheckBox.setChecked(productFromShoppingList != null);

        addedCheckBox.setOnCheckedChangeListener(this.getOnCheckBoxChangeListener(product));

        nameTextView.setText(nameText.replace("{product_name}", product.getName()));
        costTextView.setText(costText.replace("{cost}", String.format("%.2f", product.getCost())));
        amountTextView.setText(amountText.replace("{amount}", "" + product.getAmount()));
        Picasso.get().load(largerImage).into(imageView);

        editAmountTextView.setOnClickListener(this.onEditAmountClick(product, dialogView));

        // Start building the Dialog
        builder
            .setView(dialogView)
            .setPositiveButton("Done", this.onDone(addedCheckBox));

        return builder.create();
    }

    /**
     * Creates the listener for when the user decides to close the Dialog.
     *
     * @return OnClickListener to add to the positive button.
     */
    private DialogInterface.OnClickListener onDone(final CheckBox addedCheckBox)
    {
        return new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
                if (shoppingListProductAdapter != null)
                {
                    if (!addedCheckBox.isChecked())
                    {
                        shoppingListProductAdapter.removeItem(product);
                    }

                    shoppingListProductAdapter.updateItem(product);
                }
            }
        };
    }

    /**
     * Creates an OnClick listener for when the user wants to edit the amount of the Product.
     *
     * @param product The Product to change the amount of.
     * @param infoDialogView The View used in order to show the amount dialog.
     * @return The OnClick listener to add to the TextView.
     */
    private View.OnClickListener onEditAmountClick(final Product product, final View infoDialogView)
    {
        return new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ProductAmountDialog
                    .create(product, infoDialogView)
                    .show(getFragmentManager(), "amount_dialog");
            }
        };
    }

    /**
     * Creates a change listener for the CheckBox, that will be called whenever the user
     * adds or removes the current Product from their shopping list.
     *
     * @param product The Product to add or remove.
     * @return The OnChange listener to add to the CheckBox.
     */
    public CompoundButton.OnCheckedChangeListener getOnCheckBoxChangeListener(final Product product)
    {
        return new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked)
            {
                // Gets the CheckBox in the item in the search list
                // Will return null if not part of the search list
                CheckBox checkBox = view.findViewById(R.id.product_item_search_checked);

                // Checks whether the CheckBox exists
                // Then toggles it so that it will cause the OnChecked event to trigger
                // Otherwise it will add/remove the Product from the shopping list
                if (checkBox != null)
                {
                    checkBox.setChecked(checked);
                }
                else
                {
                    if (checked)
                    {
                        ShoppingList.getInstance(compoundButton.getContext()).addItem(product);
                    }
                    else
                    {
                        ShoppingList.getInstance(compoundButton.getContext()).removeItem(product.getTPNB());
                    }
                }
            }
        };
    }
}
