package com.tencent.open.p037b;

import android.os.SystemClock;
import com.tencent.connect.common.Constants;
import com.tencent.open.utils.Util;

/* compiled from: ProGuard */
public class C1322d {
    protected static C1322d f4140a;

    protected C1322d() {
    }

    public static synchronized C1322d m3896a() {
        C1322d c1322d;
        synchronized (C1322d.class) {
            if (f4140a == null) {
                f4140a = new C1322d();
            }
            c1322d = f4140a;
        }
        return c1322d;
    }

    public void m3898a(String str, String str2, String str3, String str4, String str5, String str6) {
        C1331g.m3907a().m3909a(Util.composeViaReportParams(str, str3, str4, str5, str2, str6), str2, true);
    }

    public void m3899a(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8) {
        C1331g.m3907a().m3909a(Util.composeViaReportParams(str, str4, str5, str3, str2, str6, "", str7, str8, "", "", ""), str2, false);
    }

    public void m3900a(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10) {
        C1331g.m3907a().m3909a(Util.composeViaReportParams(str, str4, str5, str3, str2, str6, str7, "", "", str8, str9, str10), str2, false);
    }

    public void m3897a(int i, String str, String str2, String str3, String str4, Long l, int i2, int i3, String str5) {
        long j;
        long elapsedRealtime = SystemClock.elapsedRealtime() - l.longValue();
        if (l.longValue() == 0 || elapsedRealtime < 0) {
            j = 0;
        } else {
            j = elapsedRealtime;
        }
        StringBuffer stringBuffer = new StringBuffer("http://c.isdspeed.qq.com/code.cgi");
        stringBuffer.append("?domain=mobile.opensdk.com&cgi=opensdk&type=").append(i).append("&code=").append(i2).append("&time=").append(j).append("&rate=").append(i3).append("&uin=").append(str2).append("&data=");
        C1331g.m3907a().m3912a(stringBuffer.toString(), Constants.HTTP_GET, Util.composeHaboCgiReportParams(String.valueOf(i), String.valueOf(i2), String.valueOf(j), String.valueOf(i3), str, str2, str3, str4, str5), true);
    }
}
