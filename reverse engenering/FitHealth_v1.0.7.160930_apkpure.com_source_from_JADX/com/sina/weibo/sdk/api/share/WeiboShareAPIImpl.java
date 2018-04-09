package com.sina.weibo.sdk.api.share;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.sina.weibo.sdk.api.share.ApiUtils.WeiboInfo;
import com.sina.weibo.sdk.api.share.IWeiboHandler.Request;
import com.sina.weibo.sdk.api.share.IWeiboHandler.Response;
import com.sina.weibo.sdk.constant.WBConstants;
import com.sina.weibo.sdk.constant.WBConstants.Base;
import com.sina.weibo.sdk.constant.WBConstants.SDK;
import com.sina.weibo.sdk.exception.WeiboShareException;
import com.sina.weibo.sdk.utils.LogUtil;
import com.sina.weibo.sdk.utils.MD5;
import com.sina.weibo.sdk.utils.Utility;

class WeiboShareAPIImpl implements IWeiboShareAPI {
    private static final String TAG = "WeiboApiImpl";
    private String mAppKey;
    private Context mContext;
    private Dialog mDownloadConfirmDialog = null;
    private IWeiboDownloadListener mDownloadListener;
    private boolean mNeedDownloadWeibo = true;
    private WeiboInfo mWeiboInfo = null;

    public WeiboShareAPIImpl(Context context, String appKey, boolean needDownloadWeibo) {
        this.mContext = context;
        this.mAppKey = appKey;
        this.mNeedDownloadWeibo = needDownloadWeibo;
        this.mWeiboInfo = ApiUtils.queryWeiboInfo(this.mContext);
        if (this.mWeiboInfo != null) {
            LogUtil.m2214d(TAG, this.mWeiboInfo.toString());
        } else {
            LogUtil.m2214d(TAG, "WeiboInfo: is null");
        }
    }

    public boolean isWeiboAppInstalled() {
        return this.mWeiboInfo != null;
    }

    public boolean isWeiboAppSupportAPI() {
        return ApiUtils.isWeiboAppSupportAPI(getWeiboAppSupportAPI());
    }

    public int getWeiboAppSupportAPI() {
        return this.mWeiboInfo == null ? -1 : this.mWeiboInfo.supportApi;
    }

