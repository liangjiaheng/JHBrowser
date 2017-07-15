package com.design.jhbrowser.fragment.listener;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;

import com.design.jhbrowser.MainActivity;
import com.design.jhbrowser.fragment.FragWebView;

/**
 * Created by AdamL on 2017/5/15.
 */

public class FragEditChangedListener implements TextWatcher {

    private FragWebView fragment;

    public FragEditChangedListener(FragWebView fragment) {
        this.fragment = fragment;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        fragment.beforeTextChanged();
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        fragment.onTextChanged(count);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
