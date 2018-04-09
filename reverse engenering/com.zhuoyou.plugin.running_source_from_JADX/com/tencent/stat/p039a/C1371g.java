package com.tencent.stat.p039a;

import android.content.Context;
import com.tencent.stat.StatGameUser;
import com.tencent.stat.common.C1389k;
import org.json.JSONObject;

public class C1371g extends C1365e {
    private StatGameUser f4369a = null;

    public C1371g(Context context, int i, StatGameUser statGameUser) {
        super(context, i);
        this.f4369a = statGameUser.clone();
    }

    public C1370f mo2219a() {
        return C1370f.MTA_GAME_USER;
    }

    public boolean mo2220a(JSONObject jSONObject) {
        if (this.f4369a == null) {
            return false;
        }
        C1389k.m4121a(jSONObject, "wod", this.f4369a.getWorldName());
        C1389k.m4121a(jSONObject, "gid", this.f4369a.getAccount());
        C1389k.m4121a(jSONObject, "lev", this.f4369a.getLevel());
        return true;
    }
}
