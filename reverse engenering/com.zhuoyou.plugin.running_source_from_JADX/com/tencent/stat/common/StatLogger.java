package com.tencent.stat.common;

import android.util.Log;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;

public final class StatLogger {
    private String f4392a = SocializeProtocolConstants.PROTOCOL_KEY_DEFAULT;
    private boolean f4393b = true;
    private int f4394c = 2;

    public StatLogger(String str) {
        this.f4392a = str;
    }

    private String m4082a() {
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

    public void m4083d(Object obj) {
        if (isDebugEnable()) {
            debug(obj);
        }
    }

    public void debug(Object obj) {
        if (this.f4394c <= 3) {
            String a = m4082a();
            Log.d(this.f4392a, a == null ? obj.toString() : a + " - " + obj);
        }
    }

    public void m4084e(Exception exception) {
        if (isDebugEnable()) {
            error(exception);
        }
    }

    public void m4085e(Object obj) {
        if (isDebugEnable()) {
            error(obj);
        }
    }

    public void error(Exception exception) {
        if (this.f4394c <= 6) {
            StringBuffer stringBuffer = new StringBuffer();
            String a = m4082a();
            StackTraceElement[] stackTrace = exception.getStackTrace();
            if (a != null) {
                stringBuffer.append(a + " - " + exception + "\r\n");
            } else {
                stringBuffer.append(exception + "\r\n");
            }
            if (stackTrace != null && stackTrace.length > 0) {
                for (StackTraceElement stackTraceElement : stackTrace) {
                    if (stackTraceElement != null) {
                        stringBuffer.append("[ " + stackTraceElement.getFileName() + ":" + stackTraceElement.getLineNumber() + " ]\r\n");
                    }
                }
            }
            Log.e(this.f4392a, stringBuffer.toString());
        }
    }

    public void error(Object obj) {
        if (this.f4394c <= 6) {
            String a = m4082a();
            Log.e(this.f4392a, a == null ? obj.toString() : a + " - " + obj);
        }
    }

    public int getLogLevel() {
        return this.f4394c;
    }

    public void m4086i(Object obj) {
        if (isDebugEnable()) {
            info(obj);
        }
    }

    public void info(Object obj) {
        if (this.f4394c <= 4) {
            String a = m4082a();
            Log.i(this.f4392a, a == null ? obj.toString() : a + " - " + obj);
        }
    }

    public boolean isDebugEnable() {
        return this.f4393b;
    }

    public void setDebugEnable(boolean z) {
        this.f4393b = z;
    }

    public void setLogLevel(int i) {
        this.f4394c = i;
    }

    public void setTag(String str) {
        this.f4392a = str;
    }

    public void m4087v(Object obj) {
        if (isDebugEnable()) {
            verbose(obj);
        }
    }

    public void verbose(Object obj) {
        if (this.f4394c <= 2) {
            String a = m4082a();
            Log.v(this.f4392a, a == null ? obj.toString() : a + " - " + obj);
        }
    }

    public void m4088w(Object obj) {
        if (isDebugEnable()) {
            warn(obj);
        }
    }

    public void warn(Object obj) {
        if (this.f4394c <= 5) {
            String a = m4082a();
            Log.w(this.f4392a, a == null ? obj.toString() : a + " - " + obj);
        }
    }
}
