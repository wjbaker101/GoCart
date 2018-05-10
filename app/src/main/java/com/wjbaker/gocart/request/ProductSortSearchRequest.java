package com.wjbaker.gocart.request;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.wjbaker.gocart.shopping.Product;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by William on 25/04/2018.
 *
 * Requests data about a specific product from the Tesco API.
 */
public class ProductSortSearchRequest
{
    private static ProductSortSearchRequest instance;

    private Context context;

    private RequestQueue queue;

    private final String url = "https://dev.tescolabs.com/product/?%s";

    public ProductSortSearchRequest(Context context)
    {
        this.context = context;

        this.queue = Volley.newRequestQueue(this.context);
    }

    /**
     * Returns the instance of the singleton.
     *
     * @param context Context of the app.
     * @return The singleton.
     */
    public static synchronized ProductSortSearchRequest getInstance(Context context)
    {
        if (instance == null)
        {
            instance = new ProductSortSearchRequest(context);
        }

        return instance;
    }

    /**
     * Does the request for information about individual products.
     *
     * @param products List of Products to get information about.
     * @param onResponse Response.
     * @param onError Error.
     */
    public void doRequest(List<Product> products, Response.Listener<JSONObject> onResponse, Response.ErrorListener onError)
    {
        // Store appended parameters
        String parameters = "";

        // Loop through each product
        // Get the tpnc and create a formatted parameter to add to the string
        for (int i = 0; i < products.size(); ++i)
        {
            parameters += "tpnc=" + products.get(i).getTPNB();

            // Add "&" to parameters except the last one
            if (i != products.size() - 1) parameters += "&";
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, String.format(this.url, parameters), null, onResponse, onError)
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                return SubscriptionKey.getHeaders();
            }
        };

        this.queue.add(request);
    }
}
