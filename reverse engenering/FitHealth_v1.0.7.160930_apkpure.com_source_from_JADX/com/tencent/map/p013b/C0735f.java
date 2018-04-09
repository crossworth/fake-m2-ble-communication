package com.tencent.map.p013b;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Handler;
import java.util.ArrayList;
import java.util.List;

public final class C0735f {
    private Context f2568a = null;
    private WifiManager f2569b = null;
    private C0734c f2570c = null;
    private Handler f2571d = null;
    private Runnable f2572e = new C0736g(this);
    private int f2573f = 1;
    private C0732a f2574g = null;
    private C0733b f2575h = null;
    private boolean f2576i = false;
    private byte[] f2577j = new byte[0];

    public interface C0732a {
        void mo2100a(C0733b c0733b);

        void mo2102b(int i);
    }

    public class C0733b implements Cloneable {
        private List<ScanResult> f2563a = null;

        public C0733b(C0735f c0735f, List<ScanResult> list, long j, int i) {
            if (list != null) {
                this.f2563a = new ArrayList();
                for (ScanResult add : list) {
                    this.f2563a.add(add);
                }
            }
        }

        public final List<ScanResult> m2450a() {
            return this.f2563a;
        }

        public final Object clone() {
            C0733b c0733b;
            try {
                c0733b = (C0733b) super.clone();
            } catch (Exception e) {
                c0733b = null;
            }
            if (this.f2563a != null) {
                c0733b.f2563a = new ArrayList();
                c0733b.f2563a.addAll(this.f2563a);
            }
            return c0733b;
        }
    }

    public class C0734c extends BroadcastReceiver {
        private int f2564a = 4;
        private List<ScanResult> f2565b = null;
        private boolean f2566c = false;
        private /* synthetic */ C0735f f2567d;

        public C0734c(C0735f c0735f) {
            this.f2567d = c0735f;
        }

        private void m2451a(List<ScanResult> list) {
            if (list != null) {
                if (this.f2566c) {
                    if (this.f2565b == null) {
                        this.f2565b = new ArrayList();
                    }
                    int size = this.f2565b.size();
                    for (ScanResult scanResult : list) {
                        for (int i = 0; i < size; i++) {
                            if (((ScanResult) this.f2565b.get(i)).BSSID.equals(scanResult.BSSID)) {
                                this.f2565b.remove(i);
                                break;
                            }
                        }
                        this.f2565b.add(scanResult);
                    }
                    return;
                }
                if (this.f2565b == null) {
                    this.f2565b = new ArrayList();
                } else {
                    this.f2565b.clear();
                }
                for (ScanResult scanResult2 : list) {
                    this.f2565b.add(scanResult2);
                }
            }
        }

