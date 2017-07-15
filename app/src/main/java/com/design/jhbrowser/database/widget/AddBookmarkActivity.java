package com.design.jhbrowser.database.widget;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.design.jhbrowser.BaseActivity;
import com.design.jhbrowser.R;
import com.design.jhbrowser.database.bean.BookmarkBean;
import com.design.jhbrowser.database.dao.BookmarkDao;
import com.design.jhbrowser.fragment.FragWebView;
import com.design.jhbrowser.utils.CustomToastUtils;
import com.design.jhbrowser.utils.DataResourcesUtils;
import com.design.jhbrowser.utils.KYStringUtils;
import com.design.jhbrowser.utils.ViewFindUtils;

public class AddBookmarkActivity extends BaseActivity implements View.OnClickListener {

    private View view;
    private TextView tvConfirm;
    private TextView tvCancel;
    private ClearEditText etTitle;
    private ClearEditText etAddress;
    private BookmarkBean bookmark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bookmark);
        view = getWindow().getDecorView();
        //初始化控件
        initView();
        //发送消息
        sendBroadcastRequest();
        //获取信息
        getBroadcastinformation();

    }

    private void sendBroadcastRequest() {
        Intent intent = new Intent();
        intent.putExtra("flag", "request");
        intent.putExtra("key", "");
        intent.setAction(DataResourcesUtils.FRAG_WEBVIEW);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }


    private void getBroadcastinformation() {
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(getApplicationContext());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DataResourcesUtils.ADD_BOOKMARK_ACTIVITY);
        BroadcastReceiver mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle bundle = intent.getExtras();
                String flag = bundle.getString("flag");
                bookmark = (BookmarkBean) intent.getSerializableExtra("key");
                CustomToastUtils.showToast(AddBookmarkActivity.this, bookmark.getTitle());

                etTitle.setText(bookmark.getTitle());
                etAddress.setText(bookmark.getUrl());

            }
        };
        manager.registerReceiver(mReceiver, intentFilter);


    }


    /**
     * 初始化控件
     */
    private void initView() {
        tvConfirm = ViewFindUtils.find(view, R.id.tv_confirm);
        tvCancel = ViewFindUtils.find(view, R.id.tv_cancel);

        etTitle = ViewFindUtils.find(view, R.id.et_clear_title);
        etAddress = ViewFindUtils.find(view, R.id.et_clear_address);

        tvCancel.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                AddBookmarkActivity.this.finish();
                break;
            case R.id.tv_confirm:
                if (!isAddressEmpty() && !isTitleEmpty()) {
                    BookmarkBean bookmarkBean = new BookmarkBean();
                    bookmarkBean.setTitle(etTitle.getText().toString().trim());
                    bookmarkBean.setUrl(etAddress.getText().toString().trim());
                    bookmarkBean.setTime(KYStringUtils.getCurrentTime());
                    long a = new BookmarkDao(getApplicationContext()).saveBookmark(bookmarkBean);
                    if (a != 0) {
                        CustomToastUtils.showToast(this, "保存成功");
                        finish();
                    }
                }
                break;
        }
    }

    public boolean isTitleEmpty() {
        if (TextUtils.isEmpty(etTitle.getText())) {
            etTitle.setShakeAnimation();
            CustomToastUtils.showToast(this, "标题不能为空");
            return true;
        } else {
            return false;
        }
    }

    public boolean isAddressEmpty() {
        if (TextUtils.isEmpty(etAddress.getText())) {
            etAddress.setShakeAnimation();
            CustomToastUtils.showToast(this, "地址不能为空");
            return true;
        } else {
            return false;
        }
    }

}
