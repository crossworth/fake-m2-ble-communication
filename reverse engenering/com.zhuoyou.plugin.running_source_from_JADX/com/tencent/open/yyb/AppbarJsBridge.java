package com.tencent.open.yyb;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.text.TextUtils;
import android.webkit.WebView;
import com.tencent.open.p036a.C1314f;
import com.tencent.open.utils.Util;
import com.umeng.facebook.internal.ServerProtocol;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: ProGuard */
public class AppbarJsBridge {
    public static final int AUTHORIZE_FAIL = -5;
    public static final String BUTTON_CLICK_CALLBACK_FUNCTION_NAME = "clickCallback";
    public static final String CALLBACK_LOGIN = "loginCallback";
    public static final String CALLBACK_SHARE = "shareCallback";
    public static final int Code_Java_Exception = -3;
    public static final int Code_None = -2;
    public static final int JSBRIDGE_VERSION = 1;
    public static final String JS_BRIDGE_SCHEME = "jsb://";
    public static final String READY_CALLBACK_FUNCTION_NAME = "readyCallback";
    public static final int Result_Fail = -1;
    public static final int Result_OK = 0;
    public static final int SHARE_QQ = 1;
    public static final int SHARE_QZ = 2;
    public static final int SHARE_TIMELINE = 4;
    public static final int SHARE_WX = 3;
    private WebView f4254a;
    private Activity f4255b;

    public AppbarJsBridge(Activity activity, WebView webView) {
        this.f4255b = activity;
        this.f4254a = webView;
    }

    public void closeWebView(Uri uri, int i, String str, String str2) {
        C1314f.m3864a("openSDK_LOG.AppbarJsBridge", "-->closeWebView : url = " + uri);
        this.f4255b.finish();
    }

    public void pageControl(Uri uri, int i, String str, String str2) {
        C1314f.m3864a("openSDK_LOG.AppbarJsBridge", "-->pageControl : url = " + uri);
        int parseIntValue = Util.parseIntValue(uri.getQueryParameter("type"));
        if (this.f4254a != null) {
            if (parseIntValue == 1) {
                this.f4254a.goBack();
            } else if (parseIntValue == 2) {
                this.f4254a.goForward();
            } else {
                this.f4254a.reload();
            }
        }
        response(str2, i, str, "");
    }

    public void share(Uri uri, int i, String str, String str2) {
        C1314f.m3864a("openSDK_LOG.AppbarJsBridge", "-->share : url = " + uri);
        String queryParameter = uri.getQueryParameter("title");
        String queryParameter2 = uri.getQueryParameter("summary");
        String queryParameter3 = uri.getQueryParameter("iconUrl");
        if (TextUtils.isEmpty(queryParameter3)) {
            queryParameter3 = "http://qzs.qq.com/open/mobile/jsbridge/demo.htm";
        }
        String queryParameter4 = uri.getQueryParameter("jumpUrl");
        C1314f.m3864a("openSDK_LOG.AppbarJsBridge", "-->share : title = " + queryParameter);
        C1314f.m3864a("openSDK_LOG.AppbarJsBridge", "-->share : summary = " + queryParameter2);
        C1314f.m3864a("openSDK_LOG.AppbarJsBridge", "-->share : iconUrl = " + queryParameter3);
        C1314f.m3864a("openSDK_LOG.AppbarJsBridge", "-->share : jumpUrl = " + queryParameter4);
        ShareModel shareModel = new ShareModel();
        shareModel.f4256a = queryParameter;
        shareModel.f4257b = queryParameter2;
        shareModel.f4258c = queryParameter3;
        shareModel.f4259d = queryParameter4;
        ((AppbarActivity) this.f4255b).setShareModel(shareModel);
        switch (Util.parseIntValue(uri.getQueryParameter("type"), 0)) {
            case 1:
                ((AppbarActivity) this.f4255b).shareToQQ();
                return;
            case 2:
                ((AppbarActivity) this.f4255b).shareToQzone();
                return;
            case 3:
                ((AppbarActivity) this.f4255b).shareToWX();
                return;
            case 4:
                ((AppbarActivity) this.f4255b).shareToTimeline();
                return;
            default:
                ((AppbarActivity) this.f4255b).showFloatingDialog();
                return;
        }
    }

