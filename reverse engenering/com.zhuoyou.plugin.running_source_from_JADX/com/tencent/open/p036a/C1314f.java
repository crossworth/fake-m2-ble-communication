package com.tencent.open.p036a;

import android.os.Environment;
import android.text.TextUtils;
import com.tencent.connect.common.Constants;
import com.tencent.open.p036a.C1312d.C1308a;
import com.tencent.open.p036a.C1312d.C1309b;
import com.tencent.open.p036a.C1312d.C1310c;
import com.tencent.open.utils.Global;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.io.File;

/* compiled from: ProGuard */
public class C1314f {
    public static C1314f f4124a = null;
    protected static final C1306b f4125c = new C1306b(C1314f.m3869c(), C1307c.f4118m, C1307c.f4112g, C1307c.f4113h, C1307c.f4108c, (long) C1307c.f4114i, 10, C1307c.f4110e, C1307c.f4119n);
    private static boolean f4126d = false;
    protected C1305a f4127b = new C1305a(f4125c);

    public static C1314f m3863a() {
        if (f4124a == null) {
            synchronized (C1314f.class) {
                if (f4124a == null) {
                    f4124a = new C1314f();
                    f4126d = true;
                }
            }
        }
        return f4124a;
    }

    private C1314f() {
    }

    protected void m3873a(int i, String str, String str2, Throwable th) {
        if (f4126d) {
            Object packageName = Global.getPackageName();
            if (TextUtils.isEmpty(packageName)) {
                String str3 = SocializeProtocolConstants.PROTOCOL_KEY_DEFAULT;
            } else {
                String str4 = packageName + " SDK_VERSION:" + Constants.SDK_VERSION;
                if (this.f4127b != null) {
                    C1313e.f4123a.m3820b(32, Thread.currentThread(), System.currentTimeMillis(), "openSDK_LOG", str4, null);
                    this.f4127b.m3820b(32, Thread.currentThread(), System.currentTimeMillis(), "openSDK_LOG", str4, null);
                    f4126d = false;
                } else {
                    return;
                }
            }
        }
        C1313e.f4123a.m3820b(i, Thread.currentThread(), System.currentTimeMillis(), str, str2, th);
        if (C1308a.m3851a(C1307c.f4107b, i) && this.f4127b != null) {
            this.f4127b.m3820b(i, Thread.currentThread(), System.currentTimeMillis(), str, str2, th);
        }
    }

    public static final void m3864a(String str, String str2) {
        C1314f.m3863a().m3873a(1, str, str2, null);
    }

    public static final void m3867b(String str, String str2) {
        C1314f.m3863a().m3873a(2, str, str2, null);
    }

    public static final void m3865a(String str, String str2, Throwable th) {
        C1314f.m3863a().m3873a(2, str, str2, th);
    }

    public static final void m3870c(String str, String str2) {
        C1314f.m3863a().m3873a(4, str, str2, null);
    }

    public static final void m3871d(String str, String str2) {
        C1314f.m3863a().m3873a(8, str, str2, null);
    }

    public static final void m3872e(String str, String str2) {
        C1314f.m3863a().m3873a(16, str, str2, null);
    }

    public static final void m3868b(String str, String str2, Throwable th) {
        C1314f.m3863a().m3873a(16, str, str2, th);
    }

    public static void m3866b() {
        synchronized (C1314f.class) {
            C1314f.m3863a().m3874d();
            if (f4124a != null) {
                f4124a = null;
            }
        }
    }

    protected static File m3869c() {
        Object obj = null;
        String str = C1307c.f4109d;
        C1310c b = C1309b.m3853b();
        if (b != null && b.m3860c() > C1307c.f4111f) {
            obj = 1;
        }
        if (obj != null) {
            return new File(Environment.getExternalStorageDirectory(), str);
        }
        return new File(Global.getFilesDir(), str);
    }

    protected void m3874d() {
        if (this.f4127b != null) {
            this.f4127b.m3827a();
            this.f4127b.m3831b();
            this.f4127b = null;
        }
    }
}
