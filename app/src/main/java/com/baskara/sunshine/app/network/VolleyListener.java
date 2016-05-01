package com.baskara.sunshine.app.network;

import org.json.JSONObject;

public interface VolleyListener {

    void onSuccess(VolleyRequest volleyRequest, JSONObject response);
    void onError(VolleyRequest volleyRequest, String message);
}
