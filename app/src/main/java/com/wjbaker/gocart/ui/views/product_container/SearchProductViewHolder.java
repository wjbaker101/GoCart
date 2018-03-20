package com.wjbaker.gocart.ui.views.product_container;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by William on 15/03/2018.
 */
public class SearchProductViewHolder extends RecyclerView.ViewHolder
{
    public View searchProductView;

    public SearchProductViewHolder(View searchProductView)
    {
        super(searchProductView);

        this.searchProductView = searchProductView;
    }
}
