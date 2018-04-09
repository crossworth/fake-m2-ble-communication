package p031u.aly;

import android.util.Log;
import com.umeng.analytics.C0919a;
import java.util.Formatter;
import java.util.Locale;

/* compiled from: MLog */
public class bl {
    public static boolean f3714a = false;
    private static String f3715b = C0919a.f3107d;

    private bl() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static void m3575a(Locale locale, String str, Object... objArr) {
        try {
            bl.m3583c(f3715b, new Formatter(locale).format(str, objArr).toString(), null);
        } catch (Throwable th) {
            bl.m3598e(th);
        }
    }

    public static void m3581b(Locale locale, String str, Object... objArr) {
        try {
            bl.m3577b(f3715b, new Formatter(locale).format(str, objArr).toString(), null);
        } catch (Throwable th) {
            bl.m3598e(th);
        }
    }

    public static void m3587c(Locale locale, String str, Object... objArr) {
        try {
            bl.m3595e(f3715b, new Formatter(locale).format(str, objArr).toString(), null);
        } catch (Throwable th) {
            bl.m3598e(th);
        }
    }

    public static void m3593d(Locale locale, String str, Object... objArr) {
        try {
            bl.m3571a(f3715b, new Formatter(locale).format(str, objArr).toString(), null);
        } catch (Throwable th) {
            bl.m3598e(th);
        }
    }

    public static void m3599e(Locale locale, String str, Object... objArr) {
        try {
            bl.m3589d(f3715b, new Formatter(locale).format(str, objArr).toString(), null);
        } catch (Throwable th) {
            bl.m3598e(th);
        }
    }

    public static void m3573a(String str, Object... objArr) {
        try {
            String str2 = "";
            if (str.contains("%")) {
                bl.m3583c(f3715b, new Formatter().format(str, objArr).toString(), null);
                return;
            }
            if (objArr != null) {
                str2 = (String) objArr[0];
            }
            bl.m3583c(str, str2, null);
        } catch (Throwable th) {
            bl.m3598e(th);
        }
    }

    public static void m3579b(String str, Object... objArr) {
        try {
            String str2 = "";
            if (str.contains("%")) {
                bl.m3577b(f3715b, new Formatter().format(str, objArr).toString(), null);
                return;
            }
            if (objArr != null) {
                str2 = (String) objArr[0];
            }
            bl.m3577b(str, str2, null);
        } catch (Throwable th) {
            bl.m3598e(th);
        }
    }

    public static void m3585c(String str, Object... objArr) {
        try {
            String str2 = "";
            if (str.contains("%")) {
                bl.m3595e(f3715b, new Formatter().format(str, objArr).toString(), null);
                return;
            }
            if (objArr != null) {
                str2 = (String) objArr[0];
            }
            bl.m3595e(str, str2, null);
        } catch (Throwable th) {
            bl.m3598e(th);
        }
    }

    public static void m3591d(String str, Object... objArr) {
        try {
            String str2 = "";
            if (str.contains("%")) {
                bl.m3571a(f3715b, new Formatter().format(str, objArr).toString(), null);
                return;
            }
            if (objArr != null) {
                str2 = (String) objArr[0];
            }
            bl.m3571a(str, str2, null);
        } catch (Throwable th) {
            bl.m3598e(th);
        }
    }

    public static void m3597e(String str, Object... objArr) {
        try {
            String str2 = "";
            if (str.contains("%")) {
                bl.m3589d(f3715b, new Formatter().format(str, objArr).toString(), null);
                return;
            }
            if (objArr != null) {
                str2 = (String) objArr[0];
            }
            bl.m3589d(str, str2, null);
        } catch (Throwable th) {
            bl.m3598e(th);
        }
    }

    public static void m3574a(Throwable th) {
        bl.m3583c(f3715b, null, th);
    }

    public static void m3580b(Throwable th) {
        bl.m3571a(f3715b, null, th);
    }

    public static void m3586c(Throwable th) {
        bl.m3589d(f3715b, null, th);
    }

    public static void m3592d(Throwable th) {
        bl.m3577b(f3715b, null, th);
    }

