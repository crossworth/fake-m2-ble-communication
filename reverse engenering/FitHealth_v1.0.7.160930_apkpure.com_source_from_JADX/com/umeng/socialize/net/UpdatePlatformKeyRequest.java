package com.umeng.socialize.net;

import android.content.Context;
import android.text.TextUtils;
import com.umeng.socialize.common.SocializeConstants;
import com.umeng.socialize.net.base.SocializeRequest;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import com.umeng.socialize.utils.SocializeUtils;
import java.util.Map;

public class UpdatePlatformKeyRequest extends SocializeRequest {
    private static final String f5529a = "/share/keysecret/";
    private static final int f5530b = 25;
    private Map<String, String> f5531c = null;

    public UpdatePlatformKeyRequest(Context context, Map<String, String> map) {
        super(context, "", UpdatePlatformKeyResponse.class, 25, RequestMethod.POST);
        this.mContext = context;
        this.f5531c = map;
    }

    public void onPrepareRequest() {
        String str = (String) this.f5531c.get(SocializeConstants.FIELD_WX_APPID);
        String str2 = (String) this.f5531c.get(SocializeConstants.FIELD_WX_SECRET);
        String str3 = (String) this.f5531c.get(SocializeConstants.FIELD_QZONE_ID);
        String str4 = (String) this.f5531c.get("qzone_secret");
        if (!TextUtils.isEmpty(str)) {
            addStringParams(SocializeProtocolConstants.PROTOCOL_KEY_WX_APPID, str);
            addStringParams(SocializeProtocolConstants.PROTOCOL_KEY_WX_SECRET, str2);
        }
        if (!TextUtils.isEmpty(str3)) {
            addStringParams(SocializeProtocolConstants.PROTOCOL_KEY_QZONE_ID, str3);
            addStringParams("qzone_secret", str4);
        }
        str = SocializeUtils.getAppkey(this.mContext);
        addStringParams(SocializeProtocolConstants.PROTOCOL_KEY_AK, str);
        addStringParams(SocializeProtocolConstants.PROTOCOL_KEY_UMENG_SECRET, SocializeUtils.reverse(str));
    }

    protected String getPath() {
        return f5529a + SocializeUtils.getAppkey(this.mContext) + "/";
    }
}
