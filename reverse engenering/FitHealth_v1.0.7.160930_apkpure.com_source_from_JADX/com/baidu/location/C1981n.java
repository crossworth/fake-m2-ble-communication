package com.baidu.location;

import android.os.Build.VERSION;
import android.telephony.CellLocation;
import android.telephony.NeighboringCellInfo;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

class C1981n implements an, C1619j {
    private static String cA = null;
    private static Method cB = null;
    private static boolean cC = false;
    private static Method cE = null;
    private static long cF = 3000;
    private static int cH = 3;
    private static Class cI = null;
    private static Method cv = null;
    private static C1981n cy = null;
    private int cD = 0;
    private boolean cG = false;
    private C0529a ct = new C0529a(this);
    private int cu = 0;
    private C0530b cw = null;
    private List cx = null;
    private TelephonyManager cz = null;

    public class C0529a {
        final /* synthetic */ C1981n f2269a;
        public long f2270byte;
        public int f2271do;
        public int f2272for;
        public int f2273if;
        public int f2274int;
        public char f2275new;
        public int f2276try;

        public C0529a(C1981n c1981n) {
            this.f2269a = c1981n;
            this.f2272for = -1;
            this.f2276try = -1;
            this.f2271do = -1;
            this.f2273if = -1;
            this.f2270byte = 0;
            this.f2274int = -1;
            this.f2275new = '\u0000';
            this.f2270byte = System.currentTimeMillis();
        }

        public C0529a(C1981n c1981n, int i, int i2, int i3, int i4, char c) {
            this.f2269a = c1981n;
            this.f2272for = -1;
            this.f2276try = -1;
            this.f2271do = -1;
            this.f2273if = -1;
            this.f2270byte = 0;
            this.f2274int = -1;
            this.f2275new = '\u0000';
            this.f2272for = i;
            this.f2276try = i2;
            this.f2271do = i3;
            this.f2273if = i4;
            this.f2275new = c;
            this.f2270byte = System.currentTimeMillis() / 1000;
        }

        public String m2199a() {
            StringBuffer stringBuffer = new StringBuffer(128);
            stringBuffer.append(this.f2276try + 23);
            stringBuffer.append("H");
            stringBuffer.append(this.f2272for + 45);
            stringBuffer.append("K");
            stringBuffer.append(this.f2273if + 54);
            stringBuffer.append("Q");
            stringBuffer.append(this.f2271do + 203);
            return stringBuffer.toString();
        }

        public boolean m2200a(C0529a c0529a) {
            return this.f2272for == c0529a.f2272for && this.f2276try == c0529a.f2276try && this.f2273if == c0529a.f2273if;
        }

        public boolean m2201do() {
            return System.currentTimeMillis() - this.f2270byte < C1981n.cF;
        }

        public boolean m2202for() {
            return this.f2272for > -1 && this.f2276try > 0;
        }

        public String m2203if() {
            StringBuffer stringBuffer = new StringBuffer(64);
            stringBuffer.append(String.format(Locale.CHINA, "cell=%d|%d|%d|%d:%d", new Object[]{Integer.valueOf(this.f2271do), Integer.valueOf(this.f2273if), Integer.valueOf(this.f2272for), Integer.valueOf(this.f2276try), Integer.valueOf(this.f2274int)}));
            return stringBuffer.toString();
        }

        public String m2204int() {
            try {
                List<NeighboringCellInfo> neighboringCellInfo = this.f2269a.cz.getNeighboringCellInfo();
                if (neighboringCellInfo == null || neighboringCellInfo.isEmpty()) {
                    return null;
                }
                String str = "&nc=";
                int i = 0;
                for (NeighboringCellInfo neighboringCellInfo2 : neighboringCellInfo) {
                    String str2;
                    if (i != 0) {
                        if (i >= 8) {
                            break;
                        }
                        str2 = neighboringCellInfo2.getLac() != this.f2272for ? str + ";" + neighboringCellInfo2.getLac() + "|" + neighboringCellInfo2.getCid() + "|" + neighboringCellInfo2.getRssi() : str + ";" + "|" + neighboringCellInfo2.getCid() + "|" + neighboringCellInfo2.getRssi();
                    } else {
                        str2 = neighboringCellInfo2.getLac() != this.f2272for ? str + neighboringCellInfo2.getLac() + "|" + neighboringCellInfo2.getCid() + "|" + neighboringCellInfo2.getRssi() : str + "|" + neighboringCellInfo2.getCid() + "|" + neighboringCellInfo2.getRssi();
                    }
                    i++;
                    str = str2;
                }
                return str;
            } catch (Exception e) {
                return null;
            }
        }

