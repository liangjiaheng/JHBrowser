package com.design.jhbrowser.personalcenter.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.design.jhbrowser.R;
import com.design.jhbrowser.personalcenter.base.BaseCardLayout;

/**
 * Created by AdamL on 2017/5/24.
 */

public class DramaView extends BaseCardLayout {

    public DramaView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void findViews() {
        View.inflate(getContext(), R.layout.view_main_drama, this);
        TextView txt_title = new TextView(getContext());
        int pad = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15f, getResources().getDisplayMetrics());
        txt_title.setPadding(pad, pad, pad, pad);
        txt_title.setGravity(Gravity.CENTER_HORIZONTAL);
        txt_title.setText("内容");
        addView(txt_title, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    }


    @Override
    protected void setViewsContent() {
    }

    @Override
    protected void setViewsListener() {
    }
}
