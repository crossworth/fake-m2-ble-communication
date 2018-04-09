package com.baidu.location.p012f;

import android.content.Context;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.GpsStatus.Listener;
import android.location.GpsStatus.NmeaListener;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import com.baidu.location.BDLocation;
import com.baidu.location.C0455f;
import com.baidu.location.Jni;
import com.baidu.location.p005a.C0332a;
import com.baidu.location.p005a.C0340c;
import com.baidu.location.p005a.C0343f;
import com.baidu.location.p005a.C0359q;
import com.baidu.location.p005a.C0362s;
import com.baidu.location.p006h.C0459b;
import com.baidu.location.p006h.C0463d;
import com.baidu.location.p006h.C0468j;
import com.baidu.location.p007b.C0372e;
import com.tencent.open.yyb.TitleBar;
import com.umeng.socialize.common.SocializeConstants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import twitter4j.HttpResponseCode;

public class C0448d {
    private static C0448d f768c = null;
    private static int f769k = 0;
    private static String f770r = null;
    private final long f771a = 1000;
    private final long f772b = 9000;
    private Context f773d;
    private LocationManager f774e = null;
    private Location f775f;
    private C0446b f776g = null;
    private C0447c f777h = null;
    private GpsStatus f778i;
    private C0445a f779j = null;
    private long f780l = 0;
    private boolean f781m = false;
    private boolean f782n = false;
    private String f783o = null;
    private boolean f784p = false;
    private long f785q = 0;
    private Handler f786s = null;
    private final int f787t = 1;
    private final int f788u = 2;
    private final int f789v = 3;
    private final int f790w = 4;
    private int f791x;
    private int f792y;
    private HashMap<Integer, List<GpsSatellite>> f793z;

    private class C0445a implements Listener, NmeaListener {
        long f755a;
        final /* synthetic */ C0448d f756b;
        private long f757c;
        private final int f758d;
        private boolean f759e;
        private List<String> f760f;
        private String f761g;
        private String f762h;
        private String f763i;
        private long f764j;

        private C0445a(C0448d c0448d) {
            this.f756b = c0448d;
            this.f755a = 0;
            this.f757c = 0;
            this.f758d = HttpResponseCode.BAD_REQUEST;
            this.f759e = false;
            this.f760f = new ArrayList();
            this.f761g = null;
            this.f762h = null;
            this.f763i = null;
            this.f764j = 0;
        }

        public void m877a(String str) {
            if (System.currentTimeMillis() - this.f757c > 400 && this.f759e && this.f760f.size() > 0) {
                try {
                    C0450f c0450f = new C0450f(this.f760f, this.f761g, this.f762h, this.f763i);
                    if (c0450f.m928a()) {
                        C0468j.f902d = this.f756b.m881a(c0450f, this.f756b.f792y);
                        if (C0468j.f902d > 0) {
                            C0448d.f770r = String.format(Locale.CHINA, "&nmea=%.1f|%.1f&g_tp=%d", new Object[]{Double.valueOf(c0450f.m930c()), Double.valueOf(c0450f.m929b()), Integer.valueOf(C0468j.f902d)});
                        }
                    } else {
                        C0468j.f902d = 0;
                    }
                } catch (Exception e) {
                    C0468j.f902d = 0;
                }
                this.f760f.clear();
                this.f763i = null;
                this.f762h = null;
                this.f761g = null;
                this.f759e = false;
            }
            if (str.startsWith("$GPGGA")) {
                this.f759e = true;
                this.f761g = str.trim();
            } else if (str.startsWith("$GPGSV")) {
                this.f760f.add(str.trim());
            } else if (str.startsWith("$GPGSA")) {
                this.f763i = str.trim();
            }
            this.f757c = System.currentTimeMillis();
        }

