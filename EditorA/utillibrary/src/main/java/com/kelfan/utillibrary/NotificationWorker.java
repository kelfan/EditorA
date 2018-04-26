package com.kelfan.utillibrary;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 1/01/2018.
 */

public class NotificationWorker {
    public static void notifyResult(Context context, String action,int result) {
        if (result == 1) {
            Toast.makeText(context, action + " success", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, action + " fail", Toast.LENGTH_SHORT).show();
        }
    }
}
