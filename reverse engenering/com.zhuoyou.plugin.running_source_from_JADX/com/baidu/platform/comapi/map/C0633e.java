package com.baidu.platform.comapi.map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.view.Display;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import com.baidu.mapapi.MessageCenter;
import com.baidu.mapapi.UIMsg.k_event;
import com.baidu.mapapi.UIMsg.m_AppUI;
import com.baidu.mapapi.common.EnvironmentUtilities;
import com.baidu.mapapi.common.SysOSUtil;
import com.baidu.mapapi.map.MapBaseIndoorMapInfo;
import com.baidu.mapapi.map.WeightedLatLng;
import com.baidu.mapapi.model.CoordUtil;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.model.LatLngBounds.Builder;
import com.baidu.mapapi.model.ParcelItem;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.baidu.platform.comapi.map.C0638i.C0637a;
import com.baidu.platform.comjni.map.basemap.BaseMapCallback;
import com.baidu.platform.comjni.map.basemap.C0632b;
import com.baidu.platform.comjni.map.basemap.C0673a;
import com.baidu.platform.comjni.map.basemap.JNIBaseMap;
import com.tencent.open.yyb.TitleBar;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import org.andengine.util.level.constants.LevelConstants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressLint({"NewApi"})
public class C0633e implements C0632b {
    private static int f2019N;
    private static int f2020O;
    private static List<JNIBaseMap> ai;
    static long f2021k = 0;
    private static final String f2022o = C0638i.class.getSimpleName();
    private Context f2023A;
    private List<C0620d> f2024B;
    private C0652z f2025C;
    private C0635g f2026D;
    private C0642n f2027E;
    private C0621G f2028F;
    private C0624J f2029G;
    private C0645r f2030H;
    private C0641m f2031I;
    private C0643o f2032J;
    private C0629a f2033K;
    private C0484p f2034L;
    private C0622H f2035M;
    private int f2036P;
    private int f2037Q;
    private int f2038R;
    private C0637a f2039S = new C0637a();
    private VelocityTracker f2040T;
    private long f2041U;
    private long f2042V;
    private long f2043W;
    private long f2044X;
    private int f2045Y;
    private float f2046Z;
    public float f2047a = 22.0f;
    private float aa;
    private boolean ab;
    private long ac;
    private long ad;
    private C0634f ae;
    private String af;
    private C0630b ag;
    private C0631c ah;
    public float f2048b = 3.0f;
    public float f2049c = 22.0f;
    boolean f2050d = true;
    boolean f2051e = true;
    List<C0482k> f2052f;
    C0673a f2053g;
    long f2054h;
    boolean f2055i;
    public int f2056j;
    boolean f2057l;
    boolean f2058m;
    boolean f2059n;
    private boolean f2060p;
    private boolean f2061q;
    private boolean f2062r = true;
    private boolean f2063s = false;
    private boolean f2064t = false;
    private boolean f2065u = false;
    private boolean f2066v = true;
    private boolean f2067w = true;
    private boolean f2068x = false;
    private C0625L f2069y;
    private C0486K f2070z;

    public C0633e(Context context, String str) {
        this.f2023A = context;
        this.f2052f = new ArrayList();
        this.af = str;
    }

    private void m1944N() {
        if (this.f2064t || this.f2061q || this.f2060p || this.f2065u) {
            if (this.f2047a > TitleBar.BACKBTN_LEFT_MARGIN) {
                this.f2047a = TitleBar.BACKBTN_LEFT_MARGIN;
            }
            if (m1953D().f1963a > TitleBar.BACKBTN_LEFT_MARGIN) {
                C0616D D = m1953D();
                D.f1963a = TitleBar.BACKBTN_LEFT_MARGIN;
                m1974a(D);
                return;
            }
            return;
        }
        this.f2047a = this.f2049c;
    }

    private Activity m1945a(Context context) {
        return context == null ? null : context instanceof Activity ? (Activity) context : context instanceof ContextWrapper ? m1945a(((ContextWrapper) context).getBaseContext()) : null;
    }

    private boolean m1946e(Bundle bundle) {
        return this.f2053g == null ? false : this.f2053g.m2232e(bundle);
    }

    private boolean m1947f(Bundle bundle) {
        boolean z = false;
        if (!(bundle == null || this.f2053g == null)) {
            z = this.f2053g.m2228d(bundle);
            if (z) {
                m1996c(z);
                this.f2053g.m2214b(this.f2069y.a);
            }
        }
        return z;
    }

