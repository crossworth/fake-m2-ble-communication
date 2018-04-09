package p031u.aly;

import android.content.Context;
import android.text.TextUtils;
import com.amap.api.services.core.AMapException;
import com.umeng.analytics.C0920b;
import com.umeng.analytics.ReportPolicy;
import java.io.File;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

/* compiled from: ImprintHandler */
public class C1527x {
    private static final String f3916a = ".imprint";
    private static final byte[] f3917b = "pbl0".getBytes();
    private static C1527x f3918f;
    private ao f3919c;
    private C1526a f3920d = new C1526a();
    private bd f3921e = null;
    private Context f3922g;

    /* compiled from: ImprintHandler */
    public static class C1526a {
        private int f3903a = -1;
        private int f3904b = -1;
        private int f3905c = -1;
        private int f3906d = -1;
        private int f3907e = -1;
        private String f3908f = null;
        private int f3909g = -1;
        private String f3910h = null;
        private int f3911i = -1;
        private int f3912j = -1;
        private String f3913k = null;
        private String f3914l = null;
        private String f3915m = null;

        C1526a() {
        }

        C1526a(bd bdVar) {
            m3930a(bdVar);
        }

        public void m3930a(bd bdVar) {
            if (bdVar != null) {
                this.f3903a = m3925a(bdVar, "defcon");
                this.f3904b = m3925a(bdVar, au.ah);
                this.f3905c = m3925a(bdVar, "codex");
                this.f3906d = m3925a(bdVar, "report_policy");
                this.f3907e = m3925a(bdVar, "report_interval");
                this.f3908f = m3926b(bdVar, "client_test");
                this.f3909g = m3925a(bdVar, "test_report_interval");
                this.f3910h = m3926b(bdVar, "umid");
                this.f3911i = m3925a(bdVar, "integrated_test");
                this.f3912j = m3925a(bdVar, "latent_hours");
                this.f3913k = m3926b(bdVar, "country");
                this.f3914l = m3926b(bdVar, "domain_p");
                this.f3915m = m3926b(bdVar, "domain_s");
            }
        }

        public String m3929a(String str) {
            if (this.f3915m != null) {
                return this.f3915m;
            }
            return str;
        }

        public String m3934b(String str) {
            if (this.f3914l != null) {
                return this.f3914l;
            }
            return str;
        }

        public String m3937c(String str) {
            if (this.f3913k != null) {
                return this.f3913k;
            }
            return str;
        }

        public int m3927a(int i) {
            return (this.f3903a != -1 && this.f3903a <= 3 && this.f3903a >= 0) ? this.f3903a : i;
        }

        public int m3933b(int i) {
            return (this.f3904b != -1 && this.f3904b >= 0 && this.f3904b <= AMapException.CODE_AMAP_CLIENT_ERRORCODE_MISSSING) ? this.f3904b * 1000 : i;
        }

        public int m3936c(int i) {
            if (this.f3905c == 0 || this.f3905c == 1 || this.f3905c == -1) {
                return this.f3905c;
            }
            return i;
        }

        public int[] m3932a(int i, int i2) {
            if (this.f3906d == -1 || !ReportPolicy.m3060a(this.f3906d)) {
                return new int[]{i, i2};
            }
            if (this.f3907e == -1 || this.f3907e < 90 || this.f3907e > 86400) {
                this.f3907e = 90;
            }
            return new int[]{this.f3906d, this.f3907e * 1000};
        }

        public String m3939d(String str) {
            return (this.f3908f == null || !aw.m5108a(this.f3908f)) ? str : this.f3908f;
        }

        public int m3938d(int i) {
            return (this.f3909g == -1 || this.f3909g < 90 || this.f3909g > 86400) ? i : this.f3909g * 1000;
        }

        public boolean m3931a() {
            return this.f3909g != -1;
        }

        public String m3940e(String str) {
            return this.f3910h;
        }

        public boolean m3935b() {
            return this.f3911i == 1;
        }

        public long m3928a(long j) {
            return (this.f3912j != -1 && this.f3912j >= 48) ? 3600000 * ((long) this.f3912j) : j;
        }

        private int m3925a(bd bdVar, String str) {
            if (bdVar == null || !bdVar.m5290f()) {
                return -1;
            }
            be beVar = (be) bdVar.m5288d().get(str);
            if (beVar == null || TextUtils.isEmpty(beVar.m5325c())) {
                return -1;
            }
            try {
                return Integer.parseInt(beVar.m5325c().trim());
            } catch (Exception e) {
                return -1;
            }
        }

        private String m3926b(bd bdVar, String str) {
            if (bdVar == null || !bdVar.m5290f()) {
                return null;
            }
            be beVar = (be) bdVar.m5288d().get(str);
            if (beVar == null || TextUtils.isEmpty(beVar.m5325c())) {
                return null;
            }
            return beVar.m5325c();
        }
    }

    C1527x(Context context) {
        this.f3922g = context;
    }

    public static synchronized C1527x m3942a(Context context) {
        C1527x c1527x;
        synchronized (C1527x.class) {
            if (f3918f == null) {
                f3918f = new C1527x(context);
                f3918f.m3952c();
            }
            c1527x = f3918f;
        }
        return c1527x;
    }

    public void m3948a(ao aoVar) {
        this.f3919c = aoVar;
    }

