package com.tencent.connect.p025a;

import android.content.Context;
import com.tencent.connect.auth.QQToken;
import com.tencent.open.utils.OpenConfig;
import java.lang.reflect.Method;

/* compiled from: ProGuard */
public class C1148a {
    private static Class<?> f3511a = null;
    private static Class<?> f3512b = null;
    private static Method f3513c = null;
    private static Method f3514d = null;
    private static Method f3515e = null;
    private static Method f3516f = null;
    private static boolean f3517g = false;

    public static boolean m3345a(Context context, QQToken qQToken) {
        return OpenConfig.getInstance(context, qQToken.getAppId()).getBoolean("Common_ta_enable");
    }

    public static void m3346b(Context context, QQToken qQToken) {
        try {
            if (C1148a.m3345a(context, qQToken)) {
                f3516f.invoke(f3511a, new Object[]{Boolean.valueOf(true)});
                return;
            }
            f3516f.invoke(f3511a, new Object[]{Boolean.valueOf(false)});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void m3347c(Context context, QQToken qQToken) {
        String str = "Aqc" + qQToken.getAppId();
        try {
            f3511a = Class.forName("com.tencent.stat.StatConfig");
            f3512b = Class.forName("com.tencent.stat.StatService");
            f3513c = f3512b.getMethod("reportQQ", new Class[]{Context.class, String.class});
            f3514d = f3512b.getMethod("trackCustomEvent", new Class[]{Context.class, String.class, String[].class});
            f3515e = f3512b.getMethod("commitEvents", new Class[]{Context.class, Integer.TYPE});
            f3516f = f3511a.getMethod("setEnableStatService", new Class[]{Boolean.TYPE});
            C1148a.m3346b(context, qQToken);
            f3511a.getMethod("setAutoExceptionCaught", new Class[]{Boolean.TYPE}).invoke(f3511a, new Object[]{Boolean.valueOf(false)});
            f3511a.getMethod("setEnableSmartReporting", new Class[]{Boolean.TYPE}).invoke(f3511a, new Object[]{Boolean.valueOf(true)});
            f3511a.getMethod("setSendPeriodMinutes", new Class[]{Integer.TYPE}).invoke(f3511a, new Object[]{Integer.valueOf(1440)});
            Class cls = Class.forName("com.tencent.stat.StatReportStrategy");
            f3511a.getMethod("setStatSendStrategy", new Class[]{cls}).invoke(f3511a, new Object[]{cls.getField("PERIOD").get(null)});
            f3512b.getMethod("startStatService", new Class[]{Context.class, String.class, String.class}).invoke(f3512b, new Object[]{context, str, Class.forName("com.tencent.stat.common.StatConstants").getField("VERSION").get(null)});
            f3517g = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void m3348d(Context context, QQToken qQToken) {
        if (f3517g) {
            C1148a.m3346b(context, qQToken);
            if (qQToken.getOpenId() != null) {
                try {
                    f3513c.invoke(f3512b, new Object[]{context, qQToken.getOpenId()});
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void m3344a(Context context, QQToken qQToken, String str, String... strArr) {
        if (f3517g) {
            C1148a.m3346b(context, qQToken);
            try {
                f3514d.invoke(f3512b, new Object[]{context, str, strArr});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
