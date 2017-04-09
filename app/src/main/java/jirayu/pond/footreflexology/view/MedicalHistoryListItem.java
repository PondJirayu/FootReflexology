package jirayu.pond.footreflexology.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.inthecheesefactory.thecheeselibrary.view.BaseCustomViewGroup;
import com.inthecheesefactory.thecheeselibrary.view.state.BundleSavedState;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;

import jirayu.pond.footreflexology.R;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class MedicalHistoryListItem extends BaseCustomViewGroup {

    /************
     * Variables
     ************/

    TextView tvDiseaseName, tvBehavior, tvCreatedAt, tvUpdatedAt, tvNumber;
    View viewColourItemMedicalHistory;

    SimpleDateFormat simpleDateFormat;

    /************
     * Functions
     ************/

    public MedicalHistoryListItem(Context context) {
        super(context);
        initInflate();
        initInstances();
    }

    public MedicalHistoryListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstances();
        initWithAttrs(attrs, 0, 0);
    }

    public MedicalHistoryListItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstances();
        initWithAttrs(attrs, defStyleAttr, 0);
    }

    @TargetApi(21)
    public MedicalHistoryListItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initInflate();
        initInstances();
        initWithAttrs(attrs, defStyleAttr, defStyleRes);
    }

    private void initInflate() {
        inflate(getContext(), R.layout.list_item_medical_history, this);
    }

    private void initInstances() {
        // findViewById here
        tvDiseaseName = (TextView) findViewById(R.id.tvDiseaseName);
        tvBehavior = (TextView) findViewById(R.id.tvBehavior);
        tvCreatedAt = (TextView) findViewById(R.id.tvCreatedAt);
        tvUpdatedAt = (TextView) findViewById(R.id.tvUpdatedAt);
        viewColourItemMedicalHistory = findViewById(R.id.viewColourItemMedicalHistory);
        tvNumber = (TextView) findViewById(R.id.tvNumber);
        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy  HH:mm", Locale.ROOT);
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

    public void setBehavior(String text) {
        tvBehavior.setText(text);
    }

    public void setCreatedAt(Timestamp createdAt) {
        tvCreatedAt.setText(simpleDateFormat.format(createdAt));
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        tvUpdatedAt.setText(simpleDateFormat.format(updatedAt));
    }

    public void setRank(int number) {
        // convert int to string
        tvNumber.setText(String.valueOf(number));
    }

    public void setViewColourItemMedicalHistory(int colourCode) {
        switch (colourCode) {
            case 1: // แย่ลง [สีแดง]
                viewColourItemMedicalHistory.setBackgroundColor(ContextCompat.getColor(getContext(),
                        R.color.worse_color));
                break;
            case 2: // ทรงตัว [สีเหลือง]
                viewColourItemMedicalHistory.setBackgroundColor(ContextCompat.getColor(getContext(),
                        R.color.poise_color));
                break;
            case 3: // ดีขึ้น [สีเขียว]
                viewColourItemMedicalHistory.setBackgroundColor(ContextCompat.getColor(getContext(),
                        R.color.better_color));
                break;
            default: // หายเป็นปกติ [สีฟ้า]
                viewColourItemMedicalHistory.setBackgroundColor(ContextCompat.getColor(getContext(),
                        R.color.normal_color));
                break;
        }
    }

    /****************
     * Listener Zone
     ****************/

    /***************
     * Inner Class
     **************/

}
