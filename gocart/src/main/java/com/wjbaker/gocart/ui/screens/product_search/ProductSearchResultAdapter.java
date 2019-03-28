package com.wjbaker.gocart.ui.screens.product_search;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wjbaker.gocart.R;
import com.wjbaker.gocart.model.TescoProduct;
import com.wjbaker.gocart.ui.dialogs.ProductItemInfoDialog;
import com.wjbaker.gocart.ui.views.product_item.ProductItemSearchView;
import com.wjbaker.gocart.util.NumberUtils;

import java.util.List;

public class ProductSearchResultAdapter
        extends RecyclerView.Adapter<ProductSearchResultAdapter.ProductSearchResultViewHolder> {

    private final List<TescoProduct> productSearchItems;

    private final FragmentManager fragmentManager;

    public ProductSearchResultAdapter(
            final List<TescoProduct> productSearchItems,
            final FragmentManager fragmentManager) {

        this.productSearchItems = productSearchItems;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public ProductSearchResultViewHolder onCreateViewHolder(
            @NonNull final ViewGroup parentViewGroup,
            final int position) {

        View view = LayoutInflater.from(parentViewGroup.getContext())
                .inflate(R.layout.view_product_search_result_item, parentViewGroup, false);

        return new ProductSearchResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull final ProductSearchResultViewHolder productSearchResultViewHolder,
            final int position) {

        productSearchResultViewHolder.bind(
                this.productSearchItems.get(position),
                this.fragmentManager);
    }

    @Override
    public int getItemCount() {
        return this.productSearchItems.size();
    }

    public void clearItems() {
        this.productSearchItems.clear();
        this.notifyDataSetChanged();
    }

    public void setItems(final List<TescoProduct> items) {
        this.productSearchItems.clear();
        this.productSearchItems.addAll(items);
        this.notifyDataSetChanged();
    }

    class ProductSearchResultViewHolder extends RecyclerView.ViewHolder {

        private View view;

        ProductSearchResultViewHolder(final View view) {
            super(view);
            this.view = view;
        }

        void bind(final TescoProduct productSearchItem, final FragmentManager fragmentManager) {
            TextView nameTextBox = this.view.findViewById(R.id.product_search_result_item_name);
            TextView costTextBox = this.view.findViewById(R.id.product_search_result_item_cost);
            ImageView image = this.view.findViewById(R.id.product_search_result_item_image);
            CheckBox checkBox = this.view.findViewById(R.id.product_search_result_item_checkbox);

            ProductItemSearchView productItemSearchView = this.view
                    .findViewById(R.id.product_search_result_item_content);

            nameTextBox.setText(productSearchItem.getName());
            costTextBox.setText(NumberUtils.formatNumber(productSearchItem.getPrice()));
            Picasso.get().load(productSearchItem.getImage()).into(image);

            productItemSearchView.setOnClickListener(this.onProductItemClick(fragmentManager, productSearchItem));
        }

        private View.OnClickListener onProductItemClick(
                final FragmentManager fragmentManager,
                final TescoProduct productSearchItem) {

            return v -> {
                fragmentManager
                        .beginTransaction()
                        .replace(
                                R.id.shopping_content_container,
                                ProductItemInfoDialog.createDialog(productSearchItem))
                        .addToBackStack(null)
                        .commit();
            };
        }
    }
}
