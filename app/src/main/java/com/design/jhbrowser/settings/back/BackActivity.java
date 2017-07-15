package com.design.jhbrowser.settings.back;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.ActionBarContainer;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.design.jhbrowser.BaseActivity;
import com.design.jhbrowser.R;
import com.design.jhbrowser.settings.fragment.AboutFragment;
import com.design.jhbrowser.settings.fragment.ClearCacheFragment;
import com.design.jhbrowser.settings.fragment.FeedBackFragment;
import com.design.jhbrowser.settings.fragment.PersonalFragment;
import com.design.jhbrowser.utils.LogUtils;
import com.design.jhbrowser.utils.ViewFindUtils;
import com.design.jhbrowser.utils.util.SharedPreferencesMgr;

import java.util.ArrayList;
import java.util.List;

public class BackActivity extends BaseActivity {

    private View view;
    private Toolbar toolbar;
    private PersonalFragment fragPersonal;
    private AboutFragment fragAbout;
    private FeedBackFragment fragFeedback;
    private ClearCacheFragment fragClear;
    private List<Fragment> fragments;
    private int currentIndex = 0;
    private int oldIndex = 0;

    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back);
        view = getWindow().getDecorView();
        initView();
        getIntentInformation();
        aboutToolbarSetting();
    }

    private void getIntentInformation() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int index = bundle.getInt("TAG");
        LogUtils.i("asd", "sends:" + index);
        switch (index) {
            case 0:
                title = "个人设置";
                break;
            case 1:
                title = "清除缓存";
                break;
            case 2:
                title = "关于我们";
                break;
            case 3:
                title = "反馈有礼";
                break;
        }
        getFragManager(index);
    }


    private void initView() {
        toolbar = ViewFindUtils.find(view, R.id.toolbar_back);
        fragPersonal = PersonalFragment.getInstance(getApplicationContext());
        fragAbout = AboutFragment.getInstance(getApplicationContext());
        fragFeedback = FeedBackFragment.getInstance(getApplicationContext());
        fragClear = ClearCacheFragment.getInstance(getApplicationContext());
        fragments = new ArrayList<>();
        fragments.add(fragPersonal);
        fragments.add(fragClear);
        fragments.add(fragAbout);
        fragments.add(fragFeedback);
        initFragment();
    }

    private void initFragment() {
        LogUtils.i("asd", "first:" + currentIndex);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frame_layout, fragPersonal)
                .show(fragPersonal)
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
                ft.add(R.id.frame_layout, fragments.get(currentIndex));
            }
            ft.show(fragments.get(currentIndex)).commitAllowingStateLoss();
            oldIndex = currentIndex;
        }
    }

    private void aboutToolbarSetting() {
        toolbar.setTitle(title);
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
                BackActivity.this.finish();
                break;
        }
        return true;
    }
}
