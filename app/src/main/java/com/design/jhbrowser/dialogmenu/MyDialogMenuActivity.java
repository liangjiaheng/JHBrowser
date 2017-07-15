package com.design.jhbrowser.dialogmenu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.design.jhbrowser.R;
import com.design.jhbrowser.database.bean.CollectBean;
import com.design.jhbrowser.database.bean.HistoryBean;
import com.design.jhbrowser.database.dao.CollectDao;
import com.design.jhbrowser.database.dao.HistoryDao;
import com.design.jhbrowser.database.widget.AddBookmarkActivity;
import com.design.jhbrowser.database.widget.HistoryActivity;
import com.design.jhbrowser.dialogmenu.adapter.MyDialogAdapter;
import com.design.jhbrowser.dialogmenu.bean.DialogBean;
import com.design.jhbrowser.file.HDExplorerActivity;
import com.design.jhbrowser.personalcenter.PersonalCenterActivity;
import com.design.jhbrowser.personalcenter.center.PersonalActivity;
import com.design.jhbrowser.settings.SettingActivity;
import com.design.jhbrowser.utils.CustomToastUtils;
import com.design.jhbrowser.utils.DataResourcesUtils;
import com.design.jhbrowser.utils.KYStringUtils;
import com.design.jhbrowser.utils.SharedPreferencesCenterUtils;
import com.design.jhbrowser.utils.ViewFindUtils;
import com.design.jhbrowser.utils.util.SharedPreferencesMgr;

import java.util.ArrayList;
import java.util.List;

public class MyDialogMenuActivity extends AppCompatActivity implements View.OnClickListener {

