package com.tencent.stat.p021a;

import android.content.Context;
import com.tencent.stat.StatGameUser;
import com.tencent.stat.common.C0837k;
import org.json.JSONObject;

public class C1739g extends C0824e {
    private StatGameUser f4693a = null;

    public C1739g(Context context, int i, StatGameUser statGameUser) {
        super(context, i);
        this.f4693a = statGameUser.clone();
    }

    public C0825f mo2142a() {
        return C0825f.MTA_GAME_USER;
    }

    public boolean mo2143a(JSONObject jSONObject) {
        if (this.f4693a == null) {
            return false;
        }
        C0837k.m2714a(jSONObject, "wod", this.f4693a.getWorldName());
        C0837k.m2714a(jSONObject, "gid", this.f4693a.getAccount());
        C0837k.m2714a(jSONObject, "lev", this.f4693a.getLevel());
        return true;
    }
}
