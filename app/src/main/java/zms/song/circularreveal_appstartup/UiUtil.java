package zms.song.circularreveal_appstartup;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by song on 2018/3/12.
 */

public class UiUtil {

    public interface OnCircularRevealListener {
        void onShowEnd();
        void onHideEnd();
    }

    public static void applyCircularReveal(@NonNull final View animEffectView,
                                           int triggerPointX,//揭露效果触发点x
                                           int triggerPointY,//揭露效果触发点
                                           final boolean show,//显示还是隐藏
                                           float startRadius,//揭露效果开始时的半径
                                           float endRadius,//揭露效果结束时的半径
                                           final OnCircularRevealListener listener) {

        Animator animator = ViewAnimationUtils.createCircularReveal(animEffectView, triggerPointX, triggerPointY, startRadius, endRadius);
        animEffectView.setVisibility(View.VISIBLE);
        animator.setDuration(800);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (show) {
                    animEffectView.setVisibility(View.VISIBLE);
                    if (listener != null) {
                        listener.onShowEnd();
                    }
                } else {
                    animEffectView.setVisibility(View.GONE);
                    if (listener != null) {
                        listener.onHideEnd();
                    }
                }
            }
        });
        animator.start();
    }

    public static void applyCircularReveal(@NonNull final View animEffectView, int triggerPointX, int triggerPointY, final boolean show, final OnCircularRevealListener listener) {

        //获取animEffectView的中心点坐标
        int[] animEffectViewLocation = new int[2];
        animEffectView.getLocationInWindow(animEffectViewLocation);
        int sourcePointX = animEffectViewLocation[0] + animEffectView.getWidth() / 2;
        int sourcePointY = animEffectViewLocation[1] + animEffectView.getWidth() / 2;

        int rippleW = triggerPointX < sourcePointX ? animEffectView.getWidth() - triggerPointX : triggerPointX -  animEffectViewLocation[0];
        int rippleH = triggerPointY < sourcePointY ? animEffectView.getHeight() - triggerPointY : triggerPointY -  animEffectViewLocation[1];

        float maxRadius = (float) Math.sqrt((rippleW*rippleW + rippleH*rippleH));
        float startRadius;
        float endRadius;
        if (show) {
            startRadius = 0;
            endRadius = maxRadius;
        } else {
            startRadius = maxRadius;
            endRadius = 0;
        }

        applyCircularReveal(animEffectView, triggerPointX, triggerPointY, show, startRadius, endRadius, listener);
    }

    public static void applyCircularReveal(@NonNull View triggerView, @NonNull View animEffectView, boolean show, OnCircularRevealListener listener) {

        //获取triggerView的中心点坐标
        int[] triggerViewLocation = new int[2];
        triggerView.getLocationInWindow(triggerViewLocation);
        int triggerPointX = triggerViewLocation[0] + triggerView.getWidth() / 2;
        int triggerPointY = triggerViewLocation[1] + triggerView.getWidth() / 2;

        applyCircularReveal(animEffectView, triggerPointX, triggerPointY, show, listener);
    }
}
