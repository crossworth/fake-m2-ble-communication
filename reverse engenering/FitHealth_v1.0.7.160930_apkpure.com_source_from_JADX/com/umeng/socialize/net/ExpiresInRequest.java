package com.umeng.socialize.net;

import android.content.Context;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.common.SocializeConstants;
import com.umeng.socialize.net.base.SocializeRequest;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import com.umeng.socialize.utils.SocializeUtils;
import com.zhuoyi.system.util.constant.SeparatorConstants;

public class ExpiresInRequest extends SocializeRequest {
    private static final String f5497a = "/share/validate_token/";
    private static final int f5498b = 24;
    private SHARE_MEDIA[] f5499c;

    public ExpiresInRequest(Context context, SHARE_MEDIA[] share_mediaArr) {
        super(context, "", ExpiresInResponse.class, 24, RequestMethod.GET);
        this.f5499c = share_mediaArr;
    }

    public void onPrepareRequest() {
        StringBuilder stringBuilder = new StringBuilder();
        if (this.f5499c != null && this.f5499c.length > 0) {
            for (SHARE_MEDIA share_media : this.f5499c) {
                if (share_media != SHARE_MEDIA.GENERIC) {
                    stringBuilder.append(share_media.toString()).append(SeparatorConstants.SEPARATOR_ADS_ID);
                }
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        addStringParams(SocializeProtocolConstants.PROTOCOL_KEY_PLATFORM, stringBuilder.toString());
        addStringParams("uid", SocializeConstants.UID);
    }

    protected String getPath() {
        return f5497a + SocializeUtils.getAppkey(this.mContext) + "/";
    }
}
