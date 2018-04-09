package com.amap.api.services.proguard;

import android.content.Context;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

/* compiled from: BinaryRequest */
public abstract class cr extends cw {
    protected Context f4376a;
    protected ba f4377b;

    public abstract byte[] mo3044a();

    public abstract byte[] mo3045d();

    public cr(Context context, ba baVar) {
        if (context != null) {
            this.f4376a = context.getApplicationContext();
        }
        this.f4377b = baVar;
    }

    public Map<String, String> mo1756b() {
        String f = as.m1215f(this.f4376a);
        String a = av.m1223a();
        String a2 = av.m1228a(this.f4376a, a, "key=" + f);
        Map<String, String> hashMap = new HashMap();
        hashMap.put("ts", a);
        hashMap.put("key", f);
        hashMap.put("scode", a2);
        return hashMap;
    }

    public final byte[] mo1758f() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            byteArrayOutputStream.write(m4482k());
            byteArrayOutputStream.write(m4491h());
            byteArrayOutputStream.write(m4483l());
            byteArrayOutputStream.write(m4484m());
            byte[] toByteArray = byteArrayOutputStream.toByteArray();
            try {
                byteArrayOutputStream.close();
                return toByteArray;
            } catch (Throwable th) {
                be.m1340a(th, "BinaryRequest", "getEntityBytes");
                return toByteArray;
            }
        } catch (Throwable th2) {
            be.m1340a(th2, "BinaryRequest", "getEntityBytes");
        }
        return null;
    }

    private byte[] m4482k() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            byteArrayOutputStream.write(bb.m1321a("PANDORA$"));
            byteArrayOutputStream.write(new byte[]{(byte) 1});
            byteArrayOutputStream.write(new byte[]{(byte) 0});
            byte[] toByteArray = byteArrayOutputStream.toByteArray();
            try {
                byteArrayOutputStream.close();
                return toByteArray;
            } catch (Throwable th) {
                be.m1340a(th, "BinaryRequest", "getBinaryHead");
                return toByteArray;
            }
        } catch (Throwable th2) {
            be.m1340a(th2, "BinaryRequest", "getBinaryHead");
        }
        return null;
    }

    public byte[] m4491h() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            byte[] a = av.m1231a(this.f4376a, false);
            byte[] a2 = m4486a(a);
            byteArrayOutputStream.write(new byte[]{(byte) 3});
            byteArrayOutputStream.write(a2);
            byteArrayOutputStream.write(a);
            a = bb.m1321a(mo3046e());
            if (a == null || a.length <= 0) {
                byteArrayOutputStream.write(new byte[]{(byte) 0, (byte) 0});
            } else {
                byteArrayOutputStream.write(m4486a(a));
                byteArrayOutputStream.write(a);
            }
            a = bb.m1321a(String.format("platform=Android&sdkversion=%s&product=%s", new Object[]{this.f4377b.m1309b(), this.f4377b.m1308a()}));
            if (a == null || a.length <= 0) {
                byteArrayOutputStream.write(new byte[]{(byte) 0, (byte) 0});
            } else {
                byteArrayOutputStream.write(m4486a(a));
                byteArrayOutputStream.write(a);
            }
            a = byteArrayOutputStream.toByteArray();
            try {
                byteArrayOutputStream.close();
                return a;
            } catch (Throwable th) {
                be.m1340a(th, "BinaryRequest", "getRequestEncryptData");
                return a;
            }
        } catch (Throwable th2) {
            be.m1340a(th2, "BinaryRequest", "getRequestEncryptData");
        }
        return new byte[]{(byte) 0};
    }

    protected String mo3046e() {
        return "2.1";
    }

    protected byte[] m4486a(byte[] bArr) {
        byte length = (byte) (bArr.length % 256);
        return new byte[]{(byte) (bArr.length / 256), length};
    }

    private byte[] m4483l() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            byte[] a = mo3044a();
            if (a == null || a.length == 0) {
                byteArrayOutputStream.write(new byte[]{(byte) 0});
                a = byteArrayOutputStream.toByteArray();
                try {
                    byteArrayOutputStream.close();
                    return a;
                } catch (Throwable th) {
                    be.m1340a(th, "BinaryRequest", "getRequestRawData");
                    return a;
                }
            }
            byteArrayOutputStream.write(new byte[]{(byte) 1});
            byteArrayOutputStream.write(m4486a(a));
            byteArrayOutputStream.write(a);
            a = byteArrayOutputStream.toByteArray();
            try {
                byteArrayOutputStream.close();
                return a;
            } catch (Throwable th2) {
                be.m1340a(th2, "BinaryRequest", "getRequestRawData");
                return a;
            }
        } catch (Throwable th3) {
            be.m1340a(th3, "BinaryRequest", "getRequestRawData");
        }
        return new byte[]{(byte) 0};
    }

    private byte[] m4484m() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            byte[] d = mo3045d();
            if (d == null || d.length == 0) {
                byteArrayOutputStream.write(new byte[]{(byte) 0});
                d = byteArrayOutputStream.toByteArray();
                try {
                    byteArrayOutputStream.close();
                    return d;
                } catch (Throwable th) {
                    be.m1340a(th, "BinaryRequest", "getRequestEncryptData");
                    return d;
                }
            }
            byteArrayOutputStream.write(new byte[]{(byte) 1});
            d = av.m1232a(this.f4376a, d);
            byteArrayOutputStream.write(m4486a(d));
            byteArrayOutputStream.write(d);
            d = byteArrayOutputStream.toByteArray();
            try {
                byteArrayOutputStream.close();
                return d;
            } catch (Throwable th2) {
                be.m1340a(th2, "BinaryRequest", "getRequestEncryptData");
                return d;
            }
        } catch (Throwable th3) {
            be.m1340a(th3, "BinaryRequest", "getRequestEncryptData");
        }
        return new byte[]{(byte) 0};
    }
}