        public void onGpsStatusChanged(int i) {
            if (this.f756b.f774e != null) {
                switch (i) {
                    case 2:
                        this.f756b.m911d(null);
                        this.f756b.m904b(false);
                        C0448d.f769k = 0;
                        return;
                    case 4:
                        if (this.f756b.f782n) {
                            try {
                                if (this.f756b.f778i == null) {
                                    this.f756b.f778i = this.f756b.f774e.getGpsStatus(null);
                                } else {
                                    this.f756b.f774e.getGpsStatus(this.f756b.f778i);
                                }
                                this.f756b.f791x = 0;
                                this.f756b.f792y = 0;
                                this.f756b.f793z = new HashMap();
                                int i2 = 0;
                                for (GpsSatellite gpsSatellite : this.f756b.f778i.getSatellites()) {
                                    if (gpsSatellite.usedInFix()) {
                                        i2++;
                                        if (gpsSatellite.getSnr() >= ((float) C0468j.f877E)) {
                                            this.f756b.f792y = this.f756b.f792y + 1;
                                        }
                                        this.f756b.m887a(gpsSatellite, this.f756b.f793z);
                                    }
                                }
                                if (i2 > 0) {
                                    this.f764j = System.currentTimeMillis();
                                    C0448d.f769k = i2;
                                    return;
                                } else if (System.currentTimeMillis() - this.f764j > 100) {
                                    this.f764j = System.currentTimeMillis();
                                    C0448d.f769k = i2;
                                    return;
                                } else {
                                    return;
                                }
                            } catch (Exception e) {
                                return;
                            }
                        }
                        return;
                    default:
                        return;
                }
            }
        }

        public void onNmeaReceived(long j, String str) {
            if (!this.f756b.f782n) {
                return;
            }
            if (!C0372e.m404a().f375g) {
                C0468j.f902d = 0;
            } else if (str != null && !str.equals("") && str.length() >= 9 && str.length() <= 150 && this.f756b.m925i()) {
                this.f756b.f786s.sendMessage(this.f756b.f786s.obtainMessage(2, str));
            }
        }
    }

    private class C0446b implements LocationListener {
        final /* synthetic */ C0448d f765a;

        private C0446b(C0448d c0448d) {
            this.f765a = c0448d;
        }

        public void onLocationChanged(Location location) {
            this.f765a.f785q = System.currentTimeMillis();
            this.f765a.m904b(true);
            this.f765a.m911d(location);
            this.f765a.f781m = false;
        }

        public void onProviderDisabled(String str) {
            this.f765a.m911d(null);
            this.f765a.m904b(false);
        }

        public void onProviderEnabled(String str) {
        }

        public void onStatusChanged(String str, int i, Bundle bundle) {
            switch (i) {
                case 0:
                    this.f765a.m911d(null);
                    this.f765a.m904b(false);
                    return;
                case 1:
                    this.f765a.f780l = System.currentTimeMillis();
                    this.f765a.f781m = true;
                    this.f765a.m904b(false);
                    return;
                case 2:
                    this.f765a.f781m = false;
                    return;
                default:
                    return;
            }
        }
    }

    private class C0447c implements LocationListener {
        final /* synthetic */ C0448d f766a;
        private long f767b;

        private C0447c(C0448d c0448d) {
            this.f766a = c0448d;
            this.f767b = 0;
        }

        public void onLocationChanged(Location location) {
            if (!this.f766a.f782n && location != null && location.getProvider() == "gps" && System.currentTimeMillis() - this.f767b >= 10000 && C0362s.m358a(location, false)) {
                this.f767b = System.currentTimeMillis();
                this.f766a.f786s.sendMessage(this.f766a.f786s.obtainMessage(4, location));
            }
        }

        public void onProviderDisabled(String str) {
        }

        public void onProviderEnabled(String str) {
        }

        public void onStatusChanged(String str, int i, Bundle bundle) {
        }
    }

    private C0448d() {
    }

