package com.baskara.sunshine.app.network;

import org.json.JSONObject;

public interface VolleyListener {

    void onSuccess(VolleyRequest request, JSONObject result);
    void onError(VolleyRequest request, String errorMessage);
}
