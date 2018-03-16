package com.wjbaker.gocart.ui.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.wjbaker.gocart.R;
import com.wjbaker.gocart.shopping.ShoppingList;
import com.wjbaker.gocart.ui.dashboard.DashboardNavigation;
import com.wjbaker.gocart.ui.elements.ShoppingListItem;

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
        final int size = ShoppingList.getInstance().size();

        for (int i = 0; i < size; ++i)
        {
            ShoppingListItem item = new ShoppingListItem(this, null);

            final int scale = (int)(this.getResources().getDisplayMetrics().density);

            item.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int)(58 * scale)));

            item.bindProduct(ShoppingList.getInstance().getProduct(i));

            this.uncheckedItemContainer.addView(item);

            System.out.println(ShoppingList.getInstance().getProduct(i));
        }

        this.uncheckedItemContainer.requestLayout();
    }
}
