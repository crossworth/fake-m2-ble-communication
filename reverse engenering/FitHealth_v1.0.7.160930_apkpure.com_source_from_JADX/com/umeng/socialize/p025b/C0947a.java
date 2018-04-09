package com.umeng.socialize.p025b;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Pair;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.PlatformConfig.Platform;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.HandlerRequestCode;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.handler.UMSSOHandler;
import com.umeng.socialize.net.RestAPI;
import com.umeng.socialize.net.UrlRequest;
import com.umeng.socialize.net.UrlResponse;
import com.umeng.socialize.p024a.C0944c;
import com.umeng.socialize.utils.Log;
import com.umeng.socialize.view.UMFriendListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: SocialRouter */
public final class C0947a {
    private int f3238a = 0;
    private final Map<SHARE_MEDIA, UMSSOHandler> f3239b = new HashMap();
    private final List<Pair<SHARE_MEDIA, String>> f3240c = new ArrayList();
    private C0946a f3241d;
    private Context f3242e;

    /* compiled from: SocialRouter */
    static class C0946a {
        private Map<SHARE_MEDIA, UMSSOHandler> f3237a;

        public C0946a(Map<SHARE_MEDIA, UMSSOHandler> map) {
            this.f3237a = map;
        }

        public boolean m3181a(Context context, SHARE_MEDIA share_media) {
            if (!m3178a(context)) {
                return false;
            }
            if (!m3179a(share_media)) {
                return false;
            }
            if (((UMSSOHandler) this.f3237a.get(share_media)).isSupportAuth()) {
                return true;
            }
            Log.m3259w(share_media.toString() + "平台不支持授权,无法完成操作");
            return false;
        }

        public boolean m3180a(Activity activity, ShareAction shareAction) {
            if (!m3178a((Context) activity)) {
                return false;
            }
            SHARE_MEDIA platform = shareAction.getPlatform();
            if (platform == null || !m3179a(platform)) {
                return false;
            }
            return true;
        }

        private boolean m3178a(Context context) {
            if (context != null) {
                return true;
            }
            Log.m3250e("Context is null");
            return false;
        }

        private boolean m3179a(SHARE_MEDIA share_media) {
            Platform platform = (Platform) PlatformConfig.configs.get(share_media);
            if (platform != null && !platform.isConfigured()) {
                Log.m3250e(share_media + ": 没有配置相关的Appkey、Secret");
                return false;
            } else if (((UMSSOHandler) this.f3237a.get(share_media)) != null) {
                return true;
            } else {
                Log.m3250e("没有配置 " + share_media + " 的jar包");
                return false;
            }
        }
    }

