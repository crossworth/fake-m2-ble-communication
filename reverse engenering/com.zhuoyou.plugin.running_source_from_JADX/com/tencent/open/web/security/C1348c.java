package com.tencent.open.web.security;

import android.webkit.WebView;
import com.tencent.open.C1317a.C1303a;
import com.tencent.open.p035c.C1334c;
import com.tencent.open.p036a.C1314f;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: ProGuard */
public class C1348c extends C1303a {
    private String f4241d;

    public C1348c(WebView webView, long j, String str, String str2) {
        super(webView, j, str);
        this.f4241d = str2;
    }

    public void mo2216a(Object obj) {
        C1314f.m3864a("openSDK_LOG.SecureJsListener", "-->onComplete, result: " + obj);
    }

    public void mo2215a() {
        C1314f.m3867b("openSDK_LOG.SecureJsListener", "-->onNoMatchMethod...");
    }

    public void mo2217a(String str) {
        C1314f.m3864a("openSDK_LOG.SecureJsListener", "-->onCustomCallback, js: " + str);
        JSONObject jSONObject = new JSONObject();
        int i = 0;
        if (!C1334c.f4174a) {
            i = -4;
        }
        try {
            jSONObject.put("result", i);
            jSONObject.put("sn", this.b);
            jSONObject.put("data", str);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        m3965b(jSONObject.toString());
    }

    private void m3965b(String str) {
        WebView webView = (WebView) this.a.get();
        if (webView != null) {
            StringBuffer stringBuffer = new StringBuffer("javascript:");
            stringBuffer.append("if(!!").append(this.f4241d).append("){");
            stringBuffer.append(this.f4241d);
            stringBuffer.append("(");
            stringBuffer.append(str);
            stringBuffer.append(")}");
            String stringBuffer2 = stringBuffer.toString();
            C1314f.m3864a("openSDK_LOG.SecureJsListener", "-->callback, callback: " + stringBuffer2);
            webView.loadUrl(stringBuffer2);
        }
    }
}
