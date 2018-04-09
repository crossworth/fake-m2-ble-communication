package com.umeng.analytics;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Message;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONObject;
import p031u.aly.au;

public class MobclickAgentJSInterface {
    private Context f3100a;

    final class C0917a extends WebChromeClient {
        WebChromeClient f3096a = null;
        final /* synthetic */ MobclickAgentJSInterface f3097b;
        private final String f3098c = au.aE;
        private final String f3099d = "event";

        public C0917a(MobclickAgentJSInterface mobclickAgentJSInterface, WebChromeClient webChromeClient) {
            this.f3097b = mobclickAgentJSInterface;
            if (webChromeClient == null) {
                this.f3096a = new WebChromeClient();
            } else {
                this.f3096a = webChromeClient;
            }
        }

        public boolean onJsPrompt(WebView webView, String str, String str2, String str3, JsPromptResult jsPromptResult) {
            if (au.aE.equals(str2)) {
                try {
                    JSONObject jSONObject = new JSONObject(str3);
                    Map hashMap = new HashMap();
                    String str4 = (String) jSONObject.remove("id");
                    int intValue = jSONObject.isNull("duration") ? 0 : ((Integer) jSONObject.remove("duration")).intValue();
                    Iterator keys = jSONObject.keys();
                    while (keys.hasNext()) {
                        String str5 = (String) keys.next();
                        hashMap.put(str5, jSONObject.getString(str5));
                    }
                    MobclickAgent.getAgent().m4941a(this.f3097b.f3100a, str4, hashMap, (long) intValue);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (!"event".equals(str2)) {
                return this.f3096a.onJsPrompt(webView, str, str2, str3, jsPromptResult);
            } else {
                try {
                    JSONObject jSONObject2 = new JSONObject(str3);
                    String optString = jSONObject2.optString("label");
                    if ("".equals(optString)) {
                        optString = null;
                    }
                    MobclickAgent.getAgent().m4939a(this.f3097b.f3100a, jSONObject2.getString("tag"), optString, (long) jSONObject2.optInt("duration"), 1);
                } catch (Exception e2) {
                }
            }
            jsPromptResult.confirm();
            return true;
        }

        public void onCloseWindow(WebView webView) {
            this.f3096a.onCloseWindow(webView);
        }

        public boolean onCreateWindow(WebView webView, boolean z, boolean z2, Message message) {
            return this.f3096a.onCreateWindow(webView, z, z2, message);
        }

        public boolean onJsAlert(WebView webView, String str, String str2, JsResult jsResult) {
            return this.f3096a.onJsAlert(webView, str, str2, jsResult);
        }

        public boolean onJsBeforeUnload(WebView webView, String str, String str2, JsResult jsResult) {
            return this.f3096a.onJsBeforeUnload(webView, str, str2, jsResult);
        }

        public boolean onJsConfirm(WebView webView, String str, String str2, JsResult jsResult) {
            return this.f3096a.onJsConfirm(webView, str, str2, jsResult);
        }

        public void onProgressChanged(WebView webView, int i) {
            this.f3096a.onProgressChanged(webView, i);
        }

        public void onReceivedIcon(WebView webView, Bitmap bitmap) {
            this.f3096a.onReceivedIcon(webView, bitmap);
        }

        public void onReceivedTitle(WebView webView, String str) {
            this.f3096a.onReceivedTitle(webView, str);
        }

        public void onRequestFocus(WebView webView) {
            this.f3096a.onRequestFocus(webView);
        }
    }

    public MobclickAgentJSInterface(Context context, WebView webView) {
        this.f3100a = context;
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new C0917a(this, null));
    }

    public MobclickAgentJSInterface(Context context, WebView webView, WebChromeClient webChromeClient) {
        this.f3100a = context;
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new C0917a(this, webChromeClient));
    }
}