    private int m881a(C0450f c0450f, int i) {
        if (f769k >= C0468j.f874B) {
            return 1;
        }
        if (f769k <= C0468j.f873A) {
            return 4;
        }
        double c = c0450f.m930c();
        if (c <= ((double) C0468j.f921w)) {
            return 1;
        }
        if (c >= ((double) C0468j.f922x)) {
            return 4;
        }
        c = c0450f.m929b();
        return c > ((double) C0468j.f923y) ? c >= ((double) C0468j.f924z) ? 4 : i < C0468j.f876D ? i <= C0468j.f875C ? 4 : this.f793z != null ? m882a(this.f793z) : 3 : 1 : 1;
    }

    private int m882a(HashMap<Integer, List<GpsSatellite>> hashMap) {
        if (this.f791x > 4) {
            List arrayList = new ArrayList();
            List arrayList2 = new ArrayList();
            int i = 0;
            for (Entry value : hashMap.entrySet()) {
                int i2;
                List list = (List) value.getValue();
                if (list != null) {
                    Object a = m899a(list);
                    if (a != null) {
                        arrayList.add(a);
                        i2 = i + 1;
                        arrayList2.add(Integer.valueOf(i));
                        i = i2;
                    }
                }
                i2 = i;
                i = i2;
            }
            if (!arrayList.isEmpty()) {
                double[] dArr;
                double[] dArr2 = new double[2];
                int size = arrayList.size();
                for (int i3 = 0; i3 < size; i3++) {
                    dArr = (double[]) arrayList.get(i3);
                    i = ((Integer) arrayList2.get(i3)).intValue();
                    dArr[0] = dArr[0] * ((double) i);
                    dArr[1] = dArr[1] * ((double) i);
                    dArr2[0] = dArr2[0] + dArr[0];
                    dArr2[1] = dArr[1] + dArr2[1];
                }
                dArr2[0] = dArr2[0] / ((double) size);
                dArr2[1] = dArr2[1] / ((double) size);
                dArr = m907b(dArr2[0], dArr2[1]);
                if (dArr[0] <= ((double) C0468j.f878F)) {
                    return 1;
                }
                if (dArr[0] >= ((double) C0468j.f879G)) {
                    return 4;
                }
            }
        }
        return 3;
    }

    public static synchronized C0448d m886a() {
        C0448d c0448d;
        synchronized (C0448d.class) {
            if (f768c == null) {
                f768c = new C0448d();
            }
            c0448d = f768c;
        }
        return c0448d;
    }

    private String m887a(GpsSatellite gpsSatellite, HashMap<Integer, List<GpsSatellite>> hashMap) {
        int floor = (int) Math.floor((double) (gpsSatellite.getAzimuth() / 6.0f));
        float elevation = gpsSatellite.getElevation();
        int floor2 = (int) Math.floor(((double) elevation) / 1.5d);
        float snr = gpsSatellite.getSnr();
        int round = Math.round(snr / 5.0f);
        int prn = gpsSatellite.getPrn();
        int i = prn >= 65 ? prn - 32 : prn;
        if (snr >= TitleBar.SHAREBTN_RIGHT_MARGIN && elevation >= 1.0f) {
            List list = (List) hashMap.get(Integer.valueOf(round));
            if (list == null) {
                list = new ArrayList();
            }
            list.add(gpsSatellite);
            hashMap.put(Integer.valueOf(round), list);
            this.f791x++;
        }
        if (floor >= 64) {
            if (floor2 < 64) {
                if (i >= 65) {
                }
            }
            return null;
        }
        if (floor2 < 64) {
            if (i >= 65) {
            }
        }
        return null;
        if (i >= 65) {
        }
        return null;
    }

    public static String m888a(Location location) {
        float f = -1.0f;
        if (location == null) {
            return null;
        }
        float speed = (float) (((double) location.getSpeed()) * 3.6d);
        if (!location.hasSpeed()) {
            speed = -1.0f;
        }
        int accuracy = (int) (location.hasAccuracy() ? location.getAccuracy() : -1.0f);
        double altitude = location.hasAltitude() ? location.getAltitude() : 555.0d;
        if (location.hasBearing()) {
            f = location.getBearing();
        }
        return String.format(Locale.CHINA, "&ll=%.5f|%.5f&s=%.1f&d=%.1f&ll_r=%d&ll_n=%d&ll_h=%.2f&ll_t=%d", new Object[]{Double.valueOf(location.getLongitude()), Double.valueOf(location.getLatitude()), Float.valueOf(speed), Float.valueOf(f), Integer.valueOf(accuracy), Integer.valueOf(f769k), Double.valueOf(altitude), Long.valueOf(location.getTime() / 1000)});
    }