        public final void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.net.wifi.WIFI_STATE_CHANGED")) {
                this.f2564a = intent.getIntExtra("wifi_state", 4);
                if (this.f2567d.f2574g != null) {
                    this.f2567d.f2574g.mo2102b(this.f2564a);
                }
            }
            if (intent.getAction().equals("android.net.wifi.SCAN_RESULTS") || intent.getAction().equals("android.net.wifi.WIFI_STATE_CHANGED")) {
                List list = null;
                if (this.f2567d.f2569b != null) {
                    list = this.f2567d.f2569b.getScanResults();
                }
                if (intent.getAction().equals("android.net.wifi.WIFI_STATE_CHANGED")) {
                    if (list == null) {
                        return;
                    }
                    if (list != null && list.size() == 0) {
                        return;
                    }
                }
                if (this.f2566c || this.f2565b == null || this.f2565b.size() < 4 || list == null || list.size() > 2) {
                    m2451a(list);
                    this.f2566c = false;
                    this.f2567d.f2575h = new C0733b(this.f2567d, this.f2565b, System.currentTimeMillis(), this.f2564a);
                    if (this.f2567d.f2574g != null) {
                        this.f2567d.f2574g.mo2100a(this.f2567d.f2575h);
                    }
                    this.f2567d.m2459a(((long) this.f2567d.f2573f) * 20000);
                    return;
                }
                m2451a(list);
                this.f2566c = true;
                this.f2567d.m2459a(0);
            }
        }
    }

    static /* synthetic */ void m2453a(C0735f c0735f) {
        if (c0735f.f2569b != null && c0735f.f2569b.isWifiEnabled()) {
            c0735f.f2569b.startScan();
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void m2458a() {
        /*
        r3 = this;
        r1 = r3.f2577j;
        monitor-enter(r1);
        r0 = r3.f2576i;	 Catch:{ all -> 0x0013 }
        if (r0 != 0) goto L_0x0009;
    L_0x0007:
        monitor-exit(r1);	 Catch:{ all -> 0x0013 }
    L_0x0008:
        return;
    L_0x0009:
        r0 = r3.f2568a;	 Catch:{ all -> 0x0013 }
        if (r0 == 0) goto L_0x0011;
    L_0x000d:
        r0 = r3.f2570c;	 Catch:{ all -> 0x0013 }
        if (r0 != 0) goto L_0x0016;
    L_0x0011:
        monitor-exit(r1);	 Catch:{ all -> 0x0013 }
        goto L_0x0008;
    L_0x0013:
        r0 = move-exception;
        monitor-exit(r1);
        throw r0;
    L_0x0016:
        r0 = r3.f2568a;	 Catch:{ Exception -> 0x0029 }
        r2 = r3.f2570c;	 Catch:{ Exception -> 0x0029 }
        r0.unregisterReceiver(r2);	 Catch:{ Exception -> 0x0029 }
    L_0x001d:
        r0 = r3.f2571d;	 Catch:{ all -> 0x0013 }
        r2 = r3.f2572e;	 Catch:{ all -> 0x0013 }
        r0.removeCallbacks(r2);	 Catch:{ all -> 0x0013 }
        r0 = 0;
        r3.f2576i = r0;	 Catch:{ all -> 0x0013 }
        monitor-exit(r1);	 Catch:{ all -> 0x0013 }
        goto L_0x0008;
    L_0x0029:
        r0 = move-exception;
        goto L_0x001d;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.map.b.f.a():void");
    }

    public final void m2459a(long j) {
        if (this.f2571d != null && this.f2576i) {
            this.f2571d.removeCallbacks(this.f2572e);
            this.f2571d.postDelayed(this.f2572e, j);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean m2460a(android.content.Context r6, com.tencent.map.p013b.C0735f.C0732a r7, int r8) {
        /*
        r5 = this;
        r0 = 1;
        r1 = 0;
        r2 = r5.f2577j;
        monitor-enter(r2);
        r3 = r5.f2576i;	 Catch:{ all -> 0x0068 }
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
        r5.f2571d = r0;	 Catch:{ all -> 0x0068 }
        r5.f2568a = r6;	 Catch:{ all -> 0x0068 }
        r5.f2574g = r7;	 Catch:{ all -> 0x0068 }
        r0 = 1;
        r5.f2573f = r0;	 Catch:{ all -> 0x0068 }
        r0 = r5.f2568a;	 Catch:{ Exception -> 0x0064 }
        r3 = "wifi";
        r0 = r0.getSystemService(r3);	 Catch:{ Exception -> 0x0064 }
        r0 = (android.net.wifi.WifiManager) r0;	 Catch:{ Exception -> 0x0064 }
        r5.f2569b = r0;	 Catch:{ Exception -> 0x0064 }
        r0 = new android.content.IntentFilter;	 Catch:{ Exception -> 0x0064 }
        r0.<init>();	 Catch:{ Exception -> 0x0064 }
        r3 = new com.tencent.map.b.f$c;	 Catch:{ Exception -> 0x0064 }
        r3.<init>(r5);	 Catch:{ Exception -> 0x0064 }
        r5.f2570c = r3;	 Catch:{ Exception -> 0x0064 }
        r3 = r5.f2569b;	 Catch:{ Exception -> 0x0064 }
        if (r3 == 0) goto L_0x0044;
    L_0x0040:
        r3 = r5.f2570c;	 Catch:{ Exception -> 0x0064 }
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
        r3 = r5.f2568a;	 Catch:{ Exception -> 0x0064 }
        r4 = r5.f2570c;	 Catch:{ Exception -> 0x0064 }
        r3.registerReceiver(r4, r0);	 Catch:{ Exception -> 0x0064 }
        r0 = 0;
        r5.m2459a(r0);	 Catch:{ all -> 0x0068 }
        r0 = 1;
        r5.f2576i = r0;	 Catch:{ all -> 0x0068 }
        monitor-exit(r2);	 Catch:{ all -> 0x0068 }
        r0 = r5.f2576i;
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
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.map.b.f.a(android.content.Context, com.tencent.map.b.f$a, int):boolean");
    }

    public final boolean m2461b() {
        return this.f2576i;
    }

    public final boolean m2462c() {
        return (this.f2568a == null || this.f2569b == null) ? false : this.f2569b.isWifiEnabled();
    }
}
