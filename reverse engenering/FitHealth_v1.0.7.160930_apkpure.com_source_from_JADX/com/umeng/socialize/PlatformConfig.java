package com.umeng.socialize;

import android.content.Context;
import android.text.TextUtils;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.net.GetPlatformKeyRequest;
import com.umeng.socialize.net.GetPlatformKeyResponse;
import com.umeng.socialize.net.RestAPI;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONObject;
import p031u.aly.au;

public class PlatformConfig {
    public static Map<SHARE_MEDIA, Platform> configs = new HashMap();

    public interface Platform {
        SHARE_MEDIA getName();

        boolean isAuthrized();

        boolean isConfigured();

        void parse(JSONObject jSONObject);
    }

    public static class Alipay implements Platform {
        public static final String Name = "alipay";
        public String id = null;

        public SHARE_MEDIA getName() {
            return SHARE_MEDIA.ALIPAY;
        }

        public void parse(JSONObject jSONObject) {
            this.id = jSONObject.optString("id");
        }

        public boolean isConfigured() {
            return !TextUtils.isEmpty(this.id);
        }

        public boolean isAuthrized() {
            return false;
        }
    }

    public static class CustomPlatform implements Platform {
        public static final String Name = "g+";
        public String appId = null;
        public String appSecret = null;
        public String appkey = null;
        private SHARE_MEDIA f4814p;

        public CustomPlatform(SHARE_MEDIA share_media) {
            this.f4814p = share_media;
        }

        public SHARE_MEDIA getName() {
            return this.f4814p;
        }

        public void parse(JSONObject jSONObject) {
        }

        public boolean isConfigured() {
            return true;
        }

        public boolean isAuthrized() {
            return false;
        }
    }

    public static class Douban implements Platform {
        public String appKey = null;
        public String appSecret = null;

        public SHARE_MEDIA getName() {
            return SHARE_MEDIA.DOUBAN;
        }

        public void parse(JSONObject jSONObject) {
            this.appKey = jSONObject.optString("key");
            this.appSecret = jSONObject.optString(au.f3574c);
        }

        public boolean isConfigured() {
            return true;
        }

        public boolean isAuthrized() {
            return false;
        }
    }

    public static class Facebook implements Platform {
        public SHARE_MEDIA getName() {
            return SHARE_MEDIA.FACEBOOK;
        }

        public void parse(JSONObject jSONObject) {
        }

        public boolean isConfigured() {
            return false;
        }

        public boolean isAuthrized() {
            return false;
        }
    }

    public static class GooglePlus implements Platform {
        public static final String Name = "g+";
        public String appId = null;
        public String appSecret = null;
        public String appkey = null;

        public SHARE_MEDIA getName() {
            return SHARE_MEDIA.GOOGLEPLUS;
        }

        public void parse(JSONObject jSONObject) {
        }

        public boolean isConfigured() {
            return true;
        }

        public boolean isAuthrized() {
            return false;
        }
    }

    public static class Laiwang implements Platform {
        public String appSecret = null;
        public String appToken = null;
        private final SHARE_MEDIA media;

        public Laiwang(SHARE_MEDIA share_media) {
            this.media = share_media;
        }

        public SHARE_MEDIA getName() {
            return this.media;
        }

        public void parse(JSONObject jSONObject) {
        }

        public boolean isConfigured() {
            return (TextUtils.isEmpty(this.appToken) && TextUtils.isEmpty(this.appSecret)) ? false : true;
        }

        public boolean isAuthrized() {
            return false;
        }
    }

    public static class Pinterest implements Platform {
        public String appId = null;

        public SHARE_MEDIA getName() {
            return SHARE_MEDIA.PINTEREST;
        }

        public void parse(JSONObject jSONObject) {
        }

        public boolean isConfigured() {
            return !TextUtils.isEmpty(this.appId);
        }

        public boolean isAuthrized() {
            return false;
        }
    }

    public static class QQZone implements Platform {
        public String appId = null;
        public String appKey = null;
        private final SHARE_MEDIA media;

        public QQZone(SHARE_MEDIA share_media) {
            this.media = share_media;
        }

        public SHARE_MEDIA getName() {
            return this.media;
        }

