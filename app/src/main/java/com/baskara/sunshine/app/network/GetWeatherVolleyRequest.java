package com.baskara.sunshine.app.network;

public class GetWeatherVolleyRequest extends GetVolleyRequest {
    public GetWeatherVolleyRequest() {
        super("http://api.openweathermap.org/data/2.5/forecast/daily");
    }
}
