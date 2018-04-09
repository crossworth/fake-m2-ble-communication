package com.umeng.socialize.net;

import android.content.Context;
import com.umeng.socialize.net.base.SocializeRequest;
import com.umeng.socialize.utils.SocializeUtils;

public class UserInfoRequest extends SocializeRequest {
    private static final String f5541a = "/share/userinfo/";
    private static final int f5542b = 12;
    private String f5543c;

    public UserInfoRequest(Context context, String str) {
        super(context, "", UserInfoResponse.class, 12, RequestMethod.GET);
        this.mContext = context;
        this.f5543c = str;
    }

    protected String getPath() {
        return f5541a + SocializeUtils.getAppkey(this.mContext) + "/" + this.f5543c + "/";
    }
}
