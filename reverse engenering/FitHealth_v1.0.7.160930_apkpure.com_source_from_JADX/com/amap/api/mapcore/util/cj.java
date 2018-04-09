package com.amap.api.mapcore.util;

import android.content.Context;
import com.amap.api.maps.AMapException;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.HttpHeaders;

/* compiled from: ProtocalHandler */
public abstract class cj<T, V> extends fw {
    protected T f4154a;
    protected int f4155b = 1;
    protected String f4156c = "";
    protected Context f4157d;
    protected final int f4158e = 5000;
    protected final int f4159f = 50000;
    private int f4160j = 1;

    protected abstract V mo3002b(String str) throws AMapException;

    public cj(Context context, T t) {
        m4183a(context, t);
    }

    private void m4183a(Context context, T t) {
        this.f4157d = context;
        this.f4154a = t;
    }

    public V m4188d() throws AMapException {
        if (this.f4154a != null) {
            return m4184h();
        }
        return null;
    }

    private V m4184h() throws AMapException {
        int i = 0;
        V v = null;
        while (i < this.f4155b) {
            try {
                fv a = fv.m4290a(false);
                m973a(dt.m688a(this.f4157d));
                v = mo3003a(a.m4298d(this));
                i = this.f4155b;
            } catch (Throwable e) {
                ee.m4243a(e, "ProtocalHandler", "getDataMayThrow AMapException");
                e.printStackTrace();
                i++;
                if (i >= this.f4155b) {
                    throw new AMapException(e.getErrorMessage());
                }
            } catch (Throwable e2) {
                ee.m4243a(e2, "ProtocalHandler", "getDataMayThrow AMapCoreException");
                e2.printStackTrace();
                i++;
                if (i < this.f4155b) {
                    try {
                        Thread.sleep((long) (this.f4160j * 1000));
                    } catch (InterruptedException e3) {
                        ee.m4243a(e2, "ProtocalHandler", "getDataMayThrow InterruptedException");
                        e2.printStackTrace();
                        throw new AMapException(e2.getMessage());
                    }
                }
                m4189e();
                throw new AMapException(e2.m599a());
            }
        }
        return v;
    }

    protected V mo3004b(byte[] bArr) throws AMapException {
        String str;
        try {
            str = new String(bArr, "utf-8");
        } catch (Throwable e) {
            ee.m4243a(e, "ProtocalHandler", "loadData Exception");
            e.printStackTrace();
            str = null;
        }
        if (str == null || str.equals("")) {
            return null;
        }
        dj.m579a(str);
        return mo3002b(str);
    }

    public Map<String, String> mo1632c() {
        Map<String, String> hashMap = new HashMap();
        hashMap.put("Content-Type", "application/x-www-form-urlencoded");
        hashMap.put(HttpHeaders.ACCEPT_ENCODING, "gzip");
        hashMap.put("User-Agent", C0273r.f697d);
        hashMap.put("X-INFO", dn.m620a(this.f4157d, dj.m597e(), null, false));
        return hashMap;
    }

    private V mo3003a(byte[] bArr) throws AMapException {
        return mo3004b(bArr);
    }

    protected V m4189e() {
        return null;
    }
}
