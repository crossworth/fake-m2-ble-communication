package com.baidu.location;

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
import android.os.Message;
import com.amap.api.maps.model.GroundOverlayOptions;
import com.tencent.open.yyb.TitleBar;
import com.zhuoyou.plugin.download.Util_update.TimeManager;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

class C1984s implements an, C1619j {
    private static final int eA = 1;
    private static final int eE = 3000;
    private static final int eF = 3;
    private static final int eG = 5;
    private static String eI = "Temp_in.dat";
    private static String eL = null;
    private static C1984s eM = null;
    private static final double eR = 1.0E-5d;
    private static int eU = 0;
    private static File ez = new File(C1976f.L, eI);
    private final int eB = 4;
    private boolean eC = false;
    private final int eD = 3;
    private GpsStatus eH;
    private C0535a eJ = null;
    private long eK = 0;
    private final int eN = 1;
    private long eO = 0;
    private C0537c eP = null;
    private Handler eQ = null;
    private Context eS;
    private boolean eT = false;
    private C0536b eV = null;
    private Location eW;
    private final long eX = 1000;
    private LocationManager eY = null;
    private long eZ = 0;
    private boolean ev = false;
    private final int ew = 2;
    private long ex = 0;
    private String ey = null;

    class C05341 extends Handler {
        final /* synthetic */ C1984s f2279a;

        C05341(C1984s c1984s) {
            this.f2279a = c1984s;
        }

        public void handleMessage(Message message) {
            if (C1976f.isServing) {
                switch (message.what) {
                    case 1:
                        this.f2279a.m6048for((Location) message.obj);
                        return;
                    case 2:
                        if (this.f2279a.eJ != null) {
                            this.f2279a.eJ.m2210a((String) message.obj);
                            return;
                        }
                        return;
                    case 3:
                        this.f2279a.m6058if("&og=1", (Location) message.obj);
                        return;
                    case 4:
                        this.f2279a.m6058if("&og=2", (Location) message.obj);
                        return;
                    default:
                        return;
                }
            }
        }
    }

    private class C0535a implements Listener, NmeaListener {
        private long f2280a;
        private boolean f2281byte;
        private List f2282case;
        private final int f2283char;
        private String f2284do;
        private String f2285for;
        long f2286if;
        final /* synthetic */ C1984s f2287int;
        private String f2288new;
        private boolean f2289try;

        private C0535a(C1984s c1984s) {
            this.f2287int = c1984s;
            this.f2286if = 0;
            this.f2280a = 0;
            this.f2283char = 400;
            this.f2289try = true;
            this.f2281byte = false;
            this.f2282case = new ArrayList();
            this.f2285for = null;
            this.f2288new = null;
            this.f2284do = null;
        }

        public void m2210a(String str) {
            if (System.currentTimeMillis() - this.f2280a > 400 && this.f2281byte && this.f2282case.size() > 0) {
                try {
                    ak akVar = new ak(this.f2282case, this.f2285for, this.f2288new, this.f2284do);
                    if (akVar.bj()) {
                        C1974b.ah = akVar.bi();
                        if (C1974b.ah > 0) {
                            C1984s.eL = String.format(Locale.CHINA, "&nmea=%.1f|%.1f&g_tp=%d", new Object[]{Double.valueOf(akVar.bk()), Double.valueOf(akVar.bo()), Integer.valueOf(C1974b.ah)});
                        }
                    } else {
                        C1974b.ah = 0;
                    }
                } catch (Exception e) {
                    C1974b.ah = 0;
                }
                this.f2282case.clear();
                this.f2284do = null;
                this.f2288new = null;
                this.f2285for = null;
                this.f2281byte = false;
            }
            if (str.startsWith("$GPGGA")) {
                this.f2281byte = true;
                this.f2285for = str.trim();
            } else if (str.startsWith("$GPGSV")) {
                this.f2282case.add(str.trim());
            } else if (str.startsWith("$GPGSA")) {
                this.f2284do = str.trim();
            }
            this.f2280a = System.currentTimeMillis();
        }