        public void parse(JSONObject jSONObject) {
            this.appId = jSONObject.optString("key");
            this.appKey = jSONObject.optString(au.f3574c);
        }

        public boolean isConfigured() {
            return (TextUtils.isEmpty(this.appId) || TextUtils.isEmpty(this.appKey)) ? false : true;
        }

        public boolean isAuthrized() {
            return false;
        }
    }

    public static class Renren implements Platform {
        public static final String Name = "renren";
        public String appId = "201874";
        public String appSecret = "3bf66e42db1e4fa9829b955cc300b737";
        public String appkey = "28401c0964f04a72a14c812d6132fcef";

        public SHARE_MEDIA getName() {
            return SHARE_MEDIA.RENREN;
        }

        public void parse(JSONObject jSONObject) {
            this.appId = jSONObject.optString("id");
            this.appkey = jSONObject.optString("key");
            this.appSecret = jSONObject.optString(au.f3574c);
        }

        public boolean isConfigured() {
            boolean z;
            if (TextUtils.isEmpty(this.appkey)) {
                z = false;
            } else {
                z = true;
            }
            boolean z2;
            if (TextUtils.isEmpty(this.appSecret)) {
                z2 = false;
            } else {
                z2 = true;
            }
            boolean z3;
            if (TextUtils.isEmpty(this.appId)) {
                z3 = false;
            } else {
                z3 = true;
            }
            if (z && r3 && r4) {
                return true;
            }
            return false;
        }

        public boolean isAuthrized() {
            return false;
        }
    }

    public static class SinaWeibo implements Platform {
        public String appKey = null;
        public String appSecret = null;

        public SHARE_MEDIA getName() {
            return SHARE_MEDIA.SINA;
        }

        public void parse(JSONObject jSONObject) {
            this.appKey = jSONObject.optString("key");
            this.appSecret = jSONObject.optString(au.f3574c);
        }

        public boolean isConfigured() {
            return (TextUtils.isEmpty(this.appKey) || TextUtils.isEmpty(this.appSecret)) ? false : true;
        }

        public boolean isAuthrized() {
            return false;
        }
    }

    public static class TencentWeibo implements Platform {
        public static final String Name = "tencent";
        public String appKey = null;
        public String appSecret = null;

        public SHARE_MEDIA getName() {
            return SHARE_MEDIA.TENCENT;
        }

        public void parse(JSONObject jSONObject) {
            this.appKey = jSONObject.optString("key");
            this.appSecret = jSONObject.optString(au.f3574c);
        }

        public boolean isConfigured() {
            return true;
        }

        public boolean isAuthrized() {
            return false;
        }
    }

    public static class Twitter implements Platform {
        public String appKey = null;
        public String appSecret = null;
        private final SHARE_MEDIA media;

        public Twitter(SHARE_MEDIA share_media) {
            this.media = share_media;
        }

        public SHARE_MEDIA getName() {
            return this.media;
        }

        public void parse(JSONObject jSONObject) {
            this.appKey = jSONObject.optString("key");
            this.appSecret = jSONObject.optString(au.f3574c);
        }

        public boolean isConfigured() {
            return (TextUtils.isEmpty(this.appSecret) || TextUtils.isEmpty(this.appKey)) ? false : true;
        }

        public boolean isAuthrized() {
            return false;
        }
    }

    public static class Weixin implements Platform {
        public String appId = null;
        public String appSecret = null;
        private final SHARE_MEDIA media;

        public SHARE_MEDIA getName() {
            return this.media;
        }

        public Weixin(SHARE_MEDIA share_media) {
            this.media = share_media;
        }

        public void parse(JSONObject jSONObject) {
        }

        public boolean isConfigured() {
            return (TextUtils.isEmpty(this.appId) || TextUtils.isEmpty(this.appSecret)) ? false : true;
        }

        public boolean isAuthrized() {
            return false;
        }
    }

    public static class Yixin implements Platform {
        private final SHARE_MEDIA media;
        public String yixinId = null;

        public Yixin(SHARE_MEDIA share_media) {
            this.media = share_media;
        }

        public SHARE_MEDIA getName() {
            return this.media;
        }

        public void parse(JSONObject jSONObject) {
        }

