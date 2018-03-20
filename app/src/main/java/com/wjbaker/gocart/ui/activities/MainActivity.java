package com.wjbaker.gocart.ui.activities;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wjbaker.gocart.R;
import com.wjbaker.gocart.shopping.Product;
import com.wjbaker.gocart.shopping.ShoppingList;
import com.wjbaker.gocart.ui.dashboard.DashboardNavigation;
import com.wjbaker.gocart.ui.views.product_item.ProductItemSearchView;
import com.wjbaker.gocart.ui.views.product_item.ProductItemShoppingView;

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

        this.displayUncheckedItems();
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

    private void displayUncheckedItems()
    {
        final int size = ShoppingList.getInstance(this).size();

        for (int i = 0; i < size; ++i)
        {
            /*ShoppingListItem item = new ShoppingListItem(this, null);

            final int scale = (int)(this.getResources().getDisplayMetrics().density);

            item.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int)(58 * scale)));

            item.bindProduct(ShoppingList.getInstance(this).getProduct(i));

            this.uncheckedItemContainer.addView(item);*/

            //System.out.println(ShoppingList.getInstance(this).getProduct(i));

            View item = getLayoutInflater().inflate(R.layout.product_item_shopping, this.uncheckedItemContainer, false);

            final Product product = ShoppingList.getInstance(this).getProduct(i);

            TextView productName = item.findViewById(R.id.product_item_shopping_name);
            //TextView productCost = item.findViewById(R.id.product_item_shopp_cost);
            //final ImageView image = item.findViewById(R.id.product_item_search_image);

            ProductItemShoppingView productItemView = item.findViewById(R.id.product_item_shopping_content);

            productName.setText(product.getName());
            //productCost.setText(String.format("£%.2f", product.getCost()));

            productItemView.setProduct(product);

            this.uncheckedItemContainer.addView(item);
        }

        this.uncheckedItemContainer.requestLayout();
    }
}
