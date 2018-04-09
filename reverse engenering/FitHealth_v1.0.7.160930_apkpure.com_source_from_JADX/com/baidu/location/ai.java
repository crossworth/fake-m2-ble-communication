package com.baidu.location;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Locale;

class ai implements an, C1619j {
    private static final int f0 = 15;
    private static ai f8 = null;
    private boolean f1 = true;
    private C0503b f2 = null;
    private Object f3 = null;
    private final long f4 = 3000;
    private long f5 = 0;
    private final long f6 = 5000;
    private WifiManager f7 = null;
    private Method f9 = null;
    private final long fZ = 3000;
    private boolean ga = false;
    private long gb = 0;
    private C0502a gc = null;

    private class C0502a extends BroadcastReceiver {
        final /* synthetic */ ai f2162a;

        private C0502a(ai aiVar) {
            this.f2162a = aiVar;
        }

        public void onReceive(Context context, Intent intent) {
            if (context != null) {
                this.f2162a.a6();
                C1976f.getHandler().obtainMessage(41).sendToTarget();
                if (C1988x.aU().aY()) {
                    C1988x.aU().fF.obtainMessage(41).sendToTarget();
                }
            }
        }
    }

    protected class C0503b {
        final /* synthetic */ ai f2163a;
        private boolean f2164do = false;
        public List f2165for = null;
        private long f2166if = 0;
        private long f2167int = 0;

        public C0503b(ai aiVar, C0503b c0503b) {
            this.f2163a = aiVar;
            if (c0503b != null) {
                this.f2165for = c0503b.f2165for;
                this.f2166if = c0503b.f2166if;
                this.f2167int = c0503b.f2167int;
                this.f2164do = c0503b.f2164do;
            }
        }

        public C0503b(ai aiVar, List list, long j) {
            this.f2163a = aiVar;
            this.f2166if = j;
            this.f2165for = list;
            this.f2167int = System.currentTimeMillis();
            m2146a();
            C1974b.m5914do(an.f2222l, m2160int());
        }

