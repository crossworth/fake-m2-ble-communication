package p031u.aly;

import android.content.Context;
import android.content.SharedPreferences;
import com.umeng.analytics.C0934h;

/* compiled from: StatTracer */
public class as implements aj {
    private static final String f4900h = "successful_request";
    private static final String f4901i = "failed_requests ";
    private static final String f4902j = "last_request_spent_ms";
    private static final String f4903k = "last_request_time";
    private static final String f4904l = "first_activate_time";
    private static final String f4905m = "last_req";
    public int f4906a;
    public int f4907b;
    public long f4908c;
    private final int f4909d = 3600000;
    private int f4910e;
    private long f4911f = 0;
    private long f4912g = 0;
    private Context f4913n;

    public as(Context context) {
        m5087a(context);
    }

    private void m5087a(Context context) {
        this.f4913n = context.getApplicationContext();
        SharedPreferences a = ap.m3451a(context);
        this.f4906a = a.getInt(f4900h, 0);
        this.f4907b = a.getInt(f4901i, 0);
        this.f4910e = a.getInt(f4902j, 0);
        this.f4908c = a.getLong(f4903k, 0);
        this.f4911f = a.getLong(f4905m, 0);
    }

    public int m5093e() {
        return this.f4910e > 3600000 ? 3600000 : this.f4910e;
    }

    public boolean m5094f() {
        boolean z;
        if (this.f4908c == 0) {
            z = true;
        } else {
            z = false;
        }
        boolean z2;
        if (C0934h.m3100a(this.f4913n).m3126i()) {
            z2 = false;
        } else {
            z2 = true;
        }
        if (z && r3) {
            return true;
        }
        return false;
    }

    public void m5095g() {
        this.f4906a++;
        this.f4908c = this.f4911f;
    }

    public void m5096h() {
        this.f4907b++;
    }

    public void m5097i() {
        this.f4911f = System.currentTimeMillis();
    }

    public void m5098j() {
        this.f4910e = (int) (System.currentTimeMillis() - this.f4911f);
    }

    public void m5099k() {
        ap.m3451a(this.f4913n).edit().putInt(f4900h, this.f4906a).putInt(f4901i, this.f4907b).putInt(f4902j, this.f4910e).putLong(f4903k, this.f4908c).putLong(f4905m, this.f4911f).commit();
    }

    public void m5100l() {
        ap.m3451a(this.f4913n).edit().putLong(f4904l, System.currentTimeMillis()).commit();
    }

    public boolean m5101m() {
        if (this.f4912g == 0) {
            this.f4912g = ap.m3451a(this.f4913n).getLong(f4904l, 0);
        }
        return this.f4912g == 0;
    }

    public long m5102n() {
        return m5101m() ? System.currentTimeMillis() : this.f4912g;
    }

    public long m5103o() {
        return this.f4911f;
    }

    public static void m5088a(Context context, av avVar) {
        SharedPreferences a = ap.m3451a(context);
        avVar.f3693a.f3658L = (long) a.getInt(f4901i, 0);
        avVar.f3693a.f3657K = (long) a.getInt(f4900h, 0);
        avVar.f3693a.f3659M = (long) a.getInt(f4902j, 0);
    }

    public void mo2756a() {
        m5097i();
    }

    public void mo2757b() {
        m5098j();
    }

    public void mo2758c() {
        m5095g();
    }

    public void mo2759d() {
        m5096h();
    }
}
