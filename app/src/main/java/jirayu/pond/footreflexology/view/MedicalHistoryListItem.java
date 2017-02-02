package jirayu.pond.footreflexology.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.inthecheesefactory.thecheeselibrary.view.BaseCustomViewGroup;
import com.inthecheesefactory.thecheeselibrary.view.state.BundleSavedState;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import jirayu.pond.footreflexology.R;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class MedicalHistoryListItem extends BaseCustomViewGroup {

    /************
     * Variables
     ************/

    TextView tvDiseaseName, tvBehavior, tvCreatedAt, tvUpdatedAt;
    View viewColourItemMedicalHistory;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ROOT);

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
        String createAt = simpleDateFormat.format(createdAt);
        tvCreatedAt.setText(convertADtoBE(createAt));
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        String updateAt = simpleDateFormat.format(updatedAt);
        tvUpdatedAt.setText(convertADtoBE(updateAt));
    }

    private String convertADtoBE(String date) {
        // แปลง ค.ศ. เป็น พ.ศ. โดยการนำ 543 ไปบวก
        return date.substring(0, 2) + "-" + date.substring(3, 5) + "-" + String.valueOf(Integer.parseInt(date.substring(6)) + 543);
    }

    public void setViewColourItemMedicalHistory(int colourCode){
        // แย่ลง [สีแดง]
        if (colourCode == 1) {
            viewColourItemMedicalHistory.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorWorse));
            viewColourItemMedicalHistory.invalidate();
        }
        // ทรงตัว [สีเหลือง]
        if (colourCode == 2) {
            viewColourItemMedicalHistory.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPoise));
            viewColourItemMedicalHistory.invalidate();
        }
        // ดีขึ้น [สีเขียว]
        if (colourCode == 3) {
            viewColourItemMedicalHistory.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorBetter));
            viewColourItemMedicalHistory.invalidate();
        }
        // หายเป็นปกติ [สีฟ้า]
        if (colourCode == 4) {
            viewColourItemMedicalHistory.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorNormal));
            viewColourItemMedicalHistory.invalidate();
        }

    }

    /****************
     * Listener Zone
     ****************/

    /***************
     * Inner Class
     **************/

}
