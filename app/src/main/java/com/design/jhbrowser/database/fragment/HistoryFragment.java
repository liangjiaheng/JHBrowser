package com.design.jhbrowser.database.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.design.jhbrowser.R;
import com.design.jhbrowser.database.adapter.MyHisAdapter;
import com.design.jhbrowser.database.bean.HistoryBean;
import com.design.jhbrowser.database.dao.HistoryDao;
import com.design.jhbrowser.fragment.FragNavigation;
import com.design.jhbrowser.utils.CustomToastUtils;
import com.design.jhbrowser.utils.DataResourcesUtils;
import com.design.jhbrowser.utils.KYStringUtils;
import com.design.jhbrowser.utils.ViewFindUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AdamL on 2017/5/18.
 */

public class HistoryFragment extends Fragment {
    private Context mContext;
    private View view;
    private TextView tvTest;
    private ImageView imgTest;
    private ListView mListView;
    private MyHisAdapter adapter;
    private static HistoryFragment historyFragment;

    public static final HistoryFragment getInstance(Context context) {
        historyFragment = new HistoryFragment();
        historyFragment.mContext = context;
        return historyFragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_history, null);

        initView();

        listItemSetting();

        return view;
    }


    private void initView() {
        mListView = ViewFindUtils.find(view, R.id.listView);
        ArrayList<HistoryBean> list = new HistoryDao(mContext).getHistory();
        ImageView imgEmpty = ViewFindUtils.find(view, R.id.img_his_empty);

        adapter = new MyHisAdapter(mContext, list);
        mListView.setAdapter(adapter);
        mListView.setEmptyView(imgEmpty);
    }

    private void listItemSetting() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String flag = "url";
                String key = new HistoryDao(getActivity()).getHistory().get(position).getUrl();
                String action = DataResourcesUtils.MAIN_ACTIVITY;
                sendBroadcastInfo(flag, key, action);
                getActivity().finish();

            }
        });

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                dialog(position);
                return true;
            }
        });
    }

    private void dialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("确定删除？？")
                .setTitle("提示")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteItemInfo(position);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();

    }

    private void deleteItemInfo(int position) {

        List<HistoryBean> list = new HistoryDao(getActivity()).getHistory();

        String time = list.get(position).getTime();

        long delete = new HistoryDao(getActivity()).deleteHistory(time);

        if (delete == 0) {
            CustomToastUtils.showToast(mContext, "删除失败");
        } else {
            CustomToastUtils.showToast(mContext, "删除成功");
        }

        List<HistoryBean> lists = new HistoryDao(getActivity()).getHistory();

        adapter = new MyHisAdapter(mContext, lists);
        adapter.notifyDataSetChanged();
        mListView.setAdapter(adapter);
    }

    public void sendBroadcastInfo(String flag, String key, String action) {
        Intent intent = new Intent();
        intent.putExtra("flag", flag);
        intent.putExtra("key", key);
        intent.setAction(action);
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
    }

}