    public String m3946a(bd bdVar) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Entry entry : new TreeMap(bdVar.m5288d()).entrySet()) {
            stringBuilder.append((String) entry.getKey());
            if (((be) entry.getValue()).m5328e()) {
                stringBuilder.append(((be) entry.getValue()).m5325c());
            }
            stringBuilder.append(((be) entry.getValue()).m5329f());
            stringBuilder.append(((be) entry.getValue()).m5332i());
        }
        stringBuilder.append(bdVar.f5044b);
        return bk.m3557a(stringBuilder.toString()).toLowerCase(Locale.US);
    }

    private boolean m3944c(bd bdVar) {
        if (!bdVar.m5294j().equals(m3946a(bdVar))) {
            return false;
        }
        for (be beVar : bdVar.m5288d().values()) {
            byte[] a = C0920b.m3065a(beVar.m5332i());
            byte[] a2 = m3949a(beVar);
            for (int i = 0; i < 4; i++) {
                if (a[i] != a2[i]) {
                    return false;
                }
            }
        }
        return true;
    }

    public byte[] m3949a(be beVar) {
        ByteBuffer allocate = ByteBuffer.allocate(8);
        allocate.order(null);
        allocate.putLong(beVar.m5329f());
        byte[] array = allocate.array();
        byte[] bArr = f3917b;
        byte[] bArr2 = new byte[4];
        for (int i = 0; i < 4; i++) {
            bArr2[i] = (byte) (array[i] ^ bArr[i]);
        }
        return bArr2;
    }

    public void m3951b(bd bdVar) {
        String str = null;
        if (bdVar != null && m3944c(bdVar)) {
            Object obj = null;
            synchronized (this) {
                bd d;
                bd bdVar2 = this.f3921e;
                String j = bdVar2 == null ? null : bdVar2.m5294j();
                if (bdVar2 == null) {
                    d = m3945d(bdVar);
                } else {
                    d = m3941a(bdVar2, bdVar);
                }
                this.f3921e = d;
                if (d != null) {
                    str = d.m5294j();
                }
                if (!m3943a(j, str)) {
                    obj = 1;
                }
            }
            if (this.f3921e != null && r0 != null) {
                this.f3920d.m3930a(this.f3921e);
                if (this.f3919c != null) {
                    this.f3919c.mo2749a(this.f3920d);
                }
            }
        }
    }

    private boolean m3943a(String str, String str2) {
        if (str != null) {
            return str.equals(str2);
        }
        if (str2 != null) {
            return false;
        }
        return true;
    }

    private bd m3941a(bd bdVar, bd bdVar2) {
        if (bdVar2 != null) {
            Map d = bdVar.m5288d();
            for (Entry entry : bdVar2.m5288d().entrySet()) {
                if (((be) entry.getValue()).m5328e()) {
                    d.put(entry.getKey(), entry.getValue());
                } else {
                    d.remove(entry.getKey());
                }
            }
            bdVar.m5275a(bdVar2.m5291g());
            bdVar.m5276a(m3946a(bdVar));
        }
        return bdVar;
    }

    private bd m3945d(bd bdVar) {
        Map d = bdVar.m5288d();
        List<String> arrayList = new ArrayList(d.size() / 2);
        for (Entry entry : d.entrySet()) {
            if (!((be) entry.getValue()).m5328e()) {
                arrayList.add(entry.getKey());
            }
        }
        for (String remove : arrayList) {
            d.remove(remove);
        }
        return bdVar;
    }

    public synchronized bd m3947a() {
        return this.f3921e;
    }

    public C1526a m3950b() {
        return this.f3920d;
    }

    public void m3952c() {
        InputStream openFileInput;
        byte[] b;
        Exception e;
        bd bdVar;
        Throwable th;
        InputStream inputStream = null;
        if (new File(this.f3922g.getFilesDir(), f3916a).exists()) {
            try {
                openFileInput = this.f3922g.openFileInput(f3916a);
                try {
                    b = bk.m3565b(openFileInput);
                    bk.m3567c(openFileInput);
                } catch (Exception e2) {
                    e = e2;
                    try {
                        e.printStackTrace();
                        bk.m3567c(openFileInput);
                        if (b == null) {
                            try {
                                bdVar = new bd();
                                new bs().m3654a((bp) bdVar, b);
                                this.f3921e = bdVar;
                                this.f3920d.m3930a(bdVar);
                            } catch (Exception e3) {
                                e3.printStackTrace();
                                return;
                            }
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        inputStream = openFileInput;
                        bk.m3567c(inputStream);
                        throw th;
                    }
                }
            } catch (Exception e4) {
                e3 = e4;
                openFileInput = inputStream;
                e3.printStackTrace();
                bk.m3567c(openFileInput);
                if (b == null) {
                    bdVar = new bd();
                    new bs().m3654a((bp) bdVar, b);
                    this.f3921e = bdVar;
                    this.f3920d.m3930a(bdVar);
                }
            } catch (Throwable th3) {
                th = th3;
                bk.m3567c(inputStream);
                throw th;
            }
            if (b == null) {
                bdVar = new bd();
                new bs().m3654a((bp) bdVar, b);
                this.f3921e = bdVar;
                this.f3920d.m3930a(bdVar);
            }
        }
    }

    public void m3953d() {
        if (this.f3921e != null) {
            try {
                bk.m3561a(new File(this.f3922g.getFilesDir(), f3916a), new by().m3669a(this.f3921e));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean m3954e() {
        return new File(this.f3922g.getFilesDir(), f3916a).delete();
    }
}
