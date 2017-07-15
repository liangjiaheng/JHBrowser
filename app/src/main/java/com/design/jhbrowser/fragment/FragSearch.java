package com.design.jhbrowser.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.design.jhbrowser.R;
import com.design.jhbrowser.search.EditChangedListener;
import com.design.jhbrowser.utils.DataResourcesUtils;
import com.design.jhbrowser.utils.LayoutParamsUtils;
import com.design.jhbrowser.utils.SearchTagUtils;
import com.design.jhbrowser.utils.ViewFindUtils;
import com.design.jhbrowser.utils.util.SharedPreferencesMgr;
import com.design.jhbrowser.utils.widget.ColorTextView;

/**
 * Created by AdamL on 2017/5/19.
 */

public class FragSearch extends Fragment implements View.OnClickListener {

    private ColorTextView tvCancel;
    private ColorTextView tvStart;
    private RelativeLayout searchWeb;
    private EditText etSearch;
    private ImageView imgEliminate;
    private View view;
    private Context mContext;
    private static FragSearch search;

    public static final FragSearch getInstence(Context context) {
        search = new FragSearch();
        search.mContext = context;
        return search;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_search, null);
        initView();
        //关于SearchView的操作
        aboutSearchViewSetting();
        return view;
    }

    /**
     * 关于SearchView的操作
     */
    private void aboutSearchViewSetting() {
        EditChangedListener listener = new EditChangedListener(search);
        etSearch.addTextChangedListener(listener);
        //设置广播接收者
        setBroadcastTextToEdit();
    }

    /**
     * 设置广播接收者
     */
    private void setBroadcastTextToEdit() {
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(mContext);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DataResourcesUtils.SEARCH_ACTIVITY);
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle bundle = intent.getExtras();
                String flag = bundle.getString("flag");
                String TAG = bundle.getString("search");
                //通过flag判断
                setJudgeFlagEvent(flag, TAG);
            }
        };
        broadcastManager.registerReceiver(receiver, intentFilter);
    }

    /**
     * 判断标签
     *
     * @param flag
     * @param tag
     */
    private void setJudgeFlagEvent(String flag, String tag) {
        if (flag.equals("classify")) {
            etSearch.setText(tag);
        }
    }

    /**
     * 设置主题
     */
    private void searchViewChangeBackGround() {
        if (SharedPreferencesMgr.getInt("theme", 0) == 1) {
            searchWeb.setBackgroundColor(Color.parseColor("#272c31"));
        } else {
            searchWeb.setBackgroundColor(Color.parseColor("#ffffff"));
        }
    }

    @Override
    public void onResume() {
        //设置主题
        searchViewChangeBackGround();
        super.onResume();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        searchWeb = ViewFindUtils.find(view, R.id.search_background);
        etSearch = ViewFindUtils.find(view, R.id.search_wab);
        //定义图片大小
        new LayoutParamsUtils().searchParams(mContext, view);
        new SearchTagUtils().getSearchTag(mContext, view);
        tvCancel = ViewFindUtils.find(view, R.id.tv_cancel);
        tvStart = ViewFindUtils.find(view, R.id.tv_start);
        imgEliminate = ViewFindUtils.find(view, R.id.img_eliminate);
        tvCancel.setOnClickListener(this);
        tvStart.setOnClickListener(this);
        imgEliminate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                sendBroadcasts("cancel", "cancel");
                break;
            case R.id.tv_start:
                String flag = "searchContent";
                String value = etSearch.getText().toString();
                saveSearchContent(value);
                sendBroadcasts(flag, value);
                break;
            case R.id.img_eliminate:
                etSearch.setText("");
                break;
        }
    }

    private void saveSearchContent(String value) {

    }

    private void sendBroadcasts(String flag, String value) {
        Intent intent = new Intent();
        intent.putExtra("flag", flag);
        intent.putExtra("key", value);
        intent.setAction(DataResourcesUtils.MAIN_ACTIVITY);
        LocalBroadcastManager.getInstance(mContext.getApplicationContext())
                .sendBroadcast(intent);
    }

    public void beforeTextChanged() {
        tvCancel.setVisibility(View.GONE);
        tvStart.setVisibility(View.VISIBLE);
        imgEliminate.setVisibility(View.VISIBLE);
    }

    public void onTextChanged(int count) {
        if (count == 0) {
            tvCancel.setVisibility(View.VISIBLE);
            tvStart.setVisibility(View.GONE);
            imgEliminate.setVisibility(View.GONE);
        }
    }


}
