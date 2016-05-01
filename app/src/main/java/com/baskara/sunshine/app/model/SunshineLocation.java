package com.baskara.sunshine.app.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class SunshineLocation extends RealmObject {

    @PrimaryKey
    private int id;
    private String name;
    private double longitude;
    private double latitude;
    private RealmList<Weather> weathers;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public RealmList<Weather> getWeathers() {
        return weathers;
    }

    public void setWeathers(RealmList<Weather> weathers) {
        this.weathers = weathers;
    }
}
