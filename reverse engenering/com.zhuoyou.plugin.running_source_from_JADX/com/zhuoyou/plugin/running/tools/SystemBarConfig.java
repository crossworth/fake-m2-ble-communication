package com.zhuoyou.plugin.running.tools;

import android.annotation.TargetApi;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.ViewConfiguration;
import com.zhuoyou.plugin.running.app.TheApp;
import java.lang.reflect.Method;

public class SystemBarConfig {
    private static final String NAV_BAR_HEIGHT_LANDSCAPE_RES_NAME = "navigation_bar_height_landscape";
    private static final String NAV_BAR_HEIGHT_RES_NAME = "navigation_bar_height";
    private static final String NAV_BAR_WIDTH_RES_NAME = "navigation_bar_width";
    private static final String SHOW_NAV_BAR_RES_NAME = "config_showNavigationBar";
    private static final String STATUS_BAR_HEIGHT_RES_NAME = "status_bar_height";
    private static final Resources res = TheApp.getInstance().getResources();

    @TargetApi(19)
    private static String getNavBarOverride() {
        if (VERSION.SDK_INT < 19) {
            return null;
        }
        try {
            Method m = Class.forName("android.os.SystemProperties").getDeclaredMethod("get", new Class[]{String.class});
            m.setAccessible(true);
            return (String) m.invoke(null, new Object[]{"qemu.hw.mainkeys"});
        } catch (Throwable th) {
            return null;
        }
    }

    @TargetApi(14)
    public int getActionBarHeight() {
        if (VERSION.SDK_INT < 14) {
            return 0;
        }
        TypedValue tv = new TypedValue();
        TheApp.getInstance().getTheme().resolveAttribute(16843499, tv, true);
        return TypedValue.complexToDimensionPixelSize(tv.data, res.getDisplayMetrics());
    }

    @TargetApi(14)
    public static int getNavigationBarHeight() {
        if (VERSION.SDK_INT < 14 || !hasNavBar()) {
            return 0;
        }
        String key;
        if (res.getConfiguration().orientation == 1) {
            key = NAV_BAR_HEIGHT_RES_NAME;
        } else {
            key = NAV_BAR_HEIGHT_LANDSCAPE_RES_NAME;
        }
        return getInternalDimensionSize(res, key);
    }

    @TargetApi(14)
    public static int getNavigationBarWidth() {
        if (VERSION.SDK_INT < 14 || !hasNavBar()) {
            return 0;
        }
        return getInternalDimensionSize(res, NAV_BAR_WIDTH_RES_NAME);
    }

    @TargetApi(14)
    public static boolean hasNavBar() {
        int resourceId = res.getIdentifier(SHOW_NAV_BAR_RES_NAME, "bool", "android");
        if (resourceId != 0) {
            boolean hasNav = res.getBoolean(resourceId);
            if ("1".equals(getNavBarOverride())) {
                return false;
            }
            if ("0".equals(getNavBarOverride())) {
                return true;
            }
            return hasNav;
        }
        return !ViewConfiguration.get(TheApp.getInstance()).hasPermanentMenuKey();
    }

    private static int getInternalDimensionSize(Resources res, String key) {
        int resourceId = res.getIdentifier(key, "dimen", "android");
        if (resourceId > 0) {
            return res.getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    @TargetApi(17)
    private static float getSmallestWidthDp() {
        float widthDp;
        float heightDp;
        DisplayMetrics metrics = res.getDisplayMetrics();
        if (VERSION.SDK_INT >= 17) {
            widthDp = ((float) metrics.widthPixels) / metrics.density;
            heightDp = ((float) metrics.heightPixels) / metrics.density;
        } else {
            widthDp = ((float) metrics.widthPixels) / metrics.density;
            heightDp = ((float) (metrics.heightPixels + getNavigationBarWidth())) / metrics.density;
        }
        return Math.min(widthDp, heightDp);
    }

    public static int getContentHeight() {
        return res.getDisplayMetrics().heightPixels - getStatusBarHeight();
    }

    public static int getContentWidth() {
        return res.getDisplayMetrics().widthPixels;
    }

    public static boolean isNavigationAtBottom() {
        return getSmallestWidthDp() >= 600.0f || res.getConfiguration().orientation == 1;
    }

    public static int getStatusBarHeight() {
        return getInternalDimensionSize(res, STATUS_BAR_HEIGHT_RES_NAME);
    }
}
