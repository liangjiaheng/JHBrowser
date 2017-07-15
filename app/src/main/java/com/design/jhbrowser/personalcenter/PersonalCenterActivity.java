package com.design.jhbrowser.personalcenter;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.design.jhbrowser.BaseActivity;
import com.design.jhbrowser.R;
import com.design.jhbrowser.login.LoginSMSActivity;
import com.design.jhbrowser.utils.ViewFindUtils;

public class PersonalCenterActivity extends BaseActivity implements View.OnClickListener {

    private View view;
    private Toolbar toolbar;
    private Button btnImmediately;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_center);
        //初始化控件
        initView();
        //关于Toolbar的设置
        aboutToolbarSetting();

    }

    /**
     * 关于Toolbar的设置
     */
    private void aboutToolbarSetting() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    /**
     * 初始化控件
     */
    private void initView() {
        view = getWindow().getDecorView();
        toolbar = ViewFindUtils.find(view, R.id.personal_toolbar);
        btnImmediately = ViewFindUtils.find(view, R.id.btn_immediately_login);
        btnImmediately.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_immediately_login:
                Intent intent = new Intent(this, LoginSMSActivity.class);
                startActivity(intent);
                break;
        }
    }
}
