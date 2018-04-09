package com.baidu.location;

import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Message;
import android.os.Messenger;
import com.autonavi.amap.mapcore.MapCore;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class C1977g implements an, C1619j {
    private static C1977g bl = null;
    private boolean bj;
    private ArrayList bk;
    private boolean bm;

    private class C0523a {
        final /* synthetic */ C1977g f2258a;
        public LocationClientOption f2259do = new LocationClientOption();
        public Messenger f2260for = null;
        public int f2261if = 0;
        public String f2262int = null;

        public C0523a(C1977g c1977g, Message message) {
            this.f2258a = c1977g;
            this.f2260for = message.replyTo;
            this.f2262int = message.getData().getString("packName");
            this.f2259do.f2116new = message.getData().getString("prodName");
            ap.bD().m5890int(this.f2259do.f2116new, this.f2262int);
            this.f2259do.f2117try = message.getData().getString("coorType");
            this.f2259do.f2108char = message.getData().getString("addrType");
            C1974b.ar = this.f2259do.f2108char;
            this.f2259do.f2107case = message.getData().getBoolean("openGPS");
            this.f2259do.f2114int = message.getData().getInt("scanSpan");
            this.f2259do.f2115long = message.getData().getInt("timeOut");
            this.f2259do.f2112goto = message.getData().getInt("priority");
            this.f2259do.f2118void = message.getData().getBoolean("location_change_notify");
        }

        private void m2187a(int i) {
            Message obtain = Message.obtain(null, i);
            try {
                if (this.f2260for != null) {
                    this.f2260for.send(obtain);
                }
                this.f2261if = 0;
            } catch (Exception e) {
                if (e instanceof DeadObjectException) {
                    this.f2261if++;
                }
            }
        }

        private void m2188a(int i, String str, BDLocation bDLocation) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(str, bDLocation);
            Message obtain = Message.obtain(null, i);
            obtain.setData(bundle);
            try {
                if (this.f2260for != null) {
                    this.f2260for.send(obtain);
                }
                this.f2261if = 0;
            } catch (Exception e) {
                if (e instanceof DeadObjectException) {
                    this.f2261if++;
                }
                e.printStackTrace();
            }
        }

        public void m2190a() {
            m2187a(23);
        }

        public void m2191a(BDLocation bDLocation) {
            m2192a(bDLocation, 21);
        }

        public void m2192a(BDLocation bDLocation, int i) {
            int i2 = 0;
            BDLocation bDLocation2 = new BDLocation(bDLocation);
            if (bDLocation2 != null) {
                if (i == 21) {
                    m2188a(27, "locStr", bDLocation2);
                }
                if (!(this.f2259do.f2117try == null || this.f2259do.f2117try.equals(BDGeofence.COORD_TYPE_GCJ))) {
                    double longitude = bDLocation2.getLongitude();
                    double latitude = bDLocation2.getLatitude();
                    if (!(longitude == Double.MIN_VALUE || latitude == Double.MIN_VALUE)) {
                        double[] dArr = Jni.m5812if(longitude, latitude, this.f2259do.f2117try);
                        bDLocation2.setLongitude(dArr[0]);
                        bDLocation2.setLatitude(dArr[1]);
                    }
                    if (this.f2259do.f2110else && i == 26) {
                        try {
                            if (bDLocation2.getLocType() == BDLocation.TypeNetWorkLocation && bDLocation2.hasPoi()) {
                                JSONObject jSONObject = new JSONObject(bDLocation2.getPoi());
                                JSONArray jSONArray = jSONObject.getJSONArray("p");
                                while (i2 < jSONArray.length()) {
                                    JSONObject jSONObject2 = jSONArray.getJSONObject(i2);
                                    double parseDouble = Double.parseDouble(jSONObject2.getString("x"));
                                    double parseDouble2 = Double.parseDouble(jSONObject2.getString("y"));
                                    if (!(parseDouble == Double.MIN_VALUE || parseDouble2 == Double.MIN_VALUE)) {
                                        double[] dArr2 = Jni.m5812if(parseDouble, parseDouble2, this.f2259do.f2117try);
                                        jSONObject2.put("x", String.valueOf(dArr2[0]));
                                        jSONObject2.put("y", String.valueOf(dArr2[1]));
                                        jSONArray.put(i2, jSONObject2);
                                        i2++;
                                    }
                                }
                                jSONObject.put("p", jSONArray);
                                bDLocation2.setPoi(jSONObject.toString());
                            }
                        } catch (JSONException e) {
                        }
                    }
                }
                m2188a(i, "locStr", bDLocation2);
            }
        }

        public void m2193if() {
            if (!this.f2259do.f2118void) {
                return;
            }
            if (C1974b.ai) {
                m2187a(54);
            } else {
                m2187a(55);
            }
        }

        public void m2194if(BDLocation bDLocation) {
            if (this.f2259do.f2118void && !C1988x.aU().aW()) {
                m2191a(bDLocation);
                ab.m2123a().m2131a(null);
                ab.m2123a().m2134if(ab.m2123a().f2152new);
            }
        }
    }

    private C1977g() {
        this.bk = null;
        this.bm = false;
        this.bj = false;
        this.bk = new ArrayList();
    }

    private void m5941d() {
        Iterator it = this.bk.iterator();
        boolean z = false;
        boolean z2 = false;
        while (it.hasNext()) {
            C0523a c0523a = (C0523a) it.next();
            if (c0523a.f2259do.f2107case) {
                z2 = true;
            }
            z = c0523a.f2259do.f2118void ? true : z;
        }
        C1974b.aV = z;
        if (this.bm != z2) {
            this.bm = z2;
            C1984s.aH().m6066do(this.bm);
        }
    }

    public static C1977g m5942g() {
        if (bl == null) {
            bl = new C1977g();
        }
        return bl;
    }

    private C0523a m5943if(Messenger messenger) {
        if (this.bk == null) {
            return null;
        }
        Iterator it = this.bk.iterator();
        while (it.hasNext()) {
            C0523a c0523a = (C0523a) it.next();
            if (c0523a.f2260for.equals(messenger)) {
                return c0523a;
            }
        }
        return null;
    }

    private void m5944if(C0523a c0523a) {
        if (c0523a != null) {
            if (m5943if(c0523a.f2260for) != null) {
                c0523a.m2187a(14);
                return;
            }
            this.bk.add(c0523a);
            c0523a.m2187a(13);
        }
    }

    private void m5945long() {
        m5941d();
        m5948e();
    }

    public void m5946do(Message message) {
        C0523a c0523a = m5943if(message.replyTo);
        if (c0523a != null) {
            this.bk.remove(c0523a);
        }
        m5945long();
    }

    public void m5947do(BDLocation bDLocation) {
        ArrayList arrayList = new ArrayList();
        Iterator it = this.bk.iterator();
        while (it.hasNext()) {
            C0523a c0523a = (C0523a) it.next();
            c0523a.m2191a(bDLocation);
            if (c0523a.f2261if > 4) {
                arrayList.add(c0523a);
            }
        }
        if (arrayList != null && arrayList.size() > 0) {
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                this.bk.remove((C0523a) it2.next());
            }
        }
    }

    public void m5948e() {
        Iterator it = this.bk.iterator();
        while (it.hasNext()) {
            ((C0523a) it.next()).m2193if();
        }
    }

    public String m5949f() {
        StringBuffer stringBuffer = new StringBuffer(256);
        if (this.bk.isEmpty()) {
            return "&prod=" + ap.g7 + ":" + ap.hb;
        }
        C0523a c0523a = (C0523a) this.bk.get(0);
        if (c0523a.f2259do.f2116new != null) {
            stringBuffer.append(c0523a.f2259do.f2116new);
        }
        if (c0523a.f2262int != null) {
            stringBuffer.append(":");
            stringBuffer.append(c0523a.f2262int);
            stringBuffer.append("|");
        }
        String stringBuffer2 = stringBuffer.toString();
        return (stringBuffer2 == null || stringBuffer2.equals("")) ? null : "&prod=" + stringBuffer2;
    }

    public int m5950for(Message message) {
        if (message == null || message.replyTo == null) {
            return 1;
        }
        C0523a c0523a = m5943if(message.replyTo);
        return (c0523a == null || c0523a.f2259do == null) ? 1 : c0523a.f2259do.f2112goto;
    }

    public void m5951goto() {
        Iterator it = this.bk.iterator();
        while (it.hasNext()) {
            ((C0523a) it.next()).m2190a();
        }
    }

    public String m5952if(Message message) {
        if (message == null || message.replyTo == null) {
            return null;
        }
        C0523a c0523a = m5943if(message.replyTo);
        if (c0523a == null) {
            return null;
        }
        c0523a.f2259do.f2105a = message.getData().getInt("num", c0523a.f2259do.f2105a);
        c0523a.f2259do.f2109do = message.getData().getFloat("distance", c0523a.f2259do.f2109do);
        c0523a.f2259do.f2113if = message.getData().getBoolean("extraInfo", c0523a.f2259do.f2113if);
        c0523a.f2259do.f2110else = true;
        String format = String.format(Locale.CHINA, "&poi=%.1f|%d", new Object[]{Float.valueOf(c0523a.f2259do.f2109do), Integer.valueOf(c0523a.f2259do.f2105a)});
        return c0523a.f2259do.f2113if ? format + "|1" : format;
    }

    public void m5953if(BDLocation bDLocation, int i) {
        ArrayList arrayList = new ArrayList();
        Iterator it = this.bk.iterator();
        while (it.hasNext()) {
            C0523a c0523a = (C0523a) it.next();
            c0523a.m2192a(bDLocation, i);
            if (c0523a.f2261if > 4) {
                arrayList.add(c0523a);
            }
        }
        if (arrayList != null && arrayList.size() > 0) {
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                this.bk.remove((C0523a) it2.next());
            }
        }
    }

    public void m5954if(BDLocation bDLocation, Message message) {
        if (bDLocation != null && message != null) {
            C0523a c0523a = m5943if(message.replyTo);
            if (c0523a != null) {
                c0523a.m2191a(bDLocation);
                if (c0523a.f2261if > 4) {
                    this.bk.remove(c0523a);
                }
            }
        }
    }

    public boolean m5955int(Message message) {
        boolean z = false;
        C0523a c0523a = m5943if(message.replyTo);
        if (c0523a != null) {
            int i = c0523a.f2259do.f2114int;
            c0523a.f2259do.f2114int = message.getData().getInt("scanSpan", c0523a.f2259do.f2114int);
            if (c0523a.f2259do.f2114int < 1000) {
                C1618d.m4603new().mo1836a();
            } else {
                C1618d.m4603new().mo1837if();
            }
            if (c0523a.f2259do.f2114int > MapCore.MAPRENDER_CAN_STOP_AND_FULLSCREEN_RENDEROVER && i < 1000) {
                z = true;
            }
            c0523a.f2259do.f2107case = message.getData().getBoolean("openGPS", c0523a.f2259do.f2107case);
            String string = message.getData().getString("coorType");
            LocationClientOption locationClientOption = c0523a.f2259do;
            if (string == null || string.equals("")) {
                string = c0523a.f2259do.f2117try;
            }
            locationClientOption.f2117try = string;
            string = message.getData().getString("addrType");
            locationClientOption = c0523a.f2259do;
            if (string == null || string.equals("")) {
                string = c0523a.f2259do.f2108char;
            }
            locationClientOption.f2108char = string;
            if (!C1974b.ar.equals(c0523a.f2259do.f2108char)) {
                C2065y.ag().ah();
            }
            C1974b.ar = c0523a.f2259do.f2108char;
            c0523a.f2259do.f2115long = message.getData().getInt("timeOut", c0523a.f2259do.f2115long);
            c0523a.f2259do.f2118void = message.getData().getBoolean("location_change_notify", c0523a.f2259do.f2118void);
            c0523a.f2259do.f2112goto = message.getData().getInt("priority", c0523a.f2259do.f2112goto);
            m5945long();
        }
        return z;
    }

    public void m5956new(Message message) {
        if (message != null && message.replyTo != null) {
            m5944if(new C0523a(this, message));
            m5945long();
        }
    }

    public void m5957try(String str) {
        BDLocation bDLocation = new BDLocation(str);
        ab.m2123a().f2152new = str;
        Iterator it = this.bk.iterator();
        while (it.hasNext()) {
            ((C0523a) it.next()).m2194if(bDLocation);
        }
    }

    public boolean m5958void() {
        return this.bm;
    }
}
