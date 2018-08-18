package com.wjbaker.gocart.ui.dashboard;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import com.wjbaker.gocart.R;
import com.wjbaker.gocart.ui.activities.MainActivity;
import com.wjbaker.gocart.ui.activities.SearchActivity;
import com.wjbaker.gocart.ui.activities.StoresActivity;

/**
 * Created by William on 09/03/2018.
 */
public class DashboardNavigation implements BottomNavigationView.OnNavigationItemSelectedListener
{
    /**
     * Stores the current activity the user is viewing.
     */
    private Activity currentActivity;

    /**
     * Stores the view for the dashboard.
     */
    private BottomNavigationView dashboardNavigationView;

    /**
     * Allows an activity to create the dashboard navigation.
     *
     * @param currentActivity The current activity.
     */
    public DashboardNavigation(Activity currentActivity)
    {
        this.currentActivity = currentActivity;

        dashboardNavigationView = this.currentActivity.findViewById(R.id.navigation);

        dashboardNavigationView.setOnNavigationItemSelectedListener(this);

        this.updateSelectedIcon();
    }

    /**
     * Updates the selected item, according to the current activity being shown.
     */
    public void updateSelectedIcon()
    {
        if (this.currentActivity.getClass() == MainActivity.class)
        {
            dashboardNavigationView.setSelectedItemId(R.id.navigation_list);
        }
        else if (this.currentActivity.getClass() == SearchActivity.class)
        {
            dashboardNavigationView.setSelectedItemId(R.id.navigation_search);
        }
        else if (this.currentActivity.getClass() == StoresActivity.class)
        {
            dashboardNavigationView.setSelectedItemId(R.id.navigation_stores);
        }
    }

    /**
     * Called when one of the menu items is pressed by the user.
     *
     * @param item The menu item selected.
     * @return Whether or not an action occurred.
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.navigation_list: return this.onNavigateList();
            case R.id.navigation_search: return this.onNavigateSearch();
            case R.id.navigation_stores: return this.onNavigateStores();
        }

        return false;
    }

    /**
     * Called when the user wants to view their shopping list.
     *
     * @return True.
     */
    private boolean onNavigateList()
    {
        this.switchActivity(MainActivity.class);

        return true;
    }

    /**
     * Called when the user wants to search for products.
     *
     * @return True.
     */
    private boolean onNavigateSearch()
    {
        this.switchActivity(SearchActivity.class);

        return true;
    }

    /**
     * Called when the user wants to view store information.
     *
     * @return True.
     */
    private boolean onNavigateStores()
    {
        this.switchActivity(StoresActivity.class);

        return true;
    }

    /**
     * Switches the app to a new activity.
     *
     * @param newClass The class of the new activity.
     */
    private void switchActivity(Class<?> newClass)
    {
        // Checks whether the current activity is the same as the new activity
        // Makes sure the current activity isn't selected again
        if (currentActivity.getClass() == newClass) return;

        Intent intent = new Intent(currentActivity, newClass);

        currentActivity.startActivity(intent);
    }
}
