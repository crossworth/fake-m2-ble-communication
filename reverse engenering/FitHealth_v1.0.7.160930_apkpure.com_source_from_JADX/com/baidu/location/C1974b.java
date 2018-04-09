package com.baidu.location;

import android.location.Location;
import android.util.Log;
import com.baidu.location.C1981n.C0529a;
import com.baidu.location.ai.C0503b;
import com.tencent.open.yyb.TitleBar;
import java.util.Calendar;
import java.util.Locale;

class C1974b implements an, C1619j {
    public static int f5424U = 10;
    public static int f5425V = 7;
    public static String f5426W = "http://loc.map.baidu.com/sdk_ep.php";
    public static float f5427X = TitleBar.SHAREBTN_RIGHT_MARGIN;
    public static int f5428Y = 20;
    public static float f5429Z = 0.9f;
    public static double a0 = 0.0d;
    public static float a1 = 0.1f;
    public static float a2 = TitleBar.SHAREBTN_RIGHT_MARGIN;
    public static String a3 = BDGeofence.COORD_TYPE_GCJ;
    public static float a4 = 0.0f;
    public static float a5 = 2.0f;
    public static final boolean aA = true;
    public static double aB = 0.0d;
    public static int aC = 3;
    public static int aD = 2;
    private static String aE = "[baidu_location_service]";
    public static int aF = 3;
    private static String aG = "http://loc.map.baidu.com/sdk.php";
    public static int aH = 30000;
    private static Process aI = null;
    public static long aJ = 1200000;
    public static float aK = 2.2f;
    public static long aL = 20;
    public static float aM = 3.8f;
    public static float aN = 0.5f;
    public static int aO = 0;
    public static int aP = 30000;
    public static int aQ = 16;
    public static boolean aR = true;
    public static float aS = 200.0f;
    private static String aT = "http://loc.map.baidu.com/user_err.php";
    public static float aU = TitleBar.SHAREBTN_RIGHT_MARGIN;
    public static boolean aV = false;
    public static int aW = 120;
    public static int aX = 100;
    public static boolean aY = true;
    public static int aZ = 30;
    public static double aa = 0.0d;
    public static float ab = 1.1f;
    public static float ac = 6.0f;
    public static float ad = 2.3f;
    public static boolean ae = false;
    public static int af = 0;
    public static int ag = 0;
    public static int ah = 0;
    public static boolean ai = false;
    public static double aj = 0.0d;
    public static float ak = 50.0f;
    public static int al = 60;
    public static boolean am = false;
    public static int an = 300;
    public static int ao = 5000;
    public static int ap = 6;
    public static int aq = 20;
    public static String ar = "no";
    public static int as = 70;
    public static int at = 1000;
    public static boolean au = true;
    private static boolean av = false;
    public static byte[] aw = null;
    private static boolean ax = false;
    public static long ay = 300000;
    public static int az = 70;

    C1974b() {
    }

