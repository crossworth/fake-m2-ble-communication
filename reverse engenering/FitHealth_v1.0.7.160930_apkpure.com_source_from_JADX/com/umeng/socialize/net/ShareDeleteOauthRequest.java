package com.umeng.socialize.net;

import android.content.Context;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.net.base.SocializeRequest;
import com.umeng.socialize.net.base.SocializeReseponse;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import com.umeng.socialize.utils.SocializeUtils;

public class ShareDeleteOauthRequest extends SocializeRequest {
    private static final String f5504a = "/share/auth_delete/";
    private static final int f5505b = 15;
    private SHARE_MEDIA f5506c;
    private String f5507d;

    public ShareDeleteOauthRequest(Context context, SHARE_MEDIA share_media, String str) {
        super(context, "", SocializeReseponse.class, 15, RequestMethod.POST);
        this.mContext = context;
        this.f5506c = share_media;
        this.f5507d = str;
    }

    public void onPrepareRequest() {
        addStringParams("uid", this.f5507d);
        addStringParams(SocializeProtocolConstants.PROTOCOL_KEY_VERIFY_MEDIA, this.f5506c.toString());
    }

    protected String getPath() {
        return f5504a + SocializeUtils.getAppkey(this.mContext) + "/";
    }
}
