package com.design.jhbrowser.personalcenter.center;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.design.jhbrowser.R;
import com.design.jhbrowser.login.LoginSMSActivity;
import com.design.jhbrowser.login.optiondate.OptionDataActivity;
import com.design.jhbrowser.login.passlogin.LoginPassActivity;
import com.design.jhbrowser.personalcenter.base.BaseCardLayout;
import com.design.jhbrowser.personalcenter.base.BaseCenter;
import com.design.jhbrowser.personalcenter.base.BaseObjectAdapter;
import com.design.jhbrowser.personalcenter.myitem.calendar.CalendarActivity;
import com.design.jhbrowser.personalcenter.myitem.notes.NotesActivity;
import com.design.jhbrowser.personalcenter.utils.AnimUtil;
import com.design.jhbrowser.personalcenter.utils.DrawableUtil;
import com.design.jhbrowser.personalcenter.view.CreativeView;
import com.design.jhbrowser.personalcenter.view.DramaView;
import com.design.jhbrowser.personalcenter.view.OpusView;
import com.design.jhbrowser.personalcenter.widget.MenusLayout;
import com.design.jhbrowser.settings.SettingActivity;
import com.design.jhbrowser.toolcabinet.CollectActivity;
import com.design.jhbrowser.utils.ViewFindUtils;
import com.design.jhbrowser.utils.util.SharedPreferencesMgr;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

/**
 * Created by AdamL on 2017/5/24.
 */

public class PersonalActivity extends BaseCenter implements OnItemClickListener, View.OnClickListener {
    private ImageView img_bg;
    private View layout_user;
    private MenusLayout mMenusLayout;
    private CreativeView mCreativeView;
    private OpusView mOpusView;
    private DramaView mDramaView;

    private BaseCardLayout[] mCardLayouts;

    private RoundedImageView img_portrait;
    private TextView txt_name;
    private TextView txt_info;
    private ImageView img_go;

