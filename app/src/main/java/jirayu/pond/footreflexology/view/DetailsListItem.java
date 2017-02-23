package jirayu.pond.footreflexology.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;


import com.inthecheesefactory.thecheeselibrary.view.BaseCustomViewGroup;
import com.inthecheesefactory.thecheeselibrary.view.state.BundleSavedState;

import jirayu.pond.footreflexology.R;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class DetailsListItem extends BaseCustomViewGroup {

    TextView tvDiseaseName, tvDetail, tvTreatment, tvRecommend, tvRecommendBold;
    View viewUnderLine;

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
        tvRecommend = (TextView) findViewById(R.id.tvRecommend);
        tvRecommendBold = (TextView) findViewById(R.id.tvRecommendBold);
        viewUnderLine = findViewById(R.id.viewUnderLine);
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

    public void setRecommend(String text) {
        tvRecommend.setText(text);
    }

    /*
     * กำหนดสี UnderLine โดยมีเงื่อนไขเป็นเลขคู่ - คี่
     */
    public void setUnderLine(int position) {
        if ((position % 2) == 0) {
            viewUnderLine.setBackgroundColor(ContextCompat.getColor(getContext(),
                    R.color.view_even_color));
            viewUnderLine.invalidate();
        } else {
            viewUnderLine.setBackgroundColor(ContextCompat.getColor(getContext(),
                    R.color.view_odd_color));
            viewUnderLine.invalidate();
        }
    }

}
