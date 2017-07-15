package com.design.jhbrowser.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.design.jhbrowser.R;
import com.design.jhbrowser.database.bean.BookmarkBean;
import com.design.jhbrowser.database.bean.HistoryBean;
import com.design.jhbrowser.database.dao.BookmarkDao;
import com.design.jhbrowser.database.dao.HistoryDao;
import com.design.jhbrowser.fragment.animation.AnimationHelper;
import com.design.jhbrowser.fragment.listener.FragEditChangedListener;
import com.design.jhbrowser.utils.CustomToastUtils;
import com.design.jhbrowser.utils.DataResourcesUtils;
import com.design.jhbrowser.utils.KYStringUtils;
import com.design.jhbrowser.utils.ViewFindUtils;
import com.design.jhbrowser.utils.util.SharedPreferencesMgr;
import com.design.jhbrowser.utils.widget.ColorTextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;

/**
 * Created by AdamL on 2017/4/29.
 */

public class FragWebView extends Fragment implements View.OnClickListener {

    private View view;
    private Context context;
    private WebView webView;
    private ProgressBar mProgressBar;
    private LocalBroadcastManager manager;
    private IntentFilter intentFilter;
    private BroadcastReceiver mReceiver;
    private EditText etContent;
    private ImageView imgEliminate;
    private ColorTextView tvCancel;
    private ColorTextView tvStart;
    private static FragWebView frag;
    private RelativeLayout searchBackground;
    private BookmarkBean bookmarkBean;
    private Bundle savedInstanceState;
    private FrameLayout fl_web_view;
    private MyWebChromeClient webChromeClient;
    private long clickTime = 0;