    public C0947a(Context context) {
        List list = this.f3240c;
        String str = "com.umeng.socialize.handler.";
        list.add(new Pair(SHARE_MEDIA.LAIWANG, "com.umeng.socialize.handler.UMLWHandler"));
        list.add(new Pair(SHARE_MEDIA.LAIWANG_DYNAMIC, "com.umeng.socialize.handler.UMLWHandler"));
        list.add(new Pair(SHARE_MEDIA.SINA, "com.umeng.socialize.handler.SinaSsoHandler"));
        list.add(new Pair(SHARE_MEDIA.PINTEREST, "com.umeng.socialize.handler.UMPinterestHandler"));
        list.add(new Pair(SHARE_MEDIA.QZONE, "com.umeng.socialize.handler.QZoneSsoHandler"));
        list.add(new Pair(SHARE_MEDIA.QQ, "com.umeng.socialize.handler.UMQQSsoHandler"));
        list.add(new Pair(SHARE_MEDIA.RENREN, "com.umeng.socialize.handler.RenrenSsoHandler"));
        list.add(new Pair(SHARE_MEDIA.TENCENT, "com.umeng.socialize.handler.QQwbHandler"));
        list.add(new Pair(SHARE_MEDIA.WEIXIN, "com.umeng.socialize.handler.UMWXHandler"));
        list.add(new Pair(SHARE_MEDIA.WEIXIN_CIRCLE, "com.umeng.socialize.handler.UMWXHandler"));
        list.add(new Pair(SHARE_MEDIA.WEIXIN_FAVORITE, "com.umeng.socialize.handler.UMWXHandler"));
        list.add(new Pair(SHARE_MEDIA.YIXIN, "com.umeng.socialize.handler.UMYXHandler"));
        list.add(new Pair(SHARE_MEDIA.YIXIN_CIRCLE, "com.umeng.socialize.handler.UMYXHandler"));
        list.add(new Pair(SHARE_MEDIA.EMAIL, "com.umeng.socialize.handler.EmailHandler"));
        list.add(new Pair(SHARE_MEDIA.EVERNOTE, "com.umeng.socialize.handler.UMEvernoteHandler"));
        list.add(new Pair(SHARE_MEDIA.FACEBOOK, "com.umeng.socialize.handler.UMFacebookHandler"));
        list.add(new Pair(SHARE_MEDIA.FLICKR, "com.umeng.socialize.handler.UMFlickrHandler"));
        list.add(new Pair(SHARE_MEDIA.FOURSQUARE, "com.umeng.socialize.handler.UMFourSquareHandler"));
        list.add(new Pair(SHARE_MEDIA.GOOGLEPLUS, "com.umeng.socialize.handler.UMGooglePlusHandler"));
        list.add(new Pair(SHARE_MEDIA.INSTAGRAM, "com.umeng.socialize.handler.UMInstagramHandler"));
        list.add(new Pair(SHARE_MEDIA.KAKAO, "com.umeng.socialize.handler.UMKakaoHandler"));
        list.add(new Pair(SHARE_MEDIA.LINE, "com.umeng.socialize.handler.UMLineHandler"));
        list.add(new Pair(SHARE_MEDIA.LINKEDIN, "com.umeng.socialize.handler.UMLinkedInHandler"));
        list.add(new Pair(SHARE_MEDIA.POCKET, "com.umeng.socialize.handler.UMPocketHandler"));
        list.add(new Pair(SHARE_MEDIA.WHATSAPP, "com.umeng.socialize.handler.UMWhatsAppHandler"));
        list.add(new Pair(SHARE_MEDIA.YNOTE, "com.umeng.socialize.handler.UMYNoteHandler"));
        list.add(new Pair(SHARE_MEDIA.SMS, "com.umeng.socialize.handler.SmsHandler"));
        list.add(new Pair(SHARE_MEDIA.DOUBAN, "com.umeng.socialize.handler.DoubanHandler"));
        list.add(new Pair(SHARE_MEDIA.TUMBLR, "com.umeng.socialize.handler.UMTumblrHandler"));
        list.add(new Pair(SHARE_MEDIA.TWITTER, "com.umeng.socialize.handler.TwitterHandler"));
        list.add(new Pair(SHARE_MEDIA.ALIPAY, "com.umeng.socialize.handler.AlipayHandler"));
        this.f3241d = new C0946a(this.f3239b);
        this.f3242e = null;
        this.f3242e = context;
        m3184a();
    }

    private void m3184a() {
        for (Pair pair : this.f3240c) {
            Object obj;
            if (pair.first == SHARE_MEDIA.WEIXIN_CIRCLE || pair.first == SHARE_MEDIA.WEIXIN_FAVORITE) {
                obj = (UMSSOHandler) this.f3239b.get(SHARE_MEDIA.WEIXIN);
            } else if (pair.first == SHARE_MEDIA.YIXIN_CIRCLE) {
                r1 = (UMSSOHandler) this.f3239b.get(SHARE_MEDIA.YIXIN);
            } else if (pair.first == SHARE_MEDIA.LAIWANG_DYNAMIC) {
                r1 = (UMSSOHandler) this.f3239b.get(SHARE_MEDIA.LAIWANG);
            } else if (pair.first != SHARE_MEDIA.TENCENT) {
                obj = m3183a((String) pair.second);
            } else if (Config.WBBYQQ) {
                obj = m3183a((String) pair.second);
            } else {
                obj = m3183a("com.umeng.socialize.handler.TencentWBSsoHandler");
            }
            this.f3239b.put(pair.first, obj);
        }
    }

