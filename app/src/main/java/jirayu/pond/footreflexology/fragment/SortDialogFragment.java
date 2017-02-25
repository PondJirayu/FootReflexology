package jirayu.pond.footreflexology.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;


/**
 * Created by lp700 on 24/2/2560.
 */

public class SortDialogFragment {

    private Context context;
    private int which;
    private CharSequence title;
    private CharSequence[] list = {
                    "อาการ : แย่ลง - ปกติ",
                    "อาการ : ปกติ - แย่ลง",
                    "วันที่เข้ารับการรักษา : ใหม่ - เก่า",
                    "วันที่เข้ารับการรักษา : เก่า - ใหม่",
                    "วันที่แก้ไข : ใหม่ - เก่า",
                    "วันที่แก้ไข : เก่า - ใหม่"
            };

    public SortDialogFragment(Context context, CharSequence title) {
        this.context = context;
        this.title = title;
    }

    public AlertDialog getDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setSingleChoiceItems(list, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setWhich(which);
            }
        });
        builder.setNegativeButton("ยกเลิก", null);
        builder.create();
        return builder.show();
    }

    public void setWhich(int which) {
        this.which = which;
    }

    public int getWhich() {
        return which;
    }
}
