package com.umeng.socialize.net;

import android.content.Context;
import android.text.TextUtils;
import com.umeng.socialize.Config;
import com.umeng.socialize.net.base.SocializeRequest;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import com.umeng.socialize.utils.SocializeUtils;

public class ActionBarRequest extends SocializeRequest {
    private static final String f4960a = "/bar/get/";
    private static final int f4961b = 1;
    private int f4962c = 0;

    public ActionBarRequest(Context context, boolean z) {
        int i = 1;
        super(context, "", ActionBarResponse.class, 1, RequestMethod.GET);
        this.mContext = context;
        if (!z) {
            i = 0;
        }
        this.f4962c = i;
    }

    public void onPrepareRequest() {
        addStringParams(SocializeProtocolConstants.PROTOCOL_KEY_DESCRIPTOR, Config.Descriptor);
        addStringParams(SocializeProtocolConstants.PROTOCOL_KEY_NEW_INSTALL, String.valueOf(this.f4962c));
        addStringParams(SocializeProtocolConstants.PROTOCOL_KEY_USECOCOS, String.valueOf(Config.UseCocos));
        if (!TextUtils.isEmpty(Config.EntityName)) {
            addStringParams(SocializeProtocolConstants.PROTOCOL_KEY_ENTITY_NAME, Config.EntityName);
        }
    }

    protected String getPath() {
        return f4960a + SocializeUtils.getAppkey(this.mContext) + "/";
    }
}
