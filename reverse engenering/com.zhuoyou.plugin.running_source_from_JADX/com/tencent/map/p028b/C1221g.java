package com.tencent.map.p028b;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Handler;
import java.util.ArrayList;
import java.util.List;

public final class C1221g {
    private Context f3881a = null;
    private WifiManager f3882b = null;
    private C1219a f3883c = null;
    private Handler f3884d = null;
    private Runnable f3885e = new C12181(this);
    private int f3886f = 1;
    private C1216c f3887g = null;
    private C1220b f3888h = null;
    private boolean f3889i = false;
    private byte[] f3890j = new byte[0];

    public interface C1216c {
        void mo2157a(C1220b c1220b);

        void mo2158b(int i);
    }

    class C12181 implements Runnable {
        private /* synthetic */ C1221g f3875a;

        C12181(C1221g c1221g) {
            this.f3875a = c1221g;
        }

        public final void run() {
            C1221g.m3609a(this.f3875a);
        }
    }

    public class C1219a extends BroadcastReceiver {
        private int f3876a = 4;
        private List<ScanResult> f3877b = null;
        private boolean f3878c = false;
        private /* synthetic */ C1221g f3879d;

        public C1219a(C1221g c1221g) {
            this.f3879d = c1221g;
        }

        private void m3606a(List<ScanResult> list) {
            if (list != null) {
                if (this.f3878c) {
                    if (this.f3877b == null) {
                        this.f3877b = new ArrayList();
                    }
                    int size = this.f3877b.size();
                    for (ScanResult scanResult : list) {
                        for (int i = 0; i < size; i++) {
                            if (((ScanResult) this.f3877b.get(i)).BSSID.equals(scanResult.BSSID)) {
                                this.f3877b.remove(i);
                                break;
                            }
                        }
                        this.f3877b.add(scanResult);
                    }
                    return;
                }
                if (this.f3877b == null) {
                    this.f3877b = new ArrayList();
                } else {
                    this.f3877b.clear();
                }
                for (ScanResult scanResult2 : list) {
                    this.f3877b.add(scanResult2);
                }
            }
        }

