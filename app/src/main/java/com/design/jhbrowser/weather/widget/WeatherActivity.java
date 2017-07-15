package com.design.jhbrowser.weather.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.design.jhbrowser.R;
import com.design.jhbrowser.utils.ViewFindUtils;
import com.design.jhbrowser.weather.bean.WeatherBean;
import com.design.jhbrowser.weather.presenter.WeatherPresenter;
import com.design.jhbrowser.weather.presenter.WeatherPresenterImpl;
import com.design.jhbrowser.weather.view.WeatherView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AdamL on 2017/5/7.
 */

public class WeatherActivity extends AppCompatActivity implements WeatherView {

    private WeatherPresenter mWeatherPresenter;
    private TextView mTodayTV;
    private ImageView mTodayWeatherImage;
    private TextView mTodayTemperatureTV;
    private TextView mTodayWindTV;
    private TextView mTodayWeatherTV;
    private TextView mCityTV;
    private ProgressBar mProgressBar;
    private LinearLayout mWeatherLayout;
    private LinearLayout mWeatherContentLayout;
    private FrameLayout mRootLayout;
    private View view;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_weather);
        view = getWindow().getDecorView();
        initView();
    }

    private void initView() {
        mWeatherPresenter = new WeatherPresenterImpl(this.getApplication(), this);
        mTodayTV = ViewFindUtils.find(view, R.id.today);
        mTodayWeatherImage = ViewFindUtils.find(view, R.id.weatherImage);
        mTodayTemperatureTV = ViewFindUtils.find(view, R.id.weatherTemp);
        mTodayWindTV = ViewFindUtils.find(view, R.id.wind);
        mTodayWeatherTV = ViewFindUtils.find(view, R.id.weather);
        mCityTV = ViewFindUtils.find(view, R.id.city);
        mProgressBar = ViewFindUtils.find(view, R.id.progress);
        mWeatherLayout = ViewFindUtils.find(view, R.id.weather_layout);
        mWeatherContentLayout = ViewFindUtils.find(view, R.id.weather_content);
        mRootLayout = ViewFindUtils.find(view, R.id.root_layout);
        mWeatherPresenter.loadWeatherData();
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showWeatherLayout() {
        mWeatherLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void setCity(String city) {
        mCityTV.setText(city);
    }

    @Override
    public void setToday(String data) {
        mTodayTV.setText(data);
    }

    @Override
    public void setTemperature(String temperature) {
        mTodayTemperatureTV.setText(temperature);
    }

    @Override
    public void setWind(String wind) {
        mTodayWindTV.setText(wind);
    }

    @Override
    public void setWeather(String weather) {
        mTodayWeatherTV.setText(weather);
    }

    @Override
    public void setWeatherImage(int res) {
        mTodayWeatherImage.setImageResource(res);
    }

    @Override
    public void setWeatherData(List<WeatherBean> lists) {
        List<View> adapterList = new ArrayList<View>();
        for (WeatherBean weatherBean : lists) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_weather, null, false);
            TextView dateTV = (TextView) view.findViewById(R.id.date);
            ImageView todayWeatherImage = (ImageView) view.findViewById(R.id.weatherImage);
            TextView todayTemperatureTV = (TextView) view.findViewById(R.id.weatherTemp);
            TextView todayWindTV = (TextView) view.findViewById(R.id.wind);
            TextView todayWeatherTV = (TextView) view.findViewById(R.id.weather);

            dateTV.setText(weatherBean.getWeek());
            todayTemperatureTV.setText(weatherBean.getTemperature());
            todayWindTV.setText(weatherBean.getWind());
            todayWeatherTV.setText(weatherBean.getWeather());
            todayWeatherImage.setImageResource(weatherBean.getImageRes());
            mWeatherContentLayout.addView(view);
            adapterList.add(view);
        }
    }

    @Override
    public void showErrorToast(String msg) {

        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

    }
}
