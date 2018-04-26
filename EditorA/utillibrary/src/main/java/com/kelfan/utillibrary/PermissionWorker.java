package com.kelfan.utillibrary;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by Administrator on 13/03/2018.
 */

public class PermissionWorker {

    public static final int READ_EXTERNAL_PERMISSIONS_REQUEST_CODE = 0;

    public static Boolean checkReadExternalPermissions(Activity activity) {
        String permission = Manifest.permission.READ_EXTERNAL_STORAGE;

        if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
            return false;
        } else {
            return true;
        }
    }
}
