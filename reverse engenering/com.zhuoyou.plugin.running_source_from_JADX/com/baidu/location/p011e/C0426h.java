package com.baidu.location.p011e;

import android.content.Context;
import android.content.pm.ProviderInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import com.baidu.location.BDLocation;
import com.baidu.location.C0455f;
import com.baidu.location.p006h.C0459b;
import com.baidu.location.p012f.C0441a;
import com.baidu.location.p012f.C0451g;
import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public final class C0426h {
    static final String f675a = "http://ofloc.map.baidu.com/offline_loc";
    static final String f676b = "com.baidu.lbs.offlinelocationprovider";
    private static Context f677c;
    private static volatile C0426h f678d;
    private final File f679e;
    private final C0431k f680f;
    private final C0417d f681g;
    private final C0436m f682h;
    private final C0422g f683i;

    public enum C0423a {
        NEED_TO_LOG,
        NO_NEED_TO_LOG
    }

    public enum C0424b {
        IS_MIX_MODE,
        IS_NOT_MIX_MODE
    }

    private enum C0425c {
        NETWORK_UNKNOWN,
        NETWORK_WIFI,
        NETWORK_2G,
        NETWORK_3G,
        NETWORK_4G
    }

    private C0426h() {
        File file;
        try {
            file = new File(f677c.getFilesDir(), "ofld");
            try {
                if (!file.exists()) {
                    file.mkdir();
                }
            } catch (Exception e) {
            }
        } catch (Exception e2) {
            file = null;
        }
        this.f679e = file;
        this.f681g = new C0417d(this);
        this.f680f = new C0431k(this.f681g.m697a());
        this.f683i = new C0422g(this, this.f681g.m697a());
        this.f682h = new C0436m(this, this.f681g.m697a(), this.f683i.m761n());
    }

    private BDLocation m765a(String[] strArr) {
        BDLocation bDLocation = new BDLocation();
        ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();
        bDLocation = (FutureTask) newSingleThreadExecutor.submit(new C0427i(this, strArr));
        try {
            bDLocation = (BDLocation) bDLocation.get(2000, TimeUnit.MILLISECONDS);
            return bDLocation;
        } catch (InterruptedException e) {
            bDLocation.cancel(true);
            return null;
        } catch (ExecutionException e2) {
            bDLocation.cancel(true);
            return null;
        } catch (TimeoutException e3) {
            bDLocation.cancel(true);
            return null;
        } finally {
            newSingleThreadExecutor.shutdown();
        }
    }

    public static C0426h m767a() {
        if (f678d == null) {
            synchronized (C0426h.class) {
                if (f678d == null) {
                    if (f677c == null) {
                        C0426h.m768a(C0455f.getServiceContext());
                    }
                    f678d = new C0426h();
                }
            }
        }
        f678d.m773q();
        return f678d;
    }

    public static void m768a(Context context) {
        if (f677c == null) {
            f677c = context;
            C0459b.m980a().m983a(f677c);
        }
    }

    private static final Uri m771c(String str) {
        return Uri.parse(String.format("content://%s/", new Object[]{str}));
    }

    private void m773q() {
        this.f683i.m754g();
    }

    private boolean m774r() {
        ProviderInfo resolveContentProvider;
        ProviderInfo providerInfo;
        String packageName = f677c.getPackageName();
        try {
            resolveContentProvider = f677c.getPackageManager().resolveContentProvider(f676b, 0);
        } catch (Exception e) {
            resolveContentProvider = null;
        }
        if (resolveContentProvider == null) {
            String[] o = this.f683i.m762o();
            providerInfo = resolveContentProvider;
            for (String resolveContentProvider2 : o) {
                try {
                    providerInfo = f677c.getPackageManager().resolveContentProvider(resolveContentProvider2, 0);
                } catch (Exception e2) {
                    providerInfo = null;
                }
                if (providerInfo != null) {
                    break;
                }
            }
        } else {
            providerInfo = resolveContentProvider;
        }
        return providerInfo == null ? true : packageName.equals(providerInfo.packageName);
    }

    public long m775a(String str) {
        return this.f683i.m747a(str);
    }

    public BDLocation m776a(C0441a c0441a, C0451g c0451g, BDLocation bDLocation, C0424b c0424b, C0423a c0423a) {
        int a;
        String str;
        if (c0424b == C0424b.IS_MIX_MODE) {
            a = this.f683i.m746a();
            str = C0459b.m980a().m987d() + "&mixMode=1";
        } else {
            str = C0459b.m980a().m987d();
            a = 0;
        }
        String[] a2 = C0429j.m796a(c0441a, c0451g, bDLocation, str, (c0423a == C0423a.NEED_TO_LOG ? Boolean.valueOf(true) : Boolean.valueOf(false)).booleanValue(), a);
        BDLocation bDLocation2 = null;
        if (a2.length > 0) {
            bDLocation2 = m765a(a2);
            if (bDLocation2 == null || bDLocation2.getLocType() != 67) {
                return bDLocation2;
            }
        }
        return bDLocation2;
    }

    public Context m777b() {
        return f677c;
    }

    File m778c() {
        return this.f679e;
    }

    public boolean m779d() {
        return this.f683i.m755h();
    }

    public boolean m780e() {
        return this.f683i.m756i();
    }

    public boolean m781f() {
        return this.f683i.m757j();
    }

    public boolean m782g() {
        return this.f683i.m758k();
    }

    public boolean m783h() {
        return this.f683i.m760m();
    }

    public void m784i() {
        this.f680f.m807a();
    }

    C0431k m785j() {
        return this.f680f;
    }

    C0436m m786k() {
        return this.f682h;
    }

    C0422g m787l() {
        return this.f683i;
    }

    public void m788m() {
        if (m774r()) {
            this.f681g.m698b();
        }
    }

    public void m789n() {
    }

    public double m790o() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) f677c.getSystemService("connectivity")).getActiveNetworkInfo();
        C0425c c0425c = C0425c.NETWORK_UNKNOWN;
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            if (activeNetworkInfo.getType() == 1) {
                c0425c = C0425c.NETWORK_WIFI;
            }
            if (activeNetworkInfo.getType() == 0) {
                int subtype = activeNetworkInfo.getSubtype();
                if (subtype == 1 || subtype == 2 || subtype == 4 || subtype == 7 || subtype == 11) {
                    c0425c = C0425c.NETWORK_2G;
                } else if (subtype == 3 || subtype == 5 || subtype == 6 || subtype == 8 || subtype == 9 || subtype == 10 || subtype == 12 || subtype == 14 || subtype == 15) {
                    c0425c = C0425c.NETWORK_3G;
                } else if (subtype == 13) {
                    c0425c = C0425c.NETWORK_4G;
                }
            }
        }
        return c0425c == C0425c.NETWORK_UNKNOWN ? this.f683i.m749b() : c0425c == C0425c.NETWORK_WIFI ? this.f683i.m750c() : c0425c == C0425c.NETWORK_2G ? this.f683i.m751d() : c0425c == C0425c.NETWORK_3G ? this.f683i.m752e() : c0425c == C0425c.NETWORK_4G ? this.f683i.m753f() : 0.0d;
    }
}
