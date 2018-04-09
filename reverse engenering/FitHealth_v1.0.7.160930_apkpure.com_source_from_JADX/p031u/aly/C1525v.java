package p031u.aly;

import android.content.Context;
import android.text.TextUtils;
import com.zhuoyi.system.util.constant.SeparatorConstants;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* compiled from: IdTracker */
public class C1525v {
    public static C1525v f3895a;
    private final String f3896b = "umeng_it.cache";
    private File f3897c;
    private bc f3898d = null;
    private long f3899e;
    private long f3900f;
    private Set<C1522r> f3901g = new HashSet();
    private C1524a f3902h = null;

    /* compiled from: IdTracker */
    public static class C1524a {
        private Context f3893a;
        private Set<String> f3894b = new HashSet();

        public C1524a(Context context) {
            this.f3893a = context;
        }

        public boolean m3909a(String str) {
            return !this.f3894b.contains(str);
        }

        public void m3911b(String str) {
            this.f3894b.add(str);
        }

        public void m3912c(String str) {
            this.f3894b.remove(str);
        }

        public void m3908a() {
            if (!this.f3894b.isEmpty()) {
                StringBuilder stringBuilder = new StringBuilder();
                for (String append : this.f3894b) {
                    stringBuilder.append(append);
                    stringBuilder.append(',');
                }
                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                ap.m3451a(this.f3893a).edit().putString("invld_id", stringBuilder.toString()).commit();
            }
        }

        public void m3910b() {
            Object string = ap.m3451a(this.f3893a).getString("invld_id", null);
            if (!TextUtils.isEmpty(string)) {
                String[] split = string.split(SeparatorConstants.SEPARATOR_ADS_ID);
                if (split != null) {
                    for (CharSequence charSequence : split) {
                        if (!TextUtils.isEmpty(charSequence)) {
                            this.f3894b.add(charSequence);
                        }
                    }
                }
            }
        }
    }

    C1525v(Context context) {
        this.f3897c = new File(context.getFilesDir(), "umeng_it.cache");
        this.f3900f = 86400000;
        this.f3902h = new C1524a(context);
        this.f3902h.m3910b();
    }

    public static synchronized C1525v m3913a(Context context) {
        C1525v c1525v;
        synchronized (C1525v.class) {
            if (f3895a == null) {
                f3895a = new C1525v(context);
                f3895a.m3919a(new C1954w(context));
                f3895a.m3919a(new C1952s(context));
                f3895a.m3919a(new ab(context));
                f3895a.m3919a(new aa(context));
                f3895a.m3919a(new C1953u(context));
                f3895a.m3919a(new C1955y(context));
                f3895a.m3919a(new C1956z());
                f3895a.m3919a(new ac(context));
                f3895a.m3923e();
            }
            c1525v = f3895a;
        }
        return c1525v;
    }

    public boolean m3919a(C1522r c1522r) {
        if (this.f3902h.m3909a(c1522r.m3890b())) {
            return this.f3901g.add(c1522r);
        }
        return false;
    }

    public void m3918a(long j) {
        this.f3900f = j;
    }

    public void m3917a() {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - this.f3899e >= this.f3900f) {
            Object obj = null;
            for (C1522r c1522r : this.f3901g) {
                if (c1522r.m3891c()) {
                    if (c1522r.m3889a()) {
                        obj = 1;
                        if (!c1522r.m3891c()) {
                            this.f3902h.m3911b(c1522r.m3890b());
                        }
                    }
                    obj = obj;
                }
            }
            if (obj != null) {
                m3915g();
                this.f3902h.m3908a();
                m3924f();
            }
            this.f3899e = currentTimeMillis;
        }
    }

    public bc m3920b() {
        return this.f3898d;
    }

    private void m3915g() {
        bc bcVar = new bc();
        Map hashMap = new HashMap();
        List arrayList = new ArrayList();
        for (C1522r c1522r : this.f3901g) {
            if (c1522r.m3891c()) {
                if (c1522r.m3892d() != null) {
                    hashMap.put(c1522r.m3890b(), c1522r.m3892d());
                }
                if (!(c1522r.m3893e() == null || c1522r.m3893e().isEmpty())) {
                    arrayList.addAll(c1522r.m3893e());
                }
            }
        }
        bcVar.m5234a(arrayList);
        bcVar.m5235a(hashMap);
        synchronized (this) {
            this.f3898d = bcVar;
        }
    }

    public String m3921c() {
        return null;
    }

    public void m3922d() {
        boolean z = false;
        for (C1522r c1522r : this.f3901g) {
            if (c1522r.m3891c()) {
                boolean z2;
                if (c1522r.m3893e() == null || c1522r.m3893e().isEmpty()) {
                    z2 = z;
                } else {
                    c1522r.m3886a(null);
                    z2 = true;
                }
                z = z2;
            }
        }
        if (z) {
            this.f3898d.m5243b(false);
            m3924f();
        }
    }

    public void m3923e() {
        bc h = m3916h();
        if (h != null) {
            List<C1522r> arrayList = new ArrayList(this.f3901g.size());
            synchronized (this) {
                this.f3898d = h;
                for (C1522r c1522r : this.f3901g) {
                    c1522r.m3888a(this.f3898d);
                    if (!c1522r.m3891c()) {
                        arrayList.add(c1522r);
                    }
                }
                for (C1522r c1522r2 : arrayList) {
                    this.f3901g.remove(c1522r2);
                }
            }
            m3915g();
        }
    }

    public void m3924f() {
        if (this.f3898d != null) {
            m3914a(this.f3898d);
        }
    }

    private bc m3916h() {
        InputStream fileInputStream;
        Exception e;
        Throwable th;
        if (!this.f3897c.exists()) {
            return null;
        }
        try {
            fileInputStream = new FileInputStream(this.f3897c);
            try {
                byte[] b = bk.m3565b(fileInputStream);
                bp bcVar = new bc();
                new bs().m3654a(bcVar, b);
                bk.m3567c(fileInputStream);
                return bcVar;
            } catch (Exception e2) {
                e = e2;
                try {
                    e.printStackTrace();
                    bk.m3567c(fileInputStream);
                    return null;
                } catch (Throwable th2) {
                    th = th2;
                    bk.m3567c(fileInputStream);
                    throw th;
                }
            }
        } catch (Exception e3) {
            e = e3;
            fileInputStream = null;
            e.printStackTrace();
            bk.m3567c(fileInputStream);
            return null;
        } catch (Throwable th3) {
            fileInputStream = null;
            th = th3;
            bk.m3567c(fileInputStream);
            throw th;
        }
    }

    private void m3914a(bc bcVar) {
        if (bcVar != null) {
            try {
                byte[] a;
                synchronized (this) {
                    a = new by().m3669a(bcVar);
                }
                if (a != null) {
                    bk.m3561a(this.f3897c, a);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
