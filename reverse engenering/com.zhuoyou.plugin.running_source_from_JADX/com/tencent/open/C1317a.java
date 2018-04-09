package com.tencent.open;

import android.net.Uri;
import android.webkit.WebView;
import com.tencent.open.p036a.C1314f;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/* compiled from: ProGuard */
public class C1317a {
    protected HashMap<String, C1277b> f4131a = new HashMap();

    /* compiled from: ProGuard */
    public static class C1277b {
        public void call(String str, List<String> list, C1303a c1303a) {
            Method method = null;
            for (Method method2 : getClass().getDeclaredMethods()) {
                if (method2.getName().equals(str) && method2.getParameterTypes().length == list.size()) {
                    method = method2;
                    break;
                }
            }
            if (method != null) {
                try {
                    Object invoke;
                    switch (list.size()) {
                        case 0:
                            invoke = method.invoke(this, new Object[0]);
                            break;
                        case 1:
                            invoke = method.invoke(this, new Object[]{list.get(0)});
                            break;
                        case 2:
                            invoke = method.invoke(this, new Object[]{list.get(0), list.get(1)});
                            break;
                        case 3:
                            invoke = method.invoke(this, new Object[]{list.get(0), list.get(1), list.get(2)});
                            break;
                        case 4:
                            invoke = method.invoke(this, new Object[]{list.get(0), list.get(1), list.get(2), list.get(3)});
                            break;
                        case 5:
                            invoke = method.invoke(this, new Object[]{list.get(0), list.get(1), list.get(2), list.get(3), list.get(4)});
                            break;
                        default:
                            invoke = method.invoke(this, new Object[]{list.get(0), list.get(1), list.get(2), list.get(3), list.get(4), list.get(5)});
                            break;
                    }
                    Class returnType = method.getReturnType();
                    C1314f.m3867b("openSDK_LOG.JsBridge", "-->call, result: " + invoke + " | ReturnType: " + returnType.getName());
                    if ("void".equals(returnType.getName()) || returnType == Void.class) {
                        if (c1303a != null) {
                            c1303a.mo2216a(null);
                        }
                    } else if (c1303a != null && customCallback()) {
                        c1303a.mo2217a(invoke != null ? invoke.toString() : null);
                    }
                } catch (Throwable e) {
                    C1314f.m3868b("openSDK_LOG.JsBridge", "-->handler call mehtod ex. targetMethod: " + method, e);
                    if (c1303a != null) {
                        c1303a.mo2215a();
                    }
                }
            } else if (c1303a != null) {
                c1303a.mo2215a();
            }
        }

        public boolean customCallback() {
            return false;
        }
    }

    /* compiled from: ProGuard */
    public static class C1303a {
        protected WeakReference<WebView> f4079a;
        protected long f4080b;
        protected String f4081c;

        public C1303a(WebView webView, long j, String str) {
            this.f4079a = new WeakReference(webView);
            this.f4080b = j;
            this.f4081c = str;
        }

        public void mo2216a(Object obj) {
            WebView webView = (WebView) this.f4079a.get();
            if (webView != null) {
                String str = "'undefined'";
                if (obj instanceof String) {
                    str = "'" + ((String) obj).replace("\\", "\\\\").replace("'", "\\'") + "'";
                } else if ((obj instanceof Number) || (obj instanceof Long) || (obj instanceof Integer) || (obj instanceof Double) || (obj instanceof Float)) {
                    str = obj.toString();
                } else if (obj instanceof Boolean) {
                    str = obj.toString();
                }
                webView.loadUrl("javascript:window.JsBridge&&JsBridge.callback(" + this.f4080b + ",{'r':0,'result':" + str + "});");
            }
        }

        public void mo2215a() {
            WebView webView = (WebView) this.f4079a.get();
            if (webView != null) {
                webView.loadUrl("javascript:window.JsBridge&&JsBridge.callback(" + this.f4080b + ",{'r':1,'result':'no such method'})");
            }
        }

        public void mo2217a(String str) {
            WebView webView = (WebView) this.f4079a.get();
            if (webView != null) {
                webView.loadUrl("javascript:" + str);
            }
        }
    }

    public void m3881a(C1277b c1277b, String str) {
        this.f4131a.put(str, c1277b);
    }

    public void mo2213a(String str, String str2, List<String> list, C1303a c1303a) {
        C1314f.m3864a("openSDK_LOG.JsBridge", "getResult---objName = " + str + " methodName = " + str2);
        int size = list.size();
        for (int i = 0; i < size; i++) {
            try {
                list.set(i, URLDecoder.decode((String) list.get(i), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        C1277b c1277b = (C1277b) this.f4131a.get(str);
        if (c1277b != null) {
            C1314f.m3867b("openSDK_LOG.JsBridge", "call----");
            c1277b.call(str2, list, c1303a);
            return;
        }
        C1314f.m3867b("openSDK_LOG.JsBridge", "not call----objName NOT FIND");
        if (c1303a != null) {
            c1303a.mo2215a();
        }
    }

    public boolean mo2214a(WebView webView, String str) {
        C1314f.m3864a("openSDK_LOG.JsBridge", "-->canHandleUrl---url = " + str);
        if (str == null || !Uri.parse(str).getScheme().equals("jsbridge")) {
            return false;
        }
        ArrayList arrayList = new ArrayList(Arrays.asList((str + "/#").split("/")));
        if (arrayList.size() < 6) {
            return false;
        }
        String str2 = (String) arrayList.get(2);
        String str3 = (String) arrayList.get(3);
        List subList = arrayList.subList(4, arrayList.size() - 1);
        C1303a c1303a = new C1303a(webView, 4, str);
        webView.getUrl();
        mo2213a(str2, str3, subList, c1303a);
        return true;
    }
}