        public String toString() {
            StringBuffer stringBuffer = new StringBuffer(128);
            stringBuffer.append("&nw=");
            stringBuffer.append(this.f2269a.ct.f2275new);
            stringBuffer.append(String.format(Locale.CHINA, "&cl=%d|%d|%d|%d&cl_s=%d", new Object[]{Integer.valueOf(this.f2271do), Integer.valueOf(this.f2273if), Integer.valueOf(this.f2272for), Integer.valueOf(this.f2276try), Integer.valueOf(this.f2274int)}));
            stringBuffer.append("&cl_t=");
            stringBuffer.append(this.f2270byte);
            if (this.f2269a.cx != null && this.f2269a.cx.size() > 0) {
                int size = this.f2269a.cx.size();
                stringBuffer.append("&clt=");
                for (int i = 0; i < size; i++) {
                    C0529a c0529a = (C0529a) this.f2269a.cx.get(i);
                    if (c0529a.f2271do != this.f2271do) {
                        stringBuffer.append(c0529a.f2271do);
                    }
                    stringBuffer.append("|");
                    if (c0529a.f2273if != this.f2273if) {
                        stringBuffer.append(c0529a.f2273if);
                    }
                    stringBuffer.append("|");
                    if (c0529a.f2272for != this.f2272for) {
                        stringBuffer.append(c0529a.f2272for);
                    }
                    stringBuffer.append("|");
                    if (c0529a.f2276try != this.f2276try) {
                        stringBuffer.append(c0529a.f2276try);
                    }
                    stringBuffer.append("|");
                    if (i != size - 1) {
                        stringBuffer.append(c0529a.f2270byte / 1000);
                    } else {
                        stringBuffer.append((System.currentTimeMillis() - c0529a.f2270byte) / 1000);
                    }
                    stringBuffer.append(";");
                }
            }
            if (this.f2269a.cD > 100) {
                this.f2269a.cD = 0;
            }
            stringBuffer.append("&cs=" + ((this.f2269a.cu << 8) + this.f2269a.cD));
            return stringBuffer.toString();
        }
    }

    private class C0530b extends PhoneStateListener {
        final /* synthetic */ C1981n f2277a;

        public C0530b(C1981n c1981n) {
            this.f2277a = c1981n;
        }

        public void onCellLocationChanged(CellLocation cellLocation) {
            if (cellLocation != null) {
                try {
                    this.f2277a.m6014if(this.f2277a.cz.getCellLocation());
                } catch (Exception e) {
                }
            }
        }

        public void onSignalStrengthsChanged(SignalStrength signalStrength) {
            if (this.f2277a.ct != null) {
                if (this.f2277a.ct.f2275new == 'g') {
                    this.f2277a.ct.f2274int = signalStrength.getGsmSignalStrength();
                } else if (this.f2277a.ct.f2275new == 'c') {
                    this.f2277a.ct.f2274int = signalStrength.getCdmaDbm();
                }
                C1974b.m5922if("cell strength", "===== cell singal strength changed : " + this.f2277a.ct.f2274int);
            }
        }
    }

    private C1981n() {
    }

