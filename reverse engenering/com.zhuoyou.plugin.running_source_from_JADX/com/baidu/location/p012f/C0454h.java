package com.baidu.location.p012f;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build.VERSION;
import android.os.Handler;
import com.baidu.location.C0455f;
import com.baidu.location.p005a.C0351i;
import com.baidu.location.p005a.C0359q;
import com.baidu.location.p005a.C0362s;
import com.baidu.location.p008c.C0397b;
import java.util.List;

public class C0454h {
    public static long f810a = 0;
    private static C0454h f811b = null;
    private WifiManager f812c = null;
    private C0453a f813d = null;
    private C0451g f814e = null;
    private long f815f = 0;
    private long f816g = 0;
    private boolean f817h = false;
    private Handler f818i = new Handler();

    private class C0453a extends BroadcastReceiver {
        final /* synthetic */ C0454h f807a;
        private long f808b;
        private boolean f809c;

        private C0453a(C0454h c0454h) {
            this.f807a = c0454h;
            this.f808b = 0;
            this.f809c = false;
        }

        public void onReceive(Context context, Intent intent) {
            if (context != null) {
                String action = intent.getAction();
                if (action.equals("android.net.wifi.SCAN_RESULTS")) {
                    C0454h.f810a = System.currentTimeMillis() / 1000;
                    this.f807a.m953q();
                    C0351i.m280c().m305i();
                    if (C0397b.m541a().m586f()) {
                        C0397b.m541a().f489c.obtainMessage(41).sendToTarget();
                    }
                    this.f807a.m966o();
                    if (System.currentTimeMillis() - C0359q.m342b() <= 5000) {
                        C0362s.m353a(C0359q.m343c(), this.f807a.m963l(), C0359q.m344d(), C0359q.m337a());
                    }
                } else if (action.equals("android.net.wifi.STATE_CHANGE") && ((NetworkInfo) intent.getParcelableExtra("networkInfo")).getState().equals(State.CONNECTED) && System.currentTimeMillis() - this.f808b >= 5000) {
                    this.f808b = System.currentTimeMillis();
                    if (!this.f809c) {
                        this.f809c = true;
                    }
                }
            }
        }
    }

    private C0454h() {
    }

    public static synchronized C0454h m948a() {
        C0454h c0454h;
        synchronized (C0454h.class) {
            if (f811b == null) {
                f811b = new C0454h();
            }
            c0454h = f811b;
        }
        return c0454h;
    }

