package jirayu.pond.footreflexology.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.TextView;


import com.inthecheesefactory.thecheeselibrary.view.BaseCustomViewGroup;
import com.inthecheesefactory.thecheeselibrary.view.state.BundleSavedState;

import jirayu.pond.footreflexology.R;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class DetailsListItem extends BaseCustomViewGroup {

    TextView tvDiseaseName, tvDetail, tvTreatment, tvRecommendation, tvRecommendationTitle, tvPageNumber, tvShouldEat, tvShouldNotEat;

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
        tvRecommendation = (TextView) findViewById(R.id.tvRecommendation);
        tvRecommendationTitle = (TextView) findViewById(R.id.tvRecommendationTitle);
        tvPageNumber = (TextView) findViewById(R.id.tvPageNumber);
        tvShouldEat = (TextView) findViewById(R.id.tvShouldEat);
        tvShouldNotEat = (TextView) findViewById(R.id.tvShouldNotEat);
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

}