        public void onGpsStatusChanged(int i) {
            if (this.f2287int.eY != null) {
                switch (i) {
                    case 2:
                        this.f2287int.m6061int(null);
                        this.f2287int.m6049for(false);
                        C1984s.eU = 0;
                        return;
                    case 4:
                        if (this.f2287int.eC || System.currentTimeMillis() - this.f2286if >= 10000) {
                            if (this.f2287int.eH == null) {
                                this.f2287int.eH = this.f2287int.eY.getGpsStatus(null);
                            } else {
                                this.f2287int.eY.getGpsStatus(this.f2287int.eH);
                            }
                            int i2 = 0;
                            int i3 = 0;
                            for (GpsSatellite usedInFix : this.f2287int.eH.getSatellites()) {
                                i2++;
                                i3 = usedInFix.usedInFix() ? i3 + 1 : i3;
                            }
                            C1984s.eU = i3;
                            if (!this.f2287int.eC && System.currentTimeMillis() - this.f2287int.eZ >= TimeManager.UNIT_MINUTE) {
                                if (i3 > 3 || i2 > 15) {
                                    Location lastKnownLocation = this.f2287int.eY.getLastKnownLocation("gps");
                                    if (lastKnownLocation != null) {
                                        this.f2286if = System.currentTimeMillis();
                                        long currentTimeMillis = (System.currentTimeMillis() + C1975c.m5927char().bf) - lastKnownLocation.getTime();
                                        if (currentTimeMillis < 3500 && currentTimeMillis > -200 && C1979l.m5991if(lastKnownLocation, false)) {
                                            this.f2287int.eQ.sendMessage(this.f2287int.eQ.obtainMessage(3, lastKnownLocation));
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
                        return;
                    default:
                        return;
                }
            }
        }

        public void onNmeaReceived(long j, String str) {
            if (!this.f2287int.eC) {
                return;
            }
            if (!C1985t.e5) {
                C1974b.ah = 0;
            } else if (str != null && !str.equals("") && str.length() >= 9 && str.length() <= 150 && this.f2287int.ay()) {
                this.f2287int.eQ.sendMessage(this.f2287int.eQ.obtainMessage(2, str));
            }
        }
    }

    private class C0536b implements LocationListener {
        final /* synthetic */ C1984s f2290a;

        private C0536b(C1984s c1984s) {
            this.f2290a = c1984s;
        }

        public void onLocationChanged(Location location) {
            this.f2290a.eO = System.currentTimeMillis();
            this.f2290a.m6049for(true);
            this.f2290a.m6061int(location);
            this.f2290a.ev = false;
        }

        public void onProviderDisabled(String str) {
            this.f2290a.m6061int(null);
            this.f2290a.m6049for(false);
        }

        public void onProviderEnabled(String str) {
        }

        public void onStatusChanged(String str, int i, Bundle bundle) {
            switch (i) {
                case 0:
                    this.f2290a.m6061int(null);
                    this.f2290a.m6049for(false);
                    return;
                case 1:
                    this.f2290a.ex = System.currentTimeMillis();
                    this.f2290a.ev = true;
                    this.f2290a.m6049for(false);
                    return;
                case 2:
                    this.f2290a.ev = false;
                    return;
                default:
                    return;
            }
        }
    }

    private class C0537c implements LocationListener {
        final /* synthetic */ C1984s f2291a;
        private long f2292if;

        private C0537c(C1984s c1984s) {
            this.f2291a = c1984s;
            this.f2292if = 0;
        }

        public void onLocationChanged(Location location) {
            if (!this.f2291a.eC && location != null && location.getProvider() == "gps") {
                this.f2291a.eZ = System.currentTimeMillis();
                if (System.currentTimeMillis() - this.f2292if >= 10000 && C1979l.m5991if(location, false)) {
                    this.f2292if = System.currentTimeMillis();
                    this.f2291a.eQ.sendMessage(this.f2291a.eQ.obtainMessage(4, location));
                }
            }
        }

        public void onProviderDisabled(String str) {
        }

        public void onProviderEnabled(String str) {
        }

        public void onStatusChanged(String str, int i, Bundle bundle) {
        }
    }

    private C1984s() {
    }

    public static C1984s aH() {
        if (eM == null) {
            eM = new C1984s();
        }
        return eM;
    }

    private boolean ax() {
        return false;
    }

    public static String m6040byte(Location location) {
        if (location == null) {
            return null;
        }
        float speed = (float) (((double) location.getSpeed()) * 3.6d);
        int accuracy = (int) (location.hasAccuracy() ? location.getAccuracy() : GroundOverlayOptions.NO_DIMENSION);
        double altitude = location.hasAltitude() ? location.getAltitude() : 555.0d;
        return String.format(Locale.CHINA, "&ll=%.5f|%.5f&s=%.1f&d=%.1f&ll_r=%d&ll_n=%d&ll_h=%.2f&ll_t=%d", new Object[]{Double.valueOf(location.getLongitude()), Double.valueOf(location.getLatitude()), Float.valueOf(speed), Float.valueOf(location.getBearing()), Integer.valueOf(accuracy), Integer.valueOf(eU), Double.valueOf(altitude), Long.valueOf(location.getTime() / 1000)});
    }

    private void m6048for(Location location) {
        this.eW = location;
        if (this.eW == null) {
            this.ey = null;
        } else {
            this.eW.setTime(System.currentTimeMillis());
            float speed = (float) (((double) this.eW.getSpeed()) * 3.6d);
            int i = eU;
            if (i == 0) {
                try {
                    i = this.eW.getExtras().getInt("satellites");
                } catch (Exception e) {
                }
            }
            this.ey = String.format(Locale.CHINA, "&ll=%.5f|%.5f&s=%.1f&d=%.1f&ll_n=%d&ll_t=%d", new Object[]{Double.valueOf(this.eW.getLongitude()), Double.valueOf(this.eW.getLatitude()), Float.valueOf(speed), Float.valueOf(this.eW.getBearing()), Integer.valueOf(i), Long.valueOf(r2)});
            m6054if(this.eW.getLongitude(), this.eW.getLatitude(), speed);
        }
        try {
            C1983r.ar().m6039do(this.eW);
        } catch (Exception e2) {
        }
        if (ay()) {
            C1977g.m5942g().m5957try(aw());
            if (eU > 2 && C1979l.m5991if(this.eW, true)) {
                ai.bb().a4();
                C1979l.m5985if(C1981n.m6008K().m6018H(), ai.bb().a7(), this.eW, C1977g.m5942g().m5949f());
            }
        }
    }

    private void m6049for(boolean z) {
        this.eT = z;
        if (!z || !ay()) {
        }
    }

    private void m6054if(double d, double d2, float f) {
        int i = 0;
        if (C1985t.e1) {
            if (d >= 73.146973d && d <= 135.252686d && d2 <= 54.258807d && d2 >= 14.604847d && f <= 18.0f) {
                int i2 = (int) ((d - C1974b.aB) * 1000.0d);
                int i3 = (int) ((C1974b.aa - d2) * 1000.0d);
                if (i2 <= 0 || i2 >= 50 || i3 <= 0 || i3 >= 50) {
                    String str = String.format(Locale.CHINA, "&ll=%.5f|%.5f", new Object[]{Double.valueOf(d), Double.valueOf(d2)}) + "&im=" + ap.bD().bC();
                    C1974b.a0 = d;
                    C1974b.aj = d2;
                    C1985t.aK().m6073h(str);
                } else {
                    i2 += i3 * 50;
                    i3 = i2 >> 2;
                    i2 &= 3;
                    if (C1974b.am) {
                        i = (C1974b.aw[i3] >> (i2 * 2)) & 3;
                    }
                }
            }
            if (C1974b.ag != i) {
                C1974b.ag = i;
            }
        }
    }

    private void m6058if(String str, Location location) {
        C1979l.m5985if(C1981n.m6008K().m6018H(), ai.bb().a7(), location, str + C1977g.m5942g().m5949f());
    }

    public static boolean m6059if(Location location, Location location2, boolean z) {
        if (location == location2) {
            return false;
        }
        if (location == null || location2 == null) {
            return true;
        }
        float speed = location2.getSpeed();
        if (z && C1974b.ag == 3 && speed < 5.0f) {
            return true;
        }
        float distanceTo = location2.distanceTo(location);
        return speed > C1974b.a2 ? distanceTo > C1974b.aS : speed > C1974b.a5 ? distanceTo > C1974b.ak : distanceTo > 5.0f;
    }

    private void m6061int(Location location) {
        this.eQ.sendMessage(this.eQ.obtainMessage(1, location));
    }

    public static String m6062new(Location location) {
        String str = C1984s.m6040byte(location);
        return str != null ? str + eL : str;
    }

    public static String m6065try(Location location) {
        String str = C1984s.m6040byte(location);
        return str != null ? str + "&g_tp=0" : str;
    }

    public boolean aA() {
        return (this.eW == null || this.eW.getLatitude() == 0.0d || this.eW.getLongitude() == 0.0d) ? false : true;
    }

    public void aB() {
        this.eS = C1976f.getServiceContext();
        try {
            this.eY = (LocationManager) this.eS.getSystemService("location");
            this.eJ = new C0535a();
            this.eY.addGpsStatusListener(this.eJ);
            this.eP = new C0537c();
            this.eY.requestLocationUpdates("passive", 1000, 0.0f, this.eP);
        } catch (Exception e) {
        }
        this.eQ = new C05341(this);
    }

    public String aC() {
        return this.ey;
    }

    public void aD() {
        aG();
        try {
            if (this.eJ != null) {
                this.eY.removeGpsStatusListener(this.eJ);
            }
            this.eY.removeUpdates(this.eP);
        } catch (Exception e) {
        }
        this.eJ = null;
        this.eY = null;
    }

    public String aE() {
        return (!ay() || this.eW == null) ? null : C1984s.m6040byte(this.eW);
    }

    public void aF() {
        if (!this.eC) {
            try {
                this.eV = new C0536b();
                this.eY.requestLocationUpdates("gps", 1000, 0.0f, this.eV);
                this.eY.addNmeaListener(this.eJ);
                this.eC = true;
            } catch (Exception e) {
            }
        }
    }

    public void aG() {
        if (this.eC) {
            if (this.eY != null) {
                try {
                    if (this.eV != null) {
                        this.eY.removeUpdates(this.eV);
                    }
                    if (this.eJ != null) {
                        this.eY.removeNmeaListener(this.eJ);
                    }
                } catch (Exception e) {
                }
            }
            C1974b.ah = 0;
            C1974b.ag = 0;
            this.eV = null;
            this.eC = false;
            m6049for(false);
        }
    }

    public String aw() {
        if (this.eW == null) {
            return null;
        }
        String str = "{\"result\":{\"time\":\"" + C1974b.m5918if() + "\",\"error\":\"61\"},\"content\":{\"point\":{\"x\":" + "\"%f\",\"y\":\"%f\"},\"radius\":\"%d\",\"d\":\"%f\"," + "\"s\":\"%f\",\"n\":\"%d\"}}";
        int accuracy = (int) (this.eW.hasAccuracy() ? this.eW.getAccuracy() : TitleBar.SHAREBTN_RIGHT_MARGIN);
        float speed = (float) (((double) this.eW.getSpeed()) * 3.6d);
        double[] dArr = Jni.m5812if(this.eW.getLongitude(), this.eW.getLatitude(), "gps2gcj");
        if (dArr[0] <= 0.0d && dArr[1] <= 0.0d) {
            dArr[0] = this.eW.getLongitude();
            dArr[1] = this.eW.getLatitude();
        }
        return String.format(Locale.CHINA, str, new Object[]{Double.valueOf(dArr[0]), Double.valueOf(dArr[1]), Integer.valueOf(accuracy), Float.valueOf(this.eW.getBearing()), Float.valueOf(speed), Integer.valueOf(eU)});
    }

    public boolean ay() {
        if (!aA() || System.currentTimeMillis() - this.eO > 10000) {
            return false;
        }
        return (!this.ev || System.currentTimeMillis() - this.ex >= 3000) ? this.eT : true;
    }

    public Location az() {
        return this.eW;
    }

    public void m6066do(boolean z) {
        if (z) {
            aF();
        } else {
            aG();
        }
    }
}
