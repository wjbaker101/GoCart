package com.wjbaker.gocart.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wjbaker.gocart.R;
import com.wjbaker.gocart.stores.DayOpeningTime;
import com.wjbaker.gocart.stores.TescoStore;
import com.wjbaker.gocart.stores.UserStore;
import com.wjbaker.gocart.ui.dashboard.DashboardNavigation;

public class StoresActivity extends AppCompatActivity
{
    /**
     * Stores the bottom navigation dashboard view.
     */
    private DashboardNavigation dashboardNavigation;

    /**
     * Stores the TextView for the edit button.
     */
    private TextView editStoreTextView;

    /**
     * Stores the TextView for the TescoStore's name.
     */
    private TextView storeNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_store);

        this.dashboardNavigation = new DashboardNavigation(this);

        this.editStoreTextView = findViewById(R.id.search_stores_edit);
        this.editStoreTextView.setOnClickListener(this.getSearchStoreEditOnClick());

        this.storeNameTextView = findViewById(R.id.store_name);

        this.updateStore();
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

        this.updateStore();
    }

    /**
     * Creates an OnClick listener for wen the user clicks the edit button.
     *
     * @return OnClick listener.
     */
    private View.OnClickListener getSearchStoreEditOnClick()
    {
        final StoresActivity thisActivity = this;

        return new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent switchActivity = new Intent(thisActivity, SearchStoreActivity.class);

                thisActivity.startActivity(switchActivity);
            }
        };
    }

    /**
     * Updates the content on the Activity.
     */
    private void updateStore()
    {
        TescoStore tescoStore = UserStore.getInstance(getBaseContext()).getStore();

        if (tescoStore != null)
        {
            this.storeNameTextView.setText(tescoStore.getName());

            this.displayDates(tescoStore);
        }
    }

    private void displayDates(TescoStore tescoStore)
    {
        TextView monday = findViewById(R.id.time_monday);
        TextView tuesday = findViewById(R.id.time_tuesday);
        TextView wednesday = findViewById(R.id.time_wednesday);
        TextView thursday = findViewById(R.id.time_thursday);
        TextView friday = findViewById(R.id.time_friday);
        TextView saturday = findViewById(R.id.time_saturday);
        TextView sunday = findViewById(R.id.time_sunday);

        monday.setText(getFormattedDay(tescoStore.getDayOpeningTimes()[0]));
        tuesday.setText(getFormattedDay(tescoStore.getDayOpeningTimes()[1]));
        wednesday.setText(getFormattedDay(tescoStore.getDayOpeningTimes()[2]));
        thursday.setText(getFormattedDay(tescoStore.getDayOpeningTimes()[3]));
        friday.setText(getFormattedDay(tescoStore.getDayOpeningTimes()[4]));
        saturday.setText(getFormattedDay(tescoStore.getDayOpeningTimes()[5]));
        sunday.setText(getFormattedDay(tescoStore.getDayOpeningTimes()[6]));
    }

    private String getFormattedDay(DayOpeningTime dayOpeningTime)
    {
        if (!dayOpeningTime.isOpen()) return "Closed";
        else
        {
            return (dayOpeningTime.getFormattedOpeningTime() + " - " + dayOpeningTime.getFormattedClosingTime());
        }
    }
}
