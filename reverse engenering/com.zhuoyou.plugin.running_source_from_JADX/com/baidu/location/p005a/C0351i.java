package com.baidu.location.p005a;

import android.location.Location;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import com.baidu.location.Address;
import com.baidu.location.BDLocation;
import com.baidu.location.C0455f;
import com.baidu.location.Poi;
import com.baidu.location.p005a.C0346g.C0344a;
import com.baidu.location.p005a.C0346g.C0345b;
import com.baidu.location.p006h.C0459b;
import com.baidu.location.p006h.C0468j;
import com.baidu.location.p007b.C0367b;
import com.baidu.location.p008c.C0397b;
import com.baidu.location.p011e.C0411a;
import com.baidu.location.p011e.C0426h;
import com.baidu.location.p011e.C0426h.C0423a;
import com.baidu.location.p011e.C0426h.C0424b;
import com.baidu.location.p012f.C0441a;
import com.baidu.location.p012f.C0443b;
import com.baidu.location.p012f.C0444c;
import com.baidu.location.p012f.C0448d;
import com.baidu.location.p012f.C0451g;
import com.baidu.location.p012f.C0454h;
import com.tencent.open.yyb.TitleBar;
import java.util.List;

public class C0351i extends C0346g {
    public static boolean f238h = false;
    private static C0351i f239i = null;
    private double f240A;
    private boolean f241B;
    private long f242C;
    private long f243D;
    private C0349a f244E;
    private boolean f245F;
    private boolean f246G;
    private boolean f247H;
    private boolean f248I;
    private C0350b f249J;
    private boolean f250K;
    final int f251e;
    public C0345b f252f;
    public final Handler f253g;
    private boolean f254j;
    private String f255k;
    private BDLocation f256l;
    private BDLocation f257m;
    private C0451g f258n;
    private C0441a f259o;
    private C0451g f260p;
    private C0441a f261q;
    private boolean f262r;
    private volatile boolean f263s;
    private boolean f264t;
    private long f265u;
    private long f266v;
    private Address f267w;
    private String f268x;
    private List<Poi> f269y;
    private double f270z;

    private class C0349a implements Runnable {
        final /* synthetic */ C0351i f236a;

        private C0349a(C0351i c0351i) {
            this.f236a = c0351i;
        }

        public void run() {
            if (this.f236a.f245F) {
                this.f236a.f245F = false;
                if (!this.f236a.f246G) {
                    this.f236a.m296a(true);
                }
            }
        }
    }

    private class C0350b implements Runnable {
        final /* synthetic */ C0351i f237a;

        private C0350b(C0351i c0351i) {
            this.f237a = c0351i;
        }

        public void run() {
            if (this.f237a.f250K) {
                this.f237a.f250K = false;
            }
            if (this.f237a.f264t) {
                this.f237a.f264t = false;
                this.f237a.m288g(null);
            }
        }
    }

    private C0351i() {
        this.f251e = 1000;
        this.f254j = true;
        this.f252f = null;
        this.f255k = null;
        this.f256l = null;
        this.f257m = null;
        this.f258n = null;
        this.f259o = null;
        this.f260p = null;
        this.f261q = null;
        this.f262r = true;
        this.f263s = false;
        this.f264t = false;
        this.f265u = 0;
        this.f266v = 0;
        this.f267w = null;
        this.f268x = null;
        this.f269y = null;
        this.f241B = false;
        this.f242C = 0;
        this.f243D = 0;
        this.f244E = null;
        this.f245F = false;
        this.f246G = false;
        this.f247H = true;
        this.f253g = new C0344a(this);
        this.f248I = false;
        this.f249J = null;
        this.f250K = false;
        this.f252f = new C0345b(this);
    }

    private boolean m275a(C0441a c0441a) {
        this.b = C0443b.m855a().m873f();
        return this.b == c0441a ? false : this.b == null || c0441a == null || !c0441a.m842a(this.b);
    }

    private boolean m276a(C0451g c0451g) {
        this.a = C0454h.m948a().m964m();
        return c0451g == this.a ? false : this.a == null || c0451g == null || !c0451g.m942c(this.a);
    }

    private boolean m279b(C0441a c0441a) {
        return c0441a == null ? false : this.f261q == null || !c0441a.m842a(this.f261q);
    }

