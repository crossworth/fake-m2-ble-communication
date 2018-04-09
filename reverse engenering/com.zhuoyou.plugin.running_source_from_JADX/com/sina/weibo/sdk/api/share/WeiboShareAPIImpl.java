package com.sina.weibo.sdk.api.share;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import com.sina.weibo.sdk.ApiUtils;
import com.sina.weibo.sdk.WeiboAppManager;
import com.sina.weibo.sdk.WeiboAppManager.WeiboInfo;
import com.sina.weibo.sdk.api.WeiboMessage;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.IWeiboHandler.Request;
import com.sina.weibo.sdk.api.share.IWeiboHandler.Response;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.cmd.WbAppActivator;
import com.sina.weibo.sdk.component.ShareRequestParam;
import com.sina.weibo.sdk.component.WeiboSdkBrowser;
import com.sina.weibo.sdk.constant.WBConstants;
import com.sina.weibo.sdk.constant.WBConstants.Base;
import com.sina.weibo.sdk.constant.WBConstants.SDK;
import com.sina.weibo.sdk.constant.WBPageConstants.Scheme;
import com.sina.weibo.sdk.exception.WeiboShareException;
import com.sina.weibo.sdk.utils.AidTask;
import com.sina.weibo.sdk.utils.LogUtil;
import com.sina.weibo.sdk.utils.MD5;
import com.sina.weibo.sdk.utils.Utility;

