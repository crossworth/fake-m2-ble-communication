package com.umeng.socialize.net;

import android.content.Context;
import com.umeng.socialize.net.base.SocializeRequest;
import com.umeng.socialize.utils.SocializeUtils;

public class PlatformTokenUploadReq extends SocializeRequest {
    private static final String f5502a = "/share/token/";
    private static final int f5503b = 21;

    public PlatformTokenUploadReq(Context context) {
        super(context, "", PlatformTokenUploadResponse.class, 21, RequestMethod.POST);
    }

    protected String getPath() {
        return f5502a + SocializeUtils.getAppkey(this.mContext) + "/";
    }
}
