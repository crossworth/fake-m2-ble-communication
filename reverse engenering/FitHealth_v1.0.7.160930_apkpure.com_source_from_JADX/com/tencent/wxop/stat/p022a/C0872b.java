package com.tencent.wxop.stat.p022a;

import com.zhuoyi.system.util.constant.SeparatorConstants;
import org.json.JSONArray;
import org.json.JSONObject;

public final class C0872b {
    public String f3005a;
    public JSONArray bl;
    public JSONObject bm = null;

    public C0872b(String str) {
        this.f3005a = str;
        this.bm = new JSONObject();
    }

    public final boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof C0872b)) {
            return false;
        }
        return toString().equals(((C0872b) obj).toString());
    }

    public final int hashCode() {
        return toString().hashCode();
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder(32);
        stringBuilder.append(this.f3005a).append(SeparatorConstants.SEPARATOR_ADS_ID);
        if (this.bl != null) {
            stringBuilder.append(this.bl.toString());
        }
        if (this.bm != null) {
            stringBuilder.append(this.bm.toString());
        }
        return stringBuilder.toString();
    }
}