    private void m892a(double d, double d2, float f) {
        int i = 0;
        if (C0372e.m404a().f374f) {
            if (d >= 73.146973d && d <= 135.252686d && d2 <= 54.258807d && d2 >= 14.604847d && f <= 18.0f) {
                int i2 = (int) ((d - C0468j.f915q) * 1000.0d);
                int i3 = (int) ((C0468j.f916r - d2) * 1000.0d);
                if (i2 <= 0 || i2 >= 50 || i3 <= 0 || i3 >= 50) {
                    String str = String.format(Locale.CHINA, "&ll=%.5f|%.5f", new Object[]{Double.valueOf(d), Double.valueOf(d2)}) + "&im=" + C0459b.m980a().m985b();
                    C0468j.f913o = d;
                    C0468j.f914p = d2;
                    C0372e.m404a().m419a(str);
                } else {
                    i2 += i3 * 50;
                    i3 = i2 >> 2;
                    i2 &= 3;
                    if (C0468j.f919u) {
                        i = (C0468j.f918t[i3] >> (i2 * 2)) & 3;
                    }
                }
            }
            if (C0468j.f917s != i) {
                C0468j.f917s = i;
            }
        }
    }

    private void m896a(String str, Location location) {
        if (location != null) {
            String str2 = str + C0332a.m176a().m188d();
            boolean d = C0454h.m948a().m956d();
            C0359q.m340a(new C0441a(C0443b.m855a().m873f()));
            C0359q.m338a(System.currentTimeMillis());
            C0359q.m339a(new Location(location));
            C0359q.m341a(str2);
            if (!d) {
                C0362s.m353a(C0359q.m343c(), null, C0359q.m344d(), str2);
            }
        }
    }

    public static boolean m897a(Location location, Location location2, boolean z) {
        if (location == location2) {
            return false;
        }
        if (location == null || location2 == null) {
            return true;
        }
        float speed = location2.getSpeed();
        if (z && ((C0468j.f917s == 3 || !C0463d.m999a().m1001a(location2.getLongitude(), location2.getLatitude())) && speed < 5.0f)) {
            return true;
        }
        float distanceTo = location2.distanceTo(location);
        return speed > C0468j.f881I ? distanceTo > C0468j.f883K : speed > C0468j.f880H ? distanceTo > C0468j.f882J : distanceTo > 5.0f;
    }

    private double[] m898a(double d, double d2) {
        return new double[]{Math.sin(Math.toRadians(d2)) * d, Math.cos(Math.toRadians(d2)) * d};
    }

    private double[] m899a(List<GpsSatellite> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        double[] dArr = new double[2];
        for (GpsSatellite gpsSatellite : list) {
            if (gpsSatellite != null) {
                double[] a = m898a((double) (90.0f - gpsSatellite.getElevation()), (double) gpsSatellite.getAzimuth());
                dArr[0] = dArr[0] + a[0];
                dArr[1] = dArr[1] + a[1];
            }
        }
        int size = list.size();
        dArr[0] = dArr[0] / ((double) size);
        dArr[1] = dArr[1] / ((double) size);
        return dArr;
    }

    public static String m902b(Location location) {
        String a = C0448d.m888a(location);
        return a != null ? a + "&g_tp=0" : a;
    }

    private void m904b(boolean z) {
        this.f784p = z;
        if (!z || !m925i()) {
        }
    }

