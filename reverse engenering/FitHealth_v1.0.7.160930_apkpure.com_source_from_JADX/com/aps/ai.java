package com.aps;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.location.GpsStatus.NmeaListener;
import android.location.LocationManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Looper;
import android.provider.Settings.Secure;
import android.provider.Settings.System;
import android.support.v4.internal.view.SupportMenu;
import android.telephony.CellLocation;
import android.telephony.NeighboringCellInfo;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TreeMap;

public final class ai {
    private static int f1724C = 10000;
    private static ai f1725t = null;
    private Thread f1726A = null;
    private Looper f1727B = null;
    private Context f1728a = null;
    private TelephonyManager f1729b = null;
    private LocationManager f1730c = null;
    private WifiManager f1731d = null;
    private String f1732e = "";
    private String f1733f = "";
    private String f1734g = "";
    private boolean f1735h = false;
    private int f1736i = 0;
    private boolean f1737j = false;
    private long f1738k = -1;
    private String f1739l = "";
    private String f1740m = "";
    private int f1741n = 0;
    private int f1742o = 0;
    private int f1743p = 0;
    private String f1744q = "";
    private long f1745r = 0;
    private long f1746s = 0;
    private ak f1747u = null;
    private al f1748v = null;
    private CellLocation f1749w = null;
    private am f1750x = null;
    private List f1751y = new ArrayList();
    private Timer f1752z = null;

    private ai(Context context) {
        if (context != null) {
            this.f1728a = context;
            this.f1732e = Build.MODEL;
            this.f1729b = (TelephonyManager) context.getSystemService("phone");
            this.f1730c = (LocationManager) context.getSystemService("location");
            this.f1731d = (WifiManager) context.getSystemService("wifi");
            context.getSystemService("sensor");
            if (this.f1729b != null && this.f1731d != null) {
                try {
                    this.f1733f = this.f1729b.getDeviceId();
                } catch (Exception e) {
                }
                this.f1734g = this.f1729b.getSubscriberId();
                if (this.f1731d.getConnectionInfo() != null) {
                    this.f1740m = this.f1731d.getConnectionInfo().getMacAddress();
                    if (this.f1740m != null && this.f1740m.length() > 0) {
                        this.f1740m = this.f1740m.replace(":", "");
                    }
                }
                String[] b = m1774b(this.f1729b);
                this.f1741n = Integer.parseInt(b[0]);
                this.f1742o = Integer.parseInt(b[1]);
                this.f1743p = this.f1729b.getNetworkType();
                this.f1744q = context.getPackageName();
                this.f1735h = this.f1729b.getPhoneType() == 2;
            }
        }
    }

    protected static ai m1757a(Context context) {
        if (f1725t == null && m1778c(context)) {
            Object obj;
            LocationManager locationManager = (LocationManager) context.getSystemService("location");
            if (locationManager != null) {
                for (String str : locationManager.getAllProviders()) {
                    if (!str.equals("passive")) {
                        if (str.equals("gps")) {
                        }
                    }
                    obj = 1;
                }
            }
            obj = null;
            if (obj != null) {
                f1725t = new ai(context);
            }
        }
        return f1725t;
    }

