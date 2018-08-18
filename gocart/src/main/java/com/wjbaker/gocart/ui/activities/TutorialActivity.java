package com.wjbaker.gocart.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;

import com.wjbaker.gocart.R;

public class TutorialActivity extends AppCompatActivity
{
    private WebView tutorialWebsite;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tutorial);

        // Display the back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.tutorialWebsite = findViewById(R.id.tutorial_website);

        // Get the passed value of page
        String page = getIntent().getStringExtra("page");
        this.navigate(page);
    }

    /**
     * Called when an option item is pressed by the user.
     *
     * @param item The item that was pressed.
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Loads the tutorial page.
     *
     * @param page The ID of an element to scroll to when the page loads.
     */
    private void navigate(String page)
    {
        // Directory of the tutorial webpage
        // Found in the assets folder
        String url = "file:///android_asset/tutorial/index.html";

        // Check whether a value was passed when the Activity switched
        if (page != null)
        {
            this.tutorialWebsite.loadUrl(String.format("%s#%s", url, page));
        }
        else
        {
            this.tutorialWebsite.loadUrl(url);
        }
    }
}
