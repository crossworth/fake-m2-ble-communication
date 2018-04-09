package com.baidu.location.p008c;

import android.location.Location;
import android.net.wifi.ScanResult;
import android.os.Handler;
import android.os.Message;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.C0455f;
import com.baidu.location.Jni;
import com.baidu.location.p005a.C0332a;
import com.baidu.location.p005a.C0351i;
import com.baidu.location.p006h.C0335e;
import com.baidu.location.p006h.C0459b;
import com.baidu.location.p006h.C0468j;
import com.baidu.location.p008c.C0401e.C0399a;
import com.baidu.location.p008c.p009a.C0386d;
import com.baidu.location.p012f.C0443b;
import com.baidu.location.p012f.C0448d;
import com.baidu.location.p012f.C0451g;
import com.baidu.location.p012f.C0454h;
import com.baidu.mapapi.map.WeightedLatLng;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class C0397b {
    private static C0397b f471i;
    private String f472A;
    private String f473B;
    private int f474C;
    private boolean f475D;
    private int f476E;
    private C0390a<String> f477F;
    private int f478G;
    private C0390a<String> f479H;
    private double f480I;
    private double f481J;
    private double f482K;
    private boolean f483L;
    private boolean f484M;
    private boolean f485N;
    private int f486O;
    boolean f487a;
    boolean f488b;
    public C0394d f489c;
    public SimpleDateFormat f490d;
    private boolean f491e;
    private BDLocationListener f492f;
    private BDLocationListener f493g;
    private int f494h;
    private long f495j;
    private volatile boolean f496k;
    private C0401e f497l;
    private C0395e f498m;
    private C0396f f499n;
    private long f500o;
    private boolean f501p;
    private boolean f502q;
    private long f503r;
    private int f504s;
    private int f505t;
    private C0399a f506u;
    private int f507v;
    private int f508w;
    private String f509x;
    private String f510y;
    private String f511z;

    class C0391a {
        final /* synthetic */ C0397b f444a;
        private HashMap<String, Integer> f445b = new HashMap();
        private double f446c = 0.0d;

        public C0391a(C0397b c0397b, C0451g c0451g) {
            this.f444a = c0397b;
            if (c0451g.f802a != null) {
                for (ScanResult scanResult : c0451g.f802a) {
                    int abs = Math.abs(scanResult.level);
                    this.f445b.put(scanResult.BSSID, Integer.valueOf(abs));
                    this.f446c = ((double) ((100 - abs) * (100 - abs))) + this.f446c;
                }
                this.f446c = Math.sqrt(this.f446c + WeightedLatLng.DEFAULT_INTENSITY);
            }
        }

        double m509a(C0391a c0391a) {
            double d = 0.0d;
            for (String str : this.f445b.keySet()) {
                int intValue = ((Integer) this.f445b.get(str)).intValue();
                Integer num = (Integer) c0391a.m510a().get(str);
                if (num != null) {
                    d = ((double) ((100 - num.intValue()) * (100 - intValue))) + d;
                }
            }
            return d / (this.f446c * c0391a.m511b());
        }

        public HashMap<String, Integer> m510a() {
            return this.f445b;
        }

        public double m511b() {
            return this.f446c;
        }
    }

    class C0392b {
        double f447a;
        double f448b;
        long f449c;
        int f450d;
        boolean f451e = false;
        final /* synthetic */ C0397b f452f;

        public C0392b(C0397b c0397b, double d, double d2, long j, int i) {
            this.f452f = c0397b;
            this.f447a = d;
            this.f448b = d2;
            this.f449c = j;
            this.f450d = i;
        }

        public double m512a() {
            return this.f447a;
        }

        public int m513a(C0392b c0392b) {
            return Math.abs(this.f450d - c0392b.m519c());
        }

        public void m514a(double d) {
            this.f447a = d;
        }

        public void m515a(boolean z) {
            this.f451e = z;
        }

        public double m516b() {
            return this.f448b;
        }

        public float m517b(C0392b c0392b) {
            float[] fArr = new float[1];
            Location.distanceBetween(this.f448b, this.f447a, c0392b.f448b, c0392b.f447a, fArr);
            return fArr[0];
        }

        public void m518b(double d) {
            this.f448b = d;
        }

        public int m519c() {
            return this.f450d;
        }

        public boolean m520c(C0392b c0392b) {
            int a = m513a(c0392b);
            return a != 0 && ((double) (m517b(c0392b) / ((float) a))) <= WeightedLatLng.DEFAULT_INTENSITY + (0.5d * Math.pow(1.2d, (double) (1 - a)));
        }

        public boolean m521d() {
            return this.f451e;
        }
    }

    class C0393c {
        final /* synthetic */ C0397b f453a;
        private C0392b[] f454b;
        private int f455c;
        private int f456d;

        public C0393c(C0397b c0397b) {
            this(c0397b, 5);
        }

        public C0393c(C0397b c0397b, int i) {
            this.f453a = c0397b;
            this.f454b = new C0392b[(i + 1)];
            this.f455c = 0;
            this.f456d = 0;
        }

        public C0392b m522a() {
            return this.f454b[((this.f456d - 1) + this.f454b.length) % this.f454b.length];
        }

        public C0392b m523a(int i) {
            return this.f454b[(((this.f456d - 1) - i) + this.f454b.length) % this.f454b.length];
        }

        public void m524a(C0392b c0392b) {
            if (this.f455c != this.f456d) {
                C0392b a = m522a();
                if (a.m519c() == c0392b.m519c()) {
                    a.m514a((a.m512a() + c0392b.m512a()) / 2.0d);
                    a.m518b((a.m516b() + c0392b.m516b()) / 2.0d);
                    return;
                }
            }
            if (m525b()) {
                m529d();
            }
            m526b(c0392b);
        }

        public boolean m525b() {
            return (this.f456d + 1) % this.f454b.length == this.f455c;
        }

        public boolean m526b(C0392b c0392b) {
            if (m525b()) {
                return false;
            }
            this.f454b[this.f456d] = c0392b;
            this.f456d = (this.f456d + 1) % this.f454b.length;
            return true;
        }

        public boolean m527c() {
            return this.f456d == this.f455c;
        }

        public boolean m528c(C0392b c0392b) {
            if (m527c()) {
                return true;
            }
            if (c0392b.m520c(m522a())) {
                return true;
            }
            if (m522a().m521d()) {
                return false;
            }
            for (int i = 0; i < m530e(); i++) {
                C0392b a = m523a(i);
                if (a.m521d() && a.m520c(c0392b)) {
                    return true;
                }
            }
            return false;
        }

        public boolean m529d() {
            if (this.f455c == this.f456d) {
                return false;
            }
            this.f455c = (this.f455c + 1) % this.f454b.length;
            return true;
        }

        public int m530e() {
            return ((this.f456d - this.f455c) + this.f454b.length) % this.f454b.length;
        }

        public String toString() {
            int i;
            int i2 = 0;
            String str = "";
            for (i = 0; i < m530e(); i++) {
                str = str + this.f454b[(this.f455c + i) % this.f454b.length].f447a + ",";
            }
            str = str + "  ";
            for (i = 0; i < m530e(); i++) {
                str = str + this.f454b[(this.f455c + i) % this.f454b.length].f448b + ",";
            }
            String str2 = str + "  ";
            while (i2 < m530e()) {
                str2 = str2 + this.f454b[(this.f455c + i2) % this.f454b.length].f450d + ",";
                i2++;
            }
            return str2 + "  ";
        }
    }

    public class C0394d extends Handler {
        final /* synthetic */ C0397b f457a;

        public C0394d(C0397b c0397b) {
            this.f457a = c0397b;
        }

        public void handleMessage(Message message) {
            if (C0455f.isServing) {
                switch (message.what) {
                    case 21:
                        this.f457a.m543a(message);
                        return;
                    case 28:
                        this.f457a.m554b(message);
                        return;
                    case 41:
                        this.f457a.m571l();
                        return;
                    case 801:
                        this.f457a.m544a((BDLocation) message.obj);
                        return;
                    default:
                        super.dispatchMessage(message);
                        return;
                }
            }
        }
    }

    class C0395e extends Thread {
        final /* synthetic */ C0397b f458a;
        private volatile boolean f459b = true;
        private long f460c = 0;

        C0395e(C0397b c0397b) {
            this.f458a = c0397b;
        }

        public void run() {
            while (this.f459b) {
                if ((((this.f458a.f496k && System.currentTimeMillis() - this.f460c > this.f458a.f495j) || System.currentTimeMillis() - this.f460c > 10000) && this.f458a.f497l.m621c() == 1) || System.currentTimeMillis() - this.f460c > 17500) {
                    C0454h.m948a().m959g();
                    this.f458a.f497l.m623e();
                    this.f460c = System.currentTimeMillis();
                    this.f458a.f496k = false;
                }
                if (System.currentTimeMillis() - this.f458a.f500o > 22000) {
                    this.f458a.f489c.sendEmptyMessage(41);
                }
                if (System.currentTimeMillis() - this.f458a.f503r > 60000) {
                    C0397b.m541a().m584d();
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    this.f459b = false;
                    return;
                }
            }
        }
    }

    class C0396f extends C0335e {
        final /* synthetic */ C0397b f461a;
        private boolean f462b;
        private boolean f463c;
        private String f464d;
        private String f465e;
        private C0391a f466f;
        private C0393c f467p;
        private int f468q;
        private long f469r;
        private long f470s;

        public C0396f(C0397b c0397b) {
            this.f461a = c0397b;
            this.f462b = false;
            this.f463c = false;
            this.f464d = null;
            this.f465e = null;
            this.f466f = null;
            this.f467p = null;
            this.f468q = -1;
            this.f469r = 0;
            this.f470s = 0;
            this.k = new HashMap();
            this.f467p = new C0393c(c0397b);
        }

        private boolean m532a(C0451g c0451g, double d) {
            C0391a c0391a = new C0391a(this.f461a, c0451g);
            if (this.f466f != null && c0391a.m509a(this.f466f) > d) {
                return false;
            }
            this.f466f = c0391a;
            return true;
        }

        public void mo1741a() {
            this.h = C0468j.m1023c();
            this.i = 1;
            String encodeTp4 = Jni.encodeTp4(this.f464d);
            this.f464d = null;
            this.k.put("bloc", encodeTp4);
            this.f469r = System.currentTimeMillis();
        }

        public void mo1742a(boolean z) {
            if (!z || this.j == null) {
                this.f461a.f504s = this.f461a.f504s + 1;
                this.f461a.f486O = 0;
                this.f461a.f485N = true;
                this.f462b = false;
                if (this.f461a.f504s > 40) {
                    this.f461a.m584d();
                } else {
                    return;
                }
            }
            try {
                BDLocation bDLocation = new BDLocation(this.j);
                this.f461a.f485N = false;
                if (this.f461a.f497l.m622d() == -1) {
                    this.f461a.f488b = false;
                }
                if (bDLocation.getBuildingName() != null) {
                    this.f461a.f472A = bDLocation.getBuildingName();
                }
                if (bDLocation.getFloor() != null) {
                    this.f461a.f503r = System.currentTimeMillis();
                    this.f470s = System.currentTimeMillis();
                    int i = (int) (this.f470s - this.f469r);
                    if (i > 10000) {
                        this.f461a.f486O = 0;
                    } else if (i < MessageHandler.WHAT_ITEM_SELECTED) {
                        this.f461a.f486O = 2;
                    } else {
                        this.f461a.f486O = 1;
                    }
                    if (bDLocation.getFloor().contains("-a")) {
                        this.f461a.f483L = true;
                        bDLocation.setFloor(bDLocation.getFloor().split("-")[0]);
                    } else {
                        this.f461a.f483L = false;
                    }
                    this.f461a.f477F.add(bDLocation.getFloor());
                }
                if (this.f461a.f487a && this.f461a.f488b) {
                    C0392b c0392b = new C0392b(this.f461a, bDLocation.getLongitude(), bDLocation.getLatitude(), System.currentTimeMillis(), this.f461a.f497l.m622d());
                    if (this.f467p.m528c(c0392b)) {
                        c0392b.m515a(true);
                        Message obtainMessage = this.f461a.f489c.obtainMessage(21);
                        obtainMessage.obj = bDLocation;
                        obtainMessage.sendToTarget();
                    }
                    if (bDLocation.getFloor() != null) {
                        this.f467p.m524a(c0392b);
                    }
                } else {
                    Message obtainMessage2 = this.f461a.f489c.obtainMessage(21);
                    obtainMessage2.obj = bDLocation;
                    obtainMessage2.sendToTarget();
                }
            } catch (Exception e) {
            }
            if (this.k != null) {
                this.k.clear();
            }
            this.f462b = false;
        }

        public void mo1747b() {
            if (this.f462b) {
                this.f463c = true;
                return;
            }
            StringBuffer stringBuffer = new StringBuffer(1024);
            String h = C0443b.m855a().m873f().m849h();
            this.f461a.f482K = 0.5d;
            C0451g n = C0454h.m948a().m965n();
            String a = n.m934a(32);
            if (a != null && a.length() >= 10) {
                if (this.f465e == null || !this.f465e.equals(a)) {
                    this.f465e = a;
                    int d = this.f461a.f497l.m622d();
                    boolean z = this.f468q < 0 || d - this.f468q > this.f461a.f494h;
                    if (this.f461a.f487a && this.f461a.f488b) {
                        if (!(!this.f461a.f502q || m532a(n, 0.8d) || z)) {
                            return;
                        }
                    } else if (this.f461a.f487a && this.f461a.f502q && !m532a(n, 0.7d) && !z) {
                        return;
                    }
                    this.f468q = d;
                    this.f462b = true;
                    stringBuffer.append(h);
                    stringBuffer.append("&coor=gcj02");
                    stringBuffer.append("&lt=1");
                    stringBuffer.append(a);
                    stringBuffer.append(C0459b.m980a().m981a(false));
                    this.f464d = stringBuffer.toString();
                    m204e();
                }
            }
        }

        public synchronized void mo1746c() {
            if (!this.f462b) {
                if (this.f463c) {
                    this.f463c = false;
                    mo1747b();
                }
            }
        }
    }

    private C0397b() {
        this.f487a = false;
        this.f488b = false;
        this.f494h = 5;
        this.f495j = 3000;
        this.f496k = true;
        this.f489c = null;
        this.f497l = null;
        this.f498m = null;
        this.f499n = null;
        this.f500o = 0;
        this.f501p = false;
        this.f502q = false;
        this.f503r = 0;
        this.f504s = 0;
        this.f505t = 0;
        this.f507v = 0;
        this.f508w = 0;
        this.f509x = null;
        this.f510y = null;
        this.f511z = null;
        this.f472A = null;
        this.f473B = null;
        this.f474C = 0;
        this.f475D = true;
        this.f476E = 7;
        this.f477F = null;
        this.f478G = 20;
        this.f479H = null;
        this.f480I = 0.0d;
        this.f481J = 0.0d;
        this.f482K = 0.5d;
        this.f483L = false;
        this.f484M = true;
        this.f485N = false;
        this.f490d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.f486O = 2;
        this.f491e = false;
        this.f492f = new C0398c(this);
        this.f489c = new C0394d(this);
        this.f506u = new C0400d(this);
        this.f497l = new C0401e(C0455f.getServiceContext(), this.f506u);
        this.f499n = new C0396f(this);
        this.f477F = new C0390a(this.f476E);
        this.f479H = new C0390a(this.f478G);
    }

    public static synchronized C0397b m541a() {
        C0397b c0397b;
        synchronized (C0397b.class) {
            if (f471i == null) {
                f471i = new C0397b();
            }
            c0397b = f471i;
        }
        return c0397b;
    }

    private void m543a(Message message) {
        BDLocation bDLocation = (BDLocation) message.obj;
        if (bDLocation.getLocType() == 161) {
            this.f504s = 0;
            if (bDLocation.getBuildingID() == null) {
                this.f502q = false;
                this.f505t++;
                if (this.f505t > 3) {
                    m584d();
                }
            } else {
                this.f507v = 0;
                this.f505t = 0;
                this.f502q = true;
                bDLocation.setIndoorLocMode(true);
                if (this.f480I < 0.1d || this.f481J < 0.1d) {
                    this.f481J = bDLocation.getLatitude();
                    this.f480I = bDLocation.getLongitude();
                }
                if (this.f509x == null) {
                    this.f509x = bDLocation.getFloor();
                }
                this.f510y = bDLocation.getBuildingID();
                this.f511z = bDLocation.getBuildingName();
                this.f473B = bDLocation.getNetworkLocationType();
                if (this.f473B.equals("ble") && this.f484M) {
                    this.f481J = bDLocation.getLatitude();
                    this.f480I = bDLocation.getLongitude();
                    this.f484M = false;
                }
                this.f474C = bDLocation.isParkAvailable();
                if (bDLocation.getFloor().equals(m572m())) {
                    this.f509x = bDLocation.getFloor();
                    if (!this.f483L) {
                        double longitude = ((this.f480I * ((double) 1000000)) * this.f482K) + ((WeightedLatLng.DEFAULT_INTENSITY - this.f482K) * (bDLocation.getLongitude() * ((double) 1000000)));
                        bDLocation.setLatitude((((this.f481J * ((double) 1000000)) * this.f482K) + ((WeightedLatLng.DEFAULT_INTENSITY - this.f482K) * (bDLocation.getLatitude() * ((double) 1000000)))) / ((double) 1000000));
                        bDLocation.setLongitude(longitude / ((double) 1000000));
                    }
                    this.f481J = bDLocation.getLatitude();
                    this.f480I = bDLocation.getLongitude();
                } else {
                    return;
                }
            }
            if (!bDLocation.getNetworkLocationType().equals("ble")) {
                C0351i.m280c().m298b(bDLocation);
            }
        } else if (bDLocation.getLocType() == 63) {
            this.f504s++;
            this.f502q = false;
            this.f485N = true;
            if (this.f504s > 10) {
                m584d();
            } else {
                return;
            }
        } else {
            this.f504s = 0;
            this.f502q = false;
        }
        if (this.f502q && !C0448d.m886a().m925i()) {
            if (bDLocation.getTime() == null) {
                bDLocation.setTime(this.f490d.format(new Date()));
            }
            BDLocation bDLocation2 = new BDLocation(bDLocation);
            if (C0386d.m487a().m500a(bDLocation2)) {
                m545a(bDLocation2, 21);
            } else {
                bDLocation2.setNetworkLocationType(bDLocation2.getNetworkLocationType() + "2");
                m545a(bDLocation2, 21);
            }
        }
        this.f499n.mo1746c();
    }

    private void m544a(BDLocation bDLocation) {
    }

    private void m545a(BDLocation bDLocation, int i) {
        if (!this.f491e || this.f493g == null) {
            bDLocation.setIndoorNetworkState(this.f486O);
            bDLocation.setUserIndoorState(1);
            C0332a.m176a().m181a(bDLocation);
            return;
        }
        bDLocation.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(new Date(System.currentTimeMillis())));
        if (bDLocation.getNetworkLocationType().contains("2")) {
            String networkLocationType = bDLocation.getNetworkLocationType();
            bDLocation.setNetworkLocationType(networkLocationType.substring(0, networkLocationType.length() - 1));
            this.f493g.onReceiveLocation(bDLocation);
            return;
        }
        BDLocation bDLocation2 = new BDLocation(bDLocation);
        Message obtainMessage = this.f489c.obtainMessage(801);
        obtainMessage.obj = bDLocation2;
        obtainMessage.sendToTarget();
    }

    private double[] m550a(double d, double d2, double d3, double d4) {
        double[] dArr = new double[2];
        double toRadians = Math.toRadians(d);
        double toRadians2 = Math.toRadians(d2);
        double toRadians3 = Math.toRadians(d4);
        double asin = Math.asin((Math.sin(toRadians) * Math.cos(d3 / 6378137.0d)) + ((Math.cos(toRadians) * Math.sin(d3 / 6378137.0d)) * Math.cos(toRadians3)));
        toRadians = Math.atan2((Math.sin(toRadians3) * Math.sin(d3 / 6378137.0d)) * Math.cos(toRadians), Math.cos(d3 / 6378137.0d) - (Math.sin(toRadians) * Math.sin(asin))) + toRadians2;
        dArr[0] = Math.toDegrees(asin);
        dArr[1] = Math.toDegrees(toRadians);
        return dArr;
    }

    private void m554b(Message message) {
        BDLocation bDLocation = (BDLocation) message.obj;
        if (this.f480I < 0.1d || this.f481J < 0.1d) {
            this.f481J = bDLocation.getLatitude();
            this.f480I = bDLocation.getLongitude();
        }
        this.f477F.add(bDLocation.getFloor());
        this.f509x = m572m();
        bDLocation.setFloor(this.f509x);
        double longitude = ((this.f480I * ((double) 1000000)) * this.f482K) + ((WeightedLatLng.DEFAULT_INTENSITY - this.f482K) * (bDLocation.getLongitude() * ((double) 1000000)));
        bDLocation.setLatitude((((this.f481J * ((double) 1000000)) * this.f482K) + ((WeightedLatLng.DEFAULT_INTENSITY - this.f482K) * (bDLocation.getLatitude() * ((double) 1000000)))) / ((double) 1000000));
        bDLocation.setLongitude(longitude / ((double) 1000000));
        bDLocation.setTime(this.f490d.format(new Date()));
        this.f481J = bDLocation.getLatitude();
        this.f480I = bDLocation.getLongitude();
        if (!C0448d.m886a().m925i()) {
            m545a(bDLocation, 21);
        }
    }

    private boolean m567j() {
        return false;
    }

    private void m569k() {
        this.f477F.clear();
        this.f479H.clear();
        this.f503r = 0;
        this.f504s = 0;
        this.f474C = 0;
        this.f508w = 0;
        this.f509x = null;
        this.f485N = false;
        this.f510y = null;
        this.f511z = null;
        this.f472A = null;
        this.f473B = null;
        this.f475D = true;
        this.f482K = 0.5d;
        this.f480I = 0.0d;
        this.f481J = 0.0d;
        this.f507v = 0;
        this.f505t = 0;
        this.f483L = false;
    }

    private void m571l() {
        if (this.f501p) {
            this.f496k = true;
            this.f499n.mo1747b();
            this.f500o = System.currentTimeMillis();
        }
    }

    private String m572m() {
        Map hashMap = new HashMap();
        int size = this.f477F.size();
        String str = null;
        int i = -1;
        int i2 = 0;
        String str2 = "";
        while (i2 < size) {
            String str3;
            try {
                str3 = (String) this.f477F.get(i2);
                str2 = str2 + str3 + "|";
                if (hashMap.containsKey(str3)) {
                    hashMap.put(str3, Integer.valueOf(((Integer) hashMap.get(str3)).intValue() + 1));
                } else {
                    hashMap.put(str3, Integer.valueOf(1));
                }
                i2++;
            } catch (Exception e) {
                return this.f509x;
            }
        }
        for (String str4 : hashMap.keySet()) {
            int intValue;
            if (((Integer) hashMap.get(str4)).intValue() > i) {
                str3 = str4;
                intValue = ((Integer) hashMap.get(str4)).intValue();
            } else {
                intValue = i;
                str3 = str;
            }
            i = intValue;
            str = str3;
        }
        return (size != this.f476E || this.f509x.equals(str)) ? str == null ? this.f509x : (size < 3 || size > this.f476E || !((String) this.f477F.get(size - 3)).equals(this.f477F.get(size - 1)) || !((String) this.f477F.get(size - 2)).equals(this.f477F.get(size - 1)) || ((String) this.f477F.get(size - 1)).equals(str)) ? str : (String) this.f477F.get(size - 1) : (((String) this.f477F.get(size + -3)).equals(str) && ((String) this.f477F.get(size - 2)).equals(str) && ((String) this.f477F.get(size - 1)).equals(str)) ? str : this.f509x;
    }

    public synchronized void m582b() {
        if (this.f501p) {
            this.f477F.clear();
        }
    }

    public synchronized void m583c() {
        if (!this.f501p) {
            this.f503r = System.currentTimeMillis();
            this.f497l.m619a();
            this.f498m = new C0395e(this);
            this.f498m.start();
            this.f502q = false;
            this.f501p = true;
            if (m567j()) {
                this.f491e = true;
            }
        }
    }

    public synchronized void m584d() {
        if (this.f501p) {
            this.f497l.m620b();
            if (this.f498m != null) {
                this.f498m.f459b = false;
                this.f498m.interrupt();
                this.f498m = null;
            }
            m569k();
            this.f502q = false;
            this.f501p = false;
            C0332a.m176a().m185c();
        }
    }

    public synchronized void m585e() {
    }

    public boolean m586f() {
        return this.f501p;
    }

    public boolean m587g() {
        return this.f501p && this.f502q;
    }

    public String m588h() {
        return this.f509x;
    }

    public String m589i() {
        return this.f510y;
    }
}
