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

    /**
     * Creates a new dialog, specifying the information to pass into it.
     *
     * @param product The Product associated with the dialog.
     * @param view The View that was clicked in order to show this dialog.
     * @return The new Dialog.
     */
    public static ProductInfoDialog create(Product product, View view)
    {
        ProductInfoDialog dialog = new ProductInfoDialog();

        dialog.setProduct(product);
        dialog.setView(view);

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

        // Checks whether there is a Product assigned to the Dialog
        // In the unlikely event that a Product has not been set
        // Prevents the app from crashing with a NullPointerException
        if (this.product != null)
        {
            TextView nameTextView = dialogView.findViewById(R.id.product_info_dialog_name);
            TextView costTextView = dialogView.findViewById(R.id.product_info_dialog_cost);
            ImageView imageView = dialogView.findViewById(R.id.product_info_dialog_image);
            CheckBox addedCheckBox = dialogView.findViewById(R.id.product_info_dialog_added_checkbox);
            final TextView amountTextView = dialogView.findViewById(R.id.product_info_dialog_amount);
            TextView editAmountTextView = dialogView.findViewById(R.id.product_info_dialog_edit_amount);

            String nameText = getString(R.string.product_info_dialog_heading);
            String costText = getString(R.string.product_info_dialog_cost);

            // Gets a larger image size by replacing the size in the URL
            String largerImage = this.product.getImageURL().replace("90x90", "225x225");

            Product productFromShoppingList = ShoppingList.getInstance(dialogView.getContext()).get(product.getTPNB());

            addedCheckBox.setChecked(productFromShoppingList != null);

            addedCheckBox.setOnCheckedChangeListener(this.getOnCheckBoxChangeListener(product));

            nameTextView.setText(nameText.replace("{product_name}", product.getName()));
            costTextView.setText(costText.replace("{cost}", String.format("%.2f", product.getCost())));
            Picasso.get().load(largerImage).into(imageView);

            editAmountTextView.setOnClickListener(this.onEditAmountClick(amountTextView));
        }

        // Start building the Dialog
        builder
            .setView(dialogView)
            .setPositiveButton("Done", this.onDone());

        return builder.create();
    }

    /**
     * Creates the listener for when the user decides to close the Dialog.
     *
     * @return OnClickListener to add to the positive button.
     */
    private DialogInterface.OnClickListener onDone()
    {
        return new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
                System.out.println("Pressed OK!");
            }
        };
    }

    /**
     * Creates the OnClickListener for when the user wishes to change the amount of the Product
     * they would like.
     *
     * @param amountTextView TextView to update.
     * @return The OnClickListener.
     */
    private View.OnClickListener onEditAmountClick(final TextView amountTextView)
    {
        return new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // Remove amount dialog for now
                // Until Amount attribute is added to products

                /*ProductAmountDialog productAmountDialog = new ProductAmountDialog();

                productAmountDialog.setAmountTextView(amountTextView);
                productAmountDialog.setAmount(4);

                productAmountDialog.show(getFragmentManager(), "amount_dialog");*/
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
