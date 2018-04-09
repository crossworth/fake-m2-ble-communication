package com.tencent.map.p013b;

import android.content.Context;
import android.telephony.CellLocation;
import android.telephony.NeighboringCellInfo;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import com.zhuoyou.plugin.bluetooth.connection.CustomCmd;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

public final class C0745m {
    private Context f2604a = null;
    private TelephonyManager f2605b = null;
    private C0744c f2606c = null;
    private C0742a f2607d = null;
    private C0743b f2608e = null;
    private boolean f2609f = false;
    private List<NeighboringCellInfo> f2610g = new LinkedList();
    private byte[] f2611h = new byte[0];
    private byte[] f2612i = new byte[0];
    private boolean f2613j = false;

    public interface C0742a {
        void mo2101a(C0743b c0743b);
    }

    public class C0743b implements Cloneable {
        public int f2582a = 0;
        public int f2583b = 0;
        public int f2584c = 0;
        public int f2585d = 0;
        public int f2586e = 0;
        public int f2587f = 0;
        public int f2588g = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        public int f2589h = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;

        public C0743b(C0745m c0745m, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            this.f2582a = i;
            this.f2583b = i2;
            this.f2584c = i3;
            this.f2585d = i4;
            this.f2586e = i5;
            this.f2587f = i6;
            this.f2588g = i7;
            this.f2589h = i8;
        }

        public final Object clone() {
            try {
                return (C0743b) super.clone();
            } catch (Exception e) {
                return null;
            }
        }
    }

    public class C0744c extends PhoneStateListener {
        private int f2590a = 0;
        private int f2591b = 0;
        private int f2592c = 0;
        private int f2593d = 0;
        private int f2594e = 0;
        private int f2595f = -1;
        private int f2596g = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        private int f2597h = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        private Method f2598i = null;
        private Method f2599j = null;
        private Method f2600k = null;
        private Method f2601l = null;
        private Method f2602m = null;
        private /* synthetic */ C0745m f2603n;

        public C0744c(C0745m c0745m, int i, int i2) {
            this.f2603n = c0745m;
            this.f2591b = i;
            this.f2590a = i2;
        }