    public static synchronized C0351i m280c() {
        C0351i c0351i;
        synchronized (C0351i.class) {
            if (f239i == null) {
                f239i = new C0351i();
            }
            c0351i = f239i;
        }
        return c0351i;
    }

    private void m281c(Message message) {
        boolean z = message.getData().getBoolean("isWaitingLocTag", false);
        if (z) {
            f238h = true;
        }
        if (z) {
        }
        if (!C0397b.m541a().m587g()) {
            int d = C0332a.m176a().m187d(message);
            C0352j.m308a().m315d();
            switch (d) {
                case 1:
                    m284d(message);
                    return;
                case 2:
                    m287f(message);
                    return;
                case 3:
                    if (C0448d.m886a().m925i()) {
                        m286e(message);
                        return;
                    }
                    return;
                default:
                    throw new IllegalArgumentException(String.format("this type %d is illegal", new Object[]{Integer.valueOf(d)}));
            }
        }
    }

    private void m284d(Message message) {
        if (C0448d.m886a().m925i()) {
            m286e(message);
            C0352j.m308a().m314c();
            return;
        }
        m287f(message);
        C0352j.m308a().m312b();
    }

    private void m286e(Message message) {
        BDLocation bDLocation = new BDLocation(C0448d.m886a().m922f());
        if (C0468j.f904f.equals("all") || C0468j.f905g || C0468j.f906h) {
            float[] fArr = new float[2];
            Location.distanceBetween(this.f240A, this.f270z, bDLocation.getLatitude(), bDLocation.getLongitude(), fArr);
            if (fArr[0] < 100.0f) {
                if (this.f267w != null) {
                    bDLocation.setAddr(this.f267w);
                }
                if (this.f268x != null) {
                    bDLocation.setLocationDescribe(this.f268x);
                }
                if (this.f269y != null) {
                    bDLocation.setPoiList(this.f269y);
                }
            } else {
                this.f241B = true;
                m287f(null);
            }
        }
        this.f256l = bDLocation;
        this.f257m = null;
        C0332a.m176a().m181a(bDLocation);
    }

    private void m287f(Message message) {
        if (this.f262r) {
            this.f243D = SystemClock.uptimeMillis();
            m288g(message);
        } else if (!this.f263s) {
            this.f243D = SystemClock.uptimeMillis();
            if (C0454h.m948a().m957e()) {
                this.f264t = true;
                if (this.f249J == null) {
                    this.f249J = new C0350b();
                }
                if (this.f250K && this.f249J != null) {
                    this.f253g.removeCallbacks(this.f249J);
                }
                this.f253g.postDelayed(this.f249J, 3500);
                this.f250K = true;
                return;
            }
            m288g(message);
        }
    }

    private void m288g(Message message) {
        if (!this.f263s) {
            if (System.currentTimeMillis() - this.f265u <= 0 || System.currentTimeMillis() - this.f265u >= 1000 || this.f256l == null) {
                this.f263s = true;
                this.f254j = m275a(this.f259o);
                if (m276a(this.f258n) || this.f254j || this.f256l == null || this.f241B) {
                    this.f265u = System.currentTimeMillis();
                    String a = m264a(null);
                    if (a == null) {
                        m290l();
                        long currentTimeMillis = System.currentTimeMillis();
                        if (currentTimeMillis - this.f242C > 60000) {
                            this.f242C = currentTimeMillis;
                        }
                        a = C0454h.m948a().m961j();
                        a = a != null ? a + m267b() : "" + m267b();
                        String a2 = C0459b.m980a().m981a(true);
                        if (a2 != null) {
                            a = a + a2;
                        }
                    }
                    if (this.f255k != null) {
                        a = a + this.f255k;
                        this.f255k = null;
                    }
                    this.f252f.m261a(a);
                    this.f259o = this.b;
                    this.f258n = this.a;
                    if (m289k()) {
                        this.f259o = this.b;
                        this.f258n = this.a;
                    }
                    if (C0426h.m767a().m783h()) {
                        if (this.f244E == null) {
                            this.f244E = new C0349a();
                        }
                        this.f253g.postDelayed(this.f244E, C0426h.m767a().m775a(C0444c.m876a(C0443b.m855a().m872e())));
                        this.f245F = true;
                    }
                    if (this.f262r) {
                        this.f262r = false;
                        if (C0454h.m952h() && message != null && C0332a.m176a().m189e(message) < 1000 && C0426h.m767a().m779d()) {
                            C0426h.m767a().m784i();
                        }
                        C0367b.m381a().m388b();
                        return;
                    }
                    return;
                }
                if (this.f257m != null && System.currentTimeMillis() - this.f266v > StatisticConfig.MIN_UPLOAD_INTERVAL) {
                    this.f256l = this.f257m;
                    this.f257m = null;
                }
                if (C0352j.m308a().m317f()) {
                    this.f256l.setDirection(C0352j.m308a().m319h());
                }
                C0332a.m176a().m181a(this.f256l);
                m291m();
                return;
            }
            C0332a.m176a().m181a(this.f256l);
            m291m();
        }
    }

