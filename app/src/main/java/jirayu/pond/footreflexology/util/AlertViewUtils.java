package jirayu.pond.footreflexology.util;

import android.content.Context;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.Switch;

import jirayu.pond.footreflexology.R;

/**
 * Created by lp700 on 10/3/2560.
 */

public class AlertViewUtils {

    private View view;
    private Animation anim;
    private int status, width, height, leftMargin, topMargin;

    public AlertViewUtils(Context context, int status, int width, int height, int leftMargin, int topMargin) {
        view = new View(context);
        this.status = status;
        this.width = width;
        this.height = height;
        this.leftMargin = leftMargin;
        this.topMargin = topMargin;
    }

    public View getAlertView() {
        switch (status) {
            case 1: // อาการแย่
                view.setBackgroundResource(R.drawable.shape_view_alert_red_color);
                break;
            case 2: // อาการทรงตัว
                view.setBackgroundResource(R.drawable.shape_view_alert_yellow_color);
                break;
            case 3: // อาการดีขึ้น
                view.setBackgroundResource(R.drawable.shape_view_alert_green_color);
                break;
            case 4: // เขตตอบสนองที่เลือก
                view.setBackgroundResource(R.drawable.shape_view_alert_grey_color);
                break;
            default:
                break;
        }

        // Create Animation of View
        anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(600);
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        view.startAnimation(anim);

        return view;
    }

    public FrameLayout.LayoutParams getParams() {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width, height);
        params.leftMargin = leftMargin;
        params.topMargin = topMargin;
        return params;
    }

    public void hideAlertView() {
        anim.setRepeatCount(0);
        view.setVisibility(Switch.GONE);
    }

    public void showAlertView() {
        view.setVisibility(Switch.VISIBLE);
        anim.setRepeatCount(Animation.INFINITE);
        view.startAnimation(anim);
    }

}
