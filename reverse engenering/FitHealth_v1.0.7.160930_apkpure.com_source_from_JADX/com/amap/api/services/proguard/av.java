package com.amap.api.services.proguard;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;

/* compiled from: ClientInfo */
public class av {

    /* compiled from: ClientInfo */
    private static class C0366a {
        String f1349a;
        String f1350b;
        String f1351c;
        String f1352d;
        String f1353e;
        String f1354f;
        String f1355g;
        String f1356h;
        String f1357i;
        String f1358j;
        String f1359k;
        String f1360l;
        String f1361m;
        String f1362n;
        String f1363o;
        String f1364p;
        String f1365q;
        String f1366r;
        String f1367s;
        String f1368t;

        private C0366a() {
        }
    }

    public static String m1228a(Context context, String str, String str2) {
        String str3 = null;
        try {
            str3 = ay.m1282b(as.m1214e(context) + ":" + str.substring(0, str.length() - 3) + ":" + str2);
        } catch (Throwable th) {
            be.m1340a(th, "CInfo", "Scode");
        }
        return str3;
    }

    public static String m1223a() {
        String str;
        Throwable th;
        Throwable th2;
        String str2 = null;
        try {
            str2 = String.valueOf(System.currentTimeMillis());
            try {
                int length = str2.length();
                str = str2.substring(0, length - 2) + "1" + str2.substring(length - 1);
            } catch (Throwable th3) {
                th = th3;
                str = str2;
                th2 = th;
                be.m1340a(th2, "CInfo", "getTS");
                return str;
            }
        } catch (Throwable th32) {
            th = th32;
            str = str2;
            th2 = th;
            be.m1340a(th2, "CInfo", "getTS");
            return str;
        }
        return str;
    }

