package com.wjbaker.gocart.ui.views.product_item;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;

import com.wjbaker.gocart.R;
import com.wjbaker.gocart.shopping.Product;

/**
 * Created by William on 14/03/2018.
 */
public class ProductItemView extends CardView
{
    private Product product;

    public ProductItemView(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        /*TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.product_item);

        int tpnb = a.getInteger(R.styleable.product_item_tpnb, -1);

        this.setProduct(tpnb);

        a.recycle();*/
    }

    public void setProduct(Product product)
    {
        this.product = product;
    }

    public Product getProduct()
    {
        return this.product;
    }
}