    private boolean m6007I() {
        if (cA == null || cA.length() < 10) {
            return false;
        }
        try {
            char[] toCharArray = cA.toCharArray();
            int i = 0;
            while (i < 10) {
                if (toCharArray[i] > '9' || toCharArray[i] < '0') {
                    return false;
                }
                i++;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static C1981n m6008K() {
        if (cy == null) {
            cy = new C1981n();
        }
        return cy;
    }

    private void m6014if(CellLocation cellLocation) {
        if (cellLocation != null && this.cz != null) {
            int intValue;
            if (!cC) {
                cA = this.cz.getDeviceId();
                cC = m6007I();
            }
            C0529a c0529a = new C0529a(this);
            c0529a.f2270byte = System.currentTimeMillis();
            try {
                String networkOperator = this.cz.getNetworkOperator();
                if (networkOperator != null && networkOperator.length() > 0) {
                    if (networkOperator.length() >= 3) {
                        intValue = Integer.valueOf(networkOperator.substring(0, 3)).intValue();
                        if (intValue < 0) {
                            intValue = this.ct.f2271do;
                        }
                        c0529a.f2271do = intValue;
                    }
                    networkOperator = networkOperator.substring(3);
                    if (networkOperator != null) {
                        char[] toCharArray = networkOperator.toCharArray();
                        intValue = 0;
                        while (intValue < toCharArray.length && Character.isDigit(toCharArray[intValue])) {
                            intValue++;
                        }
                    } else {
                        intValue = 0;
                    }
                    intValue = Integer.valueOf(networkOperator.substring(0, intValue)).intValue();
                    if (intValue < 0) {
                        intValue = this.ct.f2273if;
                    }
                    c0529a.f2273if = intValue;
                }
                this.cD = this.cz.getSimState();
            } catch (Exception e) {
                e.printStackTrace();
                this.cu = 1;
            }
            if (cellLocation instanceof GsmCellLocation) {
                c0529a.f2272for = ((GsmCellLocation) cellLocation).getLac();
                c0529a.f2276try = ((GsmCellLocation) cellLocation).getCid();
                c0529a.f2275new = 'g';
            } else if (cellLocation instanceof CdmaCellLocation) {
                c0529a.f2275new = 'c';
                if (Integer.parseInt(VERSION.SDK) >= 5) {
                    if (cI == null) {
                        try {
                            cI = Class.forName("android.telephony.cdma.CdmaCellLocation");
                            cE = cI.getMethod("getBaseStationId", new Class[0]);
                            cB = cI.getMethod("getNetworkId", new Class[0]);
                            cv = cI.getMethod("getSystemId", new Class[0]);
                        } catch (Exception e2) {
                            cI = null;
                            e2.printStackTrace();
                            this.cu = 2;
                            return;
                        }
                    }
                    if (cI != null && cI.isInstance(cellLocation)) {
                        try {
                            intValue = ((Integer) cv.invoke(cellLocation, new Object[0])).intValue();
                            if (intValue < 0) {
                                intValue = this.ct.f2273if;
                            }
                            c0529a.f2273if = intValue;
                            c0529a.f2276try = ((Integer) cE.invoke(cellLocation, new Object[0])).intValue();
                            c0529a.f2272for = ((Integer) cB.invoke(cellLocation, new Object[0])).intValue();
                        } catch (Exception e22) {
                            e22.printStackTrace();
                            this.cu = 3;
                            return;
                        }
                    }
                }
                return;
            }
            if (!c0529a.m2202for()) {
                return;
            }
            if (this.ct == null || !this.ct.m2200a(c0529a)) {
                this.ct = c0529a;
                if (c0529a.m2202for()) {
                    if (this.cx == null) {
                        this.cx = new LinkedList();
                    }
                    intValue = this.cx.size();
                    C0529a c0529a2 = intValue == 0 ? null : (C0529a) this.cx.get(intValue - 1);
                    if (c0529a2 == null || c0529a2.f2276try != this.ct.f2276try || c0529a2.f2272for != this.ct.f2272for) {
                        if (c0529a2 != null) {
                            c0529a2.f2270byte = this.ct.f2270byte - c0529a2.f2270byte;
                        }
                        this.cx.add(this.ct);
                        if (this.cx.size() > cH) {
                            this.cx.remove(0);
                        }
                    }
                } else if (this.cx != null) {
                    this.cx.clear();
                }
            }
        }
    }

    public C0529a m6018H() {
        if (!((this.ct != null && this.ct.m2201do() && this.ct.m2202for()) || this.cz == null)) {
            try {
                m6014if(this.cz.getCellLocation());
            } catch (Exception e) {
            }
        }
        return this.ct;
    }

    public void m6019J() {
        if (!this.cG) {
            this.cz = (TelephonyManager) C1976f.getServiceContext().getSystemService("phone");
            this.cx = new LinkedList();
            this.cw = new C0530b(this);
            if (this.cz != null && this.cw != null) {
                try {
                    this.cz.listen(this.cw, 272);
                } catch (Exception e) {
                }
                cC = m6007I();
                this.cG = true;
            }
        }
    }

    public String m6020L() {
        return cA;
    }

    public int m6021M() {
        return this.cz == null ? 0 : this.cz.getNetworkType();
    }

    public void m6022O() {
        if (this.cG) {
            if (!(this.cw == null || this.cz == null)) {
                this.cz.listen(this.cw, 0);
            }
            this.cw = null;
            this.cz = null;
            this.cx.clear();
            this.cx = null;
            this.cG = false;
        }
    }
}
