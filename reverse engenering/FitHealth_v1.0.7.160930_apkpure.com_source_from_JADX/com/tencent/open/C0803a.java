package com.tencent.open;

import android.net.Uri;
import android.util.Log;
import android.webkit.WebView;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/* compiled from: ProGuard */
public class C0803a {
    HashMap<String, C0795a> f2737a = new HashMap();

    /* compiled from: ProGuard */
    public static class C0795a {
        public void call(String str, List<String> list, C0796b c0796b) {
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
                    if (method.getReturnType() == Void.class) {
                        if (c0796b != null) {
                            c0796b.m2531a(null);
                        }
                    } else if (c0796b != null && customCallback()) {
                        c0796b.m2532a(invoke.toString());
                        return;
                    } else {
                        return;
                    }
                } catch (IllegalAccessException e) {
                    if (c0796b != null) {
                        c0796b.m2530a();
                    }
                } catch (InvocationTargetException e2) {
                    if (c0796b != null) {
                        c0796b.m2530a();
                    }
                } catch (Exception e3) {
                    if (c0796b != null) {
                        c0796b.m2530a();
                    }
                }
            }
            if (c0796b != null) {
                c0796b.m2530a();
            }
        }

        public boolean customCallback() {
            return false;
        }
    }

    /* compiled from: ProGuard */
    public static class C0796b {
        WeakReference<WebView> f2706a;
        long f2707b;
        String f2708c;

        public C0796b(WebView webView, long j, String str) {
            this.f2706a = new WeakReference(webView);
            this.f2707b = j;
            this.f2708c = str;
        }

        public void m2531a(Object obj) {
            WebView webView = (WebView) this.f2706a.get();
            if (webView != null) {
                String str = "'undefined'";
                if (obj instanceof String) {
                    str = "'" + ((String) obj).replace("\\", "\\\\").replace("'", "\\'") + "'";
                } else if ((obj instanceof Number) || (obj instanceof Long) || (obj instanceof Integer) || (obj instanceof Double) || (obj instanceof Float)) {
                    str = obj.toString();
                } else if (obj instanceof Boolean) {
                    str = obj.toString();
                }
                webView.loadUrl("javascript:window.JsBridge&&JsBridge.callback(" + this.f2707b + ",{'r':0,'result':" + str + "});");
            }
        }

        public void m2530a() {
            WebView webView = (WebView) this.f2706a.get();
            if (webView != null) {
                webView.loadUrl("javascript:window.JsBridge&&JsBridge.callback(" + this.f2707b + ",{'r':1,'result':'no such method'})");
            }
        }

        public void m2532a(String str) {
            WebView webView = (WebView) this.f2706a.get();
            if (webView != null) {
                webView.loadUrl("javascript:" + str);
            }
        }
    }

    public void m2568a(C0795a c0795a, String str) {
        this.f2737a.put(str, c0795a);
    }

    public void m2569a(String str, String str2, List<String> list, C0796b c0796b) {
        Log.d("TDialog", "getResult---objName = " + str + " methodName = " + str2);
        int size = list.size();
        for (int i = 0; i < size; i++) {
            try {
                list.set(i, URLDecoder.decode((String) list.get(i), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        C0795a c0795a = (C0795a) this.f2737a.get(str);
        if (c0795a != null) {
            Log.d("TDialog", "call----");
            c0795a.call(str2, list, c0796b);
            return;
        }
        Log.d("TDialog", "not call----objName NOT FIND");
        if (c0796b != null) {
            c0796b.m2530a();
        }
    }

    public boolean m2570a(WebView webView, String str) {
        Log.d("Dialog", "canHandleUrl---url = " + str);
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
        C0796b c0796b = new C0796b(webView, 4, str);
        webView.getUrl();
        m2569a(str2, str3, subList, c0796b);
        return true;
    }
}