        public boolean isConfigured() {
            return !TextUtils.isEmpty(this.yixinId);
        }

        public boolean isAuthrized() {
            return false;
        }
    }

    static {
        configs.put(SHARE_MEDIA.QQ, new QQZone(SHARE_MEDIA.QQ));
        configs.put(SHARE_MEDIA.QZONE, new QQZone(SHARE_MEDIA.QZONE));
        configs.put(SHARE_MEDIA.WEIXIN, new Weixin(SHARE_MEDIA.WEIXIN));
        configs.put(SHARE_MEDIA.WEIXIN_CIRCLE, new Weixin(SHARE_MEDIA.WEIXIN_CIRCLE));
        configs.put(SHARE_MEDIA.WEIXIN_FAVORITE, new Weixin(SHARE_MEDIA.WEIXIN_FAVORITE));
        configs.put(SHARE_MEDIA.DOUBAN, new Douban());
        configs.put(SHARE_MEDIA.LAIWANG, new Laiwang(SHARE_MEDIA.LAIWANG));
        configs.put(SHARE_MEDIA.LAIWANG_DYNAMIC, new Laiwang(SHARE_MEDIA.LAIWANG_DYNAMIC));
        configs.put(SHARE_MEDIA.YIXIN, new Yixin(SHARE_MEDIA.YIXIN));
        configs.put(SHARE_MEDIA.YIXIN_CIRCLE, new Yixin(SHARE_MEDIA.YIXIN_CIRCLE));
        configs.put(SHARE_MEDIA.SINA, new SinaWeibo());
        configs.put(SHARE_MEDIA.TENCENT, new QQZone(SHARE_MEDIA.TENCENT));
        configs.put(SHARE_MEDIA.ALIPAY, new Alipay());
        configs.put(SHARE_MEDIA.RENREN, new Renren());
        configs.put(SHARE_MEDIA.GOOGLEPLUS, new GooglePlus());
        configs.put(SHARE_MEDIA.FACEBOOK, new CustomPlatform(SHARE_MEDIA.FACEBOOK));
        configs.put(SHARE_MEDIA.TWITTER, new Twitter(SHARE_MEDIA.TWITTER));
        configs.put(SHARE_MEDIA.TUMBLR, new CustomPlatform(SHARE_MEDIA.TUMBLR));
        configs.put(SHARE_MEDIA.PINTEREST, new Pinterest());
        configs.put(SHARE_MEDIA.POCKET, new CustomPlatform(SHARE_MEDIA.POCKET));
        configs.put(SHARE_MEDIA.WHATSAPP, new CustomPlatform(SHARE_MEDIA.WHATSAPP));
        configs.put(SHARE_MEDIA.EMAIL, new CustomPlatform(SHARE_MEDIA.EMAIL));
        configs.put(SHARE_MEDIA.SMS, new CustomPlatform(SHARE_MEDIA.SMS));
        configs.put(SHARE_MEDIA.LINKEDIN, new CustomPlatform(SHARE_MEDIA.LINKEDIN));
        configs.put(SHARE_MEDIA.LINE, new CustomPlatform(SHARE_MEDIA.LINE));
        configs.put(SHARE_MEDIA.FLICKR, new CustomPlatform(SHARE_MEDIA.FLICKR));
        configs.put(SHARE_MEDIA.EVERNOTE, new CustomPlatform(SHARE_MEDIA.EVERNOTE));
        configs.put(SHARE_MEDIA.FOURSQUARE, new CustomPlatform(SHARE_MEDIA.FOURSQUARE));
        configs.put(SHARE_MEDIA.YNOTE, new CustomPlatform(SHARE_MEDIA.YNOTE));
        configs.put(SHARE_MEDIA.KAKAO, new CustomPlatform(SHARE_MEDIA.KAKAO));
        configs.put(SHARE_MEDIA.INSTAGRAM, new CustomPlatform(SHARE_MEDIA.INSTAGRAM));
    }

    public static void setQQZone(String str, String str2) {
        QQZone qQZone = (QQZone) configs.get(SHARE_MEDIA.QZONE);
        qQZone.appId = str;
        qQZone.appKey = str2;
        qQZone = (QQZone) configs.get(SHARE_MEDIA.QQ);
        qQZone.appId = str;
        qQZone.appKey = str2;
        qQZone = (QQZone) configs.get(SHARE_MEDIA.TENCENT);
        qQZone.appId = str;
        qQZone.appKey = str2;
    }

