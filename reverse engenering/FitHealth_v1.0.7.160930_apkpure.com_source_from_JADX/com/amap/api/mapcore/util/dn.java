package com.amap.api.mapcore.util;

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
public class dn {

    /* compiled from: ClientInfo */
    private static class C0241a {
        String f470a;
        String f471b;
        String f472c;
        String f473d;
        String f474e;
        String f475f;
        String f476g;
        String f477h;
        String f478i;
        String f479j;
        String f480k;
        String f481l;
        String f482m;
        String f483n;
        String f484o;
        String f485p;
        String f486q;
        String f487r;
        String f488s;
        String f489t;

        private C0241a() {
        }
    }

    public static String m621a(Context context, String str, String str2) {
        String str3 = null;
        try {
            str3 = ds.m681b(dl.m606e(context) + ":" + str.substring(0, str.length() - 3) + ":" + str2);
        } catch (Throwable th) {
            eb.m742a(th, "CInfo", "Scode");
        }
        return str3;
    }

    public static String m616a() {
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
                eb.m742a(th2, "CInfo", "getTS");
                return str;
            }
        } catch (Throwable th32) {
            th = th32;
            str = str2;
            th2 = th;
            eb.m742a(th2, "CInfo", "getTS");
            return str;
        }
        return str;
    }

    public static byte[] m624a(Context context, byte[] bArr) throws CertificateException, InvalidKeySpecException, NoSuchAlgorithmException, NullPointerException, IOException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        KeyGenerator instance = KeyGenerator.getInstance("AES");
        if (instance == null) {
            return null;
        }
        instance.init(256);
        byte[] encoded = instance.generateKey().getEncoded();
        Key a = dx.m716a(context);
        if (a == null) {
            return null;
        }
        Object a2 = dr.m672a(encoded, a);
        Object a3 = dr.m673a(encoded, bArr);
        byte[] bArr2 = new byte[(a2.length + a3.length)];
        System.arraycopy(a2, 0, bArr2, 0, a2.length);
        System.arraycopy(a3, 0, bArr2, a2.length, a3.length);
        return bArr2;
    }

    @Deprecated
    public static String m620a(Context context, dv dvVar, Map<String, String> map, boolean z) {
        try {
            return m618a(context, m615a(context, z));
        } catch (Throwable th) {
            eb.m742a(th, "CInfo", "rsaLocClineInfo");
            return null;
        }
    }

    public static String m625b(Context context, byte[] bArr) {
        try {
            return m628d(context, bArr);
        } catch (Throwable th) {
            eb.m742a(th, "CInfo", "AESData");
            return "";
        }
    }

    public static byte[] m627c(Context context, byte[] bArr) throws CertificateException, InvalidKeySpecException, NoSuchAlgorithmException, NullPointerException, IOException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        Key a = dx.m716a(context);
        if (bArr.length <= 117) {
            return dr.m672a(bArr, a);
        }
        byte[] bArr2 = new byte[117];
        System.arraycopy(bArr, 0, bArr2, 0, 117);
        Object a2 = dr.m672a(bArr2, a);
        Object obj = new byte[((bArr.length + 128) - 117)];
        System.arraycopy(a2, 0, obj, 0, 128);
        System.arraycopy(bArr, 117, obj, 128, bArr.length - 117);
        return obj;
    }

    private static String m618a(Context context, C0241a c0241a) {
        return dr.m671a(m626b(context, c0241a));
    }

    private static byte[] m626b(Context context, C0241a c0241a) {
        ByteArrayOutputStream byteArrayOutputStream;
        Throwable th;
        Throwable th2;
        byte[] bArr = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                m622a(byteArrayOutputStream, c0241a.f470a);
                m622a(byteArrayOutputStream, c0241a.f471b);
                m622a(byteArrayOutputStream, c0241a.f472c);
                m622a(byteArrayOutputStream, c0241a.f473d);
                m622a(byteArrayOutputStream, c0241a.f474e);
                m622a(byteArrayOutputStream, c0241a.f475f);
                m622a(byteArrayOutputStream, c0241a.f476g);
                m622a(byteArrayOutputStream, c0241a.f477h);
                m622a(byteArrayOutputStream, c0241a.f478i);
                m622a(byteArrayOutputStream, c0241a.f479j);
                m622a(byteArrayOutputStream, c0241a.f480k);
                m622a(byteArrayOutputStream, c0241a.f481l);
                m622a(byteArrayOutputStream, c0241a.f482m);
                m622a(byteArrayOutputStream, c0241a.f483n);
                m622a(byteArrayOutputStream, c0241a.f484o);
                m622a(byteArrayOutputStream, c0241a.f485p);
                m622a(byteArrayOutputStream, c0241a.f486q);
                m622a(byteArrayOutputStream, c0241a.f487r);
                m622a(byteArrayOutputStream, c0241a.f488s);
                m622a(byteArrayOutputStream, c0241a.f489t);
                bArr = m623a(context, byteArrayOutputStream);
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
                    eb.m742a(th3, "CInfo", "InitXInfo");
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

    private static byte[] m623a(Context context, ByteArrayOutputStream byteArrayOutputStream) throws CertificateException, NoSuchAlgorithmException, IOException, BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException {
        return m627c(context, dx.m724b(byteArrayOutputStream.toByteArray()));
    }

    static String m628d(Context context, byte[] bArr) throws InvalidKeyException, IOException, InvalidKeySpecException, NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, CertificateException {
        byte[] b = dx.m724b(m624a(context, bArr));
        if (b != null) {
            return dr.m671a(b);
        }
        return "";
    }

    public static void m622a(ByteArrayOutputStream byteArrayOutputStream, String str) {
        if (TextUtils.isEmpty(str)) {
            dx.m717a(byteArrayOutputStream, (byte) 0, new byte[0]);
            return;
        }
        byte b;
        if (str.getBytes().length > 255) {
            b = (byte) -1;
        } else {
            b = (byte) str.getBytes().length;
        }
        dx.m717a(byteArrayOutputStream, b, dx.m721a(str));
    }

    public static String m629e(Context context, byte[] bArr) {
        try {
            return m628d(context, bArr);
        } catch (Throwable th) {
            th.printStackTrace();
            return "";
        }
    }

    public static String m617a(Context context) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            stringBuilder.append("\"key\":\"").append(dl.m607f(context)).append("\",\"platform\":\"android\",\"diu\":\"").append(dq.m660q(context)).append("\",\"pkg\":\"").append(dl.m604c(context)).append("\",\"model\":\"").append(Build.MODEL).append("\",\"appname\":\"").append(dl.m603b(context)).append("\",\"appversion\":\"").append(dl.m605d(context)).append("\",\"sysversion\":\"").append(VERSION.RELEASE).append("\",");
        } catch (Throwable th) {
            eb.m742a(th, "CInfo", "getPublicJSONInfo");
        }
        return stringBuilder.toString();
    }

    public static String m619a(Context context, dv dvVar) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            stringBuilder.append("\"sim\":\"").append(dq.m648e(context)).append("\",\"sdkversion\":\"").append(dvVar.m708b()).append("\",\"product\":\"").append(dvVar.m706a()).append("\",\"ed\":\"").append(dvVar.m710d()).append("\",\"nt\":\"").append(dq.m646c(context)).append("\",\"np\":\"").append(dq.m638a(context)).append("\",\"mnc\":\"").append(dq.m644b(context)).append("\",\"ant\":\"").append(dq.m647d(context)).append("\"");
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return stringBuilder.toString();
    }

    private static C0241a m615a(Context context, boolean z) {
        C0241a c0241a = new C0241a();
        c0241a.f470a = dq.m660q(context);
        c0241a.f471b = dq.m652i(context);
        String f = dq.m649f(context);
        if (f == null) {
            f = "";
        }
        c0241a.f472c = f;
        c0241a.f473d = dl.m604c(context);
        c0241a.f474e = Build.MODEL;
        c0241a.f475f = Build.MANUFACTURER;
        c0241a.f476g = Build.DEVICE;
        c0241a.f477h = dl.m603b(context);
        c0241a.f478i = dl.m605d(context);
        c0241a.f479j = String.valueOf(VERSION.SDK_INT);
        c0241a.f480k = dq.m661r(context);
        c0241a.f481l = dq.m659p(context);
        c0241a.f482m = dq.m656m(context) + "";
        c0241a.f483n = dq.m655l(context) + "";
        c0241a.f484o = dq.m662s(context);
        c0241a.f485p = dq.m654k(context);
        if (z) {
            c0241a.f486q = "";
        } else {
            c0241a.f486q = dq.m651h(context);
        }
        if (z) {
            c0241a.f487r = "";
        } else {
            c0241a.f487r = dq.m650g(context);
        }
        if (z) {
            c0241a.f488s = "";
            c0241a.f489t = "";
        } else {
            String[] j = dq.m653j(context);
            c0241a.f488s = j[0];
            c0241a.f489t = j[1];
        }
        return c0241a;
    }
}
