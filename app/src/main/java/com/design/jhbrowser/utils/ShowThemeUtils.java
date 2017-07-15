package com.design.jhbrowser.utils;

import android.animation.Animator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;

import com.design.jhbrowser.utils.util.ColorUiUtil;

/**
 * Created by AdamL on 2017/4/29.
 */

public class ShowThemeUtils {

    public static void showTheme(final View rootView, Context context, final Resources.Theme themes, Resources resources) {

        if (Build.VERSION.SDK_INT >= 14) {
            rootView.setDrawingCacheEnabled(true);
            rootView.buildDrawingCache(true);
            final Bitmap localBitmap = Bitmap.createBitmap(rootView.getDrawingCache());
            if (localBitmap != null && rootView instanceof ViewGroup) {
                final View localView2 = new View(context);
                localView2.setBackgroundDrawable(new BitmapDrawable(resources, localBitmap));
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                ((ViewGroup) rootView).addView(localView2, params);
                localView2.animate().alpha(0).setDuration(300).setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                        ColorUiUtil.changeTheme(rootView, themes);
                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        ((ViewGroup) rootView).removeView(localView2);
                        localBitmap.recycle();
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
            }
        } else {
            ColorUiUtil.changeTheme(rootView, themes);
        }
    }

}
