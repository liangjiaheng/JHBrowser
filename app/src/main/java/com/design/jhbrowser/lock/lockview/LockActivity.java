package com.design.jhbrowser.lock.lockview;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.design.jhbrowser.BaseActivity;
import com.design.jhbrowser.R;
import com.design.jhbrowser.personalcenter.center.PersonalActivity;
import com.design.jhbrowser.utils.CustomToastUtils;
import com.design.jhbrowser.utils.ViewFindUtils;
import com.design.jhbrowser.utils.util.SharedPreferencesMgr;

public class LockActivity extends BaseActivity {

    private View view;
    private Lock9View lockView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);


        initView();

        initToolbar();

        setLockViewStart();

    }


    /**
     * 设置解锁回调
     */
    private void setLockViewStart() {
        lockView.setCallBack(new Lock9View.CallBack() {
            @Override
            public void onFinish(String password) {
                if (SharedPreferencesMgr.getString("password", "").equals(password)) {
                    startActivity(new Intent(LockActivity.this, PersonalActivity.class));
                    LockActivity.this.finish();
                } else {
                    CustomToastUtils.showToast(LockActivity.this, "密码绘制不正确！！");
                }
            }
        });
    }


    /**
     * 初始化控件
     */
    private void initView() {
        view = getWindow().getDecorView();
        lockView = ViewFindUtils.find(view, R.id.lock_view);
        toolbar = ViewFindUtils.find(view, R.id.tool_bar_lock);
    }


    /**
     * 初始化Toolbar
     */
    private void initToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                LockActivity.this.finish();
                break;
        }
        return true;
    }
}
