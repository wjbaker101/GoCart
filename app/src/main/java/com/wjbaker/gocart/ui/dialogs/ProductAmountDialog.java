package com.wjbaker.gocart.ui.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.wjbaker.gocart.R;
import com.wjbaker.gocart.shopping.Product;

/**
 * Created by William on 08/04/2018.
 */
public class ProductAmountDialog extends DialogFragment
{
    /**
     * Stores the initial amount of the Product.
     */
    private int initialAmount = 1;

    /**
     * Stores the TextView displaying the amount in the ProductInfoDialog.<br>
     * Allows the content to be changed when the "Done" button is pressed.
     */
    private TextView amountTextView;

    public void setAmount(int amount)
    {
        this.initialAmount = amount;
    }

    public void setAmountTextView(TextView amountTextView)
    {
        this.amountTextView = amountTextView;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        // Initialises the builder for the dialog
        // Inflates the pre-created layout to make the Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());

        LayoutInflater inflater = this.getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.product_amount_dialog, null);

        NumberPicker numberPicker = dialogView.findViewById(R.id.number_picker);

        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(1000);

        numberPicker.setValue(this.initialAmount);

        // Start building the Dialog
        builder
            .setView(dialogView)
            .setPositiveButton("Done", this.onDone(numberPicker));

        return builder.create();
    }

    /**
     * Creates the listener for when the user decides to close the Dialog.
     *
     * @return OnClickListener to add to the positive button.
     */
    private DialogInterface.OnClickListener onDone(final NumberPicker numberPicker)
    {
        return new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
                if (amountTextView != null)
                {
                    amountTextView.setText("Amount: " + numberPicker.getValue());
                }
            }
        };
    }
}
