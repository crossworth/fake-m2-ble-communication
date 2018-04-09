package com.droi.sdk.core;

public class FeedbackCoreHelper {
    public static String getAppId() {
        return Core.getDroiAppId();
    }

    public static String getDeviceId() {
        String droiDeviceId = Core.getDroiDeviceId();
        if (droiDeviceId == null) {
            return "";
        }
        return droiDeviceId;
    }
}
