package com.design.jhbrowser.database.widget;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.design.jhbrowser.BaseActivity;
import com.design.jhbrowser.R;
import com.design.jhbrowser.database.adapter.MyPagerAdapter;
import com.design.jhbrowser.database.fragment.BookmarkFragment;
import com.design.jhbrowser.database.fragment.HistoryFragment;
import com.design.jhbrowser.utils.CustomToastUtils;
import com.design.jhbrowser.utils.DataResourcesUtils;
import com.design.jhbrowser.utils.ViewFindUtils;
import com.design.jhbrowser.utils.util.SharedPreferencesMgr;
import com.mob.tools.utils.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AdamL on 2017/5/18.
 */

public class HistoryActivity extends BaseActivity {

    private View view;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private FragmentPagerAdapter pagerAdapter;
    private List<String> title;
    private List<Fragment> fragments;
    private HistoryFragment fragHistory;
    private BookmarkFragment fragBookmark;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        view = getWindow().getDecorView();

        initView();
        //初始化Fragment集合
        initFragment();
        //设置联动
        setTabConnPager();


    }

    private void initFragment() {
        fragments = new ArrayList<>();
        fragHistory = HistoryFragment.getInstance(getApplicationContext());
        fragBookmark = BookmarkFragment.getInstance(getApplicationContext());
        fragments.add(fragBookmark);
        fragments.add(fragHistory);
    }


    /**
     * 设置Viewpager和TabLayout联动
     */
    private void setTabConnPager() {
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager(), title, fragments);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(adapter);
        changeTabTextColor();
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                changeTabTextColor();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void changeTabTextColor() {
        if (SharedPreferencesMgr.getInt("theme", 0) == 1) {
            mTabLayout.setTabTextColors(ColorStateList.valueOf(getResources().getColor(R.color.bg_main_normal)));
        } else {
            mTabLayout.setTabTextColors(ColorStateList.valueOf(getResources().getColor(R.color.bg_personal_dark)));
        }
    }

    /**
     * 初始化控件
     */
    private void initView() {
        mTabLayout = ViewFindUtils.find(view, R.id.tabLayout_history);
        mViewPager = ViewFindUtils.find(view, R.id.vp_pager);
        mToolbar = ViewFindUtils.find(view, R.id.toolbar_history);

        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
        }

        title = new ArrayList<>();
        title.add("书签");
        title.add("历史");
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
    protected void onDestroy() {
        super.onDestroy();
    }
}
