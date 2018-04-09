package com.umeng.socialize.net;

import android.content.Context;
import android.text.TextUtils;
import com.umeng.socialize.Config;
import com.umeng.socialize.net.base.SocializeRequest;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import com.umeng.socialize.utils.SocializeUtils;

public class ActionBarRequest extends SocializeRequest {
    private static final String f5494a = "/bar/get/";
    private static final int f5495b = 1;
    private int f5496c = 0;

    public ActionBarRequest(Context context, boolean z) {
        int i = 1;
        super(context, "", ActionBarResponse.class, 1, RequestMethod.GET);
        this.mContext = context;
        if (!z) {
            i = 0;
        }
        this.f5496c = i;
    }

    public void onPrepareRequest() {
        addStringParams(SocializeProtocolConstants.PROTOCOL_KEY_DESCRIPTOR, Config.Descriptor);
        addStringParams(SocializeProtocolConstants.PROTOCOL_KEY_NEW_INSTALL, String.valueOf(this.f5496c));
        addStringParams(SocializeProtocolConstants.PROTOCOL_KEY_USECOCOS, String.valueOf(Config.UseCocos));
        if (!TextUtils.isEmpty(Config.EntityName)) {
            addStringParams(SocializeProtocolConstants.PROTOCOL_KEY_ENTITY_NAME, Config.EntityName);
        }
    }

    protected String getPath() {
        return f5494a + SocializeUtils.getAppkey(this.mContext) + "/";
    }
}