    static int m5911do(String str, String str2, String str3) {
        int i = Integer.MIN_VALUE;
        if (!(str == null || str.equals(""))) {
            int indexOf = str.indexOf(str2);
            if (indexOf != -1) {
                indexOf += str2.length();
                int indexOf2 = str.indexOf(str3, indexOf);
                if (indexOf2 != -1) {
                    String substring = str.substring(indexOf, indexOf2);
                    if (!(substring == null || substring.equals(""))) {
                        try {
                            i = Integer.parseInt(substring);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return i;
    }

    public static String m5912do() {
        return aT;
    }

    public static void m5913do(String str) {
        if (str != null) {
            aG = str;
        }
    }

    public static void m5914do(String str, String str2) {
        if (av) {
            Log.d(str, str2);
        }
    }

    static double m5915for(String str, String str2, String str3) {
        double d = Double.MIN_VALUE;
        if (!(str == null || str.equals(""))) {
            int indexOf = str.indexOf(str2);
            if (indexOf != -1) {
                indexOf += str2.length();
                int indexOf2 = str.indexOf(str3, indexOf);
                if (indexOf2 != -1) {
                    String substring = str.substring(indexOf, indexOf2);
                    if (!(substring == null || substring.equals(""))) {
                        try {
                            d = Double.parseDouble(substring);
                        } catch (NumberFormatException e) {
                        }
                    }
                }
            }
        }
        return d;
    }

    static String m5916for() {
        Calendar instance = Calendar.getInstance();
        int i = instance.get(5);
        int i2 = instance.get(1);
        int i3 = instance.get(2) + 1;
        int i4 = instance.get(11);
        int i5 = instance.get(12);
        int i6 = instance.get(13);
        return String.format(Locale.CHINA, "%d_%d_%d_%d_%d_%d", new Object[]{Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i), Integer.valueOf(i4), Integer.valueOf(i5), Integer.valueOf(i6)});
    }

    static float m5917if(String str, String str2, String str3) {
        float f = Float.MIN_VALUE;
        if (!(str == null || str.equals(""))) {
            int indexOf = str.indexOf(str2);
            if (indexOf != -1) {
                indexOf += str2.length();
                int indexOf2 = str.indexOf(str3, indexOf);
                if (indexOf2 != -1) {
                    String substring = str.substring(indexOf, indexOf2);
                    if (!(substring == null || substring.equals(""))) {
                        try {
                            f = Float.parseFloat(substring);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return f;
    }

    static String m5918if() {
        Calendar instance = Calendar.getInstance();
        int i = instance.get(5);
        int i2 = instance.get(1);
        int i3 = instance.get(2) + 1;
        int i4 = instance.get(11);
        int i5 = instance.get(12);
        int i6 = instance.get(13);
        return String.format(Locale.CHINA, "%d-%d-%d %d:%d:%d", new Object[]{Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i), Integer.valueOf(i4), Integer.valueOf(i5), Integer.valueOf(i6)});
    }

    public static String m5919if(C0529a c0529a, C0503b c0503b, Location location, String str, int i) {
        String c0529a2;
        StringBuffer stringBuffer = new StringBuffer();
        if (c0529a != null) {
            c0529a2 = c0529a.toString();
            if (c0529a2 != null) {
                stringBuffer.append(c0529a2);
            }
        }
        if (c0503b != null) {
            c0529a2 = i == 0 ? c0503b.m2152char() : c0503b.m2150byte();
            if (c0529a2 != null) {
                stringBuffer.append(c0529a2);
            }
        }
        if (location != null) {
            c0529a2 = (ah == 0 || i == 0) ? C1984s.m6065try(location) : C1984s.m6062new(location);
            if (c0529a2 != null) {
                stringBuffer.append(c0529a2);
            }
        }
        boolean z = false;
        if (i == 0) {
            z = true;
        }
        c0529a2 = ap.bD().m5891try(z);
        if (c0529a2 != null) {
            stringBuffer.append(c0529a2);
        }
        if (str != null) {
            stringBuffer.append(str);
        }
        if (c0529a != null) {
            c0529a2 = c0529a.m2204int();
            if (c0529a2 != null && c0529a2.length() + stringBuffer.length() < 750) {
                stringBuffer.append(c0529a2);
            }
        }
        c0529a2 = stringBuffer.toString();
        if (location == null || c0503b == null) {
            aF = 3;
        } else {
            try {
                float speed = location.getSpeed();
                int i2 = ah;
                int i3 = c0503b.m2153do();
                int i4 = c0503b.m2162try();
                boolean z2 = c0503b.m2151case();
                if (speed < ac && ((i2 == 1 || i2 == 0) && (i3 < al || z2))) {
                    aF = 1;
                } else if (speed >= f5427X || (!(i2 == 1 || i2 == 0 || i2 == 3) || (i3 >= az && i4 <= ap))) {
                    aF = 3;
                } else {
                    aF = 2;
                }
            } catch (Exception e) {
                aF = 3;
            }
        }
        return c0529a2;
    }

    static String m5920if(String str, String str2, String str3, double d) {
        if (str == null || str.equals("")) {
            return null;
        }
        int indexOf = str.indexOf(str2);
        if (indexOf == -1) {
            return null;
        }
        indexOf += str2.length();
        int indexOf2 = str.indexOf(str3, indexOf);
        if (indexOf2 == -1) {
            return null;
        }
        String substring = str.substring(0, indexOf);
        return substring + String.format(Locale.CHINA, "%.7f", new Object[]{Double.valueOf(d)}) + str.substring(indexOf2);
    }

    public static void m5921if(String str) {
        if (ax) {
            Log.d(aE, str);
        }
    }

    public static void m5922if(String str, String str2) {
        Log.d(str, str2);
    }

    public static boolean m5923if(BDLocation bDLocation) {
        int locType = bDLocation.getLocType();
        return locType > 100 && locType < 200;
    }

    public static String m5924int() {
        return aG;
    }

    public static void m5925new() {
        try {
            if (aI != null) {
                aI.destroy();
                aI = null;
            }
        } catch (Exception e) {
        }
    }

    public static void m5926try() {
    }
}
