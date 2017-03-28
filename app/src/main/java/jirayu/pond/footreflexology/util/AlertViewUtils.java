package jirayu.pond.footreflexology.util;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Switch;

import jirayu.pond.footreflexology.R;

/**
 * Created by lp700 on 10/3/2560.
 */

public class AlertViewUtils {

    /************
     * Variables
     ***********/

    private Button alertView;
    private Animation anim;
    private int status, width, height, leftMargin, topMargin;
    private Context context;

    /************
     * Functions
     ************/

    public AlertViewUtils(Context context, int status, int width, int height, int topMargin, int leftMargin) {
        setStatus(status);
        setWidth(width);
        setHeight(height);
        setTopMargin(topMargin);
        setLeftMargin(leftMargin);
        setContext(context);
        initInstance();
    }

    private void initInstance() {
        alertView = new Button(getContext());   // Create View
        // Set Background View
        switch (getStatus()) {
            case 1: // อาการแย่
                alertView.setBackgroundResource(R.drawable.shape_view_alert_red_color);
                break;
            case 2: // อาการทรงตัว
                alertView.setBackgroundResource(R.drawable.shape_view_alert_yellow_color);
                break;
            case 3: // อาการดีขึ้น
                alertView.setBackgroundResource(R.drawable.shape_view_alert_green_color);
                break;
            case 4: // เขตตอบสนองที่เลือก
                alertView.setBackgroundResource(R.drawable.shape_view_alert_grey_color);
                break;
            default:
                break;
        }
        anim = AnimationUtils.loadAnimation(getContext(), R.anim.alert_view_alpha_anim); // Create Animation
        alertView.startAnimation(anim); // Start View Animation
    }

    public FrameLayout.LayoutParams getParams() {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(getWidth(), getHeight());
        params.leftMargin = getLeftMargin();
        params.topMargin = getTopMargin();
        return params;
    }

    public void hideAlertView() {
        alertView.setVisibility(Switch.GONE);
        anim.setRepeatCount(0);
    }

    public void showAlertView() {
        anim.setRepeatCount(Animation.INFINITE);
        alertView.startAnimation(anim);
        alertView.setVisibility(Switch.VISIBLE);
    }

    public View getAlertView() {
        return alertView;
    }

    private int getStatus() {
        return status;
    }

    private int getWidth() {
        return width;
    }

    private int getHeight() {
        return height;
    }

    private int getLeftMargin() {
        return leftMargin;
    }

    private int getTopMargin() {
        return topMargin;
    }

    private Context getContext() {
        return context;
    }

    private void setStatus(int status) {
        this.status = status;
    }

    private void setWidth(int width) {
        this.width = width;
    }

    private void setHeight(int height) {
        this.height = height;
    }

    private void setLeftMargin(int leftMargin) {
        this.leftMargin = leftMargin;
    }

    private void setTopMargin(int topMargin) {
        this.topMargin = topMargin;
    }

    private void setContext(Context context) {
        this.context = context;
    }

}