    private void m1948g(Bundle bundle) {
        if (bundle.get("param") != null) {
            Bundle bundle2 = (Bundle) bundle.get("param");
            int i = bundle2.getInt("type");
            if (i == C0636h.ground.ordinal()) {
                bundle2.putLong("layer_addr", this.f2027E.a);
                return;
            } else if (i >= C0636h.arc.ordinal()) {
                bundle2.putLong("layer_addr", this.f2031I.a);
                return;
            } else if (i == C0636h.popup.ordinal()) {
                bundle2.putLong("layer_addr", this.f2030H.a);
                return;
            } else {
                bundle2.putLong("layer_addr", this.f2029G.a);
                return;
            }
        }
        int i2 = bundle.getInt("type");
        if (i2 == C0636h.ground.ordinal()) {
            bundle.putLong("layer_addr", this.f2027E.a);
        } else if (i2 >= C0636h.arc.ordinal()) {
            bundle.putLong("layer_addr", this.f2031I.a);
        } else if (i2 == C0636h.popup.ordinal()) {
            bundle.putLong("layer_addr", this.f2030H.a);
        } else {
            bundle.putLong("layer_addr", this.f2029G.a);
        }
    }

    public static void m1949j(boolean z) {
        ai = C0673a.m2194d();
        if (ai == null || ai.size() == 0) {
            C0673a.m2193b(0, z);
            return;
        }
        C0673a.m2193b(((JNIBaseMap) ai.get(0)).f2228a, z);
        for (JNIBaseMap jNIBaseMap : ai) {
            jNIBaseMap.ClearLayer(jNIBaseMap.f2228a, -1);
        }
    }

    void m1950A() {
        this.f2058m = false;
        this.f2057l = false;
        for (C0482k c : this.f2052f) {
            c.mo1784c(m1953D());
        }
    }

    public boolean m1951B() {
        return this.f2053g != null ? this.f2053g.m2208a(this.f2028F.a) : false;
    }

    public boolean m1952C() {
        return this.f2053g != null ? this.f2053g.m2208a(this.ah.a) : false;
    }

    public C0616D m1953D() {
        if (this.f2053g == null) {
            return null;
        }
        Bundle j = this.f2053g.m2240j();
        C0616D c0616d = new C0616D();
        c0616d.m1906a(j);
        return c0616d;
    }

    public LatLngBounds m1954E() {
        if (this.f2053g == null) {
            return null;
        }
        Bundle k = this.f2053g.m2241k();
        Builder builder = new Builder();
        int i = k.getInt("maxCoorx");
        int i2 = k.getInt("minCoorx");
        builder.include(CoordUtil.mc2ll(new GeoPoint((double) k.getInt("minCoory"), (double) i))).include(CoordUtil.mc2ll(new GeoPoint((double) k.getInt("maxCoory"), (double) i2)));
        return builder.build();
    }

    public int m1955F() {
        return this.f2036P;
    }

    public int m1956G() {
        return this.f2037Q;
    }

    public C0616D m1957H() {
        if (this.f2053g == null) {
            return null;
        }
        Bundle l = this.f2053g.m2242l();
        C0616D c0616d = new C0616D();
        c0616d.m1906a(l);
        return c0616d;
    }

    public double m1958I() {
        return m1953D().f1975m;
    }

    void m1959J() {
        if (!this.f2057l) {
            this.f2057l = true;
            this.f2058m = false;
            for (C0482k a : this.f2052f) {
                a.mo1774a(m1953D());
            }
        }
    }

    void m1960K() {
        this.f2057l = false;
        if (!this.f2058m) {
            for (C0482k c : this.f2052f) {
                c.mo1784c(m1953D());
            }
        }
    }

    void m1961L() {
        this.f2038R = 0;
        this.f2039S.f2086e = false;
        this.f2039S.f2089h = 0.0d;
    }

    void m1962M() {
        if (this.f2053g != null) {
            this.f2053g.m2217b();
            this.f2053g = null;
        }
    }

    public float m1963a(int i, int i2, int i3, int i4, int i5, int i6) {
        if (!this.f2055i) {
            return 12.0f;
        }
        if (this.f2053g == null) {
            return 0.0f;
        }
        Bundle bundle = new Bundle();
        bundle.putInt("left", i);
        bundle.putInt("right", i3);
        bundle.putInt("bottom", i4);
        bundle.putInt("top", i2);
        bundle.putInt("hasHW", 1);
        bundle.putInt(LevelConstants.TAG_LEVEL_ATTRIBUTE_WIDTH, i5);
        bundle.putInt(LevelConstants.TAG_LEVEL_ATTRIBUTE_HEIGHT, i6);
        return this.f2053g.m2221c(bundle);
    }

    int m1964a(int i, int i2, int i3) {
        return C0673a.m2191a(this.f2054h, i, i2, i3);
    }

