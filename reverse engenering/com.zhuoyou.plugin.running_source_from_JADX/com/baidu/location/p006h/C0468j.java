package com.baidu.location.p006h;

import android.content.Context;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.Process;
import android.provider.Settings.Secure;
import android.provider.Settings.System;
import android.text.TextUtils;
import com.baidu.android.bbalbs.common.p004a.C0303b;
import com.baidu.location.BDLocation;
import com.baidu.location.C0455f;
import com.baidu.location.p007b.C0370d;
import com.baidu.location.p012f.C0441a;
import com.baidu.location.p012f.C0443b;
import com.baidu.location.p012f.C0448d;
import com.baidu.location.p012f.C0451g;
import com.baidu.mapapi.UIMsg.m_AppUI;
import com.baidu.platform.comapi.location.CoordinateType;
import com.tencent.open.yyb.TitleBar;
import com.zhuoyou.plugin.running.app.Permissions;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Locale;

public class C0468j {
    public static int f873A = 3;
    public static int f874B = 10;
    public static int f875C = 2;
    public static int f876D = 7;
    public static int f877E = 20;
    public static int f878F = 70;
    public static int f879G = 120;
    public static float f880H = 2.0f;
    public static float f881I = TitleBar.SHAREBTN_RIGHT_MARGIN;
    public static float f882J = 50.0f;
    public static float f883K = 200.0f;
    public static int f884L = 16;
    public static float f885M = 0.9f;
    public static int f886N = 10000;
    public static float f887O = 0.5f;
    public static float f888P = 0.0f;
    public static float f889Q = 0.1f;
    public static int f890R = 30;
    public static int f891S = 100;
    public static int f892T = 0;
    public static int f893U = 0;
    public static int f894V = 0;
    public static int f895W = 420000;
    public static boolean f896X = true;
    public static boolean f897Y = true;
    public static int f898Z = 20;
    public static boolean f899a = false;
    public static int aa = 300;
    public static int ab = 1000;
    public static long ac = 900000;
    public static long ad = 420000;
    public static long ae = 180000;
    public static long af = 0;
    public static long ag = 15;
    public static long ah = 300000;
    public static int ai = 1000;
    public static int aj = 0;
    public static int ak = m_AppUI.MSG_RADAR_SEARCH_RETURN_RESULT;
    public static int al = m_AppUI.MSG_RADAR_SEARCH_RETURN_RESULT;
    public static float am = TitleBar.SHAREBTN_RIGHT_MARGIN;
    public static float an = 6.0f;
    public static float ao = TitleBar.SHAREBTN_RIGHT_MARGIN;
    public static int ap = 60;
    public static int aq = 70;
    public static int ar = 6;
    private static String as = "http://loc.map.baidu.com/sdk.php";
    private static String at = "http://loc.map.baidu.com/user_err.php";
    private static String au = "http://loc.map.baidu.com/oqur.php";
    private static String av = "http://loc.map.baidu.com/tcu.php";
    private static String aw = "http://loc.map.baidu.com/rtbu.php";
    private static String ax = "http://loc.map.baidu.com/iofd.php";
    private static String ay = "http://loc.map.baidu.com/wloc";
    public static boolean f900b = false;
    public static boolean f901c = false;
    public static int f902d = 0;
    public static String f903e = "http://loc.map.baidu.com/sdk_ep.php";
    public static String f904f = "no";
    public static boolean f905g = false;
    public static boolean f906h = false;
    public static boolean f907i = false;
    public static boolean f908j = false;
    public static boolean f909k = false;
    public static String f910l = CoordinateType.GCJ02;
    public static boolean f911m = true;
    public static int f912n = 3;
    public static double f913o = 0.0d;
    public static double f914p = 0.0d;
    public static double f915q = 0.0d;
    public static double f916r = 0.0d;
    public static int f917s = 0;
    public static byte[] f918t = null;
    public static boolean f919u = false;
    public static int f920v = 0;
    public static float f921w = 1.1f;
    public static float f922x = 2.2f;
    public static float f923y = 2.3f;
    public static float f924z = 3.8f;

