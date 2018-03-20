package com.wjbaker.gocart.ui.activities;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wjbaker.gocart.R;
import com.wjbaker.gocart.shopping.Product;
import com.wjbaker.gocart.shopping.ShoppingList;
import com.wjbaker.gocart.ui.dashboard.DashboardNavigation;
import com.wjbaker.gocart.ui.views.product_item.ProductItemSearchView;
import com.wjbaker.gocart.ui.views.product_item.ProductItemShoppingView;

import java.util.Map;

public class MainActivity extends AppCompatActivity
{
    private DashboardNavigation dashboardNavigation = new DashboardNavigation(this);

    /**
     * TableLayout for displaying the unchecked items in the shopping list.
     */
    private LinearLayout uncheckedItemContainer;

    /**
     * TableLayout for displaying the checked items in the shopping list.
     */
    private LinearLayout checkedItemContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.initNavigation();

        this.initViews();

        this.displayItems();
    }

    private void displayItems()
    {
        this.checkedItemContainer.removeAllViews();
        this.uncheckedItemContainer.removeAllViews();

        for (final Product product : ShoppingList.getInstance(this).getProducts().values())
        {
            View checkedItemView = getLayoutInflater().inflate(R.layout.product_item_shopping, this.checkedItemContainer, false);
            TextView nameView = checkedItemView.findViewById(R.id.product_item_shopping_name);
            ProductItemShoppingView productItemShoppingView = checkedItemView.findViewById(R.id.product_item_shopping_content);
            CheckBox checkBox = productItemShoppingView.findViewById(R.id.product_item_shopping_checked);

            nameView.setText(product.getName());
            checkBox.setChecked(product.isChecked());
            productItemShoppingView.setProduct(product);

            productItemShoppingView.addCheckBoxListener(new CompoundButton.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean checked)
                {
                    product.setChecked(checked);
                    displayItems();
                }
            });

            if (product.isChecked())
            {
                this.checkedItemContainer.addView(checkedItemView);
            }
            else
            {
                this.uncheckedItemContainer.addView(checkedItemView);
            }
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }

    /**
     * Initialises the objects storing the Views on the main activity.
     */
    private void initViews()
    {
        this.uncheckedItemContainer = findViewById(R.id.unchecked_item_container);

        this.checkedItemContainer = findViewById(R.id.checked_item_container);
    }

    private void initNavigation()
    {
        BottomNavigationView navigation = (BottomNavigationView)findViewById(R.id.navigation);

        navigation.setSelectedItemId(R.id.navigation_list);

        navigation.setOnNavigationItemSelectedListener(this.dashboardNavigation);
    }
}
