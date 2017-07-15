package com.design.jhbrowser.fragment.navigation;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.design.jhbrowser.R;
import com.design.jhbrowser.utils.ColorUiInterface;
import com.design.jhbrowser.utils.util.ViewAttributeUtil;

/**
 * Created by zhuguohui on 2016/8/21 0021.
 */
public class MyPageIndicator extends LinearLayout implements PageGridView.PageIndicator, ColorUiInterface {

    private int attrs_background = -1;

    public MyPageIndicator(Context context) {
        super(context);
    }

    public MyPageIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.attrs_background = ViewAttributeUtil.getBackgroundAttibute(attrs);
    }

    public MyPageIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.attrs_background = ViewAttributeUtil.getBackgroundAttibute(attrs);
    }

    @Override
    public void InitIndicatorItems(int itemsNumber) {
        removeAllViews();
        for (int i = 0; i < itemsNumber; i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setImageResource(R.drawable.dot_unselected);
            imageView.setPadding(10, 0, 10, 0);
            addView(imageView);
        }
    }

    @Override
    public void onPageSelected(int pageIndex) {
        ImageView imageView = (ImageView) getChildAt(pageIndex);
        if (imageView != null) {
            imageView.setImageResource(R.drawable.dot_selected);
        }
    }

    @Override
    public void onPageUnSelected(int pageIndex) {
        ImageView imageView = (ImageView) getChildAt(pageIndex);
        if (imageView != null) {
            imageView.setImageResource(R.drawable.dot_unselected);
        }
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