    public int mo1832a(Bundle bundle, long j, int i, Bundle bundle2) {
        if (j == this.f2026D.a) {
            bundle.putString("jsondata", this.f2026D.m1921a());
            bundle.putBundle("param", this.f2026D.m1924b());
            return this.f2026D.g;
        } else if (j == this.f2025C.a) {
            bundle.putString("jsondata", this.f2025C.m1921a());
            bundle.putBundle("param", this.f2025C.m1924b());
            return this.f2025C.g;
        } else if (j == this.f2032J.a) {
            bundle.putBundle("param", this.f2034L.mo1790a(bundle2.getInt("x"), bundle2.getInt("y"), bundle2.getInt("zoom")));
            return this.f2032J.g;
        } else if (j != this.f2069y.a) {
            return 0;
        } else {
            bundle.putBundle("param", this.f2070z.mo1791a(bundle2.getInt("x"), bundle2.getInt("y"), bundle2.getInt("zoom"), this.f2023A));
            return this.f2069y.g;
        }
    }

    public Point m1966a(GeoPoint geoPoint) {
        return this.f2035M.m1919a(geoPoint);
    }

    void m1967a() {
        this.f2053g = new C0673a();
        this.f2053g.m2205a();
        this.f2054h = this.f2053g.m2222c();
        if (SysOSUtil.getDensityDpi() < 180) {
            this.f2056j = 18;
        } else if (SysOSUtil.getDensityDpi() < 240) {
            this.f2056j = 25;
        } else if (SysOSUtil.getDensityDpi() < 320) {
            this.f2056j = 37;
        } else {
            this.f2056j = 50;
        }
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
        appSDCardPath = str3 + str;
        str2 = str3 + str;
        appCachePath = appCachePath + "/tmp/";
        appSecondCachePath = appSecondCachePath + "/tmp/";
        Activity a = m1945a(this.f2023A);
        if (a != null) {
            Display defaultDisplay = a.getWindowManager().getDefaultDisplay();
            this.f2053g.m2210a(moduleFileName, appSDCardPath, appCachePath, appSecondCachePath, str2, str4, this.af, str5, defaultDisplay.getWidth(), defaultDisplay.getHeight(), SysOSUtil.getDensityDpi(), mapTmpStgMax, domTmpStgMax, itsTmpStgMax, 0);
            this.f2053g.m2233f();
            return;
        }
        throw new RuntimeException("Please give the right context.");
    }

    public void m1968a(float f, float f2) {
        this.f2047a = f;
        this.f2049c = f;
        this.f2048b = f2;
    }

    void m1969a(int i, int i2) {
        this.f2036P = i;
        this.f2037Q = i2;
    }

