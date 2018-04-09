package com.umeng.socialize.handler;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnKeyListener;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.Toast;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth.Req;
import com.tencent.mm.sdk.modelmsg.SendAuth.Resp;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.open.GameAppOperation;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig.Platform;
import com.umeng.socialize.PlatformConfig.Weixin;
import com.umeng.socialize.ShareContent;
import com.umeng.socialize.SocializeException;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.HandlerRequestCode;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.common.QueuedWork;
import com.umeng.socialize.media.WeiXinShareContent;
import com.umeng.socialize.net.PlatformTokenUploadReq;
import com.umeng.socialize.net.RestAPI;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import com.umeng.socialize.utils.Dummy;
import com.umeng.socialize.utils.Log;
import com.umeng.socialize.utils.SocializeUtils;
import com.umeng.socialize.weixin.net.WXAuthUtils;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UMWXHandler extends UMSSOHandler {
    private static final int REFRESH_TOKEN_EXPIRES = 604800;
    private static final int REQUEST_CODE = 10086;
    private static final int RESP_TYPE_AUTH = 1;
    private static final int RESP_TYPE_SHARE = 2;
    private static final String TAG = "UMWXHandler";
    private static String sScope = "snsapi_userinfo,snsapi_friend,snsapi_message";
    private Weixin config;
    private boolean isToCircle = false;
    private UMAuthListener mAuthListener;
    private UMAuthListener mAuthListenerBackup;
    private IWXAPIEventHandler mEventHandler = new C16419();
    private WeiXinShareContent mShareContent;
    private SHARE_MEDIA mTarget = SHARE_MEDIA.WEIXIN;
    private IWXAPI mWXApi;
    private UMShareListener umShareListener;
    private WeixinPreferences weixinPreferences;

    class C16311 implements Runnable {
        C16311() {
        }

        public void run() {
            if (Config.dialogSwitch) {
                SocializeUtils.safeShowDialog(UMWXHandler.this.createWaitDialog());
            }
        }
    }

    class C16322 implements OnKeyListener {
        C16322() {
        }

        public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
            if (i == 4 && keyEvent.getRepeatCount() == 0) {
                SocializeUtils.safeCloseDialog(Config.wxdialog);
            }
            return false;
        }
    }

    class C16333 implements OnDismissListener {
        C16333() {
        }

        public void onDismiss(DialogInterface dialogInterface) {
            if (Config.wxdialog != null) {
                Config.wxdialog = null;
            }
        }
    }

    class C16419 implements IWXAPIEventHandler {
        C16419() {
        }

        public void onResp(BaseResp resp) {
            switch (resp.getType()) {
                case 1:
                    UMWXHandler.this.onAuthCallback((Resp) resp);
                    return;
                case 2:
                    UMWXHandler.this.onShareCallback((SendMessageToWX.Resp) resp);
                    return;
                default:
                    return;
            }
        }

        public void onReq(BaseReq req) {
        }
    }

    public void onCreate(Context context, Platform p) {
        super.onCreate(context, p);
        this.weixinPreferences = new WeixinPreferences(context.getApplicationContext(), "weixin");
        this.config = (Weixin) p;
        this.mWXApi = WXAPIFactory.createWXAPI(context.getApplicationContext(), this.config.appId);
        this.mWXApi.registerApp(this.config.appId);
        if (!isInstall()) {
            StringBuilder msb = new StringBuilder();
            msb.append("请安装");
            msb.append(this.mTarget);
            msb.append("客户端");
            if (Config.IsToastTip) {
                Toast.makeText(context.getApplicationContext(), msb.toString(), 0).show();
            }
        }
        Log.m4549e(TAG, "handleid=" + this);
    }

    public void setAuthListener(UMAuthListener listener) {
        this.mAuthListenerBackup = listener;
    }

    public void authorize(UMAuthListener listener) {
        this.mAuthListener = listener;
        this.mTarget = this.config.getName();
        if (this.weixinPreferences.isAuthValid()) {
            if (this.weixinPreferences.isAccessTokenAvailable()) {
                loadOauthData("https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=" + this.config.appId + "&grant_type=refresh_token&refresh_token=" + this.weixinPreferences.getRefreshToken());
            }
            this.mAuthListener.onComplete(SHARE_MEDIA.WEIXIN, 0, getAuthWithRefreshToken(this.weixinPreferences.getRefreshToken()));
            return;
        }
        Req req = new Req();
        req.scope = sScope;
        req.state = "none";
        this.mWXApi.sendReq(req);
        QueuedWork.runInMain(new C16311());
    }

    private Dialog createWaitDialog() {
        Activity mActivity = this.mWeakAct != null ? (Activity) this.mWeakAct.get() : null;
        if (Config.wxdialog == null && mActivity != null) {
            Config.wxdialog = new ProgressDialog(mActivity);
            Config.wxdialog.setOwnerActivity(mActivity);
            Config.wxdialog.setOnKeyListener(new C16322());
            Config.wxdialog.setOnDismissListener(new C16333());
        }
        return Config.wxdialog;
    }

    public boolean isAuthorize() {
        return this.weixinPreferences.isAuth();
    }

    private void loadOauthData(String url) {
        this.weixinPreferences.setBundle(parseAuthData(WXAuthUtils.request(url)));
    }

    private Bundle parseAuthData(String response) {
        Bundle bundle = new Bundle();
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Iterator<String> iterator = jsonObject.keys();
                String key = "";
                while (iterator.hasNext()) {
                    key = (String) iterator.next();
                    bundle.putString(key, jsonObject.optString(key));
                }
                bundle.putString("uid", bundle.getString("openid"));
                bundle.putLong("refresh_token_expires", 604800);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return bundle;
    }

    private Map<String, String> getAuthWithRefreshToken(String refresh_token) {
        StringBuilder authURL = new StringBuilder();
        authURL.append("https://api.weixin.qq.com/sns/oauth2/refresh_token?");
        authURL.append("appid=").append(this.config.appId);
        authURL.append("&grant_type=refresh_token");
        authURL.append("&refresh_token=").append(refresh_token);
        Map<String, String> map = null;
        try {
            map = SocializeUtils.jsonToMap(WXAuthUtils.request(authURL.toString()));
        } catch (Exception e) {
        }
        return map;
    }

    private void getAuthWithCode(String code, final UMAuthListener listener) {
        final StringBuilder authURL = new StringBuilder();
        authURL.append("https://api.weixin.qq.com/sns/oauth2/access_token?");
        authURL.append("appid=").append(this.config.appId);
        authURL.append("&secret=").append(this.config.appSecret);
        authURL.append("&code=").append(code);
        authURL.append("&grant_type=authorization_code");
        new Thread(new Runnable() {
            public void run() {
                String response = WXAuthUtils.request(authURL.toString());
                try {
                    Map<String, String> map = SocializeUtils.jsonToMap(response);
                    if (map == null || map.size() == 0) {
                        map = UMWXHandler.this.weixinPreferences.getmap();
                    }
                    UMWXHandler.this.weixinPreferences.setBundle(UMWXHandler.this.parseAuthData(response));
                    final Map<String, String> finalMap = map;
                    QueuedWork.runInMain(new Runnable() {
                        public void run() {
                            UMWXHandler.this.uploadAuthData(finalMap);
                            if (finalMap.get("errcode") != null) {
                                listener.onError(SHARE_MEDIA.WEIXIN, 0, new Throwable((String) finalMap.get("errmsg")));
                            } else {
                                listener.onComplete(SHARE_MEDIA.WEIXIN, 0, finalMap);
                            }
                        }
                    });
                } catch (Exception e) {
                }
            }
        }).start();
    }

    public boolean isInstall() {
        return this.mWXApi.isWXAppInstalled();
    }

    protected void onAuthCallback(Resp resp) {
        if (Config.dialogSwitch) {
            SocializeUtils.safeCloseDialog(Config.wxdialog);
        }
        if (this.mAuthListener == null) {
            this.mAuthListener = this.mAuthListenerBackup;
            Log.m4548e("UMWXHandlermAuthListener =" + this.mAuthListener);
        }
        UMAuthListener authListener = (UMAuthListener) Dummy.get(UMAuthListener.class, this.mAuthListener);
        if (resp.errCode == 0) {
            getAuthWithCode(resp.code, authListener);
        } else if (resp.errCode != -2) {
            SocializeException ex = new SocializeException(TextUtils.concat(new CharSequence[]{"weixin auth error (", String.valueOf(resp.errCode), "):", resp.errStr}).toString());
            if (authListener != null) {
                authListener.onError(SHARE_MEDIA.WEIXIN, 0, ex);
            }
        } else if (authListener != null) {
            authListener.onCancel(SHARE_MEDIA.WEIXIN, 0);
        } else {
            Log.m4548e("UMWXHandlerauthListener == null");
        }
    }

    public String getSDKVersion() {
        return "3.1.1";
    }

    public void deleteAuth(UMAuthListener listener) {
        if (isInstall()) {
            this.weixinPreferences.delete();
            listener.onComplete(SHARE_MEDIA.WEIXIN, 1, null);
        }
    }

    public boolean isSupportAuth() {
        return true;
    }

    public int getRequestCode() {
        if (this.isToCircle) {
            return HandlerRequestCode.WX_CIRCLE_REQUEST_CODE;
        }
        return 10086;
    }

    private void refreshAccessToken() {
        if (this.weixinPreferences == null) {
            return;
        }
        if (this.weixinPreferences.isAuthValid()) {
            Log.m4546d("refresh", "requesting access token with refresh");
            this.weixinPreferences.setBundle(parseAuthData(WXAuthUtils.request("https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=" + this.config.appId + "&grant_type=refresh_token&refresh_token=" + this.weixinPreferences.getRefreshToken())));
            return;
        }
        Log.m4549e("refresh", "weixin refresh token is expired");
    }

    public void getPlatformInfo(final UMAuthListener listener) {
        String uid = this.weixinPreferences.getUID();
        String accessToken = this.weixinPreferences.getAccessToken();
        Log.m4548e("uid=" + uid + "  accessToken=" + accessToken + "  weixinPreferences.isAccessTokenAvailable()=" + this.weixinPreferences.isAccessTokenAvailable() + "  weixinPreferences.isAuthValid()=" + this.weixinPreferences.isAuthValid());
        if (TextUtils.isEmpty(uid) || TextUtils.isEmpty(accessToken) || !this.weixinPreferences.isAccessTokenAvailable() || this.weixinPreferences.isAuthValid()) {
            Log.m4549e(TAG, "please check had authed...");
            authorize(new UMAuthListener() {

                class C16361 implements Runnable {
                    C16361() {
                    }

                    public void run() {
                        UMWXHandler.this.getPlatformInfo(listener);
                    }
                }

                public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> map) {
                    QueuedWork.runInBack(new C16361());
                }

                public void onError(SHARE_MEDIA platform, int action, Throwable t) {
                    listener.onError(platform, action, t);
                }

                public void onCancel(SHARE_MEDIA platform, int action) {
                    listener.onCancel(platform, action);
                }
            });
            return;
        }
        StringBuilder builder = new StringBuilder();
        builder.append("https://api.weixin.qq.com/sns/userinfo?access_token=");
        builder.append(accessToken).append("&openid=").append(uid);
        builder.append("&lang=zh_CN");
        final String jsonStr = WXAuthUtils.request(builder.toString());
        final Map<String, String> map = parseUserInfo(jsonStr);
        if (map == null) {
            QueuedWork.runInMain(new Runnable() {
                public void run() {
                    listener.onError(SHARE_MEDIA.WEIXIN, 2, new Throwable(jsonStr));
                }
            });
        } else {
            QueuedWork.runInMain(new Runnable() {
                public void run() {
                    listener.onComplete(SHARE_MEDIA.WEIXIN, 2, map);
                }
            });
        }
    }

    private void uploadAuthData(final Map<String, String> bundle) throws SocializeException {
        new Thread(new Runnable() {
            public void run() {
                if (UMWXHandler.this.getContext() != null) {
                    PlatformTokenUploadReq req = new PlatformTokenUploadReq(UMWXHandler.this.getContext());
                    req.addStringParams("to", "wxsession");
                    req.addStringParams("usid", (String) bundle.get(GameAppOperation.GAME_UNION_ID));
                    req.addStringParams("access_token", (String) bundle.get("access_token"));
                    req.addStringParams("refresh_token", (String) bundle.get("refresh_token"));
                    req.addStringParams("expires_in", (String) bundle.get("expires_in"));
                    Log.m4548e("upload token resp = " + RestAPI.uploadPlatformToken(req));
                }
            }
        }).start();
    }

    private Map<String, String> parseUserInfo(String result) {
        Map<String, String> map = new HashMap();
        try {
            JSONObject jsonObject = new JSONObject(result);
            if (jsonObject.has("errcode")) {
                Log.m4549e(TAG, result + "");
                map.put("errcode", jsonObject.getString("errcode"));
                return map;
            }
            map.put("openid", jsonObject.opt("openid").toString());
            map.put("screen_name", jsonObject.opt("nickname").toString());
            map.put("language", jsonObject.opt("language").toString());
            map.put("city", jsonObject.opt("city").toString());
            map.put("province", jsonObject.opt("province").toString());
            map.put("country", jsonObject.opt("country").toString());
            map.put(SocializeProtocolConstants.PROTOCOL_KEY_FRIENDS_ICON, jsonObject.opt("headimgurl").toString());
            map.put(GameAppOperation.GAME_UNION_ID, jsonObject.opt(GameAppOperation.GAME_UNION_ID).toString());
            map.put(SocializeProtocolConstants.PROTOCOL_KEY_GENDER, jsonObject.opt("sex").toString());
            JSONArray jsonArray = jsonObject.getJSONArray("privilege");
            int len = jsonArray.length();
            if (len > 0) {
                String[] privileges = new String[len];
                for (int i = 0; i < len; i++) {
                    privileges[i] = jsonArray.get(i).toString();
                }
                map.put("privilege", privileges.toString());
            }
            if (this.weixinPreferences == null) {
                return map;
            }
            map.put("openid", this.weixinPreferences.getUID());
            map.put(GameAppOperation.GAME_UNION_ID, this.weixinPreferences.getmUnionid());
            map.put("access_token", this.weixinPreferences.getAccessToken());
            map.put("refresh_token", this.weixinPreferences.getRefreshToken());
            map.put("expires_in", String.valueOf(this.weixinPreferences.getmAccessTokenTTL()));
            return map;
        } catch (JSONException e) {
            e.printStackTrace();
            return Collections.emptyMap();
        }
    }

    public boolean share(ShareContent content, UMShareListener listener) {
        this.mTarget = this.config.getName();
        if (isInstall()) {
            this.mShareContent = new WeiXinShareContent(content);
            if (this.mShareContent != null) {
                this.mShareContent.parseMediaType();
                String str = this.mShareContent.mShareType;
                WeiXinShareContent weiXinShareContent = this.mShareContent;
                if (str == WeiXinShareContent.TYPE_EMOJI && this.isToCircle) {
                    listener.onError(this.mTarget, new Throwable("微信朋友圈不支持表情分享..."));
                    Toast.makeText(getContext(), "微信朋友圈不支持表情分享...", 0).show();
                    return false;
                }
            }
            this.umShareListener = listener;
            return shareTo(new WeiXinShareContent(content));
        }
        listener.onError(this.mTarget, new Throwable("weixin not install"));
        return false;
    }

    public boolean isSupport() {
        return this.mWXApi.isWXAppSupportAPI();
    }

    public boolean shareTo(WeiXinShareContent shareContent) {
        boolean z = false;
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction(this.mShareContent.mShareType);
        req.message = shareContent.getWxMediaMessage();
        switch (this.mTarget) {
            case WEIXIN:
                req.scene = 0;
                break;
            case WEIXIN_CIRCLE:
                req.scene = 1;
                break;
            case WEIXIN_FAVORITE:
                req.scene = 2;
                break;
            default:
                req.scene = 2;
                break;
        }
        if (req.message == null) {
            Log.m4548e("wx,message = null");
        } else if (req.message.mediaObject == null) {
            Log.m4548e("wx,mediaobject = null");
        } else {
            z = this.mWXApi.sendReq(req);
            if (!z) {
                this.umShareListener.onError(this.mTarget, new Throwable("sendReq = false,请检查分享类型"));
            }
            Log.m4548e("BitmapUtils sendReq=" + z);
        }
        return z;
    }

    protected void onShareCallback(SendMessageToWX.Resp resp) {
        switch (resp.errCode) {
            case -3:
            case -1:
                if (this.umShareListener != null) {
                    this.umShareListener.onError(this.mTarget, new SocializeException(resp.errCode, resp.errStr));
                    return;
                }
                return;
            case -2:
                if (this.umShareListener != null) {
                    this.umShareListener.onCancel(this.mTarget);
                    return;
                }
                return;
            case 0:
                if (this.umShareListener != null) {
                    Map<String, String> map = new HashMap();
                    map.put("uid", resp.openId);
                    uploadAuthData(map);
                    this.umShareListener.onResult(this.mTarget);
                    return;
                }
                return;
            default:
                if (this.umShareListener != null) {
                    this.umShareListener.onError(this.mTarget, new SocializeException(resp.errCode, resp.errStr));
                }
                Log.m4546d(TAG, "微信发送 -- 未知错误.+" + resp.errCode + "  resp.errStr " + resp.errStr);
                return;
        }
    }

    public IWXAPIEventHandler getWXEventHandler() {
        return this.mEventHandler;
    }

    public IWXAPI getWXApi() {
        return this.mWXApi;
    }

    private String buildTransaction(String type) {
        if (type == null) {
            return String.valueOf(System.currentTimeMillis());
        }
        return type + System.currentTimeMillis();
    }
}
