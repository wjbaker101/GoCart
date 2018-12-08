package com.wjbaker.gocart.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.wjbaker.gocart.R;
import com.wjbaker.gocart.request.StoreSearchRequest;
import com.wjbaker.gocart.stores.DayOpeningTime;
import com.wjbaker.gocart.stores.TescoStore;
import com.wjbaker.gocart.ui.views.store_search_container.SearchStoreAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;

public class SearchStoreActivity extends AppCompatActivity
{
    private ProgressBar loadingIcon;

    private EditText searchTextBox;

    private RecyclerView searchStoresContainer;

    private SearchStoreAdapter searchStoreAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search_store);

        this.loadingIcon = findViewById(R.id.loading_icon);
        this.setLoading(false);

        this.searchTextBox = findViewById(R.id.textbox_search);
        this.searchTextBox.setOnEditorActionListener(this.onEditTextEnter());

        this.searchStoresContainer = findViewById(R.id.stores_container);

        this.searchStoreAdapter = new SearchStoreAdapter(this);
        this.searchStoresContainer.setAdapter(this.searchStoreAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        this.searchStoresContainer.setLayoutManager(linearLayoutManager);

        // Display the back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
     * Updates whether the loading icon should be visible to the user.
     *
     * @param visible Whether the loading icon should be visible.
     */
    private void setLoading(boolean visible)
    {
        this.loadingIcon.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    /**
     * Hides the keyboard.
     */
    private void hideKeyboard()
    {
        View view = this.getCurrentFocus();

        if (view != null)
        {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * Adds an action listener to the search textbox.<br>
     * Requests the search results after the user has finished typing.
     */
    private TextView.OnEditorActionListener onEditTextEnter()
    {
        return new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent)
            {
                // URLEncoder.encode(..., ...) throws UnsupportedEncodingException
                try
                {
                    // Encodes the search term
                    // To allow a valid string in the URL
                    final String searchTerm = URLEncoder.encode(searchTextBox.getText().toString(), "UTF-8");

                    // Requests the Tesco API for search results
                    StoreSearchRequest.getInstance(getBaseContext()).doRequest(searchTerm, getOnResponse(), getOnError());

                    // Shows the loading icon
                    // So the user knows the app is searching
                    setLoading(true);

                    // Hides the keyboard so that there is more space to display products
                    hideKeyboard();
                }
                catch (Exception e)
                {
                    CharSequence message = "Unable to load products.";
                    Toast errorMessage = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
                    errorMessage.show();
                }

                return true;
            }
        };
    }

    /**
     * Creates the response for the query.
     *
     * @return The listener to add to the Volley request.
     */
    private Response.Listener getOnResponse()
    {
        return new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                // .getJSONArray(...) and .getJSONObject(...) throw JSONException
                try
                {
                    JSONArray results = response.getJSONArray("results");

                    searchStoreAdapter.removeAll();

                    for (int i = 0; i < results.length(); ++i)
                    {
                        JSONObject store = (JSONObject) results.get(i);

                        String name = store.getJSONObject("location").getString("name");
                        double longitude = store.getJSONObject("location").getJSONObject("geo").getJSONObject("coordinates").getDouble("longitude");
                        double latitude = store.getJSONObject("location").getJSONObject("geo").getJSONObject("coordinates").getDouble("latitude");
                        JSONObject openingHours = ((JSONObject)store.getJSONObject("location").getJSONArray("openingHours").get(0)).getJSONObject("standardOpeningHours");

                        // Creates the array of the opening times for each day
                        DayOpeningTime[] dayOpeningTimes = new DayOpeningTime[]
                        {
                            new DayOpeningTime(openingHours.getJSONObject("mo").getBoolean("isOpen"),
                                openingHours.getJSONObject("mo").has("open") ? openingHours.getJSONObject("mo").getInt("open") : 0000,
                                openingHours.getJSONObject("mo").has("close") ? openingHours.getJSONObject("mo").getInt("close") : 2400),

                            new DayOpeningTime(openingHours.getJSONObject("mo").getBoolean("isOpen"),
                                openingHours.getJSONObject("tu").has("open") ? openingHours.getJSONObject("tu").getInt("open") : 0000,
                                openingHours.getJSONObject("tu").has("close") ? openingHours.getJSONObject("tu").getInt("close") : 2400),

                            new DayOpeningTime(openingHours.getJSONObject("mo").getBoolean("isOpen"),
                                openingHours.getJSONObject("we").has("open") ? openingHours.getJSONObject("we").getInt("open") : 0000,
                                openingHours.getJSONObject("we").has("close") ? openingHours.getJSONObject("we").getInt("close") : 2400),

                            new DayOpeningTime(openingHours.getJSONObject("mo").getBoolean("isOpen"),
                                openingHours.getJSONObject("th").has("open") ? openingHours.getJSONObject("th").getInt("open") : 0000,
                                openingHours.getJSONObject("th").has("close") ? openingHours.getJSONObject("th").getInt("close") : 2400),

                            new DayOpeningTime(openingHours.getJSONObject("mo").getBoolean("isOpen"),
                                openingHours.getJSONObject("fr").has("open") ? openingHours.getJSONObject("fr").getInt("open") : 0000,
                                openingHours.getJSONObject("fr").has("close") ? openingHours.getJSONObject("fr").getInt("close") : 2400),

                            new DayOpeningTime(openingHours.getJSONObject("mo").getBoolean("isOpen"),
                                openingHours.getJSONObject("sa").has("open") ? openingHours.getJSONObject("sa").getInt("open") : 0000,
                                openingHours.getJSONObject("sa").has("close") ? openingHours.getJSONObject("sa").getInt("close") : 2400),

                            new DayOpeningTime(openingHours.getJSONObject("mo").getBoolean("isOpen"),
                                openingHours.getJSONObject("su").has("open") ? openingHours.getJSONObject("su").getInt("open") : 0000,
                                openingHours.getJSONObject("su").has("close") ? openingHours.getJSONObject("su").getInt("close") : 2400),
                        };

                        TescoStore newTescoStore = new TescoStore(name, longitude, latitude, dayOpeningTimes);

                        searchStoreAdapter.addItem(newTescoStore);
                    }
                }
                catch (Exception e)
                {
                    // Do nothing
                    e.printStackTrace();
                }

                // Indicate to the user that loading has finished
                setLoading(false);
            }
        };
    }

    /**
     * Creates the response for the query when an error occurs.
     *
     * @return The error listener to add to the Volley request.
     */
    private Response.ErrorListener getOnError()
    {
        return new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                // Indicate to the user that loading has finished
                setLoading(false);

                CharSequence message = "Unable to load products.";
                Toast errorMessage = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
                errorMessage.show();
            }
        };
    }
}
