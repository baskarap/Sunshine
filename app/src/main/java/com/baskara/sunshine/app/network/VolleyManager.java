package com.baskara.sunshine.app.network;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import java.util.concurrent.TimeUnit;

public class VolleyManager {

    private final static int DEFAULT_SOCKET_TIMEOUT = (int) TimeUnit.SECONDS.toMillis(3);
    private final static String REQUEST_TAG = "SunshineVolley";
    private final String TAG = getClass().getCanonicalName();

    private static VolleyManager instance = null;
    private RequestQueue requestQueue;

    private VolleyManager(Context context) {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context);
        }
    }

    public static VolleyManager getInstance(Context context) {
        if (instance == null) {
            instance = new VolleyManager(context);
        }
        return instance;
    }

    public void createRequest(VolleyRequest volleyRequest) {
        JsonObjectRequest request = volleyRequest.generateRequest();
        request.setRetryPolicy(new DefaultRetryPolicy(
                DEFAULT_SOCKET_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setTag(REQUEST_TAG);
        request.setShouldCache(false);
        requestQueue.add(request);
    }
}