    private String m949a(long j) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(String.valueOf((int) (j & 255)));
        stringBuffer.append('.');
        stringBuffer.append(String.valueOf((int) ((j >> 8) & 255)));
        stringBuffer.append('.');
        stringBuffer.append(String.valueOf((int) ((j >> 16) & 255)));
        stringBuffer.append('.');
        stringBuffer.append(String.valueOf((int) ((j >> 24) & 255)));
        return stringBuffer.toString();
    }

    public static boolean m951a(C0451g c0451g, C0451g c0451g2, float f) {
        if (c0451g == null || c0451g2 == null) {
            return false;
        }
        List list = c0451g.f802a;
        List list2 = c0451g2.f802a;
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
        return ((float) (i2 * 2)) > f2 * f;
    }

    public static boolean m952h() {
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) C0455f.getServiceContext().getSystemService("connectivity")).getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.getType() == 1;
        } catch (Exception e) {
            return false;
        }
    }

    private void m953q() {
        if (this.f812c != null) {
            try {
                List scanResults = this.f812c.getScanResults();
                if (scanResults != null) {
                    C0451g c0451g = new C0451g(scanResults, System.currentTimeMillis());
                    if (this.f814e == null || !c0451g.m936a(this.f814e)) {
                        this.f814e = c0451g;
                    }
                }
            } catch (Exception e) {
            }
        }
    }

    public synchronized void m954b() {
        if (!this.f817h) {
            if (C0455f.isServing) {
                this.f812c = (WifiManager) C0455f.getServiceContext().getSystemService("wifi");
                this.f813d = new C0453a();
                try {
                    C0455f.getServiceContext().registerReceiver(this.f813d, new IntentFilter("android.net.wifi.SCAN_RESULTS"));
                } catch (Exception e) {
                }
                this.f817h = true;
            }
        }
    }

    public synchronized void m955c() {
        if (this.f817h) {
            try {
                C0455f.getServiceContext().unregisterReceiver(this.f813d);
                f810a = 0;
            } catch (Exception e) {
            }
            this.f813d = null;
            this.f812c = null;
            this.f817h = false;
        }
    }

    public boolean m956d() {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - this.f816g > 0 && currentTimeMillis - this.f816g <= 5000) {
            return false;
        }
        this.f816g = currentTimeMillis;
        return m957e();
    }

    public boolean m957e() {
        if (this.f812c == null) {
            return false;
        }
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - this.f815f > 0) {
            if (currentTimeMillis - this.f815f <= 5000 || currentTimeMillis - (f810a * 1000) <= 5000) {
                return false;
            }
            if (C0454h.m952h() && currentTimeMillis - this.f815f <= 10000) {
                return false;
            }
        }
        return m959g();
    }

    public String m958f() {
        String str = "";
        if (this.f812c == null) {
            return str;
        }
        try {
            return (this.f812c.isWifiEnabled() || (VERSION.SDK_INT > 17 && this.f812c.isScanAlwaysAvailable())) ? "&wifio=1" : str;
        } catch (NoSuchMethodError e) {
            return str;
        } catch (Exception e2) {
            return str;
        }
    }

    @SuppressLint({"NewApi"})
    public boolean m959g() {
        try {
            if (!this.f812c.isWifiEnabled() && (VERSION.SDK_INT <= 17 || !this.f812c.isScanAlwaysAvailable())) {
                return false;
            }
            this.f812c.startScan();
            this.f815f = System.currentTimeMillis();
            return true;
        } catch (NoSuchMethodError e) {
            return false;
        } catch (Exception e2) {
            return false;
        }
    }

    public WifiInfo m960i() {
        if (this.f812c == null) {
            return null;
        }
        try {
            WifiInfo connectionInfo = this.f812c.getConnectionInfo();
            if (connectionInfo == null || connectionInfo.getBSSID() == null) {
                return null;
            }
            String bssid = connectionInfo.getBSSID();
            if (bssid != null) {
                bssid = bssid.replace(":", "");
                if ("000000000000".equals(bssid) || "".equals(bssid)) {
                    return null;
                }
            }
            return connectionInfo;
        } catch (Exception e) {
            return null;
        }
    }

    public String m961j() {
        StringBuffer stringBuffer = new StringBuffer();
        WifiInfo i = C0454h.m948a().m960i();
        if (i == null || i.getBSSID() == null) {
            return null;
        }
        String replace = i.getBSSID().replace(":", "");
        int rssi = i.getRssi();
        String k = C0454h.m948a().m962k();
        if (rssi < 0) {
            rssi = -rssi;
        }
        if (replace == null) {
            return null;
        }
        stringBuffer.append("&wf=");
        stringBuffer.append(replace);
        stringBuffer.append(";");
        stringBuffer.append("" + rssi + ";");
        stringBuffer.append(i.getSSID());
        stringBuffer.append("&wf_n=1");
        if (k != null) {
            stringBuffer.append("&wf_gt=");
            stringBuffer.append(k);
        }
        return stringBuffer.toString();
    }

    public String m962k() {
        if (this.f812c == null) {
            return null;
        }
        DhcpInfo dhcpInfo = this.f812c.getDhcpInfo();
        return dhcpInfo != null ? m949a((long) dhcpInfo.gateway) : null;
    }

    public C0451g m963l() {
        return (this.f814e == null || !this.f814e.m945f()) ? m965n() : this.f814e;
    }

    public C0451g m964m() {
        return (this.f814e == null || !this.f814e.m946g()) ? m965n() : this.f814e;
    }

    public C0451g m965n() {
        if (this.f812c != null) {
            try {
                return new C0451g(this.f812c.getScanResults(), this.f815f);
            } catch (Exception e) {
            }
        }
        return new C0451g(null, 0);
    }

    public void m966o() {
    }

    public String m967p() {
        String str = null;
        try {
            WifiInfo connectionInfo = this.f812c.getConnectionInfo();
            if (connectionInfo != null) {
                str = connectionInfo.getMacAddress();
            }
        } catch (Exception e) {
        }
        return str;
    }
}
