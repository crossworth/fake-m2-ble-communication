package com.baidu.location;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import com.umeng.socialize.common.SocializeConstants;
import java.io.ByteArrayInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class C0533q {

    static class C0532a {
        C0532a() {
        }

        public static String m2205a(byte[] bArr) {
            char[] cArr = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
            StringBuilder stringBuilder = new StringBuilder(bArr.length * 2);
            for (int i = 0; i < bArr.length; i++) {
                stringBuilder.append(cArr[(bArr[i] & SocializeConstants.MASK_USER_CENTER_HIDE_AREA) >> 4]);
                stringBuilder.append(cArr[bArr[i] & 15]);
            }
            return stringBuilder.toString();
        }
    }

    public static String m2206a(Context context) {
        String packageName = context.getPackageName();
        return String.format("%s;%s", new Object[]{C0533q.m2207a(context, packageName), packageName});
    }

    private static String m2207a(Context context, String str) {
        String a;
        String str2 = "";
        try {
            a = C0533q.m2208a((X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(context.getPackageManager().getPackageInfo(str, 64).signatures[0].toByteArray())));
        } catch (NameNotFoundException e) {
            a = str2;
        } catch (CertificateException e2) {
            a = str2;
        }
        StringBuffer stringBuffer = new StringBuffer();
        int i = 0;
        while (i < a.length()) {
            stringBuffer.append(a.charAt(i));
            if (i > 0 && i % 2 == 1 && i < a.length() - 1) {
                stringBuffer.append(":");
            }
            i++;
        }
        return stringBuffer.toString();
    }

    static String m2208a(X509Certificate x509Certificate) {
        try {
            return C0532a.m2205a(C0533q.m2209a(x509Certificate.getEncoded()));
        } catch (CertificateEncodingException e) {
            return null;
        }
    }

    static byte[] m2209a(byte[] bArr) {
        try {
            return MessageDigest.getInstance("SHA1").digest(bArr);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
}
