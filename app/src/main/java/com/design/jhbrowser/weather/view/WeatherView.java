package com.design.jhbrowser.weather.view;


import com.design.jhbrowser.weather.bean.WeatherBean;

import java.util.List;

/**
 * Created by AdamL on 2017/5/6.
 */

public interface WeatherView {

    void showProgress();

    void hideProgress();

    void showWeatherLayout();

    void setCity(String city);

    void setToday(String data);

    void setTemperature(String temperature);

    void setWind(String wind);

    void setWeather(String weather);

    void setWeatherImage(int res);

    void setWeatherData(List<WeatherBean> lists);

    void showErrorToast(String msg);
}