    public static void m3598e(Throwable th) {
        bl.m3595e(f3715b, null, th);
    }

    public static void m3572a(String str, Throwable th) {
        bl.m3583c(f3715b, str, th);
    }

    public static void m3578b(String str, Throwable th) {
        bl.m3571a(f3715b, str, th);
    }

    public static void m3584c(String str, Throwable th) {
        bl.m3589d(f3715b, str, th);
    }

    public static void m3590d(String str, Throwable th) {
        bl.m3577b(f3715b, str, th);
    }

    public static void m3596e(String str, Throwable th) {
        bl.m3595e(f3715b, str, th);
    }

    public static void m3570a(String str) {
        bl.m3571a(f3715b, str, null);
    }

    public static void m3576b(String str) {
        bl.m3577b(f3715b, str, null);
    }

    public static void m3582c(String str) {
        bl.m3583c(f3715b, str, null);
    }

    public static void m3588d(String str) {
        bl.m3589d(f3715b, str, null);
    }

    public static void m3594e(String str) {
        bl.m3595e(f3715b, str, null);
    }

    public static void m3571a(String str, String str2, Throwable th) {
        if (!f3714a) {
            return;
        }
        if (th != null) {
            if (str2 != null) {
                Log.v(str, th.toString() + ":  [" + str2 + "]");
            } else {
                Log.v(str, th.toString());
            }
            for (StackTraceElement stackTraceElement : th.getStackTrace()) {
                Log.v(str, "        at  " + stackTraceElement.toString());
            }
        } else if (str2 == null) {
            try {
                Log.w(str, "the msg is null!");
            } catch (Throwable th2) {
            }
        } else {
            Log.v(str, str2);
        }
    }

    public static void m3577b(String str, String str2, Throwable th) {
        if (!f3714a) {
            return;
        }
        if (th != null) {
            if (str2 != null) {
                Log.d(str, th.toString() + ":  [" + str2 + "]");
            } else {
                Log.d(str, th.toString());
            }
            for (StackTraceElement stackTraceElement : th.getStackTrace()) {
                Log.d(str, "        at  " + stackTraceElement.toString());
            }
        } else if (str2 == null) {
            try {
                Log.w(str, "the msg is null!");
            } catch (Throwable th2) {
            }
        } else {
            Log.d(str, str2);
        }
    }

    public static void m3583c(String str, String str2, Throwable th) {
        if (!f3714a) {
            return;
        }
        if (th != null) {
            if (str2 != null) {
                Log.i(str, th.toString() + ":  [" + str2 + "]");
            } else {
                Log.i(str, th.toString());
            }
            for (StackTraceElement stackTraceElement : th.getStackTrace()) {
                Log.i(str, "        at  " + stackTraceElement.toString());
            }
        } else if (str2 == null) {
            try {
                Log.w(str, "the msg is null!");
            } catch (Throwable th2) {
            }
        } else {
            Log.i(str, str2);
        }
    }

    public static void m3589d(String str, String str2, Throwable th) {
        if (!f3714a) {
            return;
        }
        if (th != null) {
            if (str2 != null) {
                Log.w(str, th.toString() + ":  [" + str2 + "]");
            } else {
                Log.w(str, th.toString());
            }
            for (StackTraceElement stackTraceElement : th.getStackTrace()) {
                Log.w(str, "        at  " + stackTraceElement.toString());
            }
        } else if (str2 == null) {
            try {
                Log.w(str, "the msg is null!");
            } catch (Throwable th2) {
            }
        } else {
            Log.w(str, str2);
        }
    }

    public static void m3595e(String str, String str2, Throwable th) {
        if (!f3714a) {
            return;
        }
        if (th != null) {
            if (str2 != null) {
                Log.e(str, th.toString() + ":  [" + str2 + "]");
            } else {
                Log.e(str, th.toString());
            }
            for (StackTraceElement stackTraceElement : th.getStackTrace()) {
                Log.e(str, "        at  " + stackTraceElement.toString());
            }
        } else if (str2 == null) {
            try {
                Log.w(str, "the msg is null!");
            } catch (Throwable th2) {
            }
        } else {
            Log.e(str, str2);
        }
    }
}
