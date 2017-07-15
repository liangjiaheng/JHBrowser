package com.design.jhbrowser.personalcenter.base;

import android.content.Context;
import android.util.AttributeSet;

import com.design.library.CardFrameLayout;

/**
 * Created by AdamL on 2017/5/24.
 */

public class BaseCardLayout extends CardFrameLayout {
    public BaseCardLayout(Context context) {
        this(context, null);
    }

    public BaseCardLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        onCreateView(context);
    }

    private void onCreateView(final Context context) {
        findViews();
        setViewsContent();
        setViewsListener();
    }

    /**
     * 描述：把所有View找出来
     */
    protected void findViews() {
//		View.inflate(getContext(), R.layout.view_main_item_title, this);
//		txt_title = (TextView) findViewById(R.id.txt_title);
    }

    /**
     * 描述：设置View的内容
     */
    protected void setViewsContent() {
    }

    /**
     * 描述：设置View的监控器
     */
    protected void setViewsListener() {
    }
}
