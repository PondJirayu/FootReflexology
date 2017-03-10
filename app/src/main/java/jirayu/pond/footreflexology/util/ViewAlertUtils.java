package jirayu.pond.footreflexology.util;

import android.content.Context;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;

import jirayu.pond.footreflexology.R;

/**
 * Created by lp700 on 10/3/2560.
 */

public class ViewAlertUtils {

    public static View getViewAlert(Context context, int status) {
        // Create View
        View view = new View(context);
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
        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(600);
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        view.startAnimation(anim);

        return view;
    }

    public static FrameLayout.LayoutParams getParams(int width, int height, int leftMargin, int topMargin){
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width, height);
        params.leftMargin = leftMargin;
        params.topMargin = topMargin;
        return params;
    }

}
