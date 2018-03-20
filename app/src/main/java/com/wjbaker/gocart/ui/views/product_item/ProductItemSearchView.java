package com.wjbaker.gocart.ui.views.product_item;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.wjbaker.gocart.R;
import com.wjbaker.gocart.shopping.ShoppingList;

/**
 * Created by William on 19/03/2018.
 */
public class ProductItemSearchView extends ProductItemView
{
    private CheckBox checkBox;

    public ProductItemSearchView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    /**
     * Adds the event listener for when the CheckBox checked state changes.<br>
     * Will add or remove the associated Product from the shopping list.
     */
    public void addCheckBoxListener()
    {
        this.checkBox = this.findViewById(R.id.product_item_search_checked);

        this.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked)
            {
                if (checked)
                {
                    ShoppingList.getInstance(getContext()).addItem(getProduct());
                }
                else
                {
                    ShoppingList.getInstance(getContext()).removeItem(getProduct().getTPNB());
                }
            }
        });
    }
}
