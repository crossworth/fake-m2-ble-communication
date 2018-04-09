package com.umeng.socialize.p024a;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.net.base.SocializeReseponse;
import java.util.Map;
import org.json.JSONObject;

/* compiled from: AnalyticsResponse */
public class C2011b extends SocializeReseponse {
    public Map<SHARE_MEDIA, Integer> f5445a;
    public String f5446b;
    public SHARE_MEDIA f5447c;

    public C2011b(JSONObject jSONObject) {
        super(jSONObject);
    }

    public String toString() {
        return "ShareMultiResponse [mInfoMap=" + this.f5445a + ", mWeiboId=" + this.f5446b + ", mMsg=" + this.mMsg + ", mStCode=" + this.mStCode + "]";
    }
}
