package com.wjbaker.gocart.request;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by William on 11/03/2018.
 */
public class ProductSearchRequest
{
    private static ProductSearchRequest instance;

    private Context context;

    private RequestQueue queue;

    private final String url = "https://dev.tescolabs.com/grocery/products/?query=%s&offset=0&limit=20";

    public ProductSearchRequest(Context context)
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
    public static synchronized ProductSearchRequest getInstance(Context context)
    {
        if (instance == null)
        {
            instance = new ProductSearchRequest(context);
        }

        return instance;
    }

    public void doRequest(String searchTerm, Response.Listener<JSONObject> onResponse, Response.ErrorListener onError)
    {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, String.format(this.url, searchTerm), null, onResponse, onError)
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                Map<String, String>  params = new HashMap<String, String>();

                params.put("Ocp-Apim-Subscription-Key", "5ae0e6edbc054be791b28bbe5ceae194");

                return params;
            }
        };

        this.queue.add(request);
    }
}
