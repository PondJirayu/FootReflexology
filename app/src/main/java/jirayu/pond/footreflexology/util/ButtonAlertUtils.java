package jirayu.pond.footreflexology.util;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Switch;

import jirayu.pond.footreflexology.R;
import jirayu.pond.footreflexology.activity.ShowDetailsActivity;

/**
 * Created by lp700 on 10/3/2560.
 */

public class ButtonAlertUtils implements View.OnClickListener {

    /************
     * Variables
     ***********/

    private Button btnAlert;
    private Animation anim;
    private int status, width, height, leftMargin, topMargin;
    private String organName;
    private Context context;

    /************
     * Functions
     ************/

    public ButtonAlertUtils(Context context, int status, int width, int height, int topMargin, int leftMargin) {
        this.status = status;
        this.width = width;
        this.height = height;
        this.topMargin = topMargin;
        this.leftMargin = leftMargin;
        this.context = context;
        initInstance();
    }

    private void initInstance() {
        btnAlert = new Button(context);   // Create View
        btnAlert.setId(R.id.btnAlert); // Set Id
        setBackgroundView(status); // Set BackgroundView

        anim = AnimationUtils.loadAnimation(context, R.anim.alert_view_alpha_anim); // Create Animation
        btnAlert.startAnimation(anim); // Start View Animation

        // Handle Click btnAlert
        btnAlert.setOnClickListener(this);
    }

    public void hideAlertView() {
        btnAlert.setVisibility(Switch.GONE);
        anim.setRepeatCount(0);
    }

    public void showAlertView() {
        anim.setRepeatCount(Animation.INFINITE);
        btnAlert.startAnimation(anim);
        btnAlert.setVisibility(Switch.VISIBLE);
    }

    public void setBackgroundView(int status) {
        switch (status) {
            case 1: // อาการแย่
                btnAlert.setBackgroundResource(R.drawable.shape_view_alert_red_color);
                break;
            case 2: // อาการทรงตัว
                btnAlert.setBackgroundResource(R.drawable.shape_view_alert_yellow_color);
                break;
            case 3: // อาการดีขึ้น
                btnAlert.setBackgroundResource(R.drawable.shape_view_alert_green_color);
                break;
            case 4: // เขตตอบสนองที่เลือก
                btnAlert.setBackgroundResource(R.drawable.shape_view_alert_grey_color);
                break;
            default:
                break;
        }
    }

    public FrameLayout.LayoutParams getParams() {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width, height);
        params.leftMargin = leftMargin;
        params.topMargin = topMargin;
        return params;
    }

    public Button getBtnAlert() {
        return btnAlert;
    }

    public String getOrganName() {
        return organName;
    }

    public void setOrganName(String organName) {
        this.organName = organName;
    }

    /****************
     * Listener Zone
     ****************/

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context, ShowDetailsActivity.class);
        intent.putExtra("result", organName);
        context.startActivity(intent);
    }

    /**************
     * Inner Class
     **************/

}
