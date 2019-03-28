package com.wjbaker.gocart.client;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.wjbaker.gocart.request.SubscriptionKey;
import com.wjbaker.gocart.request.VolleyResponse;

import org.json.JSONObject;

import java.util.Map;
import java.util.function.Supplier;

public class TescoClient {

    private static final String BASE_URL = "https://dev.tescolabs.com";

    private static TescoClient instance;

    private final RequestQueue queue;

    public static synchronized TescoClient get(final Context context) {
        if (instance == null) {
            instance = new TescoClient(context);
        }

        return instance;
    }

    private TescoClient(final Context context) {
        this.queue = Volley.newRequestQueue(context);
    }

    public <T> void request(
            final String url,
            final VolleyResponse<T> volleyResponse,
            final Class<T> responseType) {

        StringRequest request = new StringRequest(
                Request.Method.GET,
                String.format("%s%s", BASE_URL, url),
                response -> volleyResponse.onSuccess(new Gson().fromJson(response, responseType)),
                error -> volleyResponse.onFailure(error)) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return SubscriptionKey.getHeaders();
            }
        };

        this.queue.add(request);
    }
}
