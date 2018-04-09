package com.tencent.stat.common;

import android.util.Log;
import com.umeng.socialize.common.SocializeConstants;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import com.zhuoyou.plugin.bluetooth.data.BMessage;

public final class StatLogger {
    private String f2872a = SocializeProtocolConstants.PROTOCOL_KEY_DEFAULT;
    private boolean f2873b = true;
    private int f2874c = 2;

    public StatLogger(String str) {
        this.f2872a = str;
    }

    private String m2677a() {
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

    public void m2678d(Object obj) {
        if (isDebugEnable()) {
            debug(obj);
        }
    }

    public void debug(Object obj) {
        if (this.f2874c <= 3) {
            String a = m2677a();
            Log.d(this.f2872a, a == null ? obj.toString() : a + " - " + obj);
        }
    }

    public void m2679e(Exception exception) {
        if (isDebugEnable()) {
            error(exception);
        }
    }

    public void m2680e(Object obj) {
        if (isDebugEnable()) {
            error(obj);
        }
    }

    public void error(Exception exception) {
        if (this.f2874c <= 6) {
            StringBuffer stringBuffer = new StringBuffer();
            String a = m2677a();
            StackTraceElement[] stackTrace = exception.getStackTrace();
            if (a != null) {
                stringBuffer.append(a + " - " + exception + BMessage.CRLF);
            } else {
                stringBuffer.append(exception + BMessage.CRLF);
            }
            if (stackTrace != null && stackTrace.length > 0) {
                for (StackTraceElement stackTraceElement : stackTrace) {
                    if (stackTraceElement != null) {
                        stringBuffer.append("[ " + stackTraceElement.getFileName() + ":" + stackTraceElement.getLineNumber() + " ]\r\n");
                    }
                }
            }
            Log.e(this.f2872a, stringBuffer.toString());
        }
    }

    public void error(Object obj) {
        if (this.f2874c <= 6) {
            String a = m2677a();
            Log.e(this.f2872a, a == null ? obj.toString() : a + " - " + obj);
        }
    }

    public int getLogLevel() {
        return this.f2874c;
    }

    public void m2681i(Object obj) {
        if (isDebugEnable()) {
            info(obj);
        }
    }

    public void info(Object obj) {
        if (this.f2874c <= 4) {
            String a = m2677a();
            Log.i(this.f2872a, a == null ? obj.toString() : a + " - " + obj);
        }
    }

    public boolean isDebugEnable() {
        return this.f2873b;
    }

    public void setDebugEnable(boolean z) {
        this.f2873b = z;
    }

    public void setLogLevel(int i) {
        this.f2874c = i;
    }

    public void setTag(String str) {
        this.f2872a = str;
    }

    public void m2682v(Object obj) {
        if (isDebugEnable()) {
            verbose(obj);
        }
    }

    public void verbose(Object obj) {
        if (this.f2874c <= 2) {
            String a = m2677a();
            Log.v(this.f2872a, a == null ? obj.toString() : a + " - " + obj);
        }
    }

    public void m2683w(Object obj) {
        if (isDebugEnable()) {
            warn(obj);
        }
    }

    public void warn(Object obj) {
        if (this.f2874c <= 5) {
            String a = m2677a();
            Log.w(this.f2872a, a == null ? obj.toString() : a + " - " + obj);
        }
    }
}