    public void getAppInfo(Uri uri, int i, String str, String str2) {
        Object queryParameter = uri.getQueryParameter("packagenames");
        C1314f.m3867b("openSDK_LOG.AppbarJsBridge", "-->getAppInfo : packageNames = " + queryParameter);
        if (!TextUtils.isEmpty(queryParameter) && !TextUtils.isEmpty(str2)) {
            String[] split = queryParameter.split(",");
            if (split != null && split.length != 0) {
                JSONObject jSONObject = new JSONObject();
                for (String trim : split) {
                    PackageInfo packageInfo;
                    String trim2 = trim.trim();
                    try {
                        packageInfo = this.f4255b.getPackageManager().getPackageInfo(trim2, 0);
                    } catch (Throwable e) {
                        C1314f.m3868b("openSDK_LOG.AppbarJsBridge", "-->getAppInfo : NameNotFoundException e1", e);
                        packageInfo = null;
                    }
                    try {
                        JSONObject jSONObject2 = new JSONObject();
                        if (packageInfo != null) {
                            jSONObject2.put("install", 1);
                            jSONObject2.put("appName", packageInfo.applicationInfo.name);
                            jSONObject2.put("verCode", packageInfo.versionCode);
                            jSONObject2.put("verName", packageInfo.versionName);
                        } else {
                            jSONObject2.put("install", 0);
                        }
                        jSONObject.put(trim2, jSONObject2);
                    } catch (Exception e2) {
                        responseFail(str2, i, str, -3);
                    }
                }
                C1314f.m3864a("openSDK_LOG.AppbarJsBridge", "-->getAppInfo : result = " + jSONObject.toString());
                response(str2, i, str, jSONObject.toString());
            }
        }
    }

    public void clickCallback() {
        response(BUTTON_CLICK_CALLBACK_FUNCTION_NAME, 0, null, "");
    }

