package com.baidu.location.p006h;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;
import com.baidu.location.C0455f;
import java.util.Map;

public abstract class C0335e {
    private static String f129a = "10.0.0.172";
    private static int f130b = 80;
    public static int f131g = C0458a.f832g;
    protected static int f132o = 0;
    public String f133h = null;
    public int f134i = 3;
    public String f135j = null;
    public Map<String, Object> f136k = null;
    public String f137l = null;
    public byte[] f138m = null;
    public String f139n = null;

    private static int m196a(Context context, NetworkInfo networkInfo) {
        String toLowerCase;
        if (!(networkInfo == null || networkInfo.getExtraInfo() == null)) {
            toLowerCase = networkInfo.getExtraInfo().toLowerCase();
            if (toLowerCase != null) {
                if (toLowerCase.startsWith("cmwap") || toLowerCase.startsWith("uniwap") || toLowerCase.startsWith("3gwap")) {
                    toLowerCase = Proxy.getDefaultHost();
                    if (toLowerCase == null || toLowerCase.equals("") || toLowerCase.equals("null")) {
                        toLowerCase = "10.0.0.172";
                    }
                    f129a = toLowerCase;
                    return C0458a.f829d;
                } else if (toLowerCase.startsWith("ctwap")) {
                    toLowerCase = Proxy.getDefaultHost();
                    if (toLowerCase == null || toLowerCase.equals("") || toLowerCase.equals("null")) {
                        toLowerCase = "10.0.0.200";
                    }
                    f129a = toLowerCase;
                    return C0458a.f829d;
                } else if (toLowerCase.startsWith("cmnet") || toLowerCase.startsWith("uninet") || toLowerCase.startsWith("ctnet") || toLowerCase.startsWith("3gnet")) {
                    return C0458a.f830e;
                }
            }
        }
        toLowerCase = Proxy.getDefaultHost();
        if (toLowerCase != null && toLowerCase.length() > 0) {
            if ("10.0.0.172".equals(toLowerCase.trim())) {
                f129a = "10.0.0.172";
                return C0458a.f829d;
            } else if ("10.0.0.200".equals(toLowerCase.trim())) {
                f129a = "10.0.0.200";
                return C0458a.f829d;
            }
        }
        return C0458a.f830e;
    }

    private void mo1747b() {
        f131g = mo1746c();
    }

    private int mo1746c() {
        Context serviceContext = C0455f.getServiceContext();
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) serviceContext.getSystemService("connectivity");
            if (connectivityManager == null) {
                return C0458a.f832g;
            }
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo == null || !activeNetworkInfo.isAvailable()) {
                return C0458a.f832g;
            }
            if (activeNetworkInfo.getType() != 1) {
                return C0335e.m196a(serviceContext, activeNetworkInfo);
            }
            String defaultHost = Proxy.getDefaultHost();
            return (defaultHost == null || defaultHost.length() <= 0) ? C0458a.f831f : C0458a.f833h;
        } catch (Exception e) {
            return C0458a.f832g;
        }
    }

    public abstract void mo1741a();

    public abstract void mo1742a(boolean z);

    public void m202b(boolean z) {
        new C0465g(this, z).start();
    }

    public void m203d() {
        new C0464f(this).start();
    }

    public void m204e() {
        m202b(false);
    }
}
