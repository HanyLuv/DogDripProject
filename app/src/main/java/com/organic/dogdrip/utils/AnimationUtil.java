package com.organic.dogdrip.utils;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

/**
 * Created by kwonojin on 16. 4. 25..
 */
public class AnimationUtil {

    public static final long ANIMATION_DURATION = 1000L;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void circularRevealExpend(final View view, final Animator.AnimatorListener ll){
        if(view != null){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                Animator anim = ViewAnimationUtils.createCircularReveal(view, view.getWidth() / 2, view.getHeight() / 2,
                        0, Math.max(view.getHeight(), view.getWidth()));
                anim.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        if(ll != null){
                            ll.onAnimationStart(animation);
                        }
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if(ll != null){
                            ll.onAnimationEnd(animation);
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        if(ll != null){
                            ll.onAnimationCancel(animation);
                        }

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                        if(ll != null){
                            ll.onAnimationRepeat(animation);
                        }

                    }
                });
                anim.start();
            }else{
                AlphaAnimation anim = new AlphaAnimation(0, 1);
                anim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        if(ll != null){
                            ll.onAnimationStart(null);
                        }

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        if(ll != null){
                            ll.onAnimationEnd(null);
                        }

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        if(ll != null){
                            ll.onAnimationRepeat(null);
                        }

                    }
                });
                view.setAnimation(anim);
                anim.start();
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void circularReveal(final View view, final Animator.AnimatorListener ll){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Animator anim = ViewAnimationUtils.createCircularReveal(view, view.getWidth() / 2, view.getHeight() / 2,
                    Math.max(view.getHeight(), view.getWidth()), 0);
            anim.addListener(new Animator.AnimatorListener() {
                public void onAnimationStart(Animator animation) {
                    if(ll != null){
                        ll.onAnimationStart(animation);
                    }
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    if(ll != null){
                        ll.onAnimationEnd(animation);
                    }
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    if(ll != null){
                        ll.onAnimationCancel(animation);
                    }

                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                    if(ll != null){
                        ll.onAnimationRepeat(animation);
                    }

                }
            });
            anim.start();
        }else{
            AlphaAnimation anim = new AlphaAnimation(1, 0);
            anim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    if(ll != null){
                        ll.onAnimationStart(null);
                    }

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if(ll != null){
                        ll.onAnimationEnd(null);
                    }

                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                    if(ll != null){
                        ll.onAnimationRepeat(null);
                    }

                }
            });
            view.setAnimation(anim);
            anim.start();
        }
    }


}
