package jirayu.pond.footreflexology.util;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import jirayu.pond.footreflexology.R;

/**
 * Created by lp700 on 12/3/2560.
 */

public class InfoDialogUtils {

    private AlertDialog.Builder builder;

    public InfoDialogUtils(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_info_main_page, null);
        builder = new AlertDialog.Builder(context);
        builder.setView(view);
        builder.setNegativeButton("เข้าใจแล้ว", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
    }

    public void showDialog() {
        builder.show();
    }

}
