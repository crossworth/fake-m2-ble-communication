package com.tencent.map.p028b;

import android.content.Context;
import android.telephony.CellLocation;
import android.telephony.NeighboringCellInfo;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import com.droi.btlib.service.BtManagerService;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public final class C1206d {
    private Context f3795a = null;
    private TelephonyManager f3796b = null;
    private C1203a f3797c = null;
    private C1205c f3798d = null;
    private C1204b f3799e = null;
    private boolean f3800f = false;
    private List<NeighboringCellInfo> f3801g = new LinkedList();
    private byte[] f3802h = new byte[0];
    private byte[] f3803i = new byte[0];
    private boolean f3804j = false;

    class C12021 extends Thread {
        private /* synthetic */ C1206d f3772a;

        C12021(C1206d c1206d) {
            this.f3772a = c1206d;
        }

        public final void run() {
            if (this.f3772a.f3796b != null) {
                Collection neighboringCellInfo = this.f3772a.f3796b.getNeighboringCellInfo();
                synchronized (this.f3772a.f3803i) {
                    if (neighboringCellInfo != null) {
                        this.f3772a.f3801g.clear();
                        this.f3772a.f3801g.addAll(neighboringCellInfo);
                    }
                }
            }
            this.f3772a.f3804j = false;
        }
    }

    public class C1203a extends PhoneStateListener {
        private int f3773a = 0;
        private int f3774b = 0;
        private int f3775c = 0;
        private int f3776d = 0;
        private int f3777e = 0;
        private int f3778f = -1;
        private int f3779g = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        private int f3780h = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        private Method f3781i = null;
        private Method f3782j = null;
        private Method f3783k = null;
        private Method f3784l = null;
        private Method f3785m = null;
        private /* synthetic */ C1206d f3786n;

        public C1203a(C1206d c1206d, int i, int i2) {
            this.f3786n = c1206d;
            this.f3774b = i;
            this.f3773a = i2;
        }

        public final void onCellLocationChanged(CellLocation cellLocation) {
            GsmCellLocation gsmCellLocation;
            String networkOperator;
            this.f3778f = -1;
            this.f3777e = -1;
            this.f3776d = -1;
            this.f3775c = -1;
            if (cellLocation != null) {
                switch (this.f3773a) {
                    case 1:
                        Object obj;
                        try {
                            gsmCellLocation = (GsmCellLocation) cellLocation;
                            try {
                                if (gsmCellLocation.getLac() <= 0 && gsmCellLocation.getCid() <= 0) {
                                    gsmCellLocation = (GsmCellLocation) this.f3786n.f3796b.getCellLocation();
                                }
                                obj = 1;
                            } catch (Exception e) {
                                obj = null;
                                networkOperator = this.f3786n.f3796b.getNetworkOperator();
                                if (networkOperator != null) {
                                    try {
                                        if (networkOperator.length() > 3) {
                                            this.f3775c = Integer.valueOf(networkOperator.substring(3)).intValue();
                                        }
                                    } catch (Exception e2) {
                                        this.f3777e = -1;
                                        this.f3776d = -1;
                                        this.f3775c = -1;
                                    }
                                }
                                this.f3776d = gsmCellLocation.getLac();
                                this.f3777e = gsmCellLocation.getCid();
                                C1206d.m3523c(this.f3786n);
                                this.f3786n.f3799e = new C1204b(this.f3786n, this.f3773a, this.f3774b, this.f3775c, this.f3776d, this.f3777e, this.f3778f, this.f3779g, this.f3780h);
                                if (this.f3786n.f3798d == null) {
                                    this.f3786n.f3798d.mo2155a(this.f3786n.f3799e);
                                }
                            }
                        } catch (Exception e3) {
                            gsmCellLocation = null;
                            obj = null;
                            networkOperator = this.f3786n.f3796b.getNetworkOperator();
                            if (networkOperator != null) {
                                if (networkOperator.length() > 3) {
                                    this.f3775c = Integer.valueOf(networkOperator.substring(3)).intValue();
                                }
                            }
                            this.f3776d = gsmCellLocation.getLac();
                            this.f3777e = gsmCellLocation.getCid();
                            C1206d.m3523c(this.f3786n);
                            this.f3786n.f3799e = new C1204b(this.f3786n, this.f3773a, this.f3774b, this.f3775c, this.f3776d, this.f3777e, this.f3778f, this.f3779g, this.f3780h);
                            if (this.f3786n.f3798d == null) {
                                this.f3786n.f3798d.mo2155a(this.f3786n.f3799e);
                            }
                        }
                        if (!(obj == null || gsmCellLocation == null)) {
                            networkOperator = this.f3786n.f3796b.getNetworkOperator();
                            if (networkOperator != null) {
                                if (networkOperator.length() > 3) {
                                    this.f3775c = Integer.valueOf(networkOperator.substring(3)).intValue();
                                }
                            }
                            this.f3776d = gsmCellLocation.getLac();
                            this.f3777e = gsmCellLocation.getCid();
                            C1206d.m3523c(this.f3786n);
                        }
                    case 2:
                        if (cellLocation != null) {
                            try {
                                if (this.f3781i == null) {
                                    this.f3781i = Class.forName("android.telephony.cdma.CdmaCellLocation").getMethod("getBaseStationId", new Class[0]);
                                    this.f3782j = Class.forName("android.telephony.cdma.CdmaCellLocation").getMethod("getSystemId", new Class[0]);
                                    this.f3783k = Class.forName("android.telephony.cdma.CdmaCellLocation").getMethod("getNetworkId", new Class[0]);
                                    this.f3784l = Class.forName("android.telephony.cdma.CdmaCellLocation").getMethod("getBaseStationLatitude", new Class[0]);
                                    this.f3785m = Class.forName("android.telephony.cdma.CdmaCellLocation").getMethod("getBaseStationLongitude", new Class[0]);
                                }
                                this.f3775c = ((Integer) this.f3782j.invoke(cellLocation, new Object[0])).intValue();
                                this.f3776d = ((Integer) this.f3783k.invoke(cellLocation, new Object[0])).intValue();
                                this.f3777e = ((Integer) this.f3781i.invoke(cellLocation, new Object[0])).intValue();
                                this.f3779g = ((Integer) this.f3784l.invoke(cellLocation, new Object[0])).intValue();
                                this.f3780h = ((Integer) this.f3785m.invoke(cellLocation, new Object[0])).intValue();
                                break;
                            } catch (Exception e4) {
                                this.f3777e = -1;
                                this.f3776d = -1;
                                this.f3775c = -1;
                                this.f3779g = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
                                this.f3780h = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
                                break;
                            }
                        }
                        break;
                }
            }
            this.f3786n.f3799e = new C1204b(this.f3786n, this.f3773a, this.f3774b, this.f3775c, this.f3776d, this.f3777e, this.f3778f, this.f3779g, this.f3780h);
            if (this.f3786n.f3798d == null) {
                this.f3786n.f3798d.mo2155a(this.f3786n.f3799e);
            }
        }

        public final void onSignalStrengthChanged(int i) {
            if (this.f3773a == 1) {
                C1206d.m3523c(this.f3786n);
            }
            if (Math.abs(i - ((this.f3778f + BtManagerService.CLASSIC_SYNC_SPORT_DATA) / 2)) <= 3) {
                return;
            }
            if (this.f3778f == -1) {
                this.f3778f = (i << 1) - 113;
                return;
            }
            this.f3778f = (i << 1) - 113;
            this.f3786n.f3799e = new C1204b(this.f3786n, this.f3773a, this.f3774b, this.f3775c, this.f3776d, this.f3777e, this.f3778f, this.f3779g, this.f3780h);
            if (this.f3786n.f3798d != null) {
                this.f3786n.f3798d.mo2155a(this.f3786n.f3799e);
            }
        }
    }

    public class C1204b implements Cloneable {
        public int f3787a = 0;
        public int f3788b = 0;
        public int f3789c = 0;
        public int f3790d = 0;
        public int f3791e = 0;
        public int f3792f = 0;
        public int f3793g = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        public int f3794h = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;

        public C1204b(C1206d c1206d, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            this.f3787a = i;
            this.f3788b = i2;
            this.f3789c = i3;
            this.f3790d = i4;
            this.f3791e = i5;
            this.f3792f = i6;
            this.f3793g = i7;
            this.f3794h = i8;
        }

        public final Object clone() {
            try {
                return (C1204b) super.clone();
            } catch (Exception e) {
                return null;
            }
        }
    }

    public interface C1205c {
        void mo2155a(C1204b c1204b);
    }

    private int m3518a(int i) {
        int intValue;
        String networkOperator = this.f3796b.getNetworkOperator();
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

    static /* synthetic */ void m3523c(C1206d c1206d) {
        if (!c1206d.f3804j) {
            c1206d.f3804j = true;
            new C12021(c1206d).start();
        }
    }

    public final void m3527a() {
        synchronized (this.f3802h) {
            if (this.f3800f) {
                if (!(this.f3796b == null || this.f3797c == null)) {
                    try {
                        this.f3796b.listen(this.f3797c, 0);
                    } catch (Exception e) {
                        this.f3800f = false;
                    }
                }
                this.f3800f = false;
                return;
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean m3528a(android.content.Context r6, com.tencent.map.p028b.C1206d.C1205c r7) {
        /*
        r5 = this;
        r0 = 1;
        r1 = 0;
        r2 = r5.f3802h;
        monitor-enter(r2);
        r3 = r5.f3800f;	 Catch:{ all -> 0x0055 }
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
        r5.f3795a = r6;	 Catch:{ all -> 0x0055 }
        r5.f3798d = r7;	 Catch:{ all -> 0x0055 }
        r0 = r5.f3795a;	 Catch:{ Exception -> 0x0051 }
        r3 = "phone";
        r0 = r0.getSystemService(r3);	 Catch:{ Exception -> 0x0051 }
        r0 = (android.telephony.TelephonyManager) r0;	 Catch:{ Exception -> 0x0051 }
        r5.f3796b = r0;	 Catch:{ Exception -> 0x0051 }
        r0 = r5.f3796b;	 Catch:{ Exception -> 0x0051 }
        if (r0 != 0) goto L_0x0029;
    L_0x0026:
        monitor-exit(r2);	 Catch:{ all -> 0x0055 }
        r0 = r1;
        goto L_0x000a;
    L_0x0029:
        r0 = r5.f3796b;	 Catch:{ Exception -> 0x0051 }
        r0 = r0.getPhoneType();	 Catch:{ Exception -> 0x0051 }
        r3 = r5.m3518a(r0);	 Catch:{ Exception -> 0x0051 }
        r4 = new com.tencent.map.b.d$a;	 Catch:{ Exception -> 0x0051 }
        r4.<init>(r5, r3, r0);	 Catch:{ Exception -> 0x0051 }
        r5.f3797c = r4;	 Catch:{ Exception -> 0x0051 }
        r0 = r5.f3797c;	 Catch:{ Exception -> 0x0051 }
        if (r0 != 0) goto L_0x0041;
    L_0x003e:
        monitor-exit(r2);	 Catch:{ all -> 0x0055 }
        r0 = r1;
        goto L_0x000a;
    L_0x0041:
        r0 = r5.f3796b;	 Catch:{ Exception -> 0x0051 }
        r3 = r5.f3797c;	 Catch:{ Exception -> 0x0051 }
        r4 = 18;
        r0.listen(r3, r4);	 Catch:{ Exception -> 0x0051 }
        r0 = 1;
        r5.f3800f = r0;	 Catch:{ all -> 0x0055 }
        monitor-exit(r2);	 Catch:{ all -> 0x0055 }
        r0 = r5.f3800f;
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
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.map.b.d.a(android.content.Context, com.tencent.map.b.d$c):boolean");
    }

    public final List<NeighboringCellInfo> m3529b() {
        List<NeighboringCellInfo> list = null;
        synchronized (this.f3803i) {
            if (this.f3801g != null) {
                list = new LinkedList();
                list.addAll(this.f3801g);
            }
        }
        return list;
    }
}
