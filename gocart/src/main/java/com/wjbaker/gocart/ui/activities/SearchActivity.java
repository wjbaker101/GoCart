package com.wjbaker.gocart.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewDebug;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.wjbaker.gocart.R;
import com.wjbaker.gocart.request.ProductSearchRequest;
import com.wjbaker.gocart.request.ProductSortSearchRequest;
import com.wjbaker.gocart.shopping.Product;
import com.wjbaker.gocart.shopping.ShoppingList;
import com.wjbaker.gocart.ui.dashboard.DashboardNavigation;
import com.wjbaker.gocart.ui.views.search_product_container.SearchProductAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
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

    /**
     * Called when the activity is shown but not resumed.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);

        this.dashboardNavigation = new DashboardNavigation(this);

        this.setupViews();

        this.onEditTextEnter();

        this.setupQueue();
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
     * Adds the sort button to allow to sorting searched products.
     *
     * @param menu The current menu.
     * @return True if the menu was created.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.search_sort, menu);

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

        if (id == R.id.sort_products)
        {
            this.sortProducts();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Stores the views from the activity into the respective objects.
     */
    private void setupViews()
    {
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

        this.searchProductAdapter = new SearchProductAdapter(list, this);
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

                        tpnb = product.getInt("id");
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

    /**
     * Compares 2 Products in descending order of their health scores.
     */
    private Comparator<Product> sortProductsByHealthScore = new Comparator<Product>()
    {
        @Override
        public int compare(Product p1, Product p2)
        {
            return p2.getHealthScore() - p1.getHealthScore();
        }
    };

    /**
     * Searches a list for a Product with the given TPNC.
     *
     * @param products The list of Products to search in.
     * @param tpnc The TPNC of the Product to find.
     * @return The correct Product, or null if none is found.
     */
    private Product getProductByTPNC(List<Product> products, int tpnc)
    {
        for (Product product : products)
        {
            if (product.getTPNB() == tpnc)
            {
                return product;
            }
        }

        return null;
    }

    /**
     * Sorts the Products in the search list by the nutritional value.
     */
    private void sortProducts()
    {
        // Stops if there are no products to sort
        if (this.searchProductAdapter.getItemCount() == 0) return;

        final List<Product> products = this.searchProductAdapter.getProducts();

        setLoading(true);

        ProductSortSearchRequest.getInstance(getBaseContext()).doRequest(products, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                try
                {
                    // Get the JSONArray of Products returned in the response
                    JSONArray responseProducts = response.getJSONArray("products");

                    // Loop through each Product found
                    for (int i = 0; i < responseProducts.length(); ++i)
                    {
                        // Get the individual Product
                        JSONObject product = ((JSONObject)responseProducts.get(i));

                        // Get the characteristics of the Product
                        JSONObject productCharacteristics = product.getJSONObject("productCharacteristics");

                        // Check whether the returned information contains a health score
                        if (productCharacteristics.has("healthScore"))
                        {
                            // Set the health score to the Product found in the original array
                            // with the same TPNC as the current Product
                            getProductByTPNC(products, Integer.parseInt(product.getString("tpnc"))).setHealthScore(productCharacteristics.getInt("healthScore"));
                        }

                        // Convert the List to an Array for sorting
                        final Product[] arrayProducts = products.toArray(new Product[products.size()]);

                        // Sort the Products by health score
                        Arrays.sort(arrayProducts, sortProductsByHealthScore);

                        searchProductAdapter.removeAll();

                        for (Product newProduct : arrayProducts)
                        {
                            searchProductAdapter.addItem(newProduct);
                        }
                    }
                }
                catch (Exception e)
                {
                    //e.printStackTrace();
                }

                setLoading(false);
            }
        },
        new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                setLoading(false);
            }
        });
    }
}
