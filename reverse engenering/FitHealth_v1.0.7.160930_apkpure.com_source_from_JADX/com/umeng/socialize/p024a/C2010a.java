package com.umeng.socialize.p024a;

import android.content.Context;
import android.text.TextUtils;
import com.umeng.socialize.Config;
import com.umeng.socialize.media.UMediaObject;
import com.umeng.socialize.net.base.SocializeRequest;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import com.umeng.socialize.utils.Log;
import com.umeng.socialize.utils.SocializeUtils;

/* compiled from: AnalyticsReqeust */
public class C2010a extends SocializeRequest {
    private static final String f5438a = "/share/multi_add/";
    private static final int f5439b = 9;
    private String f5440c;
    private String f5441d;
    private String f5442e;
    private String f5443f;
    private UMediaObject f5444g;

    public C2010a(Context context, String str, String str2) {
        super(context, "", C2011b.class, 9, RequestMethod.POST);
        this.mContext = context;
        this.f5440c = str;
        this.f5443f = str2;
        setReqType(1);
    }

    public void m6102a(String str) {
        this.f5440c = str;
    }

    public void m6103b(String str) {
        this.f5441d = str;
    }

    public void m6104c(String str) {
        this.f5442e = str;
    }

    public void m6105d(String str) {
        this.f5443f = str;
    }

    public void m6101a(UMediaObject uMediaObject) {
        this.f5444g = uMediaObject;
    }

    public void onPrepareRequest() {
        super.onPrepareRequest();
        String str = "{\"%s\":\"%s\"}";
        Object[] objArr = new Object[2];
        objArr[0] = this.f5440c;
        objArr[1] = this.f5441d == null ? "" : this.f5441d;
        String format = String.format(str, objArr);
        str = SocializeUtils.getAppkey(this.mContext);
        addStringParams("to", format);
        addStringParams("sns", format);
        addStringParams(SocializeProtocolConstants.PROTOCOL_KEY_AK, str);
        addStringParams("type", this.f5442e);
        addStringParams(SocializeProtocolConstants.PROTOCOL_KEY_COMMENT_TEXT, this.f5443f);
        Log.m3248d("para", "parameter" + format + " " + SocializeUtils.getAppkey(this.mContext) + " " + this.f5442e + " " + this.f5443f);
        addMediaParams(this.f5444g);
        if (!TextUtils.isEmpty(Config.getAdapterSDK())) {
            addStringParams(Config.getAdapterSDK(), Config.getAdapterSDKVersion());
        }
    }

    protected String getPath() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(f5438a);
        stringBuilder.append(SocializeUtils.getAppkey(this.mContext));
        stringBuilder.append("/").append(Config.EntityKey).append("/");
        return stringBuilder.toString();
    }
}
