package com.tencent.wxop.stat.p023b;

import android.util.Log;
import com.tencent.wxop.stat.C0894c;
import com.umeng.socialize.common.SocializeConstants;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;

public final class C0877b {
    private String f3021a = SocializeProtocolConstants.PROTOCOL_KEY_DEFAULT;
    private boolean ch = true;
    private int cp = 2;

    public C0877b(String str) {
        this.f3021a = str;
    }

    private String m2849c() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace == null) {
            return null;
        }
        for (StackTraceElement stackTraceElement : stackTrace) {
            if (!stackTraceElement.isNativeMethod() && !stackTraceElement.getClassName().equals(Thread.class.getName()) && !stackTraceElement.getClassName().equals(getClass().getName())) {
                return "[" + Thread.currentThread().getName() + SocializeConstants.OP_OPEN_PAREN + Thread.currentThread().getId() + "): " + stackTraceElement.getFileName() + ":" + stackTraceElement.getLineNumber() + "]";
            }
        }
        return null;
    }

    public final void m2850a(Throwable th) {
        if (this.cp <= 6) {
            Log.e(this.f3021a, "", th);
            C0894c.m2932F();
        }
    }

    public final void ap() {
        this.ch = false;
    }

    public final void m2851b(Object obj) {
        if (this.ch && this.cp <= 4) {
            String c = m2849c();
            Log.i(this.f3021a, c == null ? obj.toString() : c + " - " + obj);
            C0894c.m2932F();
        }
    }

    public final void m2852b(Throwable th) {
        if (this.ch) {
            m2850a(th);
        }
    }

    public final void m2853c(Object obj) {
        if (this.ch) {
            warn(obj);
        }
    }

    public final void m2854d(Object obj) {
        if (this.ch) {
            error(obj);
        }
    }

    public final void debug(Object obj) {
        if (this.cp <= 3) {
            String c = m2849c();
            Log.d(this.f3021a, c == null ? obj.toString() : c + " - " + obj);
            C0894c.m2932F();
        }
    }

    public final void m2855e(Object obj) {
        if (this.ch) {
            debug(obj);
        }
    }

    public final void error(Object obj) {
        if (this.cp <= 6) {
            String c = m2849c();
            Log.e(this.f3021a, c == null ? obj.toString() : c + " - " + obj);
            C0894c.m2932F();
        }
    }

    public final void warn(Object obj) {
        if (this.cp <= 5) {
            String c = m2849c();
            Log.w(this.f3021a, c == null ? obj.toString() : c + " - " + obj);
            C0894c.m2932F();
        }
    }
}
