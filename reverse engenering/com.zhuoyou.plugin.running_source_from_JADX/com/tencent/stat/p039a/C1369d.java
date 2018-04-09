package com.tencent.stat.p039a;

import android.content.Context;
import com.tencent.stat.StatConfig;
import com.tencent.stat.common.C1389k;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import org.json.JSONObject;

public class C1369d extends C1365e {
    private String f4356a;
    private int f4357l;
    private int f4358m = 100;

    public C1369d(Context context, int i, int i2, Throwable th) {
        super(context, i);
        if (th != null) {
            Throwable th2 = new Throwable(th);
            try {
                StackTraceElement[] stackTrace = th2.getStackTrace();
                if (stackTrace != null && stackTrace.length > this.f4358m) {
                    StackTraceElement[] stackTraceElementArr = new StackTraceElement[this.f4358m];
                    for (int i3 = 0; i3 < this.f4358m; i3++) {
                        stackTraceElementArr[i3] = stackTrace[i3];
                    }
                    th2.setStackTrace(stackTraceElementArr);
                }
            } catch (Throwable th3) {
                th3.printStackTrace();
            }
            Writer stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            th2.printStackTrace(printWriter);
            this.f4356a = stringWriter.toString();
            this.f4357l = i2;
            printWriter.close();
        }
    }

    public C1369d(Context context, int i, String str, int i2, int i3) {
        super(context, i);
        if (str != null) {
            if (i3 <= 0) {
                i3 = StatConfig.getMaxReportEventLength();
            }
            if (str.length() <= i3) {
                this.f4356a = str;
            } else {
                this.f4356a = str.substring(0, i3);
            }
        }
        this.f4357l = i2;
    }

    public C1370f mo2219a() {
        return C1370f.ERROR;
    }

    public void m4055a(long j) {
        this.c = j;
    }

    public boolean mo2220a(JSONObject jSONObject) {
        C1389k.m4121a(jSONObject, "er", this.f4356a);
        jSONObject.put("ea", this.f4357l);
        return true;
    }
}
