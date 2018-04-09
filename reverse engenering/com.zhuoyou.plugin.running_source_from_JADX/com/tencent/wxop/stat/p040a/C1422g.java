package com.tencent.wxop.stat.p040a;

import android.content.Context;
import com.tencent.wxop.stat.StatGameUser;
import com.tencent.wxop.stat.StatSpecifyReportedInfo;
import com.tencent.wxop.stat.common.C1448q;
import org.json.JSONObject;

public class C1422g extends C1416e {
    private StatGameUser f4623a = null;

    public C1422g(Context context, int i, StatGameUser statGameUser, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        super(context, i, statSpecifyReportedInfo);
        this.f4623a = statGameUser.clone();
    }

    public C1421f mo2223a() {
        return C1421f.MTA_GAME_USER;
    }

    public boolean mo2224a(JSONObject jSONObject) {
        if (this.f4623a == null) {
            return false;
        }
        C1448q.m4464a(jSONObject, "wod", this.f4623a.getWorldName());
        C1448q.m4464a(jSONObject, "gid", this.f4623a.getAccount());
        C1448q.m4464a(jSONObject, "lev", this.f4623a.getLevel());
        return true;
    }
}