    private double[] m907b(double d, double d2) {
        double d3 = 0.0d;
        if (d2 != 0.0d) {
            d3 = Math.toDegrees(Math.atan(d / d2));
        } else if (d > 0.0d) {
            d3 = 90.0d;
        } else if (d < 0.0d) {
            d3 = 270.0d;
        }
        return new double[]{Math.sqrt((d * d) + (d2 * d2)), d3};
    }

    public static String m909c(Location location) {
        String a = C0448d.m888a(location);
        return a != null ? a + f770r : a;
    }

    private void m911d(Location location) {
        this.f786s.sendMessage(this.f786s.obtainMessage(1, location));
    }

    private void m913e(Location location) {
        if (location != null) {
            int i = f769k;
            if (i == 0) {
                try {
                    i = location.getExtras().getInt("satellites");
                } catch (Exception e) {
                }
            }
            if (i != 0 || C0468j.f909k) {
                this.f775f = location;
                if (this.f775f == null) {
                    this.f783o = null;
                } else {
                    this.f775f.setTime(System.currentTimeMillis());
                    float speed = (float) (((double) this.f775f.getSpeed()) * 3.6d);
                    if (!this.f775f.hasSpeed()) {
                        speed = -1.0f;
                    }
                    i = f769k;
                    if (i == 0) {
                        try {
                            i = this.f775f.getExtras().getInt("satellites");
                        } catch (Exception e2) {
                        }
                    }
                    this.f783o = String.format(Locale.CHINA, "&ll=%.5f|%.5f&s=%.1f&d=%.1f&ll_n=%d&ll_t=%d", new Object[]{Double.valueOf(this.f775f.getLongitude()), Double.valueOf(this.f775f.getLatitude()), Float.valueOf(speed), Float.valueOf(this.f775f.getBearing()), Integer.valueOf(i), Long.valueOf(r2)});
                    m892a(this.f775f.getLongitude(), this.f775f.getLatitude(), speed);
                }
                try {
                    C0343f.m251a().m259a(this.f775f);
                } catch (Exception e3) {
                }
                if (this.f775f != null) {
                    C0340c.m222a().m249a(this.f775f);
                }
                if (m925i() && this.f775f != null) {
                    C0332a.m176a().m182a(m922f());
                    if (f769k > 2 && C0362s.m358a(this.f775f, true)) {
                        boolean d = C0454h.m948a().m956d();
                        C0359q.m340a(new C0441a(C0443b.m855a().m873f()));
                        C0359q.m338a(System.currentTimeMillis());
                        C0359q.m339a(new Location(this.f775f));
                        C0359q.m341a(C0332a.m176a().m188d());
                        if (!d) {
                            C0362s.m353a(C0359q.m343c(), null, C0359q.m344d(), C0332a.m176a().m188d());
                            return;
                        }
                        return;
                    }
                    return;
                }
                return;
            }
            return;
        }
        this.f775f = null;
    }

    public void m917a(boolean z) {
        if (z) {
            m919c();
        } else {
            m920d();
        }
    }

    public synchronized void m918b() {
        if (C0455f.isServing) {
            this.f773d = C0455f.getServiceContext();
            try {
                this.f774e = (LocationManager) this.f773d.getSystemService(SocializeConstants.KEY_LOCATION);
                this.f779j = new C0445a();
                this.f774e.addGpsStatusListener(this.f779j);
                this.f777h = new C0447c();
                this.f774e.requestLocationUpdates("passive", 9000, 0.0f, this.f777h);
            } catch (Exception e) {
            }
            this.f786s = new C0449e(this);
        }
    }

    public void m919c() {
        if (!this.f782n) {
            try {
                this.f776g = new C0446b();
                try {
                    this.f774e.sendExtraCommand("gps", "force_xtra_injection", new Bundle());
                } catch (Exception e) {
                }
                this.f774e.requestLocationUpdates("gps", 1000, 0.0f, this.f776g);
                this.f774e.addNmeaListener(this.f779j);
                this.f782n = true;
            } catch (Exception e2) {
            }
        }
    }