    private void m1763a(BroadcastReceiver broadcastReceiver) {
        if (broadcastReceiver != null && this.f1728a != null) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.net.wifi.SCAN_RESULTS");
            this.f1728a.registerReceiver(broadcastReceiver, intentFilter);
        }
    }

    static /* synthetic */ void m1764a(ai aiVar, NmeaListener nmeaListener) {
        if (aiVar.f1730c != null && nmeaListener != null) {
            aiVar.f1730c.addNmeaListener(nmeaListener);
        }
    }

    static /* synthetic */ void m1765a(ai aiVar, PhoneStateListener phoneStateListener) {
        if (aiVar.f1729b != null) {
            aiVar.f1729b.listen(phoneStateListener, 273);
        }
    }

    private static void m1766a(List list) {
        if (list != null && list.size() > 0) {
            Object hashMap = new HashMap();
            for (int i = 0; i < list.size(); i++) {
                ScanResult scanResult = (ScanResult) list.get(i);
                if (scanResult.SSID == null) {
                    scanResult.SSID = "null";
                }
                hashMap.put(Integer.valueOf(scanResult.level), scanResult);
            }
            TreeMap treeMap = new TreeMap(Collections.reverseOrder());
            treeMap.putAll(hashMap);
            list.clear();
            for (Integer num : treeMap.keySet()) {
                list.add(treeMap.get(num));
            }
            hashMap.clear();
            treeMap.clear();
        }
    }

    private void m1772b(BroadcastReceiver broadcastReceiver) {
        if (broadcastReceiver != null && this.f1728a != null) {
            try {
                this.f1728a.unregisterReceiver(broadcastReceiver);
            } catch (Exception e) {
            }
        }
    }

    protected static boolean m1773b(Context context) {
        if (context == null) {
            return false;
        }
        boolean z;
        if (!Secure.getString(context.getContentResolver(), "mock_location").equals("0")) {
            PackageManager packageManager = context.getPackageManager();
            List<ApplicationInfo> installedApplications = packageManager.getInstalledApplications(128);
            String str = "android.permission.ACCESS_MOCK_LOCATION";
            String packageName = context.getPackageName();
            z = false;
            for (ApplicationInfo applicationInfo : installedApplications) {
                if (z) {
                    break;
                }
                boolean z2;
                try {
                    String[] strArr = packageManager.getPackageInfo(applicationInfo.packageName, 4096).requestedPermissions;
                    if (strArr != null) {
                        int length = strArr.length;
                        int i = 0;
                        while (i < length) {
                            if (!strArr[i].equals(str)) {
                                i++;
                            } else if (!applicationInfo.packageName.equals(packageName)) {
                                z2 = true;
                                z = z2;
                            }
                        }
                    }
                } catch (Exception e) {
                    z2 = z;
                }
            }
        } else {
            z = false;
        }
        return z;
    }

    private static String[] m1774b(TelephonyManager telephonyManager) {
        int i = 0;
        String str = null;
        if (telephonyManager != null) {
            str = telephonyManager.getNetworkOperator();
        }
        String[] strArr = new String[]{"0", "0"};
        if (TextUtils.isDigitsOnly(str) && str.length() > 4) {
            strArr[0] = str.substring(0, 3);
            char[] toCharArray = str.substring(3).toCharArray();
            while (i < toCharArray.length && Character.isDigit(toCharArray[i])) {
                i++;
            }
            strArr[1] = str.substring(3, i + 3);
        }
        return strArr;
    }

    private static boolean m1778c(Context context) {
        try {
            String[] strArr = context.getPackageManager().getPackageInfo(context.getPackageName(), 4096).requestedPermissions;
            for (String a : bb.f1800a) {
                if (!bb.m1840a(strArr, a)) {
                    return false;
                }
            }
            return true;
        } catch (NameNotFoundException e) {
            return false;
        }
    }

    private void m1785z() {
        if (this.f1731d != null) {
            this.f1731d.startScan();
        }
    }

    protected final List m1786a(float f) {
        List arrayList = new ArrayList();
        long currentTimeMillis = System.currentTimeMillis();
        if (Math.abs(f) <= 1.0f) {
            f = 1.0f;
        }
        if (m1790c()) {
            CellLocation cellLocation = (CellLocation) m1797j().get(1);
            if (cellLocation != null && (cellLocation instanceof GsmCellLocation)) {
                arrayList.add(Integer.valueOf(((GsmCellLocation) cellLocation).getLac()));
                arrayList.add(Integer.valueOf(((GsmCellLocation) cellLocation).getCid()));
                if (((double) (currentTimeMillis - ((Long) m1797j().get(0)).longValue())) <= 50000.0d / ((double) f)) {
                    arrayList.add(Integer.valueOf(1));
                } else {
                    arrayList.add(Integer.valueOf(0));
                }
            }
        }
        return arrayList;
    }

    protected final void m1787a() {
        String str = "";
        m1789b();
        if (this.f1727B != null) {
            this.f1727B.quit();
            this.f1727B = null;
        }
        if (this.f1726A != null) {
            this.f1726A.interrupt();
            this.f1726A = null;
        }
        this.f1726A = new aj(this, str);
        this.f1726A.start();
    }

    protected final List m1788b(float f) {
        List arrayList = new ArrayList();
        long currentTimeMillis = System.currentTimeMillis();
        if (Math.abs(f) <= 1.0f) {
            f = 1.0f;
        }
        if (m1790c()) {
            CellLocation cellLocation = (CellLocation) m1797j().get(1);
            if (cellLocation != null && (cellLocation instanceof CdmaCellLocation)) {
                CdmaCellLocation cdmaCellLocation = (CdmaCellLocation) cellLocation;
                arrayList.add(Integer.valueOf(cdmaCellLocation.getSystemId()));
                arrayList.add(Integer.valueOf(cdmaCellLocation.getNetworkId()));
                arrayList.add(Integer.valueOf(cdmaCellLocation.getBaseStationId()));
                arrayList.add(Integer.valueOf(cdmaCellLocation.getBaseStationLongitude()));
                arrayList.add(Integer.valueOf(cdmaCellLocation.getBaseStationLatitude()));
                if (((double) (currentTimeMillis - ((Long) m1797j().get(0)).longValue())) <= 50000.0d / ((double) f)) {
                    arrayList.add(Integer.valueOf(1));
                } else {
                    arrayList.add(Integer.valueOf(0));
                }
            }
        }
        return arrayList;
    }

    protected final void m1789b() {
        if (this.f1747u != null) {
            PhoneStateListener phoneStateListener = this.f1747u;
            if (this.f1729b != null) {
                this.f1729b.listen(phoneStateListener, 0);
            }
            this.f1747u = null;
        }
        if (this.f1748v != null) {
            NmeaListener nmeaListener = this.f1748v;
            if (!(this.f1730c == null || nmeaListener == null)) {
                this.f1730c.removeNmeaListener(nmeaListener);
            }
            this.f1748v = null;
        }
        if (this.f1752z != null) {
            this.f1752z.cancel();
            this.f1752z = null;
        }
        if (this.f1727B != null) {
            this.f1727B.quit();
            this.f1727B = null;
        }
        if (this.f1726A != null) {
            this.f1726A.interrupt();
            this.f1726A = null;
        }
    }

    protected final boolean m1790c() {
        CellLocation cellLocation = null;
        if (this.f1729b != null && this.f1729b.getSimState() == 5 && this.f1737j) {
            return true;
        }
        if (this.f1729b != null) {
            try {
                cellLocation = this.f1729b.getCellLocation();
            } catch (Exception e) {
            }
            if (cellLocation != null) {
                this.f1746s = System.currentTimeMillis();
                this.f1749w = cellLocation;
                return true;
            }
        }
        return false;
    }

    protected final boolean m1791d() {
        return this.f1731d != null && this.f1731d.isWifiEnabled();
    }

    protected final boolean m1792e() {
        try {
            if (this.f1730c != null && this.f1730c.isProviderEnabled("gps")) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    protected final String m1793f() {
        if (this.f1732e == null) {
            this.f1732e = Build.MODEL;
        }
        return this.f1732e != null ? this.f1732e : "";
    }

    protected final String m1794g() {
        if (this.f1733f == null && this.f1728a != null) {
            this.f1729b = (TelephonyManager) this.f1728a.getSystemService("phone");
            if (this.f1729b != null) {
                this.f1733f = this.f1729b.getDeviceId();
            }
        }
        return this.f1733f != null ? this.f1733f : "";
    }

    protected final String m1795h() {
        if (this.f1734g == null && this.f1728a != null) {
            this.f1729b = (TelephonyManager) this.f1728a.getSystemService("phone");
            if (this.f1729b != null) {
                this.f1734g = this.f1729b.getSubscriberId();
            }
        }
        return this.f1734g != null ? this.f1734g : "";
    }

    protected final boolean m1796i() {
        return this.f1735h;
    }

    protected final List m1797j() {
        if (System.getInt(this.f1728a.getContentResolver(), "airplane_mode_on", 0) == 1) {
            return new ArrayList();
        }
        if (!m1790c()) {
            return new ArrayList();
        }
        List arrayList = new ArrayList();
        arrayList.add(Long.valueOf(this.f1746s));
        arrayList.add(this.f1749w);
        return arrayList;
    }

    protected final List m1798k() {
        int i = 0;
        List arrayList = new ArrayList();
        if (!m1791d()) {
            return new ArrayList();
        }
        List arrayList2 = new ArrayList();
        synchronized (this) {
            if ((System.currentTimeMillis() - this.f1745r < 3500 ? 1 : 0) != 0) {
                arrayList2.add(Long.valueOf(this.f1745r));
                while (i < this.f1751y.size()) {
                    arrayList.add(this.f1751y.get(i));
                    i++;
                }
                arrayList2.add(arrayList);
            }
        }
        return arrayList2;
    }

    protected final byte m1799l() {
        return m1790c() ? (byte) this.f1736i : Byte.MIN_VALUE;
    }

    protected final List m1800m() {
        List arrayList = new ArrayList();
        if (this.f1729b == null) {
            return arrayList;
        }
        if (!m1790c()) {
            return arrayList;
        }
        int i = 0;
        for (NeighboringCellInfo neighboringCellInfo : this.f1729b.getNeighboringCellInfo()) {
            if (i > 15) {
                break;
            } else if (!(neighboringCellInfo.getLac() == 0 || neighboringCellInfo.getLac() == SupportMenu.USER_MASK || neighboringCellInfo.getCid() == SupportMenu.USER_MASK || neighboringCellInfo.getCid() == 268435455)) {
                arrayList.add(neighboringCellInfo);
                i++;
            }
        }
        return arrayList;
    }

    protected final List m1801n() {
        long j;
        Object obj;
        List arrayList = new ArrayList();
        String str = "";
        if (m1792e()) {
            long j2 = this.f1738k;
            j = j2;
            obj = this.f1739l;
        } else {
            String str2 = str;
            j = -1;
            String str3 = str2;
        }
        if (j <= 0) {
            j = System.currentTimeMillis() / 1000;
        }
        if (j > 2147483647L) {
            j /= 1000;
        }
        arrayList.add(Long.valueOf(j));
        arrayList.add(obj);
        return arrayList;
    }

    protected final long m1802o() {
        long j = 0;
        long j2 = this.f1738k;
        if (j2 > 0) {
            j = j2;
            int length = String.valueOf(j2).length();
            while (length != 13) {
                j = length > 13 ? j / 10 : j * 10;
                length = String.valueOf(j).length();
            }
        }
        return j;
    }

    protected final String m1803p() {
        if (this.f1740m == null && this.f1728a != null) {
            this.f1731d = (WifiManager) this.f1728a.getSystemService("wifi");
            if (!(this.f1731d == null || this.f1731d.getConnectionInfo() == null)) {
                this.f1740m = this.f1731d.getConnectionInfo().getMacAddress();
                if (this.f1740m != null && this.f1740m.length() > 0) {
                    this.f1740m = this.f1740m.replace(":", "");
                }
            }
        }
        return this.f1740m != null ? this.f1740m : "";
    }

    protected final int m1804q() {
        return this.f1741n;
    }

    protected final int m1805r() {
        return this.f1742o;
    }

    protected final int m1806s() {
        return this.f1743p;
    }

    protected final String m1807t() {
        if (this.f1744q == null && this.f1728a != null) {
            this.f1744q = this.f1728a.getPackageName();
        }
        return this.f1744q != null ? this.f1744q : "";
    }

    protected final List m1808u() {
        int i = 0;
        List arrayList = new ArrayList();
        if (m1791d()) {
            List k = m1798k();
            List list = (List) k.get(1);
            long longValue = ((Long) k.get(0)).longValue();
            m1766a(list);
            arrayList.add(Long.valueOf(longValue));
            if (list != null && list.size() > 0) {
                while (i < list.size()) {
                    ScanResult scanResult = (ScanResult) list.get(i);
                    if (arrayList.size() - 1 >= 40) {
                        break;
                    }
                    if (scanResult != null) {
                        List arrayList2 = new ArrayList();
                        arrayList2.add(scanResult.BSSID.replace(":", ""));
                        arrayList2.add(Integer.valueOf(scanResult.level));
                        arrayList2.add(scanResult.SSID);
                        arrayList.add(arrayList2);
                    }
                    i++;
                }
            }
        }
        return arrayList;
    }

    protected final void m1809v() {
        synchronized (this) {
            this.f1751y.clear();
        }
        if (this.f1750x != null) {
            m1772b(this.f1750x);
            this.f1750x = null;
        }
        if (this.f1752z != null) {
            this.f1752z.cancel();
            this.f1752z = null;
        }
        this.f1752z = new Timer();
        this.f1750x = new am();
        m1763a(this.f1750x);
        m1785z();
    }

    protected final void m1810w() {
        synchronized (this) {
            this.f1751y.clear();
        }
        if (this.f1750x != null) {
            m1772b(this.f1750x);
            this.f1750x = null;
        }
        if (this.f1752z != null) {
            this.f1752z.cancel();
            this.f1752z = null;
        }
    }

    protected final Context m1811x() {
        return this.f1728a;
    }
}
