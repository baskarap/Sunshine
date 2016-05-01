package com.baskara.sunshine.app.network;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import java.util.concurrent.TimeUnit;

public class VolleyManager {

    private static final int DEFAULT_SOCKET_TIMEOUT = (int) TimeUnit.SECONDS.toMillis(3);
    private static VolleyManager instance = null;
    private RequestQueue requestQueue;

    private VolleyManager(Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }

    public static VolleyManager getInstance(Context context) {
        if (instance == null) {
            instance = new VolleyManager(context);
        }
        return instance;
    }

    public void createRequest(VolleyRequest volleyRequest, String tag) {
        JsonObjectRequest request = volleyRequest.generateRequest();
        request.setRetryPolicy(new DefaultRetryPolicy(
                DEFAULT_SOCKET_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        request.setTag(tag);
        request.setShouldCache(false);
        requestQueue.add(request);
    }
}
