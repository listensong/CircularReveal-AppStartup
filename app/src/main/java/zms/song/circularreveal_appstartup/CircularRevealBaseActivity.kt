package zms.song.circularreveal_appstartup

import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.view.ViewTreeObserver

/**
 * Created by song on 2018/3/12.
 */

abstract class CircularRevealBaseActivity : AppCompatActivity(), ViewTreeObserver.OnPreDrawListener {

    private var mIsInAnim = false

    override fun onPreDraw(): Boolean {
        return false
    }

    protected val isActivityAvailable: Boolean
    get() {
        if (isFinishing || isDestroyed) {
            return false
        }
        return true
    }

    protected fun setCircularRevealView(view: View?) {
        view ?: return
        view.viewTreeObserver.addOnPreDrawListener(this)
    }

    protected fun removeCircularRevealView(view: View?) {
        view ?: return
        view.viewTreeObserver.removeOnPreDrawListener(this)
    }

    protected fun runEnterCircularReveal(view: View?, show: Boolean, x: Int, y: Int) {
        view ?: return
        isActivityAvailable || return

        if (mIsInAnim) {
            return
        }

        mIsInAnim = true
        onCircularRevealStart()
        UiUtil.applyCircularReveal(view, x, y, show, object : UiUtil.OnCircularRevealListener {
            override fun onShowEnd() {
                mIsInAnim = false
                onCircularRevealShowEnd()
            }

            override fun onHideEnd() {
                mIsInAnim = false
                onCircularRevealHideEnd()
            }
        })
    }

    protected open fun onCircularRevealShowEnd() {

    }

    protected open fun onCircularRevealHideEnd() {

    }

    protected open fun onCircularRevealStart() {

    }
}