    public void m920d() {
        if (this.f782n) {
            if (this.f774e != null) {
                try {
                    if (this.f776g != null) {
                        this.f774e.removeUpdates(this.f776g);
                    }
                    if (this.f779j != null) {
                        this.f774e.removeNmeaListener(this.f779j);
                    }
                } catch (Exception e) {
                }
            }
            C0468j.f902d = 0;
            C0468j.f917s = 0;
            this.f776g = null;
            this.f782n = false;
            m904b(false);
        }
    }

    public synchronized void m921e() {
        m920d();
        if (this.f774e != null) {
            try {
                if (this.f779j != null) {
                    this.f774e.removeGpsStatusListener(this.f779j);
                }
                this.f774e.removeUpdates(this.f777h);
            } catch (Exception e) {
            }
            this.f779j = null;
            this.f774e = null;
        }
    }

    public String m922f() {
        if (this.f775f == null) {
            return null;
        }
        double[] dArr;
        int i;
        String str = "{\"result\":{\"time\":\"" + C0468j.m1012a() + "\",\"error\":\"61\"},\"content\":{\"point\":{\"x\":" + "\"%f\",\"y\":\"%f\"},\"radius\":\"%d\",\"d\":\"%f\"," + "\"s\":\"%f\",\"n\":\"%d\"";
        int accuracy = (int) (this.f775f.hasAccuracy() ? this.f775f.getAccuracy() : TitleBar.SHAREBTN_RIGHT_MARGIN);
        float speed = (float) (((double) this.f775f.getSpeed()) * 3.6d);
        if (!this.f775f.hasSpeed()) {
            speed = -1.0f;
        }
        double[] dArr2 = new double[2];
        if (C0463d.m999a().m1001a(this.f775f.getLongitude(), this.f775f.getLatitude())) {
            dArr2 = Jni.coorEncrypt(this.f775f.getLongitude(), this.f775f.getLatitude(), BDLocation.BDLOCATION_WGS84_TO_GCJ02);
            if (dArr2[0] > 0.0d || dArr2[1] > 0.0d) {
                dArr = dArr2;
                i = 1;
            } else {
                dArr2[0] = this.f775f.getLongitude();
                dArr2[1] = this.f775f.getLatitude();
                dArr = dArr2;
                i = 1;
            }
        } else {
            dArr2[0] = this.f775f.getLongitude();
            dArr2[1] = this.f775f.getLatitude();
            dArr = dArr2;
            i = 0;
        }
        String format = String.format(Locale.CHINA, str, new Object[]{Double.valueOf(dArr[0]), Double.valueOf(dArr[1]), Integer.valueOf(accuracy), Float.valueOf(this.f775f.getBearing()), Float.valueOf(speed), Integer.valueOf(f769k)});
        if (i == 0) {
            format = format + ",\"in_cn\":\"0\"";
        }
        if (!this.f775f.hasAltitude()) {
            return format + "}}";
        }
        return format + String.format(Locale.CHINA, ",\"h\":%.2f}}", new Object[]{Double.valueOf(this.f775f.getAltitude())});
    }

    public Location m923g() {
        return (this.f775f != null && Math.abs(System.currentTimeMillis() - this.f775f.getTime()) <= 60000) ? this.f775f : null;
    }

    public boolean m924h() {
        try {
            return (this.f775f == null || this.f775f.getLatitude() == 0.0d || this.f775f.getLongitude() == 0.0d || (f769k <= 2 && this.f775f.getExtras().getInt("satellites", 3) <= 2)) ? false : true;
        } catch (Exception e) {
            return (this.f775f == null || this.f775f.getLatitude() == 0.0d || this.f775f.getLongitude() == 0.0d) ? false : true;
        }
    }

    public boolean m925i() {
        if (!m924h() || System.currentTimeMillis() - this.f785q > 10000) {
            return false;
        }
        return (!this.f781m || System.currentTimeMillis() - this.f780l >= 3000) ? this.f784p : true;
    }
}