    public void m1970a(Bitmap bitmap) {
        if (this.f2053g != null) {
            Bundle bundle;
            JSONObject jSONObject = new JSONObject();
            JSONArray jSONArray = new JSONArray();
            JSONObject jSONObject2 = new JSONObject();
            try {
                jSONObject.put("type", 0);
                jSONObject2.put("x", f2019N);
                jSONObject2.put("y", f2020O);
                jSONObject2.put("hidetime", 1000);
                jSONArray.put(jSONObject2);
                jSONObject.put("data", jSONArray);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (bitmap == null) {
                bundle = null;
            } else {
                Bundle bundle2 = new Bundle();
                ArrayList arrayList = new ArrayList();
                ParcelItem parcelItem = new ParcelItem();
                Bundle bundle3 = new Bundle();
                Buffer allocate = ByteBuffer.allocate((bitmap.getWidth() * bitmap.getHeight()) * 4);
                bitmap.copyPixelsToBuffer(allocate);
                bundle3.putByteArray("imgdata", allocate.array());
                bundle3.putInt("imgindex", bitmap.hashCode());
                bundle3.putInt("imgH", bitmap.getHeight());
                bundle3.putInt("imgW", bitmap.getWidth());
                bundle3.putInt("hasIcon", 1);
                parcelItem.setBundle(bundle3);
                arrayList.add(parcelItem);
                if (arrayList.size() > 0) {
                    Parcelable[] parcelableArr = new ParcelItem[arrayList.size()];
                    for (int i = 0; i < arrayList.size(); i++) {
                        parcelableArr[i] = (ParcelItem) arrayList.get(i);
                    }
                    bundle2.putParcelableArray("icondata", parcelableArr);
                }
                bundle = bundle2;
            }
            m1993b(jSONObject.toString(), bundle);
            this.f2053g.m2214b(this.f2026D.a);
        }
    }

    void m1971a(Handler handler) {
        MessageCenter.registMessage(m_AppUI.MSG_APP_SAVESCREEN, handler);
        MessageCenter.registMessage(39, handler);
        MessageCenter.registMessage(41, handler);
        MessageCenter.registMessage(49, handler);
        MessageCenter.registMessage(m_AppUI.V_WM_VDATAENGINE, handler);
        MessageCenter.registMessage(50, handler);
        MessageCenter.registMessage(999, handler);
        BaseMapCallback.addLayerDataInterface(this.f2054h, this);
    }

    public void m1972a(LatLngBounds latLngBounds) {
        if (latLngBounds != null && this.f2053g != null) {
            LatLng latLng = latLngBounds.northeast;
            LatLng latLng2 = latLngBounds.southwest;
            GeoPoint ll2mc = CoordUtil.ll2mc(latLng);
            GeoPoint ll2mc2 = CoordUtil.ll2mc(latLng2);
            int longitudeE6 = (int) ll2mc.getLongitudeE6();
            int latitudeE6 = (int) ll2mc2.getLatitudeE6();
            int longitudeE62 = (int) ll2mc2.getLongitudeE6();
            int latitudeE62 = (int) ll2mc.getLatitudeE6();
            Bundle bundle = new Bundle();
            bundle.putInt("maxCoorx", longitudeE6);
            bundle.putInt("minCoory", latitudeE6);
            bundle.putInt("minCoorx", longitudeE62);
            bundle.putInt("maxCoory", latitudeE62);
            this.f2053g.m2215b(bundle);
        }
    }

    void m1973a(C0612B c0612b) {
        C0616D c0616d = new C0616D();
        if (c0612b == null) {
            c0612b = new C0612B();
        }
        c0616d = c0612b.f1937a;
        this.f2066v = c0612b.f1942f;
        this.f2067w = c0612b.f1940d;
        this.f2050d = c0612b.f1941e;
        this.f2051e = c0612b.f1943g;
        this.f2053g.m2201a(c0616d.m1905a(this));
        this.f2053g.m2195a(C0611A.DEFAULT.ordinal());
        this.f2062r = c0612b.f1938b;
        if (c0612b.f1938b) {
            f2019N = (int) (SysOSUtil.getDensity() * 40.0f);
            f2020O = (int) (SysOSUtil.getDensity() * 40.0f);
            JSONObject jSONObject = new JSONObject();
            JSONArray jSONArray = new JSONArray();
            JSONObject jSONObject2 = new JSONObject();
            try {
                jSONObject2.put("x", f2019N);
                jSONObject2.put("y", f2020O);
                jSONObject2.put("hidetime", 1000);
                jSONArray.put(jSONObject2);
                jSONObject.put("data", jSONArray);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            this.f2026D.m1923a(jSONObject.toString());
            this.f2053g.m2200a(this.f2026D.a, true);
        } else {
            this.f2053g.m2200a(this.f2026D.a, false);
        }
        int i = c0612b.f1939c;
        if (i == 2) {
            m1982a(true);
        }
        if (i == 3) {
            this.f2053g.m2200a(this.ae.a, false);
        }
    }

    public void m1974a(C0616D c0616d) {
        if (this.f2053g != null) {
            Bundle a = c0616d.m1905a(this);
            a.putInt("animation", 0);
            a.putInt("animatime", 0);
            this.f2053g.m2201a(a);
        }
    }

    public void m1975a(C0616D c0616d, int i) {
        if (this.f2053g != null) {
            Bundle a = c0616d.m1905a(this);
            a.putInt("animation", 1);
            a.putInt("animatime", i);
            m2038z();
            this.f2053g.m2201a(a);
        }
    }

    public void m1976a(C0486K c0486k) {
        this.f2070z = c0486k;
    }

    void m1977a(C0620d c0620d) {
        if (this.f2053g != null) {
            c0620d.f1989a = this.f2053g.m2196a(c0620d.f1991c, c0620d.f1992d, c0620d.f1990b);
            this.f2024B.add(c0620d);
        }
    }

    public void m1978a(C0482k c0482k) {
        this.f2052f.add(c0482k);
    }

    public void m1979a(C0484p c0484p) {
        this.f2034L = c0484p;
    }

    public void m1980a(String str, Bundle bundle) {
        if (this.f2053g != null) {
            this.f2025C.m1923a(str);
            this.f2025C.m1922a(bundle);
            this.f2053g.m2214b(this.f2025C.a);
        }
    }

    public void m1981a(List<Bundle> list) {
        if (this.f2053g != null && list != null) {
            int size = list.size();
            Bundle[] bundleArr = new Bundle[list.size()];
            for (int i = 0; i < size; i++) {
                m1948g((Bundle) list.get(i));
                bundleArr[i] = (Bundle) list.get(i);
            }
            this.f2053g.m2204a(bundleArr);
        }
    }

    public void m1982a(boolean z) {
        if (this.f2053g != null) {
            if (!this.f2053g.m2208a(this.ae.a)) {
                this.f2053g.m2200a(this.ae.a, true);
            }
            this.f2061q = z;
            m1944N();
            this.f2053g.m2203a(this.f2061q);
        }
    }

    public boolean mo1833a(long j) {
        for (C0620d c0620d : this.f2024B) {
            if (c0620d.f1989a == j) {
                return true;
            }
        }
        return false;
    }

    public boolean m1984a(Point point) {
        if (point == null || this.f2053g == null || point.x < 0 || point.y < 0) {
            return false;
        }
        f2019N = point.x;
        f2020O = point.y;
        JSONObject jSONObject = new JSONObject();
        JSONArray jSONArray = new JSONArray();
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject2.put("x", f2019N);
            jSONObject2.put("y", f2020O);
            jSONObject2.put("hidetime", 1000);
            jSONArray.put(jSONObject2);
            jSONObject.put("data", jSONArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.f2026D.m1923a(jSONObject.toString());
        this.f2053g.m2214b(this.f2026D.a);
        return true;
    }

    public boolean m1985a(Bundle bundle) {
        if (this.f2053g == null) {
            return false;
        }
        this.f2069y = new C0625L();
        long a = this.f2053g.m2196a(this.f2069y.c, this.f2069y.d, this.f2069y.b);
        if (a == 0) {
            return false;
        }
        this.f2069y.a = a;
        this.f2024B.add(this.f2069y);
        bundle.putLong("sdktileaddr", a);
        return m1946e(bundle) && m1947f(bundle);
    }

    boolean m1986a(MotionEvent motionEvent) {
        int pointerCount = motionEvent.getPointerCount();
        if (pointerCount == 2 && !(m1998c((int) motionEvent.getX(0), (int) motionEvent.getY(0)) && m1998c((int) motionEvent.getX(1), (int) motionEvent.getY(1)))) {
            pointerCount = 1;
        }
        if (pointerCount == 2) {
            float y = ((float) this.f2037Q) - motionEvent.getY(0);
            float y2 = ((float) this.f2037Q) - motionEvent.getY(1);
            float x = motionEvent.getX(0);
            float x2 = motionEvent.getX(1);
            switch (motionEvent.getAction()) {
                case 5:
                    this.f2042V = motionEvent.getEventTime();
                    this.f2045Y--;
                    break;
                case 6:
                    this.f2044X = motionEvent.getEventTime();
                    this.f2045Y++;
                    break;
                case 261:
                    this.f2041U = motionEvent.getEventTime();
                    this.f2045Y--;
                    break;
                case 262:
                    this.f2043W = motionEvent.getEventTime();
                    this.f2045Y++;
                    break;
            }
            if (this.f2040T == null) {
                this.f2040T = VelocityTracker.obtain();
            }
            this.f2040T.addMovement(motionEvent);
            int minimumFlingVelocity = ViewConfiguration.getMinimumFlingVelocity();
            this.f2040T.computeCurrentVelocity(1000, (float) ViewConfiguration.getMaximumFlingVelocity());
            float xVelocity = this.f2040T.getXVelocity(1);
            float yVelocity = this.f2040T.getYVelocity(1);
            float xVelocity2 = this.f2040T.getXVelocity(2);
            float yVelocity2 = this.f2040T.getYVelocity(2);
            if (Math.abs(xVelocity) > ((float) minimumFlingVelocity) || Math.abs(yVelocity) > ((float) minimumFlingVelocity) || Math.abs(xVelocity2) > ((float) minimumFlingVelocity) || Math.abs(yVelocity2) > ((float) minimumFlingVelocity)) {
                if (this.f2039S.f2086e) {
                    double sqrt;
                    int log;
                    if (this.f2038R == 0) {
                        if ((this.f2039S.f2084c - y <= 0.0f || this.f2039S.f2085d - y2 <= 0.0f) && (this.f2039S.f2084c - y >= 0.0f || this.f2039S.f2085d - y2 >= 0.0f)) {
                            this.f2038R = 2;
                        } else {
                            sqrt = Math.sqrt((double) (((x2 - x) * (x2 - x)) + ((y2 - y) * (y2 - y)))) / this.f2039S.f2089h;
                            log = (int) ((Math.log(sqrt) / Math.log(2.0d)) * 10000.0d);
                            minimumFlingVelocity = (int) (((Math.atan2((double) (y2 - y), (double) (x2 - x)) - Math.atan2((double) (this.f2039S.f2085d - this.f2039S.f2084c), (double) (this.f2039S.f2083b - this.f2039S.f2082a))) * 180.0d) / 3.1416d);
                            if ((sqrt <= 0.0d || (log <= MessageHandler.WHAT_ITEM_SELECTED && log >= -3000)) && Math.abs(minimumFlingVelocity) < 10) {
                                this.f2038R = 1;
                            } else {
                                this.f2038R = 2;
                            }
                        }
                        if (this.f2038R == 0) {
                            return true;
                        }
                    }
                    if (this.f2038R == 1 && this.f2066v) {
                        if (this.f2039S.f2084c - y > 0.0f && this.f2039S.f2085d - y2 > 0.0f) {
                            m1959J();
                            m1964a(1, 83, 0);
                        } else if (this.f2039S.f2084c - y < 0.0f && this.f2039S.f2085d - y2 < 0.0f) {
                            m1959J();
                            m1964a(1, 87, 0);
                        }
                    } else if (this.f2038R == 2 || this.f2038R == 4 || this.f2038R == 3) {
                        double atan2 = Math.atan2((double) (y2 - y), (double) (x2 - x)) - Math.atan2((double) (this.f2039S.f2085d - this.f2039S.f2084c), (double) (this.f2039S.f2083b - this.f2039S.f2082a));
                        sqrt = Math.sqrt((double) (((x2 - x) * (x2 - x)) + ((y2 - y) * (y2 - y)))) / this.f2039S.f2089h;
                        log = (int) ((Math.log(sqrt) / Math.log(2.0d)) * 10000.0d);
                        double atan22 = Math.atan2((double) (this.f2039S.f2088g - this.f2039S.f2084c), (double) (this.f2039S.f2087f - this.f2039S.f2082a));
                        double sqrt2 = Math.sqrt((double) (((this.f2039S.f2087f - this.f2039S.f2082a) * (this.f2039S.f2087f - this.f2039S.f2082a)) + ((this.f2039S.f2088g - this.f2039S.f2084c) * (this.f2039S.f2088g - this.f2039S.f2084c))));
                        float cos = (float) (((Math.cos(atan22 + atan2) * sqrt2) * sqrt) + ((double) x));
                        float sin = (float) (((Math.sin(atan22 + atan2) * sqrt2) * sqrt) + ((double) y));
                        minimumFlingVelocity = (int) ((atan2 * 180.0d) / 3.1416d);
                        if (sqrt > 0.0d && (3 == this.f2038R || (Math.abs(log) > 2000 && 2 == this.f2038R))) {
                            this.f2038R = 3;
                            float f = m1953D().f1963a;
                            if (this.f2051e) {
                                if (sqrt > WeightedLatLng.DEFAULT_INTENSITY) {
                                    if (f >= this.f2047a) {
                                        return false;
                                    }
                                    m1959J();
                                    m1964a(k_event.V_WM_ROTATE, 3, log);
                                } else if (f <= this.f2048b) {
                                    return false;
                                } else {
                                    m1959J();
                                    m1964a(k_event.V_WM_ROTATE, 3, log);
                                }
                            }
                        } else if (minimumFlingVelocity != 0 && (4 == this.f2038R || (Math.abs(minimumFlingVelocity) > 10 && 2 == this.f2038R))) {
                            this.f2038R = 4;
                            if (this.f2067w) {
                                m1959J();
                                m1964a(k_event.V_WM_ROTATE, 1, minimumFlingVelocity);
                            }
                        }
                        this.f2039S.f2087f = cos;
                        this.f2039S.f2088g = sin;
                    }
                }
            } else if (this.f2038R == 0 && this.f2045Y == 0) {
                this.f2043W = this.f2043W > this.f2044X ? this.f2043W : this.f2044X;
                this.f2041U = this.f2041U < this.f2042V ? this.f2042V : this.f2041U;
                if (this.f2043W - this.f2041U < 200 && this.f2051e) {
                    C0616D D = m1953D();
                    if (D != null) {
                        D.f1963a -= 1.0f;
                        m1975a(D, 300);
                    }
                }
            }
            if (2 != this.f2038R) {
                this.f2039S.f2084c = y;
                this.f2039S.f2085d = y2;
                this.f2039S.f2082a = x;
                this.f2039S.f2083b = x2;
            }
            if (!this.f2039S.f2086e) {
                this.f2039S.f2087f = (float) (this.f2036P / 2);
                this.f2039S.f2088g = (float) (this.f2037Q / 2);
                this.f2039S.f2086e = true;
                if (0.0d == this.f2039S.f2089h) {
                    this.f2039S.f2089h = Math.sqrt((double) (((this.f2039S.f2083b - this.f2039S.f2082a) * (this.f2039S.f2083b - this.f2039S.f2082a)) + ((this.f2039S.f2085d - this.f2039S.f2084c) * (this.f2039S.f2085d - this.f2039S.f2084c))));
                }
            }
            return true;
        }
        switch (motionEvent.getAction()) {
            case 0:
                m1992b(motionEvent);
                return true;
            case 1:
                return m2003d(motionEvent);
            case 2:
                m1999c(motionEvent);
                return true;
            default:
                return false;
        }
    }

    public boolean m1987a(String str, String str2) {
        return this.f2053g.m2209a(str, str2);
    }

    public GeoPoint m1988b(int i, int i2) {
        return this.f2035M.m1920a(i, i2);
    }

    void m1989b() {
        this.f2024B = new ArrayList();
        this.ae = new C0634f();
        m1977a(this.ae);
        this.ag = new C0630b();
        m1977a(this.ag);
        if (this.f2053g != null) {
            this.f2053g.m2231e(false);
        }
        this.f2027E = new C0642n();
        m1977a(this.f2027E);
        this.f2032J = new C0643o();
        m1977a(this.f2032J);
        this.f2033K = new C0629a();
        m1977a(this.f2033K);
        m1977a(new C0644q());
        this.f2028F = new C0621G();
        m1977a(this.f2028F);
        this.ah = new C0631c();
        m1977a(this.ah);
        this.f2031I = new C0641m();
        m1977a(this.f2031I);
        this.f2029G = new C0624J();
        m1977a(this.f2029G);
        this.f2026D = new C0635g();
        m1977a(this.f2026D);
        this.f2025C = new C0652z();
        m1977a(this.f2025C);
        this.f2030H = new C0645r();
        m1977a(this.f2030H);
    }

    public void m1990b(Bundle bundle) {
        if (this.f2053g != null) {
            m1948g(bundle);
            this.f2053g.m2234f(bundle);
        }
    }

    void m1991b(Handler handler) {
        MessageCenter.unregistMessage(m_AppUI.MSG_APP_SAVESCREEN, handler);
        MessageCenter.unregistMessage(41, handler);
        MessageCenter.unregistMessage(49, handler);
        MessageCenter.unregistMessage(39, handler);
        MessageCenter.unregistMessage(m_AppUI.V_WM_VDATAENGINE, handler);
        MessageCenter.unregistMessage(50, handler);
        MessageCenter.unregistMessage(999, handler);
        BaseMapCallback.removeLayerDataInterface(this.f2054h);
    }

    void m1992b(MotionEvent motionEvent) {
        if (!this.f2039S.f2086e) {
            this.ad = motionEvent.getDownTime();
            if (this.ad - this.ac >= 400) {
                this.ac = this.ad;
            } else if (Math.abs(motionEvent.getX() - this.f2046Z) >= 120.0f || Math.abs(motionEvent.getY() - this.aa) >= 120.0f) {
                this.ac = this.ad;
            } else {
                this.ac = 0;
            }
            this.f2046Z = motionEvent.getX();
            this.aa = motionEvent.getY();
            m1964a(4, 0, ((int) motionEvent.getX()) | (((int) motionEvent.getY()) << 16));
            this.ab = true;
        }
    }

    public void m1993b(String str, Bundle bundle) {
        if (this.f2053g != null) {
            this.f2026D.m1923a(str);
            this.f2026D.m1922a(bundle);
            this.f2053g.m2214b(this.f2026D.a);
        }
    }

    public void m1994b(boolean z) {
        this.f2068x = z;
    }

    public void m1995c(Bundle bundle) {
        if (this.f2053g != null) {
            m1948g(bundle);
            this.f2053g.m2236g(bundle);
        }
    }

    public void m1996c(boolean z) {
        if (this.f2053g != null) {
            this.f2053g.m2200a(this.f2069y.a, z);
        }
    }

    public boolean m1997c() {
        return this.f2068x;
    }

    boolean m1998c(int i, int i2) {
        return i >= 0 && i <= this.f2036P + 0 && i2 >= 0 && i2 <= this.f2037Q + 0;
    }

    boolean m1999c(MotionEvent motionEvent) {
        if (this.f2039S.f2086e) {
            return true;
        }
        if (System.currentTimeMillis() - f2021k < 300) {
            return true;
        }
        if (this.f2059n) {
            for (C0482k d : this.f2052f) {
                d.mo1786d(m1988b((int) motionEvent.getX(), (int) motionEvent.getY()));
            }
            return true;
        }
        float abs = Math.abs(motionEvent.getX() - this.f2046Z);
        float abs2 = Math.abs(motionEvent.getY() - this.aa);
        float density = (float) (((double) SysOSUtil.getDensity()) > 1.5d ? ((double) SysOSUtil.getDensity()) * 1.5d : (double) SysOSUtil.getDensity());
        if (this.ab && abs / density <= 3.0f && abs2 / density <= 3.0f) {
            return true;
        }
        this.ab = false;
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        if (x < 0) {
            x = 0;
        }
        if (y < 0) {
            y = 0;
        }
        if (!this.f2050d) {
            return false;
        }
        m1959J();
        m1964a(3, 0, (y << 16) | x);
        return false;
    }

    public void m2000d(Bundle bundle) {
        if (this.f2053g != null) {
            m1948g(bundle);
            this.f2053g.m2238h(bundle);
        }
    }

    public void m2001d(boolean z) {
        if (this.f2053g != null) {
            this.f2053g.m2200a(this.ae.a, z);
        }
    }

    public boolean m2002d() {
        return (this.f2069y == null || this.f2053g == null) ? false : this.f2053g.m2225c(this.f2069y.a);
    }

    boolean m2003d(MotionEvent motionEvent) {
        if (this.f2059n) {
            for (C0482k e : this.f2052f) {
                e.mo1788e(m1988b((int) motionEvent.getX(), (int) motionEvent.getY()));
            }
            this.f2059n = false;
            return true;
        }
        boolean z = !this.f2039S.f2086e && motionEvent.getEventTime() - this.ad < 400 && Math.abs(motionEvent.getX() - this.f2046Z) < TitleBar.SHAREBTN_RIGHT_MARGIN && Math.abs(motionEvent.getY() - this.aa) < TitleBar.SHAREBTN_RIGHT_MARGIN;
        m1961L();
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        if (z) {
            return false;
        }
        if (x < 0) {
            x = 0;
        }
        m1964a(5, 0, ((y < 0 ? 0 : y) << 16) | x);
        return true;
    }

    void m2004e() {
        if (this.f2053g != null) {
            this.f2035M = new C0622H(this.f2053g);
        }
    }

    public void m2005e(boolean z) {
        if (this.f2053g != null) {
            this.f2065u = z;
            this.f2053g.m2216b(this.f2065u);
        }
    }

    public void m2006f(boolean z) {
        if (this.f2053g != null) {
            this.f2060p = z;
            this.f2053g.m2224c(this.f2060p);
        }
    }

    public boolean m2007f() {
        return this.f2060p;
    }

    public String m2008g() {
        return this.f2053g == null ? null : this.f2053g.m2229e(this.f2026D.a);
    }

    public void m2009g(boolean z) {
        if (this.f2053g != null) {
            this.f2053g.m2227d(z);
        }
    }

    public void m2010h(boolean z) {
        if (this.f2053g != null) {
            this.f2062r = z;
            this.f2053g.m2200a(this.f2026D.a, z);
        }
    }

    public boolean m2011h() {
        return this.f2065u;
    }

    public void m2012i(boolean z) {
        this.f2053g.m2231e(z);
        this.f2053g.m2226d(this.ag.a);
        this.f2053g.m2226d(this.ah.a);
    }

    public boolean m2013i() {
        return this.f2053g == null ? false : this.f2053g.m2243m();
    }

    public boolean m2014j() {
        return this.f2061q;
    }

    public void m2015k(boolean z) {
        if (this.f2053g != null) {
            this.f2063s = z;
            this.f2053g.m2200a(this.f2025C.a, z);
        }
    }

    public boolean m2016k() {
        return this.f2053g.m2208a(this.ae.a);
    }

    public void m2017l(boolean z) {
        if (this.f2053g != null) {
            this.f2064t = z;
            this.f2053g.m2200a(this.f2032J.a, z);
        }
    }

    public boolean m2018l() {
        return this.f2053g == null ? false : this.f2053g.m2247q();
    }

    public void m2019m() {
        if (this.f2053g != null) {
            this.f2053g.m2226d(this.f2027E.a);
            this.f2053g.m2226d(this.f2031I.a);
            this.f2053g.m2226d(this.f2029G.a);
            this.f2053g.m2226d(this.f2030H.a);
        }
    }

    public void m2020m(boolean z) {
        this.f2050d = z;
    }

    public void m2021n() {
        if (this.f2053g != null) {
            this.f2053g.m2248r();
            this.f2053g.m2214b(this.f2032J.a);
        }
    }

    public void m2022n(boolean z) {
        this.f2051e = z;
    }

    public MapBaseIndoorMapInfo m2023o() {
        return this.f2053g.m2249s();
    }

    public void m2024o(boolean z) {
        this.f2067w = z;
    }

    public void m2025p(boolean z) {
        this.f2066v = z;
    }

    public boolean m2026p() {
        return this.f2053g.m2250t();
    }

    public void m2027q(boolean z) {
        if (this.f2053g != null) {
            this.f2053g.m2200a(this.f2028F.a, z);
        }
    }

    public boolean m2028q() {
        return this.f2062r;
    }

    public void m2029r(boolean z) {
        if (this.f2053g != null) {
            this.f2053g.m2200a(this.ah.a, z);
        }
    }

    public boolean m2030r() {
        return this.f2063s;
    }

    public void m2031s() {
        if (this.f2053g != null) {
            this.f2053g.m2214b(this.f2032J.a);
        }
    }

    public void m2032t() {
        if (this.f2053g != null) {
            this.f2053g.m2235g();
        }
    }

    public void m2033u() {
        if (this.f2053g != null) {
            this.f2053g.m2237h();
        }
    }

    public boolean m2034v() {
        return this.f2050d;
    }

    public boolean m2035w() {
        return this.f2051e;
    }

    public boolean m2036x() {
        return this.f2067w;
    }

    public boolean m2037y() {
        return this.f2066v;
    }

    void m2038z() {
        if (!this.f2057l && !this.f2058m) {
            this.f2058m = true;
            for (C0482k a : this.f2052f) {
                a.mo1774a(m1953D());
            }
        }
    }
}
