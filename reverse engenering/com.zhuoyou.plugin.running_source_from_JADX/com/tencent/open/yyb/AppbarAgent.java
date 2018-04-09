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
import com.tencent.open.p036a.C1314f;
import com.tencent.open.utils.Global;
import com.tencent.open.utils.SystemUtils;
import com.tencent.open.yyb.C1362a.C1360a;
import com.umeng.facebook.internal.NativeProtocol;
import java.util.regex.Pattern;

/* compiled from: ProGuard */
public class AppbarAgent extends BaseApi {
    public static final String TO_APPBAR_DETAIL = "siteIndex";
    public static final String TO_APPBAR_NEWS = "myMessage";
    public static final String TO_APPBAR_SEND_BLOG = "newThread";
    public static final String wx_appid = "wx8e8dc60535c9cd93";
    private Bundle f4252a;
    private String f4253b;

    public AppbarAgent(QQToken qQToken) {
        super(qQToken);
    }

    public void startAppbarLabel(Activity activity, String str) {
        if (TextUtils.isEmpty(str)) {
            Toast.makeText(activity, Constants.MSG_PARAM_ERROR, 0).show();
            return;
        }
        this.f4252a = new Bundle();
        this.f4252a.putString(NativeProtocol.WEB_DIALOG_PARAMS, "label/" + str);
        startAppbar(activity, "sId");
    }

    public void startAppbarThread(Activity activity, String str) {
        if (m3980d(str)) {
            this.f4253b = str;
            startAppbar(activity, "toThread");
            return;
        }
        Toast.makeText(activity, Constants.MSG_PARAM_ERROR, 0).show();
    }

    public void startAppbar(Activity activity, String str) {
        if (m3976a(str)) {
            String c = m3979c(str);
            Object b = m3978b();
            if (TextUtils.isEmpty(b) || SystemUtils.compareVersion(b, "4.2") < 0) {
                m3975a(activity, c);
                return;
            }
            String str2 = c + m3973a();
            C1314f.m3864a("openSDK_LOG.AppbarAgent", "-->(AppbarAgent)startAppbar : yybUrl = " + str2);
            try {
                Intent intent = new Intent();
                intent.setClassName("com.tencent.android.qqdownloader", "com.tencent.assistant.activity.ExportBrowserActivity");
                intent.putExtra("com.tencent.assistant.BROWSER_URL", str2);
                activity.startActivity(intent);
                activity.overridePendingTransition(17432576, 17432577);
                return;
            } catch (Throwable e) {
                C1314f.m3868b("openSDK_LOG.AppbarAgent", "-->(AppbarAgent)startAppbar : ExportBrowserActivity not found, start H5", e);
                m3975a(activity, c);
                return;
            }
        }
        Toast.makeText(activity, Constants.MSG_PARAM_ERROR, 0).show();
    }

    private boolean m3976a(String str) {
        return TO_APPBAR_DETAIL.equals(str) || TO_APPBAR_NEWS.equals(str) || TO_APPBAR_SEND_BLOG.equals(str) || "sId".equals(str) || "toThread".equals(str);
    }

    private void m3975a(Activity activity, String str) {
        if (this.mToken != null) {
            Intent intent = new Intent(activity, AppbarActivity.class);
            intent.putExtra("appid", this.mToken.getAppId());
            if (!(this.mToken.getAccessToken() == null || this.mToken.getOpenId() == null)) {
                C1360a c1360a = new C1360a();
                c1360a.f4261b = this.mToken.getAccessToken();
                c1360a.f4262c = Long.parseLong(this.mToken.getAppId());
                c1360a.f4260a = this.mToken.getOpenId();
                C1362a.m3988a(activity, str, this.mToken.getOpenId(), this.mToken.getAccessToken(), this.mToken.getAppId());
            }
            intent.putExtra("url", str);
            C1314f.m3864a("openSDK_LOG.AppbarAgent", "-->(AppbarAgent)startAppbar H5 : url = " + str);
            try {
                activity.startActivityForResult(intent, Constants.REQUEST_APPBAR);
            } catch (Throwable e) {
                C1314f.m3868b("openSDK_LOG.AppbarAgent", "-->(AppbarAgent)startAppbar : activity not found, start H5", e);
            }
        }
    }

    private Bundle m3977b(String str) {
        Bundle bundle = new Bundle();
        bundle.putString("pkgName", Global.getContext().getPackageName());
        if (!(TO_APPBAR_DETAIL.equals(str) || TO_APPBAR_SEND_BLOG.equals(str))) {
            if (TO_APPBAR_NEWS.equals(str)) {
                bundle.putString("source", "myapp");
            } else if ("sId".equals(str)) {
                if (this.f4252a != null) {
                    bundle.putAll(this.f4252a);
                }
            } else if ("toThread".equals(str)) {
                str = String.format("sId/t/%s", new Object[]{this.f4253b});
            }
        }
        bundle.putString("route", str);
        return bundle;
    }

    private String m3979c(String str) {
        StringBuilder stringBuilder = new StringBuilder("http://m.wsq.qq.com/direct?");
        stringBuilder.append(m3974a(m3977b(str)));
        return stringBuilder.toString();
    }

    private String m3973a() {
        Bundle bundle = new Bundle();
        if (!(this.mToken == null || this.mToken.getAppId() == null || this.mToken.getAccessToken() == null || this.mToken.getOpenId() == null)) {
            bundle.putString("qOpenAppId", this.mToken.getAppId());
            bundle.putString("qOpenId", this.mToken.getOpenId());
            bundle.putString("qAccessToken", this.mToken.getAccessToken());
        }
        bundle.putString("qPackageName", Global.getContext().getPackageName());
        return "&" + m3974a(bundle);
    }

    private String m3974a(Bundle bundle) {
        if (bundle == null || bundle.isEmpty()) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (String str : bundle.keySet()) {
            stringBuilder.append(str).append("=").append(bundle.get(str)).append("&");
        }
        String str2 = stringBuilder.toString();
        if (str2.endsWith("&")) {
            str2 = str2.substring(0, str2.length() - 1);
        }
        C1314f.m3864a("openSDK_LOG.AppbarAgent", "-->encodeParams, result: " + str2);
        return str2;
    }

    private String m3978b() {
        try {
            PackageInfo packageInfo = Global.getContext().getPackageManager().getPackageInfo("com.tencent.android.qqdownloader", 0);
            if (packageInfo == null) {
                return null;
            }
            return packageInfo.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean m3980d(String str) {
        return !TextUtils.isEmpty(str) && Pattern.matches("^[1-9][0-9]*$", str);
    }
}
