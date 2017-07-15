package com.design.jhbrowser;

import android.app.Application;

import com.design.jhbrowser.utils.util.SharedPreferencesMgr;

import org.xutils.x;

/**
 * Created by AdamL on 2017/4/24.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferencesMgr.init(this, "browserState");
        x.Ext.init(this);
    }
}