    public final static FragWebView getInstance(Context context) {
        frag = new FragWebView();
        frag.context = context;
        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        this.savedInstanceState = savedInstanceState;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_web, null);
        //初始化控件
        initView();
        return view;
    }

    /**
     * 初始化控件
     */
    private void initView() {
        webView = ViewFindUtils.find(view, R.id.web);
        mProgressBar = ViewFindUtils.find(view, R.id.mProgress);
        etContent = ViewFindUtils.find(view, R.id.search_wab);
        imgEliminate = ViewFindUtils.find(view, R.id.img_eliminate);
        tvCancel = ViewFindUtils.find(view, R.id.tv_cancel);
        tvStart = ViewFindUtils.find(view, R.id.tv_start);
        searchBackground = ViewFindUtils.find(view, R.id.search_background);

        fl_web_view = ViewFindUtils.find(view, R.id.fl_web_view);

        //初始化webView
        initWebView();
        //搜索框设置
        searchBarSetting();
    }

    private void searchBarSetting() {
        tvStart.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
        imgEliminate.setOnClickListener(this);
        etContent.addTextChangedListener(new FragEditChangedListener(frag));
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


    /**
     * 初始化WebView
     */
    private void initWebView() {
        //设置WebView是否允许运行JavaScript脚本，默认为false，不允许
        webView.getSettings().setJavaScriptEnabled(true);
        //是否使用内置缩放机制
        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        webView.getSettings().setBuiltInZoomControls(true);
        if (SharedPreferencesMgr.getBoolean("noPic", false)) {
            webView.getSettings().setBlockNetworkImage(true);
        } else {
            webView.getSettings().setBlockNetworkImage(false);
        }
        webView.getSettings().setUseWideViewPort(true);
        webView.loadData("", "text/html", null);
        webView.loadUrl("javascript:alert(injectedObject.toString())");

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                mProgressBar.setVisibility(View.GONE);
                super.onPageFinished(view, url);
                changedTheme();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                mProgressBar.setVisibility(View.VISIBLE);
                super.onPageStarted(view, url, favicon);
            }
        });
        webChromeClient = new MyWebChromeClient();
        webView.setWebChromeClient(webChromeClient);
    }

    @Override
    public void onPause() {
        try {
            webView.getClass().getMethod("onPause").invoke(webView, (Object[]) null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        try {
            webView.getClass().getMethod("onResume").invoke(webView, (Object[]) null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        super.onResume();
    }

    /**
     * 保存浏览纪录
     *
     * @param title
     */
    private void savedrowserRecode(String title) {
        HistoryBean historyBean = new HistoryBean();
        historyBean.setTitle(title);
        historyBean.setUrl(webView.getUrl());
        historyBean.setTime(KYStringUtils.getCurrentTime());
        historyBean.setImgUrl(KYStringUtils.getImgUrl(webView.getUrl()));
        new HistoryDao(context).saveHistory(historyBean);

    }

    public void sendBroadcastsBookmark() {
        bookmarkBean = new BookmarkBean();
        bookmarkBean.setTitle(webView.getTitle());
        bookmarkBean.setUrl(webView.getUrl());
        bookmarkBean.setTime(KYStringUtils.getCurrentTime());
        Intent in = new Intent();
        in.putExtra("flag", "bookmark");
        in.putExtra("key", bookmarkBean);
        in.setAction(DataResourcesUtils.ADD_BOOKMARK_ACTIVITY);
        LocalBroadcastManager.getInstance(context.getApplicationContext())
                .sendBroadcast(in);


    }

    /**
     * 开始加载网页
     *
     * @param address
     */
    public void startNet(String address) {
        if (null != address && !" ".equals(address)) {
            if (!address.startsWith("http://")) {
                if (address.startsWith("www.")) {
                    address = "http://" + address;
                    webView.loadUrl(address);
                } else {
                    try {
                        // address = URLEncoder.encode(address, "gb2312");
                        webView.loadUrl("http://www.baidu.com.cn/s?wd="
                                + URLEncoder.encode(address, "gb2312"));
                    } catch (UnsupportedEncodingException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            } else {
                webView.loadUrl(address);
            }
        }
    }

    /**
     * 改变WebView夜间模式
     */
    private void changedTheme() {

        if (SharedPreferencesMgr.getInt("theme", 0) == 1) {
            searchBackground.setBackgroundColor(context.getResources().getColor(
                    R.color.bg_second_dark));
            try {
                InputStream is = getResources().openRawResource(R.raw.night);
                byte[] buffer = new byte[is.available()];
                is.read(buffer);
                is.close();
                String nightCode = Base64
                        .encodeToString(buffer, Base64.NO_WRAP);

                webView.loadUrl("JavaScript:(function() {"
                        + "var parent = document.getElementsByTagName('head').item(0);"
                        + "var style = document.createElement('style');"
                        + "style.type = 'text/css';"
                        + "style.innerHTML = window.atob('" + nightCode + "');"
                        + "parent.appendChild(style)" + "})();");

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            searchBackground.setBackgroundColor(context.getResources().getColor(
                    R.color.mt_main_font_color));
        }
    }

    public void onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack(); //goBack()表示返回WebView的上一页面
        } else {
            exit();
        }
    }

    private void exit() {
        if ((System.currentTimeMillis() - clickTime) > 2000) {
            CustomToastUtils.showToast(context, "再次点击将退出浏览器");
            clickTime = System.currentTimeMillis();
        } else {
            getActivity().finish();
            System.exit(0);
        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        manager = LocalBroadcastManager.getInstance(getActivity().getApplicationContext());
        intentFilter = new IntentFilter();
        intentFilter.addAction(DataResourcesUtils.FRAG_WEBVIEW);
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle bundle = intent.getExtras();
                String flag = bundle.getString("flag");
                String value = bundle.getString("key");
                isJudgeFlag(flag, value);
            }
        };
        manager.registerReceiver(mReceiver, intentFilter);
    }

    private void isJudgeFlag(String flag, String value) {
        if (flag.equals("back")) {
            if (webView.canGoBack()) {
                webView.goBack();
            } else {
                CustomToastUtils.showToast(context, "已经到达第一页");
                String action = DataResourcesUtils.MAIN_ACTIVITY;
                String flags = "firstPage";
                String key = "";
                sendBroadcasts(flags, key, action);
            }
        } else if (flag.equals("forword")) {
            webView.goForward();
        } else if (flag.equals("noPic")) {
            if (value.equals("true")) {
                webView.getSettings().setBlockNetworkImage(true);
                CustomToastUtils.showToast(context.getApplicationContext(), "无图模式开启");
            } else {
                webView.getSettings().setBlockNetworkImage(false);
                CustomToastUtils.showToast(context.getApplicationContext(), "无图模式关闭");
            }
        } else if (flag.equals("request")) {
            sendBroadcastsBookmark();
        } else if (flag.equals("refrush")) {
            webView.reload();
        }

    }

    private void sendBroadcasts(String flags, String value, String action) {
        Intent intent = new Intent();
        intent.putExtra("flag", flags);
        intent.putExtra("key", value);
        intent.setAction(action);
        LocalBroadcastManager.getInstance(context.getApplicationContext())
                .sendBroadcast(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                etContent.setText("");
                break;
            case R.id.tv_start:
                startNet(etContent.getText().toString());
                break;
            case R.id.img_eliminate:
                etContent.setText("");
                break;
        }
    }

    private class MyWebChromeClient extends WebChromeClient {
        private View mCustomView;
        private CustomViewCallback mCustomViewCallback;

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            mProgressBar.setProgress(newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            //保存浏览纪录
            if (!SharedPreferencesMgr.getBoolean("noHistory", false)) {
                savedrowserRecode(title);
            }
            super.onReceivedTitle(view, title);
        }

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            super.onShowCustomView(view, callback);
            if (mCustomView == null) {
                callback.onCustomViewHidden();
                return;
            }
            mCustomView = view;
            fl_web_view.addView(mCustomView);
            mCustomViewCallback = callback;
            webView.setVisibility(View.GONE);
        }

        @Override
        public void onHideCustomView() {
            webView.setVisibility(View.VISIBLE);
            if (mCustomView == null) {
                return;
            }
            mCustomView.setVisibility(View.GONE);
            fl_web_view.removeView(mCustomView);
            mCustomViewCallback.onCustomViewHidden();
            mCustomView = null;
            super.onHideCustomView();
        }
    }
}
