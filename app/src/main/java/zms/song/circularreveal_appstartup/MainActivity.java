package zms.song.circularreveal_appstartup;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

public class MainActivity extends CircularRevealBaseActivity {

    int mStartX = 0;
    int mStartY = 0;
    private View mMainView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setBackgroundDrawable(null);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

        setContentView(R.layout.activity_main);
        mMainView = findViewById(R.id.main_view);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*
        主要是通过 getSourceBounds() 来获取app图标在launcher上的位置区域rect
        * */
        Rect rect = getIntent().getSourceBounds();
        if (rect == null) {
            /*
            非触摸形式启动app的话，rect是为null的。
            比如从别的app跳转启动的话，rect就是null的。
            如果rect为空，就让app从屏幕中心位置开始揭露效果动画。
            * */
            WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
            if (windowManager != null) {
                DisplayMetrics displayMetrics = new DisplayMetrics();
                windowManager.getDefaultDisplay().getMetrics(displayMetrics);
                mStartX = displayMetrics.widthPixels / 2;
                mStartY = displayMetrics.heightPixels / 2;
            }
        } else {
            mStartX = rect.centerX();
            mStartY = rect.centerY();
        }

        /*
        * 设置需要添加揭露效果的view，
        * 这里设置为mMainView，因为mMainView填充着整个屏幕，对mMainView实施揭露效果，可以营造出全屏的动画。
        * */
        setCircularRevealView(mMainView);
    }

    @Override
    public void onBackPressed() {
        runEnterCircularReveal(mMainView, false, mStartX, mStartY);
    }

    @Override
    public boolean onPreDraw() {
        removeCircularRevealView(mMainView);//记得一定要对之前添加进来监听的视图取消监听
        runEnterCircularReveal(mMainView, true, mStartX, mStartY);
        return true;
    }

    /*
    * 当揭露效果展现完毕，会调用这个方法
    * */
    @Override
    protected void onCircularRevealShowEnd() {
        Toast.makeText(this, "onCircularRevealShowEnd", Toast.LENGTH_SHORT).show();
    }

    /*
    * 当揭露效果隐藏完毕，会调用这个方法
    * */
    @Override
    protected void onCircularRevealHideEnd() {
        Toast.makeText(this, "onCircularRevealHideEnd", Toast.LENGTH_SHORT).show();
        finish();
    }

    /*
    * 当揭露效果刚开始动画时，会调用这个方法
    * */
    @Override
    protected void onCircularRevealStart() {
        Toast.makeText(this, "onCircularRevealStart", Toast.LENGTH_SHORT).show();
    }
}