    public static void setTwitter(String str, String str2) {
        Twitter twitter = (Twitter) configs.get(SHARE_MEDIA.TWITTER);
        twitter.appKey = str;
        twitter.appSecret = str2;
    }

    public static void setAlipay(String str) {
        ((Alipay) configs.get(SHARE_MEDIA.ALIPAY)).id = str;
    }

    public static void setTencentWB(String str, String str2) {
        TencentWeibo tencentWeibo = (TencentWeibo) configs.get(SHARE_MEDIA.TENCENT);
        tencentWeibo.appKey = str;
        tencentWeibo.appSecret = str2;
    }

    public static void setSinaWeibo(String str, String str2) {
        SinaWeibo sinaWeibo = (SinaWeibo) configs.get(SHARE_MEDIA.SINA);
        sinaWeibo.appKey = str;
        sinaWeibo.appSecret = str2;
    }

    public static void setTencentWeibo(String str, String str2) {
        TencentWeibo tencentWeibo = (TencentWeibo) configs.get(SHARE_MEDIA.TENCENT);
        tencentWeibo.appKey = str;
        tencentWeibo.appSecret = str2;
    }

    private void m3166a(String str, String str2, String str3) {
        Renren renren = (Renren) configs.get(SHARE_MEDIA.RENREN);
        renren.appId = str;
        renren.appkey = str2;
        renren.appSecret = str3;
    }

    private static void m3165a(String str, String str2) {
        Douban douban = (Douban) configs.get(SHARE_MEDIA.DOUBAN);
        douban.appKey = str;
        douban.appSecret = str2;
    }

    public static void setWeixin(String str, String str2) {
        Weixin weixin = (Weixin) configs.get(SHARE_MEDIA.WEIXIN);
        weixin.appId = str;
        weixin.appSecret = str2;
        weixin = (Weixin) configs.get(SHARE_MEDIA.WEIXIN_CIRCLE);
        weixin.appId = str;
        weixin.appSecret = str2;
        weixin = (Weixin) configs.get(SHARE_MEDIA.WEIXIN_FAVORITE);
        weixin.appId = str;
        weixin.appSecret = str2;
    }

    public static void setLaiwang(String str, String str2) {
        Laiwang laiwang = (Laiwang) configs.get(SHARE_MEDIA.LAIWANG);
        laiwang.appToken = str;
        laiwang.appSecret = str2;
        laiwang = (Laiwang) configs.get(SHARE_MEDIA.LAIWANG_DYNAMIC);
        laiwang.appToken = str;
        laiwang.appSecret = str2;
    }

    public static void setYixin(String str) {
        ((Yixin) configs.get(SHARE_MEDIA.YIXIN)).yixinId = str;
        ((Yixin) configs.get(SHARE_MEDIA.YIXIN_CIRCLE)).yixinId = str;
    }

    public static void setPinterest(String str) {
        ((Pinterest) configs.get(SHARE_MEDIA.PINTEREST)).appId = str;
    }

    public static Platform getPlatform(SHARE_MEDIA share_media) {
        return (Platform) configs.get(share_media);
    }

    public static void updateConfig(Context context) {
        for (Platform isConfigured : configs.values()) {
            if (!isConfigured.isConfigured()) {
                break;
            }
        }
        if (m3167a(context) || !m3168b(context)) {
        }
    }

    private static boolean m3167a(Context context) {
        return false;
    }

    private static boolean m3168b(Context context) {
        GetPlatformKeyResponse queryPlatformKey = RestAPI.queryPlatformKey(new GetPlatformKeyRequest(context));
        if (queryPlatformKey == null || !queryPlatformKey.isOk()) {
            return false;
        }
        JSONObject jsonData = queryPlatformKey.getJsonData();
        try {
            for (Entry entry : configs.entrySet()) {
                ((Platform) entry.getValue()).parse(jsonData.getJSONObject(((SHARE_MEDIA) entry.getKey()).toString()));
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
