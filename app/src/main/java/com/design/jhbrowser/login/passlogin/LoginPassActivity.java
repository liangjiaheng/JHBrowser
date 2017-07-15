package com.design.jhbrowser.login.passlogin;

import android.app.ActionBar;
import android.content.Intent;
import android.os.CpuUsageInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.design.jhbrowser.BaseActivity;
import com.design.jhbrowser.R;
import com.design.jhbrowser.database.bean.UserBean;
import com.design.jhbrowser.database.dao.UserDao;
import com.design.jhbrowser.database.widget.ClearEditText;
import com.design.jhbrowser.login.LoginSMSActivity;
import com.design.jhbrowser.utils.CustomToastUtils;
import com.design.jhbrowser.utils.ViewFindUtils;
import com.design.jhbrowser.utils.util.SharedPreferencesMgr;

import java.util.List;

public class LoginPassActivity extends BaseActivity implements View.OnClickListener {

    private View view;
    private Toolbar toolbar;
    private ClearEditText etUsername;
    private ClearEditText etPasword;
    private Button btnPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_pass);

        initToolbar();

        initView();
    }


    private void initToolbar() {
        view = getWindow().getDecorView();
        toolbar = ViewFindUtils.find(view, R.id.toolbar_pass);
        toolbar.setTitle("密码登录");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowTitleEnabled(true);
        }
    }

    private void initView() {
        TextView tvPhone = ViewFindUtils.find(view, R.id.tv_phone_confirm);
        etUsername = ViewFindUtils.find(view, R.id.et_username);
        etPasword = ViewFindUtils.find(view, R.id.et_password);
        tvPhone.setOnClickListener(this);
        btnPass = ViewFindUtils.find(view, R.id.btn_pass);
        btnPass.setOnClickListener(this);
        Button btnRegister = ViewFindUtils.find(view, R.id.btn_reegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginPassActivity.this, CompleteActivity.class));
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                LoginPassActivity.this.finish();
                break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_pass:
                login();
                break;
            case R.id.tv_phone_confirm:
                startActivity(new Intent(LoginPassActivity.this, LoginSMSActivity.class));
                finish();
                break;
        }
    }

    private void login() {
        String username = etUsername.getText().toString();
        String password = etPasword.getText().toString();

        List<UserBean> list = new UserDao(this).getUserLoginState(username);

        if (list.size() != 0) {
            if (list.size() == 1) {
                CustomToastUtils.showToast(this, list.get(0).getUsername());
                CustomToastUtils.showToast(this, list.get(0).getPassword());
            } else {
                CustomToastUtils.showToast(this, "F");
            }
        } else {
            CustomToastUtils.showToast(this,"用户名不存在！！");
        }

    }

}
