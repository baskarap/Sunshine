package com.baskara.sunshine.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.baskara.sunshine.app.network.GetWeatherVolleyRequest;
import com.baskara.sunshine.app.network.VolleyListener;
import com.baskara.sunshine.app.network.VolleyManager;
import com.baskara.sunshine.app.network.VolleyRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ForecastFragment extends Fragment {

    private final String TAG = getClass().getCanonicalName();

    private String[] fakeData = {
            "Monday",
            "Tuesday",
            "Wednesday",
            "Thursday",
            "Friday",
            "Saturday",
            "Sunday",
    };
    private ForecastAdapter forecastAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.forecast_fragment, menu);
    }

    @Override
    public void onStart() {
        super.onStart();
        updateWeather();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_refresh) {
            updateWeather();
            return true;
        }
        if (itemId == R.id.action_map) {
            openPreferredLocationInMap();
            return true;
        }
        if (itemId == R.id.action_settings) {
            Intent intent = new Intent(getActivity(), SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private String getStringLocation() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        return preferences.getString(getString(R.string.pref_key_location),
                getString(R.string.pref_defvalue_location));
    }

    private void openPreferredLocationInMap() {
        String location = getStringLocation();

        Uri geoLocation = Uri.parse("geo:0,0?").buildUpon()
                .appendQueryParameter("q", location)
                .build();

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Log.e(TAG, "Could not find location");
        }
    }

    private void updateWeather() {
        String location = getStringLocation();
        VolleyManager volleyManager = VolleyManager.getInstance(getActivity());
        GetWeatherVolleyRequest request = new GetWeatherVolleyRequest();
        request.putParams(NetworkParam.QUERY_PARAM, location);
        request.putParams(NetworkParam.FORMAT_PARAM, "json");
        request.putParams(NetworkParam.UNITS_PARAM, "metric");
        request.putParams(NetworkParam.DAYS_PARAM, 7);
        request.putParams(NetworkParam.APPID_PARAM, "fd46595ae6d61340ff374a8c836cb256");
        request.setListener(new VolleyListener() {
            @Override
            public void onSuccess(VolleyRequest request, JSONObject result) {
                Log.e(TAG, result.toString());
                try {
                    String[] weathers = WeatherFormatter.getWeatherDataFromJson(result.toString(), 7);
                    refreshAdapter(weathers);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(VolleyRequest request, String errorMessage) {
                Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
        volleyManager.createRequest(request);
    }

    private void refreshAdapter(String[] weathers) {
        if (weathers != null) {
            forecastAdapter.clear();
            for (String weather : weathers) {
                forecastAdapter.add(weather);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        List<String> weekForecast = new ArrayList<>(Arrays.asList(fakeData));
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        forecastAdapter = new ForecastAdapter(getActivity(), R.layout.list_item_forecast, weekForecast);

        ListView listView = (ListView) rootView.findViewById(R.id.listview_forecast);
        listView.setAdapter(forecastAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String forecast = forecastAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, forecast);
                startActivity(intent);
            }
        });

        return rootView;
    }
}
