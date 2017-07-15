package com.design.jhbrowser.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.design.jhbrowser.R;

/**
 * Created by AdamL on 2017/4/30.
 */

public class CustomToastUtils {

    private static TextView textView;

    public static void showToast(Context context, String message) {

        //加载Toast布局
        View toastRoot = LayoutInflater.from(context).inflate(R.layout.toast, null);
        //初始化布局控件
        textView = ViewFindUtils.find(toastRoot, R.id.message);
        //为控件设置属性
        textView.setText(message);
        //Toast初始化
        Toast toast = new Toast(context);

        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(toastRoot);
        toast.show();
    }

}