    private Menu msgMenu;
    private ArrayList<Menu> list;
    private TextView tvName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        img_bg = new ImageView(this);
        img_bg.setScaleType(ImageView.ScaleType.CENTER_CROP);
        FrameLayout.LayoutParams bgParams = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        setContentView(img_bg, bgParams);
        FrameLayout.LayoutParams mainParams = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        addContentView(View.inflate(this, R.layout.activity_personal, null), mainParams);
        findViews();
        setViewsContent();
        setViewsListener();
    }

    @Override
    protected void findViews() {
        View view = getWindow().getDecorView();
        tvName = ViewFindUtils.find(view, R.id.txt_name);
        layout_user = findViewById(R.id.layout_user);
        mMenusLayout = (MenusLayout) findViewById(R.id.menus);
        mCreativeView = (CreativeView) findViewById(R.id.view_creative);
        mOpusView = (OpusView) findViewById(R.id.view_opus);
        mDramaView = (DramaView) findViewById(R.id.view_drama);

        mCardLayouts = new BaseCardLayout[3];
        mCardLayouts[0] = mCreativeView;
        mCardLayouts[1] = mOpusView;
        mCardLayouts[2] = mDramaView;

        img_portrait = (RoundedImageView) findViewById(R.id.img_portrait);
        txt_name = (TextView) findViewById(R.id.txt_name);
        txt_info = (TextView) findViewById(R.id.txt_info);
        img_go = (ImageView) findViewById(R.id.img_go);

        layout_user.setOnClickListener(this);
    }


    @Override
    protected void onResume() {
        if (SharedPreferencesMgr.getBoolean("login", false)) {
            tvName.setText(SharedPreferencesMgr.getString("username", "点击登录"));
        } else {
            tvName.setText("点击登录");
        }
        super.onResume();
    }

    @Override
    protected void setViewsContent() {
        mCreativeView.setHideRestOthersEnable(true);
        mOpusView.setHideRestOthersEnable(true);
        mDramaView.setHideRestOthersEnable(true);

        mCreativeView.setFirstResetEnable(true);
        mOpusView.setFirstResetEnable(true);
        mDramaView.setFirstResetEnable(true);

        list = new ArrayList<>();
        list.add(new Menu(R.mipmap.img_personal_center_01, "收藏"));
        list.add(new Menu(R.mipmap.img_personal_center_03, "消息"));
        list.add(new Menu(R.mipmap.img_personal_center_04, "相册"));
        list.add(new Menu(R.mipmap.img_personal_center_05, "记录"));
        list.add(new Menu(R.mipmap.img_personal_center_06, "日期"));
        list.add(new Menu(R.mipmap.img_personal_center_02, "设置"));
        MenusAdapter menusAdapter = new MenusAdapter(this, list);
        mMenusLayout.setAdapter(menusAdapter);
        AnimUtil.postAnimation(layout_user, 200, 500);
        AnimUtil.postAnimationBottom(mCreativeView, 200, 1100);
        AnimUtil.postAnimationBottom(mOpusView, 150, 1000);
        AnimUtil.postAnimationBottom(mDramaView, 100, 900);

    }

    @Override
    protected void setViewsListener() {
        mMenusLayout.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                startActivity(new Intent(PersonalActivity.this, CollectActivity.class));
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                startActivity(new Intent(PersonalActivity.this, NotesActivity.class));
                break;
            case 4:
                startActivity(new Intent(PersonalActivity.this, CalendarActivity.class));
                break;
            case 5:
                startActivity(new Intent(PersonalActivity.this, SettingActivity.class));
                break;
        }
    }

    @Override
    public void onBackPressed() {
        boolean resetScrolled = false;
        for (BaseCardLayout cardLayout : mCardLayouts) {
            if (!cardLayout.checkReset()) {
                resetScrolled = true;
                break;
            }
        }
        if (resetScrolled) {
            for (BaseCardLayout cardLayout : mCardLayouts) {
                cardLayout.startResetScroll();
            }
            return;
        }
        super.onBackPressed();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_user:
                startActivity(new Intent(PersonalActivity.this, LoginPassActivity.class));
                break;
        }
    }


    private class MenusAdapter extends BaseObjectAdapter<Menu> {

        public MenusAdapter(Context context, ArrayList<Menu> list) {
            super(context, list);
        }

        @Override
        public View getView(int position, View convertView, final ViewGroup parent) {
            Menu menu = getItem(position);
            TextView txt = new TextView(mContext);
            txt.setTextColor(Color.WHITE);
            txt.setTextSize(14);
            txt.setGravity(Gravity.CENTER_HORIZONTAL);
            DrawableUtil.setTextDrawableTop(txt, DrawableUtil.dp2Px(mContext, 5), menu.resId);
            txt.setText(menu.text);
            final int size = LayoutParams.WRAP_CONTENT;
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(size, size, Gravity.CENTER);
            parent.addView(txt, params);
            menu.setCountListener(new CountListener() {
                @Override
                public void onCount(int count) {
                    TextView txt_count = (TextView) parent.getTag();
                    if (count > 0) {
                        if (txt_count == null) {
                            txt_count = new TextView(mContext);
                            int pad = DrawableUtil.dp2Px(mContext, 2);
                            txt_count.setPadding(pad, 0, pad, 0);
                            //txt_count.setBackgroundResource(R.drawable.bg_round_red_msg);
                            txt_count.setTextColor(Color.WHITE);
                            txt_count.setTextSize(12);
                            int height = DrawableUtil.dp2Px(mContext, 10);
                            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(size, height, Gravity.RIGHT);
                            params.topMargin = pad;
                            params.rightMargin = pad;
                            parent.addView(txt_count, params);
                            parent.setTag(txt_count);
                        }
                        txt_count.setText("" + count);
                    } else {
                        if (txt_count != null) {
                            parent.removeView(txt_count);
                            parent.setTag(null);
                        }
                    }
                }
            });
            return parent;
        }
    }


    private class Menu {
        int resId;
        String text;

        Menu(int resId, String text) {
            this.resId = resId;
            this.text = text;
        }

        CountListener mCountListener;

        void setCountListener(CountListener l) {
            mCountListener = l;
        }

        void setCount(int count) {
            if (mCountListener != null) {
                mCountListener.onCount(count);
            }
        }
    }

    interface CountListener {
        void onCount(int count);
    }

}
