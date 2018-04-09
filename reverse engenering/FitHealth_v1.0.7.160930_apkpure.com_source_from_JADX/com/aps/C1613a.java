package com.aps;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.internal.view.SupportMenu;
import android.telephony.CellLocation;
import android.telephony.NeighboringCellInfo;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.C0193d;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.core.AMapLocException;
import com.amap.api.location.core.C0188c;
import com.amap.api.location.core.C0189d;
import com.facebook.internal.ServerProtocol;
import com.zhuoyi.system.util.constant.SeparatorConstants;
import com.zhuoyou.plugin.download.Util_update.TimeManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;
import org.json.JSONObject;
import p031u.aly.au;

/* compiled from: APS */
public class C1613a implements C0455k {
    private long f4417A = 0;
    private C0456l f4418B = C0456l.m1967a();
    private int f4419C = 0;
    private String f4420D = "00:00:00:00:00:00";
    private C0475y f4421E = null;
    private StringBuilder f4422F = new StringBuilder();
    private long f4423G = 0;
    private long f4424H = 0;
    private CellLocation f4425I = null;
    private String f4426J = null;
    private String f4427K = null;
    private boolean f4428L = false;
    long f4429a = 0;
    TimerTask f4430b;
    Timer f4431c;
    ae f4432d;
    int f4433e = 0;
    private Context f4434f = null;
    private int f4435g = 9;
    private ConnectivityManager f4436h = null;
    private WifiManager f4437i = null;
    private TelephonyManager f4438j = null;
    private List<C0445e> f4439k = new ArrayList();
    private List<ScanResult> f4440l = new ArrayList();
    private Map<PendingIntent, List<C0454j>> f4441m = new HashMap();
    private Map<PendingIntent, List<C0454j>> f4442n = new HashMap();
    private C0441b f4443o = new C0441b();
    private PhoneStateListener f4444p = null;
    private int f4445q = -113;
    private C0440a f4446r = new C0440a();
    private WifiInfo f4447s = null;
    private JSONObject f4448t = null;
    private String f4449u = null;
    private C0442c f4450v = null;
    private long f4451w = 0;
    private boolean f4452x = false;
    private long f4453y = 0;
    private long f4454z = 0;

    /* compiled from: APS */
    class C04381 extends PhoneStateListener {
        final /* synthetic */ C1613a f1684a;

        C04381(C1613a c1613a) {
            this.f1684a = c1613a;
        }

        public void onCellLocationChanged(CellLocation cellLocation) {
            if (cellLocation != null) {
                try {
                    if (!this.f1684a.m4547p()) {
                        this.f1684a.f4425I = cellLocation;
                        this.f1684a.f4453y = C0470t.m2006a();
                    }
                } catch (Throwable th) {
                    th.printStackTrace();
                }
            }
        }

