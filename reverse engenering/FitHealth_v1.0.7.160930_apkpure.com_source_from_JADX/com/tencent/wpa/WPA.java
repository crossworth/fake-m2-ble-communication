package com.tencent.wpa;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Base64;
import com.tencent.connect.auth.QQAuth;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.BaseApi;
import com.tencent.connect.common.BaseApi.TempRequestListener;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import com.tencent.utils.HttpUtils;

/* compiled from: ProGuard */
public class WPA extends BaseApi {
    public WPA(Context context, QQAuth qQAuth, QQToken qQToken) {
        super(context, qQAuth, qQToken);
    }

    public WPA(Context context, QQToken qQToken) {
        super(context, qQToken);
    }

    public void getWPAUserOnlineState(String str, IUiListener iUiListener) {
        if (str == null) {
            try {
                throw new Exception("uin null");
            } catch (Exception e) {
                if (iUiListener != null) {
                    iUiListener.onError(new UiError(-5, Constants.MSG_PARAM_ERROR, null));
                }
            }
        } else if (str.length() < 5) {
            throw new Exception("uin length < 5");
        } else {
            int i = 0;
            while (i < str.length()) {
                if (Character.isDigit(str.charAt(i))) {
                    i++;
                } else {
                    throw new Exception("uin not digit");
                }
            }
            HttpUtils.requestAsync(this.mToken, this.mContext, "http://webpresence.qq.com/getonline?Type=1&" + str + ":", null, "GET", new TempRequestListener(iUiListener));
        }
    }

    public int startWPAConversation(String str, String str2) {
        String str3 = "mqqwpa://im/chat?chat_type=wpa&uin=%1$s&version=1&src_type=app&attach_content=%2$s";
        Intent intent = new Intent("android.intent.action.VIEW");
        if (TextUtils.isEmpty(str)) {
            return -1;
        }
        if (str.length() < 5) {
            return -3;
        }
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return -4;
            }
        }
        str3 = "";
        if (!TextUtils.isEmpty(str2)) {
            str3 = Base64.encodeToString(str2.getBytes(), 2);
        }
        intent.setData(Uri.parse(String.format("mqqwpa://im/chat?chat_type=wpa&uin=%1$s&version=1&src_type=app&attach_content=%2$s", new Object[]{str, str3})));
        PackageManager packageManager = this.mContext.getPackageManager();
        if (packageManager.queryIntentActivities(intent, 65536).size() > 0) {
            this.mContext.startActivity(intent);
            return 0;
        }
        intent.setData(Uri.parse("http://www.myapp.com/forward/a/45592?g_f=990935"));
        if (packageManager.queryIntentActivities(intent, 65536).size() <= 0) {
            return -2;
        }
        this.mContext.startActivity(intent);
        return 0;
    }
}
