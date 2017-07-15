package com.design.jhbrowser.database.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by AdamL on 2017/5/18.
 */

public class MyPagerAdapter extends FragmentPagerAdapter {

    private List<String> mTitle;
    private List<Fragment> mFragmnets;

    public MyPagerAdapter(FragmentManager fm, List<String> title, List<Fragment> fragments) {
        super(fm);
        this.mTitle = title;
        this.mFragmnets = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmnets.get(position);
    }

    @Override
    public int getCount() {
        return mFragmnets.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle.get(position);
    }
}
