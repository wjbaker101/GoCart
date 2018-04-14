package com.wjbaker.gocart.ui.activities;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.wjbaker.gocart.R;
import com.wjbaker.gocart.shopping.Product;
import com.wjbaker.gocart.shopping.ShoppingList;
import com.wjbaker.gocart.ui.dashboard.DashboardNavigation;
import com.wjbaker.gocart.ui.views.shopping_list_product_container.adapter.CheckedShoppingListProductAdapter;
import com.wjbaker.gocart.ui.views.shopping_list_product_container.adapter.UncheckedShoppingListProductAdapter;

import java.util.ArrayList;
import java.util.Collection;

public class MainActivity extends AppCompatActivity
{
    private DashboardNavigation dashboardNavigation = new DashboardNavigation(this);

    /**
     * TableLayout for displaying the unchecked items in the shopping list.
     */
    private RecyclerView uncheckedItemContainer;

    private UncheckedShoppingListProductAdapter uncheckedItemContainerAdapter;

    /**
     * TableLayout for displaying the checked items in the shopping list.
     */
    private RecyclerView checkedItemContainer;

    private CheckedShoppingListProductAdapter checkedItemContainerAdapter;

    private int checkedItemsCount;

    private int uncheckedItemsCount;

    private TextView checkedTextView;

    private TextView uncheckedTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.checkedItemsCount = 0;
        this.uncheckedItemsCount = 0;

        this.checkedTextView = findViewById(R.id.checked_counter);
        this.uncheckedTextView = findViewById(R.id.unchecked_counter);

        this.initNavigation();

        this.setupProducts();

        this.addProducts();

        this.scrollToTop();
    }

    /**
     * Requests focus of the NestedScrollView so that it will scroll to the top.<br>
     * Without this fix, the top of the screen will be slightly lower than it should.
     */
    private void scrollToTop()
    {
        NestedScrollView mainScrollView = findViewById(R.id.main_scroll_view);

        mainScrollView.getParent().requestChildFocus(mainScrollView, mainScrollView);
    }

    /**
     * Creates the RecyclerViews for the checked and unchecked products.
     */
    private void setupProducts()
    {
        LinearLayoutManager linearLayoutManagerChecked = new LinearLayoutManager(this);
        LinearLayoutManager linearLayoutManagerUnchecked = new LinearLayoutManager(this);

        linearLayoutManagerChecked.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManagerUnchecked.setOrientation(LinearLayoutManager.VERTICAL);

        this.uncheckedItemContainer = findViewById(R.id.unchecked_item_container);
        this.checkedItemContainer = findViewById(R.id.checked_item_container);

        this.uncheckedItemContainerAdapter = new UncheckedShoppingListProductAdapter(new ArrayList<Product>(), this);
        this.checkedItemContainerAdapter = new CheckedShoppingListProductAdapter(new ArrayList<Product>(), this);

        this.checkedItemContainerAdapter.setNewItemContainer(this.uncheckedItemContainerAdapter);
        this.uncheckedItemContainerAdapter.setNewItemContainer(this.checkedItemContainerAdapter);

        this.uncheckedItemContainer.setAdapter(this.uncheckedItemContainerAdapter);
        this.uncheckedItemContainer.setLayoutManager(linearLayoutManagerUnchecked);
        this.uncheckedItemContainer.setNestedScrollingEnabled(false);
        this.uncheckedItemContainer.setHasFixedSize(false);

        this.checkedItemContainer.setAdapter(this.checkedItemContainerAdapter);
        this.checkedItemContainer.setLayoutManager(linearLayoutManagerChecked);
        this.checkedItemContainer.setNestedScrollingEnabled(false);
        this.checkedItemContainer.setHasFixedSize(false);
    }

    /**
     * Adds products from the shopping list into the activity, putting them in either
     * the checked or unchecked RecyclerViews.
     */
    private void addProducts()
    {
        final Collection<Product> products = ShoppingList.getInstance(this).getProducts().values();

        for (final Product product : products)
        {
            if (product.isChecked())
            {
                this.checkedItemContainerAdapter.addItem(product);
            }
            else
            {
                this.uncheckedItemContainerAdapter.addItem(product);
            }
        }

        this.updateCounters();
    }

    private void initNavigation()
    {
        BottomNavigationView navigation = findViewById(R.id.navigation);

        navigation.setSelectedItemId(R.id.navigation_list);

        navigation.setOnNavigationItemSelectedListener(this.dashboardNavigation);
    }

    public void incrementCheckedItemsCount(int value)
    {
        this.checkedItemsCount += value;

        this.updateCounters();
    }

    public void incrementUncheckedItemsCount(int value)
    {
        this.uncheckedItemsCount += value;

        this.updateCounters();
    }

    private void updateCounters()
    {
        String checkedTemplate = getResources().getString(R.string.checked_items_counter);
        String uncheckedTemplate = getResources().getString(R.string.unchecked_items_counter);

        this.checkedTextView.setText(checkedTemplate.replace("{count}", "" + this.checkedItemsCount));
        this.uncheckedTextView.setText(uncheckedTemplate.replace("{count}", "" + this.uncheckedItemsCount));
    }
}
