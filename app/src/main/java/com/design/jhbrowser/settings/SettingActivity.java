package com.design.jhbrowser.settings;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.ActionBarContainer;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.design.jhbrowser.BaseActivity;
import com.design.jhbrowser.R;
import com.design.jhbrowser.settings.back.BackActivity;
import com.design.jhbrowser.utils.CustomToastUtils;
import com.design.jhbrowser.utils.LogUtils;
import com.design.jhbrowser.utils.ViewFindUtils;
import com.design.jhbrowser.utils.util.SharedPreferencesMgr;
import com.iflytek.cloud.Setting;
import com.squareup.okhttp.internal.io.RealConnection;

public class SettingActivity extends BaseActivity implements View.OnClickListener {

    private View view;
    private RelativeLayout setPersonal;
    private RelativeLayout setClear;
    private RelativeLayout setAbout;
    private RelativeLayout setFeedback;
    private Toolbar toolbar;
    private int TAG = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        view = getWindow().getDecorView();
        initView();

        initToolbar();

    }

    private void initToolbar() {
        toolbar.setTitle("设置中心");
        if (SharedPreferencesMgr.getInt("theme", 0) == 1) {
            toolbar.setTitleTextColor(getResources().getColor(R.color.textcolor_main_dark));
        } else {
            toolbar.setTitleTextColor(getResources().getColor(R.color.textcolor_main_normal));
        }

        setSupportActionBar(toolbar);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                SettingActivity.this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        toolbar = ViewFindUtils.find(view, R.id.toolbar_setting);
        setPersonal = ViewFindUtils.find(view, R.id.set_personal);
        setClear = ViewFindUtils.find(view, R.id.set_clear_cache);
        setAbout = ViewFindUtils.find(view, R.id.set_about);
        setFeedback = ViewFindUtils.find(view, R.id.set_feedback);

        setPersonal.setOnClickListener(this);
        setClear.setOnClickListener(this);
        setAbout.setOnClickListener(this);
        setFeedback.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(SettingActivity.this, BackActivity.class);
        switch (v.getId()) {
            case R.id.set_personal:
                //个人设置
                TAG = 0;
                break;
            case R.id.set_clear_cache:
                //清除缓存
                TAG = 1;
                break;
            case R.id.set_about:
                //关于我们
                TAG = 2;
                break;
            case R.id.set_feedback:
                //反馈有礼
                TAG = 3;
                break;
        }
        intent.putExtra("TAG", TAG);
        startActivity(intent);
    }
}
