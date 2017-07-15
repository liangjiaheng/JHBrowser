package com.design.jhbrowser.personalcenter.myitem.calendar;

import android.app.ActionBar;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.design.jhbrowser.BaseActivity;
import com.design.jhbrowser.R;
import com.design.jhbrowser.personalcenter.myitem.calendar.fragment.MyCalendarFragment;
import com.design.jhbrowser.utils.util.SharedPreferencesMgr;

/**
 * Created by AdamL on 2017/5/28.
 */

public class CalendarActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);


        initToolbar();


        MyCalendarFragment myCalenderFragment = new MyCalendarFragment(new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
            }
        });
        getSupportFragmentManager().beginTransaction().add(R.id.re_cotent, myCalenderFragment)
                .show(myCalenderFragment).commit();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("日期");
        if (SharedPreferencesMgr.getInt("theme", 0) == 1) {
            toolbar.setTitleTextColor(getResources().getColor(R.color.textcolor_main_dark));
        } else {
            toolbar.setTitleTextColor(getResources().getColor(R.color.textcolor_main_normal));
        }
        setSupportActionBar(toolbar);
        ActionBar actionbar = getActionBar();
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setDisplayShowTitleEnabled(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                CalendarActivity.this.finish();
                break;
        }
        return true;
    }
}
