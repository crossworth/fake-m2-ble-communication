package com.tencent.connect.auth;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.webkit.CookieSyncManager;
import android.widget.Toast;
import com.facebook.internal.ServerProtocol;
import com.tencent.connect.common.BaseApi;
import com.tencent.connect.p010a.C0687a;
import com.tencent.p004a.p005a.C1711d;
import com.tencent.p004a.p008b.C0678a;
import com.tencent.tauth.IUiListener;
import com.tencent.utils.ApkExternalInfoTool;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/* compiled from: ProGuard */
public class QQAuth {
    private static HashMap<String, QQAuth> f2376c = null;
    private AuthAgent f2377a;
    private QQToken f2378b;

    private QQAuth(String str, Context context) {
        C1711d.m4636a("openSDK_LOG", "new Tencent() --start");
        this.f2378b = new QQToken(str);
        this.f2377a = new AuthAgent(context, this.f2378b);
        C0687a.m2309c(context, this.f2378b);
        C1711d.m4636a("openSDK_LOG", "new Tencent() --end");
    }

    public static QQAuth createInstance(String str, Context context) {
        C0678a.m2291a(context.getApplicationContext());
        C1711d.m4636a("openSDK_LOG", "createInstance() --start");
        if (f2376c == null) {
            f2376c = new HashMap();
        } else if (f2376c.containsKey(str)) {
            C1711d.m4636a("openSDK_LOG", "createInstance() ,sessionMap.containsKey --end");
            return (QQAuth) f2376c.get(str);
        }
        try {
            PackageManager packageManager = context.getPackageManager();
            packageManager.getActivityInfo(new ComponentName(context.getPackageName(), "com.tencent.tauth.AuthActivity"), 0);
            packageManager.getActivityInfo(new ComponentName(context.getPackageName(), "com.tencent.connect.common.AssistActivity"), 0);
            QQAuth qQAuth = new QQAuth(str, context);
            f2376c.put(str, qQAuth);
            C1711d.m4636a("openSDK_LOG", "createInstance()  --end");
            return qQAuth;
        } catch (Throwable e) {
            C1711d.m4637a("openSDK_LOG", "createInstance() error --end", e);
            Toast.makeText(context.getApplicationContext(), "请参照文档在Androidmanifest.xml加上AuthActivity和AssitActivity的定义 ", 1).show();
            return null;
        }
    }

    public int login(Activity activity, String str, IUiListener iUiListener) {
        C1711d.m4636a("openSDK_LOG", "login()");
        return login(activity, str, iUiListener, "");
    }

    public int login(Activity activity, String str, IUiListener iUiListener, String str2) {
        String str3;
        C1711d.m4638b("openSDK_LOG", "-->login activity: " + activity);
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
                    C1711d.m4638b("openSDK_LOG", "-->login channelId: " + readChannelId);
                    return loginWithOEM(activity, str, iUiListener, readChannelId, readChannelId, "");
                }
            } catch (IOException e) {
                C1711d.m4638b("openSDK_LOG", "-->login get channel id exception." + e.getMessage());
                e.printStackTrace();
            }
        }
        C1711d.m4638b("openSDK_LOG", "-->login channelId is null ");
        BaseApi.isOEM = false;
        return this.f2377a.doLogin(activity, str, iUiListener);
    }

    public int loginWithOEM(Activity activity, String str, IUiListener iUiListener, String str2, String str3, String str4) {
        C1711d.m4638b("openSDK_LOG", "loginWithOEM");
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
        return this.f2377a.doLogin(activity, str, iUiListener);
    }

    public int reAuth(Activity activity, String str, IUiListener iUiListener) {
        C1711d.m4636a("openSDK_LOG", "reAuth()");
        return this.f2377a.doLogin(activity, str, iUiListener, true, true);
    }

    public void logout(Context context) {
        C1711d.m4636a("openSDK_LOG", "logout() --start");
        CookieSyncManager.createInstance(context);
        setAccessToken(null, null);
        setOpenId(context, null);
        C1711d.m4636a("openSDK_LOG", "logout() --end");
    }

    public QQToken getQQToken() {
        return this.f2378b;
    }

    public void setAccessToken(String str, String str2) {
        C1711d.m4636a("openSDK_LOG", "setAccessToken(), validTimeInSecond = " + str2 + "");
        this.f2378b.setAccessToken(str, str2);
    }

    public boolean isSessionValid() {
        C1711d.m4636a("openSDK_LOG", "isSessionValid(), result = " + (this.f2378b.isSessionValid() ? ServerProtocol.DIALOG_RETURN_SCOPES_TRUE : "false") + "");
        return this.f2378b.isSessionValid();
    }

    public void setOpenId(Context context, String str) {
        C1711d.m4636a("openSDK_LOG", "setOpenId() --start");
        this.f2378b.setOpenId(str);
        C0687a.m2310d(context, this.f2378b);
        C1711d.m4636a("openSDK_LOG", "setOpenId() --end");
    }

    public boolean onActivityResult(int i, int i2, Intent intent) {
        C1711d.m4639c("openSDK_LOG", "onActivityResult() ,resultCode = " + i2 + "");
        this.f2377a.onActivityResult(i, i2, intent);
        return true;
    }
}
