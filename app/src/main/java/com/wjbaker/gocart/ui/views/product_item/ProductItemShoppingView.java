package com.wjbaker.gocart.ui.views.product_item;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.wjbaker.gocart.R;
import com.wjbaker.gocart.shopping.ShoppingList;

/**
 * Created by William on 19/03/2018.
 */
public class ProductItemShoppingView extends ProductItemView
{
    private CheckBox checkBox;

    public ProductItemShoppingView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    /**
     * Adds the event listener for when the CheckBox checked state changes.<br>
     * Will add or remove the associated Product from the shopping list.
     */
    public void addCheckBoxListener(CompoundButton.OnCheckedChangeListener listener)
    {
        this.checkBox = this.findViewById(R.id.product_item_shopping_checked);

        this.checkBox.setOnCheckedChangeListener(listener);
    }
}
