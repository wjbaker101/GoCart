package com.wjbaker.gocart.ui.activities;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.wjbaker.gocart.R;
import com.wjbaker.gocart.request.ProductSearchRequest;
import com.wjbaker.gocart.shopping.Product;
import com.wjbaker.gocart.ui.dashboard.DashboardNavigation;
import com.wjbaker.gocart.ui.views.product_container.SearchProductAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Activity allows the user to be able to search for specific Tesco products.
 * Each product is then displayed to the user, where they can then view the cost or add it to their shopping list.
 */
public class SearchActivity extends AppCompatActivity
{
    /**
     * Stores the bottom navigation dashboard view.
     */
    private DashboardNavigation dashboardNavigation;

    /**
     * Stores the loading icon view.
     */
    private ProgressBar loadingIcon;

    /**
     * Stores the RecylerView for holding the search results.
     */
    private RecyclerView productContainer;

    private SearchProductAdapter searchProductAdapter;

    /**
     * Stores the callback when successfully receiving a response from the Tesco API.
     */
    private Response.Listener onResponse;

    /**
     * Stores the callback when an error occures after requesting the Tesco API.
     */
    private Response.ErrorListener onError;

    /**
     * Stores the textbox which the user will enter the product they would like to search for.
     */
    private EditText searchTextBox;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);

        this.setupViews();

        this.onEditTextEnter();

        this.setupQueue();
    }

    /**
     * Stores the views from the activity into the respective objects.
     */
    private void setupViews()
    {
        this.initNavigation();

        this.initProductContainer();

        this.searchTextBox = findViewById(R.id.textbox_search);

        //this.searchItemContainer = findViewById(R.id.search_item_container);

        // Sets up loading icon
        // Changes the colour of the circle to the primary colour
        // Initially hides the loading icon
        this.loadingIcon = findViewById(R.id.loading_icon);
        this.loadingIcon.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        this.setLoading(false);
    }

    /**
     * Sets up the bottom navigation dashboard.<br>
     * Sets the currently selected item to be search.
     */
    private void initNavigation()
    {
        BottomNavigationView navigation = findViewById(R.id.navigation);

        this.dashboardNavigation = new DashboardNavigation(this);

        navigation.setSelectedItemId(R.id.navigation_search);

        navigation.setOnNavigationItemSelectedListener(this.dashboardNavigation);
    }

    /**
     * Creates the RecyclerView for storing the search results.
     */
    private void initProductContainer()
    {
        this.productContainer = findViewById(R.id.product_container);

        this.productContainer.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        this.productContainer.setLayoutManager(linearLayoutManager);

        List<Product> list = new ArrayList<>();

        this.searchProductAdapter = new SearchProductAdapter(list);
        this.productContainer.setAdapter(this.searchProductAdapter);
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
     * Adds an action listener to the search textbox.<br>
     * Requests the search results after the user has finished typing.
     */
    private void onEditTextEnter()
    {
        TextView.OnEditorActionListener searchBoxActionListener = new TextView.OnEditorActionListener()
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
                    ProductSearchRequest.getInstance(getBaseContext()).doRequest(searchTerm, onResponse, onError);

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

        this.searchTextBox.setOnEditorActionListener(searchBoxActionListener);
    }

    /**
     * Creates the responses for the search request.
     */
    private void setupQueue()
    {
        this.onResponse = new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                // .getJSONArray(...) and .getJSONObject(...) throw JSONException
                try
                {
                    JSONArray results = response.getJSONObject("uk")
                                                .getJSONObject("ghs")
                                                .getJSONObject("products")
                                                .getJSONArray("results");

                    // Clears the RecyclerView
                    // Replaces existing products with the new ones
                    searchProductAdapter.removeAll();

                    Product newProduct;
                    int tpnb;
                    String name;
                    String description;
                    float cost;
                    float quantity;
                    String superDepartment;
                    String department;
                    String imageURL;

                    // Display search results
                    for (int i = 0; i < results.length(); ++i)
                    {
                        JSONObject product = (JSONObject)results.get(i);

                        tpnb = product.getInt("tpnb");
                        name = product.getString("name");
                        description = "";
                        cost = (float)product.getDouble("price");
                        quantity = (float)product.getDouble("ContentsQuantity");
                        superDepartment = product.getString("superDepartment");
                        department = product.getString("department");
                        imageURL = product.getString("image");

                        if (product.has("description"))
                        {
                            description = product.getJSONArray("description").toString();
                        }

                        newProduct = new Product(tpnb, name, description, cost, quantity, superDepartment, department, imageURL);

                        searchProductAdapter.addItem(newProduct);
                    }
                }
                catch (Exception e)
                {
                    // Do nothing
                }

                // Indicate to the user that loading has finished
                setLoading(false);
            }
        };

        this.onError = new Response.ErrorListener()
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
}
