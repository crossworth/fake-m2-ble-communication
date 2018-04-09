package com.droi.sdk.core.priv;

import com.droi.sdk.internal.DroiLog;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class C0895b {
    private static final String f2862a = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    private static final SimpleDateFormat f2863b = new SimpleDateFormat(f2862a);
    private static final TimeZone f2864c = TimeZone.getTimeZone("UTC");
    private static final String f2865d = "DroiDateUtils";

    public static String m2661a(Date date) {
        f2863b.setTimeZone(f2864c);
        return f2863b.format(date);
    }

    public static Date m2662a(String str) {
        f2863b.setTimeZone(f2864c);
        try {
            return f2863b.parse(str);
        } catch (Exception e) {
            DroiLog.m2873w(f2865d, e);
            return null;
        }
    }
}
