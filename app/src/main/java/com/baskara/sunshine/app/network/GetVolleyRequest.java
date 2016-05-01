package com.baskara.sunshine.app.network;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;

import java.util.Map;

public class GetVolleyRequest extends VolleyRequest {

    public GetVolleyRequest(String url, Object... args) {
        super(url, args);
    }

    @Override
    public JsonObjectRequest generateRequest() {
        StringBuilder builder = new StringBuilder();
        builder.append(getUrl());
        if (hasParams()) {
            builder.append("?");
            for (Map.Entry<String, Object> param : getParams().entrySet()) {
                builder.append(param.getKey());
                builder.append("=");
                builder.append(param.getValue());
                builder.append("&");
            }
        }
        return new JsonObjectRequest(Request.Method.GET, builder.toString(), this, this);
    }
}
