package com.droi.sdk.utility.priv;

public class Version {
    public static final int COMPARE_EQUAL = 1;
    public static final int COMPARE_FAIL = -1;
    public static final int COMPARE_GREATER = 2;
    public static final int COMPARE_LOWER = 0;
    public static final String OPEN_PLATFORM_VERSION = "0.5.268";

    public static int compare(String ver1, String ver2) {
        try {
            String[] verArr1 = ver1.split("\\.");
            String[] verArr2 = ver2.split("\\.");
            int count = Math.min(verArr1.length, verArr2.length);
            int i = 0;
            while (i < count) {
                int v1 = Integer.parseInt(verArr1[i]);
                int v2 = Integer.parseInt(verArr2[i]);
                if (v1 != v2) {
                    return v1 > v2 ? 2 : 0;
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
