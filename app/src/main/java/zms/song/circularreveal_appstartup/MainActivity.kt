package zms.song.circularreveal_appstartup

import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import android.widget.Toast

class MainActivity : CircularRevealBaseActivity() {

    private var mStartX = 0
    private var mStartY = 0
    private var mMainView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.apply {
            setBackgroundDrawable(null)
            statusBarColor = Color.TRANSPARENT
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        }

        setContentView(R.layout.activity_main)
        mMainView = findViewById(R.id.main_view)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        /*
        主要是通过 getSourceBounds() 来获取app图标在launcher上的位置区域rect
        * */
        val rect = intent.sourceBounds
        if (rect == null) {
            /*
            非触摸形式启动app的话，rect是为null的。
            比如从别的app跳转启动的话，rect就是null的。
            如果rect为空，就让app从屏幕中心位置开始揭露效果动画。
            * */
            val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val displayMetrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            mStartX = displayMetrics.widthPixels / 2
            mStartY = displayMetrics.heightPixels / 2
        } else {
            mStartX = rect.centerX()
            mStartY = rect.centerY()
        }

        /*
        * 设置需要添加揭露效果的view，
        * 这里设置为mMainView，因为mMainView填充着整个屏幕，对mMainView实施揭露效果，可以营造出全屏的动画。
        * */
        setCircularRevealView(mMainView)
    }

    override fun onBackPressed() {
        runEnterCircularReveal(mMainView, false, mStartX, mStartY)
    }

    override fun onPreDraw(): Boolean {
        removeCircularRevealView(mMainView)//记得一定要对之前添加进来监听的视图取消监听
        runEnterCircularReveal(mMainView, true, mStartX, mStartY)
        return true
    }

    /*
    * 当揭露效果展现完毕，会调用这个方法
    * */
    override fun onCircularRevealShowEnd() {
        Toast.makeText(this, "onCircularRevealShowEnd", Toast.LENGTH_SHORT).show()
    }

    /*
    * 当揭露效果隐藏完毕，会调用这个方法
    * */
    override fun onCircularRevealHideEnd() {
        Toast.makeText(this, "onCircularRevealHideEnd", Toast.LENGTH_SHORT).show()
        finish()
    }

    /*
    * 当揭露效果刚开始动画时，会调用这个方法
    * */
    override fun onCircularRevealStart() {
        Toast.makeText(this, "onCircularRevealStart", Toast.LENGTH_SHORT).show()
    }
}
