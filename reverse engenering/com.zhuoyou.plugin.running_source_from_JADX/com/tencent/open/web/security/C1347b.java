package com.tencent.open.web.security;

import android.net.Uri;
import android.text.TextUtils;
import android.webkit.WebView;
import com.tencent.open.C1317a;
import com.tencent.open.C1317a.C1277b;
import com.tencent.open.C1317a.C1303a;
import com.tencent.open.p036a.C1314f;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: ProGuard */
public class C1347b extends C1317a {
    public void mo2213a(String str, String str2, List<String> list, C1303a c1303a) {
        C1314f.m3864a("openSDK_LOG.SecureJsBridge", "-->getResult, objectName: " + str + " | methodName: " + str2);
        int size = list.size();
        for (int i = 0; i < size; i++) {
            try {
                list.set(i, URLDecoder.decode((String) list.get(i), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        C1277b c1277b = (C1277b) this.a.get(str);
        if (c1277b != null) {
            C1314f.m3867b("openSDK_LOG.SecureJsBridge", "-->handler != null");
            c1277b.call(str2, list, c1303a);
            return;
        }
        C1314f.m3867b("openSDK_LOG.SecureJsBridge", "-->handler == null");
        if (c1303a != null) {
            c1303a.mo2215a();
        }
    }

    public boolean mo2214a(WebView webView, String str) {
        C1314f.m3864a("openSDK_LOG.SecureJsBridge", "-->canHandleUrl---url = " + str);
        if (str == null) {
            return false;
        }
        if (!Uri.parse(str).getScheme().equals("jsbridge")) {
            return false;
        }
        ArrayList arrayList = new ArrayList(Arrays.asList((str + "/#").split("/")));
        if (arrayList.size() < 7) {
            return false;
        }
        String str2 = (String) arrayList.get(2);
        String str3 = (String) arrayList.get(3);
        String str4 = (String) arrayList.get(4);
        String str5 = (String) arrayList.get(5);
        C1314f.m3864a("openSDK_LOG.SecureJsBridge", "-->canHandleUrl, objectName: " + str2 + " | methodName: " + str3 + " | snStr: " + str4);
        if (TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3) || TextUtils.isEmpty(str4)) {
            return false;
        }
        try {
            mo2213a(str2, str3, arrayList.subList(6, arrayList.size() - 1), new C1348c(webView, Long.parseLong(str4), str, str5));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
