package com.design.jhbrowser.database.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.design.jhbrowser.R;
import com.design.jhbrowser.database.bean.BookmarkBean;
import com.design.jhbrowser.database.dao.BookmarkDao;
import com.design.jhbrowser.utils.CustomToastUtils;
import com.design.jhbrowser.utils.DataResourcesUtils;
import com.design.jhbrowser.utils.ViewFindUtils;

import java.util.List;

/**
 * Created by AdamL on 2017/5/18.
 */

public class BookmarkFragment extends Fragment {

    private View view;
    private Context mContext;
    private ListView bookmarkList;
    private SimpleCursorAdapter adapter;

    public static final BookmarkFragment getInstance(Context context) {
        BookmarkFragment bookmarkFragment = new BookmarkFragment();
        bookmarkFragment.mContext = context;
        return bookmarkFragment;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_bookmark, null);
        initView();
        return view;
    }

    private void initView() {

        bookmarkList = ViewFindUtils.find(view, R.id.bookmark_list);

        Cursor cursor = new BookmarkDao(mContext).getCursor();
        if (cursor != null) {
            adapter = new SimpleCursorAdapter(mContext, R.layout.item_frag_bookmark, cursor, new String[]{"title", "time"}, new int[]{R.id.book_title, R.id.book_time});
            bookmarkList.setAdapter(adapter);
        }

        bookmarkList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String flag = "url";
                String key = new BookmarkDao(getActivity()).getBookmark().get(position).getUrl();
                String action = DataResourcesUtils.MAIN_ACTIVITY;
                sendBroadcastInfo(flag, key, action);
                getActivity().finish();

            }
        });

        bookmarkList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                dialog(position);
                return true;
            }
        });

    }

    private void sendBroadcastInfo(String flag, String key, String action) {
        Intent intent = new Intent();
        intent.putExtra("flag", flag);
        intent.putExtra("key", key);
        intent.setAction(action);
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
    }

    private void dialog(final int position) {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("确定删除？")
                .setTitle("提示");
        builder.setPositiveButton(R.string.button_text_yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteItemInfo(position);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    private void deleteItemInfo(int position) {
        CustomToastUtils.showToast(mContext, "执行删除操作");

        List<BookmarkBean> list = new BookmarkDao(getActivity()).getBookmark();

        String time = list.get(position).getTime();

        long delete = new BookmarkDao(getActivity()).deleteBookmark(time);

        if (delete == 0) {
            CustomToastUtils.showToast(getActivity(), "删除失败");
        } else {
            CustomToastUtils.showToast(getActivity(), "删除成功");
        }
        Cursor newCursor = new BookmarkDao(getActivity()).getCursor();
        adapter.changeCursor(newCursor);
        adapter.notifyDataSetChanged();
    }
}
