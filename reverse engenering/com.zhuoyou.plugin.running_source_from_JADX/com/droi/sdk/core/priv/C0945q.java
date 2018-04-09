package com.droi.sdk.core.priv;

public class C0945q {
    public static final String f3082a = "1.0.3512";
    public static final int f3083b = 2;
    public static final int f3084c = 1;
    public static final int f3085d = 0;
    public static final int f3086e = -1;

    public static int m2805a(String str, String str2) {
        try {
            String[] split = str.split("\\.");
            String[] split2 = str2.split("\\.");
            int min = Math.min(split.length, split2.length);
            int i = 0;
            while (i < min) {
                int parseInt = Integer.parseInt(split[i]);
                int parseInt2 = Integer.parseInt(split2[i]);
                if (parseInt != parseInt2) {
                    return parseInt > parseInt2 ? 2 : 0;
                } else {
                    i++;
                }
            }
            return 1;
        } catch (Exception e) {
            return -1;
        }
    }
}
