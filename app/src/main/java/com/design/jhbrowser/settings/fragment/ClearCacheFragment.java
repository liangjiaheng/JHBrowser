package com.design.jhbrowser.settings.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.design.jhbrowser.R;
import com.design.jhbrowser.database.dao.BookmarkDao;
import com.design.jhbrowser.database.dao.HistoryDao;
import com.design.jhbrowser.utils.CustomToastUtils;
import com.design.jhbrowser.utils.ViewFindUtils;

/**
 * Created by AdamL on 2017/5/27.
 */

public class ClearCacheFragment extends Fragment implements View.OnClickListener {

    private Context mContext;
    private View view;
    private LinearLayout clearHistory;
    private LinearLayout clearBookmark;

    public final static ClearCacheFragment getInstance(Context context) {
        ClearCacheFragment fragment = new ClearCacheFragment();

        fragment.mContext = context;

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_back_clear_cache, null);
        initView();
        return view;
    }

    private void initView() {
        clearHistory = ViewFindUtils.find(view, R.id.clear_history_data);
        clearBookmark = ViewFindUtils.find(view, R.id.clear_bookmark_data);

        clearBookmark.setOnClickListener(this);
        clearHistory.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.clear_history_data:
                int wipe = new HistoryDao(getActivity()).wipeTable();
                isSuccess(wipe);
                break;
            case R.id.clear_bookmark_data:
                int success = new BookmarkDao(getActivity()).wipeData();
                isSuccess(success);
                break;
        }
    }

    public void isSuccess(int wipe) {
        if (wipe == 0) {
            CustomToastUtils.showToast(getActivity(), "清除失败！");
        } else {
            CustomToastUtils.showToast(getActivity(), "清除成功！");
        }

    }
}
