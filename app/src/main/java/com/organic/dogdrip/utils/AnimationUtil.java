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
            Animator anim = ViewAnimationUtils.createCircularReveal(view, getCx(view), getCy(view),
                    0, Math.max(view.getHeight(), view.getWidth()));
            anim.setDuration(ANIMATION_DURATION);
            if(ll != null){
                anim.addListener(ll);
            }
            anim.start();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void circularReveal(final View view, Animator.AnimatorListener ll){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Animator anim = ViewAnimationUtils.createCircularReveal(view, getCx(view), getCy(view),
                    Math.max(view.getHeight(), view.getWidth()), 0);
            if(ll != null){
                anim.addListener(ll);
            }
            anim.setDuration(ANIMATION_DURATION);
            anim.start();
        }
    }

    private static int getCy(View view){
        return (view.getTop() + view.getBottom()) / 2;
    }

    private static int getCx(View view){
        return (view.getLeft() + view.getRight()) / 2;
    }


}
