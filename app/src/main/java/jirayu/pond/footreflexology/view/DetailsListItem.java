package jirayu.pond.footreflexology.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;


import com.inthecheesefactory.thecheeselibrary.view.BaseCustomViewGroup;
import com.inthecheesefactory.thecheeselibrary.view.state.BundleSavedState;
import com.inthecheesefactory.thecheeselibrary.widget.AdjustableImageButton;

import jirayu.pond.footreflexology.R;

public class DetailsListItem extends BaseCustomViewGroup implements View.OnClickListener {

    /************
     * Variables
     ************/

    AdjustableImageButton imgButtonExpandView;
    TextView tvDiseaseName, tvDetail, tvTreatment, tvTreatmentTitle,
            tvRecommendation, tvRecommendationTitle,
            tvPageNumber, tvShouldEat, tvShouldEatTitle, tvShouldNotEat,
            tvShouldNotEatTitle, tvExpandView, tvBehaviorTitle, tvBehavior;
    Boolean tvVisible = false;

    /************
     * Functions
     ************/

    public DetailsListItem(Context context) {
        super(context);
        initInflate();
        initInstances();
    }

    public DetailsListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstances();
        initWithAttrs(attrs, 0, 0);
    }

    public DetailsListItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstances();
        initWithAttrs(attrs, defStyleAttr, 0);
    }

    @TargetApi(21)
    public DetailsListItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initInflate();
        initInstances();
        initWithAttrs(attrs, defStyleAttr, defStyleRes);
    }

    private void initInflate() {
        inflate(getContext(), R.layout.list_item_details, this);
    }

    private void initInstances() {
        // findViewById here
        tvDiseaseName = (TextView) findViewById(R.id.tvDiseaseName);
        tvDetail = (TextView) findViewById(R.id.tvDetail);
        tvTreatment = (TextView) findViewById(R.id.tvTreatment);
        tvTreatmentTitle = (TextView) findViewById(R.id.tvTreatmentTitle);
        tvRecommendation = (TextView) findViewById(R.id.tvRecommendation);
        tvRecommendationTitle = (TextView) findViewById(R.id.tvRecommendationTitle);
        tvPageNumber = (TextView) findViewById(R.id.tvPageNumber);
        tvShouldEat = (TextView) findViewById(R.id.tvShouldEat);
        tvShouldEatTitle = (TextView) findViewById(R.id.tvShouldEatTitle);
        tvShouldNotEat = (TextView) findViewById(R.id.tvShouldNotEat);
        tvShouldNotEatTitle = (TextView) findViewById(R.id.tvShouldNotEatTitle);
        imgButtonExpandView = (AdjustableImageButton) findViewById(R.id.imgButtonExpandView);
        tvExpandView = (TextView) findViewById(R.id.tvExpandView);
        tvBehaviorTitle = (TextView) findViewById(R.id.tvBehaviorTitle);
        tvBehavior = (TextView) findViewById(R.id.tvBehavior);

        // Handle Click Button
        imgButtonExpandView.setOnClickListener(this);
    }

    private void initWithAttrs(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        /*
        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.StyleableName,
                defStyleAttr, defStyleRes);

        try {

        } finally {
            a.recycle();
        }
        */
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();

        BundleSavedState savedState = new BundleSavedState(superState);
        // Save Instance State(s) here to the 'savedState.getBundle()'
        // for example,
        // savedState.getBundle().putString("key", value);

        return savedState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        BundleSavedState ss = (BundleSavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());

        Bundle bundle = ss.getBundle();
        // Restore State from bundle here
    }

    public void setDiseaseName(String text) {
        tvDiseaseName.setText(text);
    }

    public void setDetail(String text) {
        tvDetail.setText(text);
    }

    public void setTreatment(String text) {
        tvTreatment.setText(text);
    }

    public void setRecommendation(String text) {
        tvRecommendation.setText(text);
    }

    public void setShouldEat(String text) {
        tvShouldEat.setText(text);
    }

    public void setShouldNotEat(String text) {
        tvShouldNotEat.setText(text);
    }

    public void setPageNumber(int pageNumber) {
        tvPageNumber.setText(String.valueOf(pageNumber + 1));
    }

    public void setBehavior(String behavior) {
        switch (behavior) {
            case "แย่":
                tvBehavior.setText("แย่");
                displayBehavior();
                break;
            case "ทรงตัว":
                tvBehavior.setText("ทรงตัว");
                displayBehavior();
                break;
            case "ดีขึ้น":
                tvBehavior.setText("ดีขึ้น");
                displayBehavior();
                break;
            default:
                break;
        }
    }

    // แสดงอาการ
    private void displayBehavior() {
        tvBehaviorTitle.setVisibility(Switch.VISIBLE);
        tvBehavior.setVisibility(Switch.VISIBLE);
    }

    // แสดงวิวย่อ
    private void expandLess() {
        tvTreatment.setVisibility(Switch.GONE);
        tvTreatmentTitle.setVisibility(Switch.GONE);
        tvRecommendation.setVisibility(Switch.GONE);
        tvRecommendationTitle.setVisibility(Switch.GONE);
        tvShouldEat.setVisibility(Switch.GONE);
        tvShouldEatTitle.setVisibility(Switch.GONE);
        tvShouldNotEat.setVisibility(Switch.GONE);
        tvShouldNotEatTitle.setVisibility(Switch.GONE);
        tvExpandView.setText(R.string.expand_more);
        imgButtonExpandView.setImageResource(R.drawable.ic_expand_more_black_24dp);
    }

    // แสดงวิวเต็ม
    private void expandMore() {
        tvTreatment.setVisibility(Switch.VISIBLE);
        tvTreatmentTitle.setVisibility(Switch.VISIBLE);
        tvRecommendation.setVisibility(Switch.VISIBLE);
        tvRecommendationTitle.setVisibility(Switch.VISIBLE);
        tvShouldEat.setVisibility(Switch.VISIBLE);
        tvShouldEatTitle.setVisibility(Switch.VISIBLE);
        tvShouldNotEat.setVisibility(Switch.VISIBLE);
        tvShouldNotEatTitle.setVisibility(Switch.VISIBLE);
        tvExpandView.setText(R.string.expand_less);
        imgButtonExpandView.setImageResource(R.drawable.ic_expand_less_black_24dp);
    }

    public void setVisibilityTextViewBehavior() {
        tvBehaviorTitle.setVisibility(Switch.GONE);
        tvBehavior.setVisibility(Switch.GONE);
    }

    /****************
     * Listener Zone
     ****************/

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgButtonExpandView:
                if (tvVisible) {
                    expandLess();
                    tvVisible = false;
                } else {
                    expandMore();
                    tvVisible = true;
                }
                break;
        }
    }

}
