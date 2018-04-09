package com.umeng.socialize.analytics;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.net.base.SocializeReseponse;
import java.util.Map;
import org.json.JSONObject;

/* compiled from: AnalyticsResponse */
public class C1583b extends SocializeReseponse {
    public Map<SHARE_MEDIA, Integer> f4930a;
    public String f4931b;
    public SHARE_MEDIA f4932c;

    public C1583b(JSONObject jSONObject) {
        super(jSONObject);
    }

    public String toString() {
        return "ShareMultiResponse [mInfoMap=" + this.f4930a + ", mWeiboId=" + this.f4931b + ", mMsg=" + this.mMsg + ", mStCode=" + this.mStCode + "]";
    }
}
