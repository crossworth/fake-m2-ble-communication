package com.umeng.socialize.net.stats;

import com.umeng.socialize.common.SocializeConstants;
import com.umeng.socialize.net.base.SocializeReseponse;
import com.umeng.socialize.net.utils.SocializeNetUtils;
import java.util.HashMap;
import java.util.Map;

public class StatsLogRequest extends StatsRequest {
    public StatsLogRequest(Class<? extends SocializeReseponse> cls) {
        super(null, "", cls, 0, RequestMethod.POST);
        this.mRequestPath += "?test=";
        this.mRequestPath += (SocializeConstants.DEBUG_MODE ? "1" : "0");
        this.mMimeType = MIME.JSON;
    }

    public String toGetUrl() {
        return SocializeNetUtils.generateGetURL(getBaseUrl(), buildParams());
    }

    protected String getPath() {
        return this.mRequestPath;
    }

    public void onPrepareRequest() {
    }

    public Map<String, Object> getBodyPair() {
        Map<String, Object> hashMap = new HashMap();
        hashMap.putAll(this.mParams);
        return hashMap;
    }
}
