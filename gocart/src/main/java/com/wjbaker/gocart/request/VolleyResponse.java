package com.wjbaker.gocart.request;

import com.android.volley.VolleyError;

/**
 * Defines the callbacks used for a successful and failed request when using the Volley API.
 *
 * @param <T> The type to map the response to.
 */
public interface VolleyResponse<T> {

    void onSuccess(T response);

    void onFailure(VolleyError error);
}
