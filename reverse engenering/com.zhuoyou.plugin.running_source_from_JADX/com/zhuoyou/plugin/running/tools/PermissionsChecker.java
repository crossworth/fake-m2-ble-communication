package com.zhuoyou.plugin.running.tools;

import android.content.Context;
import android.support.v4.content.ContextCompat;

public class PermissionsChecker {
    private final Context mContext;

    public PermissionsChecker(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public boolean lacksPermissions(String... permissions) {
        for (String permission : permissions) {
            if (lacksPermission(permission)) {
                return true;
            }
        }
        return false;
    }

    private boolean lacksPermission(String permission) {
        return ContextCompat.checkSelfPermission(this.mContext, permission) == -1;
    }
}