    public static int m1009a(String str, String str2, String str3) {
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
                        }
                    }
                }
            }
        }
        return i;
    }

    public static Object m1010a(Context context, String str) {
        Object obj = null;
        if (context != null) {
            try {
                obj = context.getApplicationContext().getSystemService(str);
            } catch (Throwable th) {
            }
        }
        return obj;
    }

    public static Object m1011a(Object obj, String str, Object... objArr) throws Exception {
        Class cls = obj.getClass();
        Class[] clsArr = new Class[objArr.length];
        int length = objArr.length;
        for (int i = 0; i < length; i++) {
            clsArr[i] = objArr[i].getClass();
            if (clsArr[i] == Integer.class) {
                clsArr[i] = Integer.TYPE;
            }
        }
        Method declaredMethod = cls.getDeclaredMethod(str, clsArr);
        if (!declaredMethod.isAccessible()) {
            declaredMethod.setAccessible(true);
        }
        return declaredMethod.invoke(obj, objArr);
    }

    public static String m1012a() {
        Calendar instance = Calendar.getInstance();
        int i = instance.get(5);
        int i2 = instance.get(1);
        int i3 = instance.get(2) + 1;
        int i4 = instance.get(11);
        int i5 = instance.get(12);
        int i6 = instance.get(13);
        return String.format(Locale.CHINA, "%d-%02d-%02d %02d:%02d:%02d", new Object[]{Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i), Integer.valueOf(i4), Integer.valueOf(i5), Integer.valueOf(i6)});
    }

    public static String m1013a(C0441a c0441a, C0451g c0451g, Location location, String str, int i) {
        String b;
        StringBuffer stringBuffer = new StringBuffer(1024);
        if (c0441a != null) {
            b = C0443b.m855a().m868b(c0441a);
            if (b != null) {
                stringBuffer.append(b);
            }
        }
        if (c0451g != null) {
            b = i == 0 ? c0451g.m937b() : c0451g.m940c();
            if (b != null) {
                stringBuffer.append(b);
            }
        }
        if (location != null) {
            b = (f902d == 0 || i == 0) ? C0448d.m902b(location) : C0448d.m909c(location);
            if (b != null) {
                stringBuffer.append(b);
            }
        }
        boolean z = false;
        if (i == 0) {
            z = true;
        }
        b = C0459b.m980a().m981a(z);
        if (b != null) {
            stringBuffer.append(b);
        }
        if (str != null) {
            stringBuffer.append(str);
        }
        Object d = C0370d.m393a().m398d();
        if (!TextUtils.isEmpty(d)) {
            stringBuffer.append("&bc=").append(d);
        }
        if (i == 0) {
            if (c0441a != null) {
                b = C0443b.m855a().m867a(c0441a);
                if (b != null && b.length() + stringBuffer.length() < 750) {
                    stringBuffer.append(b);
                }
            }
            b = stringBuffer.toString();
            if (location != null || c0451g == null) {
                f912n = 3;
            } else {
                try {
                    float speed = location.getSpeed();
                    int i2 = f902d;
                    int d2 = c0451g.m943d();
                    int a = c0451g.m933a();
                    boolean e = c0451g.m944e();
                    if (speed < an && ((i2 == 1 || i2 == 0) && (d2 < ap || e))) {
                        f912n = 1;
                    } else if (speed >= ao || (!(i2 == 1 || i2 == 0 || i2 == 3) || (d2 >= aq && a <= ar))) {
                        f912n = 3;
                    } else {
                        f912n = 2;
                    }
                } catch (Exception e2) {
                    f912n = 3;
                }
            }
            return b;
        }
        if (c0441a != null) {
            b = C0443b.m855a().m867a(c0441a);
            stringBuffer.append(b);
        }
        b = stringBuffer.toString();
        if (location != null) {
        }
        f912n = 3;
        return b;
    }

    public static String m1014a(File file, String str) {
        if (!file.isFile()) {
            return null;
        }
        byte[] bArr = new byte[1024];
        try {
            MessageDigest instance = MessageDigest.getInstance(str);
            FileInputStream fileInputStream = new FileInputStream(file);
            while (true) {
                int read = fileInputStream.read(bArr, 0, 1024);
                if (read != -1) {
                    instance.update(bArr, 0, read);
                } else {
                    fileInputStream.close();
                    return new BigInteger(1, instance.digest()).toString(16);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean m1015a(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager == null) {
            return false;
        }
        NetworkInfo[] allNetworkInfo = connectivityManager.getAllNetworkInfo();
        if (allNetworkInfo == null) {
            return false;
        }
        for (NetworkInfo state : allNetworkInfo) {
            if (state.getState() == State.CONNECTED) {
                return true;
            }
        }
        return false;
    }

    public static boolean m1016a(BDLocation bDLocation) {
        int locType = bDLocation.getLocType();
        return locType > 100 && locType < 200;
    }

    public static int m1017b(Context context) {
        try {
            return System.getInt(context.getContentResolver(), "airplane_mode_on", 0);
        } catch (Exception e) {
            return 2;
        }
    }

    private static int m1018b(Context context, String str) {
        return (context.checkPermission(str, Process.myPid(), Process.myUid()) == 0 ? 1 : 0) == 0 ? 0 : 1;
    }

    public static int m1019b(Object obj, String str, Object... objArr) throws Exception {
        Class cls = obj.getClass();
        Class[] clsArr = new Class[objArr.length];
        int length = objArr.length;
        for (int i = 0; i < length; i++) {
            clsArr[i] = objArr[i].getClass();
            if (clsArr[i] == Integer.class) {
                clsArr[i] = Integer.TYPE;
            }
        }
        Method declaredMethod = cls.getDeclaredMethod(str, clsArr);
        if (!declaredMethod.isAccessible()) {
            declaredMethod.setAccessible(true);
        }
        return ((Integer) declaredMethod.invoke(obj, objArr)).intValue();
    }

    public static String m1020b() {
        try {
            Enumeration networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                Enumeration inetAddresses = ((NetworkInterface) networkInterfaces.nextElement()).getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = (InetAddress) inetAddresses.nextElement();
                    if (!inetAddress.isLoopbackAddress() && (inetAddress instanceof Inet4Address)) {
                        byte[] address = inetAddress.getAddress();
                        int i = 0;
                        String str = "";
                        while (i < address.length) {
                            String toHexString = Integer.toHexString(address[i] & 255);
                            if (toHexString.length() == 1) {
                                toHexString = '0' + toHexString;
                            }
                            i++;
                            str = str + toHexString;
                        }
                        return str;
                    }
                }
            }
        } catch (Exception e) {
        }
        return null;
    }

    public static boolean m1021b(String str, String str2, String str3) {
        try {
            PublicKey generatePublic = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(C0303b.m64a(str3.getBytes())));
            Signature instance = Signature.getInstance("SHA1WithRSA");
            instance.initVerify(generatePublic);
            instance.update(str.getBytes());
            return instance.verify(C0303b.m64a(str2.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static int m1022c(Context context) {
        int i = -1;
        if (VERSION.SDK_INT < 23) {
            return -2;
        }
        try {
            return Secure.getInt(context.getContentResolver(), "location_mode", -1);
        } catch (Exception e) {
            return i;
        }
    }

    public static String m1023c() {
        return as;
    }

    public static String m1024d() {
        return av;
    }

    public static String m1025d(Context context) {
        int b = C0468j.m1018b(context, "android.permission.ACCESS_COARSE_LOCATION");
        int b2 = C0468j.m1018b(context, Permissions.PERMISSION_LOCATION);
        return "&per=" + b + "|" + b2 + "|" + C0468j.m1018b(context, Permissions.PERMISSION_PHONE);
    }

    public static String m1026e() {
        try {
            if (!Environment.getExternalStorageState().equals("mounted")) {
                return null;
            }
            String path = Environment.getExternalStorageDirectory().getPath();
            File file = new File(path + "/baidu/tempdata");
            if (file.exists()) {
                return path;
            }
            file.mkdirs();
            return path;
        } catch (Exception e) {
            return null;
        }
    }

    public static String m1027e(Context context) {
        int type;
        int i = -1;
        if (context != null) {
            try {
                NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
                if (activeNetworkInfo != null && activeNetworkInfo.isAvailable()) {
                    type = activeNetworkInfo.getType();
                    i = type;
                    return "&netc=" + i;
                }
            } catch (Exception e) {
            }
        }
        type = -1;
        i = type;
        return "&netc=" + i;
    }

    public static String m1028f() {
        String e = C0468j.m1026e();
        return e == null ? null : e + "/baidu/tempdata";
    }

    public static String m1029g() {
        try {
            File file = new File(C0455f.getServiceContext().getFilesDir() + File.separator + "lldt");
            if (!file.exists()) {
                file.mkdirs();
            }
            return file.getAbsolutePath();
        } catch (Exception e) {
            return null;
        }
    }
}
