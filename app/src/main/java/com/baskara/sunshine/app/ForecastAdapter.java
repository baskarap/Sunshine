package com.baskara.sunshine.app;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

public class ForecastAdapter extends ArrayAdapter<String> {
    public ForecastAdapter(Context context, int layoutRes, List<String> weekForecast) {
        super(context, layoutRes, weekForecast);
    }
}
