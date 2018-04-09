package com.baidu.location;

class Jni implements an, C1619j {
    private static int em = 14;
    private static int en = 13;
    private static int eo = 1024;
    private static int ep = 11;
    private static int eq = 12;
    private static boolean er;
    private static int es = 1;
    private static int et = 2;
    private static int eu = 0;

    static {
        er = false;
        try {
            System.loadLibrary("locSDK4");
        } catch (UnsatisfiedLinkError e) {
            e.printStackTrace();
            er = true;
        }
    }

    Jni() {
    }

    private static native String m5807a(byte[] bArr, int i);

    private static native String m5808b(double d, double d2, int i, int i2);

    private static native String m5809c(byte[] bArr, int i);

    public static String m5810e(String str) {
        return er ? "err!" : m5809c(str.getBytes(), 132456);
    }

    public static String m5811f(String str) {
        int i = 740;
        int i2 = 0;
        if (er) {
            return "err!";
        }
        byte[] bytes = str.getBytes();
        byte[] bArr = new byte[eo];
        int length = bytes.length;
        if (length <= 740) {
            i = length;
        }
        length = 0;
        while (i2 < i) {
            if (bytes[i2] != (byte) 0) {
                bArr[length] = bytes[i2];
                length++;
            }
            i2++;
        }
        return m5807a(bArr, 132456) + "|tp=3";
    }

    public static double[] m5812if(double d, double d2, String str) {
        double[] dArr = new double[]{0.0d, 0.0d};
        if (er) {
            return dArr;
        }
        int i = -1;
        if (str.equals(BDGeofence.COORD_TYPE_BD09)) {
            i = eu;
        } else if (str.equals(BDGeofence.COORD_TYPE_BD09LL)) {
            i = es;
        } else if (str.equals(BDGeofence.COORD_TYPE_GCJ)) {
            i = et;
        } else if (str.equals("gps2gcj")) {
            i = ep;
        } else if (str.equals("bd092gcj")) {
            i = eq;
        } else if (str.equals("bd09ll2gcj")) {
            i = en;
        }
        try {
            String[] split = m5808b(d, d2, i, 132456).split(":");
            dArr[0] = Double.parseDouble(split[0]);
            dArr[1] = Double.parseDouble(split[1]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dArr;
    }
}
