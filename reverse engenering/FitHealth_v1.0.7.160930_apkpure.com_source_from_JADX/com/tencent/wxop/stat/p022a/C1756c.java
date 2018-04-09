package com.tencent.wxop.stat.p022a;

import android.content.Context;
import com.tencent.wxop.stat.C0897f;
import com.tencent.wxop.stat.p023b.C0879d;
import com.tencent.wxop.stat.p023b.C0891r;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import org.json.JSONObject;

public final class C1756c extends C0873d {
    private String f4739a;
    private int ay;
    private int bn = 100;
    private Thread bo = null;

    public C1756c(Context context, int i, Throwable th, C0897f c0897f) {
        super(context, i, c0897f);
        m4890a(99, th);
    }

    public C1756c(Context context, int i, Throwable th, Thread thread) {
        super(context, i, null);
        m4890a(2, th);
        this.bo = thread;
    }

    private void m4890a(int i, Throwable th) {
        if (th != null) {
            Writer stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            th.printStackTrace(printWriter);
            this.f4739a = stringWriter.toString();
            this.ay = i;
            printWriter.close();
        }
    }

    public final C0874e ac() {
        return C0874e.ERROR;
    }

    public final boolean mo2147b(JSONObject jSONObject) {
        C0891r.m2918a(jSONObject, "er", this.f4739a);
        jSONObject.put("ea", this.ay);
        if (this.ay == 2 || this.ay == 3) {
            new C0879d(this.bv).m2859a(jSONObject, this.bo);
        }
        return true;
    }
}
