package com.design.jhbrowser;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.design.jhbrowser.utils.util.SharedPreferencesMgr;

/**
 * Created by AdamL on 2017/4/24.
 */

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (SharedPreferencesMgr.getInt("theme", 0) == 1) {
            setTheme(R.style.theme_2);
        } else {
            setTheme(R.style.theme_1);
        }


    }
}
