package com.design.jhbrowser.login.passlogin;

import android.app.ActionBar;
import android.content.Intent;
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
import com.design.jhbrowser.login.optiondate.OptionDataActivity;
import com.design.jhbrowser.utils.CustomToastUtils;
import com.design.jhbrowser.utils.ViewFindUtils;
import com.design.jhbrowser.utils.util.SharedPreferencesMgr;

public class CompleteActivity extends BaseActivity implements View.OnClickListener {

    private View view;
    private ClearEditText etUsername;
    private ClearEditText etPassword;
    private ClearEditText telPhone;
    private TextView tvData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete);

        initToolbar();

        initView();

    }

    private void initView() {

        etUsername = ViewFindUtils.find(view, R.id.cl_username);
        etPassword = ViewFindUtils.find(view, R.id.cl_password);
        telPhone = ViewFindUtils.find(view, R.id.cl_telphone);
        tvData = ViewFindUtils.find(view, R.id.cl_age);
        Button btnComplete = ViewFindUtils.find(view, R.id.btn_complete);


        tvData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CompleteActivity.this, OptionDataActivity.class));
            }
        });
        btnComplete.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        tvData.setText(SharedPreferencesMgr.getString("data", "选择日期"));
        super.onResume();
    }

    private void initToolbar() {
        view = getWindow().getDecorView();
        Toolbar toolbar = ViewFindUtils.find(view, R.id.toolbar_com);
        toolbar.setTitle("完善信息");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowTitleEnabled(true);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_complete:
                String username = etUsername.getText().toString();
                String password = etPassword.getEditableText().toString();
                String telphone = telPhone.getText().toString();
                int year = SharedPreferencesMgr.getInt("year", 2017);
                int age = 2017 - year;


                if (!username.equals("") && !password.equals("") && !telphone.equals("")) {
                    UserBean userBean = new UserBean();
                    userBean.setUsername(username);
                    userBean.setPassword(password);
                    userBean.setTelphone(telphone);
                    userBean.setAge(age + "");

                    long flag = new UserDao(this).saveUserData(userBean);
                    if (flag == 0) {
                        CustomToastUtils.showToast(this, "保存失败");
                    } else {
                        CustomToastUtils.showToast(this, "保存成功");
                    }
                } else {
                    CustomToastUtils.showToast(this, "您有内容未输入");
                }
                break;
        }

    }
}