    public boolean registerApp() {
        try {
            if (!checkEnvironment(this.mNeedDownloadWeibo)) {
                return false;
            }
            sendBroadcast(this.mContext, WBConstants.ACTION_WEIBO_REGISTER, this.mAppKey, null, null);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean handleWeiboResponse(Intent intent, Response handler) {
        String appPackage = intent.getStringExtra(Base.APP_PKG);
        if (appPackage == null) {
            LogUtil.m2215e(TAG, "responseListener() faild appPackage is null");
            return false;
        } else if (handler instanceof Activity) {
            LogUtil.m2214d(TAG, "responseListener() callPkg : " + ((Activity) handler).getCallingPackage());
            if (intent.getStringExtra(WBConstants.TRAN) == null) {
                LogUtil.m2215e(TAG, "responseListener() faild intent TRAN is null");
                return false;
            } else if (ApiUtils.validateWeiboSign(this.mContext, appPackage)) {
                handler.onResponse(new SendMessageToWeiboResponse(intent.getExtras()));
                return true;
            } else {
                LogUtil.m2215e(TAG, "responseListener() faild appPackage validateSign faild");
                return false;
            }
        } else {
            LogUtil.m2215e(TAG, "responseListener() faild handler is not Activity");
            return false;
        }
    }

    public boolean handleWeiboRequest(Intent intent, Request handler) {
        if (intent == null || handler == null) {
            return false;
        }
        String appPackage = intent.getStringExtra(Base.APP_PKG);
        String transaction = intent.getStringExtra(WBConstants.TRAN);
        if (appPackage == null) {
            LogUtil.m2215e(TAG, "requestListener() faild appPackage validateSign faild");
            handler.onRequest(null);
            return false;
        } else if (transaction == null) {
            LogUtil.m2215e(TAG, "requestListener() faild intent TRAN is null");
            handler.onRequest(null);
            return false;
        } else if (ApiUtils.validateWeiboSign(this.mContext, appPackage)) {
            handler.onRequest(new ProvideMessageForWeiboRequest(intent.getExtras()));
            return true;
        } else {
            LogUtil.m2215e(TAG, "requestListener() faild appPackage validateSign faild");
            handler.onRequest(null);
            return false;
        }
    }

    public boolean launchWeibo() {
        if (this.mWeiboInfo == null) {
            LogUtil.m2215e(TAG, "startWeibo() faild winfo is null");
            return false;
        }
        try {
            if (TextUtils.isEmpty(this.mWeiboInfo.packageName)) {
                LogUtil.m2215e(TAG, "startWeibo() faild packageName is null");
                return false;
            }
            this.mContext.startActivity(this.mContext.getPackageManager().getLaunchIntentForPackage(this.mWeiboInfo.packageName));
            return true;
        } catch (Exception e) {
            LogUtil.m2215e(TAG, e.getMessage());
            return false;
        }
    }

    public boolean sendRequest(BaseRequest request) {
        if (request == null) {
            LogUtil.m2215e(TAG, "sendRequest faild act == null or request == null");
            return false;
        }
        try {
            if (!checkEnvironment(this.mNeedDownloadWeibo)) {
                return false;
            }
            if (request.check(this.mContext, new VersionCheckHandler(this.mWeiboInfo.packageName))) {
                Bundle data = new Bundle();
                request.toBundle(data);
                return launchWeiboActivity((Activity) this.mContext, WBConstants.ACTIVITY_WEIBO, this.mWeiboInfo.packageName, this.mAppKey, data);
            }
            LogUtil.m2215e(TAG, "sendRequest faild request check faild");
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean sendResponse(BaseResponse response) {
        if (response == null) {
            LogUtil.m2215e(TAG, "sendResponse failed response null");
            return false;
        } else if (response.check(this.mContext, new VersionCheckHandler())) {
            Bundle data = new Bundle();
            response.toBundle(data);
            sendBroadcast(this.mContext, WBConstants.ACTION_WEIBO_RESPONSE, this.mAppKey, response.reqPackageName, data);
            return true;
        } else {
            LogUtil.m2215e(TAG, "sendResponse checkArgs fail");
            return false;
        }
    }

    public void registerWeiboDownloadListener(IWeiboDownloadListener listener) {
        this.mDownloadListener = listener;
    }

    public boolean checkEnvironment(boolean bShowDownloadDialog) throws WeiboShareException {
        if (this.mWeiboInfo == null) {
            if (bShowDownloadDialog) {
                if (this.mDownloadConfirmDialog == null) {
                    this.mDownloadConfirmDialog = WeiboDownloader.createDownloadConfirmDialog(this.mContext, this.mDownloadListener);
                    this.mDownloadConfirmDialog.show();
                } else if (!this.mDownloadConfirmDialog.isShowing()) {
                    this.mDownloadConfirmDialog.show();
                }
                return false;
            }
            throw new WeiboShareException("Weibo is NOT installed!");
        } else if (!ApiUtils.isWeiboAppSupportAPI(this.mWeiboInfo.supportApi)) {
            throw new WeiboShareException("Weibo do NOT support Share API!");
        } else if (ApiUtils.validateWeiboSign(this.mContext, this.mWeiboInfo.packageName)) {
            return true;
        } else {
            throw new WeiboShareException("Weibo signature is incorrect!");
        }
    }

    private boolean launchWeiboActivity(Activity activity, String action, String pkgName, String appkey, Bundle data) {
        if (activity == null || TextUtils.isEmpty(action) || TextUtils.isEmpty(pkgName) || TextUtils.isEmpty(appkey)) {
            LogUtil.m2215e("ActivityHandler", "send fail, invalid arguments");
            return false;
        }
        Intent intent = new Intent();
        intent.setPackage(pkgName);
        intent.setAction(action);
        String appPackage = activity.getPackageName();
        intent.putExtra(Base.SDK_VER, 22);
        intent.putExtra(Base.APP_PKG, appPackage);
        intent.putExtra(Base.APP_KEY, appkey);
        intent.putExtra(SDK.FLAG, WBConstants.WEIBO_FLAG_SDK);
        intent.putExtra(WBConstants.SIGN, MD5.hexdigest(Utility.getSign(activity, appPackage)));
        if (data != null) {
            intent.putExtras(data);
        }
        try {
            LogUtil.m2214d(TAG, "intent=" + intent + ", extra=" + intent.getExtras());
            activity.startActivityForResult(intent, 765);
            return true;
        } catch (ActivityNotFoundException e) {
            LogUtil.m2215e(TAG, "Failed, target ActivityNotFound");
            return false;
        }
    }

    private void sendBroadcast(Context context, String action, String key, String packageName, Bundle data) {
        Intent intent = new Intent(action);
        String appPackage = context.getPackageName();
        intent.putExtra(Base.SDK_VER, 22);
        intent.putExtra(Base.APP_PKG, appPackage);
        intent.putExtra(Base.APP_KEY, key);
        intent.putExtra(SDK.FLAG, WBConstants.WEIBO_FLAG_SDK);
        intent.putExtra(WBConstants.SIGN, MD5.hexdigest(Utility.getSign(context, appPackage)));
        if (!TextUtils.isEmpty(packageName)) {
            intent.setPackage(packageName);
        }
        if (data != null) {
            intent.putExtras(data);
        }
        LogUtil.m2214d(TAG, "intent=" + intent + ", extra=" + intent.getExtras());
        context.sendBroadcast(intent, WBConstants.ACTION_WEIBO_SDK_PERMISSION);
    }
}
