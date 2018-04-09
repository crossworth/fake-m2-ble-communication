package com.umeng.socialize.net.stats;

import android.content.Context;
import com.umeng.socialize.common.SocializeConstants;
import com.umeng.socialize.net.base.SocializeRequest;
import com.umeng.socialize.net.base.SocializeReseponse;
import com.umeng.socialize.net.utils.SocializeNetUtils;

public abstract class StatsRequest extends SocializeRequest {
    protected static String PARAMS_STATS_TYPE = "stats_type";
    protected String mRequestPath = "sdkstats";

    public StatsRequest(Context context, String str, Class<? extends SocializeReseponse> cls, int i, RequestMethod requestMethod) {
        super(context, str, cls, i, requestMethod);
        addStringParams("test", SocializeConstants.DEBUG_MODE ? "1" : "0");
    }

    public String toGetUrl() {
        return SocializeNetUtils.generateGetURL(getBaseUrl(), buildParams());
    }
}