    public static byte[] m1232a(Context context, byte[] bArr) throws CertificateException, InvalidKeySpecException, NoSuchAlgorithmException, NullPointerException, IOException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        KeyGenerator instance = KeyGenerator.getInstance("AES");
        if (instance == null) {
            return null;
        }
        instance.init(256);
        byte[] encoded = instance.generateKey().getEncoded();
        Key a = bb.m1316a(context);
        if (a == null) {
            return null;
        }
        Object a2 = ax.m1273a(encoded, a);
        Object a3 = ax.m1274a(encoded, bArr);
        byte[] bArr2 = new byte[(a2.length + a3.length)];
        System.arraycopy(a2, 0, bArr2, 0, a2.length);
        System.arraycopy(a3, 0, bArr2, a2.length, a3.length);
        return bArr2;
    }

    public static byte[] m1231a(Context context, boolean z) {
        try {
            return m1235b(context, m1233b(context, z));
        } catch (Throwable th) {
            be.m1340a(th, "CInfo", "getGZipXInfo");
            return null;
        }
    }

    @Deprecated
    public static String m1227a(Context context, ba baVar, Map<String, String> map, boolean z) {
        try {
            return m1225a(context, m1233b(context, z));
        } catch (Throwable th) {
            be.m1340a(th, "CInfo", "rsaLocClineInfo");
            return null;
        }
    }

    public static String m1234b(Context context, byte[] bArr) {
        try {
            return m1237d(context, bArr);
        } catch (Throwable th) {
            be.m1340a(th, "CInfo", "AESData");
            return "";
        }
    }

    public static byte[] m1236c(Context context, byte[] bArr) throws CertificateException, InvalidKeySpecException, NoSuchAlgorithmException, NullPointerException, IOException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        Key a = bb.m1316a(context);
        if (bArr.length <= 117) {
            return ax.m1273a(bArr, a);
        }
        byte[] bArr2 = new byte[117];
        System.arraycopy(bArr, 0, bArr2, 0, 117);
        Object a2 = ax.m1273a(bArr2, a);
        Object obj = new byte[((bArr.length + 128) - 117)];
        System.arraycopy(a2, 0, obj, 0, 128);
        System.arraycopy(bArr, 117, obj, 128, bArr.length - 117);
        return obj;
    }

    private static String m1225a(Context context, C0366a c0366a) {
        return ax.m1272a(m1235b(context, c0366a));
    }

    private static byte[] m1235b(Context context, C0366a c0366a) {
        ByteArrayOutputStream byteArrayOutputStream;
        Throwable th;
        Throwable th2;
        byte[] bArr = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                m1229a(byteArrayOutputStream, c0366a.f1349a);
                m1229a(byteArrayOutputStream, c0366a.f1350b);
                m1229a(byteArrayOutputStream, c0366a.f1351c);
                m1229a(byteArrayOutputStream, c0366a.f1352d);
                m1229a(byteArrayOutputStream, c0366a.f1353e);
                m1229a(byteArrayOutputStream, c0366a.f1354f);
                m1229a(byteArrayOutputStream, c0366a.f1355g);
                m1229a(byteArrayOutputStream, c0366a.f1356h);
                m1229a(byteArrayOutputStream, c0366a.f1357i);
                m1229a(byteArrayOutputStream, c0366a.f1358j);
                m1229a(byteArrayOutputStream, c0366a.f1359k);
                m1229a(byteArrayOutputStream, c0366a.f1360l);
                m1229a(byteArrayOutputStream, c0366a.f1361m);
                m1229a(byteArrayOutputStream, c0366a.f1362n);
                m1229a(byteArrayOutputStream, c0366a.f1363o);
                m1229a(byteArrayOutputStream, c0366a.f1364p);
                m1229a(byteArrayOutputStream, c0366a.f1365q);
                m1229a(byteArrayOutputStream, c0366a.f1366r);
                m1229a(byteArrayOutputStream, c0366a.f1367s);
                m1229a(byteArrayOutputStream, c0366a.f1368t);
                bArr = m1230a(context, byteArrayOutputStream);
                if (byteArrayOutputStream != null) {
                    try {
                        byteArrayOutputStream.close();
                    } catch (Throwable th3) {
                        th3.printStackTrace();
                    }
                }
            } catch (Throwable th4) {
                th3 = th4;
                try {
                    be.m1340a(th3, "CInfo", "InitXInfo");
                    if (byteArrayOutputStream != null) {
                        try {
                            byteArrayOutputStream.close();
                        } catch (Throwable th32) {
                            th32.printStackTrace();
                        }
                    }
                    return bArr;
                } catch (Throwable th5) {
                    th2 = th5;
                    if (byteArrayOutputStream != null) {
                        try {
                            byteArrayOutputStream.close();
                        } catch (Throwable th322) {
                            th322.printStackTrace();
                        }
                    }
                    throw th2;
                }
            }
        } catch (Throwable th3222) {
            byteArrayOutputStream = bArr;
            th2 = th3222;
            if (byteArrayOutputStream != null) {
                byteArrayOutputStream.close();
            }
            throw th2;
        }
        return bArr;
    }

    private static byte[] m1230a(Context context, ByteArrayOutputStream byteArrayOutputStream) throws CertificateException, NoSuchAlgorithmException, IOException, BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException {
        return m1236c(context, bb.m1323b(byteArrayOutputStream.toByteArray()));
    }

    static String m1237d(Context context, byte[] bArr) throws InvalidKeyException, IOException, InvalidKeySpecException, NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, CertificateException {
        byte[] b = bb.m1323b(m1232a(context, bArr));
        if (b != null) {
            return ax.m1272a(b);
        }
        return "";
    }

    public static void m1229a(ByteArrayOutputStream byteArrayOutputStream, String str) {
        if (TextUtils.isEmpty(str)) {
            bb.m1317a(byteArrayOutputStream, (byte) 0, new byte[0]);
            return;
        }
        byte b;
        if (str.getBytes().length > 255) {
            b = (byte) -1;
        } else {
            b = (byte) str.getBytes().length;
        }
        bb.m1317a(byteArrayOutputStream, b, bb.m1321a(str));
    }

    public static String m1238e(Context context, byte[] bArr) {
        try {
            return m1237d(context, bArr);
        } catch (Throwable th) {
            th.printStackTrace();
            return "";
        }
    }

    public static String m1224a(Context context) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            stringBuilder.append("\"key\":\"").append(as.m1215f(context)).append("\",\"platform\":\"android\",\"diu\":\"").append(aw.m1261q(context)).append("\",\"pkg\":\"").append(as.m1212c(context)).append("\",\"model\":\"").append(Build.MODEL).append("\",\"appname\":\"").append(as.m1211b(context)).append("\",\"appversion\":\"").append(as.m1213d(context)).append("\",\"sysversion\":\"").append(VERSION.RELEASE).append("\",");
        } catch (Throwable th) {
            be.m1340a(th, "CInfo", "getPublicJSONInfo");
        }
        return stringBuilder.toString();
    }

    public static String m1226a(Context context, ba baVar) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            stringBuilder.append("\"sim\":\"").append(aw.m1249e(context)).append("\",\"sdkversion\":\"").append(baVar.m1309b()).append("\",\"product\":\"").append(baVar.m1308a()).append("\",\"ed\":\"").append(baVar.m1310c()).append("\",\"nt\":\"").append(aw.m1247c(context)).append("\",\"np\":\"").append(aw.m1239a(context)).append("\",\"mnc\":\"").append(aw.m1245b(context)).append("\",\"ant\":\"").append(aw.m1248d(context)).append("\"");
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return stringBuilder.toString();
    }

    private static C0366a m1233b(Context context, boolean z) {
        C0366a c0366a = new C0366a();
        c0366a.f1349a = aw.m1261q(context);
        c0366a.f1350b = aw.m1253i(context);
        String f = aw.m1250f(context);
        if (f == null) {
            f = "";
        }
        c0366a.f1351c = f;
        c0366a.f1352d = as.m1212c(context);
        c0366a.f1353e = Build.MODEL;
        c0366a.f1354f = Build.MANUFACTURER;
        c0366a.f1355g = Build.DEVICE;
        c0366a.f1356h = as.m1211b(context);
        c0366a.f1357i = as.m1213d(context);
        c0366a.f1358j = String.valueOf(VERSION.SDK_INT);
        c0366a.f1359k = aw.m1262r(context);
        c0366a.f1360l = aw.m1260p(context);
        c0366a.f1361m = aw.m1257m(context) + "";
        c0366a.f1362n = aw.m1256l(context) + "";
        c0366a.f1363o = aw.m1263s(context);
        c0366a.f1364p = aw.m1255k(context);
        if (z) {
            c0366a.f1365q = "";
        } else {
            c0366a.f1365q = aw.m1252h(context);
        }
        if (z) {
            c0366a.f1366r = "";
        } else {
            c0366a.f1366r = aw.m1251g(context);
        }
        if (z) {
            c0366a.f1367s = "";
            c0366a.f1368t = "";
        } else {
            String[] j = aw.m1254j(context);
            c0366a.f1367s = j[0];
            c0366a.f1368t = j[1];
        }
        return c0366a;
    }
}