    private boolean m289k() {
        BDLocation bDLocation = null;
        double random = Math.random();
        SystemClock.uptimeMillis();
        C0441a f = C0443b.m855a().m873f();
        C0451g l = C0454h.m948a().m963l();
        boolean z = f != null && f.m846e() && (l == null || l.m933a() == 0);
        if (C0426h.m767a().m779d() && C0426h.m767a().m781f() && (z || (0.0d < random && random < C0426h.m767a().m790o()))) {
            bDLocation = C0426h.m767a().m776a(C0443b.m855a().m873f(), C0454h.m948a().m963l(), null, C0424b.IS_MIX_MODE, C0423a.NEED_TO_LOG);
        }
        if (bDLocation == null || bDLocation.getLocType() != 66 || !this.f263s) {
            return false;
        }
        BDLocation bDLocation2 = new BDLocation(bDLocation);
        bDLocation2.setLocType(161);
        if (!this.f263s) {
            return false;
        }
        this.f246G = true;
        C0332a.m176a().m181a(bDLocation2);
        this.f256l = bDLocation2;
        return true;
    }

    private String[] m290l() {
        String[] strArr = new String[]{"", "Location failed beacuse we can not get any loc information!"};
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("&apl=");
        int b = C0468j.m1017b(C0455f.getServiceContext());
        if (b == 1) {
            strArr[1] = "Location failed beacuse we can not get any loc information in airplane mode, you can turn it off and try again!!";
        }
        stringBuffer.append(b);
        String d = C0468j.m1025d(C0455f.getServiceContext());
        if (d.contains("0|0|")) {
            strArr[1] = "Location failed beacuse we can not get any loc information without any location permission!";
        }
        stringBuffer.append(d);
        if (VERSION.SDK_INT >= 23) {
            stringBuffer.append("&loc=");
            if (C0468j.m1022c(C0455f.getServiceContext()) == 0) {
                strArr[1] = "Location failed beacuse we can not get any loc information with the phone loc mode is off, you can turn it on and try again!";
            }
            stringBuffer.append(C0468j.m1022c(C0455f.getServiceContext()));
        }
        stringBuffer.append(C0454h.m948a().m958f());
        stringBuffer.append(C0443b.m855a().m874g());
        stringBuffer.append(C0468j.m1027e(C0455f.getServiceContext()));
        strArr[0] = stringBuffer.toString();
        return strArr;
    }

    private void m291m() {
        this.f263s = false;
        this.f246G = false;
        this.f247H = false;
        this.f241B = false;
        m292n();
    }

    private void m292n() {
        if (this.f256l != null) {
            C0362s.m350a().m368c();
        }
    }

    public Address m293a(BDLocation bDLocation) {
        if (C0468j.f904f.equals("all") || C0468j.f905g || C0468j.f906h) {
            float[] fArr = new float[2];
            Location.distanceBetween(this.f240A, this.f270z, bDLocation.getLatitude(), bDLocation.getLongitude(), fArr);
            if (fArr[0] >= 100.0f) {
                this.f268x = null;
                this.f269y = null;
                this.f241B = true;
                m287f(null);
            } else if (this.f267w != null) {
                return this.f267w;
            }
        }
        return null;
    }

