package com.design.jhbrowser.utils;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.LinearLayout;

import com.design.jhbrowser.R;

/**
 * Created by AdamL on 2017/5/13.
 */

public class SearchTagUtils implements View.OnClickListener {
    private LinearLayout layoutHotSpot1;
    private LinearLayout laytoutApplication;
    private LinearLayout layoutBook;
    private LinearLayout layoutVideo;
    private LinearLayout layoutGame;
    private LinearLayout layoutShop;
    private Context context;

    public void getSearchTag(Context context, View view) {
        this.context = context;

        layoutHotSpot1 = ViewFindUtils.find(view, R.id.layout_hotspot);
        laytoutApplication = ViewFindUtils.find(view, R.id.layout_application);
        layoutBook = ViewFindUtils.find(view, R.id.layout_book);
        layoutVideo = ViewFindUtils.find(view, R.id.layout_video);
        layoutGame = ViewFindUtils.find(view, R.id.layout_game);
        layoutShop = ViewFindUtils.find(view, R.id.layout_shop);

        layoutBook.setOnClickListener(this);
        layoutGame.setOnClickListener(this);
        laytoutApplication.setOnClickListener(this);
        layoutHotSpot1.setOnClickListener(this);
        layoutVideo.setOnClickListener(this);
        layoutShop.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        String TAG = "";
        switch (v.getId()) {
            case R.id.layout_hotspot:
                TAG = "热点";
                break;
            case R.id.layout_application:
                TAG = "应用";
                break;
            case R.id.layout_book:
                TAG = "小说";
                break;
            case R.id.layout_game:
                TAG = "游戏";
                break;
            case R.id.layout_shop:
                TAG = "购物";
                break;
            case R.id.layout_video:
                TAG = "视频";
                break;
        }
        intent.putExtra("flag", "classify");
        intent.putExtra("search", TAG);
        intent.setAction(DataResourcesUtils.SEARCH_ACTIVITY);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
}
