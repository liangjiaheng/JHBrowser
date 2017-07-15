package com.design.jhbrowser.welcome;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.Toast;

import com.design.jhbrowser.MainActivity;
import com.design.jhbrowser.R;
import com.design.jhbrowser.utils.ViewFindUtils;
import com.design.jhbrowser.utils.util.SharedPreferencesMgr;
import com.design.jhbrowser.welcome.launcher.LauncherView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.Timer;
import java.util.TimerTask;

@ContentView(R.layout.activity_guide_interface)
public class GuideInterfaceActivity extends AppCompatActivity {

    private View view;
    private ImageView imgLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        initWindows();
        initView();

        skip();
    }

    private void initView() {
        view = getWindow().getDecorView();
        LauncherView launcher = ViewFindUtils.find(view, R.id.launcher_view);
        launcher.start();
    }

    /**
     * 初始化Windows，消除手机状态栏
     */
    private void initWindows() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }


    private void firstLoadJudge(Timer timer, Intent intent_main, Intent intent_welcome) {
        boolean firstLoad = SharedPreferencesMgr.getBoolean("firstLoad", true);
        if (firstLoad) {
            delayToWel(timer, intent_welcome);
            SharedPreferencesMgr.setBoolean("firstLoad", false);
        } else {
            delayToMain(timer, intent_main);
        }
    }

    private void delayToMain(Timer timer, final Intent intent_main) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                startActivity(intent_main);
                GuideInterfaceActivity.this.finish();
            }
        };
        timer.schedule(task, 4000);
    }

    private void delayToWel(Timer timer, final Intent intent_welcome) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                startActivity(intent_welcome);
                GuideInterfaceActivity.this.finish();
            }
        };
        timer.schedule(task, 3000);
    }


    private void skip() {
        final Intent intent_main = new Intent(this, MainActivity.class);
        final Intent intent_welcome = new Intent(this, WelcomeActivity.class);
        Timer timer = new Timer();//创建一个定时器实例
        firstLoadJudge(timer, intent_main, intent_welcome);
    }
}
