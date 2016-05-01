package com.baskara.sunshine.app.data;

import android.content.Context;
import android.util.Log;

import com.baskara.sunshine.app.Utility;
import com.baskara.sunshine.app.model.SunshineLocation;
import com.baskara.sunshine.app.model.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;

public class RealmManager {

    private static final String DB_NAME = "sunshine";
    private RealmConfiguration config;
    private Realm realm;
    
    public RealmManager(Context context) {
        config = new RealmConfiguration.Builder(context)
                .name(DB_NAME)
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();
    }

    public Realm getRealm() {
        if (realm == null) {
            realm = Realm.getInstance(config);
        }
        return realm;
    }

    public void beginTrx() {
        getRealm().beginTransaction();
    }

    public void commitTrx() {
        getRealm().commitTransaction();
    }

    public void cancelTrx() {
        getRealm().cancelTransaction();
    }

    public void close() {
        getRealm().close();
        realm = null;
    }

    public void clearLocationAndWeather() {
        beginTrx();
        getRealm().clear(Weather.class);
        getRealm().clear(SunshineLocation.class);
        commitTrx();
    }

    public void insertLocationAndWeather(JSONObject response) {
        try {
            JSONObject entryData = response.getJSONObject("city");
            int id = entryData.getInt("id");
            String name = entryData.getString("name");
            JSONObject coordinate = entryData.getJSONObject("coord");
            double longitude = coordinate.getDouble("lon");
            double latitude = coordinate.getDouble("lat");
            String[] date = Utility.getWeatherDataFromJson(response.toString(), 7);

            RealmList<Weather> weathers = getValidWeathers(response, date);

            beginTrx();
            SunshineLocation sunshineLocation = getRealm().createObject(SunshineLocation.class);
            sunshineLocation.setId(id);
            sunshineLocation.setName(name);
            sunshineLocation.setLongitude(longitude);
            sunshineLocation.setLatitude(latitude);
            sunshineLocation.setWeathers(weathers);
            commitTrx();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private RealmList<Weather> getValidWeathers(JSONObject response, String[] date) throws JSONException{
        JSONArray jsonArray = response.getJSONArray("list");
        RealmList<Weather> weathers = new RealmList<>();
        int size = jsonArray.length();
        for (int i=0; i<size; i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            beginTrx();
            Weather weather = getRealm().createObject(Weather.class);
            weather.setDt(object.getInt("dt"));
            weather.setDay(date[i]);
            weather.setHumidity(object.getInt("humidity"));
            weather.setPressure(object.getDouble("pressure"));
            commitTrx();
            weathers.add(weather);
            Log.e("Date", date[i]);
        }
        return weathers;
    }

    public SunshineLocation getLocation() {
        return getRealm().where(SunshineLocation.class).findFirst();
    }

    public RealmResults<Weather> getWeathers() {
        return getRealm().where(Weather.class).findAll();
    }
}
