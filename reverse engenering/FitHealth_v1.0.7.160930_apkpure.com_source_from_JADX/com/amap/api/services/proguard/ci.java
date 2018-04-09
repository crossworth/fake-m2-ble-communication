package com.amap.api.services.proguard;

/* compiled from: Utils */
public class ci {
    public static int m1483a(String str, String str2) {
        int i = 0;
        try {
            String[] split = str.split("\\.");
            String[] split2 = str2.split("\\.");
            int min = Math.min(split.length, split2.length);
            for (int i2 = 0; i2 < min; i2++) {
                i = split[i2].length() - split2[i2].length();
                if (i != 0) {
                    break;
                }
                i = split[i2].compareTo(split2[i2]);
                if (i != 0) {
                    break;
                }
            }
            if (i != 0) {
                return i;
            }
            return split.length - split2.length;
        } catch (Throwable e) {
            be.m1340a(e, "Utils", "compareVersion");
            return -1;
        }
    }
}