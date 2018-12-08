package com.wjbaker.gocart.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wjbaker.gocart.R;
import com.wjbaker.gocart.database.ProductsLoader;
import com.wjbaker.gocart.shopping.Product;
import com.wjbaker.gocart.shopping.ShoppingList;
import com.wjbaker.gocart.ui.dashboard.DashboardNavigation;
import com.wjbaker.gocart.ui.views.shopping_list_product_container.ProductMovementHelper;
import com.wjbaker.gocart.ui.views.shopping_list_product_container.adapter.CheckedShoppingListProductAdapter;
import com.wjbaker.gocart.ui.views.shopping_list_product_container.adapter.UncheckedShoppingListProductAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    private DashboardNavigation dashboardNavigation;

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

    /**
     * Stores the View for the clickable area for the visibility of the checked items.
     */
    private LinearLayout checkedVisibilityContainer;

    /**
     * Whether or not the checked items should be shown or not.
     */
    private boolean isCheckedVisible;

    /**
     * Called when the activity is shown but not resumed.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.checkedItemsCount = 0;
        this.uncheckedItemsCount = 0;

        this.checkedTextView = findViewById(R.id.checked_counter);
        this.uncheckedTextView = findViewById(R.id.unchecked_counter);

        this.checkedVisibilityContainer = findViewById(R.id.checked_visibility_container);
        this.checkedVisibilityContainer.setOnClickListener(this.getvisiblityClickListener());

        this.isCheckedVisible = true;

        this.dashboardNavigation = new DashboardNavigation(this);

        this.setupProducts();

        this.addProducts();

        this.scrollToTop();
    }

    /**
     * Called when the activity is resumed.<br>
     * e.g. When the back button is pressed.
     */
    @Override
    protected void onResume()
    {
        super.onResume();

        this.dashboardNavigation.updateSelectedIcon();
    }

    /**
     * Create the menu on the Action Bar.<br>
     *
     * @param menu The current menu.
     * @return True if the menu was created.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        return true;
    }

    /**
     * Called when a menu option is pressed.
     *
     * @param item MenuItem that was pressed.
     * @return True if an action occurred.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    /**
     * Creates an OnClick listener for the area the user will click when they want to
     * toggle the visibility of the checked items.
     *
     * @return The OnClick listener to add to the View.
     */
    private View.OnClickListener getvisiblityClickListener()
    {
        final ImageView visiblityArrowImageView = findViewById(R.id.checked_visibility_arrow);
        final TextView checkedHiddenTextView = findViewById(R.id.heading_checked_hidden);

        return new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // Toggles the visibility of the items
                isCheckedVisible = !isCheckedVisible;

                if (isCheckedVisible)
                {
                    // Updates arrow to point upwards
                    visiblityArrowImageView.setImageDrawable(getDrawable(R.drawable.icon_arrow_up));

                    // Shows the list of items
                    // Hides the hidden message
                    checkedItemContainer.setVisibility(View.VISIBLE);
                    checkedHiddenTextView.setVisibility(View.GONE);
                }
                else
                {
                    // Updates the arrow to point downwards
                    visiblityArrowImageView.setImageDrawable(getDrawable(R.drawable.icon_arrow_down));

                    // Hides the list of items
                    // Shows the hidden message
                    checkedItemContainer.setVisibility(View.GONE);
                    checkedHiddenTextView.setVisibility(View.VISIBLE);
                }
            }
        };
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

        ProductMovementHelper productMovementHelperUnchecked = new ProductMovementHelper(this.uncheckedItemContainerAdapter);
        ItemTouchHelper touchHelperUnchecked = new ItemTouchHelper(productMovementHelperUnchecked);
        touchHelperUnchecked.attachToRecyclerView(this.uncheckedItemContainer);

        this.checkedItemContainer.setAdapter(this.checkedItemContainerAdapter);
        this.checkedItemContainer.setLayoutManager(linearLayoutManagerChecked);
        this.checkedItemContainer.setNestedScrollingEnabled(false);
        this.checkedItemContainer.setHasFixedSize(false);

        ProductMovementHelper productMovementHelperChecked = new ProductMovementHelper(this.checkedItemContainerAdapter);
        ItemTouchHelper touchHelperChecked = new ItemTouchHelper(productMovementHelperChecked);
        touchHelperChecked.attachToRecyclerView(this.checkedItemContainer);
    }

    /**
     * Compares 2 Products in ascending order of their positions.
     */
    private Comparator<Product> sortProductsByPosition = new Comparator<Product>()
    {
        @Override
        public int compare(Product p1, Product p2)
        {
            return p1.getPosition() - p2.getPosition();
        }
    };

    /**
     * Adds products from the shopping list into the activity, putting them in either
     * the checked or unchecked RecyclerViews.
     */
    private void addProducts()
    {
        // Get Products from the shopping list
        // Convert the collection into a format that can be sorted
        final Collection<Product> productsCollection = ShoppingList.getInstance(this).getProducts().values();
        final Product[] products = productsCollection.toArray(new Product[productsCollection.size()]);

        // Sort the Products
        Arrays.sort(products, sortProductsByPosition);

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

    /**
     * Adds products from the shopping list into the activity, putting them in either
     * the checked or unchecked RecyclerViews.
     */
    private void addProducts(Bundle bundle)
    {
        getSupportLoaderManager().initLoader(0, bundle, new LoaderManager.LoaderCallbacks<List<Product>>()
        {
            @Override
            public Loader<List<Product>> onCreateLoader(final int id, final Bundle args)
            {
                return new ProductsLoader(MainActivity.this);
            }

            @Override
            public void onLoadFinished(final Loader<List<Product>> loader, final List<Product> result)
            {
                if (result == null)
                    return;

                for (final Product product : result)
                {
                    if (product.isChecked())
                    {
                        checkedItemContainerAdapter.addItem(product);
                    }
                    else
                    {
                        uncheckedItemContainerAdapter.addItem(product);
                    }
                }
            }

            @Override
            public void onLoaderReset(final Loader<List<Product>> loader) {}
        });

        this.updateCounters();
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

    public void updateCounters()
    {
        String checkedTemplate = getResources().getString(R.string.checked_items_counter);
        String uncheckedTemplate = getResources().getString(R.string.unchecked_items_counter);

        String checkedText = checkedTemplate
                                .replace("{count}", "" + this.checkedItemsCount);

        String uncheckedText = uncheckedTemplate
                                .replace("{count}", "" + this.uncheckedItemsCount)
                                .replace("{total_cost}", String.format("%.2f", this.uncheckedItemContainerAdapter.getTotalCost()));

        this.checkedTextView.setText(checkedText);
        this.uncheckedTextView.setText(uncheckedText);
    }
}
