package com.wjbaker.gocart.ui.elements.product_container;

import android.support.v7.widget.RecyclerView;

import com.wjbaker.gocart.ui.elements.ProductSearchItem;

/**
 * Created by William on 15/03/2018.
 */
public class SearchProductViewHolder extends RecyclerView.ViewHolder
{
    public ProductSearchItem searchProductView;

    public SearchProductViewHolder(ProductSearchItem searchProductView)
    {
        super(searchProductView);

        this.searchProductView = searchProductView;
    }
}