    public void mo1744a() {
        if (this.f244E != null && this.f245F) {
            this.f245F = false;
            this.f253g.removeCallbacks(this.f244E);
        }
        if (C0448d.m886a().m925i()) {
            BDLocation bDLocation = new BDLocation(C0448d.m886a().m922f());
            if (C0468j.f904f.equals("all") || C0468j.f905g || C0468j.f906h) {
                float[] fArr = new float[2];
                Location.distanceBetween(this.f240A, this.f270z, bDLocation.getLatitude(), bDLocation.getLongitude(), fArr);
                if (fArr[0] < 100.0f) {
                    if (this.f267w != null) {
                        bDLocation.setAddr(this.f267w);
                    }
                    if (this.f268x != null) {
                        bDLocation.setLocationDescribe(this.f268x);
                    }
                    if (this.f269y != null) {
                        bDLocation.setPoiList(this.f269y);
                    }
                }
            }
            C0332a.m176a().m181a(bDLocation);
            m291m();
        } else if (this.f246G) {
            m291m();
        } else {
            BDLocation a;
            if (C0426h.m767a().m779d() && C0426h.m767a().m780e()) {
                a = C0426h.m767a().m776a(C0443b.m855a().m873f(), C0454h.m948a().m963l(), null, C0424b.IS_NOT_MIX_MODE, C0423a.NEED_TO_LOG);
                if (a != null && a.getLocType() == 66) {
                    C0332a.m176a().m181a(a);
                }
            } else {
                a = null;
            }
            if (a == null || a.getLocType() == 67) {
                if (this.f254j || this.f256l == null) {
                    if (C0411a.m637a().f577a) {
                        a = C0411a.m637a().m655a(false);
                    } else if (a == null) {
                        a = new BDLocation();
                        a.setLocType(67);
                    }
                    C0332a.m176a().m181a(a);
                    boolean z = true;
                    if (C0468j.f904f.equals("all") && a.getAddrStr() == null) {
                        z = false;
                    }
                    if (C0468j.f905g && a.getLocationDescribe() == null) {
                        z = false;
                    }
                    if (C0468j.f906h && a.getPoiList() == null) {
                        z = false;
                    }
                    if (!z) {
                        a.setLocType(67);
                    }
                } else {
                    C0332a.m176a().m181a(this.f256l);
                }
            }
            this.f257m = null;
            m291m();
        }
    }

    public void mo1745a(Message message) {
        if (this.f244E != null && this.f245F) {
            this.f245F = false;
            this.f253g.removeCallbacks(this.f244E);
        }
        BDLocation bDLocation = (BDLocation) message.obj;
        BDLocation bDLocation2 = new BDLocation(bDLocation);
        if (bDLocation.hasAddr()) {
            this.f267w = bDLocation.getAddress();
            this.f270z = bDLocation.getLongitude();
            this.f240A = bDLocation.getLatitude();
        }
        if (bDLocation.getLocationDescribe() != null) {
            this.f268x = bDLocation.getLocationDescribe();
            this.f270z = bDLocation.getLongitude();
            this.f240A = bDLocation.getLatitude();
        }
        if (bDLocation.getPoiList() != null) {
            this.f269y = bDLocation.getPoiList();
            this.f270z = bDLocation.getLongitude();
            this.f240A = bDLocation.getLatitude();
        }
        float[] fArr;
        if (C0448d.m886a().m925i()) {
            bDLocation = new BDLocation(C0448d.m886a().m922f());
            if (C0468j.f904f.equals("all") || C0468j.f905g || C0468j.f906h) {
                fArr = new float[2];
                Location.distanceBetween(this.f240A, this.f270z, bDLocation.getLatitude(), bDLocation.getLongitude(), fArr);
                if (fArr[0] < 100.0f) {
                    if (this.f267w != null) {
                        bDLocation.setAddr(this.f267w);
                    }
                    if (this.f268x != null) {
                        bDLocation.setLocationDescribe(this.f268x);
                    }
                    if (this.f269y != null) {
                        bDLocation.setPoiList(this.f269y);
                    }
                }
            }
            C0332a.m176a().m181a(bDLocation);
            m291m();
        } else if (this.f246G) {
            fArr = new float[2];
            if (this.f256l != null) {
                Location.distanceBetween(this.f256l.getLatitude(), this.f256l.getLongitude(), bDLocation.getLatitude(), bDLocation.getLongitude(), fArr);
            }
            if (fArr[0] > TitleBar.SHAREBTN_RIGHT_MARGIN) {
                this.f256l = bDLocation;
                if (!this.f247H) {
                    this.f247H = false;
                    C0332a.m176a().m181a(bDLocation);
                }
            } else if (bDLocation.getUserIndoorState() > -1) {
                this.f256l = bDLocation;
                C0332a.m176a().m181a(bDLocation);
            }
            m291m();
        } else {
            boolean z;
            this.f257m = null;
            if (bDLocation.getLocType() == 161 && "cl".equals(bDLocation.getNetworkLocationType()) && this.f256l != null && this.f256l.getLocType() == 161 && "wf".equals(this.f256l.getNetworkLocationType()) && System.currentTimeMillis() - this.f266v < StatisticConfig.MIN_UPLOAD_INTERVAL) {
                z = true;
                this.f257m = bDLocation;
            } else {
                z = false;
            }
            if (z) {
                C0332a.m176a().m181a(this.f256l);
            } else {
                C0332a.m176a().m181a(bDLocation);
                this.f266v = System.currentTimeMillis();
            }
            if (!C0468j.m1016a(bDLocation)) {
                this.f256l = null;
            } else if (!z) {
                this.f256l = bDLocation;
            }
            int a = C0468j.m1009a(c, "ssid\":\"", "\"");
            if (a == Integer.MIN_VALUE || this.f258n == null) {
                this.f255k = null;
            } else {
                this.f255k = this.f258n.m941c(a);
            }
            if (C0426h.m767a().m779d() && bDLocation.getLocType() == 161 && "cl".equals(bDLocation.getNetworkLocationType()) && m279b(this.f259o)) {
                C0426h.m767a().m776a(this.f259o, null, bDLocation2, C0424b.IS_NOT_MIX_MODE, C0423a.NO_NEED_TO_LOG);
                this.f261q = this.f259o;
            }
            if (C0426h.m767a().m779d() && bDLocation.getLocType() == 161 && "wf".equals(bDLocation.getNetworkLocationType())) {
                C0426h.m767a().m776a(null, this.f258n, bDLocation2, C0424b.IS_NOT_MIX_MODE, C0423a.NO_NEED_TO_LOG);
                this.f260p = this.f258n;
            }
            if (this.f259o != null) {
                C0411a.m637a().m656a(c, this.f259o, this.f258n, bDLocation2);
            }
            if (C0454h.m952h()) {
                C0426h.m767a().m784i();
                C0426h.m767a().m788m();
            }
            m291m();
        }
    }

