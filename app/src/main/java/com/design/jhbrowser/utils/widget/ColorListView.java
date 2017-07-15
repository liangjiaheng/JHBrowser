package com.design.jhbrowser.utils.widget;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

import com.design.jhbrowser.utils.ColorUiInterface;
import com.design.jhbrowser.utils.util.ViewAttributeUtil;

/**
 * Created by AdamL on 2017/4/24.
 */

public class ColorListView extends ListView implements ColorUiInterface {

    private int attrs_img = -1;

    public ColorListView(Context context) {
        super(context);
    }

    public ColorListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.attrs_img = ViewAttributeUtil.getSrcAttribute(attrs);
    }

    public ColorListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.attrs_img = ViewAttributeUtil.getSrcAttribute(attrs);
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void setTheme(Resources.Theme themeId) {
        if (attrs_img != -1) {
            ViewAttributeUtil.applyImageDrawable(this, themeId, attrs_img);
        }
    }
}
