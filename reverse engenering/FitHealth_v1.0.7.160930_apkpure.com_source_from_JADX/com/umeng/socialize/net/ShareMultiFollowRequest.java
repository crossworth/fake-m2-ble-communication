package com.umeng.socialize.net;

import android.content.Context;
import com.umeng.socialize.net.base.SocializeRequest;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import com.umeng.socialize.utils.SocializeUtils;

public class ShareMultiFollowRequest extends SocializeRequest {
    private static final String f5512a = "/share/follow/";
    private static final int f5513b = 18;
    private String f5514c;
    private String f5515d;
    private String f5516e;

    public ShareMultiFollowRequest(Context context, String str, String str2, String str3) {
        super(context, "", ShareMultiFollowResponse.class, 18, RequestMethod.POST);
        this.mContext = context;
        this.f5514c = str;
        this.f5515d = str2;
        this.f5516e = str3;
    }

    public void onPrepareRequest() {
        super.onPrepareRequest();
        addStringParams("to", this.f5514c);
        addStringParams(SocializeProtocolConstants.PROTOCOL_KEY_SHARE_FOLLOWS, this.f5516e);
    }

    protected String getPath() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(f5512a).append(SocializeUtils.getAppkey(this.mContext)).append("/").append(this.f5515d).append("/");
        return stringBuilder.toString();
    }
}
