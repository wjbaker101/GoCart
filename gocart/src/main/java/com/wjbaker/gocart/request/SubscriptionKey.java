package com.wjbaker.gocart.request;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by William on 15/08/2018.
 */
public class SubscriptionKey
{
    public static final Map<String, String> getHeaders()
    {
        Map<String, String> params = new HashMap<>();

        params.put("Ocp-Apim-Subscription-Key", "<tescolabs-subscription-key>");

        return params;
    }
}
