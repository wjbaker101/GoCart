package com.wjbaker.gocart.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
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
     * Stores the button for the first time selection of a store.
     */
    private ImageButton selectStoreButton;

    /**
     * Stores the TextView for the TescoStore's name.
     */
    private TextView storeNameTextView;

    private ScrollView storeInformationLayout;

    private RelativeLayout storeFirstTimeLayout;

    private FloatingActionButton editStoreFloatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_store);

        this.dashboardNavigation = new DashboardNavigation(this);

        this.selectStoreButton = findViewById(R.id.button_add_store);
        this.selectStoreButton.setOnClickListener(this.createSearchStoreActivityOnClick());

        this.storeNameTextView = findViewById(R.id.store_name);

        this.storeInformationLayout = findViewById(R.id.store_info_layout);
        this.storeFirstTimeLayout = findViewById(R.id.store_firsttime_layout);

        this.editStoreFloatingActionButton = findViewById(R.id.edit_store_floating_button);
        this.editStoreFloatingActionButton.setOnClickListener(this.createSearchStoreActivityOnClick());

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
     * Create the menu on the Action Bar.<br>
     * Adds the "?" button, allowing the user to view the tutorial page.
     *
     * @param menu The current menu.
     * @return True if the menu was created.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.tutorial, menu);

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

        if (id == R.id.tutorial_open)
        {
            // Create the Intent for switching to the tutorial Activity
            // Pass "shopping-list" as the page, so the page is scrolled to the
            // relevant content to this Activity
            Intent openTutorial = new Intent(this, TutorialActivity.class);
            openTutorial.putExtra("page", "tesco-store-screen");

            this.startActivity(openTutorial);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Creates an OnClick listener for opening the SearchStore activity.
     *
     * @return OnClick listener.
     */
    private View.OnClickListener createSearchStoreActivityOnClick()
    {
        final StoresActivity thisActivity = this;

        return new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale));

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

            this.setLayoutVisibility(true);
        }
        else
        {
            this.setLayoutVisibility(false);
        }
    }

    private void setLayoutVisibility(boolean isStore)
    {
        this.storeInformationLayout.setVisibility(isStore ? View.VISIBLE : View.GONE);

        this.storeFirstTimeLayout.setVisibility(isStore ? View.GONE : View.VISIBLE);
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
        if (!dayOpeningTime.isOpen())
        {
            return "Closed";
        }
        else if (dayOpeningTime.getOpenHour() == 0 && dayOpeningTime.getCloseHour() == 2400)
        {
            return "24 Hours";
        }
        else
        {
            return String.format("%s - %s", dayOpeningTime.getFormattedOpeningTime(), dayOpeningTime.getFormattedClosingTime());
        }
    }
}
