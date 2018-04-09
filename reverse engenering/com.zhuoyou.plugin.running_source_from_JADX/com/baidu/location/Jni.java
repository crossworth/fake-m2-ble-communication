package com.baidu.location;

import android.util.Log;
import com.baidu.location.p006h.C0458a;
import com.baidu.platform.comapi.location.CoordinateType;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class Jni {
    private static int f98a = 0;
    private static int f99b = 1;
    private static int f100c = 2;
    private static int f101d = 11;
    private static int f102e = 12;
    private static int f103f = 13;
    private static int f104g = 14;
    private static int f105h = 15;
    private static int f106i = 1024;
    private static boolean f107j;

    static {
        f107j = false;
        try {
            System.loadLibrary("locSDK7");
        } catch (UnsatisfiedLinkError e) {
            e.printStackTrace();
            f107j = true;
        }
    }

    private static native String m163a(byte[] bArr, int i);

    private static native String m164b(double d, double d2, int i, int i2);

    private static native String m165c(byte[] bArr, int i);

    public static double[] coorEncrypt(double d, double d2, String str) {
        double[] dArr = new double[]{0.0d, 0.0d};
        if (f107j) {
            return dArr;
        }
        int i = -1;
        if (str.equals(BDLocation.BDLOCATION_GCJ02_TO_BD09)) {
            i = f98a;
        } else if (str.equals("bd09ll")) {
            i = f99b;
        } else if (str.equals(CoordinateType.GCJ02)) {
            i = f100c;
        } else if (str.equals(BDLocation.BDLOCATION_WGS84_TO_GCJ02)) {
            i = f101d;
        } else if (str.equals(BDLocation.BDLOCATION_BD09_TO_GCJ02)) {
            i = f102e;
        } else if (str.equals(BDLocation.BDLOCATION_BD09LL_TO_GCJ02)) {
            i = f103f;
        } else if (str.equals("wgs842mc")) {
            i = f105h;
        }
        try {
            String[] split = m164b(d, d2, str.equals("gcj2wgs") ? 16 : i, 132456).split(":");
            dArr[0] = Double.parseDouble(split[0]);
            dArr[1] = Double.parseDouble(split[1]);
        } catch (UnsatisfiedLinkError e) {
        }
        return dArr;
    }

    public static String decodeIBeacon(byte[] bArr, byte[] bArr2) {
        return f107j ? null : ib(bArr, bArr2);
    }

    private static native String ee(String str, int i);

    public static String en1(String str) {
        int i = 740;
        int i2 = 0;
        if (f107j) {
            return "err!";
        }
        if (str == null) {
            return "null";
        }
        byte[] bytes = str.getBytes();
        byte[] bArr = new byte[f106i];
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
        String str2 = "err!";
        try {
            return m163a(bArr, 132456);
        } catch (UnsatisfiedLinkError e) {
            e.printStackTrace();
            return "err!";
        }
    }

    public static String encode(String str) {
        return f107j ? "err!" : en1(str) + "|tp=3";
    }

    public static String encode2(String str) {
        if (f107j) {
            return "err!";
        }
        if (str == null) {
            return "null";
        }
        String str2 = "err!";
        try {
            return m165c(str.getBytes(), 132456);
        } catch (UnsatisfiedLinkError e) {
            e.printStackTrace();
            return "err!";
        }
    }

    public static Long encode3(String str) {
        Long l = null;
        if (!f107j) {
            String str2;
            String str3 = "";
            try {
                str2 = new String(str.getBytes(), "UTF-8");
            } catch (Exception e) {
                str2 = str3;
            }
            try {
                l = Long.valueOf(murmur(str2));
            } catch (UnsatisfiedLinkError e2) {
                e2.printStackTrace();
            }
        }
        return l;
    }

    private static native String encodeNotLimit(String str, int i);

    public static String encodeOfflineLocationUpdateRequest(String str) {
        if (f107j) {
            return "err!";
        }
        String str2;
        String str3 = "";
        try {
            str2 = new String(str.getBytes(), "UTF-8");
        } catch (Exception e) {
            str2 = str3;
        }
        str3 = "err!";
        try {
            str2 = encodeNotLimit(str2, 132456);
        } catch (UnsatisfiedLinkError e2) {
            e2.printStackTrace();
            str2 = "err!";
        }
        return str2 + "|tp=3";
    }

    public static String encodeTp4(String str) {
        if (f107j) {
            return "err!";
        }
        String str2;
        String str3 = "";
        try {
            str2 = new String(str.getBytes(), "UTF-8");
        } catch (Exception e) {
            str2 = str3;
        }
        str3 = "err!";
        try {
            str2 = ee(str2, 132456);
        } catch (UnsatisfiedLinkError e2) {
            e2.printStackTrace();
            str2 = "err!";
        }
        return str2 + "|tp=4";
    }

    private static native void m166f(byte[] bArr, byte[] bArr2);

    private static native String m167g(byte[] bArr);

    public static double getGpsSwiftRadius(float f, double d, double d2) {
        double d3 = 0.0d;
        if (!f107j) {
            try {
                d3 = gsr(f, d, d2);
            } catch (UnsatisfiedLinkError e) {
            }
        }
        return d3;
    }

    public static String getSkyKey() {
        if (f107j) {
            return "err!";
        }
        String str = "err!";
        try {
            return sky();
        } catch (UnsatisfiedLinkError e) {
            e.printStackTrace();
            return "err!";
        }
    }

    private static native double gsr(float f, double d, double d2);

    public static String gtr2(String str) {
        if (f107j) {
            return null;
        }
        try {
            String g = m167g(str.getBytes());
            return (g == null || g.length() < 2 || "no".equals(g)) ? null : g;
        } catch (UnsatisfiedLinkError e) {
            return null;
        }
    }

    private static native String ib(byte[] bArr, byte[] bArr2);

    private static native long murmur(String str);

    public static void removeSoList(int i, String str) {
        File file = new File("/proc/" + i + "/maps");
        if (file.exists()) {
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                while (true) {
                    String readLine = bufferedReader.readLine();
                    if (readLine != null) {
                        readLine = readLine.trim();
                        if (readLine.contains("/data/data") && !readLine.contains("/data/data/" + str)) {
                            readLine.substring(readLine.indexOf("/data/data"));
                            Log.w(C0458a.f826a, str + " uninstall tempString = " + readLine);
                            try {
                                uninstall(readLine);
                            } catch (Exception e) {
                                e.printStackTrace();
                            } catch (Error e2) {
                                e2.printStackTrace();
                            }
                        }
                    } else {
                        bufferedReader.close();
                        return;
                    }
                }
            } catch (FileNotFoundException e3) {
                e3.printStackTrace();
            } catch (IOException e4) {
                e4.printStackTrace();
            }
        }
    }

    private static native String sky();

    public static void tr2(String str, String str2) {
        if (!f107j) {
            try {
                m166f(str.getBytes(), str2.getBytes());
            } catch (UnsatisfiedLinkError e) {
                e.printStackTrace();
            }
        }
    }

    public static native void uninstall(String str);
}
