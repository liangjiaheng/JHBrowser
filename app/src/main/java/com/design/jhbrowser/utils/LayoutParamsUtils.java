package com.design.jhbrowser.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.design.jhbrowser.R;
import com.design.jhbrowser.utils.widget.ColorLinearLayout;

/**
 * Created by AdamL on 2017/5/13.
 */

public class LayoutParamsUtils {

    private View view;
    private DisplayMetrics metrics;
    private ImageView imgHotspot;
    private ImageView imgApplication;
    private ImageView imgBook;
    private ImageView imgVideo;
    private ImageView imgGame;
    private ImageView imgShop;

    public void searchParams(Context context, View view) {
        this.view = view;
        metrics = context.getResources().getDisplayMetrics();
        initView();
    }

    private void initView() {
        imgHotspot = ViewFindUtils.find(view, R.id.img_hotspot);
        imgApplication = ViewFindUtils.find(view, R.id.img_application);
        imgBook = ViewFindUtils.find(view, R.id.img_book);
        imgVideo = ViewFindUtils.find(view, R.id.img_video);
        imgGame = ViewFindUtils.find(view, R.id.img_game);
        imgShop = ViewFindUtils.find(view, R.id.img_shop);
        initImgParams();
    }

    private void initImgParams() {
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) imgApplication.getLayoutParams();
        layoutParams.width = width / 18;
        layoutParams.height = height / 18;

        imgApplication.setLayoutParams(layoutParams);
        imgBook.setLayoutParams(layoutParams);
        imgGame.setLayoutParams(layoutParams);
        imgHotspot.setLayoutParams(layoutParams);
        imgShop.setLayoutParams(layoutParams);
        imgVideo.setLayoutParams(layoutParams);
    }

    public static ColorLinearLayout.LayoutParams setLayoutParams(Context activity, ImageView img) {
        DisplayMetrics dm = activity.getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        ColorLinearLayout.LayoutParams params = (ColorLinearLayout.LayoutParams) img.getLayoutParams();
        params.width = width / 18;
        params.height = height / 18;
        return params;
    }


}
