package com.umeng.socialize;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.common.QueuedWork.DialogThread;
import com.umeng.socialize.common.QueuedWork.UMAsyncTask;
import com.umeng.socialize.common.SocializeConstants;
import com.umeng.socialize.controller.SocialRouter;
import com.umeng.socialize.handler.UMSSOHandler;
import com.umeng.socialize.net.ActionBarRequest;
import com.umeng.socialize.net.ActionBarResponse;
import com.umeng.socialize.net.RestAPI;
import com.umeng.socialize.net.base.SocializeReseponse;
import com.umeng.socialize.net.stats.DauStatsRequest;
import com.umeng.socialize.net.stats.StatsAPIs;
import com.umeng.socialize.uploadlog.UMLog;
import com.umeng.socialize.utils.ContextUtil;
import com.umeng.socialize.utils.Log;
import com.umeng.socialize.utils.SocializeSpUtils;
import java.lang.ref.WeakReference;

public class UMShareAPI {
    private static UMShareAPI singleton = null;
    SocialRouter router;

    static class InitThread extends UMAsyncTask<Void> {
        private Context mContext;

        public InitThread(Context context) {
            this.mContext = context;
            String umId = SocializeSpUtils.getUMId(context);
            if (!TextUtils.isEmpty(umId)) {
                Config.UID = umId;
            }
            String umEk = SocializeSpUtils.getUMEk(context);
            if (!TextUtils.isEmpty(umEk)) {
                Config.EntityKey = umEk;
            }
        }

        protected Void doInBackground() {
            boolean isNewInstall = isNewInstall();
            ActionBarResponse response = RestAPI.queryShareId(new ActionBarRequest(this.mContext, isNewInstall));
            Log.m4548e("----sdkversion:6.0.5---");
            if (response != null && response.isOk()) {
                setInstalled();
                Log.m4551i("response: " + response.mMsg);
                Config.EntityKey = response.mEntityKey;
                Config.SessionId = response.mSid;
                Config.UID = response.mUid;
                SocializeSpUtils.putUMId(this.mContext, Config.UID);
                SocializeSpUtils.putUMEk(this.mContext, Config.EntityKey);
            }
            DauStatsRequest req = new DauStatsRequest(this.mContext, SocializeReseponse.class);
            Bundle bundle = UMLog.getShareAndAuth();
            if (bundle != null) {
                req.addStringParams("isshare", bundle.getBoolean("share") + "");
                req.addStringParams("isauth", bundle.getBoolean("auth") + "");
                req.addStringParams(SocializeConstants.USHARETYPE, Config.shareType);
                req.addStringParams("ni", (isNewInstall ? 1 : 0) + "");
            }
            StatsAPIs.dauStats(req);
            Log.m4551i("response has error: " + (response == null ? "null" : response.mMsg));
            return null;
        }

        private boolean isNewInstall() {
            return this.mContext.getSharedPreferences(SocializeConstants.SOCIAL_PREFERENCE_NAME, 0).getBoolean("newinstall", false);
        }

        public void setInstalled() {
            Editor ed = this.mContext.getSharedPreferences(SocializeConstants.SOCIAL_PREFERENCE_NAME, 0).edit();
            ed.putBoolean("newinstall", true);
            ed.commit();
        }
    }

    private UMShareAPI(Context context) {
        ContextUtil.setContext(context.getApplicationContext());
        this.router = new SocialRouter(context.getApplicationContext());
        new InitThread(context.getApplicationContext()).execute();
    }

    public static UMShareAPI get(Context context) {
        if (singleton == null || singleton.router == null) {
            singleton = new UMShareAPI(context);
        }
        singleton.router.setmContext(context);
        return singleton;
    }

    public static void init(Context context, String appkey) {
        SocializeConstants.APPKEY = appkey;
        get(context);
    }

    public void doOauthVerify(Activity activity, SHARE_MEDIA platform, UMAuthListener listener) {
        UMLog.putAuth();
        singleton.router.setmContext(activity);
        if (activity != null) {
            final Activity activity2 = activity;
            final SHARE_MEDIA share_media = platform;
            final UMAuthListener uMAuthListener = listener;
            new DialogThread<Void>(activity) {
                protected Void doInBackground() {
                    if (UMShareAPI.this.router != null) {
                        UMShareAPI.this.router.doOauthVerify(activity2, share_media, uMAuthListener);
                    } else {
                        UMShareAPI.this.router = new SocialRouter(activity2);
                        UMShareAPI.this.router.doOauthVerify(activity2, share_media, uMAuthListener);
                    }
                    return null;
                }
            }.execute();
            return;
        }
        Log.m4546d("UMerror", "doOauthVerify activity is null");
    }

