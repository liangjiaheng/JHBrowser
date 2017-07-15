package com.design.jhbrowser.utils.widget;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.design.jhbrowser.utils.ColorUiInterface;
import com.design.jhbrowser.utils.util.ViewAttributeUtil;

/**
 * Created by AdamL on 2017/4/24.
 */

public class ColorLinearLayout extends LinearLayout implements ColorUiInterface {
    private int attrs_background = -1;

    public ColorLinearLayout(Context context) {
        super(context);
    }

    public ColorLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.attrs_background = ViewAttributeUtil.getBackgroundAttibute(attrs);

    }

    public ColorLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.attrs_background = ViewAttributeUtil.getBackgroundAttibute(attrs);
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void setTheme(Resources.Theme themeId) {
        if (attrs_background != -1) {
            ViewAttributeUtil.applyBackgroundDrawable(this, themeId, attrs_background);
        }
    }
}
