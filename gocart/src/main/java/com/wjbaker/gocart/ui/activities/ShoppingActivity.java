package com.wjbaker.gocart.ui.activities;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;

import com.wjbaker.gocart.R;
import com.wjbaker.gocart.ui.fragments.LoginFragment;
import com.wjbaker.gocart.ui.screens.product_search.ProductSearchFragment;
import com.wjbaker.gocart.ui.fragments.shopping.ShoppingListFragment;

/**
 * Handles the main usage of the app, allowing the user to view their shopping list and search for
 * products they would like to add to their list. Additionally, contains the feature of viewing and
 * searching their preferred Tesco Store.
 */
public class ShoppingActivity extends GoCartActivity {

    private ShoppingListFragment shoppingListFragment;
    private ProductSearchFragment productSearchFragment;
    private LoginFragment loginFragment;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_shopping);

        if (this.getSupportActionBar() != null)
            this.getSupportActionBar().setElevation(0.0F);

        BottomNavigationView navigation = this.findViewById(R.id.shopping_navigation);

        this.shoppingListFragment = new ShoppingListFragment();
        this.productSearchFragment = new ProductSearchFragment();
        this.loginFragment = new LoginFragment();

        navigation.setOnNavigationItemSelectedListener(this.getSelectedListener());

        this.showFragment(this.shoppingListFragment);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener getSelectedListener() {
        return item -> {
            Fragment nextFragment;

            switch (item.getItemId()) {
                case R.id.shopping_navigation_list: {
                    nextFragment = this.shoppingListFragment;
                    break;
                }
                case R.id.shopping_navigation_search: {
                    nextFragment = this.productSearchFragment;
                    break;
                }
                case R.id.shopping_navigation_stores: {
                    nextFragment = this.loginFragment;
                    break;
                }
                default: {
                    return false;
                }
            }

            this.showFragment(nextFragment);

            return true;
        };
    }

    private void showFragment(final Fragment fragment) {
        this.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.shopping_content_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