        public final void onCellLocationChanged(CellLocation cellLocation) {
            String networkOperator;
            this.f2595f = -1;
            this.f2594e = -1;
            this.f2593d = -1;
            this.f2592c = -1;
            if (cellLocation != null) {
                switch (this.f2590a) {
                    case 1:
                        GsmCellLocation gsmCellLocation;
                        Object obj;
                        try {
                            gsmCellLocation = (GsmCellLocation) cellLocation;
                            try {
                                if (gsmCellLocation.getLac() <= 0 && gsmCellLocation.getCid() <= 0) {
                                    gsmCellLocation = (GsmCellLocation) this.f2603n.f2605b.getCellLocation();
                                }
                                obj = 1;
                            } catch (Exception e) {
                                obj = null;
                                networkOperator = this.f2603n.f2605b.getNetworkOperator();
                                if (networkOperator != null) {
                                    try {
                                        if (networkOperator.length() > 3) {
                                            this.f2592c = Integer.valueOf(networkOperator.substring(3)).intValue();
                                        }
                                    } catch (Exception e2) {
                                        this.f2594e = -1;
                                        this.f2593d = -1;
                                        this.f2592c = -1;
                                    }
                                }
                                this.f2593d = gsmCellLocation.getLac();
                                this.f2594e = gsmCellLocation.getCid();
                                C0745m.m2475c(this.f2603n);
                                this.f2603n.f2608e = new C0743b(this.f2603n, this.f2590a, this.f2591b, this.f2592c, this.f2593d, this.f2594e, this.f2595f, this.f2596g, this.f2597h);
                                if (this.f2603n.f2607d == null) {
                                    this.f2603n.f2607d.mo2101a(this.f2603n.f2608e);
                                }
                            }
                        } catch (Exception e3) {
                            gsmCellLocation = null;
                            obj = null;
                            networkOperator = this.f2603n.f2605b.getNetworkOperator();
                            if (networkOperator != null) {
                                if (networkOperator.length() > 3) {
                                    this.f2592c = Integer.valueOf(networkOperator.substring(3)).intValue();
                                }
                            }
                            this.f2593d = gsmCellLocation.getLac();
                            this.f2594e = gsmCellLocation.getCid();
                            C0745m.m2475c(this.f2603n);
                            this.f2603n.f2608e = new C0743b(this.f2603n, this.f2590a, this.f2591b, this.f2592c, this.f2593d, this.f2594e, this.f2595f, this.f2596g, this.f2597h);
                            if (this.f2603n.f2607d == null) {
                                this.f2603n.f2607d.mo2101a(this.f2603n.f2608e);
                            }
                        }
                        if (!(obj == null || gsmCellLocation == null)) {
                            networkOperator = this.f2603n.f2605b.getNetworkOperator();
                            if (networkOperator != null) {
                                if (networkOperator.length() > 3) {
                                    this.f2592c = Integer.valueOf(networkOperator.substring(3)).intValue();
                                }
                            }
                            this.f2593d = gsmCellLocation.getLac();
                            this.f2594e = gsmCellLocation.getCid();
                            C0745m.m2475c(this.f2603n);
                        }
                    case 2:
                        if (cellLocation != null) {
                            try {
                                if (this.f2598i == null) {
                                    this.f2598i = Class.forName("android.telephony.cdma.CdmaCellLocation").getMethod("getBaseStationId", new Class[0]);
                                    this.f2599j = Class.forName("android.telephony.cdma.CdmaCellLocation").getMethod("getSystemId", new Class[0]);
                                    this.f2600k = Class.forName("android.telephony.cdma.CdmaCellLocation").getMethod("getNetworkId", new Class[0]);
                                    this.f2601l = Class.forName("android.telephony.cdma.CdmaCellLocation").getMethod("getBaseStationLatitude", new Class[0]);
                                    this.f2602m = Class.forName("android.telephony.cdma.CdmaCellLocation").getMethod("getBaseStationLongitude", new Class[0]);
                                }
                                this.f2592c = ((Integer) this.f2599j.invoke(cellLocation, new Object[0])).intValue();
                                this.f2593d = ((Integer) this.f2600k.invoke(cellLocation, new Object[0])).intValue();
                                this.f2594e = ((Integer) this.f2598i.invoke(cellLocation, new Object[0])).intValue();
                                this.f2596g = ((Integer) this.f2601l.invoke(cellLocation, new Object[0])).intValue();
                                this.f2597h = ((Integer) this.f2602m.invoke(cellLocation, new Object[0])).intValue();
                                break;
                            } catch (Exception e4) {
                                this.f2594e = -1;
                                this.f2593d = -1;
                                this.f2592c = -1;
                                this.f2596g = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
                                this.f2597h = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
                                break;
                            }
                        }
                        break;
                }
            }
            this.f2603n.f2608e = new C0743b(this.f2603n, this.f2590a, this.f2591b, this.f2592c, this.f2593d, this.f2594e, this.f2595f, this.f2596g, this.f2597h);
            if (this.f2603n.f2607d == null) {
                this.f2603n.f2607d.mo2101a(this.f2603n.f2608e);
            }
        }

        public final void onSignalStrengthChanged(int i) {
            if (this.f2590a == 1) {
                C0745m.m2475c(this.f2603n);
            }
            if (Math.abs(i - ((this.f2595f + CustomCmd.CMD_SYNC_SPORT_DATA) / 2)) <= 3) {
                return;
            }
            if (this.f2595f == -1) {
                this.f2595f = (i << 1) - 113;
                return;
            }
            this.f2595f = (i << 1) - 113;
            this.f2603n.f2608e = new C0743b(this.f2603n, this.f2590a, this.f2591b, this.f2592c, this.f2593d, this.f2594e, this.f2595f, this.f2596g, this.f2597h);
            if (this.f2603n.f2607d != null) {
                this.f2603n.f2607d.mo2101a(this.f2603n.f2608e);
            }
        }
    }

