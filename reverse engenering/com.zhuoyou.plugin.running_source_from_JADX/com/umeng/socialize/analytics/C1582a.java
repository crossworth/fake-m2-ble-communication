package com.umeng.socialize.analytics;

import android.content.Context;
import android.text.TextUtils;
import com.umeng.socialize.Config;
import com.umeng.socialize.media.UMediaObject;
import com.umeng.socialize.net.base.SocializeRequest;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import com.umeng.socialize.utils.Log;
import com.umeng.socialize.utils.SocializeUtils;

/* compiled from: AnalyticsReqeust */
public class C1582a extends SocializeRequest {
    private static final String f4923a = "/share/multi_add/";
    private static final int f4924b = 9;
    private String f4925c;
    private String f4926d;
    private String f4927e;
    private String f4928f;
    private UMediaObject f4929g;

    public C1582a(Context context, String str, String str2) {
        super(context, "", C1583b.class, 9, RequestMethod.POST);
        this.mContext = context;
        this.f4925c = str;
        this.f4928f = str2;
        setReqType(1);
    }

    public void m4494a(String str) {
        this.f4925c = str;
    }

    public void m4495b(String str) {
        this.f4926d = str;
    }

    public void m4496c(String str) {
        this.f4927e = str;
    }

    public void m4497d(String str) {
        this.f4928f = str;
    }

    public void m4493a(UMediaObject uMediaObject) {
        this.f4929g = uMediaObject;
    }

    public void onPrepareRequest() {
        super.onPrepareRequest();
        String str = "{\"%s\":\"%s\"}";
        Object[] objArr = new Object[2];
        objArr[0] = this.f4925c;
        objArr[1] = this.f4926d == null ? "" : this.f4926d;
        String format = String.format(str, objArr);
        str = SocializeUtils.getAppkey(this.mContext);
        addStringParams(SocializeProtocolConstants.PROTOCOL_KEY_DESCRIPTOR, Config.Descriptor);
        addStringParams("to", format);
        addStringParams("sns", format);
        addStringParams(SocializeProtocolConstants.PROTOCOL_KEY_AK, str);
        addStringParams("type", this.f4927e);
        addStringParams(SocializeProtocolConstants.PROTOCOL_KEY_COMMENT_TEXT, this.f4928f);
        Log.m4546d("para", "parameter" + format + " " + SocializeUtils.getAppkey(this.mContext) + " " + this.f4927e + " " + this.f4928f);
        addMediaParams(this.f4929g);
        if (!TextUtils.isEmpty(Config.getAdapterSDK())) {
            addStringParams(Config.getAdapterSDK(), Config.getAdapterSDKVersion());
        }
    }

    protected String getPath() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(f4923a);
        stringBuilder.append(SocializeUtils.getAppkey(this.mContext));
        stringBuilder.append("/").append(Config.EntityKey).append("/");
        return stringBuilder.toString();
    }
}
