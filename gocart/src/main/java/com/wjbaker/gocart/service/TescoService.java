package com.wjbaker.gocart.service;

import android.content.Context;

import com.android.volley.VolleyError;
import com.wjbaker.gocart.client.TescoClient;
import com.wjbaker.gocart.model.ProductSearchResponse;
import com.wjbaker.gocart.request.VolleyResponse;

/**
 * Handles the requests to the Tesco API.
 */
public class TescoService {

    private static final String PRODUCT_SEARCH = "/grocery/products/?query=%s&offset=%s&limit=20";
    private static final String PRODUCT_GET = "";
    private static final String STORE_SEARCH = "";

    private static TescoService instance;

    private final TescoClient tescoClient;

    private TescoService(final Context context) {
        this.tescoClient = TescoClient.get(context);
    }

    public static synchronized TescoService get(final Context context) {
        if (instance == null) {
            instance = new TescoService(context);
        }

        return instance;
    }

    public void searchProduct(
            final String product,
            final int page,
            final VolleyResponse<ProductSearchResponse> volleyResponse) {

        String url = String.format(PRODUCT_SEARCH, product, page);

        this.tescoClient.request(url, volleyResponse, ProductSearchResponse.class);
    }
}
