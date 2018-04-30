package com.wjbaker.gocart.ui.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.wjbaker.gocart.R;
import com.wjbaker.gocart.stores.TescoStore;
import com.wjbaker.gocart.stores.UserStore;

/**
 * Created by William on 28/04/2018.
 */
public class StoreSelectDialog extends DialogFragment
{
    private TescoStore tescoStore;

    public void setTescoStore(TescoStore tescoStore)
    {
        this.tescoStore = tescoStore;
    }

    public static StoreSelectDialog create(TescoStore tescoStore)
    {
        StoreSelectDialog dialog = new StoreSelectDialog();

        dialog.setTescoStore(tescoStore);

        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        // Initialises the builder for the dialog
        // Inflates the pre-created layout to make the Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());

        LayoutInflater inflater = this.getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.store_select_dialog, null);

        TextView headingTextView = dialogView.findViewById(R.id.store_select_dialog_heading);
        String headingTextTemplate = getResources().getString(R.string.store_select_dialog_heading);

        headingTextView.setText(headingTextTemplate.replace("{store_name}", this.tescoStore.getName()));

        // Start building the Dialog
        builder
            .setView(dialogView)
            .setPositiveButton("Select", this.getOnSelectListener(this.tescoStore))
            .setNegativeButton("Cancel", null);

        return builder.create();
    }

    /**
     * Creates an OnClick listener for the positive button of the dialog.<br>
     * Allows the user to confirm their choice of a new TescoStore.
     *
     * @param tescoStore The currently selected TescoStore.
     * @return Onclick listener.
     */
    private DialogInterface.OnClickListener getOnSelectListener(final TescoStore tescoStore)
    {
        return new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                UserStore.getInstance(getActivity().getBaseContext()).setStore(tescoStore);
            }
        };
    }
}
