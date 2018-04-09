package com.tencent.wxop.stat.common;

import android.util.Log;
import com.tencent.wxop.stat.C1453g;
import com.tencent.wxop.stat.StatConfig;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;

public final class StatLogger {
    private String f4733a = SocializeProtocolConstants.PROTOCOL_KEY_DEFAULT;
    private boolean f4734b = true;
    private int f4735c = 2;

    public StatLogger(String str) {
        this.f4733a = str;
    }

    private String m4372a() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace == null) {
            return null;
        }
        for (StackTraceElement stackTraceElement : stackTrace) {
            if (!stackTraceElement.isNativeMethod() && !stackTraceElement.getClassName().equals(Thread.class.getName()) && !stackTraceElement.getClassName().equals(getClass().getName())) {
                return "[" + Thread.currentThread().getName() + "(" + Thread.currentThread().getId() + "): " + stackTraceElement.getFileName() + ":" + stackTraceElement.getLineNumber() + "]";
            }
        }
        return null;
    }

    public final void m4373d(Object obj) {
        if (isDebugEnable()) {
            debug(obj);
        }
    }

    public final void debug(Object obj) {
        if (this.f4735c <= 3) {
            String a = m4372a();
            a = a == null ? obj.toString() : a + " - " + obj;
            Log.d(this.f4733a, a);
            C1453g customLogger = StatConfig.getCustomLogger();
            if (customLogger != null) {
                customLogger.m4482e(a);
            }
        }
    }

    public final void m4374e(Object obj) {
        if (isDebugEnable()) {
            error(obj);
        }
    }

    public final void m4375e(Throwable th) {
        if (isDebugEnable()) {
            error(th);
        }
    }

    public final void error(Object obj) {
        if (this.f4735c <= 6) {
            String a = m4372a();
            a = a == null ? obj.toString() : a + " - " + obj;
            Log.e(this.f4733a, a);
            C1453g customLogger = StatConfig.getCustomLogger();
            if (customLogger != null) {
                customLogger.m4481d(a);
            }
        }
    }

    public final void error(Throwable th) {
        if (this.f4735c <= 6) {
            Log.e(this.f4733a, "", th);
            C1453g customLogger = StatConfig.getCustomLogger();
            if (customLogger != null) {
                customLogger.m4481d(th);
            }
        }
    }

    public final int getLogLevel() {
        return this.f4735c;
    }

    public final void m4376i(Object obj) {
        if (isDebugEnable()) {
            info(obj);
        }
    }

    public final void info(Object obj) {
        if (this.f4735c <= 4) {
            String a = m4372a();
            a = a == null ? obj.toString() : a + " - " + obj;
            Log.i(this.f4733a, a);
            C1453g customLogger = StatConfig.getCustomLogger();
            if (customLogger != null) {
                customLogger.m4478a(a);
            }
        }
    }

    public final boolean isDebugEnable() {
        return this.f4734b;
    }

    public final void setDebugEnable(boolean z) {
        this.f4734b = z;
    }

    public final void setLogLevel(int i) {
        this.f4735c = i;
    }

    public final void setTag(String str) {
        this.f4733a = str;
    }

    public final void m4377v(Object obj) {
        if (isDebugEnable()) {
            verbose(obj);
        }
    }

    public final void verbose(Object obj) {
        if (this.f4735c <= 2) {
            String a = m4372a();
            a = a == null ? obj.toString() : a + " - " + obj;
            Log.v(this.f4733a, a);
            C1453g customLogger = StatConfig.getCustomLogger();
            if (customLogger != null) {
                customLogger.m4479b(a);
            }
        }
    }

    public final void m4378w(Object obj) {
        if (isDebugEnable()) {
            warn(obj);
        }
    }

    public final void warn(Object obj) {
        if (this.f4735c <= 5) {
            String a = m4372a();
            a = a == null ? obj.toString() : a + " - " + obj;
            Log.w(this.f4733a, a);
            C1453g customLogger = StatConfig.getCustomLogger();
            if (customLogger != null) {
                customLogger.m4480c(a);
            }
        }
    }
}