        private void m2146a() {
            if (m2162try() >= 1) {
                Object obj = 1;
                for (int size = this.f2165for.size() - 1; size >= 1 && r2 != null; size--) {
                    int i = 0;
                    obj = null;
                    while (i < size) {
                        Object obj2;
                        if (((ScanResult) this.f2165for.get(i)).level < ((ScanResult) this.f2165for.get(i + 1)).level) {
                            ScanResult scanResult = (ScanResult) this.f2165for.get(i + 1);
                            this.f2165for.set(i + 1, this.f2165for.get(i));
                            this.f2165for.set(i, scanResult);
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

        public String m2147a(int i) {
            if (m2162try() < 1) {
                return null;
            }
            StringBuffer stringBuffer = new StringBuffer(512);
            String bd = this.f2163a.bd();
            int i2 = 0;
            int i3 = 0;
            int size = this.f2165for.size();
            Object obj = 1;
            if (size <= i) {
                i = size;
            }
            int i4 = 0;
            while (i4 < i) {
                Object obj2;
                int i5;
                if (((ScanResult) this.f2165for.get(i4)).level == 0) {
                    obj2 = obj;
                    i5 = i2;
                } else if (obj != null) {
                    stringBuffer.append("&wf=");
                    String replace = ((ScanResult) this.f2165for.get(i4)).BSSID.replace(":", "");
                    stringBuffer.append(replace);
                    size = ((ScanResult) this.f2165for.get(i4)).level;
                    if (size < 0) {
                        size = -size;
                    }
                    stringBuffer.append(String.format(Locale.CHINA, ";%d;", new Object[]{Integer.valueOf(size)}));
                    i5 = i2 + 1;
                    size = (bd == null || !bd.equals(replace)) ? i3 : i5;
                    i3 = size;
                    obj2 = null;
                } else {
                    stringBuffer.append("|");
                    String replace2 = ((ScanResult) this.f2165for.get(i4)).BSSID.replace(":", "");
                    stringBuffer.append(replace2);
                    size = ((ScanResult) this.f2165for.get(i4)).level;
                    if (size < 0) {
                        size = -size;
                    }
                    stringBuffer.append(String.format(Locale.CHINA, ";%d;", new Object[]{Integer.valueOf(size)}));
                    size = i2 + 1;
                    Object obj3;
                    if (bd == null || !bd.equals(replace2)) {
                        obj3 = obj;
                        i5 = size;
                        obj2 = obj3;
                    } else {
                        i3 = size;
                        obj3 = obj;
                        i5 = size;
                        obj2 = obj3;
                    }
                }
                i4++;
                i2 = i5;
                obj = obj2;
            }
            if (obj != null) {
                return null;
            }
            stringBuffer.append("&wf_n=" + i3);
            stringBuffer.append("&wf_st=");
            stringBuffer.append(this.f2166if);
            stringBuffer.append("&wf_et=");
            stringBuffer.append(this.f2167int);
            if (i3 > 0) {
                this.f2164do = true;
            }
            return stringBuffer.toString();
        }

        public boolean m2148a(C0503b c0503b) {
            return m2149a(c0503b, this, C1974b.aN);
        }

        public boolean m2149a(C0503b c0503b, C0503b c0503b2, float f) {
            if (c0503b == null || c0503b2 == null) {
                return false;
            }
            List list = c0503b.f2165for;
            List list2 = c0503b2.f2165for;
            if (list == list2) {
                return true;
            }
            if (list == null || list2 == null) {
                return false;
            }
            int size = list.size();
            int size2 = list2.size();
            float f2 = (float) (size + size2);
            if (size == 0 && size2 == 0) {
                return true;
            }
            if (size == 0 || size2 == 0) {
                return false;
            }
            int i = 0;
            int i2 = 0;
            while (i < size) {
                int i3;
                String str = ((ScanResult) list.get(i)).BSSID;
                if (str == null) {
                    i3 = i2;
                } else {
                    for (int i4 = 0; i4 < size2; i4++) {
                        if (str.equals(((ScanResult) list2.get(i4)).BSSID)) {
                            i3 = i2 + 1;
                            break;
                        }
                    }
                    i3 = i2;
                }
                i++;
                i2 = i3;
            }
            return ((float) (i2 * 2)) >= f2 * f;
        }

        public String m2150byte() {
            try {
                return m2147a(15);
            } catch (Exception e) {
                return null;
            }
        }

        public boolean m2151case() {
            return this.f2164do;
        }

        public String m2152char() {
            try {
                return m2147a(C1974b.aQ);
            } catch (Exception e) {
                return null;
            }
        }

        public int m2153do() {
            for (int i = 0; i < m2162try(); i++) {
                int i2 = -((ScanResult) this.f2165for.get(i)).level;
                if (i2 > 0) {
                    return i2;
                }
            }
            return 0;
        }

        public boolean m2154do(C0503b c0503b) {
            if (this.f2165for == null || c0503b == null || c0503b.f2165for == null) {
                return false;
            }
            int size = this.f2165for.size() < c0503b.f2165for.size() ? this.f2165for.size() : c0503b.f2165for.size();
            for (int i = 0; i < size; i++) {
                String str = ((ScanResult) this.f2165for.get(i)).BSSID;
                int i2 = ((ScanResult) this.f2165for.get(i)).level;
                String str2 = ((ScanResult) c0503b.f2165for.get(i)).BSSID;
                int i3 = ((ScanResult) c0503b.f2165for.get(i)).level;
                if (!str.equals(str2) || i2 != i3) {
                    return false;
                }
            }
            return true;
        }

        public String m2155else() {
            StringBuffer stringBuffer = new StringBuffer(512);
            stringBuffer.append("wifi info:");
            if (m2162try() < 1) {
                return stringBuffer.toString();
            }
            int size = this.f2165for.size();
            if (size > 10) {
                size = 10;
            }
            int i = 0;
            int i2 = 1;
            while (i < size) {
                int i3;
                if (((ScanResult) this.f2165for.get(i)).level == 0) {
                    i3 = i2;
                } else if (i2 != 0) {
                    stringBuffer.append("wifi=");
                    stringBuffer.append(((ScanResult) this.f2165for.get(i)).BSSID.replace(":", ""));
                    i3 = ((ScanResult) this.f2165for.get(i)).level;
                    stringBuffer.append(String.format(Locale.CHINA, ";%d;", new Object[]{Integer.valueOf(i3)}));
                    i3 = 0;
                } else {
                    stringBuffer.append(";");
                    stringBuffer.append(((ScanResult) this.f2165for.get(i)).BSSID.replace(":", ""));
                    i3 = ((ScanResult) this.f2165for.get(i)).level;
                    stringBuffer.append(String.format(Locale.CHINA, ",%d;", new Object[]{Integer.valueOf(i3)}));
                    i3 = i2;
                }
                i++;
                i2 = i3;
            }
            return stringBuffer.toString();
        }

        public boolean m2156for() {
            return System.currentTimeMillis() - this.f2167int < 3000;
        }

        public String m2157if(int i) {
            if (i == 0 || m2162try() < 1) {
                return null;
            }
            StringBuffer stringBuffer = new StringBuffer(256);
            int i2 = 0;
            int i3 = 1;
            for (int i4 = 0; i4 < C1974b.aQ; i4++) {
                if ((i3 & i) != 0) {
                    if (i2 == 0) {
                        stringBuffer.append("&ssid=");
                    } else {
                        stringBuffer.append("|");
                    }
                    stringBuffer.append(((ScanResult) this.f2165for.get(i4)).BSSID);
                    stringBuffer.append(";");
                    stringBuffer.append(((ScanResult) this.f2165for.get(i4)).SSID);
                    i2++;
                }
                i3 <<= 1;
            }
            return stringBuffer.toString();
        }

        public boolean m2158if() {
            return System.currentTimeMillis() - this.f2166if < 3000;
        }

        public boolean m2159if(C0503b c0503b) {
            if (this.f2165for == null || c0503b == null || c0503b.f2165for == null) {
                return false;
            }
            int size = this.f2165for.size() < c0503b.f2165for.size() ? this.f2165for.size() : c0503b.f2165for.size();
            for (int i = 0; i < size; i++) {
                if (!((ScanResult) this.f2165for.get(i)).BSSID.equals(((ScanResult) c0503b.f2165for.get(i)).BSSID)) {
                    return false;
                }
            }
            return true;
        }

        public String m2160int() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("wifi=");
            if (this.f2165for == null) {
                return stringBuilder.toString();
            }
            for (int i = 0; i < this.f2165for.size(); i++) {
                int i2 = ((ScanResult) this.f2165for.get(i)).level;
                stringBuilder.append(((ScanResult) this.f2165for.get(i)).BSSID.replace(":", ""));
                stringBuilder.append(String.format(Locale.CHINA, ",%d;", new Object[]{Integer.valueOf(i2)}));
            }
            return stringBuilder.toString();
        }

        public boolean m2161new() {
            return System.currentTimeMillis() - this.f2167int < 5000;
        }

        public int m2162try() {
            return this.f2165for == null ? 0 : this.f2165for.size();
        }
    }

    private ai() {
    }

    private void a6() {
        if (this.f7 != null) {
            try {
                C0503b c0503b = new C0503b(this, this.f7.getScanResults(), this.f5);
                this.f5 = 0;
                if (this.f2 == null || !c0503b.m2159if(this.f2)) {
                    this.f2 = c0503b;
                }
            } catch (Exception e) {
            }
        }
    }

    public static ai bb() {
        if (f8 == null) {
            f8 = new ai();
        }
        return f8;
    }

    public static boolean bf() {
        State state;
        State state2 = State.UNKNOWN;
        try {
            state = ((ConnectivityManager) C1976f.getServiceContext().getSystemService("connectivity")).getNetworkInfo(1).getState();
        } catch (Exception e) {
            state = state2;
        }
        return state == State.CONNECTED;
    }

    public boolean a4() {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - this.gb <= 10000) {
            return false;
        }
        this.gb = currentTimeMillis;
        return a8();
    }

    public boolean a5() {
        return this.f7.isWifiEnabled() && 3 == this.f7.getWifiState();
    }

    public C0503b a7() {
        return (this.f2 == null || !this.f2.m2156for()) ? be() : this.f2;
    }

    public boolean a8() {
        return (this.f7 != null && System.currentTimeMillis() - this.f5 > 3000) ? bc() : false;
    }

    public void a9() {
        if (!this.ga) {
            this.f7 = (WifiManager) C1976f.getServiceContext().getSystemService("wifi");
            this.gc = new C0502a();
            try {
                C1976f.getServiceContext().registerReceiver(this.gc, new IntentFilter("android.net.wifi.SCAN_RESULTS"));
            } catch (Exception e) {
            }
            this.ga = true;
            try {
                Field declaredField = Class.forName("android.net.wifi.WifiManager").getDeclaredField("mService");
                if (declaredField != null) {
                    declaredField.setAccessible(true);
                    this.f3 = declaredField.get(this.f7);
                    this.f9 = this.f3.getClass().getDeclaredMethod("startScan", new Class[]{Boolean.TYPE});
                    if (this.f9 != null) {
                        this.f9.setAccessible(true);
                    }
                }
            } catch (Exception e2) {
            }
        }
    }

    public C0503b ba() {
        return (this.f2 == null || !this.f2.m2161new()) ? be() : this.f2;
    }

    public boolean bc() {
        try {
            if (this.f7.isWifiEnabled()) {
                if (this.f9 == null || this.f3 == null) {
                    this.f7.startScan();
                } else {
                    try {
                        this.f9.invoke(this.f3, new Object[]{Boolean.valueOf(this.f1)});
                    } catch (Exception e) {
                        e.printStackTrace();
                        this.f7.startScan();
                    }
                }
                this.f5 = System.currentTimeMillis();
                return true;
            }
            this.f5 = 0;
            return false;
        } catch (Exception e2) {
            return false;
        }
    }

    public String bd() {
        String str = null;
        WifiInfo connectionInfo = this.f7.getConnectionInfo();
        if (connectionInfo != null) {
            try {
                String bssid = connectionInfo.getBSSID();
                if (bssid != null) {
                    str = bssid.replace(":", "");
                }
            } catch (Exception e) {
            }
        }
        return str;
    }

    public C0503b be() {
        if (this.f7 != null) {
            try {
                return new C0503b(this, this.f7.getScanResults(), 0);
            } catch (Exception e) {
            }
        }
        return new C0503b(this, null, 0);
    }

    public void bg() {
        if (this.ga) {
            try {
                C1976f.getServiceContext().unregisterReceiver(this.gc);
            } catch (Exception e) {
            }
            this.gc = null;
            this.f7 = null;
            this.ga = false;
        }
    }
}
