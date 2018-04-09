package p031u.aly;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.C0919a;
import java.lang.reflect.Method;
import p031u.aly.av.C1923o;

/* compiled from: SessionTracker */
public class ar {
    private static final String f3533a = "session_start_time";
    private static final String f3534b = "session_end_time";
    private static final String f3535c = "session_id";
    private static final String f3536f = "activities";
    private static final String f3537g = "uptr";
    private static final String f3538h = "dntr";
    private static String f3539i = null;
    private static Context f3540j = null;
    private final String f3541d = "a_start_time";
    private final String f3542e = "a_end_time";

    public C1923o m3470a(Context context) {
        SharedPreferences a = ap.m3451a(context);
        String string = a.getString("session_id", null);
        if (string == null) {
            return null;
        }
        long j = a.getLong(f3533a, 0);
        long j2 = a.getLong(f3534b, 0);
        long j3 = 0;
        if (j2 != 0) {
            j3 = j2 - j;
            if (Math.abs(j3) > 86400000) {
                j3 = 0;
            }
        }
        C1923o c1923o = new C1923o();
        c1923o.f4928b = string;
        c1923o.f4929c = j;
        c1923o.f4930d = j2;
        c1923o.f4931e = j3;
        double[] location = AnalyticsConfig.getLocation();
        if (location != null) {
            c1923o.f4935j.f3627a = location[0];
            c1923o.f4935j.f3628b = location[1];
            c1923o.f4935j.f3629c = System.currentTimeMillis();
        }
        try {
            Class cls = Class.forName("android.net.TrafficStats");
            Method method = cls.getMethod("getUidRxBytes", new Class[]{Integer.TYPE});
            Method method2 = cls.getMethod("getUidTxBytes", new Class[]{Integer.TYPE});
            if (context.getApplicationInfo().uid == -1) {
                return null;
            }
            j = ((Long) method.invoke(null, new Object[]{Integer.valueOf(context.getApplicationInfo().uid)})).longValue();
            j3 = ((Long) method2.invoke(null, new Object[]{Integer.valueOf(r5)})).longValue();
            if (j > 0 && j3 > 0) {
                long j4 = a.getLong(f3537g, -1);
                j2 = a.getLong(f3538h, -1);
                a.edit().putLong(f3537g, j3).putLong(f3538h, j).commit();
                if (j4 > 0 && j2 > 0) {
                    j -= j2;
                    j3 -= j4;
                    if (j > 0 && j3 > 0) {
                        c1923o.f4934i.f3689a = j;
                        c1923o.f4934i.f3690b = j3;
                    }
                }
            }
            at.m3476a(a, c1923o);
            m3467a(a);
            return c1923o;
        } catch (Throwable th) {
        }
    }

    private void m3467a(SharedPreferences sharedPreferences) {
        Editor edit = sharedPreferences.edit();
        edit.remove(f3533a);
        edit.remove(f3534b);
        edit.remove("a_start_time");
        edit.remove("a_end_time");
        edit.putString(f3536f, "");
        edit.commit();
    }

