package com.aps;

import android.content.ContentResolver;
import android.content.Context;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build.VERSION;
import android.provider.Settings.Global;
import android.provider.Settings.System;
import android.telephony.CellLocation;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;
import android.widget.Toast;
import com.amap.api.location.core.C0188c;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;
import org.apache.http.conn.params.ConnManagerPNames;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;

/* compiled from: Utils */
public class C0470t {
    private C0470t() {
    }

    public static void m2010a(Object... objArr) {
    }

    static void m2007a(Context context, String str) {
        int i;
        if (str == null) {
            str = "null";
        }
        if (C0188c.m95j().indexOf("test") != -1) {
            i = 1;
        } else if (C0446f.f1850d.indexOf("test") != -1) {
            i = 1;
        } else {
            char[] cArr = null;
            if (C0188c.m95j().length() > 0) {
                cArr = C0188c.m95j().substring(7, 8).toCharArray();
            }
            if (cArr == null || !Character.isLetter(cArr[0])) {
                i = 1;
            } else {
                i = 0;
            }
        }
        if (i != 0 && context != null) {
            Toast.makeText(context, str, 0).show();
            C0470t.m2010a(str);
        }
    }

    public static void m2008a(Throwable th) {
    }

    static boolean m2012a(C0442c c0442c) {
        if (c0442c == null || c0442c.m1874i().equals("5") || c0442c.m1874i().equals("6")) {
            return false;
        }
        double d = c0442c.m1864d();
        double e = c0442c.m1866e();
        float f = c0442c.m1868f();
        if (d == 0.0d && e == 0.0d && ((double) f) == 0.0d) {
            return false;
        }
        return true;
    }

    static int m2004a(int i) {
        return (i * 2) - 113;
    }

    public static String[] m2014a(TelephonyManager telephonyManager) {
        int i = 1;
        int i2 = 0;
        String[] strArr = new String[]{"0", "0"};
        Object obj = null;
        if (telephonyManager != null) {
            try {
                obj = telephonyManager.getNetworkOperator();
            } catch (Exception e) {
            }
        }
        if (TextUtils.isEmpty(obj)) {
            i = i2;
        } else if (!TextUtils.isDigitsOnly(obj)) {
            i = i2;
        } else if (obj.length() <= 4) {
            i = i2;
        }
        if (i != 0) {
            strArr[0] = obj.substring(0, 3);
            char[] toCharArray = obj.substring(3).toCharArray();
            i = i2;
            while (i < toCharArray.length && Character.isDigit(toCharArray[i])) {
                i++;
            }
            strArr[1] = obj.substring(3, i + 3);
        }
        try {
            i2 = Integer.parseInt(strArr[0]);
        } catch (Exception e2) {
        }
        if (i2 == 0) {
            strArr[0] = "0";
        }
        return strArr;
    }

    static int m2005a(CellLocation cellLocation, Context context) {
        if (C0470t.m2011a(context)) {
            C0470t.m2010a("air plane mode on");
            return 9;
        } else if (cellLocation instanceof GsmCellLocation) {
            return 1;
        } else {
            try {
                Class.forName("android.telephony.cdma.CdmaCellLocation");
                return 2;
            } catch (Throwable th) {
                th.printStackTrace();
                C0470t.m2008a(th);
                return 9;
            }
        }
    }

    static long m2006a() {
        return System.currentTimeMillis();
    }

    static boolean m2011a(Context context) {
        boolean z = true;
        if (context == null) {
            return false;
        }
        ContentResolver contentResolver = context.getContentResolver();
        if (C0470t.m2015b() < 17) {
            try {
                if (System.getInt(contentResolver, "airplane_mode_on", 0) != 1) {
                    z = false;
                }
                return z;
            } catch (Throwable th) {
                th.printStackTrace();
                C0470t.m2008a(th);
                return false;
            }
        }
        try {
            if (Global.getInt(contentResolver, "airplane_mode_on", 0) != 1) {
                z = false;
            }
            return z;
        } catch (Throwable th2) {
            th2.printStackTrace();
            C0470t.m2008a(th2);
            return false;
        }
    }

    static float m2003a(double[] dArr) {
        if (dArr.length != 4) {
            return 0.0f;
        }
        float[] fArr = new float[1];
        Location.distanceBetween(dArr[0], dArr[1], dArr[2], dArr[3], fArr);
        return fArr[0];
    }

    static Object m2017b(Context context, String str) {
        if (context == null) {
            return null;
        }
        return context.getApplicationContext().getSystemService(str);
    }

    static void m2009a(HttpParams httpParams, int i) {
        httpParams.setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, i);
        httpParams.setIntParameter(CoreConnectionPNames.SO_TIMEOUT, i);
        httpParams.setLongParameter(ConnManagerPNames.TIMEOUT, (long) i);
    }

    static int m2015b() {
        int i = 0;
        try {
            return VERSION.SDK_INT;
        } catch (Throwable th) {
            th.printStackTrace();
            C0470t.m2008a(th);
            return i;
        }
    }

    public static byte[] m2013a(byte[] bArr) {
        byte[] toByteArray;
        Throwable th;
        try {
            OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            gZIPOutputStream.write(bArr);
            gZIPOutputStream.close();
            toByteArray = byteArrayOutputStream.toByteArray();
            try {
                byteArrayOutputStream.close();
            } catch (Throwable th2) {
                th = th2;
                th.printStackTrace();
                return toByteArray;
            }
        } catch (Throwable th3) {
            Throwable th4 = th3;
            toByteArray = null;
            th = th4;
            th.printStackTrace();
            return toByteArray;
        }
        return toByteArray;
    }

    static NetworkInfo m2016b(Context context) {
        NetworkInfo activeNetworkInfo;
        ConnectivityManager connectivityManager = (ConnectivityManager) C0470t.m2017b(context, "connectivity");
        if (connectivityManager != null) {
            try {
                activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            } catch (SecurityException e) {
                return null;
            }
        }
        activeNetworkInfo = null;
        return activeNetworkInfo;
    }
}
