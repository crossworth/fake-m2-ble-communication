package com.baidu.location.p005a;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.telephony.CellLocation;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;
import com.baidu.android.bbalbs.common.util.CommonParam;
import com.baidu.location.BDLocation;
import com.baidu.location.Jni;
import com.baidu.location.LocationClientOption;
import com.baidu.location.p006h.C0335e;
import com.baidu.location.p006h.C0468j;
import com.baidu.location.p011e.C0411a;
import com.baidu.platform.comapi.location.CoordinateType;
import com.tencent.connect.common.Constants;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class C0338b {
    private static Method f145e = null;
    private static Method f146f = null;
    private static Method f147g = null;
    private static Class<?> f148h = null;
    C0336c f149a = new C0336c(this);
    private Context f150b = null;
    private TelephonyManager f151c = null;
    private C0334a f152d = new C0334a();
    private WifiManager f153i = null;
    private C0337d f154j = null;
    private String f155k = null;
    private LocationClientOption f156l;
    private C0329b f157m;
    private String f158n = null;
    private String f159o = null;

    public interface C0329b {
        void onReceiveLocation(BDLocation bDLocation);
    }

    private class C0334a {
        public int f123a;
        public int f124b;
        public int f125c;
        public int f126d;
        public char f127e;
        final /* synthetic */ C0338b f128f;

        private C0334a(C0338b c0338b) {
            this.f128f = c0338b;
            this.f123a = -1;
            this.f124b = -1;
            this.f125c = -1;
            this.f126d = -1;
            this.f127e = '\u0000';
        }

        private boolean m192d() {
            return this.f123a > -1 && this.f124b > 0;
        }

        public int m193a() {
            return (this.f125c <= 0 || !m192d()) ? 2 : (this.f125c == 460 || this.f125c == 454 || this.f125c == 455 || this.f125c == 466) ? 1 : 0;
        }

        public String m194b() {
            if (!m192d()) {
                return null;
            }
            StringBuffer stringBuffer = new StringBuffer(128);
            stringBuffer.append("&nw=");
            stringBuffer.append(this.f127e);
            stringBuffer.append(String.format(Locale.CHINA, "&cl=%d|%d|%d|%d", new Object[]{Integer.valueOf(this.f125c), Integer.valueOf(this.f126d), Integer.valueOf(this.f123a), Integer.valueOf(this.f124b)}));
            return stringBuffer.toString();
        }

        public String m195c() {
            if (!m192d()) {
                return null;
            }
            StringBuffer stringBuffer = new StringBuffer(128);
            stringBuffer.append(this.f124b + 23);
            stringBuffer.append("H");
            stringBuffer.append(this.f123a + 45);
            stringBuffer.append("K");
            stringBuffer.append(this.f126d + 54);
            stringBuffer.append("Q");
            stringBuffer.append(this.f125c + 203);
            return stringBuffer.toString();
        }
    }

    class C0336c extends C0335e {
        String f140a;
        final /* synthetic */ C0338b f141b;

        C0336c(C0338b c0338b) {
            this.f141b = c0338b;
            this.f140a = null;
            this.k = new HashMap();
        }

        public void mo1741a() {
            this.h = C0468j.m1023c();
            String encodeTp4 = Jni.encodeTp4(this.f140a);
            this.f140a = null;
            this.k.put("bloc", encodeTp4);
            StringBuffer stringBuffer = new StringBuffer(512);
            if (this.f141b.f159o != null) {
                stringBuffer.append(String.format(Locale.CHINA, "&ki=%s", new Object[]{this.f141b.f159o}));
            }
            if (stringBuffer.length() > 0) {
                this.k.put(SocializeProtocolConstants.PROTOCOL_KEY_EXTEND, Jni.encode(stringBuffer.toString()));
            }
            this.k.put("trtm", String.format(Locale.CHINA, "%d", new Object[]{Long.valueOf(System.currentTimeMillis())}));
        }

        public void m206a(String str) {
            this.f140a = str;
            m204e();
        }

        public void mo1742a(boolean z) {
            if (z && this.j != null) {
                try {
                    BDLocation bDLocation;
                    try {
                        bDLocation = new BDLocation(this.j);
                    } catch (Exception e) {
                        bDLocation = new BDLocation();
                        bDLocation.setLocType(63);
                    }
                    if (bDLocation != null) {
                        if (bDLocation.getLocType() == 161) {
                            bDLocation.setCoorType(this.f141b.f156l.coorType);
                            this.f141b.f157m.onReceiveLocation(bDLocation);
                        }
                    }
                } catch (Exception e2) {
                }
            }
            if (this.k != null) {
                this.k.clear();
            }
        }
    }

    protected class C0337d {
        public List<ScanResult> f142a = null;
        final /* synthetic */ C0338b f143b;
        private long f144c = 0;

        public C0337d(C0338b c0338b, List<ScanResult> list) {
            this.f143b = c0338b;
            this.f142a = list;
            this.f144c = System.currentTimeMillis();
            m208b();
        }

        private void m208b() {
            if (m209a() >= 1) {
                Object obj = 1;
                for (int size = this.f142a.size() - 1; size >= 1 && r2 != null; size--) {
                    int i = 0;
                    obj = null;
                    while (i < size) {
                        Object obj2;
                        if (((ScanResult) this.f142a.get(i)).level < ((ScanResult) this.f142a.get(i + 1)).level) {
                            ScanResult scanResult = (ScanResult) this.f142a.get(i + 1);
                            this.f142a.set(i + 1, this.f142a.get(i));
                            this.f142a.set(i, scanResult);
                            obj2 = 1;
                        } else {
                            obj2 = obj;
                        }
                        i++;
                        obj = obj2;
                    }
                }
            }
        }

        public int m209a() {
            return this.f142a == null ? 0 : this.f142a.size();
        }

        public String m210a(int i) {
            if (m209a() < 2) {
                return null;
            }
            StringBuffer stringBuffer = new StringBuffer(512);
            int size = this.f142a.size();
            int i2 = 0;
            int i3 = 0;
            int i4 = 1;
            while (i2 < size) {
                int i5;
                if (((ScanResult) this.f142a.get(i2)).level == 0) {
                    i5 = i3;
                } else {
                    if (i4 != 0) {
                        stringBuffer.append("&wf=");
                        i4 = 0;
                    } else {
                        stringBuffer.append("|");
                    }
                    stringBuffer.append(((ScanResult) this.f142a.get(i2)).BSSID.replace(":", ""));
                    i5 = ((ScanResult) this.f142a.get(i2)).level;
                    if (i5 < 0) {
                        i5 = -i5;
                    }
                    stringBuffer.append(String.format(Locale.CHINA, ";%d;", new Object[]{Integer.valueOf(i5)}));
                    i5 = i3 + 1;
                    if (i5 > i) {
                        break;
                    }
                }
                i2++;
                i3 = i5;
            }
            return i4 != 0 ? null : stringBuffer.toString();
        }
    }

    public C0338b(Context context, LocationClientOption locationClientOption, C0329b c0329b) {
        String deviceId;
        String a;
        this.f150b = context.getApplicationContext();
        this.f156l = locationClientOption;
        this.f157m = c0329b;
        String packageName = this.f150b.getPackageName();
        try {
            this.f151c = (TelephonyManager) this.f150b.getSystemService("phone");
            deviceId = this.f151c.getDeviceId();
        } catch (Exception e) {
            deviceId = null;
        }
        try {
            a = CommonParam.m69a(this.f150b);
        } catch (Exception e2) {
            a = null;
        }
        if (a != null) {
            this.f155k = "&prod=" + this.f156l.prodName + ":" + packageName + "|&cu=" + a + "&coor=" + locationClientOption.getCoorType();
        } else {
            this.f155k = "&prod=" + this.f156l.prodName + ":" + packageName + "|&im=" + deviceId + "&coor=" + locationClientOption.getCoorType();
        }
        StringBuffer stringBuffer = new StringBuffer(256);
        stringBuffer.append("&fw=");
        stringBuffer.append("7.01");
        stringBuffer.append("&lt=1");
        stringBuffer.append("&mb=");
        stringBuffer.append(Build.MODEL);
        stringBuffer.append("&resid=");
        stringBuffer.append(Constants.VIA_REPORT_TYPE_SET_AVATAR);
        if (locationClientOption.getAddrType() != null) {
        }
        if (locationClientOption.getAddrType() != null && locationClientOption.getAddrType().equals("all")) {
            this.f155k += "&addr=all";
        }
        if (locationClientOption.isNeedAptag || locationClientOption.isNeedAptagd) {
            this.f155k += "&sema=";
            if (locationClientOption.isNeedAptag) {
                this.f155k += "aptag|";
            }
            if (locationClientOption.isNeedAptagd) {
                this.f155k += "aptagd|";
            }
            this.f159o = C0347h.m269b(this.f150b);
        }
        stringBuffer.append("&first=1");
        stringBuffer.append(VERSION.SDK);
        this.f155k += stringBuffer.toString();
        this.f153i = (WifiManager) this.f150b.getSystemService("wifi");
        a = m216a();
        if (!TextUtils.isEmpty(a)) {
            a = a.replace(":", "");
        }
        if (!(TextUtils.isEmpty(a) || a.equals("02:00:00:00:00:00"))) {
            this.f155k += "&mac=" + a;
        }
        m217b();
    }

    private String m211a(int i) {
        String b;
        String a;
        if (i < 3) {
            i = 3;
        }
        try {
            m213a(this.f151c.getCellLocation());
            b = this.f152d.m194b();
        } catch (Exception e) {
            b = null;
        }
        try {
            this.f154j = null;
            this.f154j = new C0337d(this, this.f153i.getScanResults());
            a = this.f154j.m210a(i);
        } catch (Exception e2) {
            a = null;
        }
        if (b == null && a == null) {
            this.f158n = null;
            return null;
        }
        if (a != null) {
            b = b + a;
        }
        if (b == null) {
            return null;
        }
        this.f158n = b + this.f155k;
        return b + this.f155k;
    }

    private void m213a(CellLocation cellLocation) {
        int i = 0;
        if (cellLocation != null && this.f151c != null) {
            C0334a c0334a = new C0334a();
            String networkOperator = this.f151c.getNetworkOperator();
            if (networkOperator != null && networkOperator.length() > 0) {
                try {
                    if (networkOperator.length() >= 3) {
                        int intValue = Integer.valueOf(networkOperator.substring(0, 3)).intValue();
                        if (intValue < 0) {
                            intValue = this.f152d.f125c;
                        }
                        c0334a.f125c = intValue;
                    }
                    String substring = networkOperator.substring(3);
                    if (substring != null) {
                        char[] toCharArray = substring.toCharArray();
                        while (i < toCharArray.length && Character.isDigit(toCharArray[i])) {
                            i++;
                        }
                    }
                    i = Integer.valueOf(substring.substring(0, i)).intValue();
                    if (i < 0) {
                        i = this.f152d.f126d;
                    }
                    c0334a.f126d = i;
                } catch (Exception e) {
                }
            }
            if (cellLocation instanceof GsmCellLocation) {
                c0334a.f123a = ((GsmCellLocation) cellLocation).getLac();
                c0334a.f124b = ((GsmCellLocation) cellLocation).getCid();
                c0334a.f127e = 'g';
            } else if (cellLocation instanceof CdmaCellLocation) {
                c0334a.f127e = 'c';
                if (f148h == null) {
                    try {
                        f148h = Class.forName("android.telephony.cdma.CdmaCellLocation");
                        f145e = f148h.getMethod("getBaseStationId", new Class[0]);
                        f146f = f148h.getMethod("getNetworkId", new Class[0]);
                        f147g = f148h.getMethod("getSystemId", new Class[0]);
                    } catch (Exception e2) {
                        f148h = null;
                        return;
                    }
                }
                if (f148h != null && f148h.isInstance(cellLocation)) {
                    try {
                        i = ((Integer) f147g.invoke(cellLocation, new Object[0])).intValue();
                        if (i < 0) {
                            i = this.f152d.f126d;
                        }
                        c0334a.f126d = i;
                        c0334a.f124b = ((Integer) f145e.invoke(cellLocation, new Object[0])).intValue();
                        c0334a.f123a = ((Integer) f146f.invoke(cellLocation, new Object[0])).intValue();
                    } catch (Exception e3) {
                        return;
                    }
                }
            }
            if (c0334a.m192d()) {
                this.f152d = c0334a;
            } else {
                this.f152d = null;
            }
        }
    }

    public String m216a() {
        String str = null;
        try {
            WifiInfo connectionInfo = this.f153i.getConnectionInfo();
            if (connectionInfo != null) {
                str = connectionInfo.getMacAddress();
            }
        } catch (Exception e) {
        }
        return str;
    }

    public String m217b() {
        try {
            return m211a(15);
        } catch (Exception e) {
            return null;
        }
    }

    public void m218c() {
        if (this.f158n != null && this.f152d != null && this.f152d.m193a() == 1) {
            BDLocation bDLocation;
            if (this.f153i == null || this.f156l.scanSpan < 1000 || this.f156l.getAddrType().equals("all") || this.f156l.isNeedAptag || this.f156l.isNeedAptagd) {
                bDLocation = null;
            } else {
                try {
                    bDLocation = C0411a.m637a().m654a(this.f152d.m195c(), this.f153i.getScanResults(), false);
                    if (!this.f156l.coorType.equals(CoordinateType.GCJ02)) {
                        double longitude = bDLocation.getLongitude();
                        double latitude = bDLocation.getLatitude();
                        if (!(longitude == Double.MIN_VALUE || latitude == Double.MIN_VALUE)) {
                            double[] coorEncrypt = Jni.coorEncrypt(longitude, latitude, this.f156l.coorType);
                            bDLocation.setLongitude(coorEncrypt[0]);
                            bDLocation.setLatitude(coorEncrypt[1]);
                            bDLocation.setCoorType(this.f156l.coorType);
                        }
                    }
                    if (bDLocation.getLocType() == 66) {
                        this.f157m.onReceiveLocation(bDLocation);
                    }
                } catch (Exception e) {
                    bDLocation = null;
                }
            }
            if (bDLocation == null) {
                this.f149a.m206a(this.f158n);
            }
        }
    }
}
