package com.umeng.socialize.net;

import android.content.Context;
import android.text.TextUtils;
import com.umeng.socialize.Config;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.UMLocation;
import com.umeng.socialize.media.BaseMediaObject;
import com.umeng.socialize.media.UMediaObject;
import com.umeng.socialize.net.base.SocializeRequest;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import com.umeng.socialize.utils.SocializeUtils;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class ShareMultiReqeust extends SocializeRequest {
    private static final String f5517a = "/share/multi_add/";
    private static final int f5518b = 17;
    private List<Pair> f5519c = new ArrayList();
    private String f5520d;
    private String f5521e;
    private UMediaObject f5522f;
    private UMLocation f5523g;

    static class Pair {
        String f3328a;
        String f3329b;

        public Pair(String str, String str2) {
            this.f3328a = str;
            this.f3329b = str2;
        }
    }

    public ShareMultiReqeust(Context context, String str, String str2, String str3, UMediaObject uMediaObject) {
        super(context, "", ShareMultiResponse.class, 17, RequestMethod.POST);
        this.mContext = context;
        this.f5519c.add(new Pair(str, str2));
        this.f5521e = str3;
        this.f5522f = uMediaObject;
    }

    public void addPlatform(String str, String str2) {
        this.f5519c.add(new Pair(str, str2));
    }

    public void setType(String str) {
        this.f5520d = str;
    }

    public void setMedia(UMediaObject uMediaObject) {
        this.f5522f = uMediaObject;
    }

    public void setLocation(UMLocation uMLocation) {
        this.f5523g = uMLocation;
    }

    public void onPrepareRequest() {
        addStringParams("sns", m6117a());
        addStringParams(SocializeProtocolConstants.PROTOCOL_KEY_AK, SocializeUtils.getAppkey(this.mContext));
        addStringParams("type", this.f5520d);
        addStringParams(SocializeProtocolConstants.PROTOCOL_KEY_COMMENT_TEXT, this.f5521e);
        if (this.f5523g != null) {
            addStringParams(SocializeProtocolConstants.PROTOCOL_KEY_LOCATION, this.f5523g.toString());
        }
        m6119a(this.f5522f);
        Object adapterSDKVersion = Config.getAdapterSDKVersion();
        if (!TextUtils.isEmpty(adapterSDKVersion)) {
            addStringParams(Config.getAdapterSDK(), adapterSDKVersion);
        }
    }

    private String m6117a() {
        JSONObject jSONObject = new JSONObject();
        try {
            for (Pair pair : this.f5519c) {
                jSONObject.put(pair.f3328a, pair.f3329b);
            }
        } catch (Exception e) {
        }
        return jSONObject.toString();
    }

    protected String getPath() {
        return f5517a + SocializeUtils.getAppkey(this.mContext) + "/" + Config.EntityKey + "/";
    }

    private void m6119a(UMediaObject uMediaObject) {
        Object obj = "";
        CharSequence charSequence = "";
        String str = "";
        SHARE_MEDIA share_media = SHARE_MEDIA.GENERIC;
        CharSequence charSequence2 = "";
        if (uMediaObject != null) {
            addMediaParams(uMediaObject);
            if (uMediaObject instanceof BaseMediaObject) {
                BaseMediaObject baseMediaObject = (BaseMediaObject) uMediaObject;
                obj = baseMediaObject.getTitle();
                charSequence = baseMediaObject.getThumb();
            }
        }
        CharSequence a = m6118a(this.mContext);
        try {
            JSONObject jSONObject = new JSONObject();
            if (!TextUtils.isEmpty(a)) {
                jSONObject.put("app_name", a);
            }
            if (!(TextUtils.isEmpty(obj) || obj.equals("未知"))) {
                jSONObject.put(SocializeProtocolConstants.PROTOCOL_KEY_TITLE, obj);
            }
            if (!TextUtils.isEmpty(charSequence)) {
                jSONObject.put(SocializeProtocolConstants.PROTOCOL_KEY_THUMB, charSequence);
            }
            if (!TextUtils.isEmpty(charSequence2)) {
                jSONObject.put(SocializeProtocolConstants.PROTOCOL_KEY_APP_WEBSITE, charSequence2);
            }
            addStringParams(SocializeProtocolConstants.PROTOCOL_KEY_EXTEND, jSONObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String m6118a(Context context) {
        CharSequence loadLabel = context.getApplicationInfo().loadLabel(context.getPackageManager());
        if (TextUtils.isEmpty(loadLabel)) {
            return null;
        }
        return loadLabel.toString();
    }
}
