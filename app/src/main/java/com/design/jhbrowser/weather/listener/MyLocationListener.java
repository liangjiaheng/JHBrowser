package com.design.jhbrowser.weather.listener;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.design.jhbrowser.utils.DataResourcesUtils;
import com.design.jhbrowser.weather.bean.LocationBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by AdamL on 2017/5/7.
 */

public class MyLocationListener implements BDLocationListener {

    private Context mContext;
    private List<LocationBean> locationBeans;
    private LocationBean locationBean;

    public MyLocationListener(Context context) {
        this.mContext = context;
    }

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        Intent intent = new Intent();
        locationBeans = new ArrayList<>();
        locationBean = new LocationBean();
        locationBean.setCity(bdLocation.getCity());
        locationBean.setRegion(bdLocation.getDistrict());
        locationBean.setLatitude(bdLocation.getLatitude());
        locationBean.setLongitude(bdLocation.getLongitude());
        locationBeans.add(locationBean);

        intent.putExtra("flag", "navigation");
        intent.putExtra("location", (Serializable) locationBeans);
        intent.setAction(DataResourcesUtils.FRAG_NAVIGATION);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
    }
}
