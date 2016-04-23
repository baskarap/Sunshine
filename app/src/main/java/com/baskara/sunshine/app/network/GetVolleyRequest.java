package com.baskara.sunshine.app.network;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;

import java.util.Map;

public class GetVolleyRequest extends VolleyRequest {

    public GetVolleyRequest(String url) {
        super(url);
    }

    @Override
    public JsonObjectRequest generateRequest() {
        StringBuilder builder = new StringBuilder();
        builder.append(getUrl());
        if (hasParams()) {
            builder.append("?");
            for (Map.Entry<String, Object> entry : getParams().entrySet()) {
                builder.append(entry.getKey());
                builder.append("=");
                builder.append(entry.getValue());
                builder.append("&");
            }
        }
        return new JsonObjectRequest(Request.Method.GET, builder.toString(), GetVolleyRequest.this, GetVolleyRequest.this);
    }
}