    private UMSSOHandler m3183a(String str) {
        try {
            return (UMSSOHandler) Class.forName(str).newInstance();
        } catch (Exception e) {
            Log.m3257v("xxxx", "ignore=" + e);
            return null;
        }
    }

    public UMSSOHandler m3186a(SHARE_MEDIA share_media) {
        UMSSOHandler uMSSOHandler = (UMSSOHandler) this.f3239b.get(share_media);
        if (uMSSOHandler != null) {
            uMSSOHandler.onCreate(this.f3242e, PlatformConfig.getPlatform(share_media));
        }
        return uMSSOHandler;
    }

    public void m3187a(int i, int i2, Intent intent) {
        UMSSOHandler a = m3182a(i);
        if (a != null) {
            a.onActivityResult(i, i2, intent);
        }
    }

    private UMSSOHandler m3182a(int i) {
        int i2 = 10103;
        if (!(i == 10103 || i == 11101)) {
            i2 = i;
        }
        if (i == HandlerRequestCode.FACEBOOK_REQUEST_SHARE_CODE || i == HandlerRequestCode.FACEBOOK_REQUEST_AUTH_CODE) {
            i2 = 64206;
        }
        int i3;
        if (i == HandlerRequestCode.SINA_AUTH_REQUEST_CODE || i == 765) {
            i3 = 5659;
        } else {
            i3 = i2;
        }
        for (UMSSOHandler uMSSOHandler : this.f3239b.values()) {
            if (uMSSOHandler != null && r1 == uMSSOHandler.getRequestCode()) {
                return uMSSOHandler;
            }
        }
        return null;
    }

    public void m3191a(SHARE_MEDIA share_media, UMSSOHandler uMSSOHandler) {
        if (share_media == null || uMSSOHandler == null) {
            Log.m3250e("SHARE_MEDIA or UMSSOHandler is null");
        } else {
            this.f3239b.put(share_media, uMSSOHandler);
        }
    }

    public void m3189a(Activity activity, SHARE_MEDIA share_media, UMAuthListener uMAuthListener) {
        if (this.f3241d.m3181a((Context) activity, share_media)) {
            if (uMAuthListener == null) {
                uMAuthListener = new C1799b(this);
            }
            ((UMSSOHandler) this.f3239b.get(share_media)).onCreate(activity, PlatformConfig.getPlatform(share_media));
            ((UMSSOHandler) this.f3239b.get(share_media)).deleteAuth(activity, uMAuthListener);
        }
    }

    public void m3193b(Activity activity, SHARE_MEDIA share_media, UMAuthListener uMAuthListener) {
        if (this.f3241d.m3181a((Context) activity, share_media)) {
            if (uMAuthListener == null) {
                uMAuthListener = new C1800c(this);
            }
            ((UMSSOHandler) this.f3239b.get(share_media)).onCreate(activity, PlatformConfig.getPlatform(share_media));
            ((UMSSOHandler) this.f3239b.get(share_media)).getPlatformInfo(activity, uMAuthListener);
        }
    }

    public void m3190a(Activity activity, SHARE_MEDIA share_media, UMFriendListener uMFriendListener) {
        if (this.f3241d.m3181a((Context) activity, share_media)) {
            if (uMFriendListener == null) {
                uMFriendListener = new C1801d(this);
            }
            ((UMSSOHandler) this.f3239b.get(share_media)).onCreate(activity, PlatformConfig.getPlatform(share_media));
            ((UMSSOHandler) this.f3239b.get(share_media)).getfriend(activity, uMFriendListener);
        }
    }

