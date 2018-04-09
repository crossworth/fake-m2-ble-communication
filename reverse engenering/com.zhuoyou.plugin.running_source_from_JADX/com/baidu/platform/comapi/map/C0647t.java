package com.baidu.platform.comapi.map;

import android.os.Handler;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MessageCenter;
import com.baidu.mapapi.UIMsg.m_AppUI;
import com.baidu.mapapi.common.EnvironmentUtilities;
import com.baidu.mapapi.common.SysOSUtil;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.baidu.platform.comjni.map.basemap.C0673a;
import com.tencent.stat.DeviceInfo;
import com.umeng.facebook.share.internal.ShareConstants;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class C0647t {
    private static final String f2114a = C0647t.class.getSimpleName();
    private static C0647t f2115c;
    private C0673a f2116b;
    private C0651y f2117d;
    private Handler f2118e;

    private C0647t() {
    }

    public static C0647t m2062a() {
        if (f2115c == null) {
            f2115c = new C0647t();
            f2115c.m2065g();
        }
        return f2115c;
    }

    private void m2065g() {
        m2066h();
        this.f2117d = new C0651y();
        this.f2118e = new C0648u(this);
        MessageCenter.registMessage(m_AppUI.V_WM_VDATAENGINE, this.f2118e);
    }

    private void m2066h() {
        EnvironmentUtilities.initAppDirectory(BMapManager.getContext());
        this.f2116b = new C0673a();
        this.f2116b.m2205a();
        String moduleFileName = SysOSUtil.getModuleFileName();
        String appSDCardPath = EnvironmentUtilities.getAppSDCardPath();
        String appCachePath = EnvironmentUtilities.getAppCachePath();
        String appSecondCachePath = EnvironmentUtilities.getAppSecondCachePath();
        int mapTmpStgMax = EnvironmentUtilities.getMapTmpStgMax();
        int domTmpStgMax = EnvironmentUtilities.getDomTmpStgMax();
        int itsTmpStgMax = EnvironmentUtilities.getItsTmpStgMax();
        String str = SysOSUtil.getDensityDpi() >= 180 ? "/h/" : "/l/";
        String str2 = moduleFileName + "/cfg";
        String str3 = appSDCardPath + "/vmp";
        moduleFileName = str2 + str;
        String str4 = str2 + "/a/";
        String str5 = str2 + "/idrres/";
        this.f2116b.m2210a(moduleFileName, str3 + str, appCachePath + "/tmp/", appSecondCachePath + "/tmp/", str3 + str, str4, null, str5, SysOSUtil.getScreenSizeX(), SysOSUtil.getScreenSizeY(), SysOSUtil.getDensityDpi(), mapTmpStgMax, domTmpStgMax, itsTmpStgMax, 0);
        this.f2116b.m2233f();
    }

    public ArrayList<C0646s> m2067a(String str) {
        if (str.equals("") || this.f2116b == null) {
            return null;
        }
        String str2 = "";
        String a = this.f2116b.m2199a(str);
        if (a == null || a.equals("")) {
            return null;
        }
        ArrayList<C0646s> arrayList = new ArrayList();
        try {
            JSONObject jSONObject = new JSONObject(a);
            if (jSONObject == null || jSONObject.length() == 0) {
                return null;
            }
            JSONArray optJSONArray = jSONObject.optJSONArray("dataset");
            if (optJSONArray == null) {
                return null;
            }
            for (int i = 0; i < optJSONArray.length(); i++) {
                C0646s c0646s = new C0646s();
                JSONObject jSONObject2 = optJSONArray.getJSONObject(i);
                c0646s.f2109a = jSONObject2.optInt(ShareConstants.WEB_DIALOG_PARAM_ID);
                c0646s.f2110b = jSONObject2.optString("name");
                c0646s.f2111c = jSONObject2.optInt("mapsize");
                c0646s.f2112d = jSONObject2.optInt("cty");
                if (jSONObject2.has("child")) {
                    JSONArray optJSONArray2 = jSONObject2.optJSONArray("child");
                    ArrayList arrayList2 = new ArrayList();
                    for (int i2 = 0; i2 < optJSONArray2.length(); i2++) {
                        C0646s c0646s2 = new C0646s();
                        JSONObject optJSONObject = optJSONArray2.optJSONObject(i2);
                        c0646s2.f2109a = optJSONObject.optInt(ShareConstants.WEB_DIALOG_PARAM_ID);
                        c0646s2.f2110b = optJSONObject.optString("name");
                        c0646s2.f2111c = optJSONObject.optInt("mapsize");
                        c0646s2.f2112d = optJSONObject.optInt("cty");
                        arrayList2.add(c0646s2);
                    }
                    c0646s.m2061a(arrayList2);
                }
                arrayList.add(c0646s);
            }
            return arrayList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void m2068a(C0499x c0499x) {
        if (this.f2117d != null) {
            this.f2117d.m2085a(c0499x);
        }
    }

    public boolean m2069a(int i) {
        return (this.f2116b == null || i < 0) ? false : this.f2116b.m2218b(i);
    }

    public boolean m2070a(boolean z, boolean z2) {
        return this.f2116b == null ? false : this.f2116b.m2211a(z, z2);
    }

    public void m2071b() {
        MessageCenter.unregistMessage(m_AppUI.V_WM_VDATAENGINE, this.f2118e);
        this.f2116b.m2217b();
        f2115c = null;
    }

    public void m2072b(C0499x c0499x) {
        if (this.f2117d != null) {
            this.f2117d.m2086b(c0499x);
        }
    }

    public boolean m2073b(int i) {
        return (this.f2116b == null || i < 0) ? false : this.f2116b.m2207a(i, false, 0);
    }

    public ArrayList<C0646s> m2074c() {
        if (this.f2116b == null) {
            return null;
        }
        String str = "";
        String o = this.f2116b.m2245o();
        ArrayList<C0646s> arrayList = new ArrayList();
        try {
            JSONArray optJSONArray = new JSONObject(o).optJSONArray("dataset");
            for (int i = 0; i < optJSONArray.length(); i++) {
                C0646s c0646s = new C0646s();
                JSONObject optJSONObject = optJSONArray.optJSONObject(i);
                c0646s.f2109a = optJSONObject.optInt(ShareConstants.WEB_DIALOG_PARAM_ID);
                c0646s.f2110b = optJSONObject.optString("name");
                c0646s.f2111c = optJSONObject.optInt("mapsize");
                c0646s.f2112d = optJSONObject.optInt("cty");
                if (optJSONObject.has("child")) {
                    JSONArray optJSONArray2 = optJSONObject.optJSONArray("child");
                    ArrayList arrayList2 = new ArrayList();
                    for (int i2 = 0; i2 < optJSONArray2.length(); i2++) {
                        C0646s c0646s2 = new C0646s();
                        JSONObject optJSONObject2 = optJSONArray2.optJSONObject(i2);
                        c0646s2.f2109a = optJSONObject2.optInt(ShareConstants.WEB_DIALOG_PARAM_ID);
                        c0646s2.f2110b = optJSONObject2.optString("name");
                        c0646s2.f2111c = optJSONObject2.optInt("mapsize");
                        c0646s2.f2112d = optJSONObject2.optInt("cty");
                        arrayList2.add(c0646s2);
                    }
                    c0646s.m2061a(arrayList2);
                }
                arrayList.add(c0646s);
            }
            return arrayList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean m2075c(int i) {
        return (this.f2116b == null || i < 0) ? false : this.f2116b.m2220b(i, false, 0);
    }

    public ArrayList<C0646s> m2076d() {
        String str = "";
        if (this.f2116b == null) {
            return null;
        }
        String a = this.f2116b.m2199a("");
        ArrayList<C0646s> arrayList = new ArrayList();
        try {
            JSONArray optJSONArray = new JSONObject(a).optJSONArray("dataset");
            for (int i = 0; i < optJSONArray.length(); i++) {
                C0646s c0646s = new C0646s();
                JSONObject optJSONObject = optJSONArray.optJSONObject(i);
                c0646s.f2109a = optJSONObject.optInt(ShareConstants.WEB_DIALOG_PARAM_ID);
                c0646s.f2110b = optJSONObject.optString("name");
                c0646s.f2111c = optJSONObject.optInt("mapsize");
                c0646s.f2112d = optJSONObject.optInt("cty");
                if (optJSONObject.has("child")) {
                    JSONArray optJSONArray2 = optJSONObject.optJSONArray("child");
                    ArrayList arrayList2 = new ArrayList();
                    for (int i2 = 0; i2 < optJSONArray2.length(); i2++) {
                        C0646s c0646s2 = new C0646s();
                        JSONObject optJSONObject2 = optJSONArray2.optJSONObject(i2);
                        c0646s2.f2109a = optJSONObject2.optInt(ShareConstants.WEB_DIALOG_PARAM_ID);
                        c0646s2.f2110b = optJSONObject2.optString("name");
                        c0646s2.f2111c = optJSONObject2.optInt("mapsize");
                        c0646s2.f2112d = optJSONObject2.optInt("cty");
                        arrayList2.add(c0646s2);
                    }
                    c0646s.m2061a(arrayList2);
                }
                arrayList.add(c0646s);
            }
            return arrayList;
        } catch (JSONException e) {
            return null;
        } catch (Exception e2) {
            return null;
        }
    }

    public boolean m2077d(int i) {
        return this.f2116b == null ? false : this.f2116b.m2220b(0, true, i);
    }

    public ArrayList<C0650w> m2078e() {
        if (this.f2116b == null) {
            return null;
        }
        String str = "";
        String n = this.f2116b.m2244n();
        if (n == null || n.equals("")) {
            return null;
        }
        ArrayList<C0650w> arrayList = new ArrayList();
        try {
            JSONObject jSONObject = new JSONObject(n);
            if (jSONObject.length() == 0) {
                return null;
            }
            JSONArray optJSONArray = jSONObject.optJSONArray("dataset");
            for (int i = 0; i < optJSONArray.length(); i++) {
                C0650w c0650w = new C0650w();
                C0649v c0649v = new C0649v();
                JSONObject optJSONObject = optJSONArray.optJSONObject(i);
                c0649v.f2120a = optJSONObject.optInt(ShareConstants.WEB_DIALOG_PARAM_ID);
                c0649v.f2121b = optJSONObject.optString("name");
                c0649v.f2122c = optJSONObject.optString(SocializeProtocolConstants.PROTOCOL_KEY_FRIENDS_PINYIN);
                c0649v.f2127h = optJSONObject.optInt("mapoldsize");
                c0649v.f2128i = optJSONObject.optInt("ratio");
                c0649v.f2131l = optJSONObject.optInt("status");
                c0649v.f2126g = new GeoPoint((double) optJSONObject.optInt("y"), (double) optJSONObject.optInt("x"));
                if (optJSONObject.optInt("up") == 1) {
                    c0649v.f2129j = true;
                } else {
                    c0649v.f2129j = false;
                }
                c0649v.f2124e = optJSONObject.optInt("lev");
                if (c0649v.f2129j) {
                    c0649v.f2130k = optJSONObject.optInt("mapsize");
                } else {
                    c0649v.f2130k = 0;
                }
                c0650w.m2083a(c0649v);
                arrayList.add(c0650w);
            }
            return arrayList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean m2079e(int i) {
        return (this.f2116b == null || i < 0) ? false : this.f2116b.m2219b(i, false);
    }

    public boolean m2080f(int i) {
        return (this.f2116b == null || i < 0) ? false : this.f2116b.m2206a(i, false);
    }

    public C0650w m2081g(int i) {
        if (this.f2116b == null || i < 0) {
            return null;
        }
        String str = "";
        String c = this.f2116b.m2223c(i);
        if (c == null || c.equals("")) {
            return null;
        }
        C0650w c0650w = new C0650w();
        C0649v c0649v = new C0649v();
        try {
            JSONObject jSONObject = new JSONObject(c);
            if (jSONObject.length() == 0) {
                return null;
            }
            c0649v.f2120a = jSONObject.optInt(ShareConstants.WEB_DIALOG_PARAM_ID);
            c0649v.f2121b = jSONObject.optString("name");
            c0649v.f2122c = jSONObject.optString(SocializeProtocolConstants.PROTOCOL_KEY_FRIENDS_PINYIN);
            c0649v.f2123d = jSONObject.optString("headchar");
            c0649v.f2127h = jSONObject.optInt("mapoldsize");
            c0649v.f2128i = jSONObject.optInt("ratio");
            c0649v.f2131l = jSONObject.optInt("status");
            c0649v.f2126g = new GeoPoint((double) jSONObject.optInt("y"), (double) jSONObject.optInt("x"));
            if (jSONObject.optInt("up") == 1) {
                c0649v.f2129j = true;
            } else {
                c0649v.f2129j = false;
            }
            c0649v.f2124e = jSONObject.optInt("lev");
            if (c0649v.f2129j) {
                c0649v.f2130k = jSONObject.optInt("mapsize");
            } else {
                c0649v.f2130k = 0;
            }
            c0649v.f2125f = jSONObject.optInt(DeviceInfo.TAG_VERSION);
            c0650w.m2083a(c0649v);
            return c0650w;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
