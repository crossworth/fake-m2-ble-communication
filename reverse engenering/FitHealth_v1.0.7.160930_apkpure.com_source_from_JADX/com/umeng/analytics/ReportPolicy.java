package com.umeng.analytics;

import android.content.Context;
import com.zhuoyi.system.statistics.sale.util.StatsSaleConst;
import p031u.aly.ak;
import p031u.aly.as;
import p031u.aly.ax;
import p031u.aly.bj;

public class ReportPolicy {
    public static final int BATCH_AT_LAUNCH = 1;
    public static final int BATCH_BY_INTERVAL = 6;
    public static final int DAILY = 4;
    public static final int REALTIME = 0;
    public static final int SMART_POLICY = 8;
    public static final int WIFIONLY = 5;
    static final int f3101a = 2;
    static final int f3102b = 3;

    public static class C0918i {
        public boolean mo2150a(boolean z) {
            return true;
        }

        public boolean mo2151a() {
            return true;
        }
    }

    public static class C1769a extends C0918i {
        private final long f4741a = 15000;
        private as f4742b;

        public C1769a(as asVar) {
            this.f4742b = asVar;
        }

        public boolean mo2150a(boolean z) {
            if (System.currentTimeMillis() - this.f4742b.f4908c >= 15000) {
                return true;
            }
            return false;
        }
    }

    public static class C1770b extends C0918i {
        private ax f4743a;
        private as f4744b;

        public C1770b(as asVar, ax axVar) {
            this.f4744b = asVar;
            this.f4743a = axVar;
        }

        public boolean mo2150a(boolean z) {
            long currentTimeMillis = System.currentTimeMillis();
            if (currentTimeMillis - this.f4744b.f4908c >= this.f4743a.m5120a()) {
                return true;
            }
            return false;
        }

        public boolean mo2151a() {
            return this.f4743a.m5126c();
        }
    }

    public static class C1771c extends C0918i {
        private long f4745a;
        private long f4746b = 0;

        public C1771c(int i) {
            this.f4745a = (long) i;
            this.f4746b = System.currentTimeMillis();
        }

        public boolean mo2150a(boolean z) {
            if (System.currentTimeMillis() - this.f4746b >= this.f4745a) {
                return true;
            }
            return false;
        }

        public boolean mo2151a() {
            return System.currentTimeMillis() - this.f4746b < this.f4745a;
        }
    }

    public static class C1772d extends C0918i {
        public boolean mo2150a(boolean z) {
            return z;
        }
    }

    public static class C1773e extends C0918i {
        private static long f4747a = 90000;
        private static long f4748b = 86400000;
        private long f4749c;
        private as f4750d;

        public C1773e(as asVar, long j) {
            this.f4750d = asVar;
            m4911a(j);
        }

        public boolean mo2150a(boolean z) {
            if (System.currentTimeMillis() - this.f4750d.f4908c >= this.f4749c) {
                return true;
            }
            return false;
        }

        public void m4911a(long j) {
            if (j < f4747a || j > f4748b) {
                this.f4749c = f4747a;
            } else {
                this.f4749c = j;
            }
        }

        public long m4913b() {
            return this.f4749c;
        }

        public static boolean m4910a(int i) {
            if (((long) i) < f4747a) {
                return false;
            }
            return true;
        }
    }

    public static class C1774f extends C0918i {
        private final int f4751a;
        private ak f4752b;

        public C1774f(ak akVar, int i) {
            this.f4751a = i;
            this.f4752b = akVar;
        }

        public boolean mo2150a(boolean z) {
            return this.f4752b.m3437b() > this.f4751a;
        }
    }

    public static class C1775g extends C0918i {
        private long f4753a = 86400000;
        private as f4754b;

        public C1775g(as asVar) {
            this.f4754b = asVar;
        }

        public boolean mo2150a(boolean z) {
            if (System.currentTimeMillis() - this.f4754b.f4908c >= this.f4753a) {
                return true;
            }
            return false;
        }
    }

    public static class C1776h extends C0918i {
        public boolean mo2150a(boolean z) {
            return true;
        }
    }

    public static class C1777j extends C0918i {
        private Context f4755a = null;

        public C1777j(Context context) {
            this.f4755a = context;
        }

        public boolean mo2150a(boolean z) {
            return bj.m3540n(this.f4755a);
        }
    }

    public static class C1778k extends C0918i {
        private final long f4756a = StatsSaleConst.CHECK_SALESTATS_ACTIVE_TIME;
        private as f4757b;

        public C1778k(as asVar) {
            this.f4757b = asVar;
        }

        public boolean mo2150a(boolean z) {
            if (System.currentTimeMillis() - this.f4757b.f4908c >= StatsSaleConst.CHECK_SALESTATS_ACTIVE_TIME) {
                return true;
            }
            return false;
        }
    }

    public static boolean m3060a(int i) {
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 8:
                return true;
            default:
                return false;
        }
    }
}
