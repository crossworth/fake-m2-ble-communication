package com.baidu.platform.comapi.util;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import com.baidu.lbsapi.auth.LBSAuthManager;
import com.baidu.lbsapi.auth.LBSAuthManagerListener;
import com.sina.weibo.sdk.exception.WeiboAuthException;
import com.tencent.stat.DeviceInfo;
import com.umeng.facebook.share.internal.ShareConstants;
import com.umeng.socialize.handler.TwitterPreferences;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.util.Hashtable;
import org.json.JSONException;
import org.json.JSONObject;

public class PermissionCheck {
    private static final String f2166a = PermissionCheck.class.getSimpleName();
    private static Context f2167b;
    private static Hashtable<String, String> f2168c;
    private static LBSAuthManager f2169d = null;
    private static LBSAuthManagerListener f2170e = null;
    private static C0600c f2171f = null;

    public interface C0600c {
        void mo1830a(C0663b c0663b);
    }

    private static class C0662a implements LBSAuthManagerListener {
        private C0662a() {
        }

        public void onAuthResult(int i, String str) {
            if (str != null) {
                C0663b c0663b = new C0663b();
                try {
                    JSONObject jSONObject = new JSONObject(str);
                    if (jSONObject.has("status")) {
                        c0663b.f2161a = jSONObject.optInt("status");
                    }
                    if (jSONObject.has("appid")) {
                        c0663b.f2163c = jSONObject.optString("appid");
                    }
                    if (jSONObject.has("uid")) {
                        c0663b.f2162b = jSONObject.optString("uid");
                    }
                    if (jSONObject.has(ShareConstants.WEB_DIALOG_PARAM_MESSAGE)) {
                        c0663b.f2164d = jSONObject.optString(ShareConstants.WEB_DIALOG_PARAM_MESSAGE);
                    }
                    if (jSONObject.has(TwitterPreferences.TOKEN)) {
                        c0663b.f2165e = jSONObject.optString(TwitterPreferences.TOKEN);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (PermissionCheck.f2171f != null) {
                    PermissionCheck.f2171f.mo1830a(c0663b);
                }
            }
        }
    }

    public static class C0663b {
        public int f2161a = 0;
        public String f2162b = WeiboAuthException.DEFAULT_AUTH_ERROR_CODE;
        public String f2163c = WeiboAuthException.DEFAULT_AUTH_ERROR_CODE;
        public String f2164d = "";
        public String f2165e;

        public String toString() {
            return String.format("errorcode: %d uid: %s appid %s msg: %s", new Object[]{Integer.valueOf(this.f2161a), this.f2162b, this.f2163c, this.f2164d});
        }
    }

    public static void destory() {
        f2171f = null;
        f2167b = null;
        f2170e = null;
    }

    public static void init(Context context) {
        f2167b = context;
        if (f2168c == null) {
            f2168c = new Hashtable();
        }
        if (f2169d == null) {
            f2169d = LBSAuthManager.getInstance(f2167b);
        }
        if (f2170e == null) {
            f2170e = new C0662a();
        }
        Object obj = "";
        try {
            obj = context.getPackageManager().getPackageInfo(f2167b.getPackageName(), 0).applicationInfo.loadLabel(f2167b.getPackageManager()).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("auth info", "mcode: " + C0665a.m2136a(f2167b));
        Bundle a = C0671f.m2166a();
        f2168c.put("mb", a.getString("mb"));
        f2168c.put(SocializeProtocolConstants.PROTOCOL_KEY_OS, a.getString(SocializeProtocolConstants.PROTOCOL_KEY_OS));
        f2168c.put("sv", a.getString("sv"));
        f2168c.put("imt", "1");
        f2168c.put("net", a.getString("net"));
        f2168c.put("cpu", a.getString("cpu"));
        f2168c.put("glr", a.getString("glr"));
        f2168c.put("glv", a.getString("glv"));
        f2168c.put("resid", a.getString("resid"));
        f2168c.put("appid", WeiboAuthException.DEFAULT_AUTH_ERROR_CODE);
        f2168c.put(DeviceInfo.TAG_VERSION, "1");
        f2168c.put("screen", String.format("(%d,%d)", new Object[]{Integer.valueOf(a.getInt("screen_x")), Integer.valueOf(a.getInt("screen_y"))}));
        f2168c.put("dpi", String.format("(%d,%d)", new Object[]{Integer.valueOf(a.getInt("dpi_x")), Integer.valueOf(a.getInt("dpi_y"))}));
        f2168c.put("pcn", a.getString("pcn"));
        f2168c.put("cuid", a.getString("cuid"));
        f2168c.put("name", obj);
    }

    public static synchronized int permissionCheck() {
        int i = 0;
        synchronized (PermissionCheck.class) {
            if (!(f2169d == null || f2170e == null || f2167b == null)) {
                i = f2169d.authenticate(false, "lbs_androidsdk", f2168c, f2170e);
            }
        }
        return i;
    }

    public static void setPermissionCheckResultListener(C0600c c0600c) {
        f2171f = c0600c;
    }
}
