package com.design.jhbrowser.login;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.design.jhbrowser.BaseActivity;
import com.design.jhbrowser.R;
import com.design.jhbrowser.login.passlogin.CompleteActivity;
import com.design.jhbrowser.login.passlogin.LoginPassActivity;
import com.design.jhbrowser.utils.CustomToastUtils;
import com.design.jhbrowser.utils.ViewFindUtils;

import org.json.JSONObject;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class LoginSMSActivity extends BaseActivity implements View.OnClickListener {

    private Button btnSendMsg, btnSubmitCode;
    private EditText etPhoneNumber, etCode;
    private int i = 60;//倒计时
    private View view;
    private TextView tvChanged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_login);
        //初始化Toolbar
        initToolbar();
        //初始化控件
        initView();
        //初始化MobSDK
        initSDK();
    }

    private void initToolbar() {
        view = getWindow().getDecorView();
        Toolbar toolbar = ViewFindUtils.find(view, R.id.toolbar_sms);
        toolbar.setTitle("短信登录");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                LoginSMSActivity.this.finish();
                break;
        }
        return true;
    }

    /**
     * 初始化mobSDK
     */
    private void initSDK() {
        // 启动短信验证sdk
        SMSSDK.initSDK(LoginSMSActivity.this, "1ddb010553832", "779484c3914e8b1d227116e4f53ffaa8");
        //initSDK方法是短信SDK的入口，需要传递您从MOB应用管理后台中注册的SMSSDK的应用AppKey和AppSecrete，如果填写错误，后续的操作都将不能进行
        EventHandler eventHandler = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handler.sendMessage(msg);
            }
        };
        SMSSDK.registerEventHandler(eventHandler);
        btnSendMsg.setOnClickListener(this);
        btnSubmitCode.setOnClickListener(this);
    }

    /**
     * 初始化控件
     */
    private void initView() {
        etPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);
        etCode = (EditText) findViewById(R.id.etCode);
        btnSendMsg = (Button) findViewById(R.id.btnSendMsg);
        btnSubmitCode = (Button) findViewById(R.id.btnSubmitCode);
        tvChanged = ViewFindUtils.find(view, R.id.tv_changed_way);
        tvChanged.setOnClickListener(this);
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == -1) {
                btnSendMsg.setText(i + " s");
            } else if (msg.what == -2) {
                btnSendMsg.setText("重新发送");
                btnSendMsg.setClickable(true);
                i = 60;
            } else {
                int event = msg.arg1;
                int result = msg.arg2;
                Object data = msg.obj;
                Log.e("asd", "event=" + event + "  result=" + result + "  ---> result=-1 success , result=0 error");
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // 短信注册成功后，返回MainActivity,然后提示
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        // 提交验证码成功,调用注册接口，之后直接登录
                        //当号码来自短信注册页面时调用登录注册接口
                        //当号码来自绑定页面时调用绑定手机号码接口

                        CustomToastUtils.showToast(getApplicationContext(), "短信验证成功");

                        startActivity(new Intent(LoginSMSActivity.this, CompleteActivity.class));

                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        CustomToastUtils.showToast(getApplicationContext(), "验证码已经发送");
                    } else {
                        ((Throwable) data).printStackTrace();
                    }
                } else if (result == SMSSDK.RESULT_ERROR) {
                    try {
                        Throwable throwable = (Throwable) data;
                        throwable.printStackTrace();
                        JSONObject object = new JSONObject(throwable.getMessage());
                        String des = object.optString("detail");//错误描述
                        int status = object.optInt("status");//错误代码
                        if (status > 0 && !TextUtils.isEmpty(des)) {
                            Log.e("asd", "des: " + des);
                            CustomToastUtils.showToast(LoginSMSActivity.this, "" + des);
                            return;
                        }
                    } catch (Exception e) {
                        //do something
                    }
                }
            }
        }
    };

    @Override
    public void onClick(View v) {
        String phoneNum = etPhoneNumber.getText().toString().trim();
        switch (v.getId()) {
            case R.id.btnSendMsg:
                if (TextUtils.isEmpty(phoneNum)) {
                    CustomToastUtils.showToast(getApplicationContext(), "手机号码不能为空");
                    return;
                }
                SMSSDK.getVerificationCode("86", phoneNum);
                btnSendMsg.setClickable(false);
                //开始倒计时
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (; i > 0; i--) {
                            handler.sendEmptyMessage(-1);
                            if (i <= 0) {
                                break;
                            }
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        handler.sendEmptyMessage(-2);
                    }
                }).start();
                break;
            case R.id.btnSubmitCode:
                String code = etCode.getText().toString().trim();
                if (TextUtils.isEmpty(phoneNum)) {
                    CustomToastUtils.showToast(getApplicationContext(), "手机号码不能为空");
                    return;
                }
                if (TextUtils.isEmpty(code)) {
                    CustomToastUtils.showToast(getApplicationContext(), "验证码不能为空");
                    return;
                }
                SMSSDK.submitVerificationCode("86", phoneNum, code);
                break;
            case R.id.tv_changed_way:
                startActivity(new Intent(LoginSMSActivity.this, LoginPassActivity.class));
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //销毁回调监听接口
        SMSSDK.unregisterAllEventHandler();
    }
}
