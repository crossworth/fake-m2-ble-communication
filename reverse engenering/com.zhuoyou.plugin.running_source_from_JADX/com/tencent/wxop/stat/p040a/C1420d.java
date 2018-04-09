package com.tencent.wxop.stat.p040a;

import android.content.Context;
import com.tencent.wxop.stat.StatConfig;
import com.tencent.wxop.stat.StatSpecifyReportedInfo;
import com.tencent.wxop.stat.common.C1433b;
import com.tencent.wxop.stat.common.C1448q;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import org.json.JSONObject;

public class C1420d extends C1416e {
    private String f4608a;
    private int f4609m;
    private int f4610n = 100;
    private Thread f4611o = null;

    public C1420d(Context context, int i, int i2, Throwable th, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        super(context, i, statSpecifyReportedInfo);
        m4281a(i2, th);
    }

    public C1420d(Context context, int i, int i2, Throwable th, Thread thread, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        super(context, i, statSpecifyReportedInfo);
        m4281a(i2, th);
        this.f4611o = thread;
    }

    public C1420d(Context context, int i, String str, int i2, int i3, Thread thread, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        super(context, i, statSpecifyReportedInfo);
        if (str != null) {
            if (i3 <= 0) {
                i3 = StatConfig.getMaxReportEventLength();
            }
            if (str.length() <= i3) {
                this.f4608a = str;
            } else {
                this.f4608a = str.substring(0, i3);
            }
        }
        this.f4611o = thread;
        this.f4609m = i2;
    }

    private void m4281a(int i, Throwable th) {
        if (th != null) {
            Writer stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            th.printStackTrace(printWriter);
            this.f4608a = stringWriter.toString();
            this.f4609m = i;
            printWriter.close();
        }
    }

    public C1421f mo2223a() {
        return C1421f.ERROR;
    }

    public boolean mo2224a(JSONObject jSONObject) {
        C1448q.m4464a(jSONObject, "er", this.f4608a);
        jSONObject.put("ea", this.f4609m);
        if (this.f4609m == 2 || this.f4609m == 3) {
            new C1433b(this.l).m4386a(jSONObject, this.f4611o);
        }
        return true;
    }
}
