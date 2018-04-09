package com.umeng.socialize.net;

import android.content.Context;
import com.umeng.socialize.net.base.SocializeRequest;
import com.umeng.socialize.utils.SocializeUtils;

public class UrlRequest extends SocializeRequest {
    private static final String f4971a = "/link/add/";
    private static final int f4972d = 26;
    private String f4973b;
    private String f4974c;

    public UrlRequest(Context context, String str, String str2) {
        super(context, "", UrlResponse.class, 26, RequestMethod.POST);
        this.mContext = context;
        this.f4973b = str2;
        this.f4974c = str;
    }

    public void onPrepareRequest() {
        super.onPrepareRequest();
        addStringParams("url", this.f4973b);
        addStringParams("to", this.f4974c);
    }

    protected String getPath() {
        return f4971a + SocializeUtils.getAppkey(this.mContext) + "/";
    }
}
