package com.design.jhbrowser.toolcabinet;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.design.jhbrowser.BaseActivity;
import com.design.jhbrowser.R;
import com.design.jhbrowser.database.bean.BookmarkBean;
import com.design.jhbrowser.database.bean.CollectBean;
import com.design.jhbrowser.database.dao.BookmarkDao;
import com.design.jhbrowser.database.dao.CollectDao;
import com.design.jhbrowser.utils.CustomToastUtils;
import com.design.jhbrowser.utils.DataResourcesUtils;
import com.design.jhbrowser.utils.ViewFindUtils;

import java.util.List;

/**
 * Created by AdamL on 2017/5/24.
 */

public class CollectActivity extends BaseActivity {

    private View view;
    private ListView list;
    private SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        initTollbar();

        initView();
    }


    private void initTollbar() {
        view = getWindow().getDecorView();
        Toolbar toolbar = ViewFindUtils.find(view, R.id.toolbar_collect);
        toolbar.setTitle("收藏");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowTitleEnabled(true);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }


    private void initView() {
        list = ViewFindUtils.find(view, R.id.list_collect);

        Cursor cursor = new CollectDao(this).getCursor();

        if (cursor != null) {
            adapter = new SimpleCursorAdapter(this, R.layout.item_frag_bookmark, cursor, new String[]{"title", "time"}, new int[]{R.id.book_title, R.id.book_time});
            list.setAdapter(adapter);
        }

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String flag = "url";
                String key = new CollectDao(CollectActivity.this).getCollect().get(position).getUrl();
                String action = DataResourcesUtils.MAIN_ACTIVITY;
                sendBroadcastInfo(flag, key, action);
                finish();
            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
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
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private void dialog(final int position) {


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
        CustomToastUtils.showToast(this, "执行删除操作");

        List<CollectBean> list = new CollectDao(this).getCollect();

        String time = list.get(position).getTime();

        long delete = new CollectDao(this).deleteItemCollect(time);

        if (delete == 0) {
            CustomToastUtils.showToast(this, "删除失败");
        } else {
            CustomToastUtils.showToast(this, "删除成功");
        }

        Cursor newCursor = new CollectDao(this).getCursor();
        adapter.changeCursor(newCursor);
        adapter.notifyDataSetChanged();
    }

}
