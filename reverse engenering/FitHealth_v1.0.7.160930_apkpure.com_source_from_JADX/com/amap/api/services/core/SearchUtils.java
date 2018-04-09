package com.amap.api.services.core;

import android.content.Context;
import com.amap.api.services.proguard.C0390i;
import java.security.MessageDigest;
import java.util.Locale;

public class SearchUtils {
    public static String getSHA1(Context context) {
        String str = null;
        try {
            byte[] digest = MessageDigest.getInstance("SHA1").digest(context.getApplicationContext().getPackageManager().getPackageInfo(context.getPackageName(), 64).signatures[0].toByteArray());
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b : digest) {
                String toUpperCase = Integer.toHexString(b & 255).toUpperCase(Locale.US);
                if (toUpperCase.length() == 1) {
                    stringBuffer.append("0");
                }
                stringBuffer.append(toUpperCase);
                stringBuffer.append(":");
            }
            str = stringBuffer.toString();
        } catch (Throwable th) {
            C0390i.m1594a(th, "SearchUtils", "getSHA1");
        }
        return str;
    }

    public static String getPkgName(Context context) {
        String str = null;
        try {
            str = context.getApplicationContext().getPackageName();
        } catch (Throwable th) {
            C0390i.m1594a(th, "SearchUtils", "getPkgName");
        }
        return str;
    }
}
