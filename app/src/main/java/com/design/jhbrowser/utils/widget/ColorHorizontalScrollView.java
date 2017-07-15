package com.design.jhbrowser.utils.widget;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;

import com.design.jhbrowser.utils.ColorUiInterface;
import com.design.jhbrowser.utils.util.ViewAttributeUtil;

/**
 * Created by AdamL on 2017/4/24.
 */

public class ColorHorizontalScrollView extends HorizontalScrollView implements ColorUiInterface {
    private int attr_background = -1;

    public ColorHorizontalScrollView(Context context) {
        super(context);
    }

    public ColorHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.attr_background = ViewAttributeUtil.getBackgroundAttibute(attrs);
    }

    public ColorHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.attr_background = ViewAttributeUtil.getBackgroundAttibute(attrs);
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void setTheme(Resources.Theme themeId) {
        if (attr_background != -1) {
            ViewAttributeUtil.applyBackgroundDrawable(this, themeId, attr_background);
        }
    }
}
