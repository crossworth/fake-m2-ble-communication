package com.tencent.open.yyb;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.BaseApi;
import com.tencent.connect.common.Constants;
import com.tencent.open.SocialConstants;
import com.tencent.open.yyb.C0820b.C0818a;
import com.tencent.p004a.p005a.C1711d;
import com.tencent.utils.SystemUtils;

/* compiled from: ProGuard */
public class AppbarAgent extends BaseApi {
    public static final String TO_APPBAR_DETAIL = "siteIndex";
    public static final String TO_APPBAR_NEWS = "myMessage";
    public static final String TO_APPBAR_SEND_BLOG = "newThread";
    public static String wx_appid = "wx8e8dc60535c9cd93";
    private Activity f4686a;

    public AppbarAgent(Activity activity, QQToken qQToken) {
        super(activity.getApplicationContext(), qQToken);
        this.f4686a = activity;
    }

    public void startAppbar(String str) {
        if (m4851a(str)) {
            String d = m4855d(str);
            Object b = m4852b();
            if (TextUtils.isEmpty(b) || SystemUtils.compareVersion(b, "4.2") < 0) {
                m4853b(d);
                return;
            }
            String str2 = d + m4849a();
            C1711d.m4638b("openSDK_LOG", "-->(AppbarAgent)startAppbar : yybUrl = " + str2);
            try {
                Intent intent = new Intent();
                intent.setClassName("com.tencent.android.qqdownloader", "com.tencent.assistant.activity.ExportBrowserActivity");
                intent.putExtra("com.tencent.assistant.BROWSER_URL", str2);
                intent.addFlags(268435456);
                this.f4686a.startActivity(intent);
                return;
            } catch (Exception e) {
                C1711d.m4638b("openSDK_LOG", "-->(AppbarAgent)startAppbar : ExportBrowserActivity not found, start H5");
                m4853b(d);
                return;
            }
        }
        Toast.makeText(this.f4686a, Constants.MSG_PARAM_ERROR, 0).show();
    }

    private boolean m4851a(String str) {
        return TO_APPBAR_DETAIL.equals(str) || TO_APPBAR_NEWS.equals(str) || TO_APPBAR_SEND_BLOG.equals(str);
    }

    private void m4853b(String str) {
        Intent intent = new Intent(this.f4686a, AppbarActivity.class);
        intent.putExtra("appid", this.mToken.getAppId());
        if (!(this.mToken == null || this.mToken.getAccessToken() == null || this.mToken.getOpenId() == null)) {
            C0818a c0818a = new C0818a();
            c0818a.f2759b = this.mToken.getAccessToken();
            c0818a.f2760c = Long.parseLong(this.mToken.getAppId());
            c0818a.f2758a = this.mToken.getOpenId();
            C0820b.m2604a(this.f4686a, str, this.mToken.getOpenId(), this.mToken.getAccessToken(), this.mToken.getAppId());
        }
        intent.putExtra("url", str);
        intent.addFlags(268435456);
        C1711d.m4638b("openSDK_LOG", "-->(AppbarAgent)startAppbar H5 : url = " + str);
        try {
            this.f4686a.startActivity(intent);
        } catch (Exception e) {
            C1711d.m4638b("openSDK_LOG", "-->(AppbarAgent)startAppbar : activity not found, start H5");
        }
    }

    private Bundle m4854c(String str) {
        Bundle bundle = new Bundle();
        if (TO_APPBAR_DETAIL.equals(str) || TO_APPBAR_SEND_BLOG.equals(str)) {
            bundle.putString("pkgName", this.mContext.getPackageName());
        } else if (TO_APPBAR_NEWS.equals(str)) {
            bundle.putString(SocialConstants.PARAM_SOURCE, "myapp");
        }
        bundle.putString("route", str);
        return bundle;
    }

    private String m4855d(String str) {
        StringBuilder stringBuilder = new StringBuilder("http://mq.wsq.qq.com/direct?");
        stringBuilder.append(m4850a(m4854c(str)));
        return stringBuilder.toString();
    }

    private String m4849a() {
        Bundle bundle = new Bundle();
        if (!(this.mToken == null || this.mToken.getAppId() == null || this.mToken.getAccessToken() == null || this.mToken.getOpenId() == null)) {
            bundle.putString("qOpenAppId", this.mToken.getAppId());
            bundle.putString("qOpenId", this.mToken.getOpenId());
            bundle.putString("qAccessToken", this.mToken.getAccessToken());
        }
        bundle.putString("qPackageName", this.mContext.getPackageName());
        return "&" + m4850a(bundle);
    }

    private String m4850a(Bundle bundle) {
        if (bundle == null || bundle.isEmpty()) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (String str : bundle.keySet()) {
            stringBuilder.append(str).append("=").append(bundle.get(str)).append("&");
        }
        return stringBuilder.substring(0, stringBuilder.length() - 1);
    }

    private String m4852b() {
        try {
            PackageInfo packageInfo = this.mContext.getPackageManager().getPackageInfo("com.tencent.android.qqdownloader", 0);
            if (packageInfo == null) {
                return null;
            }
            return packageInfo.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
