package com.tencent.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build.VERSION;
import android.util.Log;
import android.view.MotionEvent;
import com.tencent.connect.common.Constants;
import com.tencent.p004a.p005a.C1711d;
import java.lang.reflect.Method;
import java.security.MessageDigest;

/* compiled from: ProGuard */
public class SystemUtils {
    public static final String QQ_SHARE_CALLBACK_ACTION = "shareToQQ";
    public static final String QQ_VERSION_NAME_4_1_0 = "4.1.0";
    public static final String QQ_VERSION_NAME_4_2_0 = "4.2.0";
    public static final String QQ_VERSION_NAME_4_3_0 = "4.3.0";
    public static final String QQ_VERSION_NAME_4_5_0 = "4.5.0";
    public static final String QQ_VERSION_NAME_4_6_0 = "4.6.0";
    public static final String QZONE_SHARE_CALLBACK_ACTION = "shareToQzone";

    public static String getAppVersionName(Context context, String str) {
        try {
            return context.getPackageManager().getPackageInfo(str, 0).versionName;
        } catch (NameNotFoundException e) {
            return null;
        }
    }

    public static int compareVersion(String str, String str2) {
        if (str == null && str2 == null) {
            return 0;
        }
        if (str != null && str2 == null) {
            return 1;
        }
        if (str == null && str2 != null) {
            return -1;
        }
        String[] split = str.split("\\.");
        String[] split2 = str2.split("\\.");
        int i = 0;
        while (i < split.length && i < split2.length) {
            try {
                int parseInt = Integer.parseInt(split[i]);
                int parseInt2 = Integer.parseInt(split2[i]);
                if (parseInt < parseInt2) {
                    return -1;
                }
                if (parseInt > parseInt2) {
                    return 1;
                }
                i++;
            } catch (NumberFormatException e) {
                return str.compareTo(str2);
            }
        }
        if (split.length > i) {
            return 1;
        }
        if (split2.length > i) {
            return -1;
        }
        return 0;
    }

    public static boolean isAppSignatureValid(Context context, String str, String str2) {
        C1711d.m4636a("openSDK_LOG", "OpenUi, validateAppSignatureForPackage");
        try {
            for (Signature toCharsString : context.getPackageManager().getPackageInfo(str, 64).signatures) {
                if (Util.encrypt(toCharsString.toCharsString()).equals(str2)) {
                    return true;
                }
            }
            return false;
        } catch (NameNotFoundException e) {
            return false;
        }
    }

    public static String getAppSignatureMD5(Context context, String str) {
        String packageName;
        Throwable e;
        C1711d.m4636a("openSDK_LOG", "OpenUi, getSignValidString");
        String str2 = "";
        try {
            packageName = context.getPackageName();
            Signature[] signatureArr = context.getPackageManager().getPackageInfo(packageName, 64).signatures;
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(signatureArr[0].toByteArray());
            String toHexString = Util.toHexString(instance.digest());
            instance.reset();
            instance.update((packageName + "_" + toHexString + "_" + str + "").getBytes());
            packageName = Util.toHexString(instance.digest());
            try {
                instance.reset();
            } catch (Exception e2) {
                e = e2;
                e.printStackTrace();
                C1711d.m4637a("openSDK_LOG", "OpenUi, getSignValidString error", e);
                return packageName;
            }
        } catch (Throwable e3) {
            Throwable th = e3;
            packageName = str2;
            e = th;
            e.printStackTrace();
            C1711d.m4637a("openSDK_LOG", "OpenUi, getSignValidString error", e);
            return packageName;
        }
        return packageName;
    }

    public static boolean isActivityExist(Context context, Intent intent) {
        if (context == null || intent == null || context.getPackageManager().queryIntentActivities(intent, 0).size() == 0) {
            return false;
        }
        return true;
    }

    public static String getRealPathFromUri(Activity activity, Uri uri) {
        Cursor managedQuery = activity.managedQuery(uri, new String[]{"_data"}, null, null, null);
        if (managedQuery == null) {
            return null;
        }
        int columnIndexOrThrow = managedQuery.getColumnIndexOrThrow("_data");
        managedQuery.moveToFirst();
        return managedQuery.getString(columnIndexOrThrow);
    }

    public static String getAppName(Context context) {
        return context.getApplicationInfo().loadLabel(context.getPackageManager()).toString();
    }

    public static int compareQQVersion(Context context, String str) {
        return compareVersion(getAppVersionName(context, Constants.MOBILEQQ_PACKAGE_NAME), str);
    }

    public static boolean checkMobileQQ(Context context) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(Constants.MOBILEQQ_PACKAGE_NAME, 0);
        } catch (NameNotFoundException e) {
            Log.d("checkMobileQQ", "error");
            e.printStackTrace();
            packageInfo = null;
        }
        if (packageInfo == null) {
            return false;
        }
        String str = packageInfo.versionName;
        try {
            Log.d("MobileQQ verson", str);
            String[] split = str.split("\\.");
            int parseInt = Integer.parseInt(split[0]);
            int parseInt2 = Integer.parseInt(split[1]);
            if (parseInt > 4 || (parseInt == 4 && parseInt2 >= 1)) {
                return true;
            }
            return false;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public static int getAndroidSDKVersion() {
        int i = 0;
        try {
            i = Integer.valueOf(VERSION.SDK).intValue();
        } catch (NumberFormatException e) {
        }
        return i;
    }

    public static boolean isSupportMultiTouch() {
        boolean z = false;
        boolean z2 = false;
        for (Method method : MotionEvent.class.getDeclaredMethods()) {
            if (method.getName().equals("getPointerCount")) {
                z2 = true;
            }
            if (method.getName().equals("getPointerId")) {
                z = true;
            }
        }
        if (getAndroidSDKVersion() >= 7) {
            return true;
        }
        if (z2 && r2) {
            return true;
        }
        return false;
    }
}
