package com.umeng.socialize.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Pair;
import android.util.SparseArray;
import com.umeng.facebook.GraphResponse;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.PlatformConfig.Platform;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.analytics.SocialAnalytics;
import com.umeng.socialize.bean.HandlerRequestCode;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.handler.UMMoreHandler;
import com.umeng.socialize.handler.UMSSOHandler;
import com.umeng.socialize.net.RestAPI;
import com.umeng.socialize.net.UrlRequest;
import com.umeng.socialize.net.UrlResponse;
import com.umeng.socialize.utils.ContextUtil;
import com.umeng.socialize.utils.Log;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class SocialRouter {
    private ParamsGuard guard;
    private Context mContext;
    private final Map<SHARE_MEDIA, UMSSOHandler> platformHandlers = new HashMap();
    private final List<Pair<SHARE_MEDIA, String>> supportedPlatform = new ArrayList();

    class C15911 implements UMAuthListener {
        C15911() {
        }

        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> map) {
        }

        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
        }

        public void onCancel(SHARE_MEDIA platform, int action) {
        }
    }

    static class ParamsGuard {
        private Map<SHARE_MEDIA, UMSSOHandler> handlers;

        public ParamsGuard(Map<SHARE_MEDIA, UMSSOHandler> handlers) {
            this.handlers = handlers;
        }

        public boolean auth(Context context, SHARE_MEDIA platform) {
            if (!checkContext(context) || !checkPlatformConfig(platform)) {
                return false;
            }
            if (((UMSSOHandler) this.handlers.get(platform)).isSupportAuth()) {
                return true;
            }
            Log.m4557w(platform.toString() + "平台不支持授权,无法完成操作");
            return false;
        }

        public boolean share(ShareAction action) {
            SHARE_MEDIA platform = action.getPlatform();
            if (platform != null && checkPlatformConfig(platform)) {
                return true;
            }
            return false;
        }

        private boolean checkContext(Context context) {
            if (context != null) {
                return true;
            }
            Log.m4548e("Context is null");
            return false;
        }

        private boolean checkPlatformConfig(SHARE_MEDIA media) {
            Platform platform = (Platform) PlatformConfig.configs.get(media);
            if (platform != null && !platform.isConfigured()) {
                Log.m4548e(media + ": 没有配置相关的Appkey、Secret");
                return false;
            } else if (((UMSSOHandler) this.handlers.get(media)) != null) {
                return true;
            } else {
                Log.m4548e("没有配置 " + media + " 的jar包");
                return false;
            }
        }
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext.getApplicationContext();
    }

    public SocialRouter(Context context) {
        List<Pair<SHARE_MEDIA, String>> sp = this.supportedPlatform;
        String pkg = "com.umeng.socialize.handler.";
        String pkgwx = "com.umeng.weixin.handler.";
        String pkgqq = "com.umeng.qq.handler.";
        sp.add(new Pair(SHARE_MEDIA.LAIWANG, "com.umeng.socialize.handler.UMLWHandler"));
        sp.add(new Pair(SHARE_MEDIA.LAIWANG_DYNAMIC, "com.umeng.socialize.handler.UMLWHandler"));
        sp.add(new Pair(SHARE_MEDIA.SINA, "com.umeng.socialize.handler.UmengSinaHandler"));
        sp.add(new Pair(SHARE_MEDIA.PINTEREST, "com.umeng.socialize.handler.UMPinterestHandler"));
        sp.add(new Pair(SHARE_MEDIA.QZONE, "com.umeng.qq.handler.UmengQZoneHandler"));
        sp.add(new Pair(SHARE_MEDIA.QQ, "com.umeng.qq.handler.UmengQQHandler"));
        sp.add(new Pair(SHARE_MEDIA.RENREN, "com.umeng.socialize.handler.RenrenSsoHandler"));
        sp.add(new Pair(SHARE_MEDIA.TENCENT, "com.umeng.socialize.handler.TencentWBSsoHandler"));
        sp.add(new Pair(SHARE_MEDIA.WEIXIN, "com.umeng.weixin.handler.UmengWXHandler"));
        sp.add(new Pair(SHARE_MEDIA.WEIXIN_CIRCLE, "com.umeng.weixin.handler.UmengWXHandler"));
        sp.add(new Pair(SHARE_MEDIA.WEIXIN_FAVORITE, "com.umeng.weixin.handler.UmengWXHandler"));
        sp.add(new Pair(SHARE_MEDIA.YIXIN, "com.umeng.socialize.handler.UMYXHandler"));
        sp.add(new Pair(SHARE_MEDIA.YIXIN_CIRCLE, "com.umeng.socialize.handler.UMYXHandler"));
        sp.add(new Pair(SHARE_MEDIA.EMAIL, "com.umeng.socialize.handler.EmailHandler"));
        sp.add(new Pair(SHARE_MEDIA.EVERNOTE, "com.umeng.socialize.handler.UMEvernoteHandler"));
        sp.add(new Pair(SHARE_MEDIA.FACEBOOK, "com.umeng.socialize.handler.UMFacebookHandler"));
        sp.add(new Pair(SHARE_MEDIA.FLICKR, "com.umeng.socialize.handler.UMFlickrHandler"));
        sp.add(new Pair(SHARE_MEDIA.FOURSQUARE, "com.umeng.socialize.handler.UMFourSquareHandler"));
        sp.add(new Pair(SHARE_MEDIA.GOOGLEPLUS, "com.umeng.socialize.handler.UMGooglePlusHandler"));
        sp.add(new Pair(SHARE_MEDIA.INSTAGRAM, "com.umeng.socialize.handler.UMInstagramHandler"));
        sp.add(new Pair(SHARE_MEDIA.KAKAO, "com.umeng.socialize.handler.UMKakaoHandler"));
        sp.add(new Pair(SHARE_MEDIA.LINE, "com.umeng.socialize.handler.UMLineHandler"));
        sp.add(new Pair(SHARE_MEDIA.LINKEDIN, "com.umeng.socialize.handler.UMLinkedInHandler"));
        sp.add(new Pair(SHARE_MEDIA.POCKET, "com.umeng.socialize.handler.UMPocketHandler"));
        sp.add(new Pair(SHARE_MEDIA.WHATSAPP, "com.umeng.socialize.handler.UMWhatsAppHandler"));
        sp.add(new Pair(SHARE_MEDIA.YNOTE, "com.umeng.socialize.handler.UMYNoteHandler"));
        sp.add(new Pair(SHARE_MEDIA.SMS, "com.umeng.socialize.handler.SmsHandler"));
        sp.add(new Pair(SHARE_MEDIA.DOUBAN, "com.umeng.socialize.handler.DoubanHandler"));
        sp.add(new Pair(SHARE_MEDIA.TUMBLR, "com.umeng.socialize.handler.UMTumblrHandler"));
        sp.add(new Pair(SHARE_MEDIA.TWITTER, "com.umeng.socialize.handler.TwitterHandler"));
        sp.add(new Pair(SHARE_MEDIA.ALIPAY, "com.umeng.socialize.handler.AlipayHandler"));
        sp.add(new Pair(SHARE_MEDIA.MORE, "com.umeng.socialize.handler.UMMoreHandler"));
        this.guard = new ParamsGuard(this.platformHandlers);
        this.mContext = null;
        this.mContext = context;
        init();
    }

    private void init() {
        for (Pair<SHARE_MEDIA, String> pair : this.supportedPlatform) {
            UMSSOHandler h;
            if (pair.first == SHARE_MEDIA.WEIXIN_CIRCLE || pair.first == SHARE_MEDIA.WEIXIN_FAVORITE) {
                h = (UMSSOHandler) this.platformHandlers.get(SHARE_MEDIA.WEIXIN);
            } else if (pair.first == SHARE_MEDIA.YIXIN_CIRCLE) {
                h = (UMSSOHandler) this.platformHandlers.get(SHARE_MEDIA.YIXIN);
            } else if (pair.first == SHARE_MEDIA.LAIWANG_DYNAMIC) {
                h = (UMSSOHandler) this.platformHandlers.get(SHARE_MEDIA.LAIWANG);
            } else if (pair.first == SHARE_MEDIA.TENCENT) {
                h = newHandler((String) pair.second);
            } else if (pair.first == SHARE_MEDIA.MORE) {
                h = new UMMoreHandler();
            } else if (pair.first == SHARE_MEDIA.SINA) {
                if (Config.isUmengSina.booleanValue()) {
                    h = newHandler((String) pair.second);
                } else {
                    h = newHandler("com.umeng.socialize.handler.SinaSsoHandler");
                }
            } else if (pair.first == SHARE_MEDIA.WEIXIN) {
                if (Config.isUmengWx.booleanValue()) {
                    h = newHandler((String) pair.second);
                } else {
                    h = newHandler("com.umeng.socialize.handler.UMWXHandler");
                }
            } else if (pair.first == SHARE_MEDIA.QQ) {
                if (Config.isUmengQQ.booleanValue()) {
                    h = newHandler((String) pair.second);
                } else {
                    h = newHandler("com.umeng.socialize.handler.UMQQSsoHandler");
                }
            } else if (pair.first != SHARE_MEDIA.QZONE) {
                h = newHandler((String) pair.second);
            } else if (Config.isUmengQQ.booleanValue()) {
                h = newHandler((String) pair.second);
            } else {
                h = newHandler("com.umeng.socialize.handler.QZoneSsoHandler");
            }
            this.platformHandlers.put(pair.first, h);
        }
    }

    private UMSSOHandler newHandler(String classpath) {
        UMSSOHandler handler = null;
        try {
            handler = (UMSSOHandler) Class.forName(classpath).newInstance();
        } catch (Exception ignore) {
            Log.m4548e("xxxxxx ignore=" + ignore);
        }
        if (handler != null) {
            return handler;
        }
        if (classpath.contains("UmengSinaHandler")) {
            Config.isUmengSina = Boolean.valueOf(false);
            return newHandler("com.umeng.socialize.handler.SinaSsoHandler");
        } else if (classpath.contains("UmengQQHandler")) {
            Config.isUmengQQ = Boolean.valueOf(false);
            return newHandler("com.umeng.socialize.handler.UMQQSsoHandler");
        } else if (classpath.contains("UmengQZoneHandler")) {
            Config.isUmengQQ = Boolean.valueOf(false);
            return newHandler("com.umeng.socialize.handler.QZoneSsoHandler");
        } else if (!classpath.contains("UmengWXHandler")) {
            return handler;
        } else {
            Config.isUmengWx = Boolean.valueOf(false);
            return newHandler("com.umeng.socialize.handler.UMWXHandler");
        }
    }

    public UMSSOHandler getHandler(SHARE_MEDIA name) {
        UMSSOHandler handler = (UMSSOHandler) this.platformHandlers.get(name);
        if (handler != null) {
            handler.onCreate(this.mContext, PlatformConfig.getPlatform(name));
        }
        return handler;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        UMSSOHandler handler = getHandler(requestCode);
        if (handler != null) {
            handler.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void onCreate(Activity context, int requestCode, UMAuthListener listener) {
        UMSSOHandler handler = getHandler(requestCode);
        if (handler == null) {
            return;
        }
        if (requestCode == 10103 || requestCode == 11101) {
            handler.onCreate(context, PlatformConfig.getPlatform(getShareMediaByrequestCode(requestCode)));
            handler.setAuthListener(listener);
        }
    }

    private UMSSOHandler getHandler(int requestCode) {
        int code = requestCode;
        if (requestCode == 10103 || requestCode == 11101) {
            code = 10103;
        }
        if (requestCode == HandlerRequestCode.FACEBOOK_REQUEST_SHARE_CODE || requestCode == HandlerRequestCode.FACEBOOK_REQUEST_AUTH_CODE) {
            code = HandlerRequestCode.FACEBOOK_REQUEST_AUTH_CODE;
        }
        if (requestCode == HandlerRequestCode.SINA_AUTH_REQUEST_CODE || requestCode == 765) {
            code = HandlerRequestCode.SINA_REQUEST_CODE;
        }
        if (requestCode == HandlerRequestCode.SINASSO_REQUEST_CODE) {
            code = HandlerRequestCode.SINA_REQUEST_CODE;
        }
        for (UMSSOHandler handler : this.platformHandlers.values()) {
            if (handler != null && code == handler.getRequestCode()) {
                return handler;
            }
        }
        return null;
    }

    public SHARE_MEDIA getShareMediaByrequestCode(int requestCode) {
        if (requestCode == 10103 || requestCode == 11101) {
            return SHARE_MEDIA.QQ;
        }
        if (requestCode == HandlerRequestCode.SINA_AUTH_REQUEST_CODE || requestCode == 765) {
            return SHARE_MEDIA.SINA;
        }
        return SHARE_MEDIA.QQ;
    }

    public void deleteOauth(Activity context, SHARE_MEDIA platform, UMAuthListener listener) {
        if (this.guard.auth(context, platform)) {
            if (listener == null) {
                listener = new C15911();
            }
            ((UMSSOHandler) this.platformHandlers.get(platform)).onCreate(context, PlatformConfig.getPlatform(platform));
            ((UMSSOHandler) this.platformHandlers.get(platform)).deleteAuth(listener);
        }
    }

    public void getPlatformInfo(Activity context, SHARE_MEDIA platform, UMAuthListener listener) {
        if (this.guard.auth(context, platform)) {
            UMSSOHandler handler = (UMSSOHandler) this.platformHandlers.get(platform);
            handler.onCreate(context, PlatformConfig.getPlatform(platform));
            final String tag = String.valueOf(System.currentTimeMillis());
            if (ContextUtil.getContext() != null) {
                SocialAnalytics.getInfostart(ContextUtil.getContext(), platform, handler.getSDKVersion(), tag);
            }
            final SparseArray<UMAuthListener> mListenerRef = new SparseArray();
            mListenerRef.put(0, listener);
            handler.getPlatformInfo(new UMAuthListener() {
                public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
                    UMAuthListener mRef = (UMAuthListener) mListenerRef.get(0, null);
                    if (mRef != null) {
                        mRef.onComplete(platform, action, data);
                    }
                    mListenerRef.clear();
                    if (ContextUtil.getContext() != null) {
                        SocialAnalytics.getInfoendt(ContextUtil.getContext(), platform.toString().toLowerCase(), GraphResponse.SUCCESS_KEY, "", tag);
                    }
                }

                public void onError(SHARE_MEDIA platform, int action, Throwable t) {
                    UMAuthListener mRef = (UMAuthListener) mListenerRef.get(0, null);
                    if (mRef != null) {
                        mRef.onError(platform, action, t);
                    }
                    mListenerRef.clear();
                    if (ContextUtil.getContext() != null && t != null) {
                        SocialAnalytics.getInfoendt(ContextUtil.getContext(), platform.toString().toLowerCase(), "fail", t.getMessage(), tag);
                    }
                }

                public void onCancel(SHARE_MEDIA platform, int action) {
                    UMAuthListener mRef = (UMAuthListener) mListenerRef.get(0, null);
                    if (mRef != null) {
                        mRef.onCancel(platform, action);
                    }
                    mListenerRef.clear();
                    if (ContextUtil.getContext() != null) {
                        SocialAnalytics.getInfoendt(ContextUtil.getContext(), platform.toString().toLowerCase(), "cancel", "", tag);
                    }
                }
            });
        }
    }

    public boolean isInstall(Activity context, SHARE_MEDIA platform) {
        if (!this.guard.auth(context, platform)) {
            return false;
        }
        ((UMSSOHandler) this.platformHandlers.get(platform)).onCreate(context, PlatformConfig.getPlatform(platform));
        return ((UMSSOHandler) this.platformHandlers.get(platform)).isInstall();
    }

    public boolean isSupport(Activity context, SHARE_MEDIA platform) {
        if (!this.guard.auth(context, platform)) {
            return false;
        }
        ((UMSSOHandler) this.platformHandlers.get(platform)).onCreate(context, PlatformConfig.getPlatform(platform));
        return ((UMSSOHandler) this.platformHandlers.get(platform)).isSupport();
    }

    public String getSDKVersion(Activity context, SHARE_MEDIA platform) {
        if (!this.guard.auth(context, platform)) {
            return "";
        }
        ((UMSSOHandler) this.platformHandlers.get(platform)).onCreate(context, PlatformConfig.getPlatform(platform));
        return ((UMSSOHandler) this.platformHandlers.get(platform)).getSDKVersion();
    }

    public boolean isAuthorize(Activity context, SHARE_MEDIA platform) {
        if (!this.guard.auth(context, platform)) {
            return false;
        }
        ((UMSSOHandler) this.platformHandlers.get(platform)).onCreate(context, PlatformConfig.getPlatform(platform));
        return ((UMSSOHandler) this.platformHandlers.get(platform)).isAuthorize();
    }

    public void doOauthVerify(Activity activity, SHARE_MEDIA platform, UMAuthListener listener) {
        if (this.guard.auth(activity, platform)) {
            UMSSOHandler handler = (UMSSOHandler) this.platformHandlers.get(platform);
            handler.onCreate(activity, PlatformConfig.getPlatform(platform));
            final String tag = String.valueOf(System.currentTimeMillis());
            if (ContextUtil.getContext() != null) {
                SocialAnalytics.authstart(ContextUtil.getContext(), platform, handler.getSDKVersion(), handler.isInstall(), tag);
            }
            final SparseArray<UMAuthListener> mAuthListenerRef = new SparseArray();
            mAuthListenerRef.put(0, listener);
            handler.authorize(new UMAuthListener() {
                public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
                    UMAuthListener mRef = (UMAuthListener) mAuthListenerRef.get(0, null);
                    if (mRef != null) {
                        mRef.onComplete(platform, action, data);
                    }
                    mAuthListenerRef.clear();
                    if (ContextUtil.getContext() != null) {
                        SocialAnalytics.authendt(ContextUtil.getContext(), platform.toString().toLowerCase(), GraphResponse.SUCCESS_KEY, "", tag);
                    }
                }

                public void onError(SHARE_MEDIA platform, int action, Throwable t) {
                    UMAuthListener mRef = (UMAuthListener) mAuthListenerRef.get(0, null);
                    if (mRef != null) {
                        mRef.onError(platform, action, t);
                    }
                    mAuthListenerRef.clear();
                    if (ContextUtil.getContext() != null && t != null) {
                        SocialAnalytics.authendt(ContextUtil.getContext(), platform.toString().toLowerCase(), "fail", t.getMessage(), tag);
                    }
                }

                public void onCancel(SHARE_MEDIA platform, int action) {
                    UMAuthListener mRef = (UMAuthListener) mAuthListenerRef.get(0, null);
                    if (mRef != null) {
                        mRef.onCancel(platform, action);
                    }
                    mAuthListenerRef.clear();
                    if (ContextUtil.getContext() != null) {
                        SocialAnalytics.authendt(ContextUtil.getContext(), platform.toString().toLowerCase(), "cancel", "", tag);
                    }
                }
            });
        }
    }

    public void share(Activity activity, ShareAction action, UMShareListener listener) {
        WeakReference<Activity> mWeakAct = new WeakReference(activity);
        if (this.guard.share(action)) {
            SHARE_MEDIA platform = action.getPlatform();
            UMSSOHandler handler = (UMSSOHandler) this.platformHandlers.get(platform);
            handler.setCaller(action.getFrom());
            handler.onCreate((Context) mWeakAct.get(), PlatformConfig.getPlatform(platform));
            if (!(platform.toString().equals("TENCENT") || platform.toString().equals("RENREN") || platform.toString().equals("DOUBAN"))) {
                if (platform.toString().equals("WEIXIN")) {
                    SocialAnalytics.log((Context) mWeakAct.get(), "wxsession", action.getShareContent().mText, action.getShareContent().mMedia);
                } else if (platform.toString().equals("WEIXIN_CIRCLE")) {
                    SocialAnalytics.log((Context) mWeakAct.get(), "wxtimeline", action.getShareContent().mText, action.getShareContent().mMedia);
                } else if (platform.toString().equals("WEIXIN_FAVORITE")) {
                    SocialAnalytics.log((Context) mWeakAct.get(), "wxfavorite", action.getShareContent().mText, action.getShareContent().mMedia);
                } else {
                    SocialAnalytics.log((Context) mWeakAct.get(), platform.toString().toLowerCase(), action.getShareContent().mText, action.getShareContent().mMedia);
                }
            }
            if (Config.isloadUrl) {
                initUrl((Activity) mWeakAct.get(), action);
            }
            final String tag = String.valueOf(System.currentTimeMillis());
            if (ContextUtil.getContext() != null) {
                SocialAnalytics.sharestart(ContextUtil.getContext(), action.getPlatform(), handler.getSDKVersion(), handler.isInstall(), action.getShareContent().getShareType(), tag);
            }
            final SparseArray<UMShareListener> mListenerRef = new SparseArray();
            mListenerRef.put(0, listener);
            handler.share(action.getShareContent(), new UMShareListener() {
                public void onResult(SHARE_MEDIA platform) {
                    if (ContextUtil.getContext() != null) {
                        SocialAnalytics.shareend(ContextUtil.getContext(), platform.toString().toLowerCase(), GraphResponse.SUCCESS_KEY, "", tag);
                    }
                    UMShareListener mRef = (UMShareListener) mListenerRef.get(0, null);
                    if (mRef != null) {
                        mRef.onResult(platform);
                    }
                    mListenerRef.clear();
                }

                public void onError(SHARE_MEDIA platform, Throwable t) {
                    if (!(ContextUtil.getContext() == null || t == null)) {
                        SocialAnalytics.shareend(ContextUtil.getContext(), platform.toString().toLowerCase(), "fail", t.getMessage(), tag);
                    }
                    UMShareListener mRef = (UMShareListener) mListenerRef.get(0, null);
                    if (mRef != null) {
                        mRef.onError(platform, t);
                    }
                    mListenerRef.clear();
                }

                public void onCancel(SHARE_MEDIA platform) {
                    if (ContextUtil.getContext() != null) {
                        SocialAnalytics.shareend(ContextUtil.getContext(), platform.toString().toLowerCase(), "cancel", "", tag);
                    }
                    UMShareListener mRef = (UMShareListener) mListenerRef.get(0, null);
                    if (mRef != null) {
                        mRef.onCancel(platform);
                    }
                    mListenerRef.clear();
                }
            });
        }
    }

    private void initUrl(Activity activity, ShareAction action) {
        String targeturl = action.getShareContent().mTargetUrl;
        if (!TextUtils.isEmpty(targeturl)) {
            String platform;
            if (action.getPlatform().toString().equals("WEIXIN")) {
                platform = "wxsession";
            } else if (action.getPlatform().toString().equals("")) {
                platform = "wxtimeline";
            } else {
                platform = action.getPlatform().toString().toLowerCase();
            }
            UrlResponse resp = RestAPI.uploadUrl(new UrlRequest(activity, platform, targeturl));
            if (resp == null || resp.mStCode != 200) {
                Log.m4548e("upload url fail ");
            } else {
                action.withTargetUrl(resp.result);
            }
        }
    }
}
