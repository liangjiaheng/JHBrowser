package com.design.jhbrowser.fragment.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

/**
 * Created by wubb on 2017/1/17.
 */

public class AnimationHelper {
    public static void alphaAnimation(View view, float start, float end, long duration, final Runnable endAnimationRunnable)
    {
        Animator animator= ObjectAnimator.ofFloat(view, "alpha", start,end);
        animator.setDuration(duration);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.addListener(new SimpleAnimatorListener()
        {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if(endAnimationRunnable!=null)
                    endAnimationRunnable.run();
            }
        });
        animator.start();
    }
}
