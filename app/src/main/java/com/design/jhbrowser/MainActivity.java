package com.design.jhbrowser;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.design.jhbrowser.dialogmenu.MyDialogMenuActivity;
import com.design.jhbrowser.fragment.FragNavigation;
import com.design.jhbrowser.fragment.FragSearch;
import com.design.jhbrowser.fragment.FragWebView;
import com.design.jhbrowser.lock.lockview.LockActivity;
import com.design.jhbrowser.personalcenter.center.PersonalActivity;
import com.design.jhbrowser.utils.DataResourcesUtils;
import com.design.jhbrowser.utils.ShowThemeUtils;
import com.design.jhbrowser.utils.util.SharedPreferencesMgr;
import com.design.jhbrowser.utils.widget.ColorLinearLayout;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity implements View.OnClickListener {

    private View rootView;

    @ViewInject(R.id.img_nav_back)
    private ImageView imgBack;

    @ViewInject(R.id.img_nav_forword)
    private ImageView imgForword;

    @ViewInject(R.id.img_nav_dialog)
    private ImageView imgDialog;

    @ViewInject(R.id.img_nav_main)
    private ImageView imgMain;

    @ViewInject(R.id.img_nav_manager)
    private ImageView imgManager;


    @ViewInject(R.id.navcigation_buttom)
    private ColorLinearLayout nav_buttom;

    private ArrayList<Fragment> fragments;
    private int currentIndex = 0;
    private int oldIndex = 0;

    private LocalBroadcastManager broadcastManager;
    private IntentFilter intentFilter;
    private BroadcastReceiver receiver;
    private FragNavigation fragNavigation;
    private FragWebView fragWebView;
    private FragSearch fragSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        initView();
        //设置监听器
        setNavigationListener();
        //设置BroadcastReceiver接收者
        setBroadcastReceiverSetting();
    }

    /**
     * 设置BroadcastReceiver接收者
     */
    private void setBroadcastReceiverSetting() {
        broadcastManager = LocalBroadcastManager.getInstance(this);
        intentFilter = new IntentFilter();
        intentFilter.addAction(DataResourcesUtils.MAIN_ACTIVITY);
        //通过flag判断
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle bundle = intent.getExtras();
                String flag = bundle.getString("flag");
                String value = bundle.getString("key");
                isJudgeFlag(flag, value);
            }
        };
        broadcastManager.registerReceiver(receiver, intentFilter);
    }

    /**
     * 通过判断flag值
     *
     * @param flag
     * @param value
     */
    private void isJudgeFlag(String flag, String value) {
        if (flag.equals("night")) {
            showTheme();
        } else if (flag.equals("navItem")) {
            fragWebView.startNet(value);
            getFragManager(1);
        } else if (flag.equals("firstPage")) {
            getFragManager(0);
        } else if (flag.equals("searchContent")) {
            fragWebView.startNet(value);
            getFragManager(1);
        } else if (flag.equals("search")) {
            getFragManager(2);
        } else if (flag.equals("cancel")) {
            getFragManager(0);
        } else if (flag.equals("speak")) {
            fragWebView.startNet(value);
            getFragManager(1);
        } else if (flag.equals("url")) {
            fragWebView.startNet(value);
            getFragManager(1);
        }
    }

    /**
     * 设置监听器
     */
    private void setNavigationListener() {
        imgBack.setOnClickListener(this);
        imgForword.setOnClickListener(this);
        imgDialog.setOnClickListener(this);
        imgMain.setOnClickListener(this);
        imgManager.setOnClickListener(this);
    }

    /**
     * 初始化控件
     */
    private void initView() {
        rootView = getWindow().getDecorView();
        fragNavigation = FragNavigation.getInstance(getApplicationContext());
        fragWebView = FragWebView.getInstance(getApplicationContext());
        fragSearch = FragSearch.getInstence(getApplicationContext());

        fragments = new ArrayList<>();

        fragments.add(fragNavigation);
        fragments.add(fragWebView);
        fragments.add(fragSearch);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.layout_placeholder, fragNavigation)
                .add(R.id.layout_placeholder, fragWebView)
                .add(R.id.layout_placeholder, fragSearch)
                .hide(fragSearch)
                .hide(fragWebView)
                .show(fragNavigation)
                .commit();
    }


    /**
     * 切换Fragment
     *
     * @param currentIndex
     */
    private void getFragManager(int currentIndex) {
        if (currentIndex != oldIndex) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.hide(fragments.get(oldIndex));
            if (!fragments.get(currentIndex).isAdded()) {
                ft.add(R.id.layout_placeholder, fragments.get(currentIndex));
            }
            ft.show(fragments.get(currentIndex)).commitAllowingStateLoss();
            oldIndex = currentIndex;
        }
    }

    /**
     * 显示夜间模式
     */
    private void showTheme() {
        if (SharedPreferencesMgr.getInt("theme", 0) == 1) {
            SharedPreferencesMgr.setInt("theme", 0);
            setTheme(R.style.theme_1);
        } else {
            SharedPreferencesMgr.setInt("theme", 1);
            setTheme(R.style.theme_2);
        }
        ShowThemeUtils.showTheme(rootView, getApplicationContext(), getTheme(), getResources());
    }

    @Override
    public void onClick(View view) {
        String flag = "";
        String value = "";
        switch (view.getId()) {
            case R.id.img_nav_back:
                flag = "back";
                sendBroadcastInfo(flag, value);
                break;
            case R.id.img_nav_forword:
                flag = "forword";
                sendBroadcastInfo(flag, value);
                break;
            case R.id.img_nav_dialog:
                startActivity(new Intent(MainActivity.this, MyDialogMenuActivity.class));
                break;
            case R.id.img_nav_main:
                currentIndex = 0;
                getFragManager(currentIndex);
                break;
            case R.id.img_nav_manager:
                if (SharedPreferencesMgr.getBoolean("isLock", false)) {
                    startActivity(new Intent(MainActivity.this, LockActivity.class));
                } else {
                    startActivity(new Intent(MainActivity.this, PersonalActivity.class));
                }
                break;
        }
    }

    private void sendBroadcastInfo(String flag, String value) {
        Intent intent = new Intent();
        intent.putExtra("flag", flag);
        intent.putExtra("key", value);
        intent.setAction(DataResourcesUtils.FRAG_WEBVIEW);
        LocalBroadcastManager.getInstance(getApplicationContext())
                .sendBroadcast(intent);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Fragment mCurrentFragment = fragments.get(1);
        if (mCurrentFragment instanceof FragWebView) {
            ((FragWebView) mCurrentFragment).onKeyDown(keyCode, event);
            return true;
        }
        return false;
    }
}