    public String m3471b(Context context) {
        String f = bj.m3531f(context);
        String appkey = AnalyticsConfig.getAppkey(context);
        long currentTimeMillis = System.currentTimeMillis();
        if (appkey == null) {
            throw new RuntimeException("Appkey is null or empty, Please check AndroidManifest.xml");
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(currentTimeMillis).append(appkey).append(f);
        f3539i = bk.m3557a(stringBuilder.toString());
        return f3539i;
    }

    public void m3472c(Context context) {
        f3540j = context;
        SharedPreferences a = ap.m3451a(context);
        if (a != null) {
            Editor edit = a.edit();
            int i = a.getInt(C0919a.f3128y, 0);
            int parseInt = Integer.parseInt(bj.m3526c(f3540j));
            if (i != 0 && parseInt != i) {
                if (ar.m3469g(context) == null) {
                    m3466a(context, a);
                }
                m3474e(f3540j);
                ae.m5075a(f3540j).mo2752c();
                m3475f(f3540j);
            } else if (m3468b(a)) {
                bl.m3582c("Start new session: " + m3466a(context, a));
            } else {
                String string = a.getString("session_id", null);
                edit.putLong("a_start_time", System.currentTimeMillis());
                edit.putLong("a_end_time", 0);
                edit.commit();
                bl.m3582c("Extend current session: " + string);
            }
        }
    }

    public void m3473d(Context context) {
        SharedPreferences a = ap.m3451a(context);
        if (a != null) {
            if (a.getLong("a_start_time", 0) == 0 && AnalyticsConfig.ACTIVITY_DURATION_OPEN) {
                bl.m3594e("onPause called before onResume");
                return;
            }
            long currentTimeMillis = System.currentTimeMillis();
            Editor edit = a.edit();
            edit.putLong("a_start_time", 0);
            edit.putLong("a_end_time", currentTimeMillis);
            edit.putLong(f3534b, currentTimeMillis);
            edit.commit();
        }
    }

    private boolean m3468b(SharedPreferences sharedPreferences) {
        long j = sharedPreferences.getLong("a_start_time", 0);
        long j2 = sharedPreferences.getLong("a_end_time", 0);
        long currentTimeMillis = System.currentTimeMillis();
        if (j != 0 && currentTimeMillis - j < AnalyticsConfig.kContinueSessionMillis) {
            bl.m3594e("onResume called before onPause");
            return false;
        } else if (currentTimeMillis - j2 > AnalyticsConfig.kContinueSessionMillis) {
            return true;
        } else {
            return false;
        }
    }

    private String m3466a(Context context, SharedPreferences sharedPreferences) {
        ae a = ae.m5075a(context);
        String b = m3471b(context);
        ai a2 = m3470a(context);
        Editor edit = sharedPreferences.edit();
        edit.putString("session_id", b);
        edit.putLong(f3533a, System.currentTimeMillis());
        edit.putLong(f3534b, 0);
        edit.putLong("a_start_time", System.currentTimeMillis());
        edit.putLong("a_end_time", 0);
        edit.putInt(C0919a.f3128y, Integer.parseInt(bj.m3526c(context)));
        edit.putString(C0919a.f3129z, bj.m3528d(context));
        edit.commit();
        if (a2 != null) {
            a.mo2748a(a2);
        } else {
            a.mo2748a((C1923o) null);
        }
        return b;
    }

    public boolean m3474e(Context context) {
        boolean z = false;
        SharedPreferences a = ap.m3451a(context);
        if (!(a == null || a.getString("session_id", null) == null)) {
            long j = a.getLong("a_start_time", 0);
            long j2 = a.getLong("a_end_time", 0);
            if (j > 0 && j2 == 0) {
                z = true;
                m3473d(context);
            }
            ae a2 = ae.m5075a(context);
            ai a3 = m3470a(context);
            if (a3 != null) {
                a2.mo2751b(a3);
            }
        }
        return z;
    }

    public void m3475f(Context context) {
        SharedPreferences a = ap.m3451a(context);
        if (a != null) {
            String b = m3471b(context);
            Editor edit = a.edit();
            edit.putString("session_id", b);
            edit.putLong(f3533a, System.currentTimeMillis());
            edit.putLong(f3534b, 0);
            edit.putLong("a_start_time", System.currentTimeMillis());
            edit.putLong("a_end_time", 0);
            edit.putInt(C0919a.f3128y, Integer.parseInt(bj.m3526c(context)));
            edit.putString(C0919a.f3129z, bj.m3528d(context));
            edit.commit();
            bl.m3582c("Restart session: " + b);
        }
    }

    public static String m3469g(Context context) {
        if (f3539i == null) {
            f3539i = ap.m3451a(context).getString("session_id", null);
        }
        return f3539i;
    }

    public static String m3465a() {
        try {
            if (f3539i == null) {
                f3539i = ap.m3451a(f3540j).getString("session_id", null);
            }
        } catch (Exception e) {
        }
        return f3539i;
    }
}