class WeiboShareAPIImpl implements IWeiboShareAPI {
    private static final String TAG = WeiboShareAPIImpl.class.getName();
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
        this.mWeiboInfo = WeiboAppManager.getInstance(context).getWeiboInfo();
        if (this.mWeiboInfo != null) {
            LogUtil.m3307d(TAG, this.mWeiboInfo.toString());
        } else {
            LogUtil.m3307d(TAG, "WeiboInfo is null");
        }
        AidTask.getInstance(context).aidTaskInit(appKey);
    }

    public int getWeiboAppSupportAPI() {
        return (this.mWeiboInfo == null || !this.mWeiboInfo.isLegal()) ? -1 : this.mWeiboInfo.getSupportApi();
    }

    public boolean isWeiboAppInstalled() {
        return this.mWeiboInfo != null && this.mWeiboInfo.isLegal();
    }

    public boolean isWeiboAppSupportAPI() {
        return getWeiboAppSupportAPI() >= ApiUtils.BUILD_INT;
    }

    public boolean isSupportWeiboPay() {
        return getWeiboAppSupportAPI() >= ApiUtils.BUILD_INT_VER_2_5;
    }

    public boolean registerApp() {
        sendBroadcast(this.mContext, WBConstants.ACTION_WEIBO_REGISTER, this.mAppKey, null, null);
        return true;
    }

    public boolean handleWeiboResponse(Intent intent, Response handler) {
        String appPackage = intent.getStringExtra(Base.APP_PKG);
        String transaction = intent.getStringExtra(WBConstants.TRAN);
        if (TextUtils.isEmpty(appPackage)) {
            LogUtil.m3308e(TAG, "handleWeiboResponse faild appPackage is null");
            return false;
        } else if (handler instanceof Activity) {
            Activity act = (Activity) handler;
            LogUtil.m3307d(TAG, "handleWeiboResponse getCallingPackage : " + act.getCallingPackage());
            if (TextUtils.isEmpty(transaction)) {
                LogUtil.m3308e(TAG, "handleWeiboResponse faild intent _weibo_transaction is null");
                return false;
            } else if (ApiUtils.validateWeiboSign(this.mContext, appPackage) || appPackage.equals(act.getPackageName())) {
                handler.onResponse(new SendMessageToWeiboResponse(intent.getExtras()));
                return true;
            } else {
                LogUtil.m3308e(TAG, "handleWeiboResponse faild appPackage validateSign faild");
                return false;
            }
        } else {
            LogUtil.m3308e(TAG, "handleWeiboResponse faild handler is not Activity");
            return false;
        }
    }

    public boolean handleWeiboRequest(Intent intent, Request handler) {
        if (intent == null || handler == null) {
            return false;
        }
        String appPackage = intent.getStringExtra(Base.APP_PKG);
        String transaction = intent.getStringExtra(WBConstants.TRAN);
        if (TextUtils.isEmpty(appPackage)) {
            LogUtil.m3308e(TAG, "handleWeiboRequest faild appPackage validateSign faild");
            handler.onRequest(null);
            return false;
        } else if (TextUtils.isEmpty(transaction)) {
            LogUtil.m3308e(TAG, "handleWeiboRequest faild intent _weibo_transaction is null");
            handler.onRequest(null);
            return false;
        } else if (ApiUtils.validateWeiboSign(this.mContext, appPackage)) {
            handler.onRequest(new ProvideMessageForWeiboRequest(intent.getExtras()));
            return true;
        } else {
            LogUtil.m3308e(TAG, "handleWeiboRequest faild appPackage validateSign faild");
            handler.onRequest(null);
            return false;
        }
    }

    public boolean launchWeibo(Activity act) {
        if (isWeiboAppInstalled()) {
            try {
                act.startActivity(act.getPackageManager().getLaunchIntentForPackage(this.mWeiboInfo.getPackageName()));
                return true;
            } catch (Exception e) {
                LogUtil.m3308e(TAG, e.getMessage());
                return false;
            }
        }
        LogUtil.m3308e(TAG, "launchWeibo faild WeiboInfo is null");
        return false;
    }

    public boolean sendRequest(Activity act, BaseRequest request) {
        if (request == null) {
            LogUtil.m3308e(TAG, "sendRequest faild request is null");
            return false;
        }
        try {
            if (!checkEnvironment(this.mNeedDownloadWeibo)) {
                return false;
            }
            if (request.check(this.mContext, this.mWeiboInfo, new VersionCheckHandler())) {
                WbAppActivator.getInstance(this.mContext, this.mAppKey).activateApp();
                Bundle data = new Bundle();
                request.toBundle(data);
                return launchWeiboActivity(act, WBConstants.ACTIVITY_WEIBO, this.mWeiboInfo.getPackageName(), this.mAppKey, data);
            }
            LogUtil.m3308e(TAG, "sendRequest faild request check faild");
            return false;
        } catch (Exception e) {
            LogUtil.m3308e(TAG, e.getMessage());
            return false;
        }
    }

    public boolean sendRequest(Activity act, BaseRequest request, AuthInfo authInfo, String token, WeiboAuthListener authListener) {
        if (request == null) {
            LogUtil.m3308e(TAG, "sendRequest faild request is null !");
            return false;
        } else if (!isWeiboAppInstalled() || !isWeiboAppSupportAPI()) {
            return startShareWeiboActivity(act, token, request, authListener);
        } else {
            if (getWeiboAppSupportAPI() >= ApiUtils.BUILD_INT_VER_2_2) {
                return sendRequest(act, request);
            }
            if (!(request instanceof SendMultiMessageToWeiboRequest)) {
                return sendRequest(act, request);
            }
            SendMultiMessageToWeiboRequest multiMessageReq = (SendMultiMessageToWeiboRequest) request;
            SendMessageToWeiboRequest singleMessageReq = new SendMessageToWeiboRequest();
            singleMessageReq.packageName = multiMessageReq.packageName;
            singleMessageReq.transaction = multiMessageReq.transaction;
            singleMessageReq.message = adapterMultiMessage2SingleMessage(multiMessageReq.multiMessage);
            return sendRequest(act, singleMessageReq);
        }
    }

    private WeiboMessage adapterMultiMessage2SingleMessage(WeiboMultiMessage multiMessage) {
        if (multiMessage == null) {
            return new WeiboMessage();
        }
        Bundle data = new Bundle();
        multiMessage.toBundle(data);
        return new WeiboMessage(data);
    }

    private boolean startShareWeiboActivity(Activity act, String token, BaseRequest request, WeiboAuthListener authListener) {
        try {
            WbAppActivator.getInstance(this.mContext, this.mAppKey).activateApp();
            Bundle data = new Bundle();
            String appPackage = act.getPackageName();
            ShareRequestParam param = new ShareRequestParam(act);
            param.setToken(token);
            param.setAppKey(this.mAppKey);
            param.setAppPackage(appPackage);
            param.setBaseRequest(request);
            param.setSpecifyTitle("微博分享");
            param.setAuthListener(authListener);
            Intent intent = new Intent(act, WeiboSdkBrowser.class);
            intent.putExtras(param.createRequestParamBundle());
            act.startActivity(intent);
            return true;
        } catch (ActivityNotFoundException e) {
            return false;
        }
    }

    public boolean sendResponse(BaseResponse response) {
        if (response == null) {
            LogUtil.m3308e(TAG, "sendResponse failed response null");
            return false;
        } else if (response.check(this.mContext, new VersionCheckHandler())) {
            Bundle data = new Bundle();
            response.toBundle(data);
            sendBroadcast(this.mContext, WBConstants.ACTION_WEIBO_RESPONSE, this.mAppKey, response.reqPackageName, data);
            return true;
        } else {
            LogUtil.m3308e(TAG, "sendResponse check fail");
            return false;
        }
    }

    private void registerWeiboDownloadListener(IWeiboDownloadListener listener) {
        this.mDownloadListener = listener;
    }

    private boolean checkEnvironment(boolean bShowDownloadDialog) throws WeiboShareException {
        if (isWeiboAppInstalled()) {
            if (!isWeiboAppSupportAPI()) {
                throw new WeiboShareException("Weibo do not support share api!");
            } else if (ApiUtils.validateWeiboSign(this.mContext, this.mWeiboInfo.getPackageName())) {
                return true;
            } else {
                throw new WeiboShareException("Weibo signature is incorrect!");
            }
        } else if (bShowDownloadDialog) {
            if (this.mDownloadConfirmDialog == null) {
                this.mDownloadConfirmDialog = WeiboDownloader.createDownloadConfirmDialog(this.mContext, this.mDownloadListener);
                this.mDownloadConfirmDialog.show();
            } else if (!this.mDownloadConfirmDialog.isShowing()) {
                this.mDownloadConfirmDialog.show();
            }
            return false;
        } else {
            throw new WeiboShareException("Weibo is not installed!");
        }
    }

    public boolean launchWeiboPay(Activity act, String payArgs) {
        Bundle bundle = new Bundle();
        bundle.putString("rawdata", payArgs);
        bundle.putInt(WBConstants.COMMAND_TYPE_KEY, 4);
        bundle.putString(WBConstants.TRAN, String.valueOf(System.currentTimeMillis()));
        return launchWeiboActivity(act, WBConstants.ACTIVITY_WEIBO_PAY, this.mWeiboInfo.getPackageName(), this.mAppKey, bundle);
    }

    public boolean launchWeiboPayLogin(Activity act, String payArgs) {
        if (!Utility.isWeiBoVersionSupportNewPay(act).booleanValue()) {
            return launchWeiboPay(act, payArgs);
        }
        if (act == null) {
            LogUtil.m3308e(TAG, "launchWeiboActivity fail, invalid arguments");
            return false;
        }
        Bundle bundle = new Bundle();
        bundle.putString("rawdata", payArgs);
        bundle.putInt(WBConstants.COMMAND_TYPE_KEY, 4);
        bundle.putString(WBConstants.TRAN, String.valueOf(System.currentTimeMillis()));
        Intent intent = new Intent();
        intent.setPackage(this.mWeiboInfo.getPackageName());
        intent.setData(Uri.parse(Scheme.SDK_DELIVER_SCHEME));
        String appPackage = act.getPackageName();
        intent.putExtra(Base.SDK_VER, WBConstants.WEIBO_SDK_VERSION_CODE);
        intent.putExtra(Base.APP_PKG, appPackage);
        intent.putExtra(Base.APP_KEY, this.mAppKey);
        intent.putExtra(SDK.FLAG, WBConstants.WEIBO_FLAG_SDK);
        intent.putExtra(WBConstants.SIGN, MD5.hexdigest(Utility.getSign(act, appPackage)));
        intent.putExtra(WBConstants.SDK_REAL_ACTION, WBConstants.ACTIVITY_WEIBO_PAY);
        intent.putExtra(WBConstants.SDK_IS_SCHEME, false);
        intent.putExtra(WBConstants.SDK_REQUESTCODE, 765);
        intent.putExtras(bundle);
        try {
            LogUtil.m3307d(TAG, "launchWeiboActivity intent=" + intent + ", extra=" + intent.getExtras());
            act.startActivityForResult(intent, 765);
            return true;
        } catch (ActivityNotFoundException e) {
            LogUtil.m3308e(TAG, e.getMessage());
            return false;
        }
    }

    private boolean launchWeiboActivity(Activity activity, String action, String pkgName, String appkey, Bundle data) {
        if (activity == null || TextUtils.isEmpty(action) || TextUtils.isEmpty(pkgName) || TextUtils.isEmpty(appkey)) {
            LogUtil.m3308e(TAG, "launchWeiboActivity fail, invalid arguments");
            return false;
        }
        Intent intent = new Intent();
        intent.setPackage(pkgName);
        intent.setAction(action);
        String appPackage = activity.getPackageName();
        intent.putExtra(Base.SDK_VER, WBConstants.WEIBO_SDK_VERSION_CODE);
        intent.putExtra(Base.APP_PKG, appPackage);
        intent.putExtra(Base.APP_KEY, appkey);
        intent.putExtra(SDK.FLAG, WBConstants.WEIBO_FLAG_SDK);
        intent.putExtra(WBConstants.SIGN, MD5.hexdigest(Utility.getSign(activity, appPackage)));
        intent.putExtra(WBConstants.TRAN, String.valueOf(System.currentTimeMillis()));
        if (data != null) {
            intent.putExtras(data);
        }
        try {
            LogUtil.m3307d(TAG, "launchWeiboActivity intent=" + intent + ", extra=" + intent.getExtras());
            activity.startActivityForResult(intent, 765);
            return true;
        } catch (ActivityNotFoundException e) {
            LogUtil.m3308e(TAG, e.getMessage());
            return false;
        }
    }

    private void sendBroadcast(Context context, String action, String key, String packageName, Bundle data) {
        Intent intent = new Intent(action);
        String appPackage = context.getPackageName();
        intent.putExtra(Base.SDK_VER, WBConstants.WEIBO_SDK_VERSION_CODE);
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
        LogUtil.m3307d(TAG, "intent=" + intent + ", extra=" + intent.getExtras());
        context.sendBroadcast(intent, WBConstants.ACTION_WEIBO_SDK_PERMISSION);
    }

    public void shareMessageToWeiyou(Context context, Bundle bundle) {
        Utility.openWeiboActivity(context, Scheme.SHARETOWEIYOU, bundle);
    }
}