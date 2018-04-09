package com.droi.sdk.core;

import android.content.Context;

public class SelfUpdateCoreHelper {
    public static String getAppId() {
        return Core.getDroiAppId();
    }

    public static String getDeviceId(Context context) {
        Core.initialize(context);
        String droiDeviceId = Core.getDroiDeviceId();
        if (droiDeviceId == null) {
            return "";
        }
        return droiDeviceId;
    }
}
