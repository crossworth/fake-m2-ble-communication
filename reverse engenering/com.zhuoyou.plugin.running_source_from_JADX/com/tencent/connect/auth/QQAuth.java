package com.tencent.connect.auth;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.webkit.CookieSyncManager;
import android.widget.Toast;
import com.tencent.connect.common.BaseApi;
import com.tencent.connect.p025a.C1148a;
import com.tencent.open.p036a.C1314f;
import com.tencent.open.utils.ApkExternalInfoTool;
import com.tencent.open.utils.Global;
import com.tencent.tauth.IUiListener;
import com.umeng.facebook.internal.ServerProtocol;
import java.io.File;

/* compiled from: ProGuard */
public class QQAuth {
    private AuthAgent f3583a = new AuthAgent(this.f3584b);
    private QQToken f3584b;

    private QQAuth(String str, Context context) {
        C1314f.m3870c("openSDK_LOG.QQAuth", "new QQAuth() --start");
        this.f3584b = new QQToken(str);
        C1148a.m3347c(context, this.f3584b);
        C1314f.m3870c("openSDK_LOG.QQAuth", "new QQAuth() --end");
    }

    public static QQAuth createInstance(String str, Context context) {
        Global.setContext(context.getApplicationContext());
        C1314f.m3870c("openSDK_LOG.QQAuth", "QQAuth -- createInstance() --start");
        try {
            PackageManager packageManager = context.getPackageManager();
            packageManager.getActivityInfo(new ComponentName(context.getPackageName(), "com.tencent.tauth.AuthActivity"), 0);
            packageManager.getActivityInfo(new ComponentName(context.getPackageName(), "com.tencent.connect.common.AssistActivity"), 0);
            QQAuth qQAuth = new QQAuth(str, context);
            C1314f.m3870c("openSDK_LOG.QQAuth", "QQAuth -- createInstance()  --end");
            return qQAuth;
        } catch (Throwable e) {
            C1314f.m3868b("openSDK_LOG.QQAuth", "createInstance() error --end", e);
            Toast.makeText(context.getApplicationContext(), "请参照文档在Androidmanifest.xml加上AuthActivity和AssitActivity的定义 ", 1).show();
            return null;
        }
    }

    public int login(Activity activity, String str, IUiListener iUiListener) {
        C1314f.m3870c("openSDK_LOG.QQAuth", "login()");
        return login(activity, str, iUiListener, "");
    }

    public int login(Activity activity, String str, IUiListener iUiListener, String str2) {
        C1314f.m3870c("openSDK_LOG.QQAuth", "-->login activity: " + activity);
        return m3398a(activity, null, str, iUiListener, str2);
    }

    public int login(Fragment fragment, String str, IUiListener iUiListener, String str2) {
        Activity activity = fragment.getActivity();
        C1314f.m3870c("openSDK_LOG.QQAuth", "-->login activity: " + activity);
        return m3398a(activity, fragment, str, iUiListener, str2);
    }

    private int m3398a(Activity activity, Fragment fragment, String str, IUiListener iUiListener, String str2) {
        String str3;
        String packageName = activity.getApplicationContext().getPackageName();
        for (ApplicationInfo applicationInfo : activity.getPackageManager().getInstalledApplications(128)) {
            if (packageName.equals(applicationInfo.packageName)) {
                str3 = applicationInfo.sourceDir;
                break;
            }
        }
        str3 = null;
        if (str3 != null) {
            try {
                String readChannelId = ApkExternalInfoTool.readChannelId(new File(str3));
                if (!TextUtils.isEmpty(readChannelId)) {
                    C1314f.m3864a("openSDK_LOG.QQAuth", "-->login channelId: " + readChannelId);
                    return loginWithOEM(activity, str, iUiListener, readChannelId, readChannelId, "");
                }
            } catch (Throwable e) {
                C1314f.m3868b("openSDK_LOG.QQAuth", "-->login get channel id exception.", e);
                e.printStackTrace();
            }
        }
        C1314f.m3867b("openSDK_LOG.QQAuth", "-->login channelId is null ");
        BaseApi.isOEM = false;
        return this.f3583a.doLogin(activity, str, iUiListener, false, fragment);
    }

    @Deprecated
    public int loginWithOEM(Activity activity, String str, IUiListener iUiListener, String str2, String str3, String str4) {
        C1314f.m3870c("openSDK_LOG.QQAuth", "loginWithOEM");
        BaseApi.isOEM = true;
        if (str2.equals("")) {
            str2 = "null";
        }
        if (str3.equals("")) {
            str3 = "null";
        }
        if (str4.equals("")) {
            str4 = "null";
        }
        BaseApi.installChannel = str3;
        BaseApi.registerChannel = str2;
        BaseApi.businessId = str4;
        return this.f3583a.doLogin(activity, str, iUiListener);
    }

    public int reAuth(Activity activity, String str, IUiListener iUiListener) {
        C1314f.m3870c("openSDK_LOG.QQAuth", "reAuth()");
        return this.f3583a.doLogin(activity, str, iUiListener, true, null);
    }

    public void reportDAU() {
        this.f3583a.m3363a(null);
    }

    public void checkLogin(IUiListener iUiListener) {
        this.f3583a.m3364b(iUiListener);
    }

    public void logout(Context context) {
        C1314f.m3870c("openSDK_LOG.QQAuth", "logout() --start");
        CookieSyncManager.createInstance(context);
        setAccessToken(null, null);
        setOpenId(context, null);
        C1314f.m3870c("openSDK_LOG.QQAuth", "logout() --end");
    }

    public QQToken getQQToken() {
        return this.f3584b;
    }

    public void setAccessToken(String str, String str2) {
        C1314f.m3864a("openSDK_LOG.QQAuth", "setAccessToken(), validTimeInSecond = " + str2 + "");
        this.f3584b.setAccessToken(str, str2);
    }

    public boolean isSessionValid() {
        C1314f.m3864a("openSDK_LOG.QQAuth", "isSessionValid(), result = " + (this.f3584b.isSessionValid() ? ServerProtocol.DIALOG_RETURN_SCOPES_TRUE : "false") + "");
        return this.f3584b.isSessionValid();
    }

    public void setOpenId(Context context, String str) {
        C1314f.m3864a("openSDK_LOG.QQAuth", "setOpenId() --start");
        this.f3584b.setOpenId(str);
        C1148a.m3348d(context, this.f3584b);
        C1314f.m3864a("openSDK_LOG.QQAuth", "setOpenId() --end");
    }

    public boolean onActivityResult(int i, int i2, Intent intent) {
        C1314f.m3870c("openSDK_LOG.QQAuth", "onActivityResult() ,resultCode = " + i2 + "");
        return true;
    }
}
