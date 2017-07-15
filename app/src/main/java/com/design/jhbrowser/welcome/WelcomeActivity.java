package com.design.jhbrowser.welcome;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.design.jhbrowser.MainActivity;
import com.design.jhbrowser.R;
import com.design.jhbrowser.utils.ViewFindUtils;
import com.design.jhbrowser.welcome.custom.MyVideoView;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    private View view;
    private MyVideoView videoView;
    private TextView textSkip;
    private Button btnBegin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        //初始化控件
        initView();

        //关于VideoView的操作
        aboutVideoViewSetting();

        //关于welcome界面的点击事件
        aboutonClickSetting();
    }


    private void aboutonClickSetting() {
        textSkip.setOnClickListener(this);
        btnBegin.setOnClickListener(this);
    }


    /**
     * 控件初始化
     */
    private void initView() {
        view = getWindow().getDecorView();
        videoView = ViewFindUtils.find(view, R.id.video_view);
        textSkip = ViewFindUtils.find(view, R.id.text_skip);
        btnBegin = ViewFindUtils.find(view, R.id.button_watch);
    }

    private void aboutVideoViewSetting() {
        String url = "android.resource://" + getPackageName() + "/" + R.raw.splash_video;
        videoView.setVideoURI(Uri.parse(url));

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                videoView.start();
            }
        });
        videoView.start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_watch:
                Toast.makeText(this, "开始直播", Toast.LENGTH_SHORT).show();
                break;
            case R.id.text_skip:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                WelcomeActivity.this.finish();
                break;
        }
    }
}
