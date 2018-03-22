package com.wjbaker.gocart.ui.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
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
        for (final Product product : ShoppingList.getInstance(this).getProducts().values())
        {
            final View checkedItemView = getLayoutInflater().inflate(R.layout.product_item_shopping, this.checkedItemContainer, false);
            TextView nameView = checkedItemView.findViewById(R.id.product_item_shopping_name);
            ProductItemShoppingView productItemShoppingView = checkedItemView.findViewById(R.id.product_item_shopping_content);
            CheckBox checkBox = productItemShoppingView.findViewById(R.id.product_item_shopping_checked);

            nameView.setText(product.getName());
            checkBox.setChecked(product.isChecked());
            productItemShoppingView.setProduct(product);

            productItemShoppingView.addCheckBoxListener(new CompoundButton.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, final boolean checked)
                {
                    // Adds the fading out animation
                    checkedItemView.animate().setDuration(200).alpha(0).setListener(new AnimatorListenerAdapter()
                    {
                        @Override
                        public void onAnimationEnd(Animator animation)
                        {
                            ShoppingList.getInstance(getBaseContext()).setProductChecked(product.getTPNB(), checked);

                            if (checked)
                            {
                                uncheckedItemContainer.removeView(checkedItemView);
                                checkedItemContainer.addView(checkedItemView);
                            }
                            else
                            {
                                checkedItemContainer.removeView(checkedItemView);
                                uncheckedItemContainer.addView(checkedItemView);
                            }

                            checkedItemView.setVisibility(View.VISIBLE);
                            checkedItemView.setAlpha(0);

                            // Adds the fading in animation
                            checkedItemView.animate().setDuration(200).alpha(1).setListener(null);
                        }
                    });
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