    public void m296a(boolean z) {
        BDLocation bDLocation = null;
        if (C0426h.m767a().m779d() && C0426h.m767a().m782g()) {
            bDLocation = C0426h.m767a().m776a(C0443b.m855a().m873f(), C0454h.m948a().m963l(), null, C0424b.IS_NOT_MIX_MODE, C0423a.NEED_TO_LOG);
            if ((bDLocation == null || bDLocation.getLocType() == 67) && z && C0411a.m637a().f577a) {
                bDLocation = C0411a.m637a().m655a(false);
            }
        } else if (z && C0411a.m637a().f577a) {
            bDLocation = C0411a.m637a().m655a(false);
        }
        if (bDLocation != null && bDLocation.getLocType() == 66) {
            boolean z2 = true;
            if (C0468j.f904f.equals("all") && bDLocation.getAddrStr() == null) {
                z2 = false;
            }
            if (C0468j.f905g && bDLocation.getLocationDescribe() == null) {
                z2 = false;
            }
            if (C0468j.f906h && bDLocation.getPoiList() == null) {
                z2 = false;
            }
            if (z2) {
                C0332a.m176a().m181a(bDLocation);
            }
        }
    }

    public void m297b(Message message) {
        if (this.f248I) {
            m281c(message);
        }
    }

    public void m298b(BDLocation bDLocation) {
        m306j();
        this.f256l = bDLocation;
        this.f256l.setIndoorLocMode(false);
    }

    public void m299c(BDLocation bDLocation) {
        this.f256l = new BDLocation(bDLocation);
    }

    public void m300d() {
        this.f262r = true;
        this.f263s = false;
        this.f248I = true;
    }

    public void m301e() {
        this.f263s = false;
        this.f264t = false;
        this.f246G = false;
        this.f247H = true;
        m306j();
        this.f248I = false;
    }

    public String m302f() {
        return this.f268x;
    }

    public List<Poi> m303g() {
        return this.f269y;
    }

    public boolean m304h() {
        return this.f254j;
    }

    public void m305i() {
        if (this.f264t) {
            m288g(null);
            this.f264t = false;
            return;
        }
        C0367b.m381a().m390d();
    }

    public void m306j() {
        this.f256l = null;
    }
}
