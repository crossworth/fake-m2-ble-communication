package com.aps;

import com.amap.api.location.core.C0187b;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* compiled from: Aes */
public class C0441b {
    private String f1796a = "AES/CBC/PKCS5Padding";
    private Cipher f1797b = null;

    C0441b() {
        try {
            Key secretKeySpec = new SecretKeySpec("#a@u!t*o(n)a&v^i".getBytes("UTF-8"), "AES");
            AlgorithmParameterSpec ivParameterSpec = new IvParameterSpec("_a+m-a=p?a>p<s%3".getBytes("UTF-8"));
            this.f1797b = Cipher.getInstance(this.f1796a);
            this.f1797b.init(2, secretKeySpec, ivParameterSpec);
        } catch (Throwable th) {
            th.printStackTrace();
            C0470t.m2008a(th);
        }
    }

    String m1839a(String str, String str2) {
        if (str == null || str.length() == 0) {
            return null;
        }
        try {
            return new String(this.f1797b.doFinal(m1838a(str)), str2);
        } catch (Throwable e) {
            C0470t.m2008a(e);
            return null;
        }
    }

    private byte[] m1838a(String str) {
        byte[] bArr = null;
        if (!(str == null || str.length() == 0 || str.length() % 2 != 0)) {
            try {
                bArr = new byte[(str.length() / 2)];
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < str.length(); i += 2) {
                    stringBuilder.delete(0, stringBuilder.length());
                    stringBuilder.append("0X");
                    stringBuilder.append(str.substring(i, i + 2));
                    bArr[i / 2] = (byte) Integer.decode(stringBuilder.toString()).intValue();
                }
            } catch (Throwable th) {
                th.printStackTrace();
                C0470t.m2008a(th);
            }
        }
        return bArr;
    }

    public static String m1837a(byte[] bArr) {
        String str = "";
        if (bArr != null) {
            try {
                str = C0187b.m83a(bArr);
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
        return str;
    }
}
