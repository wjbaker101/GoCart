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
 * Created by William on 25/04/2018.
 */
public class StoreSearchRequest
{
    private static StoreSearchRequest instance;

    private Context context;

    private RequestQueue queue;

    private final String url = "https://dev.tescolabs.com/locations/search?offset=0&limit=10&sort=near:%s";

    public StoreSearchRequest(Context context)
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
    public static synchronized StoreSearchRequest getInstance(Context context)
    {
        if (instance == null)
        {
            instance = new StoreSearchRequest(context);
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
                return SubscriptionKey.getHeaders();
            }
        };

        this.queue.add(request);
    }
}
