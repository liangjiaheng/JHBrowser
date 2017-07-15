package com.design.jhbrowser.weather.bean;

import java.io.Serializable;

/**
 * Created by AdamL on 2017/5/7.
 */

public class LocationBean implements Serializable {

    private String city;//城市
    private String region;//地区
    private double latitude;//经度
    private double longitude;//纬度

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
