package com.zhuoyou.plugin.running.tools;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build.VERSION;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class StatusBarUtils {
    @TargetApi(19)
    public static void transparencyBar(Activity activity) {
        if (VERSION.SDK_INT >= 19) {
            Window window = activity.getWindow();
            window.addFlags(67108864);
            window.addFlags(134217728);
        }
    }

    @TargetApi(19)
    public static void transparencyBar(Activity activity, boolean includeNB) {
        if (VERSION.SDK_INT >= 19) {
            Window window = activity.getWindow();
            window.addFlags(67108864);
            if (includeNB) {
                window.addFlags(134217728);
            }
        }
    }

    public static void setStatusBarColor(Activity activity, int colorId) {
        if (VERSION.SDK_INT >= 21) {
            activity.getWindow().setStatusBarColor(activity.getResources().getColor(colorId));
        } else if (VERSION.SDK_INT >= 19) {
            transparencyBar(activity);
            SystemBarManager tintManager = new SystemBarManager(activity);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(colorId);
        }
    }

    public static int StatusBarLightMode(Activity activity) {
        if (VERSION.SDK_INT < 19) {
            return 0;
        }
        if (MIUISetStatusBarLightMode(activity.getWindow(), true)) {
            return 1;
        }
        if (FlymeSetStatusBarLightMode(activity.getWindow(), true)) {
            return 2;
        }
        if (VERSION.SDK_INT < 23) {
            return 0;
        }
        activity.getWindow().getDecorView().setSystemUiVisibility(9216);
        return 3;
    }

    public static void StatusBarLightMode(Activity activity, int type) {
        if (type == 1) {
            MIUISetStatusBarLightMode(activity.getWindow(), true);
        } else if (type == 2) {
            FlymeSetStatusBarLightMode(activity.getWindow(), true);
        } else if (type == 3) {
            activity.getWindow().getDecorView().setSystemUiVisibility(9216);
        }
    }

    public static void StatusBarDarkMode(Activity activity, int type) {
        if (type == 1) {
            MIUISetStatusBarLightMode(activity.getWindow(), false);
        } else if (type == 2) {
            FlymeSetStatusBarLightMode(activity.getWindow(), false);
        } else if (type == 3) {
            activity.getWindow().getDecorView().setSystemUiVisibility(0);
        }
    }

    public static int StatusBarDarkMode(Activity activity) {
        if (VERSION.SDK_INT < 19) {
            return 0;
        }
        if (MIUISetStatusBarLightMode(activity.getWindow(), true)) {
            return 1;
        }
        if (FlymeSetStatusBarLightMode(activity.getWindow(), true)) {
            return 2;
        }
        if (VERSION.SDK_INT < 23) {
            return 0;
        }
        activity.getWindow().getDecorView().setSystemUiVisibility(0);
        return 3;
    }

    public static boolean FlymeSetStatusBarLightMode(Window window, boolean dark) {
        if (window == null) {
            return false;
        }
        try {
            LayoutParams lp = window.getAttributes();
            Field darkFlag = LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
            Field meizuFlags = LayoutParams.class.getDeclaredField("meizuFlags");
            darkFlag.setAccessible(true);
            meizuFlags.setAccessible(true);
            int bit = darkFlag.getInt(null);
            int value = meizuFlags.getInt(lp);
            if (dark) {
                value |= bit;
            } else {
                value &= bit ^ -1;
            }
            meizuFlags.setInt(lp, value);
            window.setAttributes(lp);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean MIUISetStatusBarLightMode(Window window, boolean dark) {
        if (window == null) {
            return false;
        }
        Class clazz = window.getClass();
        try {
            Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            int darkModeFlag = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE").getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", new Class[]{Integer.TYPE, Integer.TYPE});
            if (dark) {
                extraFlagField.invoke(window, new Object[]{Integer.valueOf(darkModeFlag), Integer.valueOf(darkModeFlag)});
            } else {
                extraFlagField.invoke(window, new Object[]{Integer.valueOf(0), Integer.valueOf(darkModeFlag)});
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
