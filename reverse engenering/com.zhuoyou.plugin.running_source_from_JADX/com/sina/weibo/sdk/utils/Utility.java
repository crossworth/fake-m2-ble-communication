package com.sina.weibo.sdk.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.TextUtils;
import com.sina.weibo.sdk.constant.WBConstants;
import com.sina.weibo.sdk.constant.WBConstants.Base;
import com.sina.weibo.sdk.utils.AidTask.AidInfo;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class Utility {
    private static final String DEFAULT_CHARSET = "UTF-8";
    private static final String WEIBO_IDENTITY_ACTION = "com.sina.weibo.action.sdkidentity";

    public static Bundle parseUrl(String url) {
        try {
            URL u = new URL(url);
            Bundle b = decodeUrl(u.getQuery());
            b.putAll(decodeUrl(u.getRef()));
            return b;
        } catch (MalformedURLException e) {
            return new Bundle();
        }
    }

    public static Bundle parseUri(String uri) {
        try {
            return decodeUrl(new URI(uri).getQuery());
        } catch (Exception e) {
            return new Bundle();
        }
    }

    public static Bundle decodeUrl(String s) {
        Bundle params = new Bundle();
        if (s != null) {
            for (String parameter : s.split("&")) {
                String[] v = parameter.split("=");
                try {
                    params.putString(URLDecoder.decode(v[0], "UTF-8"), URLDecoder.decode(v[1], "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        return params;
    }

    public static boolean isChineseLocale(Context context) {
        try {
            Locale locale = context.getResources().getConfiguration().locale;
            if (Locale.CHINA.equals(locale) || Locale.CHINESE.equals(locale) || Locale.SIMPLIFIED_CHINESE.equals(locale) || Locale.TAIWAN.equals(locale)) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    public static String generateGUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String getSign(Context context, String pkgName) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(pkgName, 64);
            for (Signature toByteArray : packageInfo.signatures) {
                byte[] str = toByteArray.toByteArray();
                if (str != null) {
                    return MD5.hexdigest(str);
                }
            }
            return null;
        } catch (NameNotFoundException e) {
            return null;
        }
    }

    public static String safeString(String orignal) {
        return TextUtils.isEmpty(orignal) ? "" : orignal;
    }

    public static String getAid(Context context, String appKey) {
        AidInfo aidInfo = AidTask.getInstance(context).getAidSync(appKey);
        if (aidInfo != null) {
            return aidInfo.getAid();
        }
        return "";
    }

    public static String generateUA(Context ctx) {
        StringBuilder buffer = new StringBuilder();
        buffer.append(Build.MANUFACTURER).append("-").append(Build.MODEL);
        buffer.append("_");
        buffer.append(VERSION.RELEASE);
        buffer.append("_");
        buffer.append("weibosdk");
        buffer.append("_");
        buffer.append(WBConstants.WEIBO_SDK_VERSION_CODE);
        buffer.append("_android");
        return buffer.toString();
    }

    public static String generateUAAid(Context ctx) {
        StringBuilder buffer = new StringBuilder();
        buffer.append(Build.MANUFACTURER).append("-").append(Build.MODEL);
        buffer.append("__");
        buffer.append("weibosdk");
        buffer.append("__");
        try {
            buffer.append(WBConstants.WEIBO_SDK_VERSION_CODE.replaceAll("\\s+", "_"));
        } catch (Exception e) {
            buffer.append("unknown");
        }
        buffer.append("__").append("android").append("__android").append(VERSION.RELEASE);
        return buffer.toString();
    }

    public static void openWeiboActivity(Context context, String action, Bundle bundle) {
        try {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.putExtra(Base.APP_PKG, context.getPackageName());
            intent.setData(Uri.parse(action));
            intent.setFlags(268435456);
            intent.putExtras(bundle);
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
        }
    }

    public static Boolean isWeiBoVersionSupportNewPay(Context context) {
        boolean z = false;
        Intent intent = new Intent(WEIBO_IDENTITY_ACTION);
        intent.addCategory("android.intent.category.DEFAULT");
        List<ResolveInfo> list = context.getPackageManager().queryIntentServices(intent, 0);
        if (list == null || list.isEmpty()) {
            return Boolean.valueOf(false);
        }
        int versionCode = 0;
        for (ResolveInfo ri : list) {
            if (!(ri.serviceInfo == null || ri.serviceInfo.applicationInfo == null || TextUtils.isEmpty(ri.serviceInfo.applicationInfo.packageName))) {
                try {
                    versionCode = context.getPackageManager().getPackageInfo(ri.serviceInfo.applicationInfo.packageName, 0).versionCode;
                } catch (NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        if (versionCode >= WBConstants.SDK_NEW_PAY_VERSION) {
            z = true;
        }
        return Boolean.valueOf(z);
    }
}
