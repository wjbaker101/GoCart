package com.wjbaker.gocart.ui.dashboard;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import com.wjbaker.gocart.R;
import com.wjbaker.gocart.ui.activities.MainActivity;
import com.wjbaker.gocart.ui.activities.SearchActivity;

/**
 * Created by William on 09/03/2018.
 */
public class DashboardNavigation implements BottomNavigationView.OnNavigationItemSelectedListener
{
    private Activity currentActivity;

    public DashboardNavigation(Activity currentActivity)
    {
        this.currentActivity = currentActivity;
    }

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

    private boolean onNavigateList()
    {
        if (currentActivity.getClass() == MainActivity.class) return false;

        Intent intent = new Intent(currentActivity, MainActivity.class);

        currentActivity.startActivity(intent);

        return true;
    }

    private boolean onNavigateSearch()
    {
        if (currentActivity.getClass() == SearchActivity.class) return false;

        Intent intent = new Intent(currentActivity, SearchActivity.class);

        currentActivity.startActivity(intent);

        return true;
    }

    private boolean onNavigateStores()
    {
        /*if (currentActivity.getClass() == MainActivity.class) return false;

        Intent myIntent = new Intent(currentActivity, SearchActivity.class);

        currentActivity.startActivity(myIntent);*/

        return true;
    }
}
