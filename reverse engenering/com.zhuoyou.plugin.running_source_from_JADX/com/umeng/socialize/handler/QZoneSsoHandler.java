package com.umeng.socialize.handler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig.Platform;
import com.umeng.socialize.ShareContent;
import com.umeng.socialize.SocializeException;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.common.QueuedWork;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.net.PlatformTokenUploadReq;
import com.umeng.socialize.net.RestAPI;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import com.umeng.socialize.utils.Log;
import com.umeng.socialize.utils.SocializeUtils;
import org.json.JSONObject;

public class QZoneSsoHandler extends UMTencentSSOHandler {
    private static final String TAG = "QZoneSsoHandler";
    private QZoneShareContent mShareContent;
    private QQPreferences qqPreferences;

    class C15991 implements IUiListener {
        C15991() {
        }

        public void onError(UiError e) {
            if (e != null) {
                Log.m4546d(QZoneSsoHandler.TAG, "授权失败! ==> errorCode = " + e.errorCode + ", errorMsg = " + e.errorMessage + ", detail = " + e.errorDetail);
            }
            QZoneSsoHandler.this.mAuthListener.onError(SHARE_MEDIA.QQ, 0, new Throwable("授权失败! ==> errorCode = " + e.errorCode + ", errorMsg = " + e.errorMessage + ", detail = " + e.errorDetail));
        }

        public void onCancel() {
            QZoneSsoHandler.this.mAuthListener.onCancel(SHARE_MEDIA.QQ, 0);
        }

        public void onComplete(Object response) {
            SocializeUtils.safeCloseDialog(QZoneSsoHandler.this.mProgressDialog);
            Bundle values = QZoneSsoHandler.this.parseOauthData(response);
            QZoneSsoHandler.this.qqPreferences.setAuthData(values).commit();
            QZoneSsoHandler.this.initOpenidAndToken((JSONObject) response);
            if (QZoneSsoHandler.this.mAuthListener != null) {
                QZoneSsoHandler.this.mAuthListener.onComplete(SHARE_MEDIA.QQ, 0, SocializeUtils.bundleTomap(values));
            }
            QZoneSsoHandler.this.uploadAuthData(values);
            if (values != null && !TextUtils.isEmpty(values.getString("ret"))) {
            }
        }
    }

    public void onCreate(Context context, Platform p) {
        super.onCreate(context, p);
        this.qqPreferences = new QQPreferences(context, SHARE_MEDIA.QQ.toString());
    }

    public boolean share(ShareContent content, UMShareListener listener) {
        if (listener != null) {
            this.mShareListener = listener;
        }
        if (isInstall(getConfig())) {
            this.mShareContent = new QZoneShareContent(content);
            shareToQZone();
        }
        return false;
    }

    private void shareToQZone() {
        Bundle bundle = this.mShareContent.buildParamsQzone();
        bundle.putString("appName", getAppName());
        if (this.mShareContent.getisPublish()) {
            doPublishToQzone(bundle);
        } else {
            defaultQZoneShare(bundle);
        }
    }

    public void deleteAuth(UMAuthListener listener) {
        this.mTencent.logout(getContext());
        if (this.qqPreferences != null) {
            this.qqPreferences.delete();
        }
        listener.onComplete(SHARE_MEDIA.QZONE, 1, null);
    }

    private boolean isInstall(Platform p) {
        if (this.mWeakAct.get() == null || ((Activity) this.mWeakAct.get()).isFinishing() || this.mTencent.isSupportSSOLogin((Activity) this.mWeakAct.get())) {
            return true;
        }
        StringBuilder msb = new StringBuilder();
        msb.append("请安装");
        msb.append("qq");
        msb.append("客户端");
        Log.m4554v(msb.toString());
        if (Config.IsToastTip) {
            Toast.makeText(getContext(), msb, 1).show();
        }
        return false;
    }

    public boolean isSupportAuth() {
        return true;
    }

    public void authorize(UMAuthListener authListener) {
        if (isInstall(getConfig())) {
            this.mAuthListener = authListener;
            loginDeal();
        }
    }

