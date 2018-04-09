package com.umeng.socialize.net;

import android.content.Context;
import com.umeng.socialize.net.base.SocializeRequest;
import com.umeng.socialize.utils.SocializeUtils;

public class UrlRequest extends SocializeRequest {
    private static final String f5537a = "/link/add/";
    private static final int f5538d = 26;
    private String f5539b;
    private String f5540c;

    public UrlRequest(Context context, String str, String str2) {
        super(context, "", UrlResponse.class, 26, RequestMethod.POST);
        this.mContext = context;
        this.f5539b = str2;
        this.f5540c = str;
    }

    public void onPrepareRequest() {
        super.onPrepareRequest();
        addStringParams("url", this.f5539b);
        addStringParams("to", this.f5540c);
    }

    protected String getPath() {
        return f5537a + SocializeUtils.getAppkey(this.mContext) + "/";
    }
}
