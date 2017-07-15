package com.design.jhbrowser.weather.presenter;

import android.content.Context;


import com.design.jhbrowser.utils.ToolsUtil;
import com.design.jhbrowser.weather.bean.WeatherBean;
import com.design.jhbrowser.weather.model.WeatherModel;
import com.design.jhbrowser.weather.model.WeatherModelImpl;
import com.design.jhbrowser.weather.view.WeatherView;

import java.util.List;

/**
 * Created by AdamL on 2017/5/6.
 */

public class WeatherPresenterImpl implements WeatherPresenter, WeatherModelImpl.LoadWeatherListener {


    private WeatherView mWeatherView;
    private WeatherModel mWeatherModel;
    private Context mContext;

    public WeatherPresenterImpl(Context context, WeatherView weatherView) {
        this.mContext = context;
        this.mWeatherView = weatherView;
        mWeatherModel = new WeatherModelImpl();
    }

    @Override
    public void loadWeatherData() {
        mWeatherView.showProgress();
        if (!ToolsUtil.isNetworkAvailable(mContext)) {
            mWeatherView.hideProgress();
            mWeatherView.showErrorToast("无网络连接");
            return;
        }

        WeatherModelImpl.LoadLocationListener listener = new WeatherModelImpl.LoadLocationListener() {
            @Override
            public void onSuccess(String cityName) {
                //定位成功，获取定位城市天气预报
                mWeatherView.setCity(cityName);
                mWeatherModel.loadWeatherData(cityName, WeatherPresenterImpl.this);
            }

            @Override
            public void onFailure(String msg, Exception e) {
                mWeatherView.showErrorToast("定位失败");
                mWeatherView.setCity("北京市");
                mWeatherModel.loadWeatherData("北京市", WeatherPresenterImpl.this);
            }
        };
        //获取定位信息
        mWeatherModel.loadLocation(mContext, listener);
    }

    @Override
    public void onSuccess(List<WeatherBean> list) {
        if (list != null && list.size() > 0) {
            WeatherBean todayWeather = list.remove(0);
            mWeatherView.setToday(todayWeather.getDate());
            mWeatherView.setTemperature(todayWeather.getTemperature());
            mWeatherView.setWeather(todayWeather.getWeather());
            mWeatherView.setWind(todayWeather.getWind());
            mWeatherView.setWeatherImage(todayWeather.getImageRes());
        }
        mWeatherView.setWeatherData(list);
        mWeatherView.hideProgress();
        mWeatherView.showWeatherLayout();
    }

    @Override
    public void onFailure(String msg, Exception e) {
        mWeatherView.hideProgress();
        mWeatherView.showErrorToast("获取天气数据失败");
    }
}
