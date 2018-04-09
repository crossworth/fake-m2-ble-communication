package com.sina.weibo.sdk.auth.sso;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import com.facebook.internal.NativeProtocol;
import com.sina.sso.RemoteSSO;
import com.sina.sso.RemoteSSO.Stub;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuth;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.constant.WBConstants;
import com.sina.weibo.sdk.exception.WeiboDialogException;
import com.sina.weibo.sdk.utils.LogUtil;
import com.sina.weibo.sdk.utils.SecurityHelper;

public class SsoHandler {
    private static final String DEFAULT_SINA_WEIBO_PACKAGE_NAME = "com.sina.weibo";
    private static final String DEFAULT_WEIBO_REMOTE_SSO_SERVICE_NAME = "com.sina.weibo.remotessoservice";
    private static final int REQUEST_CODE_SSO_AUTH = 32973;
    private static final String TAG = "Weibo_SSO_login";
    private Activity mAuthActivity;
    private WeiboAuthListener mAuthListener;
    private ServiceConnection mConnection = new C06631();
    private int mSSOAuthRequestCode;
    private WeiboAuth mWeiboAuth;

    class C06631 implements ServiceConnection {
        C06631() {
        }

        public void onServiceDisconnected(ComponentName name) {
            SsoHandler.this.mWeiboAuth.anthorize(SsoHandler.this.mAuthListener);
        }

        public void onServiceConnected(ComponentName name, IBinder service) {
            RemoteSSO remoteSSOservice = Stub.asInterface(service);
            try {
                String ssoPackageName = remoteSSOservice.getPackageName();
                String ssoActivityName = remoteSSOservice.getActivityName();
                SsoHandler.this.mAuthActivity.getApplicationContext().unbindService(SsoHandler.this.mConnection);
                if (!SsoHandler.this.startSingleSignOn(ssoPackageName, ssoActivityName)) {
                    SsoHandler.this.mWeiboAuth.anthorize(SsoHandler.this.mAuthListener);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public SsoHandler(Activity activity, WeiboAuth weiboAuth) {
        this.mAuthActivity = activity;
        this.mWeiboAuth = weiboAuth;
    }

    public void authorize(WeiboAuthListener listener) {
        authorize(32973, listener, null);
    }

    public void authorize(WeiboAuthListener listener, String packageName) {
        authorize(32973, listener, packageName);
    }

    public void authorize(int requestCode, WeiboAuthListener listener, String packageName) {
        this.mSSOAuthRequestCode = requestCode;
        this.mAuthListener = listener;
        if (!bindRemoteSSOService(this.mAuthActivity.getApplicationContext(), packageName) && this.mWeiboAuth != null) {
            this.mWeiboAuth.anthorize(this.mAuthListener);
        }
    }

    public void authorizeCallBack(int requestCode, int resultCode, Intent data) {
        LogUtil.m2214d(TAG, "requestCode: " + requestCode + ", resultCode: " + resultCode + ", data: " + data);
        if (requestCode != this.mSSOAuthRequestCode) {
            return;
        }
        if (resultCode == -1) {
            if (SecurityHelper.checkResponseAppLegal(this.mAuthActivity, data)) {
                String error = data.getStringExtra("error");
                if (error == null) {
                    error = data.getStringExtra(NativeProtocol.BRIDGE_ARG_ERROR_TYPE);
                }
                if (error == null) {
                    Bundle bundle = data.getExtras();
                    Oauth2AccessToken accessToken = Oauth2AccessToken.parseAccessToken(bundle);
                    if (accessToken == null || !accessToken.isSessionValid()) {
                        LogUtil.m2214d(TAG, "Failed to receive access token by SSO");
                        this.mWeiboAuth.anthorize(this.mAuthListener);
                        return;
                    }
                    LogUtil.m2214d(TAG, "Login Success! " + accessToken.toString());
                    this.mAuthListener.onComplete(bundle);
                } else if (error.equals("access_denied") || error.equals("OAuthAccessDeniedException")) {
                    LogUtil.m2214d(TAG, "Login canceled by user.");
                    this.mAuthListener.onCancel();
                } else {
                    String description = data.getStringExtra(NativeProtocol.BRIDGE_ARG_ERROR_DESCRIPTION);
                    if (description != null) {
                        error = new StringBuilder(String.valueOf(error)).append(":").append(description).toString();
                    }
                    LogUtil.m2214d(TAG, "Login failed: " + error);
                    this.mAuthListener.onWeiboException(new WeiboDialogException(error, resultCode, description));
                }
            }
        } else if (resultCode != 0) {
        } else {
            if (data != null) {
                LogUtil.m2214d(TAG, "Login failed: " + data.getStringExtra("error"));
                this.mAuthListener.onWeiboException(new WeiboDialogException(data.getStringExtra("error"), data.getIntExtra("error_code", -1), data.getStringExtra("failing_url")));
                return;
            }
            LogUtil.m2214d(TAG, "Login canceled by user.");
            this.mAuthListener.onCancel();
        }
    }

    public static ComponentName isServiceExisted(Context context, String packageName) {
        for (RunningServiceInfo runningServiceInfo : ((ActivityManager) context.getSystemService("activity")).getRunningServices(ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED)) {
            ComponentName serviceName = runningServiceInfo.service;
            if (serviceName.getPackageName().equals(packageName) && serviceName.getClassName().equals(new StringBuilder(String.valueOf(packageName)).append(".business.RemoteSSOService").toString())) {
                return serviceName;
            }
        }
        return null;
    }

    private boolean bindRemoteSSOService(Context context, String packageName) {
        String tempPkgName;
        if (TextUtils.isEmpty(packageName) || packageName.trim().equals("")) {
            tempPkgName = DEFAULT_SINA_WEIBO_PACKAGE_NAME;
        } else {
            tempPkgName = packageName;
        }
        Intent intent = new Intent(DEFAULT_WEIBO_REMOTE_SSO_SERVICE_NAME);
        intent.setPackage(tempPkgName);
        if (context.bindService(intent, this.mConnection, 1)) {
            return true;
        }
        return context.bindService(new Intent(DEFAULT_WEIBO_REMOTE_SSO_SERVICE_NAME), this.mConnection, 1);
    }

    private boolean startSingleSignOn(String ssoPackageName, String ssoActivityName) {
        boolean bSucceed = true;
        Intent intent = new Intent();
        intent.setClassName(ssoPackageName, ssoActivityName);
        intent.putExtras(this.mWeiboAuth.getAuthInfo().getAuthBundle());
        intent.putExtra(WBConstants.COMMAND_TYPE_KEY, 3);
        intent.putExtra(WBConstants.TRAN, String.valueOf(System.currentTimeMillis()));
        if (!SecurityHelper.validateAppSignatureForIntent(this.mAuthActivity, intent)) {
            return false;
        }
        try {
            this.mAuthActivity.startActivityForResult(intent, this.mSSOAuthRequestCode);
        } catch (ActivityNotFoundException e) {
            bSucceed = false;
        }
        return bSucceed;
    }
}
