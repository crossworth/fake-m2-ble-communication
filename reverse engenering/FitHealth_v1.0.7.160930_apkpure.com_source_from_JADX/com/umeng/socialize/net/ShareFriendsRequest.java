package com.umeng.socialize.net;

import android.content.Context;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.net.base.SocializeRequest;
import com.umeng.socialize.utils.SocializeUtils;

public class ShareFriendsRequest extends SocializeRequest {
    private static final String f5508a = "/share/friends/";
    private static final int f5509b = 14;
    private String f5510c;
    private SHARE_MEDIA f5511d;

    public ShareFriendsRequest(Context context, SHARE_MEDIA share_media, String str) {
        super(context, "", ShareFriendsResponse.class, 14, RequestMethod.GET);
        this.mContext = context;
        this.f5510c = str;
        this.f5511d = share_media;
    }

    public void onPrepareRequest() {
        addStringParams("to", this.f5511d.toString().toLowerCase());
    }

    protected String getPath() {
        return f5508a + SocializeUtils.getAppkey(this.mContext) + "/" + this.f5510c + "/";
    }
}