        public final void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.net.wifi.WIFI_STATE_CHANGED")) {
                this.f3876a = intent.getIntExtra("wifi_state", 4);
                if (this.f3879d.f3887g != null) {
                    this.f3879d.f3887g.mo2158b(this.f3876a);
                }
            }
            if (intent.getAction().equals("android.net.wifi.SCAN_RESULTS") || intent.getAction().equals("android.net.wifi.WIFI_STATE_CHANGED")) {
                List list = null;
                if (this.f3879d.f3882b != null) {
                    list = this.f3879d.f3882b.getScanResults();
                }
                if (intent.getAction().equals("android.net.wifi.WIFI_STATE_CHANGED")) {
                    if (list == null) {
                        return;
                    }
                    if (list != null && list.size() == 0) {
                        return;
                    }
                }
                if (this.f3878c || this.f3877b == null || this.f3877b.size() < 4 || list == null || list.size() > 2) {
                    m3606a(list);
                    this.f3878c = false;
                    this.f3879d.f3888h = new C1220b(this.f3879d, this.f3877b, System.currentTimeMillis(), this.f3876a);
                    if (this.f3879d.f3887g != null) {
                        this.f3879d.f3887g.mo2157a(this.f3879d.f3888h);
                    }
                    this.f3879d.m3615a(((long) this.f3879d.f3886f) * 20000);
                    return;
                }
                m3606a(list);
                this.f3878c = true;
                this.f3879d.m3615a(0);
            }
        }
    }

    public class C1220b implements Cloneable {
        private List<ScanResult> f3880a = null;

        public C1220b(C1221g c1221g, List<ScanResult> list, long j, int i) {
            if (list != null) {
                this.f3880a = new ArrayList();
                for (ScanResult add : list) {
                    this.f3880a.add(add);
                }
            }
        }

        public final List<ScanResult> m3607a() {
            return this.f3880a;
        }

        public final Object clone() {
            C1220b c1220b;
            try {
                c1220b = (C1220b) super.clone();
            } catch (Exception e) {
                c1220b = null;
            }
            if (this.f3880a != null) {
                c1220b.f3880a = new ArrayList();
                c1220b.f3880a.addAll(this.f3880a);
            }
            return c1220b;
        }
    }

    static /* synthetic */ void m3609a(C1221g c1221g) {
        if (c1221g.f3882b != null && c1221g.f3882b.isWifiEnabled()) {
            c1221g.f3882b.startScan();
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void m3614a() {
        /*
        r3 = this;
        r1 = r3.f3890j;
        monitor-enter(r1);
        r0 = r3.f3889i;	 Catch:{ all -> 0x0013 }
        if (r0 != 0) goto L_0x0009;
    L_0x0007:
        monitor-exit(r1);	 Catch:{ all -> 0x0013 }
    L_0x0008:
        return;
    L_0x0009:
        r0 = r3.f3881a;	 Catch:{ all -> 0x0013 }
        if (r0 == 0) goto L_0x0011;
    L_0x000d:
        r0 = r3.f3883c;	 Catch:{ all -> 0x0013 }
        if (r0 != 0) goto L_0x0016;
    L_0x0011:
        monitor-exit(r1);	 Catch:{ all -> 0x0013 }
        goto L_0x0008;
    L_0x0013:
        r0 = move-exception;
        monitor-exit(r1);
        throw r0;
    L_0x0016:
        r0 = r3.f3881a;	 Catch:{ Exception -> 0x0029 }
        r2 = r3.f3883c;	 Catch:{ Exception -> 0x0029 }
        r0.unregisterReceiver(r2);	 Catch:{ Exception -> 0x0029 }
    L_0x001d:
        r0 = r3.f3884d;	 Catch:{ all -> 0x0013 }
        r2 = r3.f3885e;	 Catch:{ all -> 0x0013 }
        r0.removeCallbacks(r2);	 Catch:{ all -> 0x0013 }
        r0 = 0;
        r3.f3889i = r0;	 Catch:{ all -> 0x0013 }
        monitor-exit(r1);	 Catch:{ all -> 0x0013 }
        goto L_0x0008;
    L_0x0029:
        r0 = move-exception;
        goto L_0x001d;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.map.b.g.a():void");
    }

    public final void m3615a(long j) {
        if (this.f3884d != null && this.f3889i) {
            this.f3884d.removeCallbacks(this.f3885e);
            this.f3884d.postDelayed(this.f3885e, j);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean m3616a(android.content.Context r6, com.tencent.map.p028b.C1221g.C1216c r7, int r8) {
        /*
        r5 = this;
        r0 = 1;
        r1 = 0;
        r2 = r5.f3890j;
        monitor-enter(r2);
        r3 = r5.f3889i;	 Catch:{ all -> 0x0068 }
        if (r3 == 0) goto L_0x000b;
    L_0x0009:
        monitor-exit(r2);	 Catch:{ all -> 0x0068 }
    L_0x000a:
        return r0;
    L_0x000b:
        if (r6 == 0) goto L_0x000f;
    L_0x000d:
        if (r7 != 0) goto L_0x0012;
    L_0x000f:
        monitor-exit(r2);
        r0 = r1;
        goto L_0x000a;
    L_0x0012:
        r0 = new android.os.Handler;	 Catch:{ all -> 0x0068 }
        r3 = android.os.Looper.getMainLooper();	 Catch:{ all -> 0x0068 }
        r0.<init>(r3);	 Catch:{ all -> 0x0068 }
        r5.f3884d = r0;	 Catch:{ all -> 0x0068 }
        r5.f3881a = r6;	 Catch:{ all -> 0x0068 }
        r5.f3887g = r7;	 Catch:{ all -> 0x0068 }
        r0 = 1;
        r5.f3886f = r0;	 Catch:{ all -> 0x0068 }
        r0 = r5.f3881a;	 Catch:{ Exception -> 0x0064 }
        r3 = "wifi";
        r0 = r0.getSystemService(r3);	 Catch:{ Exception -> 0x0064 }
        r0 = (android.net.wifi.WifiManager) r0;	 Catch:{ Exception -> 0x0064 }
        r5.f3882b = r0;	 Catch:{ Exception -> 0x0064 }
        r0 = new android.content.IntentFilter;	 Catch:{ Exception -> 0x0064 }
        r0.<init>();	 Catch:{ Exception -> 0x0064 }
        r3 = new com.tencent.map.b.g$a;	 Catch:{ Exception -> 0x0064 }
        r3.<init>(r5);	 Catch:{ Exception -> 0x0064 }
        r5.f3883c = r3;	 Catch:{ Exception -> 0x0064 }
        r3 = r5.f3882b;	 Catch:{ Exception -> 0x0064 }
        if (r3 == 0) goto L_0x0044;
    L_0x0040:
        r3 = r5.f3883c;	 Catch:{ Exception -> 0x0064 }
        if (r3 != 0) goto L_0x0047;
    L_0x0044:
        monitor-exit(r2);	 Catch:{ all -> 0x0068 }
        r0 = r1;
        goto L_0x000a;
    L_0x0047:
        r3 = "android.net.wifi.WIFI_STATE_CHANGED";
        r0.addAction(r3);	 Catch:{ Exception -> 0x0064 }
        r3 = "android.net.wifi.SCAN_RESULTS";
        r0.addAction(r3);	 Catch:{ Exception -> 0x0064 }
        r3 = r5.f3881a;	 Catch:{ Exception -> 0x0064 }
        r4 = r5.f3883c;	 Catch:{ Exception -> 0x0064 }
        r3.registerReceiver(r4, r0);	 Catch:{ Exception -> 0x0064 }
        r0 = 0;
        r5.m3615a(r0);	 Catch:{ all -> 0x0068 }
        r0 = 1;
        r5.f3889i = r0;	 Catch:{ all -> 0x0068 }
        monitor-exit(r2);	 Catch:{ all -> 0x0068 }
        r0 = r5.f3889i;
        goto L_0x000a;
    L_0x0064:
        r0 = move-exception;
        monitor-exit(r2);	 Catch:{ all -> 0x0068 }
        r0 = r1;
        goto L_0x000a;
    L_0x0068:
        r0 = move-exception;
        monitor-exit(r2);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.map.b.g.a(android.content.Context, com.tencent.map.b.g$c, int):boolean");
    }

    public final boolean m3617b() {
        return this.f3889i;
    }

    public final boolean m3618c() {
        return (this.f3881a == null || this.f3882b == null) ? false : this.f3882b.isWifiEnabled();
    }
}
