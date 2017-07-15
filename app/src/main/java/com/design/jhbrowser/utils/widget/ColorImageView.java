package com.design.jhbrowser.utils.widget;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.design.jhbrowser.utils.ColorUiInterface;
import com.design.jhbrowser.utils.util.ViewAttributeUtil;

/**
 * Created by AdamL on 2017/5/16.
 */

public class ColorImageView extends ImageView implements ColorUiInterface {

    private int attr_img = -1;


    public ColorImageView(Context context) {
        super(context);
    }

    public ColorImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.attr_img = ViewAttributeUtil.getSrcAttribute(attrs);
    }

    public ColorImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.attr_img = ViewAttributeUtil.getSrcAttribute(attrs);
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void setTheme(Resources.Theme themeId) {
        if (attr_img != -1) {
            ViewAttributeUtil.applyImageDrawable(this, themeId, attr_img);
        }
    }
}
