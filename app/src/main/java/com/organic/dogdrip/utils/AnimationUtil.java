package com.organic.dogdrip.utils;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;
import android.view.ViewAnimationUtils;

/**
 * Created by kwonojin on 16. 4. 25..
 */
public class AnimationUtil {

    public static final long ANIMATION_DURATION = 1000L;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void circularRevealExpend(View view, Animator.AnimatorListener ll){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Animator anim = ViewAnimationUtils.createCircularReveal(view, view.getWidth() / 2, view.getHeight() / 2,
                    0, Math.max(view.getHeight(), view.getWidth()));
            if(ll != null){
                anim.addListener(ll);
            }
            anim.start();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void circularReveal(final View view, Animator.AnimatorListener ll){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Animator anim = ViewAnimationUtils.createCircularReveal(view, view.getWidth() / 2, view.getHeight() / 2,
                    Math.max(view.getHeight(), view.getWidth()), 0);
            if(ll != null){
                anim.addListener(ll);
            }
            anim.start();
        }
    }


}