        public void onSignalStrengthChanged(int i) {
            int i2 = -113;
            try {
                switch (this.f1684a.f4435g) {
                    case 1:
                        i2 = C0470t.m2004a(i);
                        break;
                    case 2:
                        i2 = C0470t.m2004a(i);
                        break;
                }
                this.f1684a.m4524b(i2);
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }

        public void onSignalStrengthsChanged(SignalStrength signalStrength) {
            int i = -113;
            try {
                switch (this.f1684a.f4435g) {
                    case 1:
                        i = C0470t.m2004a(signalStrength.getGsmSignalStrength());
                        break;
                    case 2:
                        i = signalStrength.getCdmaDbm();
                        break;
                }
                this.f1684a.m4524b(i);
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }

        public void onServiceStateChanged(ServiceState serviceState) {
            try {
                switch (serviceState.getState()) {
                    case 1:
                        this.f1684a.f4439k.clear();
                        this.f1684a.f4445q = -113;
                        return;
                    default:
                        return;
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    /* compiled from: APS */
    private class C0440a extends BroadcastReceiver {
        final /* synthetic */ C1613a f1687a;

        private C0440a(C1613a c1613a) {
            this.f1687a = c1613a;
        }

        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                try {
                    String action = intent.getAction();
                    if (action.equals("android.net.wifi.SCAN_RESULTS")) {
                        if (this.f1687a.f4437i != null) {
                            this.f1687a.f4440l = this.f1687a.f4437i.getScanResults();
                            if (this.f1687a.f4440l == null) {
                                this.f1687a.f4440l = new ArrayList();
                            }
                        }
                    } else if (action.equals("android.net.wifi.WIFI_STATE_CHANGED")) {
                        if (this.f1687a.f4437i != null) {
                            r0 = 4;
                            try {
                                r0 = this.f1687a.f4437i.getWifiState();
                            } catch (SecurityException e) {
                            }
                            switch (r0) {
                                case 0:
                                    this.f1687a.m4545n();
                                    return;
                                case 1:
                                    this.f1687a.m4545n();
                                    return;
                                case 4:
                                    this.f1687a.m4545n();
                                    return;
                                default:
                                    return;
                            }
                            th.printStackTrace();
                        }
                    } else if (action.equals("android.intent.action.SCREEN_ON")) {
                        CellLocation.requestLocationUpdate();
                        this.f1687a.m4546o();
                        C0446f.f1855i = 10000;
                        C0446f.f1856j = 30000;
                    } else if (action.equals("android.intent.action.SCREEN_OFF")) {
                        if (this.f1687a.f4419C >= 5) {
                            C0446f.f1855i = 20000;
                            C0446f.f1856j = TimeManager.UNIT_MINUTE;
                        }
                    } else if (action.equals("android.intent.action.AIRPLANE_MODE")) {
                        this.f1687a.f4452x = C0470t.m2011a(context);
                    } else if (action.equals("android.intent.action.BATTERY_CHANGED")) {
                        r0 = intent.getIntExtra(LogColumns.LEVEL, 0);
                        int intExtra = intent.getIntExtra("scale", 100);
                        int intExtra2 = intent.getIntExtra("status", 0);
                        C0470t.m2010a("batt is ", Integer.valueOf((r0 * 100) / intExtra), "%");
                        switch (intExtra2) {
                            case 4:
                                if (r0 < 15 && this.f1687a.f4419C >= 5) {
                                    return;
                                }
                                return;
                            default:
                                return;
                        }
                    } else if (action.equals("android.location.GPS_FIX_CHANGE")) {
                        this.f1687a.m4567c();
                    } else if (action.equals("android.net.conn.CONNECTIVITY_CHANGE")) {
                        if (this.f1687a.m4544m()) {
                            this.f1687a.m4556a(true, 2);
                        }
                    } else if (!action.equals("android.location.PROVIDERS_CHANGED")) {
                    }
                } catch (Throwable th) {
                    th.printStackTrace();
                }
            }
        }
    }

    public void mo1795a(Context context, C0193d c0193d) {
        if (context != null && this.f4434f == null) {
            this.f4434f = context.getApplicationContext();
            C0470t.m2007a(this.f4434f, "in debug mode, only for test");
            m4533f();
            m4535g();
            this.f4423G = System.currentTimeMillis();
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized com.aps.C0442c mo1793a() throws java.lang.Exception {
        /*
        r10 = this;
        r2 = 0;
        r1 = 1;
        monitor-enter(r10);
        r0 = r10.f4434f;	 Catch:{ all -> 0x000f }
        if (r0 != 0) goto L_0x0012;
    L_0x0007:
        r0 = new com.amap.api.location.core.AMapLocException;	 Catch:{ all -> 0x000f }
        r1 = "未知的错误";
        r0.<init>(r1);	 Catch:{ all -> 0x000f }
        throw r0;	 Catch:{ all -> 0x000f }
    L_0x000f:
        r0 = move-exception;
        monitor-exit(r10);
        throw r0;
    L_0x0012:
        r0 = com.aps.C0446f.f1850d;	 Catch:{ all -> 0x000f }
        r0 = android.text.TextUtils.isEmpty(r0);	 Catch:{ all -> 0x000f }
        if (r0 == 0) goto L_0x0022;
    L_0x001a:
        r0 = new com.amap.api.location.core.AMapLocException;	 Catch:{ all -> 0x000f }
        r1 = "key鉴权失败";
        r0.<init>(r1);	 Catch:{ all -> 0x000f }
        throw r0;	 Catch:{ all -> 0x000f }
    L_0x0022:
        r0 = com.aps.C0446f.f1851e;	 Catch:{ all -> 0x000f }
        r0 = android.text.TextUtils.isEmpty(r0);	 Catch:{ all -> 0x000f }
        if (r0 == 0) goto L_0x0032;
    L_0x002a:
        r0 = new com.amap.api.location.core.AMapLocException;	 Catch:{ all -> 0x000f }
        r1 = "key鉴权失败";
        r0.<init>(r1);	 Catch:{ all -> 0x000f }
        throw r0;	 Catch:{ all -> 0x000f }
    L_0x0032:
        r0 = r10.f4448t;	 Catch:{ all -> 0x000f }
        r0 = com.aps.C0456l.m1972a(r0);	 Catch:{ all -> 0x000f }
        r3 = "false";
        r4 = 0;
        r0 = r0[r4];	 Catch:{ all -> 0x000f }
        r0 = r3.equals(r0);	 Catch:{ all -> 0x000f }
        if (r0 == 0) goto L_0x0052;
    L_0x0043:
        r0 = "AuthLocation";
        r1 = "key鉴权失败";
        android.util.Log.e(r0, r1);	 Catch:{ all -> 0x000f }
        r0 = new com.amap.api.location.core.AMapLocException;	 Catch:{ all -> 0x000f }
        r1 = "key鉴权失败";
        r0.<init>(r1);	 Catch:{ all -> 0x000f }
        throw r0;	 Catch:{ all -> 0x000f }
    L_0x0052:
        r0 = r10.m4542k();	 Catch:{ all -> 0x000f }
        if (r0 == 0) goto L_0x0061;
    L_0x0058:
        android.telephony.CellLocation.requestLocationUpdate();	 Catch:{ all -> 0x000f }
        r4 = com.aps.C0470t.m2006a();	 Catch:{ all -> 0x000f }
        r10.f4454z = r4;	 Catch:{ all -> 0x000f }
    L_0x0061:
        r0 = r10.m4543l();	 Catch:{ all -> 0x000f }
        if (r0 == 0) goto L_0x006a;
    L_0x0067:
        r10.m4546o();	 Catch:{ all -> 0x000f }
    L_0x006a:
        r0 = r10.f4419C;	 Catch:{ all -> 0x000f }
        r0 = r0 + 1;
        r10.f4419C = r0;	 Catch:{ all -> 0x000f }
        r0 = r10.f4419C;	 Catch:{ all -> 0x000f }
        if (r0 <= r1) goto L_0x0077;
    L_0x0074:
        r10.m4567c();	 Catch:{ all -> 0x000f }
    L_0x0077:
        r0 = r10.f4419C;	 Catch:{ all -> 0x000f }
        if (r0 != r1) goto L_0x0095;
    L_0x007b:
        r4 = java.lang.System.currentTimeMillis();	 Catch:{ all -> 0x000f }
        r10.f4424H = r4;	 Catch:{ all -> 0x000f }
        r0 = r10.f4434f;	 Catch:{ all -> 0x000f }
        r0 = com.aps.C0470t.m2011a(r0);	 Catch:{ all -> 0x000f }
        r10.f4452x = r0;	 Catch:{ all -> 0x000f }
        r0 = r10.f4437i;	 Catch:{ all -> 0x000f }
        if (r0 == 0) goto L_0x0095;
    L_0x008d:
        r0 = r10.f4437i;	 Catch:{ all -> 0x000f }
        r0 = r0.getScanResults();	 Catch:{ all -> 0x000f }
        r10.f4440l = r0;	 Catch:{ all -> 0x000f }
    L_0x0095:
        r0 = r10.f4440l;	 Catch:{ all -> 0x000f }
        if (r0 != 0) goto L_0x00a0;
    L_0x0099:
        r0 = new java.util.ArrayList;	 Catch:{ all -> 0x000f }
        r0.<init>();	 Catch:{ all -> 0x000f }
        r10.f4440l = r0;	 Catch:{ all -> 0x000f }
    L_0x00a0:
        r0 = r10.f4419C;	 Catch:{ all -> 0x000f }
        if (r0 != r1) goto L_0x00c8;
    L_0x00a4:
        r0 = r10.m4550s();	 Catch:{ all -> 0x000f }
        if (r0 == 0) goto L_0x00c8;
    L_0x00aa:
        r4 = r10.f4424H;	 Catch:{ all -> 0x000f }
        r6 = r10.f4423G;	 Catch:{ all -> 0x000f }
        r4 = r4 - r6;
        r6 = 2000; // 0x7d0 float:2.803E-42 double:9.88E-321;
        r0 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1));
        if (r0 >= 0) goto L_0x00c8;
    L_0x00b5:
        r0 = 4;
    L_0x00b6:
        if (r0 <= 0) goto L_0x00c8;
    L_0x00b8:
        r3 = r10.f4440l;	 Catch:{ all -> 0x000f }
        r3 = r3.size();	 Catch:{ all -> 0x000f }
        if (r3 != 0) goto L_0x00c8;
    L_0x00c0:
        r4 = 500; // 0x1f4 float:7.0E-43 double:2.47E-321;
        android.os.SystemClock.sleep(r4);	 Catch:{ all -> 0x000f }
        r0 = r0 + -1;
        goto L_0x00b6;
    L_0x00c8:
        r4 = r10.f4451w;	 Catch:{ all -> 0x000f }
        r0 = r10.m4515a(r4);	 Catch:{ all -> 0x000f }
        if (r0 == 0) goto L_0x00de;
    L_0x00d0:
        r0 = r10.f4450v;	 Catch:{ all -> 0x000f }
        if (r0 == 0) goto L_0x00de;
    L_0x00d4:
        r0 = com.aps.C0470t.m2006a();	 Catch:{ all -> 0x000f }
        r10.f4451w = r0;	 Catch:{ all -> 0x000f }
        r0 = r10.f4450v;	 Catch:{ all -> 0x000f }
    L_0x00dc:
        monitor-exit(r10);
        return r0;
    L_0x00de:
        r0 = r10.f4425I;	 Catch:{ all -> 0x000f }
        r10.m4510a(r0);	 Catch:{ all -> 0x000f }
        r0 = r10.f4440l;	 Catch:{ all -> 0x000f }
        r10.m4513a(r0);	 Catch:{ all -> 0x000f }
        r3 = r10.m4538h();	 Catch:{ all -> 0x000f }
        r0 = android.text.TextUtils.isEmpty(r3);	 Catch:{ all -> 0x000f }
        if (r0 == 0) goto L_0x00f4;
    L_0x00f2:
        r0 = 0;
        goto L_0x00dc;
    L_0x00f4:
        r4 = r10.m4539i();	 Catch:{ all -> 0x000f }
        r0 = r10.f4434f;	 Catch:{ all -> 0x000f }
        r0 = com.aps.C0444d.m1892a(r0);	 Catch:{ all -> 0x000f }
        r5 = "mem";
        r0 = r0.m1897a(r3, r4, r5);	 Catch:{ all -> 0x000f }
        if (r0 == 0) goto L_0x0150;
    L_0x0106:
        r6 = r0.m1870g();	 Catch:{ all -> 0x000f }
        r8 = com.aps.C0470t.m2006a();	 Catch:{ all -> 0x000f }
        r6 = r8 - r6;
        r8 = 3600000; // 0x36ee80 float:5.044674E-39 double:1.7786363E-317;
        r5 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1));
        if (r5 <= 0) goto L_0x0150;
    L_0x0117:
        if (r0 == 0) goto L_0x011b;
    L_0x0119:
        if (r1 == 0) goto L_0x014d;
    L_0x011b:
        r0 = r10.m4551t();	 Catch:{ AMapLocException -> 0x0149 }
    L_0x011f:
        r10.f4450v = r0;	 Catch:{ all -> 0x000f }
    L_0x0121:
        r0 = r10.f4434f;	 Catch:{ all -> 0x000f }
        r0 = com.aps.C0444d.m1892a(r0);	 Catch:{ all -> 0x000f }
        r1 = r10.f4450v;	 Catch:{ all -> 0x000f }
        r0.m1899a(r3, r1, r4);	 Catch:{ all -> 0x000f }
        r0 = 0;
        r1 = r4.length();	 Catch:{ all -> 0x000f }
        r4.delete(r0, r1);	 Catch:{ all -> 0x000f }
        r0 = com.aps.C0470t.m2006a();	 Catch:{ all -> 0x000f }
        r10.f4451w = r0;	 Catch:{ all -> 0x000f }
        r10.m4548q();	 Catch:{ all -> 0x000f }
        r10.m4568d();	 Catch:{ all -> 0x000f }
        r0 = com.aps.C0470t.m2006a();	 Catch:{ all -> 0x000f }
        r10.f4429a = r0;	 Catch:{ all -> 0x000f }
        r0 = r10.f4450v;	 Catch:{ all -> 0x000f }
        goto L_0x00dc;
    L_0x0149:
        r1 = move-exception;
        if (r0 != 0) goto L_0x011f;
    L_0x014c:
        throw r1;	 Catch:{ all -> 0x000f }
    L_0x014d:
        r10.f4450v = r0;	 Catch:{ all -> 0x000f }
        goto L_0x0121;
    L_0x0150:
        r1 = r2;
        goto L_0x0117;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aps.a.a():com.aps.c");
    }

    public void mo1798a(String str) {
        if (str != null && str.indexOf("##") != -1) {
            String[] split = str.split("##");
            if (split.length == 3) {
                C0446f.m1901a(split[0]);
                if (!C0446f.f1851e.equals(split[1])) {
                    C0444d.m1892a(this.f4434f).m1898a();
                }
                C0446f.m1903b(split[1]);
                C0446f.m1904c(split[2]);
            }
        }
    }

    public void mo1799a(JSONObject jSONObject) {
        this.f4448t = jSONObject;
    }

    public void mo1797a(C0454j c0454j, PendingIntent pendingIntent) {
        if (pendingIntent != null && c0454j != null) {
            long a = c0454j.m1953a();
            if (a != -1 && a < C0470t.m2006a()) {
                return;
            }
            List list;
            if (this.f4441m.get(pendingIntent) != null) {
                list = (List) this.f4441m.get(pendingIntent);
                list.add(c0454j);
                this.f4441m.put(pendingIntent, list);
                return;
            }
            list = new ArrayList();
            list.add(c0454j);
            this.f4441m.put(pendingIntent, list);
        }
    }

    public void mo1802b(C0454j c0454j, PendingIntent pendingIntent) {
        if (pendingIntent != null && c0454j != null) {
            long a = c0454j.m1953a();
            if (a != -1 && a < C0470t.m2006a()) {
                return;
            }
            List list;
            if (this.f4442n.get(pendingIntent) != null) {
                list = (List) this.f4442n.get(pendingIntent);
                list.add(c0454j);
                this.f4442n.put(pendingIntent, list);
                return;
            }
            list = new ArrayList();
            list.add(c0454j);
            this.f4442n.put(pendingIntent, list);
        }
    }

    public void mo1794a(PendingIntent pendingIntent) {
        if (pendingIntent != null) {
            this.f4441m.remove(pendingIntent);
        }
    }

    public void mo1801b(PendingIntent pendingIntent) {
        if (pendingIntent != null) {
            this.f4442n.remove(pendingIntent);
        }
    }

    public void mo1800b() {
        try {
            if (this.f4421E != null) {
                this.f4421E.m2073c();
                this.f4428L = false;
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
        try {
            if (this.f4434f != null) {
                this.f4434f.unregisterReceiver(this.f4446r);
            }
        } catch (Throwable th2) {
            this.f4446r = null;
        }
        this.f4446r = null;
        m4553v();
        try {
            if (!(this.f4438j == null || this.f4444p == null)) {
                this.f4438j.listen(this.f4444p, 0);
            }
        } catch (Throwable th3) {
            th3.printStackTrace();
            C0470t.m2008a(th3);
        }
        C0444d.m1892a(this.f4434f).m1898a();
        C0446f.m1902a(false);
        this.f4451w = 0;
        this.f4439k.clear();
        this.f4441m.clear();
        this.f4442n.clear();
        this.f4445q = -113;
        m4545n();
        this.f4449u = null;
        this.f4450v = null;
        this.f4434f = null;
        this.f4438j = null;
    }

    private void m4533f() {
        this.f4437i = (WifiManager) C0470t.m2017b(this.f4434f, "wifi");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        intentFilter.addAction("android.net.wifi.SCAN_RESULTS");
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.AIRPLANE_MODE");
        intentFilter.addAction("android.intent.action.BATTERY_CHANGED");
        intentFilter.addAction("android.location.GPS_FIX_CHANGE");
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        intentFilter.addAction("android.location.PROVIDERS_CHANGED");
        this.f4434f.registerReceiver(this.f4446r, intentFilter);
        m4546o();
    }

    private void m4535g() {
        int i;
        this.f4436h = (ConnectivityManager) C0470t.m2017b(this.f4434f, "connectivity");
        CellLocation.requestLocationUpdate();
        this.f4454z = C0470t.m2006a();
        this.f4438j = (TelephonyManager) C0470t.m2017b(this.f4434f, "phone");
        switch (this.f4438j.getPhoneType()) {
            case 1:
                this.f4435g = 1;
                break;
            case 2:
                this.f4435g = 2;
                break;
            default:
                this.f4435g = 9;
                break;
        }
        this.f4444p = new C04381(this);
        if (C0470t.m2015b() < 7) {
            i = 2;
        } else {
            i = 256;
        }
        if (i == 0) {
            this.f4438j.listen(this.f4444p, 16);
            return;
        }
        try {
            this.f4438j.listen(this.f4444p, i | 16);
        } catch (Throwable e) {
            C0470t.m2008a(e);
        }
    }

    private void m4510a(CellLocation cellLocation) {
        CellLocation cellLocation2 = null;
        if (!(this.f4452x || this.f4438j == null)) {
            cellLocation2 = this.f4438j.getCellLocation();
        }
        if (cellLocation2 != null) {
            cellLocation = cellLocation2;
        }
        if (cellLocation != null) {
            switch (C0470t.m2005a(cellLocation, this.f4434f)) {
                case 1:
                    if (this.f4438j != null) {
                        m4527c(cellLocation);
                        return;
                    }
                    return;
                case 2:
                    m4531d(cellLocation);
                    return;
                default:
                    return;
            }
        }
    }

    private boolean m4515a(long j) {
        long a = C0470t.m2006a();
        if (a - j >= 300) {
            return false;
        }
        long j2 = 0;
        if (this.f4450v != null) {
            j2 = a - this.f4450v.m1870g();
        }
        if (j2 > 10000) {
            return false;
        }
        return true;
    }

    private String m4538h() {
        m4552u();
        String str = "";
        String str2 = "";
        str2 = LocationManagerProxy.NETWORK_PROVIDER;
        if (m4550s()) {
            this.f4447s = this.f4437i.getConnectionInfo();
        } else {
            m4545n();
        }
        String str3 = "";
        C0445e c0445e;
        StringBuilder stringBuilder;
        switch (this.f4435g) {
            case 1:
                if (this.f4439k.size() > 0) {
                    c0445e = (C0445e) this.f4439k.get(0);
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(c0445e.f1837a).append("#");
                    stringBuilder.append(c0445e.f1838b).append("#");
                    stringBuilder.append(c0445e.f1839c).append("#");
                    stringBuilder.append(c0445e.f1840d).append("#");
                    stringBuilder.append(str2).append("#");
                    if (this.f4440l.size() > 0) {
                        str = "cellwifi";
                    } else {
                        str = "cell";
                    }
                    stringBuilder.append(str);
                    return stringBuilder.toString();
                }
                break;
            case 2:
                if (this.f4439k.size() > 0) {
                    c0445e = (C0445e) this.f4439k.get(0);
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(c0445e.f1837a).append("#");
                    stringBuilder.append(c0445e.f1838b).append("#");
                    stringBuilder.append(c0445e.f1843g).append("#");
                    stringBuilder.append(c0445e.f1844h).append("#");
                    stringBuilder.append(c0445e.f1845i).append("#");
                    stringBuilder.append(str2).append("#");
                    if (this.f4440l.size() > 0) {
                        str = "cellwifi";
                    } else {
                        str = "cell";
                    }
                    stringBuilder.append(str);
                    return stringBuilder.toString();
                }
                break;
            case 9:
                str2 = String.format("#%s#", new Object[]{str2});
                if ((this.f4440l.size() == 1 && !m4517a(this.f4447s)) || this.f4440l.size() == 0) {
                    return null;
                }
                if (this.f4440l.size() != 1 || !m4517a(this.f4447s)) {
                    return str2 + "wifi";
                }
                ScanResult scanResult = (ScanResult) this.f4440l.get(0);
                if (scanResult == null || !this.f4447s.getBSSID().equals(scanResult.BSSID)) {
                    str = str2;
                } else {
                    str = null;
                }
                return str;
        }
        return str;
    }

    private boolean m4517a(WifiInfo wifiInfo) {
        if (wifiInfo == null || wifiInfo.getBSSID() == null || wifiInfo.getSSID() == null || wifiInfo.getBSSID().equals("00:00:00:00:00:00") || TextUtils.isEmpty(wifiInfo.getSSID())) {
            return false;
        }
        return true;
    }

    private boolean m4516a(ScanResult scanResult) {
        boolean z = false;
        if (scanResult != null) {
            try {
                if (!(TextUtils.isEmpty(scanResult.BSSID) || scanResult.BSSID.equals("00:00:00:00:00:00"))) {
                    z = true;
                }
            } catch (Exception e) {
                return true;
            }
        }
        return z;
    }

    private StringBuilder m4539i() {
        m4552u();
        StringBuilder stringBuilder = new StringBuilder(700);
        switch (this.f4435g) {
            case 1:
                for (int i = 0; i < this.f4439k.size(); i++) {
                    if (i != 0) {
                        C0445e c0445e = (C0445e) this.f4439k.get(i);
                        stringBuilder.append("#").append(c0445e.f1838b);
                        stringBuilder.append("|").append(c0445e.f1839c);
                        stringBuilder.append("|").append(c0445e.f1840d);
                    }
                }
                break;
        }
        if ((this.f4420D == null || this.f4420D.equals("00:00:00:00:00:00")) && this.f4447s != null) {
            this.f4420D = this.f4447s.getMacAddress();
            if (this.f4420D == null) {
                this.f4420D = "00:00:00:00:00:00";
            }
        }
        if (m4550s()) {
            String bssid;
            String str = "";
            if (m4517a(this.f4447s)) {
                bssid = this.f4447s.getBSSID();
            } else {
                bssid = str;
            }
            int i2 = 0;
            for (int i3 = 0; i3 < this.f4440l.size(); i3++) {
                ScanResult scanResult = (ScanResult) this.f4440l.get(i3);
                if (m4516a(scanResult)) {
                    String str2 = scanResult.BSSID;
                    str = "nb";
                    if (bssid.equals(str2)) {
                        str = au.f3554I;
                        i2 = 1;
                    }
                    stringBuilder.append(String.format("#%s,%s", new Object[]{str2, str}));
                }
            }
            if (i2 == 0 && bssid.length() > 0) {
                stringBuilder.append("#").append(bssid);
                stringBuilder.append(",access");
            }
        } else {
            m4545n();
        }
        if (stringBuilder.length() > 0) {
            stringBuilder.deleteCharAt(0);
        }
        return stringBuilder;
    }

    private synchronized byte[] m4520a(Object obj) {
        C0462o c0462o;
        String str;
        StringBuilder stringBuilder;
        c0462o = new C0462o();
        this.f4422F.delete(0, this.f4422F.length());
        String str2 = "0";
        String str3 = "0";
        String str4 = "0";
        String str5 = "0";
        String str6 = "0";
        String str7 = "";
        C0446f.f1847a = "888888888888888";
        C0446f.f1848b = "888888888888888";
        C0446f.f1849c = "";
        String str8 = "";
        String str9 = "";
        String str10 = "";
        StringBuilder stringBuilder2 = new StringBuilder();
        StringBuilder stringBuilder3 = new StringBuilder();
        StringBuilder stringBuilder4 = new StringBuilder();
        if (this.f4435g == 2) {
            str = "1";
        } else {
            str = str2;
        }
        if (this.f4438j != null) {
            if (this.f4426J == null && C0446f.f1847a.equals("888888888888888")) {
                try {
                    C0446f.f1847a = this.f4438j.getDeviceId();
                } catch (SecurityException e) {
                }
            }
            if (C0446f.f1847a == null) {
                C0446f.f1847a = "888888888888888";
            }
            this.f4426J = C0446f.f1847a;
            if ((this.f4427K == null && C0446f.f1848b == null) || C0446f.f1848b.equals("888888888888888")) {
                C0446f.f1848b = "888888888888888";
                try {
                    if (this.f4438j != null) {
                        C0446f.f1848b = this.f4438j.getSubscriberId();
                    }
                } catch (SecurityException e2) {
                }
            }
            if (C0446f.f1848b == null) {
                C0446f.f1848b = "888888888888888";
            }
            this.f4427K = C0446f.f1848b;
            if (TextUtils.isEmpty(C0446f.f1849c)) {
                C0446f.f1849c = "";
            }
            if (C0446f.f1849c == null) {
                C0446f.f1849c = "";
            }
        }
        NetworkInfo networkInfo = null;
        try {
            networkInfo = this.f4436h.getActiveNetworkInfo();
        } catch (SecurityException e3) {
        }
        if (C0456l.m1966a(networkInfo) != -1) {
            str8 = C0456l.m1968a(this.f4438j);
            if (m4550s()) {
                if (m4517a(this.f4447s)) {
                    str9 = "2";
                }
            }
            str9 = "1";
            if (!m4550s()) {
                m4545n();
            }
        } else {
            this.f4447s = null;
        }
        String[] a = C0456l.m1972a(this.f4448t);
        if (a[0].equals(ServerProtocol.DIALOG_RETURN_SCOPES_TRUE)) {
            str7 = a[1];
        }
        c0462o.f1913i = str;
        c0462o.f1914j = str3;
        c0462o.f1916l = str4;
        c0462o.f1917m = str5;
        c0462o.f1918n = str6;
        c0462o.f1907c = C0446f.f1850d;
        c0462o.f1908d = C0446f.f1851e;
        c0462o.f1919o = str7;
        c0462o.f1920p = this.f4426J;
        c0462o.f1923s = C0446f.f1849c;
        c0462o.f1921q = this.f4427K;
        c0462o.f1930z = this.f4420D;
        c0462o.f1924t = str8;
        c0462o.f1925u = str9;
        c0462o.f1910f = C0188c.m92e();
        c0462o.f1911g = "android" + C0188c.m91d();
        c0462o.f1912h = C0188c.m93g();
        this.f4422F.append("<?xml version=\"1.0\" encoding=\"");
        this.f4422F.append("GBK").append("\"?>");
        this.f4422F.append("<Cell_Req ver=\"3.0\"><HDR version=\"3.0\" cdma=\"");
        this.f4422F.append(str);
        this.f4422F.append("\" gtype=\"").append(str3);
        this.f4422F.append("\" glong=\"").append(str4);
        this.f4422F.append("\" glat=\"").append(str5);
        this.f4422F.append("\" precision=\"").append(str6);
        this.f4422F.append("\"><src>").append(C0446f.f1850d);
        this.f4422F.append("</src><license>").append(C0446f.f1851e);
        this.f4422F.append("</license><key>").append(str7);
        this.f4422F.append("</key><clientid>").append(C0446f.f1852f);
        this.f4422F.append("</clientid><imei>").append(C0446f.f1847a);
        this.f4422F.append("</imei><imsi>").append(C0446f.f1848b);
        this.f4422F.append("</imsi><smac>").append(this.f4420D);
        this.f4422F.append("</smac></HDR><DRR phnum=\"").append(C0446f.f1849c);
        this.f4422F.append("\" nettype=\"").append(str8);
        this.f4422F.append("\" inftype=\"").append(str9).append("\">");
        if (this.f4439k.size() > 0) {
            StringBuilder stringBuilder5 = new StringBuilder();
            C0445e c0445e;
            switch (this.f4435g) {
                case 1:
                    c0445e = (C0445e) this.f4439k.get(0);
                    stringBuilder5.delete(0, stringBuilder5.length());
                    stringBuilder5.append("<mcc>").append(c0445e.f1837a).append("</mcc>");
                    stringBuilder5.append("<mnc>").append(c0445e.f1838b).append("</mnc>");
                    stringBuilder5.append("<lac>").append(c0445e.f1839c).append("</lac>");
                    stringBuilder5.append("<cellid>").append(c0445e.f1840d);
                    stringBuilder5.append("</cellid>");
                    stringBuilder5.append("<signal>").append(c0445e.f1846j);
                    stringBuilder5.append("</signal>");
                    str7 = stringBuilder5.toString();
                    for (int i = 0; i < this.f4439k.size(); i++) {
                        if (i != 0) {
                            c0445e = (C0445e) this.f4439k.get(i);
                            stringBuilder2.append(c0445e.f1839c).append(SeparatorConstants.SEPARATOR_ADS_ID);
                            stringBuilder2.append(c0445e.f1840d).append(SeparatorConstants.SEPARATOR_ADS_ID);
                            stringBuilder2.append(c0445e.f1846j);
                            if (i != this.f4439k.size() - 1) {
                                stringBuilder2.append("*");
                            }
                        }
                    }
                    str10 = str7;
                    break;
                case 2:
                    c0445e = (C0445e) this.f4439k.get(0);
                    stringBuilder5.delete(0, stringBuilder5.length());
                    stringBuilder5.append("<mcc>").append(c0445e.f1837a).append("</mcc>");
                    stringBuilder5.append("<sid>").append(c0445e.f1843g).append("</sid>");
                    stringBuilder5.append("<nid>").append(c0445e.f1844h).append("</nid>");
                    stringBuilder5.append("<bid>").append(c0445e.f1845i).append("</bid>");
                    if (c0445e.f1842f > 0 && c0445e.f1841e > 0) {
                        stringBuilder5.append("<lon>").append(c0445e.f1842f).append("</lon>");
                        stringBuilder5.append("<lat>").append(c0445e.f1841e).append("</lat>");
                    }
                    stringBuilder5.append("<signal>").append(c0445e.f1846j).append("</signal>");
                    str10 = stringBuilder5.toString();
                    break;
            }
            stringBuilder5.delete(0, stringBuilder5.length());
            str9 = str10;
        } else {
            str9 = str10;
        }
        if (m4550s()) {
            if (m4517a(this.f4447s)) {
                stringBuilder4.append(this.f4447s.getBSSID()).append(SeparatorConstants.SEPARATOR_ADS_ID);
                stringBuilder4.append(this.f4447s.getRssi()).append(SeparatorConstants.SEPARATOR_ADS_ID);
                stringBuilder4.append(this.f4447s.getSSID().replace("*", "."));
            }
            for (int i2 = 0; i2 < this.f4440l.size(); i2++) {
                ScanResult scanResult = (ScanResult) this.f4440l.get(i2);
                if (m4516a(scanResult)) {
                    stringBuilder3.append(scanResult.BSSID).append(SeparatorConstants.SEPARATOR_ADS_ID);
                    stringBuilder3.append(scanResult.level).append(SeparatorConstants.SEPARATOR_ADS_ID);
                    stringBuilder3.append(i2).append("*");
                }
            }
        } else {
            m4545n();
        }
        this.f4422F.append(str9);
        this.f4422F.append(String.format("<nb>%s</nb>", new Object[]{stringBuilder2}));
        if (stringBuilder3.length() == 0) {
            this.f4422F.append(String.format("<macs><![CDATA[%s]]></macs>", new Object[]{stringBuilder4}));
        } else {
            stringBuilder3.deleteCharAt(stringBuilder3.length() - 1);
            this.f4422F.append(String.format("<macs><![CDATA[%s]]></macs>", new Object[]{stringBuilder3}));
        }
        this.f4422F.append(String.format("<mmac><![CDATA[%s]]></mmac>", new Object[]{stringBuilder4}));
        this.f4422F.append("</DRR></Cell_Req>");
        m4512a(this.f4422F);
        if (stringBuilder3.length() == 0) {
            stringBuilder = stringBuilder4;
        } else {
            stringBuilder = stringBuilder3;
        }
        c0462o.f1927w = str9;
        c0462o.f1928x = stringBuilder2.toString();
        c0462o.f1930z = stringBuilder4.toString();
        c0462o.f1929y = stringBuilder.toString();
        c0462o.f1926v = String.valueOf(this.f4435g);
        stringBuilder2.delete(0, stringBuilder2.length());
        stringBuilder.delete(0, stringBuilder.length());
        stringBuilder4.delete(0, stringBuilder4.length());
        return c0462o.m1984a();
    }

    private synchronized void m4513a(List<ScanResult> list) {
        if (list != null) {
            if (list.size() >= 1) {
                Object hashMap = new HashMap();
                for (int i = 0; i < list.size(); i++) {
                    ScanResult scanResult = (ScanResult) list.get(i);
                    if (list.size() <= 20 || m4514a(scanResult.level)) {
                        if (scanResult.SSID != null) {
                            scanResult.SSID = scanResult.SSID.replace("*", ".");
                        } else {
                            scanResult.SSID = "null";
                        }
                        hashMap.put(Integer.valueOf((scanResult.level * 30) + i), scanResult);
                    }
                }
                TreeMap treeMap = new TreeMap(Collections.reverseOrder());
                treeMap.putAll(hashMap);
                list.clear();
                for (Entry value : treeMap.entrySet()) {
                    list.add(value.getValue());
                    if (list.size() > 29) {
                        break;
                    }
                }
                hashMap.clear();
                treeMap.clear();
            }
        }
    }

    private boolean m4514a(int i) {
        int i2 = 20;
        try {
            i2 = WifiManager.calculateSignalLevel(i, 20);
        } catch (Throwable e) {
            C0470t.m2008a(e);
        }
        if (i2 >= 1) {
            return true;
        }
        return false;
    }

    private synchronized byte[] m4541j() {
        if (m4542k()) {
            CellLocation.requestLocationUpdate();
            this.f4454z = C0470t.m2006a();
        }
        if (m4543l()) {
            m4546o();
        }
        return m4520a(null);
    }

    private boolean m4542k() {
        if (this.f4452x || this.f4454z == 0 || C0470t.m2006a() - this.f4454z < C0446f.f1856j) {
            return false;
        }
        return true;
    }

    private boolean m4543l() {
        if (m4550s() && this.f4417A != 0 && C0470t.m2006a() - this.f4417A >= C0446f.f1855i) {
            return true;
        }
        return false;
    }

    private boolean m4544m() {
        if (this.f4437i == null || !m4550s()) {
            return false;
        }
        NetworkInfo networkInfo = null;
        try {
            if (this.f4436h != null) {
                networkInfo = this.f4436h.getActiveNetworkInfo();
            }
            if (C0456l.m1966a(networkInfo) == -1 || !m4517a(this.f4437i.getConnectionInfo())) {
                return false;
            }
            return true;
        } catch (SecurityException e) {
            return false;
        } catch (Throwable th) {
            th.printStackTrace();
            return false;
        }
    }

    private C0442c m4506a(byte[] bArr, boolean z) throws Exception {
        if (this.f4434f == null) {
            return null;
        }
        C0460m c0460m = new C0460m();
        String a = this.f4418B.m1976a(bArr, this.f4434f, this.f4448t);
        try {
            C0189d.m107a(a);
        } catch (AMapLocException e) {
            throw e;
        } catch (Exception e2) {
        }
        String str = "";
        String[] a2 = C0456l.m1972a(this.f4448t);
        if (a != null && a.indexOf("<saps>") != -1) {
            a = this.f4443o.m1839a(c0460m.m1977a(a), "GBK");
        } else if (a2[0].equals(ServerProtocol.DIALOG_RETURN_SCOPES_TRUE)) {
            C0470t.m2010a("api return pure");
        } else {
            C0470t.m2010a("aps return pure");
        }
        C0442c b = c0460m.m1978b(a);
        if (C0470t.m2012a(b)) {
            if (b.m1882m() != null) {
            }
            if (this.f4422F == null || this.f4422F.length() <= 0) {
                return b;
            }
            this.f4449u = this.f4422F.toString();
            return b;
        }
        throw new AMapLocException("未知的错误");
    }

    private void m4512a(StringBuilder stringBuilder) {
        int i = 0;
        if (stringBuilder != null) {
            String[] strArr = new String[]{" phnum=\"\"", " nettype=\"\"", " nettype=\"UNKNOWN\"", " inftype=\"\"", "<macs><![CDATA[]]></macs>", "<nb></nb>", "<mmac><![CDATA[]]></mmac>", " gtype=\"0\"", " glong=\"0.0\"", " glat=\"0.0\"", " precision=\"0.0\"", " glong=\"0\"", " glat=\"0\"", " precision=\"0\"", "<smac>null</smac>", "<smac>00:00:00:00:00:00</smac>", "<imei>000000000000000</imei>", "<imsi>000000000000000</imsi>", "<mcc>000</mcc>", "<mcc>0</mcc>", "<lac>0</lac>", "<cellid>0</cellid>", "<key></key>"};
            int length = strArr.length;
            while (i < length) {
                String str = strArr[i];
                while (stringBuilder.indexOf(str) != -1) {
                    int indexOf = stringBuilder.indexOf(str);
                    stringBuilder.delete(indexOf, str.length() + indexOf);
                }
                i++;
            }
            while (stringBuilder.indexOf("*<") != -1) {
                stringBuilder.deleteCharAt(stringBuilder.indexOf("*<"));
            }
        }
    }

    private void m4524b(int i) {
        if (i == -113) {
            this.f4445q = -113;
            return;
        }
        this.f4445q = i;
        switch (this.f4435g) {
            case 1:
            case 2:
                if (this.f4439k.size() > 0) {
                    ((C0445e) this.f4439k.get(0)).f1846j = this.f4445q;
                    return;
                }
                return;
            default:
                return;
        }
    }

    private C0445e m4523b(CellLocation cellLocation) {
        GsmCellLocation gsmCellLocation = (GsmCellLocation) cellLocation;
        C0445e c0445e = new C0445e();
        String[] a = C0470t.m2014a(this.f4438j);
        c0445e.f1837a = a[0];
        c0445e.f1838b = a[1];
        c0445e.f1839c = gsmCellLocation.getLac();
        c0445e.f1840d = gsmCellLocation.getCid();
        c0445e.f1846j = this.f4445q;
        return c0445e;
    }

    private C0445e m4507a(NeighboringCellInfo neighboringCellInfo) {
        if (C0470t.m2015b() < 5) {
            return null;
        }
        try {
            C0445e c0445e = new C0445e();
            String[] a = C0470t.m2014a(this.f4438j);
            c0445e.f1837a = a[0];
            c0445e.f1838b = a[1];
            c0445e.f1839c = neighboringCellInfo.getLac();
            c0445e.f1840d = neighboringCellInfo.getCid();
            c0445e.f1846j = C0470t.m2004a(neighboringCellInfo.getRssi());
            return c0445e;
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    private void m4527c(CellLocation cellLocation) {
        if (this.f4439k != null && cellLocation != null && this.f4438j != null) {
            int i;
            this.f4439k.clear();
            GsmCellLocation gsmCellLocation = (GsmCellLocation) cellLocation;
            if (gsmCellLocation.getLac() == -1) {
                i = 0;
            } else if (gsmCellLocation.getCid() == -1 || gsmCellLocation.getCid() == SupportMenu.USER_MASK || gsmCellLocation.getCid() >= 268435455) {
                i = 0;
            } else if (gsmCellLocation.getLac() == 0) {
                i = 0;
            } else if (gsmCellLocation.getLac() > SupportMenu.USER_MASK) {
                i = 0;
            } else if (gsmCellLocation.getCid() == 0) {
                i = 0;
            } else {
                i = 1;
            }
            if (i == 0) {
                this.f4435g = 9;
                C0470t.m2010a("case 2,gsm illegal");
                return;
            }
            this.f4435g = 1;
            this.f4439k.add(m4523b(cellLocation));
            List<NeighboringCellInfo> neighboringCellInfo = this.f4438j.getNeighboringCellInfo();
            if (neighboringCellInfo != null) {
                for (NeighboringCellInfo neighboringCellInfo2 : neighboringCellInfo) {
                    if (neighboringCellInfo2.getCid() != -1) {
                        int i2;
                        if (neighboringCellInfo2.getLac() == -1) {
                            i2 = 0;
                        } else if (neighboringCellInfo2.getLac() == 0) {
                            i2 = 0;
                        } else if (neighboringCellInfo2.getLac() > SupportMenu.USER_MASK) {
                            i2 = 0;
                        } else if (neighboringCellInfo2.getCid() == -1) {
                            i2 = 0;
                        } else if (neighboringCellInfo2.getCid() == 0) {
                            i2 = 0;
                        } else if (neighboringCellInfo2.getCid() == SupportMenu.USER_MASK) {
                            i2 = 0;
                        } else if (neighboringCellInfo2.getCid() >= 268435455) {
                            i2 = 0;
                        } else {
                            i2 = 1;
                        }
                        if (i2 != 0) {
                            C0445e a = m4507a(neighboringCellInfo2);
                            if (a != null) {
                                this.f4439k.add(a);
                            }
                        }
                    }
                }
            }
        }
    }

    private void m4531d(CellLocation cellLocation) {
        this.f4439k.clear();
        if (C0470t.m2015b() >= 5) {
            try {
                CdmaCellLocation cdmaCellLocation = (CdmaCellLocation) cellLocation;
                if (cdmaCellLocation.getSystemId() <= 0) {
                    this.f4435g = 9;
                    C0470t.m2010a("cdma illegal");
                } else if (cdmaCellLocation.getNetworkId() < 0) {
                    this.f4435g = 9;
                    C0470t.m2010a("cdma illegal");
                } else if (cdmaCellLocation.getBaseStationId() < 0) {
                    this.f4435g = 9;
                    C0470t.m2010a("cdma illegal");
                } else {
                    this.f4435g = 2;
                    String[] a = C0470t.m2014a(this.f4438j);
                    C0445e c0445e = new C0445e();
                    c0445e.f1837a = a[0];
                    c0445e.f1838b = a[1];
                    c0445e.f1843g = cdmaCellLocation.getSystemId();
                    c0445e.f1844h = cdmaCellLocation.getNetworkId();
                    c0445e.f1845i = cdmaCellLocation.getBaseStationId();
                    c0445e.f1846j = this.f4445q;
                    c0445e.f1841e = cdmaCellLocation.getBaseStationLatitude();
                    c0445e.f1842f = cdmaCellLocation.getBaseStationLongitude();
                    this.f4439k.add(c0445e);
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    private void m4545n() {
        this.f4440l.clear();
        this.f4447s = null;
    }

    private void m4546o() {
        if (m4550s()) {
            try {
                this.f4437i.startScan();
                this.f4417A = C0470t.m2006a();
            } catch (SecurityException e) {
            }
        }
    }

    private boolean m4547p() {
        if (this.f4453y != 0 && C0470t.m2006a() - this.f4453y < 2000) {
            return true;
        }
        return false;
    }

    public void m4567c() {
        try {
            if (this.f4421E == null) {
                this.f4421E = C0475y.m2034a(this.f4434f);
                this.f4421E.m2070a(256);
            }
            if (!this.f4428L) {
                this.f4428L = true;
                this.f4421E.m2069a();
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    private void m4548q() {
        if (this.f4450v != null && this.f4441m.size() >= 1) {
            Iterator it = this.f4441m.entrySet().iterator();
            while (it != null && it.hasNext()) {
                Entry entry = (Entry) it.next();
                PendingIntent pendingIntent = (PendingIntent) entry.getKey();
                List<C0454j> list = (List) entry.getValue();
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                for (C0454j c0454j : list) {
                    long a = c0454j.m1953a();
                    if (a == -1 || a >= C0470t.m2006a()) {
                        float a2 = C0470t.m2003a(new double[]{c0454j.f1895b, c0454j.f1894a, this.f4450v.m1866e(), this.f4450v.m1864d()});
                        if (a2 < c0454j.f1896c) {
                            bundle.putFloat("distance", a2);
                            bundle.putString("fence", c0454j.m1955b());
                            intent.putExtras(bundle);
                            try {
                                pendingIntent.send(this.f4434f, 0, intent);
                            } catch (Throwable th) {
                                th.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }

    public void m4568d() {
        if (this.f4450v != null && this.f4442n.size() >= 1) {
            Iterator it = this.f4442n.entrySet().iterator();
            while (it != null && it.hasNext()) {
                Entry entry = (Entry) it.next();
                PendingIntent pendingIntent = (PendingIntent) entry.getKey();
                List<C0454j> list = (List) entry.getValue();
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                for (C0454j c0454j : list) {
                    long a = c0454j.m1953a();
                    if (a == -1 || a >= C0470t.m2006a()) {
                        float a2 = C0470t.m2003a(new double[]{c0454j.f1895b, c0454j.f1894a, this.f4450v.m1866e(), this.f4450v.m1864d()});
                        if (a2 >= c0454j.f1896c) {
                            if (c0454j.f1897d != 0) {
                                c0454j.f1897d = 0;
                            }
                        }
                        if (a2 < c0454j.f1896c) {
                            if (c0454j.f1897d != 1) {
                                c0454j.f1897d = 1;
                            }
                        }
                        bundle.putFloat("distance", a2);
                        bundle.putString("fence", c0454j.m1955b());
                        bundle.putInt("status", c0454j.f1897d);
                        intent.putExtras(bundle);
                        try {
                            pendingIntent.send(this.f4434f, 0, intent);
                        } catch (Throwable th) {
                            th.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public void mo1796a(AMapLocation aMapLocation) {
        if (aMapLocation != null && this.f4442n.size() >= 1) {
            Iterator it = this.f4442n.entrySet().iterator();
            while (it != null && it.hasNext()) {
                Entry entry = (Entry) it.next();
                PendingIntent pendingIntent = (PendingIntent) entry.getKey();
                List<C0454j> list = (List) entry.getValue();
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                for (C0454j c0454j : list) {
                    long a = c0454j.m1953a();
                    if (a == -1 || a >= C0470t.m2006a()) {
                        float a2 = C0470t.m2003a(new double[]{c0454j.f1895b, c0454j.f1894a, aMapLocation.getLatitude(), aMapLocation.getLongitude()});
                        if (a2 >= c0454j.f1896c) {
                            if (c0454j.f1897d != 0) {
                                c0454j.f1897d = 0;
                            }
                        }
                        if (a2 < c0454j.f1896c) {
                            if (c0454j.f1897d != 1) {
                                c0454j.f1897d = 1;
                            }
                        }
                        bundle.putFloat("distance", a2);
                        bundle.putString("fence", c0454j.m1955b());
                        bundle.putInt("status", c0454j.f1897d);
                        intent.putExtras(bundle);
                        try {
                            pendingIntent.send(this.f4434f, 0, intent);
                        } catch (Throwable th) {
                            th.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    private void m4549r() {
        switch (this.f4435g) {
            case 1:
                if (this.f4439k.size() == 0) {
                    this.f4435g = 9;
                    return;
                }
                return;
            case 2:
                if (this.f4439k.size() == 0) {
                    this.f4435g = 9;
                    return;
                }
                return;
            default:
                return;
        }
    }

    private boolean m4550s() {
        boolean z = false;
        if (this.f4437i != null) {
            try {
                z = this.f4437i.isWifiEnabled();
            } catch (Exception e) {
            }
            if (!z && C0470t.m2015b() > 17) {
                try {
                    z = String.valueOf(C0461n.m1979a(this.f4437i, "isScanAlwaysAvailable", new Object[0])).equals(ServerProtocol.DIALOG_RETURN_SCOPES_TRUE);
                } catch (Exception e2) {
                }
            }
        }
        return z;
    }

    private C0442c m4551t() throws Exception {
        byte[] j = m4541j();
        if (this.f4422F == null || !this.f4422F.toString().equals(this.f4449u) || this.f4450v == null) {
            return m4506a(j, false);
        }
        this.f4451w = C0470t.m2006a();
        return this.f4450v;
    }

    private void m4552u() {
        if (this.f4452x) {
            this.f4435g = 9;
            this.f4439k.clear();
            return;
        }
        m4549r();
    }

    public int m4556a(boolean z, int i) {
        if (z) {
            m4526c(i);
        } else {
            m4553v();
        }
        return m4569e() ? this.f4421E.m2076f() : -1;
    }

    private void m4526c(final int i) {
        try {
            if (C0470t.m2006a() - this.f4423G >= 45000) {
                if (!m4569e() || this.f4421E.m2076f() >= 20) {
                    m4555x();
                    if (this.f4430b == null) {
                        this.f4430b = new TimerTask(this) {
                            final /* synthetic */ C1613a f1686b;

                            public void run() {
                                this.f1686b.m4530d(i);
                            }
                        };
                    }
                    if (this.f4431c == null) {
                        this.f4431c = new Timer(false);
                        this.f4431c.schedule(this.f4430b, 3000, 3000);
                    }
                }
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    private void m4553v() {
        if (this.f4431c != null) {
            this.f4431c.cancel();
            this.f4431c = null;
        }
        if (this.f4430b != null) {
            this.f4430b.cancel();
            this.f4430b = null;
        }
    }

    private void m4554w() {
        if (m4569e()) {
            try {
                this.f4421E.m2070a(768);
            } catch (Throwable th) {
                th.printStackTrace();
                C0470t.m2008a(th);
            }
        }
    }

    private void m4530d(int i) {
        int i2 = 70254591;
        if (m4569e()) {
            try {
                m4554w();
                switch (i) {
                    case 1:
                        i2 = 674234367;
                        break;
                    case 2:
                        if (!m4544m()) {
                            i2 = 674234367;
                            break;
                        } else {
                            i2 = 2083520511;
                            break;
                        }
                }
                this.f4421E.m2071a(null, m4508a(1, i2, 1));
                this.f4432d = this.f4421E.m2074d();
                if (this.f4432d != null) {
                    Object a = this.f4418B.m1975a(this.f4432d.m1730a(), this.f4434f);
                    if (m4569e()) {
                        if (TextUtils.isEmpty(a) || !a.equals(ServerProtocol.DIALOG_RETURN_SCOPES_TRUE)) {
                            this.f4433e++;
                            this.f4421E.m2071a(this.f4432d, m4508a(1, i2, 0));
                        } else {
                            this.f4421E.m2071a(this.f4432d, m4508a(1, i2, 1));
                        }
                    }
                }
                m4555x();
                if (m4569e() && this.f4421E.m2076f() == 0) {
                    m4553v();
                } else if (this.f4433e >= 3) {
                    m4553v();
                }
            } catch (Throwable th) {
                th.printStackTrace();
                C0470t.m2008a(th);
            }
        }
    }

    boolean m4569e() {
        if (this.f4421E == null) {
            return false;
        }
        return true;
    }

    private void m4555x() {
        if (m4569e() && this.f4421E.m2076f() <= 0) {
            try {
                if (!this.f4421E.m2075e()) {
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    private String m4508a(int i, int i2, int i3) throws Exception {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("e", i);
        jSONObject.put("d", i2);
        jSONObject.put("u", i3);
        return jSONObject.toString();
    }
}
