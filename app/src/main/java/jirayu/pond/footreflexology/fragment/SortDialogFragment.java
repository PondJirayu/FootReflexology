package jirayu.pond.footreflexology.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

/**
 * Created by lp700 on 24/2/2560.
 */

public class SortDialogFragment {

    private Context context;
    private CharSequence[] list = {
                    "อาการ : แย่ลง - ปกติ",
                    "อาการ : ปกติ - แย่ลง",
                    "วันที่เข้ารับการรักษา : ใหม่ - เก่า",
                    "วันที่เข้ารับการรักษา : เก่า - ใหม่",
                    "วันที่แก้ไข : ใหม่ - เก่า",
                    "วันที่แก้ไข : เก่า - ใหม่"
            };

    public SortDialogFragment(Context context) {
        this.context = context;
    }

    public AlertDialog getDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("เรียงลำดับ");
        builder.setSingleChoiceItems(list, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setNegativeButton("ยกเลิก", null);
        builder.create();

        return builder.show();
    }

}
