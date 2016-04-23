package com.baskara.sunshine.app.network;

import android.util.Log;

import com.android.volley.NoConnectionError;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public abstract class VolleyRequest implements Response.Listener<JSONObject>, Response.ErrorListener {

    private final String TAG = getClass().getCanonicalName();

    private String url;
    private Map<String, Object> params;
    private VolleyListener listener;

    public abstract JsonObjectRequest generateRequest();

    public VolleyRequest(String url, Object... args) {
        this.url = String.format(url, args);
    }

    public String getUrl() {
        return url;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public boolean hasParams() {
        return params != null;
    }

    public void setListener(VolleyListener listener) {
        this.listener = listener;
    }

    public void putParams(String key, Object value) {
        if (params == null) {
            params = new HashMap<>();
        }
        params.put(key, value);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        if (listener != null) {
            String message = "Something wrong happened, please try again";

            if (error instanceof TimeoutError) {
                message = "Timeout exceeded";
            } else if (error instanceof NoConnectionError) {
                message = "No internet connection";
            }

            listener.onError(this, message);
        } else {
            Log.e(TAG, "onErrorResponse: Listener is null");
        }
    }

    @Override
    public void onResponse(JSONObject response) {
        if (listener != null) {
            listener.onSuccess(this, response);
        } else {
            Log.e(TAG, "onResponse: Listener is null");
        }
    }
}
