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

        Rect rect = getIntent().getSourceBounds();
        if (rect == null) {
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

        setCircularRevealView(mMainView);
    }

    @Override
    public void onBackPressed() {
        runEnterCircularReveal(mMainView, false, mStartX, mStartY);
    }

    @Override
    public boolean onPreDraw() {
        removeCircularRevealView(mMainView);
        runEnterCircularReveal(mMainView, true, mStartX, mStartY);
        return true;
    }

    @Override
    protected void onCircularRevealShowEnd() {
        Toast.makeText(this, "onCircularRevealShowEnd", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCircularRevealHideEnd() {
        Toast.makeText(this, "onCircularRevealHideEnd", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    protected void onCircularRevealStart() {
        Toast.makeText(this, "onCircularRevealStart", Toast.LENGTH_SHORT).show();
    }
}
