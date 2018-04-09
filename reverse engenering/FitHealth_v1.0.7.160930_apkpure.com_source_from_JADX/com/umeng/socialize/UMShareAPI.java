package com.umeng.socialize;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Build.VERSION;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.common.QueuedWork.UMAsyncTask;
import com.umeng.socialize.common.SocializeConstants;
import com.umeng.socialize.handler.UMSSOHandler;
import com.umeng.socialize.net.ActionBarRequest;
import com.umeng.socialize.net.ActionBarResponse;
import com.umeng.socialize.net.RestAPI;
import com.umeng.socialize.p025b.C0947a;
import com.umeng.socialize.utils.ContextUtil;
import com.umeng.socialize.utils.Log;
import com.umeng.socialize.view.UMFriendListener;

public class UMShareAPI {
    private static UMShareAPI f3232b = null;
    C0947a f3233a;

    static class C1798a extends UMAsyncTask<Void> {
        private Context f4815a;

        protected /* synthetic */ Object doInBackground() {
            return m4987a();
        }

        public C1798a(Context context) {
            this.f4815a = context;
        }

        protected Void m4987a() {
            ActionBarResponse queryShareId = RestAPI.queryShareId(new ActionBarRequest(this.f4815a, m4986c()));
            if (queryShareId != null && queryShareId.isOk()) {
                m4988b();
                Log.m3253i("response: " + queryShareId.mMsg);
                Config.EntityKey = queryShareId.mEntityKey;
                Config.SessionId = queryShareId.mSid;
                Config.UID = queryShareId.mUid;
            }
            Log.m3253i("response has error: " + (queryShareId == null ? "null" : queryShareId.mMsg));
            return null;
        }

        private boolean m4986c() {
            return this.f4815a.getSharedPreferences(SocializeConstants.SOCIAL_PREFERENCE_NAME, 0).getBoolean("newinstall", false);
        }

        public void m4988b() {
            Editor edit = this.f4815a.getSharedPreferences(SocializeConstants.SOCIAL_PREFERENCE_NAME, 0).edit();
            edit.putBoolean("newinstall", true);
            edit.commit();
        }
    }

    private UMShareAPI(Context context) {
        ContextUtil.setContext(context);
        if (VERSION.SDK_INT >= 23) {
            String[] strArr = new String[]{"android.permission.ACCESS_FINE_LOCATION", "android.permission.CALL_PHONE", "android.permission.READ_LOGS", "android.permission.READ_PHONE_STATE", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.SET_DEBUG_APP", "android.permission.SYSTEM_ALERT_WINDOW", "android.permission.GET_ACCOUNTS"};
            Log.m3248d("check", "check permission");
            for (String str : strArr) {
                try {
                    if (((Integer) Class.forName("android.content.Context").getMethod("checkSelfPermission", new Class[]{String.class}).invoke(context, new Object[]{str})).intValue() == 0) {
                        Log.m3248d("Umeng", "PERMISSION_GRANTED:" + str);
                    } else {
                        Log.m3251e("Umeng Error", "PERMISSION_MISSING:" + str);
                    }
                } catch (Exception e) {
                    Log.m3251e("Umeng Error", "error" + e.getMessage());
                }
            }
        }
        this.f3233a = new C0947a(context);
        new C1798a(context).execute();
    }

    public static UMShareAPI get(Context context) {
        if (f3232b == null || f3232b.f3233a == null) {
            f3232b = new UMShareAPI(context);
        }
        return f3232b;
    }

    public void doOauthVerify(Activity activity, SHARE_MEDIA share_media, UMAuthListener uMAuthListener) {
        if (activity != null) {
            new C2012e(this, activity, activity, share_media, uMAuthListener).execute();
        } else {
            Log.m3248d("UMerror", "doOauthVerify activity is null");
        }
    }

    public void deleteOauth(Activity activity, SHARE_MEDIA share_media, UMAuthListener uMAuthListener) {
        if (activity != null) {
            new C2013f(this, activity, activity, share_media, uMAuthListener).execute();
        } else {
            Log.m3248d("UMerror", "deleteOauth activity is null");
        }
    }

    public void getPlatformInfo(Activity activity, SHARE_MEDIA share_media, UMAuthListener uMAuthListener) {
        if (activity != null) {
            new C2014g(this, activity, activity, share_media, uMAuthListener).execute();
        } else {
            Log.m3248d("UMerror", "getPlatformInfo activity argument is null");
        }
    }

    public boolean isInstall(Activity activity, SHARE_MEDIA share_media) {
        if (this.f3233a != null) {
            return this.f3233a.m3192a(activity, share_media);
        }
        this.f3233a = new C0947a(activity);
        return this.f3233a.m3192a(activity, share_media);
    }

    public boolean isAuthorize(Activity activity, SHARE_MEDIA share_media) {
        if (this.f3233a != null) {
            return this.f3233a.m3194b(activity, share_media);
        }
        this.f3233a = new C0947a(activity);
        return this.f3233a.m3194b(activity, share_media);
    }

    public void getFriend(Activity activity, SHARE_MEDIA share_media, UMFriendListener uMFriendListener) {
        if (activity != null) {
            new C2015h(this, activity, activity, share_media, uMFriendListener).execute();
        } else {
            Log.m3248d("UMerror", "getFriend activity is null");
        }
    }

    public void doShare(Activity activity, ShareAction shareAction, UMShareListener uMShareListener) {
        if (activity != null) {
            new C2016i(this, activity, activity, shareAction, uMShareListener).execute();
        } else {
            Log.m3248d("UMerror", "Share activity is null");
        }
    }

    public void openShare(ShareAction shareAction, UMShareListener uMShareListener) {
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        if (this.f3233a != null) {
            this.f3233a.m3187a(i, i2, intent);
        } else {
            Log.m3257v("auth fail", "router=null");
        }
    }

    public UMSSOHandler getHandler(SHARE_MEDIA share_media) {
        if (this.f3233a != null) {
            return this.f3233a.m3186a(share_media);
        }
        return null;
    }
}
