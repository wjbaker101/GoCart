package com.wjbaker.gocart.ui.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wjbaker.gocart.R;
import com.wjbaker.gocart.model.TescoProduct;
import com.wjbaker.gocart.util.NumberUtils;

public class ProductItemInfoDialog extends Fragment {

    private TescoProduct tescoProduct;

    public ProductItemInfoDialog() {
        this.tescoProduct = new TescoProduct();
        this.tescoProduct.setName("Product Title");
        this.tescoProduct.setPrice(3.50111D);
    }

    public static ProductItemInfoDialog createDialog(final TescoProduct product) {
        ProductItemInfoDialog dialog = new ProductItemInfoDialog();
        dialog.tescoProduct = product;

        return dialog;
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.dialog_product_item_info, null);

        TextView titleView = layout.findViewById(R.id.product_item_info_title);
        TextView costView = layout.findViewById(R.id.product_item_info_cost);
        ImageView imageView = layout.findViewById(R.id.product_item_info_image);
        CheckBox addedView = layout.findViewById(R.id.product_item_info_added);
        TextView amountView = layout.findViewById(R.id.product_item_info_amount);
        TextView editAmountView = layout.findViewById(R.id.product_item_info_edit_amount);

        String title = this.getString(R.string.product_item_info_title)
                .replace("{title}", this.tescoProduct.getName());

        String cost = this.getString(R.string.product_item_info_cost)
                .replace("{cost}", NumberUtils.formatNumber(this.tescoProduct.getPrice()));

        String amount = this.getString(R.string.product_item_info_amount_value)
                .replace("{amount}", "5");

        String image = this.tescoProduct.getImage().replace("90x90", "540x540");

        titleView.setText(title);
        costView.setText(cost);
        amountView.setText(amount);

        Picasso.get().load(image).into(imageView);

        System.out.println(image);

        return layout;
    }
}
