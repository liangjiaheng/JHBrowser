package com.design.jhbrowser.utils.widget;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import com.design.jhbrowser.utils.ColorUiInterface;
import com.design.jhbrowser.utils.util.ViewAttributeUtil;

/**
 * Created by AdamL on 2017/4/24.
 */

public class ColorButton extends Button implements ColorUiInterface {

    private int attrs_background = -1;
    private int attrs_textAppreance = -1;

    public ColorButton(Context context) {
        super(context);
    }

    public ColorButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.attrs_background = ViewAttributeUtil.getBackgroundAttibute(attrs);
        this.attrs_textAppreance = ViewAttributeUtil.getTextApperanceAttribute(attrs);
    }

    public ColorButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.attrs_background = ViewAttributeUtil.getBackgroundAttibute(attrs);
        this.attrs_textAppreance = ViewAttributeUtil.getTextApperanceAttribute(attrs);
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void setTheme(Resources.Theme themeId) {
        ViewAttributeUtil.applyBackgroundDrawable(this, themeId, attrs_background);
        ViewAttributeUtil.applyTextAppearance(this, themeId, attrs_textAppreance);
    }
}
