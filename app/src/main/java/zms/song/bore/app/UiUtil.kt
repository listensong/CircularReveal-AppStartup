package zms.song.bore.app

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.DecelerateInterpolator
import kotlin.math.sqrt

/**
 * Created by song on 2018/3/12.
 */

object UiUtil {

    interface OnCircularRevealListener {
        fun onShowEnd()
        fun onHideEnd()
    }

    /*这部分代码是copy自网上一位网友的代码，忘记链接了，在此说明一下，找到后补链接，或者侵删*/
    fun applyCircularReveal(animEffectView: View,
                            triggerPointX: Int, //揭露效果触发点x
                            triggerPointY: Int, //揭露效果触发点
                            show: Boolean, //显示还是隐藏
                            startRadius: Float, //揭露效果开始时的半径
                            endRadius: Float, //揭露效果结束时的半径
                            listener: OnCircularRevealListener?) {

        val animator = ViewAnimationUtils.createCircularReveal(animEffectView, triggerPointX, triggerPointY, startRadius, endRadius)
        animEffectView.visibility = View.VISIBLE
        animator.duration = 800
        animator.interpolator = DecelerateInterpolator()
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                if (show) {
                    animEffectView.visibility = View.VISIBLE
                    listener?.onShowEnd()
                } else {
                    animEffectView.visibility = View.GONE
                    listener?.onHideEnd()
                }
            }
        })
        animator.start()
    }

    fun applyCircularReveal(animEffectView: View,
                            triggerPointX: Int,
                            triggerPointY: Int,
                            show: Boolean, listener: OnCircularRevealListener) {

        //获取animEffectView的中心点坐标
        val animEffectViewLocation = IntArray(2)
        animEffectView.getLocationInWindow(animEffectViewLocation)
        val sourcePointX = animEffectViewLocation[0] + animEffectView.width / 2
        val sourcePointY = animEffectViewLocation[1] + animEffectView.width / 2

        val rippleW = if (triggerPointX < sourcePointX) animEffectView.width - triggerPointX else triggerPointX - animEffectViewLocation[0]
        val rippleH = if (triggerPointY < sourcePointY) animEffectView.height - triggerPointY else triggerPointY - animEffectViewLocation[1]

        val maxRadius = sqrt((rippleW * rippleW + rippleH * rippleH).toDouble()).toFloat()
        val startRadius: Float
        val endRadius: Float
        if (show) {
            startRadius = 0f
            endRadius = maxRadius
        } else {
            startRadius = maxRadius
            endRadius = 0f
        }

        applyCircularReveal(animEffectView, triggerPointX, triggerPointY, show,
                startRadius, endRadius, listener)
    }

    fun applyCircularReveal(triggerView: View,
                            animEffectView: View,
                            show: Boolean,
                            listener: OnCircularRevealListener) {

        //获取triggerView的中心点坐标
        val triggerViewLocation = IntArray(2)
        triggerView.getLocationInWindow(triggerViewLocation)
        val triggerPointX = triggerViewLocation[0] + triggerView.width / 2
        val triggerPointY = triggerViewLocation[1] + triggerView.width / 2

        applyCircularReveal(animEffectView, triggerPointX, triggerPointY, show, listener)
    }
}