    private View view;
    private LinearLayout layoutBack;
    private CheckBox check_night, check_no_pic, check_lock, check_history;
    private SharedPreferences sp;
    private SharedPreferencesCenterUtils spc;
    private String CHECK_NIGHT = "night";
    private String CHECK_NO_PIC = "no_pic";
    private String CHECK_LOCK = "full";
    private String CHECK_HISTORY = "history";
    private RecyclerView recycler;
    //初始化数据
    int[] imgId = DataResourcesUtils.img_settings;
    String[] tvName = DataResourcesUtils.text_settings;
    private ImageView imgLogin;
    private RelativeLayout layoutDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_dialog_menu);
        //初始化Windows，消除手机状态栏
        initWindow();
        //初始化控件
        initView();
        //关于CheckBox的设置
        aboutCheckBoxSetting();

    }

    /**
     * 关于CheckBox的设置
     */
    private void aboutCheckBoxSetting() {
        check_night = ViewFindUtils.find(view, R.id.check_night);
        check_no_pic = ViewFindUtils.find(view, R.id.check_no_pic);
        check_lock = ViewFindUtils.find(view, R.id.check_lock);
        check_history = ViewFindUtils.find(view, R.id.check_history);

        sp = getSharedPreferences("isCheck", MODE_PRIVATE);
        spc = new SharedPreferencesCenterUtils(sp);

        boolean isNight = (Boolean) spc.read(CHECK_NIGHT).get(CHECK_NIGHT);
        boolean isNoPic = (Boolean) spc.read(CHECK_NO_PIC).get(CHECK_NO_PIC);
        boolean isLock = (Boolean) spc.read(CHECK_LOCK).get(CHECK_LOCK);
        boolean isHistory = (Boolean) spc.read(CHECK_HISTORY)
                .get(CHECK_HISTORY);
        check_night.setChecked(isNight);
        check_no_pic.setChecked(isNoPic);
        check_lock.setChecked(isLock);
        check_history.setChecked(isHistory);
        // 设置监听CheckBox的操作
        SettingCheckBoxListener();
    }

    /**
     * 保存CheckBox状态
     *
     * @param key
     * @param isChecked
     */
    public void savedIsCheck(String key, boolean isChecked) {
        spc.write(key, isChecked);
    }

    /**
     * 设置监听CheckBox的操作
     */
    private void SettingCheckBoxListener() {
        // 夜间模式的切换
        check_night.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                String action = DataResourcesUtils.MAIN_ACTIVITY;
                if (isChecked) {
                    savedIsCheck(CHECK_NIGHT, isChecked);
                    isTrueBroadcast("night", "isNight", action);
                } else {
                    savedIsCheck(CHECK_NIGHT, isChecked);
                    isTrueBroadcast("night", "isDay", action);
                }
                MyDialogMenuActivity.this.finish();
            }

        });
        // 无图模式的切换
        check_no_pic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                String action = DataResourcesUtils.FRAG_WEBVIEW;
                SharedPreferencesMgr.setBoolean("noPic", isChecked);
                savedIsCheck(CHECK_NO_PIC, isChecked);
                isTrueBroadcast("noPic", "" + isChecked, action);
                MyDialogMenuActivity.this.finish();
            }
        });

        // 个人中心加锁
        check_lock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                SharedPreferencesMgr.setBoolean("isLock", isChecked);
                savedIsCheck(CHECK_LOCK, isChecked);
                finish();
            }

        });

        // 无痕模式的切换
        check_history.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                savedIsCheck(CHECK_HISTORY, isChecked);
                SharedPreferencesMgr.setBoolean("noHistory", isChecked);
                if (isChecked) {
                    CustomToastUtils.showToast(MyDialogMenuActivity.this, "无痕模式开启");
                } else {
                    CustomToastUtils.showToast(MyDialogMenuActivity.this, "无痕模式关闭");
                }
                MyDialogMenuActivity.this.finish();
            }
        });
    }


    /**
     * 初始化控件
     */
    private void initView() {
        //设置底部返回按钮
        view = getWindow().getDecorView();
        layoutBack = ViewFindUtils.find(view, R.id.layout_back);
        layoutBack.setOnClickListener(this);
        //设置RecycleView
        recycler = ViewFindUtils.find(view, R.id.recycle_dialog);
        //设置弹出的菜单
        setDialogMenu();
        imgLogin = ViewFindUtils.find(view, R.id.img_login);
        imgLogin.setOnClickListener(this);
        layoutDown = ViewFindUtils.find(view, R.id.down_layout);
        layoutDown.setOnClickListener(this);
        DialogThemeChanged.changedTheme(MyDialogMenuActivity.this);
    }

    /**
     * 设置弹出的菜单
     */
    private void setDialogMenu() {
        //设置数据源
        List<DialogBean> list = new ArrayList<>();
        for (int i = 0; i < imgId.length; i++) {
            DialogBean dialog = new DialogBean();
            dialog.setImgId(imgId[i]);
            dialog.setMenuName(tvName[i]);
            list.add(dialog);
        }

        MyDialogAdapter adapter = new MyDialogAdapter(list);

        GridLayoutManager manager = new GridLayoutManager(MyDialogMenuActivity.this, 4);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(manager);

        recycler.addOnItemTouchListener(new MyDialogItemClickListener(this, new MyDialogItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                CustomToastUtils.showToast(MyDialogMenuActivity.this, "" + tvName[position]);
                //跳转设置
                startIntentSetting(position);

                MyDialogMenuActivity.this.finish();
            }
        }));
    }

    private void startIntentSetting(int position) {
        switch (position) {
            case 0:
                //添加书签
                startActivity(new Intent(MyDialogMenuActivity.this, AddBookmarkActivity.class));
                break;
            case 1:
                //历史记录和书签
                startActivity(new Intent(MyDialogMenuActivity.this, HistoryActivity.class));
                break;
            case 2:
                //我的文件跳转
                startActivity(new Intent(MyDialogMenuActivity.this, HDExplorerActivity.class));
                break;
            case 3:
                //刷新
                String falg = "refrush";
                String Value = "";
                String action = DataResourcesUtils.FRAG_WEBVIEW;
                isTrueBroadcast(falg, Value, action);
                break;
            case 4:
                //设置中心
                startActivity(new Intent(MyDialogMenuActivity.this, SettingActivity.class));
                break;
            case 5:
                //分享
                List<HistoryBean> hisList = new HistoryDao(this).getHistory();
                if (hisList.size() > 1) {
                    int i = hisList.size() - 1;
                    String url = hisList.get(i).getUrl();
                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.putExtra(Intent.EXTRA_TEXT, url);
                    shareIntent.setType("text/plain");
                    // 设置分享列表的标题，并且每次都显示分享列表
                    startActivity(Intent.createChooser(shareIntent, "分享到"));
                }
                break;
            case 6:
                //收藏
                List<HistoryBean> colList = new HistoryDao(this).getHistory();
                if (colList.size() > 1) {
                    int i = colList.size() - 1;
                    String title = colList.get(i).getTitle();
                    String url = colList.get(i).getUrl();
                    String time = KYStringUtils.getCurrentTime();
                    CollectBean collectBean = new CollectBean();
                    collectBean.setTitle(title);
                    collectBean.setUrl(url);
                    collectBean.setTime(time);
                    long flag = new CollectDao(this).saveCollect(collectBean);
                    if (flag == 0) {
                        CustomToastUtils.showToast(this, "收藏失败");
                    } else {
                        CustomToastUtils.showToast(this, "收藏成功");
                    }
                }

                break;
            case 7:
                //退出
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
                break;
        }
    }

    /**
     * 初始化Windows，消除手机状态栏
     */
    private void initWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 判断
     *
     * @param TAG
     * @param isTrue
     */
    private void isTrueBroadcast(String TAG, String isTrue, String action) {
        Intent intent = new Intent();
        intent.putExtra("flag", TAG);
        intent.putExtra("key", isTrue);
        intent.setAction(action);
        LocalBroadcastManager.getInstance(MyDialogMenuActivity.this)
                .sendBroadcast(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.down_layout:
                MyDialogMenuActivity.this.finish();
                break;
            case R.id.layout_back:
                MyDialogMenuActivity.this.finish();
                break;
            case R.id.img_login:
                Intent intent = new Intent(MyDialogMenuActivity.this, PersonalActivity.class);
                startActivity(intent);
                break;
        }
    }
}