package com.sdl.sdlarchivesmanager.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

/**
 * Created by majingyuan on 15/12/5.
 * 图片选择提示
 */
public class PhotoDialog extends AlertDialog {

    private CharSequence[] items;
    public PhotoDialog(Context context,final Activity activity) {
        super(context);
        // TODO Auto-generated constructor stub

        AlertDialog.Builder builder = new Builder(context);
        items = new CharSequence[]{"拍照","从相册选择"};

        builder.setTitle("请选择图片来源");
        builder.setCancelable(true);
        builder.setItems(items, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(activity, items[which], Toast.LENGTH_SHORT).show();
//                if (which == 0) {
//
//                }
            }
        });


        builder.create().show();
    }
}