    public boolean m3192a(Activity activity, SHARE_MEDIA share_media) {
        if (!this.f3241d.m3181a((Context) activity, share_media)) {
            return false;
        }
        ((UMSSOHandler) this.f3239b.get(share_media)).onCreate(activity, PlatformConfig.getPlatform(share_media));
        return ((UMSSOHandler) this.f3239b.get(share_media)).isInstall(activity);
    }

    public boolean m3194b(Activity activity, SHARE_MEDIA share_media) {
        if (!this.f3241d.m3181a((Context) activity, share_media)) {
            return false;
        }
        ((UMSSOHandler) this.f3239b.get(share_media)).onCreate(activity, PlatformConfig.getPlatform(share_media));
        return ((UMSSOHandler) this.f3239b.get(share_media)).isAuthorize(activity);
    }

    public void m3195c(Activity activity, SHARE_MEDIA share_media, UMAuthListener uMAuthListener) {
        if (this.f3241d.m3181a((Context) activity, share_media)) {
            UMSSOHandler uMSSOHandler = (UMSSOHandler) this.f3239b.get(share_media);
            uMSSOHandler.onCreate(activity, PlatformConfig.getPlatform(share_media));
            uMSSOHandler.authorize(activity, uMAuthListener);
        }
    }

    public void m3188a(Activity activity, ShareAction shareAction, UMShareListener uMShareListener) {
        if (this.f3241d.m3180a(activity, shareAction)) {
            if (uMShareListener == null) {
                uMShareListener = new C1802e(this);
            }
            SHARE_MEDIA platform = shareAction.getPlatform();
            UMSSOHandler uMSSOHandler = (UMSSOHandler) this.f3239b.get(platform);
            uMSSOHandler.setCaller(shareAction.getFrom());
            uMSSOHandler.onCreate(activity, PlatformConfig.getPlatform(platform));
            if (!(platform.toString().equals("TENCENT") || platform.toString().equals("RENREN") || platform.toString().equals("DOUBAN"))) {
                if (platform.toString().equals("WEIXIN")) {
                    C0944c.m3174a(activity, "wxsession", shareAction.getShareContent().mText, shareAction.getShareContent().mMedia);
                } else if (platform.toString().equals("WEIXIN_CIRCLE")) {
                    C0944c.m3174a(activity, "wxtimeline", shareAction.getShareContent().mText, shareAction.getShareContent().mMedia);
                } else if (platform.toString().equals("WEIXIN_FAVORITE")) {
                    C0944c.m3174a(activity, "wxfavorite", shareAction.getShareContent().mText, shareAction.getShareContent().mMedia);
                } else {
                    C0944c.m3174a(activity, platform.toString().toLowerCase(), shareAction.getShareContent().mText, shareAction.getShareContent().mMedia);
                }
            }
            if (platform.toString().equals("TENCENT") && Config.WBBYQQ) {
                C0944c.m3174a(activity, platform.toString().toLowerCase(), shareAction.getShareContent().mText, shareAction.getShareContent().mMedia);
            }
            if (Config.isloadUrl) {
                m3185a(activity, shareAction);
            }
            uMSSOHandler.share(activity, shareAction.getShareContent(), uMShareListener);
        }
    }

    private void m3185a(Activity activity, ShareAction shareAction) {
        Object obj = shareAction.getShareContent().mTargetUrl;
        if (!TextUtils.isEmpty(obj)) {
            String str;
            if (shareAction.getPlatform().toString().equals("WEIXIN")) {
                str = "wxsession";
            } else if (shareAction.getPlatform().toString().equals("")) {
                str = "wxtimeline";
            } else {
                str = shareAction.getPlatform().toString().toLowerCase();
            }
            UrlResponse uploadUrl = RestAPI.uploadUrl(new UrlRequest(activity, str, obj));
            Log.m3250e("xxxxxx resp" + uploadUrl);
            if (uploadUrl == null || uploadUrl.mStCode != 200) {
                Log.m3250e("upload url fail ");
            } else {
                shareAction.withTargetUrl(uploadUrl.result);
            }
        }
    }
}
