package org.andengine.input.touch.controller;

import android.content.Context;
import org.andengine.util.system.SystemUtils;

public class MultiTouch {
    private static Boolean SUPPORTED = null;
    private static Boolean SUPPORTED_DISTINCT = null;

    public static boolean isSupported(Context pContext) {
        if (SUPPORTED == null) {
            SUPPORTED = Boolean.valueOf(SystemUtils.hasSystemFeature(pContext, "android.hardware.touchscreen.multitouch"));
        }
        return SUPPORTED.booleanValue();
    }

    public static boolean isSupportedDistinct(Context pContext) {
        if (SUPPORTED_DISTINCT == null) {
            SUPPORTED_DISTINCT = Boolean.valueOf(SystemUtils.hasSystemFeature(pContext, "android.hardware.touchscreen.multitouch.distinct"));
        }
        return SUPPORTED_DISTINCT.booleanValue();
    }
}
