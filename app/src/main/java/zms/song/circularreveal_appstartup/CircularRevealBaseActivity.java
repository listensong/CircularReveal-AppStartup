package zms.song.circularreveal_appstartup;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * Created by song on 2018/3/12.
 */

public class CircularRevealBaseActivity extends AppCompatActivity implements ViewTreeObserver.OnPreDrawListener {

    private boolean mIsInAnim = false;

    @Override
    public boolean onPreDraw() {
        return false;
    }

    protected void setCircularRevealView(View view) {
        if (view == null) {
            return;
        }
        view.getViewTreeObserver().addOnPreDrawListener(this);
    }

    protected void removeCircularRevealView(View view) {
        if (view == null) {
            return;
        }
        view.getViewTreeObserver().removeOnPreDrawListener(this);
    }

    protected void runEnterCircularReveal(View view , boolean show, int x, int y) {
        if (view == null) {
            return;
        }

        if (isFinishing() || isDestroyed()) {
            return;
        }

        if (mIsInAnim) {
            return;
        }

        mIsInAnim = true;
        onCircularRevealStart();
        UiUtil.applyCircularReveal(view, x, y, show, new UiUtil.OnCircularRevealListener() {
            @Override
            public void onShowEnd() {
                mIsInAnim = false;
                onCircularRevealShowEnd();
            }

            @Override
            public void onHideEnd() {
                mIsInAnim = false;
                onCircularRevealHideEnd();
            }
        });
    }

    protected void onCircularRevealShowEnd(){

    }

    protected void onCircularRevealHideEnd(){

    }

    protected void onCircularRevealStart() {

    }
}
