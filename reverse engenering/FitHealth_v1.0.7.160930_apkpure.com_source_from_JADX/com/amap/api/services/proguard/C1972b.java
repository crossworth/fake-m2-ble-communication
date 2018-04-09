package com.amap.api.services.proguard;

import android.content.Context;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.HttpHeaders;

/* compiled from: BasicLBSRestHandler */
public abstract class C1972b<T, V> extends C1608a<T, V> {
    protected abstract String mo3048e();

    public C1972b(Context context, T t) {
        super(context, t);
    }

    public byte[] mo1758f() {
        byte[] bArr = null;
        try {
            String e = mo3048e();
            String d = mo3703d(e);
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(e);
            e = av.m1223a();
            stringBuffer.append("&ts=" + e);
            stringBuffer.append("&scode=" + av.m1228a(this.d, e, d));
            bArr = stringBuffer.toString().getBytes("utf-8");
        } catch (Throwable th) {
            C0390i.m1594a(th, "ProtocalHandler", "getEntity");
        }
        return bArr;
    }

    public Map<String, String> mo1756b() {
        return null;
    }

    public Map<String, String> mo1757c() {
        Map<String, String> hashMap = new HashMap();
        hashMap.put("Content-Type", "application/x-www-form-urlencoded");
        hashMap.put(HttpHeaders.ACCEPT_ENCODING, "gzip");
        hashMap.put("User-Agent", "AMAP SDK Android Search 3.3.0");
        hashMap.put("X-INFO", av.m1227a(this.d, C0394o.f1543a, null, false));
        hashMap.put("platinfo", String.format("platform=Android&sdkversion=%s&product=%s", new Object[]{"3.3.0", "sea"}));
        hashMap.put("logversion", "2.1");
        return hashMap;
    }

    protected V mo3047d() {
        return null;
    }

    private String mo3703d(String str) {
        String[] split = str.split("&");
        Arrays.sort(split);
        StringBuffer stringBuffer = new StringBuffer();
        for (String c : split) {
            stringBuffer.append(m5791c(c));
            stringBuffer.append("&");
        }
        String stringBuffer2 = stringBuffer.toString();
        if (stringBuffer2.length() > 1) {
            return (String) stringBuffer2.subSequence(0, stringBuffer2.length() - 1);
        }
        return str;
    }

    protected String m5789b(String str) {
        if (str == null) {
            return str;
        }
        try {
            return URLEncoder.encode(str, "utf-8");
        } catch (Throwable e) {
            C0390i.m1594a(e, "ProtocalHandler", "strEncoderUnsupportedEncodingException");
            return "";
        } catch (Throwable e2) {
            C0390i.m1594a(e2, "ProtocalHandler", "strEncoderException");
            return "";
        }
    }

    protected String m5791c(String str) {
        if (str == null) {
            return str;
        }
        try {
            return URLDecoder.decode(str, "utf-8");
        } catch (Throwable e) {
            C0390i.m1594a(e, "ProtocalHandler", "strReEncoder");
            return "";
        } catch (Throwable e2) {
            C0390i.m1594a(e2, "ProtocalHandler", "strReEncoderException");
            return "";
        }
    }
}
