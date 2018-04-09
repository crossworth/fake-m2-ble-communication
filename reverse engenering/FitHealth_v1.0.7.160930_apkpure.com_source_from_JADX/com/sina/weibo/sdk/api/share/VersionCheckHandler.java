package com.sina.weibo.sdk.api.share;

import android.content.Context;
import com.sina.weibo.sdk.api.CmdObject;
import com.sina.weibo.sdk.api.VoiceObject;
import com.sina.weibo.sdk.api.WeiboMessage;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.ApiUtils.WeiboInfo;
import com.sina.weibo.sdk.utils.LogUtil;

public class VersionCheckHandler implements IVersionCheckHandler {
    private static final String TAG = "VersionCheckHandler";
    private String mPackageName;

    public VersionCheckHandler(String packageName) {
        this.mPackageName = packageName;
    }

    public void setPackageName(String packageName) {
        this.mPackageName = packageName;
    }

    public boolean check(Context context, WeiboMessage message) {
        LogUtil.m2214d(TAG, "check WeiboMessage package : " + this.mPackageName);
        if (this.mPackageName == null || this.mPackageName.length() == 0) {
            return false;
        }
        WeiboInfo winfo = ApiUtils.queryWeiboInfoByPackage(context, this.mPackageName);
        if (winfo == null) {
            return false;
        }
        LogUtil.m2214d(TAG, "check WeiboMessage WeiboInfo supportApi : " + winfo.supportApi);
        if (winfo.supportApi < ApiUtils.BUILD_INT_VER_2_2 && message.mediaObject != null && (message.mediaObject instanceof VoiceObject)) {
            message.mediaObject = null;
        }
        if (winfo.supportApi < ApiUtils.BUILD_INT_VER_2_3 && message.mediaObject != null && (message.mediaObject instanceof CmdObject)) {
            message.mediaObject = null;
        }
        return true;
    }

    public boolean check(Context context, WeiboMultiMessage message) {
        LogUtil.m2214d(TAG, "check WeiboMultiMessage package : " + this.mPackageName);
        if (this.mPackageName == null || this.mPackageName.length() == 0) {
            return false;
        }
        WeiboInfo winfo = ApiUtils.queryWeiboInfoByPackage(context, this.mPackageName);
        if (winfo == null) {
            return false;
        }
        LogUtil.m2214d(TAG, "check WeiboMultiMessage WeiboInfo supportApi : " + winfo.supportApi);
        if (winfo.supportApi < ApiUtils.BUILD_INT_VER_2_2) {
            return false;
        }
        if (winfo.supportApi < ApiUtils.BUILD_INT_VER_2_3 && message.mediaObject != null && (message.mediaObject instanceof CmdObject)) {
            message.mediaObject = null;
        }
        return true;
    }
}
