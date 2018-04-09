package com.tencent.stat.p021a;

import android.content.Context;
import com.tencent.stat.StatConfig;
import com.tencent.stat.common.C0837k;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import org.json.JSONObject;

public class C1738d extends C0824e {
    private String f4690a;
    private int f4691l;
    private int f4692m = 100;

    public C1738d(Context context, int i, int i2, Throwable th) {
        super(context, i);
        if (th != null) {
            Throwable th2 = new Throwable(th);
            try {
                StackTraceElement[] stackTrace = th2.getStackTrace();
                if (stackTrace != null && stackTrace.length > this.f4692m) {
                    StackTraceElement[] stackTraceElementArr = new StackTraceElement[this.f4692m];
                    for (int i3 = 0; i3 < this.f4692m; i3++) {
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
            this.f4690a = stringWriter.toString();
            this.f4691l = i2;
            printWriter.close();
        }
    }

    public C1738d(Context context, int i, String str, int i2, int i3) {
        super(context, i);
        if (str != null) {
            if (i3 <= 0) {
                i3 = StatConfig.getMaxReportEventLength();
            }
            if (str.length() <= i3) {
                this.f4690a = str;
            } else {
                this.f4690a = str.substring(0, i3);
            }
        }
        this.f4691l = i2;
    }

    public C0825f mo2142a() {
        return C0825f.ERROR;
    }

    public void m4864a(long j) {
        this.c = j;
    }

    public boolean mo2143a(JSONObject jSONObject) {
        C0837k.m2714a(jSONObject, "er", this.f4690a);
        jSONObject.put("ea", this.f4691l);
        return true;
    }
}