    private int m2470a(int i) {
        int intValue;
        String networkOperator = this.f2605b.getNetworkOperator();
        if (networkOperator != null && networkOperator.length() >= 3) {
            try {
                intValue = Integer.valueOf(networkOperator.substring(0, 3)).intValue();
            } catch (Exception e) {
            }
            return (i == 2 || intValue != -1) ? intValue : 0;
        }
        intValue = -1;
        if (i == 2) {
        }
    }

    static /* synthetic */ void m2475c(C0745m c0745m) {
        if (!c0745m.f2613j) {
            c0745m.f2613j = true;
            new C0741l(c0745m).start();
        }
    }

    public final void m2479a() {
        synchronized (this.f2611h) {
            if (this.f2609f) {
                if (!(this.f2605b == null || this.f2606c == null)) {
                    try {
                        this.f2605b.listen(this.f2606c, 0);
                    } catch (Exception e) {
                        this.f2609f = false;
                    }
                }
                this.f2609f = false;
                return;
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean m2480a(android.content.Context r6, com.tencent.map.p013b.C0745m.C0742a r7) {
        /*
        r5 = this;
        r0 = 1;
        r1 = 0;
        r2 = r5.f2611h;
        monitor-enter(r2);
        r3 = r5.f2609f;	 Catch:{ all -> 0x0055 }
        if (r3 == 0) goto L_0x000b;
    L_0x0009:
        monitor-exit(r2);	 Catch:{ all -> 0x0055 }
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
        r5.f2604a = r6;	 Catch:{ all -> 0x0055 }
        r5.f2607d = r7;	 Catch:{ all -> 0x0055 }
        r0 = r5.f2604a;	 Catch:{ Exception -> 0x0051 }
        r3 = "phone";
        r0 = r0.getSystemService(r3);	 Catch:{ Exception -> 0x0051 }
        r0 = (android.telephony.TelephonyManager) r0;	 Catch:{ Exception -> 0x0051 }
        r5.f2605b = r0;	 Catch:{ Exception -> 0x0051 }
        r0 = r5.f2605b;	 Catch:{ Exception -> 0x0051 }
        if (r0 != 0) goto L_0x0029;
    L_0x0026:
        monitor-exit(r2);	 Catch:{ all -> 0x0055 }
        r0 = r1;
        goto L_0x000a;
    L_0x0029:
        r0 = r5.f2605b;	 Catch:{ Exception -> 0x0051 }
        r0 = r0.getPhoneType();	 Catch:{ Exception -> 0x0051 }
        r3 = r5.m2470a(r0);	 Catch:{ Exception -> 0x0051 }
        r4 = new com.tencent.map.b.m$c;	 Catch:{ Exception -> 0x0051 }
        r4.<init>(r5, r3, r0);	 Catch:{ Exception -> 0x0051 }
        r5.f2606c = r4;	 Catch:{ Exception -> 0x0051 }
        r0 = r5.f2606c;	 Catch:{ Exception -> 0x0051 }
        if (r0 != 0) goto L_0x0041;
    L_0x003e:
        monitor-exit(r2);	 Catch:{ all -> 0x0055 }
        r0 = r1;
        goto L_0x000a;
    L_0x0041:
        r0 = r5.f2605b;	 Catch:{ Exception -> 0x0051 }
        r3 = r5.f2606c;	 Catch:{ Exception -> 0x0051 }
        r4 = 18;
        r0.listen(r3, r4);	 Catch:{ Exception -> 0x0051 }
        r0 = 1;
        r5.f2609f = r0;	 Catch:{ all -> 0x0055 }
        monitor-exit(r2);	 Catch:{ all -> 0x0055 }
        r0 = r5.f2609f;
        goto L_0x000a;
    L_0x0051:
        r0 = move-exception;
        monitor-exit(r2);	 Catch:{ all -> 0x0055 }
        r0 = r1;
        goto L_0x000a;
    L_0x0055:
        r0 = move-exception;
        monitor-exit(r2);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.map.b.m.a(android.content.Context, com.tencent.map.b.m$a):boolean");
    }

    public final List<NeighboringCellInfo> m2481b() {
        List<NeighboringCellInfo> list = null;
        synchronized (this.f2612i) {
            if (this.f2610g != null) {
                list = new LinkedList();
                list.addAll(this.f2610g);
            }
        }
        return list;
    }
}
