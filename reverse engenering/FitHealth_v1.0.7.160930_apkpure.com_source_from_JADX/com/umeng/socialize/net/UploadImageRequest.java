package com.umeng.socialize.net;

import android.content.Context;
import com.umeng.socialize.media.UMediaObject;
import com.umeng.socialize.net.base.SocializeRequest;
import com.umeng.socialize.utils.SocializeUtils;

public class UploadImageRequest extends SocializeRequest {
    private static final String f5532a = "/api/upload_pic/";
    private static final int f5533b = 23;
    private Context f5534c;
    private String f5535d;
    private UMediaObject f5536e;

    public UploadImageRequest(Context context, UMediaObject uMediaObject, String str) {
        super(context, "", UploadImageResponse.class, 23, RequestMethod.POST);
        this.f5534c = context;
        this.f5535d = str;
        this.f5536e = uMediaObject;
    }

    protected String getPath() {
        return f5532a + SocializeUtils.getAppkey(this.f5534c) + "/";
    }

    public void onPrepareRequest() {
        addStringParams("usid", this.f5535d);
        addMediaParams(this.f5536e);
    }
}
