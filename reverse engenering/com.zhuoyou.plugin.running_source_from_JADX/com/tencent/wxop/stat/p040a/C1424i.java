package com.tencent.wxop.stat.p040a;

import android.content.Context;
import com.tencent.wxop.stat.StatConfig;
import com.tencent.wxop.stat.StatSpecifyReportedInfo;
import com.tencent.wxop.stat.common.C1448q;
import org.json.JSONObject;

public class C1424i extends C1416e {
    public static final StatSpecifyReportedInfo f4627a;

    static {
        StatSpecifyReportedInfo statSpecifyReportedInfo = new StatSpecifyReportedInfo();
        f4627a = statSpecifyReportedInfo;
        statSpecifyReportedInfo.setAppKey("A9VH9B8L4GX4");
    }

    public C1424i(Context context) {
        super(context, 0, f4627a);
    }

    public C1421f mo2223a() {
        return C1421f.NETWORK_DETECTOR;
    }

    public boolean mo2224a(JSONObject jSONObject) {
        C1448q.m4464a(jSONObject, "actky", StatConfig.getAppKey(this.l));
        return true;
    }
}
