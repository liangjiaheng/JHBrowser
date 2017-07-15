package com.design.jhbrowser.lock.lockview;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.design.jhbrowser.R;
import com.design.jhbrowser.utils.ViewFindUtils;
import com.design.jhbrowser.utils.util.SharedPreferencesMgr;

public class LockSettingsActivity extends AppCompatActivity {

    private View view;
    private Lock9View lock9View;
    private Toolbar toolbar;
    private TextView tvDraw;

    private String oldPass;
    private String firstPass;
    private int STATE = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_settings);
        view = getWindow().getDecorView();
        initView();

        aboutToolbarSetting();

        aboutLockViewSetting();
    }

    public void reDraw(View view) {
        firstPass = "";
        STATE = 0;
        tvDraw.setText("重新绘制密码图案");
    }

    private void aboutLockViewSetting() {
        lock9View.setCallBack(new Lock9View.CallBack() {

            @Override
            public void onFinish(String password) {

                if (STATE == 0) {
                    firstPass = password;
                    STATE = 1;
                    tvDraw.setText("请再次绘制图案");
                } else {
                    oldPass = password;
                    if (firstPass.equals(oldPass)) {
                        tvDraw.setText("密码保存成功");
                        SharedPreferencesMgr.setString("password", oldPass);
                    } else {
                        tvDraw.setText("两次密码输入不一致，请重新绘制");
                    }
                }
            }
        });

    }

    private void initView() {
        lock9View = ViewFindUtils.find(view, R.id.lock_9_view);
        toolbar = ViewFindUtils.find(view, R.id.toolbar_lock);
        tvDraw = ViewFindUtils.find(view, R.id.draw_clear_pic);

    }

    /**
     * 关于Toolbar
     */
    private void aboutToolbarSetting() {
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.bg_personal_normal));
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("图案解锁");
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


}
