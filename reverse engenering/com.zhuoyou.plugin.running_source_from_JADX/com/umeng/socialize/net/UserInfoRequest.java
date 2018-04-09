package com.umeng.socialize.net;

import android.content.Context;
import com.umeng.socialize.net.base.SocializeRequest;
import com.umeng.socialize.utils.SocializeUtils;

public class UserInfoRequest extends SocializeRequest {
    private static final String f4975a = "/share/userinfo/";
    private static final int f4976b = 12;
    private String f4977c;

    public UserInfoRequest(Context context, String str) {
        super(context, "", UserInfoResponse.class, 12, RequestMethod.GET);
        this.mContext = context;
        this.f4977c = str;
    }

    protected String getPath() {
        return f4975a + SocializeUtils.getAppkey(this.mContext) + "/" + this.f4977c + "/";
    }
}
