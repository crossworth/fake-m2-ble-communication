package com.baidu.platform.comapi.favrite;

import android.os.Bundle;
import android.text.TextUtils;
import com.baidu.mapapi.UIMsg.d_ResultType;
import com.baidu.mapapi.common.SysOSUtil;
import com.baidu.mapapi.model.inner.Point;
import com.baidu.platform.comjni.map.favorite.C0677a;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Vector;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class C0610a {
    private static C0610a f1923b = null;
    private C0677a f1924a = null;
    private boolean f1925c = false;
    private boolean f1926d = false;
    private Vector<String> f1927e = null;
    private Vector<String> f1928f = null;
    private boolean f1929g = false;
    private C0609c f1930h = new C0609c();
    private C0608b f1931i = new C0608b();

    class C0607a implements Comparator<String> {
        final /* synthetic */ C0610a f1915a;

        C0607a(C0610a c0610a) {
            this.f1915a = c0610a;
        }

        public int m1868a(String str, String str2) {
            return str2.compareTo(str);
        }

        public /* synthetic */ int compare(Object obj, Object obj2) {
            return m1868a((String) obj, (String) obj2);
        }
    }

    private class C0608b {
        final /* synthetic */ C0610a f1916a;
        private long f1917b;
        private long f1918c;

        private C0608b(C0610a c0610a) {
            this.f1916a = c0610a;
        }

        private void m1869a() {
            this.f1917b = System.currentTimeMillis();
        }

        private void m1871b() {
            this.f1918c = System.currentTimeMillis();
        }

        private boolean m1874c() {
            return this.f1918c - this.f1917b > 1000;
        }
    }

    private class C0609c {
        final /* synthetic */ C0610a f1919a;
        private String f1920b;
        private long f1921c;
        private long f1922d;

        private C0609c(C0610a c0610a) {
            this.f1919a = c0610a;
            this.f1921c = 5000;
            this.f1922d = 0;
        }

        private String m1875a() {
            return this.f1920b;
        }

        private void m1877a(String str) {
            this.f1920b = str;
            this.f1922d = System.currentTimeMillis();
        }

        private boolean m1879b() {
            return TextUtils.isEmpty(this.f1920b);
        }

        private boolean m1882c() {
            return true;
        }
    }

    private C0610a() {
    }

    public static C0610a m1883a() {
        if (f1923b == null) {
            synchronized (C0610a.class) {
                if (f1923b == null) {
                    f1923b = new C0610a();
                    f1923b.m1885h();
                }
            }
        }
        return f1923b;
    }

    public static boolean m1884g() {
        return (f1923b == null || f1923b.f1924a == null || !f1923b.f1924a.m2273d()) ? false : true;
    }

    private boolean m1885h() {
        if (this.f1924a != null) {
            return true;
        }
        this.f1924a = new C0677a();
        if (this.f1924a.m2263a() == 0) {
            this.f1924a = null;
            return false;
        }
        m1887j();
        m1886i();
        return true;
    }

    private boolean m1886i() {
        if (this.f1924a == null) {
            return false;
        }
        String str = "fav_poi";
        this.f1924a.m2264a(1);
        return this.f1924a.m2267a(SysOSUtil.getModuleFileName() + "/", str, "fifo", 10, d_ResultType.VERSION_CHECK, -1);
    }

    private void m1887j() {
        this.f1925c = false;
        this.f1926d = false;
    }

    public synchronized int m1888a(String str, FavSyncPoi favSyncPoi) {
        int i;
        if (this.f1924a == null) {
            i = 0;
        } else if (str == null || str.equals("") || favSyncPoi == null) {
            i = -1;
        } else {
            m1887j();
            ArrayList e = m1896e();
            if ((e != null ? e.size() : 0) + 1 > 500) {
                i = -2;
            } else {
                if (e != null && e.size() > 0) {
                    Iterator it = e.iterator();
                    while (it.hasNext()) {
                        FavSyncPoi b = m1890b((String) it.next());
                        if (b != null && str.equals(b.f1906b)) {
                            i = -1;
                            break;
                        }
                    }
                }
                String str2 = "";
                try {
                    JSONObject jSONObject = new JSONObject();
                    favSyncPoi.f1906b = str;
                    String valueOf = String.valueOf(System.currentTimeMillis());
                    String str3 = valueOf + "_" + favSyncPoi.hashCode();
                    favSyncPoi.f1912h = valueOf;
                    favSyncPoi.f1905a = str3;
                    jSONObject.put("bdetail", favSyncPoi.f1913i);
                    jSONObject.put("uspoiname", favSyncPoi.f1906b);
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put("x", favSyncPoi.f1907c.getmPtx());
                    jSONObject2.put("y", favSyncPoi.f1907c.getmPty());
                    jSONObject.put(SocializeProtocolConstants.PROTOCOL_KEY_SNSACCOUNT_ICON, jSONObject2);
                    jSONObject.put("ncityid", favSyncPoi.f1909e);
                    jSONObject.put("npoitype", favSyncPoi.f1911g);
                    jSONObject.put("uspoiuid", favSyncPoi.f1910f);
                    jSONObject.put("addr", favSyncPoi.f1908d);
                    jSONObject.put("addtimesec", favSyncPoi.f1912h);
                    jSONObject2 = new JSONObject();
                    jSONObject2.put("Fav_Sync", jSONObject);
                    jSONObject2.put("Fav_Content", favSyncPoi.f1914j);
                    if (this.f1924a.m2266a(str3, jSONObject2.toString())) {
                        m1887j();
                        i = 1;
                    } else {
                        C0610a.m1884g();
                        i = 0;
                    }
                } catch (JSONException e2) {
                    i = e2;
                    i = 0;
                    return i;
                } finally {
                    C0610a.m1884g();
                }
            }
        }
        return i;
    }

    public synchronized boolean m1889a(String str) {
        boolean z = false;
        synchronized (this) {
            if (!(this.f1924a == null || str == null)) {
                if (!str.equals("") && m1894c(str)) {
                    m1887j();
                    z = this.f1924a.m2265a(str);
                }
            }
        }
        return z;
    }

    public FavSyncPoi m1890b(String str) {
        if (this.f1924a == null || str == null || str.equals("")) {
            return null;
        }
        try {
            if (!m1894c(str)) {
                return null;
            }
            FavSyncPoi favSyncPoi = new FavSyncPoi();
            String b = this.f1924a.m2269b(str);
            if (b == null || b.equals("")) {
                return null;
            }
            JSONObject jSONObject = new JSONObject(b);
            JSONObject optJSONObject = jSONObject.optJSONObject("Fav_Sync");
            String optString = jSONObject.optString("Fav_Content");
            favSyncPoi.f1906b = optJSONObject.optString("uspoiname");
            JSONObject optJSONObject2 = optJSONObject.optJSONObject(SocializeProtocolConstants.PROTOCOL_KEY_SNSACCOUNT_ICON);
            favSyncPoi.f1907c = new Point(optJSONObject2.optInt("x"), optJSONObject2.optInt("y"));
            favSyncPoi.f1909e = optJSONObject.optString("ncityid");
            favSyncPoi.f1910f = optJSONObject.optString("uspoiuid");
            favSyncPoi.f1911g = optJSONObject.optInt("npoitype");
            favSyncPoi.f1908d = optJSONObject.optString("addr");
            favSyncPoi.f1912h = optJSONObject.optString("addtimesec");
            favSyncPoi.f1913i = optJSONObject.optBoolean("bdetail");
            favSyncPoi.f1914j = optString;
            favSyncPoi.f1905a = str;
            return favSyncPoi;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return null;
        } catch (JSONException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public void m1891b() {
        if (f1923b != null) {
            if (f1923b.f1924a != null) {
                f1923b.f1924a.m2268b();
                f1923b.f1924a = null;
            }
            f1923b = null;
        }
    }

    public synchronized boolean m1892b(String str, FavSyncPoi favSyncPoi) {
        boolean z = false;
        synchronized (this) {
            if (!(this.f1924a == null || str == null || str.equals("") || favSyncPoi == null)) {
                if (m1894c(str)) {
                    try {
                        JSONObject jSONObject = new JSONObject();
                        jSONObject.put("uspoiname", favSyncPoi.f1906b);
                        JSONObject jSONObject2 = new JSONObject();
                        jSONObject2.put("x", favSyncPoi.f1907c.getmPtx());
                        jSONObject2.put("y", favSyncPoi.f1907c.getmPty());
                        jSONObject.put(SocializeProtocolConstants.PROTOCOL_KEY_SNSACCOUNT_ICON, jSONObject2);
                        jSONObject.put("ncityid", favSyncPoi.f1909e);
                        jSONObject.put("npoitype", favSyncPoi.f1911g);
                        jSONObject.put("uspoiuid", favSyncPoi.f1910f);
                        jSONObject.put("addr", favSyncPoi.f1908d);
                        favSyncPoi.f1912h = String.valueOf(System.currentTimeMillis());
                        jSONObject.put("addtimesec", favSyncPoi.f1912h);
                        jSONObject.put("bdetail", false);
                        jSONObject2 = new JSONObject();
                        jSONObject2.put("Fav_Sync", jSONObject);
                        jSONObject2.put("Fav_Content", favSyncPoi.f1914j);
                        m1887j();
                        if (this.f1924a != null && this.f1924a.m2270b(str, jSONObject2.toString())) {
                            z = true;
                        }
                    } catch (JSONException e) {
                    }
                }
            }
        }
        return z;
    }

    public synchronized boolean m1893c() {
        boolean z;
        if (this.f1924a == null) {
            z = false;
        } else {
            m1887j();
            z = this.f1924a.m2271c();
            C0610a.m1884g();
        }
        return z;
    }

    public boolean m1894c(String str) {
        return (this.f1924a == null || str == null || str.equals("") || !this.f1924a.m2272c(str)) ? false : true;
    }

    public ArrayList<String> m1895d() {
        if (this.f1924a == null) {
            return null;
        }
        if (this.f1926d && this.f1928f != null) {
            return new ArrayList(this.f1928f);
        }
        try {
            Bundle bundle = new Bundle();
            this.f1924a.m2262a(bundle);
            String[] stringArray = bundle.getStringArray("rstString");
            if (stringArray != null) {
                if (this.f1928f == null) {
                    this.f1928f = new Vector();
                } else {
                    this.f1928f.clear();
                }
                for (int i = 0; i < stringArray.length; i++) {
                    if (!stringArray[i].equals("data_version")) {
                        String b = this.f1924a.m2269b(stringArray[i]);
                        if (!(b == null || b.equals(""))) {
                            this.f1928f.add(stringArray[i]);
                        }
                    }
                }
                if (this.f1928f.size() > 0) {
                    try {
                        Collections.sort(this.f1928f, new C0607a(this));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    this.f1926d = true;
                }
            } else if (this.f1928f != null) {
                this.f1928f.clear();
                this.f1928f = null;
            }
            ArrayList<String> arrayList = (this.f1928f == null || this.f1928f.isEmpty()) ? null : new ArrayList(this.f1928f);
            return arrayList;
        } catch (Exception e2) {
            return null;
        }
    }

    public ArrayList<String> m1896e() {
        if (this.f1924a == null) {
            return null;
        }
        if (this.f1925c && this.f1927e != null) {
            return new ArrayList(this.f1927e);
        }
        try {
            Bundle bundle = new Bundle();
            this.f1924a.m2262a(bundle);
            String[] stringArray = bundle.getStringArray("rstString");
            if (stringArray != null) {
                if (this.f1927e == null) {
                    this.f1927e = new Vector();
                } else {
                    this.f1927e.clear();
                }
                for (String str : stringArray) {
                    if (!str.equals("data_version")) {
                        this.f1927e.add(str);
                    }
                }
                if (this.f1927e.size() > 0) {
                    try {
                        Collections.sort(this.f1927e, new C0607a(this));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    this.f1925c = true;
                }
            } else if (this.f1927e != null) {
                this.f1927e.clear();
                this.f1927e = null;
            }
            return (this.f1927e == null || this.f1927e.size() == 0) ? null : new ArrayList(this.f1927e);
        } catch (Exception e2) {
            return null;
        }
    }

    public String m1897f() {
        if (this.f1931i.m1874c() && !this.f1930h.m1882c() && !this.f1930h.m1879b()) {
            return this.f1930h.m1875a();
        }
        this.f1931i.m1869a();
        if (this.f1924a == null) {
            return null;
        }
        ArrayList d = m1895d();
        JSONObject jSONObject = new JSONObject();
        if (d != null) {
            try {
                JSONArray jSONArray = new JSONArray();
                int i = 0;
                Iterator it = d.iterator();
                while (it.hasNext()) {
                    int i2;
                    String str = (String) it.next();
                    if (!(str == null || str.equals("data_version"))) {
                        String b = this.f1924a.m2269b(str);
                        if (!(b == null || b.equals(""))) {
                            JSONObject optJSONObject = new JSONObject(b).optJSONObject("Fav_Sync");
                            optJSONObject.put("key", str);
                            jSONArray.put(i, optJSONObject);
                            i2 = i + 1;
                            i = i2;
                        }
                    }
                    i2 = i;
                    i = i2;
                }
                if (i > 0) {
                    jSONObject.put("favcontents", jSONArray);
                    jSONObject.put("favpoinum", i);
                }
            } catch (JSONException e) {
                return null;
            }
        }
        this.f1931i.m1871b();
        this.f1930h.m1877a(jSONObject.toString());
        return this.f1930h.m1875a();
    }
}
