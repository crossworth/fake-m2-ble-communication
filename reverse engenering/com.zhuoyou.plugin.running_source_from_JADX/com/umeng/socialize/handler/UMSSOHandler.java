package com.umeng.socialize.handler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.umeng.socialize.PlatformConfig.Platform;
import com.umeng.socialize.ShareContent;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.utils.ContextUtil;
import com.umeng.socialize.utils.Log;
import java.lang.ref.WeakReference;

public abstract class UMSSOHandler {
    private static final String TAG = "UMSSOHandler";
    private String mCaller;
    private Platform mConfig = null;
    private Context mContext = null;
    public String mTargetUrl;
    protected int mThumbLimit = 32768;
    protected WeakReference<Activity> mWeakAct;

    public abstract boolean share(ShareContent shareContent, UMShareListener uMShareListener);

    public void onCreate(Context context, Platform p) {
        this.mContext = ContextUtil.getContext();
        this.mConfig = p;
        if (context instanceof Activity) {
            this.mWeakAct = new WeakReference((Activity) context);
        }
    }

    public void setCaller(String caller) {
        this.mCaller = caller;
    }

    public Context getContext() {
        return this.mContext;
    }

    public Platform getConfig() {
        return this.mConfig;
    }

    public void authorize(UMAuthListener listener) {
    }

    public void deleteAuth(UMAuthListener listener) {
    }

    public void setAuthListener(UMAuthListener listener) {
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    public void getPlatformInfo(UMAuthListener listener) {
        Log.m4545d("'getPlatformInfo', it works!");
    }

    public boolean isInstall() {
        Log.m4548e("该平台不支持查询");
        return true;
    }

    public boolean isSupport() {
        Log.m4548e("该平台不支持查询");
        return true;
    }

    public boolean isAuthorize() {
        Log.m4548e("该平台不支持查询");
        return true;
    }

    public String getSDKVersion() {
        return "";
    }

    public int getRequestCode() {
        return 0;
    }

    public boolean isSupportAuth() {
        return false;
    }
}
