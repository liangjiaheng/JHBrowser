package com.design.jhbrowser.search;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;

import com.design.jhbrowser.fragment.FragSearch;

/**
 * Created by AdamL on 2017/5/15.
 */

public class EditChangedListener implements TextWatcher {

    public FragSearch context;

    public EditChangedListener(FragSearch context) {
        this.context = context;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        context.beforeTextChanged();
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        context.onTextChanged(count);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
