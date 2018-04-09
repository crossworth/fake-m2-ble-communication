package com.umeng.socialize;

import android.text.TextUtils;
import com.umeng.facebook.share.internal.ShareConstants;
import com.umeng.socialize.bean.SHARE_MEDIA;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class PlatformConfig {
    public static Map<SHARE_MEDIA, Platform> configs = new HashMap();

    public interface Platform {
        SHARE_MEDIA getName();

        boolean isConfigured();

        void parse(JSONObject jSONObject);
    }

    public static class Alipay implements Platform {
        public static final String Name = "alipay";
        public String id = null;

        public SHARE_MEDIA getName() {
            return SHARE_MEDIA.ALIPAY;
        }

        public void parse(JSONObject json) {
            this.id = json.optString(ShareConstants.WEB_DIALOG_PARAM_ID);
        }

        public boolean isConfigured() {
            return !TextUtils.isEmpty(this.id);
        }
    }

    public static class CustomPlatform implements Platform {
        public static final String Name = "g+";
        public String appId = null;
        public String appkey = null;
        private SHARE_MEDIA f4884p;

        public CustomPlatform(SHARE_MEDIA p) {
            this.f4884p = p;
        }

        public SHARE_MEDIA getName() {
            return this.f4884p;
        }

        public void parse(JSONObject json) {
        }

        public boolean isConfigured() {
            return true;
        }
    }

    public static class Facebook implements Platform {
        public SHARE_MEDIA getName() {
            return SHARE_MEDIA.FACEBOOK;
        }

        public void parse(JSONObject json) {
        }

        public boolean isConfigured() {
            return false;
        }
    }

    public static class Kakao implements Platform {
        public static final String Name = "kakao";
        public String id = null;

        public SHARE_MEDIA getName() {
            return SHARE_MEDIA.KAKAO;
        }

        public void parse(JSONObject json) {
            this.id = json.optString(ShareConstants.WEB_DIALOG_PARAM_ID);
        }

        public boolean isConfigured() {
            return !TextUtils.isEmpty(this.id);
        }

        public boolean isAuthrized() {
            return false;
        }
    }

    public static class Laiwang implements Platform {
        public String appSecret = null;
        public String appToken = null;
        private final SHARE_MEDIA media;

        public Laiwang(SHARE_MEDIA media) {
            this.media = media;
        }

        public SHARE_MEDIA getName() {
            return this.media;
        }

        public void parse(JSONObject json) {
        }

        public boolean isConfigured() {
            return (TextUtils.isEmpty(this.appToken) && TextUtils.isEmpty(this.appSecret)) ? false : true;
        }
    }

    public static class Pinterest implements Platform {
        public String appId = null;

        public SHARE_MEDIA getName() {
            return SHARE_MEDIA.PINTEREST;
        }

        public void parse(JSONObject json) {
        }

        public boolean isConfigured() {
            return !TextUtils.isEmpty(this.appId);
        }
    }

    public static class QQZone implements Platform {
        public String appId = null;
        public String appKey = null;
        private final SHARE_MEDIA media;

        public QQZone(SHARE_MEDIA media) {
            this.media = media;
        }

        public SHARE_MEDIA getName() {
            return this.media;
        }

        public void parse(JSONObject json) {
            this.appId = json.optString("key");
            this.appKey = json.optString("secret");
        }

        public boolean isConfigured() {
            return (TextUtils.isEmpty(this.appId) || TextUtils.isEmpty(this.appKey)) ? false : true;
        }
    }

    public static class SinaWeibo implements Platform {
        public String appKey = null;
        public String appSecret = null;

        public SHARE_MEDIA getName() {
            return SHARE_MEDIA.SINA;
        }

        public void parse(JSONObject json) {
            this.appKey = json.optString("key");
            this.appSecret = json.optString("secret");
        }

        public boolean isConfigured() {
            return (TextUtils.isEmpty(this.appKey) || TextUtils.isEmpty(this.appSecret)) ? false : true;
        }
    }

    public static class Twitter implements Platform {
        public String appKey = null;
        public String appSecret = null;
        private final SHARE_MEDIA media;

        public Twitter(SHARE_MEDIA media) {
            this.media = media;
        }

        public SHARE_MEDIA getName() {
            return this.media;
        }

        public void parse(JSONObject json) {
            this.appKey = json.optString("key");
            this.appSecret = json.optString("secret");
        }

        public boolean isConfigured() {
            return (TextUtils.isEmpty(this.appSecret) || TextUtils.isEmpty(this.appKey)) ? false : true;
        }
    }

    public static class Weixin implements Platform {
        public String appId = null;
        public String appSecret = null;
        private final SHARE_MEDIA media;

        public SHARE_MEDIA getName() {
            return this.media;
        }

        public Weixin(SHARE_MEDIA media) {
            this.media = media;
        }

        public void parse(JSONObject json) {
        }

        public boolean isConfigured() {
            return (TextUtils.isEmpty(this.appId) || TextUtils.isEmpty(this.appSecret)) ? false : true;
        }
    }

    public static class Yixin implements Platform {
        private final SHARE_MEDIA media;
        public String yixinId = null;

        public Yixin(SHARE_MEDIA media) {
            this.media = media;
        }

        public SHARE_MEDIA getName() {
            return this.media;
        }

        public void parse(JSONObject json) {
        }

        public boolean isConfigured() {
            return !TextUtils.isEmpty(this.yixinId);
        }
    }

    static {
        configs.put(SHARE_MEDIA.QQ, new QQZone(SHARE_MEDIA.QQ));
        configs.put(SHARE_MEDIA.QZONE, new QQZone(SHARE_MEDIA.QZONE));
        configs.put(SHARE_MEDIA.WEIXIN, new Weixin(SHARE_MEDIA.WEIXIN));
        configs.put(SHARE_MEDIA.WEIXIN_CIRCLE, new Weixin(SHARE_MEDIA.WEIXIN_CIRCLE));
        configs.put(SHARE_MEDIA.WEIXIN_FAVORITE, new Weixin(SHARE_MEDIA.WEIXIN_FAVORITE));
        configs.put(SHARE_MEDIA.DOUBAN, new CustomPlatform(SHARE_MEDIA.DOUBAN));
        configs.put(SHARE_MEDIA.LAIWANG, new Laiwang(SHARE_MEDIA.LAIWANG));
        configs.put(SHARE_MEDIA.LAIWANG_DYNAMIC, new Laiwang(SHARE_MEDIA.LAIWANG_DYNAMIC));
        configs.put(SHARE_MEDIA.YIXIN, new Yixin(SHARE_MEDIA.YIXIN));
        configs.put(SHARE_MEDIA.YIXIN_CIRCLE, new Yixin(SHARE_MEDIA.YIXIN_CIRCLE));
        configs.put(SHARE_MEDIA.SINA, new SinaWeibo());
        configs.put(SHARE_MEDIA.TENCENT, new QQZone(SHARE_MEDIA.TENCENT));
        configs.put(SHARE_MEDIA.ALIPAY, new Alipay());
        configs.put(SHARE_MEDIA.RENREN, new CustomPlatform(SHARE_MEDIA.RENREN));
        configs.put(SHARE_MEDIA.GOOGLEPLUS, new CustomPlatform(SHARE_MEDIA.GOOGLEPLUS));
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
        configs.put(SHARE_MEDIA.KAKAO, new Kakao());
        configs.put(SHARE_MEDIA.INSTAGRAM, new CustomPlatform(SHARE_MEDIA.INSTAGRAM));
        configs.put(SHARE_MEDIA.MORE, new CustomPlatform(SHARE_MEDIA.MORE));
    }

    public static void setQQZone(String id, String key) {
        QQZone qzone = (QQZone) configs.get(SHARE_MEDIA.QZONE);
        qzone.appId = id;
        qzone.appKey = key;
        QQZone qq = (QQZone) configs.get(SHARE_MEDIA.QQ);
        qq.appId = id;
        qq.appKey = key;
        QQZone tencent = (QQZone) configs.get(SHARE_MEDIA.TENCENT);
        tencent.appId = id;
        tencent.appKey = key;
    }

    public static void setTwitter(String key, String secret) {
        Twitter twitter = (Twitter) configs.get(SHARE_MEDIA.TWITTER);
        twitter.appKey = key;
        twitter.appSecret = secret;
    }

    public static void setAlipay(String id) {
        ((Alipay) configs.get(SHARE_MEDIA.ALIPAY)).id = id;
    }

    public static void setSinaWeibo(String key, String secret) {
        SinaWeibo weibo = (SinaWeibo) configs.get(SHARE_MEDIA.SINA);
        weibo.appKey = key;
        weibo.appSecret = secret;
    }

    public static void setWeixin(String id, String secret) {
        Weixin weixin = (Weixin) configs.get(SHARE_MEDIA.WEIXIN);
        weixin.appId = id;
        weixin.appSecret = secret;
        Weixin circle = (Weixin) configs.get(SHARE_MEDIA.WEIXIN_CIRCLE);
        circle.appId = id;
        circle.appSecret = secret;
        Weixin favorite = (Weixin) configs.get(SHARE_MEDIA.WEIXIN_FAVORITE);
        favorite.appId = id;
        favorite.appSecret = secret;
    }

    public static void setLaiwang(String token, String secret) {
        Laiwang laiwang = (Laiwang) configs.get(SHARE_MEDIA.LAIWANG);
        laiwang.appToken = token;
        laiwang.appSecret = secret;
        Laiwang dynamic = (Laiwang) configs.get(SHARE_MEDIA.LAIWANG_DYNAMIC);
        dynamic.appToken = token;
        dynamic.appSecret = secret;
    }

    public static void setYixin(String id) {
        ((Yixin) configs.get(SHARE_MEDIA.YIXIN)).yixinId = id;
        ((Yixin) configs.get(SHARE_MEDIA.YIXIN_CIRCLE)).yixinId = id;
    }

    public static void setPinterest(String id) {
        ((Pinterest) configs.get(SHARE_MEDIA.PINTEREST)).appId = id;
    }

    public static void setKakao(String id) {
        ((Kakao) configs.get(SHARE_MEDIA.KAKAO)).id = id;
    }

    public static Platform getPlatform(SHARE_MEDIA name) {
        return (Platform) configs.get(name);
    }
}