    private void loginDeal() {
        Log.m4552i(TAG, "QQ oauth login...");
        if (this.mWeakAct.get() != null && !((Activity) this.mWeakAct.get()).isFinishing()) {
            this.mTencent.login((Activity) this.mWeakAct.get(), "all", getAuthlistener(this.mAuthListener));
        }
    }

    private IUiListener getAuthlistener(UMAuthListener listener) {
        return new C15991();
    }

    private void uploadAuthData(final Bundle bundle) throws SocializeException {
        new Thread(new Runnable() {
            public void run() {
                PlatformTokenUploadReq req = new PlatformTokenUploadReq(QZoneSsoHandler.this.getContext());
                req.addStringParams("to", "qq");
                req.addStringParams("usid", bundle.getString("uid"));
                req.addStringParams("access_token", bundle.getString("access_token"));
                req.addStringParams("refresh_token", bundle.getString("refresh_token"));
                req.addStringParams("expires_in", bundle.getString("expires_in"));
                req.addStringParams("app_id", QZoneSsoHandler.this.config.appId);
                req.addStringParams(SocializeProtocolConstants.PROTOCOL_KEY_APP_KEY, QZoneSsoHandler.this.config.appKey);
                Log.m4548e("upload token resp = " + RestAPI.uploadPlatformToken(req));
            }
        }).start();
    }

    public void initOpenidAndToken(JSONObject jsonObject) {
        try {
            String token = jsonObject.getString("access_token");
            String expires = jsonObject.getString("expires_in");
            String openId = jsonObject.getString("openid");
            if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires) && !TextUtils.isEmpty(openId)) {
                this.mTencent.setAccessToken(token, expires);
                this.mTencent.setOpenId(openId);
            }
        } catch (Exception e) {
        }
    }

    public int getRequestCode() {
        return 10104;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 10104) {
            Tencent.onActivityResultData(requestCode, resultCode, data, getmShareListener(this.mShareListener));
        }
        if (requestCode == 11101) {
            Tencent.onActivityResultData(requestCode, resultCode, data, getAuthlistener(this.mAuthListener));
        }
    }

    public IUiListener getmShareListener(final UMShareListener mShareListener) {
        return new IUiListener() {
            public void onComplete(Object o) {
                if (mShareListener != null) {
                    mShareListener.onResult(SHARE_MEDIA.QZONE);
                }
            }

            public void onError(UiError uiError) {
                if (mShareListener != null) {
                    mShareListener.onError(SHARE_MEDIA.QZONE, new Throwable(uiError.errorMessage));
                }
            }

            public void onCancel() {
                if (mShareListener != null) {
                    mShareListener.onCancel(SHARE_MEDIA.QZONE);
                }
            }
        };
    }

    private void doPublishToQzone(final Bundle bundle) {
        if (bundle != null) {
            QueuedWork.runInMain(new Runnable() {
                public void run() {
                    if (QZoneSsoHandler.this.mWeakAct.get() != null && !((Activity) QZoneSsoHandler.this.mWeakAct.get()).isFinishing()) {
                        QZoneSsoHandler.this.mTencent.publishToQzone((Activity) QZoneSsoHandler.this.mWeakAct.get(), bundle, QZoneSsoHandler.this.getmShareListener(QZoneSsoHandler.this.mShareListener));
                    }
                }
            });
        }
    }

    private void defaultQZoneShare(final Bundle bundle) {
        if (bundle != null) {
            QueuedWork.runInMain(new Runnable() {
                public void run() {
                    if (QZoneSsoHandler.this.mWeakAct.get() != null && !((Activity) QZoneSsoHandler.this.mWeakAct.get()).isFinishing()) {
                        QZoneSsoHandler.this.mTencent.shareToQzone((Activity) QZoneSsoHandler.this.mWeakAct.get(), bundle, QZoneSsoHandler.this.getmShareListener(QZoneSsoHandler.this.mShareListener));
                    }
                }
            });
        }
    }
}
