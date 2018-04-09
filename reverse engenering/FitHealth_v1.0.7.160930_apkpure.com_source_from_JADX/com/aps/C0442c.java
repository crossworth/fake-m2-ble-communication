package com.aps;

import com.amap.api.location.core.AMapLocException;
import com.amap.api.services.district.DistrictSearchQuery;
import com.sina.weibo.sdk.constant.WBPageConstants.ParamKey;
import com.tencent.open.SocialConstants;
import com.zhuoyou.plugin.database.DataBaseContants;
import org.json.JSONObject;
import p031u.aly.au;

/* compiled from: AmapLoc */
public class C0442c {
    private String f1809a = "";
    private double f1810b = 0.0d;
    private double f1811c = 0.0d;
    private float f1812d = 0.0f;
    private float f1813e = 0.0f;
    private float f1814f = 0.0f;
    private long f1815g = 0;
    private AMapLocException f1816h = new AMapLocException();
    private String f1817i = "new";
    private String f1818j = "";
    private String f1819k = "";
    private String f1820l = "";
    private String f1821m = "";
    private String f1822n = "";
    private String f1823o = "";
    private String f1824p = "";
    private String f1825q = "";
    private String f1826r = "";
    private String f1827s = "";
    private String f1828t = "";
    private String f1829u = "";
    private JSONObject f1830v = null;

    public AMapLocException m1852a() {
        return this.f1816h;
    }

    public void m1856a(AMapLocException aMapLocException) {
        this.f1816h = aMapLocException;
    }

    public String m1859b() {
        return this.f1828t;
    }

    public void m1857a(String str) {
        this.f1828t = str;
    }

    public String m1862c() {
        return this.f1829u;
    }

    public void m1861b(String str) {
        this.f1829u = str;
    }

    public C0442c(JSONObject jSONObject) {
        if (jSONObject != null) {
            try {
                this.f1809a = jSONObject.getString("provider");
                this.f1810b = jSONObject.getDouble("lon");
                this.f1811c = jSONObject.getDouble(au.f3570Y);
                this.f1812d = (float) jSONObject.getLong(DataBaseContants.ACCURACY);
                this.f1813e = (float) jSONObject.getLong(DataBaseContants.SPEED);
                this.f1814f = (float) jSONObject.getLong("bearing");
                this.f1817i = jSONObject.getString("type");
                this.f1818j = jSONObject.getString("retype");
                this.f1819k = jSONObject.getString("citycode");
                this.f1820l = jSONObject.getString(SocialConstants.PARAM_APP_DESC);
                this.f1821m = jSONObject.getString("adcode");
                this.f1822n = jSONObject.getString("country");
                this.f1823o = jSONObject.getString(DistrictSearchQuery.KEYWORDS_PROVINCE);
                this.f1824p = jSONObject.getString(DistrictSearchQuery.KEYWORDS_CITY);
                this.f1825q = jSONObject.getString("road");
                this.f1827s = jSONObject.getString(ParamKey.POINAME);
                this.f1829u = jSONObject.getString("floor");
                this.f1828t = jSONObject.getString(ParamKey.POIID);
                this.f1815g = jSONObject.getLong(LogColumns.TIME);
            } catch (Throwable th) {
                th.printStackTrace();
                C0470t.m2008a(th);
            }
        }
    }

    public void m1863c(String str) {
        this.f1809a = str;
    }

    public double m1864d() {
        return this.f1810b;
    }

    public void m1853a(double d) {
        this.f1810b = d;
    }

    public double m1866e() {
        return this.f1811c;
    }

    public void m1860b(double d) {
        this.f1811c = d;
    }

    public float m1868f() {
        return this.f1812d;
    }

    public void m1854a(float f) {
        this.f1812d = f;
    }

    public long m1870g() {
        return this.f1815g;
    }

    public void m1855a(long j) {
        this.f1815g = j;
    }

    public String m1872h() {
        return this.f1817i;
    }

    public void m1865d(String str) {
        this.f1817i = str;
    }

    public String m1874i() {
        return this.f1818j;
    }

    public void m1867e(String str) {
        this.f1818j = str;
    }

    public String m1876j() {
        return this.f1819k;
    }

    public void m1869f(String str) {
        this.f1819k = str;
    }

    public String m1878k() {
        return this.f1820l;
    }

    public void m1871g(String str) {
        this.f1820l = str;
    }

    public String m1880l() {
        return this.f1821m;
    }

    public void m1873h(String str) {
        this.f1821m = str;
    }

    public void m1875i(String str) {
        this.f1822n = str;
    }

    public void m1877j(String str) {
        this.f1823o = str;
    }

    public void m1879k(String str) {
        this.f1824p = str;
    }

    public void m1881l(String str) {
        this.f1825q = str;
    }

    public void m1883m(String str) {
        this.f1826r = str;
    }

    public void m1885n(String str) {
        this.f1827s = str;
    }

    public JSONObject m1882m() {
        return this.f1830v;
    }

    public void m1858a(JSONObject jSONObject) {
        this.f1830v = jSONObject;
    }

    public String m1884n() {
        JSONObject jSONObject;
        try {
            jSONObject = new JSONObject();
            jSONObject.put("provider", this.f1809a);
            jSONObject.put("lon", this.f1810b);
            jSONObject.put(au.f3570Y, this.f1811c);
            jSONObject.put(DataBaseContants.ACCURACY, (double) this.f1812d);
            jSONObject.put(DataBaseContants.SPEED, (double) this.f1813e);
            jSONObject.put("bearing", (double) this.f1814f);
            jSONObject.put(LogColumns.TIME, this.f1815g);
            jSONObject.put("type", this.f1817i);
            jSONObject.put("retype", this.f1818j);
            jSONObject.put("citycode", this.f1819k);
            jSONObject.put(SocialConstants.PARAM_APP_DESC, this.f1820l);
            jSONObject.put("adcode", this.f1821m);
            jSONObject.put("country", this.f1822n);
            jSONObject.put(DistrictSearchQuery.KEYWORDS_PROVINCE, this.f1823o);
            jSONObject.put(DistrictSearchQuery.KEYWORDS_CITY, this.f1824p);
            jSONObject.put("road", this.f1825q);
            jSONObject.put("street", this.f1826r);
            jSONObject.put(ParamKey.POINAME, this.f1827s);
            jSONObject.put(ParamKey.POIID, this.f1828t);
            jSONObject.put("floor", this.f1829u);
        } catch (Throwable e) {
            C0470t.m2008a(e);
            jSONObject = null;
        }
        if (jSONObject == null) {
            return null;
        }
        return jSONObject.toString();
    }
}
