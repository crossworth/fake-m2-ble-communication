package com.baidu.location.p005a;

import android.location.Location;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Message;
import android.os.Messenger;
import com.baidu.location.Address;
import com.baidu.location.BDLocation;
import com.baidu.location.Jni;
import com.baidu.location.LocationClientOption;
import com.baidu.location.p006h.C0459b;
import com.baidu.location.p006h.C0468j;
import com.baidu.location.p007b.C0365a;
import com.baidu.location.p007b.C0376g;
import com.baidu.location.p008c.C0397b;
import com.baidu.location.p012f.C0448d;
import com.baidu.platform.comapi.location.CoordinateType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class C0332a {
    private static C0332a f116c = null;
    public boolean f117a;
    boolean f118b;
    private ArrayList<C0331a> f119d;
    private boolean f120e;
    private BDLocation f121f;
    private BDLocation f122g;

    private class C0331a {
        public String f111a = null;
        public Messenger f112b = null;
        public LocationClientOption f113c = new LocationClientOption();
        public int f114d = 0;
        final /* synthetic */ C0332a f115e;

        public C0331a(C0332a c0332a, Message message) {
            boolean z = true;
            this.f115e = c0332a;
            this.f112b = message.replyTo;
            this.f111a = message.getData().getString("packName");
            this.f113c.prodName = message.getData().getString("prodName");
            C0459b.m980a().m984a(this.f113c.prodName, this.f111a);
            this.f113c.coorType = message.getData().getString("coorType");
            this.f113c.addrType = message.getData().getString("addrType");
            this.f113c.enableSimulateGps = message.getData().getBoolean("enableSimulateGps", false);
            boolean z2 = C0468j.f909k || this.f113c.enableSimulateGps;
            C0468j.f909k = z2;
            if (!C0468j.f904f.equals("all")) {
                C0468j.f904f = this.f113c.addrType;
            }
            this.f113c.openGps = message.getData().getBoolean("openGPS");
            this.f113c.scanSpan = message.getData().getInt("scanSpan");
            this.f113c.timeOut = message.getData().getInt("timeOut");
            this.f113c.priority = message.getData().getInt("priority");
            this.f113c.location_change_notify = message.getData().getBoolean("location_change_notify");
            this.f113c.mIsNeedDeviceDirect = message.getData().getBoolean("needDirect", false);
            this.f113c.isNeedAltitude = message.getData().getBoolean("isneedaltitude", false);
            z2 = C0468j.f905g || message.getData().getBoolean("isneedaptag", false);
            C0468j.f905g = z2;
            if (!(C0468j.f906h || message.getData().getBoolean("isneedaptagd", false))) {
                z = false;
            }
            C0468j.f906h = z;
            C0468j.f887O = message.getData().getFloat("autoNotifyLocSensitivity", 0.5f);
            int i = message.getData().getInt("autoNotifyMaxInterval", 0);
            if (i >= C0468j.f892T) {
                C0468j.f892T = i;
            }
            i = message.getData().getInt("autoNotifyMinDistance", 0);
            if (i >= C0468j.f894V) {
                C0468j.f894V = i;
            }
            i = message.getData().getInt("autoNotifyMinTimeInterval", 0);
            if (i >= C0468j.f893U) {
                C0468j.f893U = i;
            }
            if (this.f113c.scanSpan >= 1000) {
                C0376g.m428a().m436b();
            }
            if (this.f113c.mIsNeedDeviceDirect || this.f113c.isNeedAltitude) {
                C0352j.m308a().m311a(this.f113c.mIsNeedDeviceDirect);
                C0352j.m308a().m313b(this.f113c.isNeedAltitude);
                C0352j.m308a().m312b();
            }
            c0332a.f118b |= this.f113c.isNeedAltitude;
        }

        private void m168a(int i) {
            Message obtain = Message.obtain(null, i);
            try {
                if (this.f112b != null) {
                    this.f112b.send(obtain);
                }
                this.f114d = 0;
            } catch (Exception e) {
                if (e instanceof DeadObjectException) {
                    this.f114d++;
                }
            }
        }

        private void m169a(int i, String str, BDLocation bDLocation) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(str, bDLocation);
            bundle.setClassLoader(BDLocation.class.getClassLoader());
            Message obtain = Message.obtain(null, i);
            obtain.setData(bundle);
            try {
                if (this.f112b != null) {
                    this.f112b.send(obtain);
                }
                this.f114d = 0;
            } catch (Exception e) {
                if (e instanceof DeadObjectException) {
                    this.f114d++;
                }
            }
        }

        public void m171a() {
            m168a(111);
        }

        public void m172a(BDLocation bDLocation) {
            m173a(bDLocation, 21);
        }

        public void m173a(BDLocation bDLocation, int i) {
            BDLocation bDLocation2 = new BDLocation(bDLocation);
            if (C0397b.m541a().m586f()) {
                bDLocation2.setIndoorLocMode(true);
            }
            if (C0352j.m308a().m318g() && (bDLocation2.getLocType() == 161 || bDLocation2.getLocType() == 66)) {
                bDLocation2.setAltitude(C0352j.m308a().m320i());
            }
            if (i == 21) {
                m169a(27, "locStr", bDLocation2);
            }
            if (!(this.f113c.coorType == null || this.f113c.coorType.equals(CoordinateType.GCJ02))) {
                double longitude = bDLocation2.getLongitude();
                double latitude = bDLocation2.getLatitude();
                if (!(longitude == Double.MIN_VALUE || latitude == Double.MIN_VALUE)) {
                    double[] coorEncrypt;
                    if ((bDLocation2.getCoorType() != null && bDLocation2.getCoorType().equals(CoordinateType.GCJ02)) || bDLocation2.getCoorType() == null) {
                        coorEncrypt = Jni.coorEncrypt(longitude, latitude, this.f113c.coorType);
                        bDLocation2.setLongitude(coorEncrypt[0]);
                        bDLocation2.setLatitude(coorEncrypt[1]);
                        bDLocation2.setCoorType(this.f113c.coorType);
                    } else if (!(bDLocation2.getCoorType() == null || !bDLocation2.getCoorType().equals(CoordinateType.WGS84) || this.f113c.coorType.equals("bd09ll"))) {
                        coorEncrypt = Jni.coorEncrypt(longitude, latitude, "wgs842mc");
                        bDLocation2.setLongitude(coorEncrypt[0]);
                        bDLocation2.setLatitude(coorEncrypt[1]);
                        bDLocation2.setCoorType("wgs84mc");
                    }
                }
            }
            m169a(i, "locStr", bDLocation2);
        }

        public void m174b() {
            if (!this.f113c.location_change_notify) {
                return;
            }
            if (C0468j.f900b) {
                m168a(54);
            } else {
                m168a(55);
            }
        }
    }

    private C0332a() {
        this.f119d = null;
        this.f120e = false;
        this.f117a = false;
        this.f118b = false;
        this.f121f = null;
        this.f122g = null;
        this.f119d = new ArrayList();
    }

    private C0331a m175a(Messenger messenger) {
        if (this.f119d == null) {
            return null;
        }
        Iterator it = this.f119d.iterator();
        while (it.hasNext()) {
            C0331a c0331a = (C0331a) it.next();
            if (c0331a.f112b.equals(messenger)) {
                return c0331a;
            }
        }
        return null;
    }

    public static C0332a m176a() {
        if (f116c == null) {
            f116c = new C0332a();
        }
        return f116c;
    }

    private void m177a(C0331a c0331a) {
        if (c0331a != null) {
            if (m175a(c0331a.f112b) != null) {
                c0331a.m168a(14);
                return;
            }
            this.f119d.add(c0331a);
            c0331a.m168a(13);
        }
    }

    private void m178f() {
        m179g();
        m190e();
    }

    private void m179g() {
        Iterator it = this.f119d.iterator();
        boolean z = false;
        boolean z2 = false;
        while (it.hasNext()) {
            C0331a c0331a = (C0331a) it.next();
            if (c0331a.f113c.openGps) {
                z2 = true;
            }
            z = c0331a.f113c.location_change_notify ? true : z;
        }
        C0468j.f899a = z;
        if (this.f120e != z2) {
            this.f120e = z2;
            C0448d.m886a().m917a(this.f120e);
        }
    }

    public void m180a(Message message) {
        if (message != null && message.replyTo != null) {
            this.f117a = true;
            m177a(new C0331a(this, message));
            m178f();
        }
    }

    public void m181a(BDLocation bDLocation) {
        boolean z = C0351i.f238h;
        if (z) {
            C0351i.f238h = false;
        }
        if (C0468j.f892T >= 10000 && (bDLocation.getLocType() == 61 || bDLocation.getLocType() == 161 || bDLocation.getLocType() == 66)) {
            if (this.f121f != null) {
                float[] fArr = new float[1];
                Location.distanceBetween(this.f121f.getLatitude(), this.f121f.getLongitude(), bDLocation.getLatitude(), bDLocation.getLongitude(), fArr);
                if (fArr[0] > ((float) C0468j.f894V) || z) {
                    this.f121f = null;
                    this.f121f = new BDLocation(bDLocation);
                } else {
                    return;
                }
            }
            this.f121f = new BDLocation(bDLocation);
        }
        Iterator it;
        if (C0347h.m268a().m271b()) {
            if (!bDLocation.hasAltitude() && this.f118b && (bDLocation.getLocType() == 161 || bDLocation.getLocType() == 66)) {
                double a = C0365a.m373a().m376a(bDLocation.getLongitude(), bDLocation.getLatitude());
                if (a != Double.MAX_VALUE) {
                    bDLocation.setAltitude(a);
                }
            }
            if (bDLocation.getLocType() == 61) {
                bDLocation.setGpsAccuracyStatus(C0365a.m373a().m377a(bDLocation));
            }
            it = this.f119d.iterator();
            while (it.hasNext()) {
                try {
                    C0331a c0331a;
                    c0331a = (C0331a) it.next();
                    c0331a.m172a(bDLocation);
                    if (c0331a.f114d > 4) {
                        it.remove();
                    }
                } catch (Exception e) {
                    return;
                }
            }
            return;
        }
        if (this.f122g == null) {
            this.f122g = new BDLocation();
            this.f122g.setLocType(505);
        }
        it = this.f119d.iterator();
        while (it.hasNext()) {
            try {
                c0331a = (C0331a) it.next();
                c0331a.m172a(this.f122g);
                if (c0331a.f114d > 4) {
                    it.remove();
                }
            } catch (Exception e2) {
                return;
            }
        }
    }

    public void m182a(String str) {
        BDLocation bDLocation = new BDLocation(str);
        Address a = C0351i.m280c().m293a(bDLocation);
        String f = C0351i.m280c().m302f();
        List g = C0351i.m280c().m303g();
        if (a != null) {
            bDLocation.setAddr(a);
        }
        if (f != null) {
            bDLocation.setLocationDescribe(f);
        }
        if (g != null) {
            bDLocation.setPoiList(g);
        }
        if (C0397b.m541a().m587g() && C0397b.m541a().m588h() != null) {
            bDLocation.setFloor(C0397b.m541a().m588h());
            bDLocation.setIndoorLocMode(true);
            if (C0397b.m541a().m589i() != null) {
                bDLocation.setBuildingID(C0397b.m541a().m589i());
            }
        }
        C0351i.m280c().m299c(bDLocation);
        m181a(bDLocation);
    }

    public void m183b() {
        this.f119d.clear();
        this.f121f = null;
        m178f();
    }

    public void m184b(Message message) {
        C0331a a = m175a(message.replyTo);
        if (a != null) {
            this.f119d.remove(a);
        }
        C0376g.m428a().m437c();
        C0352j.m308a().m314c();
        m178f();
    }

    public void m185c() {
        Iterator it = this.f119d.iterator();
        while (it.hasNext()) {
            ((C0331a) it.next()).m171a();
        }
    }

    public boolean m186c(Message message) {
        boolean z = true;
        C0331a a = m175a(message.replyTo);
        if (a == null) {
            return false;
        }
        int i = a.f113c.scanSpan;
        a.f113c.scanSpan = message.getData().getInt("scanSpan", a.f113c.scanSpan);
        if (a.f113c.scanSpan < 1000) {
            C0376g.m428a().m439e();
            C0352j.m308a().m314c();
            this.f117a = false;
        } else {
            C0376g.m428a().m438d();
            this.f117a = true;
        }
        if (a.f113c.scanSpan <= 999 || i >= 1000) {
            z = false;
        } else {
            if (a.f113c.mIsNeedDeviceDirect || a.f113c.isNeedAltitude) {
                C0352j.m308a().m311a(a.f113c.mIsNeedDeviceDirect);
                C0352j.m308a().m313b(a.f113c.isNeedAltitude);
                C0352j.m308a().m312b();
            }
            this.f118b |= a.f113c.isNeedAltitude;
        }
        a.f113c.openGps = message.getData().getBoolean("openGPS", a.f113c.openGps);
        String string = message.getData().getString("coorType");
        LocationClientOption locationClientOption = a.f113c;
        if (string == null || string.equals("")) {
            string = a.f113c.coorType;
        }
        locationClientOption.coorType = string;
        string = message.getData().getString("addrType");
        locationClientOption = a.f113c;
        if (string == null || string.equals("")) {
            string = a.f113c.addrType;
        }
        locationClientOption.addrType = string;
        if (!C0468j.f904f.equals(a.f113c.addrType)) {
            C0351i.m280c().m306j();
        }
        a.f113c.timeOut = message.getData().getInt("timeOut", a.f113c.timeOut);
        a.f113c.location_change_notify = message.getData().getBoolean("location_change_notify", a.f113c.location_change_notify);
        a.f113c.priority = message.getData().getInt("priority", a.f113c.priority);
        m178f();
        return z;
    }

    public int m187d(Message message) {
        if (message == null || message.replyTo == null) {
            return 1;
        }
        C0331a a = m175a(message.replyTo);
        return (a == null || a.f113c == null) ? 1 : a.f113c.priority;
    }

    public String m188d() {
        StringBuffer stringBuffer = new StringBuffer(256);
        if (this.f119d.isEmpty()) {
            return "&prod=" + C0459b.f836e + ":" + C0459b.f835d;
        }
        C0331a c0331a = (C0331a) this.f119d.get(0);
        if (c0331a.f113c.prodName != null) {
            stringBuffer.append(c0331a.f113c.prodName);
        }
        if (c0331a.f111a != null) {
            stringBuffer.append(":");
            stringBuffer.append(c0331a.f111a);
            stringBuffer.append("|");
        }
        String stringBuffer2 = stringBuffer.toString();
        return (stringBuffer2 == null || stringBuffer2.equals("")) ? null : "&prod=" + stringBuffer2;
    }

    public int m189e(Message message) {
        if (message == null || message.replyTo == null) {
            return 1000;
        }
        C0331a a = m175a(message.replyTo);
        return (a == null || a.f113c == null) ? 1000 : a.f113c.scanSpan;
    }

    public void m190e() {
        Iterator it = this.f119d.iterator();
        while (it.hasNext()) {
            ((C0331a) it.next()).m174b();
        }
    }
}