    public void openNewWindow(Uri uri, int i, String str, String str2) {
        String queryParameter = uri.getQueryParameter("url");
        try {
            Intent intent = new Intent(this.f4255b, AppbarActivity.class);
            intent.putExtra("url", queryParameter);
            this.f4255b.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setWebView(Uri uri, int i, String str, String str2) {
        boolean z = true;
        try {
            Object queryParameter = uri.getQueryParameter("title");
            int parseIntValue = Util.parseIntValue(uri.getQueryParameter("buttonVisible"), 0);
            if (!TextUtils.isEmpty(queryParameter)) {
                ((AppbarActivity) this.f4255b).setAppbarTitle(queryParameter);
            }
            AppbarActivity appbarActivity = (AppbarActivity) this.f4255b;
            if (parseIntValue != 1) {
                z = false;
            }
            appbarActivity.setShareVisibility(z);
            C1314f.m3864a("openSDK_LOG.AppbarJsBridge", "-->setWebView : url = " + uri + " -- buttonVisiable = " + parseIntValue);
            response(str2, i, str, "");
        } catch (Exception e) {
            responseFail(str2, i, str, -3);
        }
    }

    public void openLoginActivity(Uri uri, int i, String str, String str2) {
        C1314f.m3864a("openSDK_LOG.AppbarJsBridge", "-->openLoginActivity : url = " + uri);
        ((AppbarActivity) this.f4255b).login();
    }

    public int getVersion() {
        return 1;
    }

    public void invoke(String str) {
        int i;
        C1314f.m3864a("openSDK_LOG.AppbarJsBridge", "-->invoke : url = " + str);
        Uri parse = Uri.parse(str);
        String host = parse.getHost();
        C1314f.m3864a("openSDK_LOG.AppbarJsBridge", "-->invoke : hostAsMethodName = " + host);
        if (!TextUtils.isEmpty(host)) {
            List pathSegments = parse.getPathSegments();
            String str2 = null;
            if (pathSegments == null || pathSegments.size() <= 0) {
                i = 0;
            } else {
                i = Util.parseIntValue((String) pathSegments.get(0));
                if (pathSegments.size() > 1) {
                    str2 = (String) pathSegments.get(1);
                }
            }
            C1314f.m3864a("openSDK_LOG.AppbarJsBridge", "-->invoke : seqid = " + i + " callbackName = " + str2);
            if (host.equals("callBatch")) {
                try {
                    JSONArray jSONArray = new JSONArray(parse.getQueryParameter("param"));
                    int length = jSONArray.length();
                    for (int i2 = 0; i2 < length; i2++) {
                        JSONObject jSONObject = jSONArray.getJSONObject(i2);
                        String string = jSONObject.getString("method");
                        int i3 = jSONObject.getInt("seqid");
                        String optString = jSONObject.optString("callback");
                        JSONObject jSONObject2 = jSONObject.getJSONObject("args");
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(JS_BRIDGE_SCHEME).append(string).append("/").append(i3).append("/").append(!TextUtils.isEmpty(optString) ? optString : "").append("?");
                        if (jSONObject2 != null) {
                            Iterator keys = jSONObject2.keys();
                            while (keys.hasNext()) {
                                String str3 = (String) keys.next();
                                stringBuilder.append(str3).append("=").append(Uri.encode(Uri.decode(jSONObject2.getString(str3)))).append("&");
                            }
                        }
                        m3981a(Uri.parse(stringBuilder.toString()), string, i3, optString);
                    }
                    return;
                } catch (Exception e) {
                    if (!TextUtils.isEmpty(str2)) {
                        responseFail(str2, i, host, -5);
                        return;
                    }
                    return;
                }
            }
            m3981a(parse, host, i, str2);
        }
    }

    private void m3981a(Uri uri, String str, int i, String str2) {
        C1314f.m3864a("openSDK_LOG.AppbarJsBridge", "-->callAMethod : uri = " + uri);
        if (m3982a(str)) {
            try {
                AppbarJsBridge.class.getDeclaredMethod(str, new Class[]{Uri.class, Integer.TYPE, String.class, String.class}).invoke(this, new Object[]{uri, Integer.valueOf(i), str, str2});
            } catch (Throwable e) {
                C1314f.m3865a("openSDK_LOG.AppbarJsBridge", "-->callAMethod : Exception = ", e);
                e.printStackTrace();
                if (!TextUtils.isEmpty(str2)) {
                    responseFail(str2, i, str, -3);
                }
            }
        } else if (!TextUtils.isEmpty(str2)) {
            responseFail(str2, i, str, -5);
        }
    }

    private boolean m3982a(String str) {
        return true;
    }

    public void ready() {
        response(READY_CALLBACK_FUNCTION_NAME, 1, null, ServerProtocol.DIALOG_RETURN_SCOPES_TRUE);
    }

    public void responseShare(int i) {
        Map hashMap = new HashMap();
        hashMap.put("type", i + "");
        response(CALLBACK_SHARE, 0, null, "0", hashMap);
    }

    public void responseShareFail(int i) {
        Map hashMap = new HashMap();
        hashMap.put("type", i + "");
        response(CALLBACK_SHARE, 0, null, "1", hashMap);
    }

    public void response(String str, int i, String str2, String str3) {
        response(str, i, str2, str3, null);
    }

    public void response(String str, int i, String str2, String str3, Map<String, String> map) {
        if (!TextUtils.isEmpty(str)) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("result", 0);
                jSONObject.put("data", str3);
                if (!TextUtils.isEmpty(str2)) {
                    jSONObject.put("method", str2);
                }
                jSONObject.put("seqid", i);
                if (map != null) {
                    for (String str4 : map.keySet()) {
                        jSONObject.put(str4, map.get(str4));
                    }
                }
                callback(str, jSONObject.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void responseFail(String str, int i, String str2, int i2) {
        if (!TextUtils.isEmpty(str)) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("result", -1);
                jSONObject.put("code", i2);
                jSONObject.put("method", str2);
                jSONObject.put("seqid", i);
                callback(str, jSONObject.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void callback(String str, String str2) {
        if (this.f4254a != null) {
            StringBuffer stringBuffer = new StringBuffer("javascript:");
            stringBuffer.append("if(!!").append(str).append("){");
            stringBuffer.append(str);
            stringBuffer.append("(");
            stringBuffer.append(str2);
            stringBuffer.append(")}");
            this.f4254a.loadUrl(stringBuffer.toString());
        }
    }
}