package com.design.jhbrowser.utils.widget;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.design.jhbrowser.utils.ColorUiInterface;
import com.design.jhbrowser.utils.util.ViewAttributeUtil;

/**
 * Created by AdamL on 2017/4/24.
 */

public class ColorTextView extends TextView implements ColorUiInterface {

    private int attr_drawable = -1;
    private int attr_textColor = -1;

    public ColorTextView(Context context) {
        super(context);
    }

    public ColorTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.attr_drawable = ViewAttributeUtil.getBackgroundAttibute(attrs);
        this.attr_textColor = ViewAttributeUtil.getTextColorAttribute(attrs);
    }

    public ColorTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.attr_drawable = ViewAttributeUtil.getBackgroundAttibute(attrs);
        this.attr_textColor = ViewAttributeUtil.getTextColorAttribute(attrs);
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void setTheme(Resources.Theme themeId) {
        if (attr_drawable != -1) {
            ViewAttributeUtil.applyBackgroundDrawable(this, themeId, attr_drawable);
        }
        if (attr_textColor != -1) {
            ViewAttributeUtil.applyTextColor(this, themeId, attr_textColor);
        }
    }
}
