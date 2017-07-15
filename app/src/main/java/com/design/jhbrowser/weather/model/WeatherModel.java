package com.design.jhbrowser.weather.model;

import android.content.Context;

/**
 * Created by AdamL on 2017/5/7.
 */

public interface WeatherModel {
    void loadWeatherData(String cityName, WeatherModelImpl.LoadWeatherListener listener);

    void loadLocation(Context context, WeatherModelImpl.LoadLocationListener listener);


}