    public void deleteOauth(Activity context, SHARE_MEDIA platform, UMAuthListener listener) {
        if (context != null) {
            singleton.router.setmContext(context);
            final Activity activity = context;
            final SHARE_MEDIA share_media = platform;
            final UMAuthListener uMAuthListener = listener;
            new DialogThread<Void>(context) {
                protected Object doInBackground() {
                    if (UMShareAPI.this.router != null) {
                        UMShareAPI.this.router.deleteOauth(activity, share_media, uMAuthListener);
                    }
                    return null;
                }
            }.execute();
            return;
        }
        Log.m4546d("UMerror", "deleteOauth activity is null");
    }

    public void getPlatformInfo(Activity context, SHARE_MEDIA platform, UMAuthListener listener) {
        if (context != null) {
            UMLog.putAuth();
            singleton.router.setmContext(context);
            final Activity activity = context;
            final SHARE_MEDIA share_media = platform;
            final UMAuthListener uMAuthListener = listener;
            new DialogThread<Void>(context) {
                protected Object doInBackground() {
                    if (UMShareAPI.this.router != null) {
                        UMShareAPI.this.router.getPlatformInfo(activity, share_media, uMAuthListener);
                    }
                    return null;
                }
            }.execute();
            return;
        }
        Log.m4546d("UMerror", "getPlatformInfo activity argument is null");
    }

    public boolean isInstall(Activity context, SHARE_MEDIA platform) {
        if (this.router != null) {
            return this.router.isInstall(context, platform);
        }
        this.router = new SocialRouter(context);
        return this.router.isInstall(context, platform);
    }

    public boolean isAuthorize(Activity context, SHARE_MEDIA platform) {
        if (this.router != null) {
            return this.router.isAuthorize(context, platform);
        }
        this.router = new SocialRouter(context);
        return this.router.isAuthorize(context, platform);
    }

    public boolean isSupport(Activity context, SHARE_MEDIA platform) {
        if (this.router != null) {
            return this.router.isSupport(context, platform);
        }
        this.router = new SocialRouter(context);
        return this.router.isSupport(context, platform);
    }

    public String getversion(Activity context, SHARE_MEDIA platform) {
        if (this.router != null) {
            return this.router.getSDKVersion(context, platform);
        }
        this.router = new SocialRouter(context);
        return this.router.getSDKVersion(context, platform);
    }

    public void doShare(Activity activity, ShareAction share, UMShareListener listener) {
        UMLog.putShare();
        final WeakReference<Activity> mWeakAct = new WeakReference(activity);
        if (mWeakAct.get() == null || ((Activity) mWeakAct.get()).isFinishing()) {
            Log.m4546d("UMerror", "Share activity is null");
            return;
        }
        singleton.router.setmContext(activity);
        final ShareAction shareAction = share;
        final UMShareListener uMShareListener = listener;
        new DialogThread<Void>((Context) mWeakAct.get()) {
            protected Void doInBackground() {
                if (!(mWeakAct.get() == null || ((Activity) mWeakAct.get()).isFinishing())) {
                    if (UMShareAPI.this.router != null) {
                        UMShareAPI.this.router.share((Activity) mWeakAct.get(), shareAction, uMShareListener);
                    } else {
                        UMShareAPI.this.router = new SocialRouter((Context) mWeakAct.get());
                        UMShareAPI.this.router.share((Activity) mWeakAct.get(), shareAction, uMShareListener);
                    }
                }
                return null;
            }
        }.execute();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (this.router != null) {
            this.router.onActivityResult(requestCode, resultCode, data);
        } else {
            Log.m4555v("auth fail", "router=null");
        }
    }

    public void HandleQQError(Activity activity, int requestCode, UMAuthListener listener) {
        if (this.router != null) {
            this.router.onCreate(activity, requestCode, listener);
        } else {
            Log.m4555v("auth fail", "router=null");
        }
    }

    public UMSSOHandler getHandler(SHARE_MEDIA name) {
        if (this.router != null) {
            return this.router.getHandler(name);
        }
        return null;
    }
}
