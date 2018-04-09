package com.baidu.location.p012f;

import android.annotation.SuppressLint;
import android.os.Build.VERSION;
import android.os.SystemClock;
import android.telephony.CellIdentityCdma;
import android.telephony.CellIdentityGsm;
import android.telephony.CellIdentityLte;
import android.telephony.CellIdentityWcdma;
import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.CellLocation;
import android.telephony.NeighboringCellInfo;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import com.baidu.location.C0455f;
import com.baidu.location.p006h.C0458a;
import com.baidu.location.p006h.C0468j;
import com.baidu.location.p007b.C0367b;
import java.io.File;
import java.io.RandomAccessFile;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import org.andengine.util.time.TimeConstants;

@SuppressLint({"NewApi"})
public class C0443b {
    public static int f738a = 0;
    public static int f739b = 0;
    private static C0443b f740c = null;
    private static Method f741k = null;
    private static Method f742l = null;
    private static Method f743m = null;
    private static Method f744n = null;
    private static Method f745o = null;
    private static Class<?> f746p = null;
    private TelephonyManager f747d = null;
    private Object f748e = null;
    private C0441a f749f = new C0441a();
    private C0441a f750g = null;
    private List<C0441a> f751h = null;
    private C0442a f752i = null;
    private boolean f753j = false;
    private boolean f754q = false;

    private class C0442a extends PhoneStateListener {
        final /* synthetic */ C0443b f737a;

        public C0442a(C0443b c0443b) {
            this.f737a = c0443b;
        }

        public void onCellLocationChanged(CellLocation cellLocation) {
            if (cellLocation != null) {
                try {
                    this.f737a.m862k();
                } catch (Exception e) {
                }
                C0367b.m381a().m391e();
            }
        }

        public void onSignalStrengthsChanged(SignalStrength signalStrength) {
            if (this.f737a.f749f == null) {
                return;
            }
            if (this.f737a.f749f.f735i == 'g') {
                this.f737a.f749f.f734h = signalStrength.getGsmSignalStrength();
            } else if (this.f737a.f749f.f735i == 'c') {
                this.f737a.f749f.f734h = signalStrength.getCdmaDbm();
            }
        }
    }

    private C0443b() {
    }

    private int m850a(int i) {
        return i == ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED ? -1 : i;
    }

    private CellLocation m851a(List<?> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        int i;
        CellLocation cdmaCellLocation;
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        CellLocation cellLocation = null;
        int i2 = 0;
        CellLocation cellLocation2 = null;
        for (int i3 = 0; i3 < list.size(); i3++) {
            Object obj = list.get(i3);
            if (obj != null) {
                try {
                    Class loadClass = systemClassLoader.loadClass("android.telephony.CellInfoGsm");
                    Class loadClass2 = systemClassLoader.loadClass("android.telephony.CellInfoWcdma");
                    Class loadClass3 = systemClassLoader.loadClass("android.telephony.CellInfoLte");
                    Class loadClass4 = systemClassLoader.loadClass("android.telephony.CellInfoCdma");
                    i = loadClass.isInstance(obj) ? 1 : loadClass2.isInstance(obj) ? 2 : loadClass3.isInstance(obj) ? 3 : loadClass4.isInstance(obj) ? 4 : 0;
                    if (i > 0) {
                        Object obj2 = null;
                        if (i == 1) {
                            try {
                                obj2 = loadClass.cast(obj);
                            } catch (Exception e) {
                                i2 = i;
                            }
                        } else if (i == 2) {
                            obj2 = loadClass2.cast(obj);
                        } else if (i == 3) {
                            obj2 = loadClass3.cast(obj);
                        } else if (i == 4) {
                            obj2 = loadClass4.cast(obj);
                        }
                        obj = C0468j.m1011a(obj2, "getCellIdentity", new Object[0]);
                        if (obj == null) {
                            i2 = i;
                        } else if (i == 4) {
                            cdmaCellLocation = new CdmaCellLocation();
                            try {
                                cdmaCellLocation.setCellLocationData(C0468j.m1019b(obj, "getBasestationId", new Object[0]), C0468j.m1019b(obj, "getLatitude", new Object[0]), C0468j.m1019b(obj, "getLongitude", new Object[0]), C0468j.m1019b(obj, "getSystemId", new Object[0]), C0468j.m1019b(obj, "getNetworkId", new Object[0]));
                                cellLocation2 = cellLocation;
                                break;
                            } catch (Exception e2) {
                                cellLocation2 = cdmaCellLocation;
                                i2 = i;
                            }
                        } else if (i == 3) {
                            r3 = C0468j.m1019b(obj, "getTac", new Object[0]);
                            r2 = C0468j.m1019b(obj, "getCi", new Object[0]);
                            cdmaCellLocation = new GsmCellLocation();
                            try {
                                cdmaCellLocation.setLacAndCid(r3, r2);
                                r12 = cellLocation2;
                                cellLocation2 = cdmaCellLocation;
                                cdmaCellLocation = r12;
                                break;
                            } catch (Exception e3) {
                                cellLocation = cdmaCellLocation;
                                i2 = i;
                            }
                        } else {
                            r3 = C0468j.m1019b(obj, "getLac", new Object[0]);
                            r2 = C0468j.m1019b(obj, "getCid", new Object[0]);
                            cdmaCellLocation = new GsmCellLocation();
                            try {
                                cdmaCellLocation.setLacAndCid(r3, r2);
                                r12 = cellLocation2;
                                cellLocation2 = cdmaCellLocation;
                                cdmaCellLocation = r12;
                                break;
                            } catch (Exception e4) {
                                cellLocation = cdmaCellLocation;
                                i2 = i;
                            }
                        }
                    } else {
                        i2 = i;
                    }
                } catch (Exception e5) {
                }
            }
        }
        i = i2;
        cdmaCellLocation = cellLocation2;
        cellLocation2 = cellLocation;
        return i != 4 ? cellLocation2 : cdmaCellLocation;
    }

    @SuppressLint({"NewApi"})
    private C0441a m852a(CellInfo cellInfo) {
        Object obj = null;
        int i = -1;
        int intValue = Integer.valueOf(VERSION.SDK_INT).intValue();
        if (intValue < 17) {
            return null;
        }
        C0441a c0441a = new C0441a();
        if (cellInfo instanceof CellInfoGsm) {
            CellIdentityGsm cellIdentity = ((CellInfoGsm) cellInfo).getCellIdentity();
            c0441a.f729c = m850a(cellIdentity.getMcc());
            c0441a.f730d = m850a(cellIdentity.getMnc());
            c0441a.f727a = m850a(cellIdentity.getLac());
            c0441a.f728b = m850a(cellIdentity.getCid());
            c0441a.f735i = 'g';
            c0441a.f734h = ((CellInfoGsm) cellInfo).getCellSignalStrength().getAsuLevel();
            obj = 1;
        } else if (cellInfo instanceof CellInfoCdma) {
            CellIdentityCdma cellIdentity2 = ((CellInfoCdma) cellInfo).getCellIdentity();
            c0441a.f731e = cellIdentity2.getLatitude();
            c0441a.f732f = cellIdentity2.getLongitude();
            c0441a.f730d = m850a(cellIdentity2.getSystemId());
            c0441a.f727a = m850a(cellIdentity2.getNetworkId());
            c0441a.f728b = m850a(cellIdentity2.getBasestationId());
            c0441a.f735i = 'c';
            c0441a.f734h = ((CellInfoCdma) cellInfo).getCellSignalStrength().getCdmaDbm();
            if (this.f749f == null || this.f749f.f729c <= 0) {
                try {
                    String networkOperator = this.f747d.getNetworkOperator();
                    if (networkOperator != null && networkOperator.length() > 0 && networkOperator.length() >= 3) {
                        r2 = Integer.valueOf(networkOperator.substring(0, 3)).intValue();
                        if (r2 < 0) {
                            r2 = -1;
                        }
                        i = r2;
                    }
                } catch (Exception e) {
                }
                if (i > 0) {
                    c0441a.f729c = i;
                }
            } else {
                c0441a.f729c = this.f749f.f729c;
            }
            r2 = 1;
        } else if (cellInfo instanceof CellInfoLte) {
            CellIdentityLte cellIdentity3 = ((CellInfoLte) cellInfo).getCellIdentity();
            c0441a.f729c = m850a(cellIdentity3.getMcc());
            c0441a.f730d = m850a(cellIdentity3.getMnc());
            c0441a.f727a = m850a(cellIdentity3.getTac());
            c0441a.f728b = m850a(cellIdentity3.getCi());
            c0441a.f735i = 'g';
            c0441a.f734h = ((CellInfoLte) cellInfo).getCellSignalStrength().getAsuLevel();
            r2 = 1;
        }
        if (intValue >= 18 && r2 == null) {
            try {
                if (cellInfo instanceof CellInfoWcdma) {
                    CellIdentityWcdma cellIdentity4 = ((CellInfoWcdma) cellInfo).getCellIdentity();
                    c0441a.f729c = m850a(cellIdentity4.getMcc());
                    c0441a.f730d = m850a(cellIdentity4.getMnc());
                    c0441a.f727a = m850a(cellIdentity4.getLac());
                    c0441a.f728b = m850a(cellIdentity4.getCid());
                    c0441a.f735i = 'g';
                    c0441a.f734h = ((CellInfoWcdma) cellInfo).getCellSignalStrength().getAsuLevel();
                }
            } catch (Exception e2) {
            }
        }
        try {
            c0441a.f733g = System.currentTimeMillis() - ((SystemClock.elapsedRealtimeNanos() - cellInfo.getTimeStamp()) / TimeConstants.NANOSECONDS_PER_MILLISECOND);
        } catch (Error e3) {
            c0441a.f733g = System.currentTimeMillis();
        }
        return c0441a;
    }

    private C0441a m853a(CellLocation cellLocation) {
        return m854a(cellLocation, false);
    }

    private C0441a m854a(CellLocation cellLocation, boolean z) {
        int i = 0;
        if (cellLocation == null || this.f747d == null) {
            return null;
        }
        C0441a c0441a = new C0441a();
        if (z) {
            c0441a.m847f();
        }
        c0441a.f733g = System.currentTimeMillis();
        try {
            String networkOperator = this.f747d.getNetworkOperator();
            if (networkOperator != null && networkOperator.length() > 0) {
                if (networkOperator.length() >= 3) {
                    int intValue = Integer.valueOf(networkOperator.substring(0, 3)).intValue();
                    if (intValue < 0) {
                        intValue = this.f749f.f729c;
                    }
                    c0441a.f729c = intValue;
                }
                String substring = networkOperator.substring(3);
                if (substring != null) {
                    char[] toCharArray = substring.toCharArray();
                    while (i < toCharArray.length && Character.isDigit(toCharArray[i])) {
                        i++;
                    }
                }
                i = Integer.valueOf(substring.substring(0, i)).intValue();
                if (i < 0) {
                    i = this.f749f.f730d;
                }
                c0441a.f730d = i;
            }
            f738a = this.f747d.getSimState();
        } catch (Exception e) {
            f739b = 1;
        }
        if (cellLocation instanceof GsmCellLocation) {
            c0441a.f727a = ((GsmCellLocation) cellLocation).getLac();
            c0441a.f728b = ((GsmCellLocation) cellLocation).getCid();
            c0441a.f735i = 'g';
        } else if (cellLocation instanceof CdmaCellLocation) {
            c0441a.f735i = 'c';
            if (Integer.valueOf(VERSION.SDK_INT).intValue() < 5) {
                return c0441a;
            }
            if (f746p == null) {
                try {
                    f746p = Class.forName("android.telephony.cdma.CdmaCellLocation");
                    f741k = f746p.getMethod("getBaseStationId", new Class[0]);
                    f742l = f746p.getMethod("getNetworkId", new Class[0]);
                    f743m = f746p.getMethod("getSystemId", new Class[0]);
                    f744n = f746p.getMethod("getBaseStationLatitude", new Class[0]);
                    f745o = f746p.getMethod("getBaseStationLongitude", new Class[0]);
                } catch (Exception e2) {
                    f746p = null;
                    f739b = 2;
                    return c0441a;
                }
            }
            if (f746p != null && f746p.isInstance(cellLocation)) {
                try {
                    int intValue2 = ((Integer) f743m.invoke(cellLocation, new Object[0])).intValue();
                    if (intValue2 < 0) {
                        intValue2 = this.f749f.f730d;
                    }
                    c0441a.f730d = intValue2;
                    c0441a.f728b = ((Integer) f741k.invoke(cellLocation, new Object[0])).intValue();
                    c0441a.f727a = ((Integer) f742l.invoke(cellLocation, new Object[0])).intValue();
                    Object invoke = f744n.invoke(cellLocation, new Object[0]);
                    if (((Integer) invoke).intValue() < ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED) {
                        c0441a.f731e = ((Integer) invoke).intValue();
                    }
                    invoke = f745o.invoke(cellLocation, new Object[0]);
                    if (((Integer) invoke).intValue() < ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED) {
                        c0441a.f732f = ((Integer) invoke).intValue();
                    }
                } catch (Exception e3) {
                    f739b = 3;
                    return c0441a;
                }
            }
        }
        m858c(c0441a);
        return c0441a;
    }

    public static synchronized C0443b m855a() {
        C0443b c0443b;
        synchronized (C0443b.class) {
            if (f740c == null) {
                f740c = new C0443b();
            }
            c0443b = f740c;
        }
        return c0443b;
    }

    private void m858c(C0441a c0441a) {
        if (!c0441a.m843b()) {
            return;
        }
        if (this.f749f == null || !this.f749f.m842a(c0441a)) {
            this.f749f = c0441a;
            if (c0441a.m843b()) {
                int size = this.f751h.size();
                C0441a c0441a2 = size == 0 ? null : (C0441a) this.f751h.get(size - 1);
                if (c0441a2 == null || c0441a2.f728b != this.f749f.f728b || c0441a2.f727a != this.f749f.f727a) {
                    this.f751h.add(this.f749f);
                    if (this.f751h.size() > 3) {
                        this.f751h.remove(0);
                    }
                    m861j();
                    this.f754q = false;
                }
            } else if (this.f751h != null) {
                this.f751h.clear();
            }
        }
    }

    @SuppressLint({"NewApi"})
    private String m859d(C0441a c0441a) {
        StringBuilder stringBuilder = new StringBuilder();
        if (Integer.valueOf(VERSION.SDK_INT).intValue() >= 17) {
            try {
                List<CellInfo> allCellInfo = this.f747d.getAllCellInfo();
                if (allCellInfo != null && allCellInfo.size() > 0) {
                    stringBuilder.append("&nc=");
                    for (CellInfo cellInfo : allCellInfo) {
                        if (!cellInfo.isRegistered()) {
                            C0441a a = m852a(cellInfo);
                            if (!(a == null || a.f727a == -1 || a.f728b == -1)) {
                                if (c0441a.f727a != a.f727a) {
                                    stringBuilder.append(a.f727a + "|" + a.f728b + "|" + a.f734h + ";");
                                } else {
                                    stringBuilder.append("|" + a.f728b + "|" + a.f734h + ";");
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
            } catch (NoSuchMethodError e2) {
            }
        }
        return stringBuilder.toString();
    }

    private void m860i() {
        String f = C0468j.m1028f();
        if (f != null) {
            File file = new File(f + File.separator + "lcvif.dat");
            if (file.exists()) {
                try {
                    RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
                    randomAccessFile.seek(0);
                    if (System.currentTimeMillis() - randomAccessFile.readLong() > 60000) {
                        randomAccessFile.close();
                        file.delete();
                        return;
                    }
                    randomAccessFile.readInt();
                    for (int i = 0; i < 3; i++) {
                        long readLong = randomAccessFile.readLong();
                        int readInt = randomAccessFile.readInt();
                        int readInt2 = randomAccessFile.readInt();
                        int readInt3 = randomAccessFile.readInt();
                        int readInt4 = randomAccessFile.readInt();
                        int readInt5 = randomAccessFile.readInt();
                        char c = '\u0000';
                        if (readInt5 == 1) {
                            c = 'g';
                        }
                        if (readInt5 == 2) {
                            c = 'c';
                        }
                        if (readLong != 0) {
                            C0441a c0441a = new C0441a(readInt3, readInt4, readInt, readInt2, 0, c);
                            c0441a.f733g = readLong;
                            if (c0441a.m843b()) {
                                this.f754q = true;
                                this.f751h.add(c0441a);
                            }
                        }
                    }
                    randomAccessFile.close();
                } catch (Exception e) {
                    file.delete();
                }
            }
        }
    }

    private void m861j() {
        int i = 0;
        if (this.f751h != null || this.f750g != null) {
            if (this.f751h == null && this.f750g != null) {
                this.f751h = new LinkedList();
                this.f751h.add(this.f750g);
            }
            String f = C0468j.m1028f();
            if (f != null) {
                File file = new File(f + File.separator + "lcvif.dat");
                int size = this.f751h.size();
                try {
                    if (file.exists()) {
                        file.delete();
                    }
                    file.createNewFile();
                    RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
                    randomAccessFile.seek(0);
                    randomAccessFile.writeLong(((C0441a) this.f751h.get(size - 1)).f733g);
                    randomAccessFile.writeInt(size);
                    for (int i2 = 0; i2 < 3 - size; i2++) {
                        randomAccessFile.writeLong(0);
                        randomAccessFile.writeInt(-1);
                        randomAccessFile.writeInt(-1);
                        randomAccessFile.writeInt(-1);
                        randomAccessFile.writeInt(-1);
                        randomAccessFile.writeInt(2);
                    }
                    while (i < size) {
                        randomAccessFile.writeLong(((C0441a) this.f751h.get(i)).f733g);
                        randomAccessFile.writeInt(((C0441a) this.f751h.get(i)).f729c);
                        randomAccessFile.writeInt(((C0441a) this.f751h.get(i)).f730d);
                        randomAccessFile.writeInt(((C0441a) this.f751h.get(i)).f727a);
                        randomAccessFile.writeInt(((C0441a) this.f751h.get(i)).f728b);
                        if (((C0441a) this.f751h.get(i)).f735i == 'g') {
                            randomAccessFile.writeInt(1);
                        } else if (((C0441a) this.f751h.get(i)).f735i == 'c') {
                            randomAccessFile.writeInt(2);
                        } else {
                            randomAccessFile.writeInt(3);
                        }
                        i++;
                    }
                    randomAccessFile.close();
                } catch (Exception e) {
                }
            }
        }
    }

    private void m862k() {
        C0441a n = m865n();
        if (n != null) {
            m858c(n);
        }
        if (n == null || !n.m843b()) {
            n = m853a(this.f747d.getCellLocation());
            if (n == null || !n.m843b()) {
                CellLocation l = m863l();
                if (l != null) {
                    Log.i(C0458a.f826a, "cell sim2 cell is valid");
                    m854a(l, true);
                }
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private android.telephony.CellLocation m863l() {
        /*
        r7 = this;
        r1 = 0;
        r0 = r7.f748e;
        if (r0 != 0) goto L_0x0006;
    L_0x0005:
        return r1;
    L_0x0006:
        r2 = r7.m864m();	 Catch:{ Exception -> 0x0066 }
        r3 = r2.isInstance(r0);	 Catch:{ Exception -> 0x0066 }
        if (r3 == 0) goto L_0x0073;
    L_0x0010:
        r2 = r2.cast(r0);	 Catch:{ Exception -> 0x0066 }
        r3 = "getCellLocation";
        r0 = 0;
        r0 = new java.lang.Object[r0];	 Catch:{ NoSuchMethodException -> 0x005a, Exception -> 0x005d }
        r0 = com.baidu.location.p006h.C0468j.m1011a(r2, r3, r0);	 Catch:{ NoSuchMethodException -> 0x005a, Exception -> 0x005d }
    L_0x001d:
        if (r0 != 0) goto L_0x002e;
    L_0x001f:
        r4 = 1;
        r4 = new java.lang.Object[r4];	 Catch:{ NoSuchMethodException -> 0x006f, Exception -> 0x006d }
        r5 = 0;
        r6 = 1;
        r6 = java.lang.Integer.valueOf(r6);	 Catch:{ NoSuchMethodException -> 0x006f, Exception -> 0x006d }
        r4[r5] = r6;	 Catch:{ NoSuchMethodException -> 0x006f, Exception -> 0x006d }
        r0 = com.baidu.location.p006h.C0468j.m1011a(r2, r3, r4);	 Catch:{ NoSuchMethodException -> 0x006f, Exception -> 0x006d }
    L_0x002e:
        if (r0 != 0) goto L_0x0041;
    L_0x0030:
        r3 = "getCellLocationGemini";
        r4 = 1;
        r4 = new java.lang.Object[r4];	 Catch:{ NoSuchMethodException -> 0x006b, Exception -> 0x0069 }
        r5 = 0;
        r6 = 1;
        r6 = java.lang.Integer.valueOf(r6);	 Catch:{ NoSuchMethodException -> 0x006b, Exception -> 0x0069 }
        r4[r5] = r6;	 Catch:{ NoSuchMethodException -> 0x006b, Exception -> 0x0069 }
        r0 = com.baidu.location.p006h.C0468j.m1011a(r2, r3, r4);	 Catch:{ NoSuchMethodException -> 0x006b, Exception -> 0x0069 }
    L_0x0041:
        if (r0 != 0) goto L_0x0054;
    L_0x0043:
        r0 = "getAllCellInfo";
        r3 = 0;
        r3 = new java.lang.Object[r3];	 Catch:{ NoSuchMethodException -> 0x0060, Exception -> 0x0063 }
        r0 = com.baidu.location.p006h.C0468j.m1011a(r2, r0, r3);	 Catch:{ NoSuchMethodException -> 0x0060, Exception -> 0x0063 }
        r0 = (java.util.List) r0;	 Catch:{ NoSuchMethodException -> 0x0060, Exception -> 0x0063 }
    L_0x004e:
        r0 = r7.m851a(r0);	 Catch:{ Exception -> 0x0066 }
        if (r0 == 0) goto L_0x0054;
    L_0x0054:
        if (r0 == 0) goto L_0x0071;
    L_0x0056:
        r0 = (android.telephony.CellLocation) r0;	 Catch:{ Exception -> 0x0066 }
    L_0x0058:
        r1 = r0;
        goto L_0x0005;
    L_0x005a:
        r0 = move-exception;
        r0 = r1;
        goto L_0x001d;
    L_0x005d:
        r0 = move-exception;
        r0 = r1;
        goto L_0x001d;
    L_0x0060:
        r0 = move-exception;
        r0 = r1;
        goto L_0x004e;
    L_0x0063:
        r0 = move-exception;
        r0 = r1;
        goto L_0x004e;
    L_0x0066:
        r0 = move-exception;
        r0 = r1;
        goto L_0x0058;
    L_0x0069:
        r3 = move-exception;
        goto L_0x0041;
    L_0x006b:
        r3 = move-exception;
        goto L_0x0041;
    L_0x006d:
        r3 = move-exception;
        goto L_0x002e;
    L_0x006f:
        r3 = move-exception;
        goto L_0x002e;
    L_0x0071:
        r0 = r1;
        goto L_0x0058;
    L_0x0073:
        r0 = r1;
        goto L_0x0054;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.location.f.b.l():android.telephony.CellLocation");
    }

    private Class<?> m864m() {
        String str;
        Class<?> cls = null;
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        switch (m866o()) {
            case 0:
                str = "android.telephony.TelephonyManager";
                break;
            case 1:
                str = "android.telephony.MSimTelephonyManager";
                break;
            case 2:
                str = "android.telephony.TelephonyManager2";
                break;
            default:
                str = cls;
                break;
        }
        try {
            cls = systemClassLoader.loadClass(str);
        } catch (Exception e) {
        }
        return cls;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    @android.annotation.SuppressLint({"NewApi"})
    private com.baidu.location.p012f.C0441a m865n() {
        /*
        r5 = this;
        r1 = 0;
        r0 = android.os.Build.VERSION.SDK_INT;
        r0 = java.lang.Integer.valueOf(r0);
        r0 = r0.intValue();
        r2 = 17;
        if (r0 >= r2) goto L_0x0010;
    L_0x000f:
        return r1;
    L_0x0010:
        r0 = r5.f747d;	 Catch:{ Exception -> 0x0048, NoSuchMethodError -> 0x0046 }
        r0 = r0.getAllCellInfo();	 Catch:{ Exception -> 0x0048, NoSuchMethodError -> 0x0046 }
        if (r0 == 0) goto L_0x000f;
    L_0x0018:
        r2 = r0.size();	 Catch:{ Exception -> 0x0048, NoSuchMethodError -> 0x0046 }
        if (r2 <= 0) goto L_0x000f;
    L_0x001e:
        r3 = r0.iterator();	 Catch:{ Exception -> 0x0048, NoSuchMethodError -> 0x0046 }
        r2 = r1;
    L_0x0023:
        r0 = r3.hasNext();	 Catch:{ Exception -> 0x004a, NoSuchMethodError -> 0x0046 }
        if (r0 == 0) goto L_0x0050;
    L_0x0029:
        r0 = r3.next();	 Catch:{ Exception -> 0x004a, NoSuchMethodError -> 0x0046 }
        r0 = (android.telephony.CellInfo) r0;	 Catch:{ Exception -> 0x004a, NoSuchMethodError -> 0x0046 }
        r4 = r0.isRegistered();	 Catch:{ Exception -> 0x004a, NoSuchMethodError -> 0x0046 }
        if (r4 == 0) goto L_0x0023;
    L_0x0035:
        r0 = r5.m852a(r0);	 Catch:{ Exception -> 0x004a, NoSuchMethodError -> 0x0046 }
        if (r0 != 0) goto L_0x003d;
    L_0x003b:
        r2 = r0;
        goto L_0x0023;
    L_0x003d:
        r2 = r0.m843b();	 Catch:{ Exception -> 0x004d, NoSuchMethodError -> 0x0046 }
        if (r2 != 0) goto L_0x0044;
    L_0x0043:
        r0 = r1;
    L_0x0044:
        r1 = r0;
        goto L_0x000f;
    L_0x0046:
        r0 = move-exception;
        goto L_0x000f;
    L_0x0048:
        r0 = move-exception;
        goto L_0x000f;
    L_0x004a:
        r0 = move-exception;
        r1 = r2;
        goto L_0x000f;
    L_0x004d:
        r1 = move-exception;
        r1 = r0;
        goto L_0x000f;
    L_0x0050:
        r1 = r2;
        goto L_0x000f;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.location.f.b.n():com.baidu.location.f.a");
    }

    private int m866o() {
        int i = 0;
        try {
            Class.forName("android.telephony.MSimTelephonyManager");
            i = 1;
        } catch (Exception e) {
        }
        if (i != 0) {
            return i;
        }
        try {
            Class.forName("android.telephony.TelephonyManager2");
            return 2;
        } catch (Exception e2) {
            return i;
        }
    }

    public String m867a(C0441a c0441a) {
        String str = "";
        try {
            str = m859d(c0441a);
            if (str != null && !str.equals("") && !str.equals("&nc=")) {
                return str;
            }
            List<NeighboringCellInfo> neighboringCellInfo = this.f747d.getNeighboringCellInfo();
            if (neighboringCellInfo != null && !neighboringCellInfo.isEmpty()) {
                String str2 = "&nc=";
                int i = 0;
                for (NeighboringCellInfo neighboringCellInfo2 : neighboringCellInfo) {
                    int lac = neighboringCellInfo2.getLac();
                    str = (lac == -1 || neighboringCellInfo2.getCid() == -1) ? str2 : c0441a.f727a != lac ? str2 + lac + "|" + neighboringCellInfo2.getCid() + "|" + neighboringCellInfo2.getRssi() + ";" : str2 + "|" + neighboringCellInfo2.getCid() + "|" + neighboringCellInfo2.getRssi() + ";";
                    int i2 = i + 1;
                    if (i2 >= 8) {
                        break;
                    }
                    i = i2;
                    str2 = str;
                }
                str = str2;
            }
            return (str == null || !str.equals("&nc=")) ? str : null;
        } catch (Exception e) {
            e.printStackTrace();
            str = "";
        }
    }

    public String m868b(C0441a c0441a) {
        StringBuffer stringBuffer = new StringBuffer(128);
        stringBuffer.append("&nw=");
        stringBuffer.append(c0441a.f735i);
        stringBuffer.append(String.format(Locale.CHINA, "&cl=%d|%d|%d|%d&cl_s=%d", new Object[]{Integer.valueOf(c0441a.f729c), Integer.valueOf(c0441a.f730d), Integer.valueOf(c0441a.f727a), Integer.valueOf(c0441a.f728b), Integer.valueOf(c0441a.f734h)}));
        if (c0441a.f731e < ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED && c0441a.f732f < ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED) {
            stringBuffer.append(String.format(Locale.CHINA, "&cdmall=%.6f|%.6f", new Object[]{Double.valueOf(((double) c0441a.f732f) / 14400.0d), Double.valueOf(((double) c0441a.f731e) / 14400.0d)}));
        }
        stringBuffer.append("&cl_t=");
        stringBuffer.append(c0441a.f733g);
        if (this.f751h != null && this.f751h.size() > 0) {
            int size = this.f751h.size();
            stringBuffer.append("&clt=");
            for (int i = 0; i < size; i++) {
                C0441a c0441a2 = (C0441a) this.f751h.get(i);
                if (c0441a2.f729c != c0441a.f729c) {
                    stringBuffer.append(c0441a2.f729c);
                }
                stringBuffer.append("|");
                if (c0441a2.f730d != c0441a.f730d) {
                    stringBuffer.append(c0441a2.f730d);
                }
                stringBuffer.append("|");
                if (c0441a2.f727a != c0441a.f727a) {
                    stringBuffer.append(c0441a2.f727a);
                }
                stringBuffer.append("|");
                if (c0441a2.f728b != c0441a.f728b) {
                    stringBuffer.append(c0441a2.f728b);
                }
                stringBuffer.append("|");
                stringBuffer.append((System.currentTimeMillis() - c0441a2.f733g) / 1000);
                stringBuffer.append(";");
            }
        }
        if (f738a > 100) {
            f738a = 0;
        }
        stringBuffer.append("&cs=" + ((f739b << 8) + f738a));
        return stringBuffer.toString();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void m869b() {
        /*
        r3 = this;
        r1 = 1;
        monitor-enter(r3);
        r0 = r3.f753j;	 Catch:{ all -> 0x0047 }
        if (r0 != r1) goto L_0x0008;
    L_0x0006:
        monitor-exit(r3);
        return;
    L_0x0008:
        r0 = com.baidu.location.C0455f.isServing;	 Catch:{ all -> 0x0047 }
        if (r0 == 0) goto L_0x0006;
    L_0x000c:
        r0 = com.baidu.location.C0455f.getServiceContext();	 Catch:{ all -> 0x0047 }
        r1 = "phone";
        r0 = r0.getSystemService(r1);	 Catch:{ all -> 0x0047 }
        r0 = (android.telephony.TelephonyManager) r0;	 Catch:{ all -> 0x0047 }
        r3.f747d = r0;	 Catch:{ all -> 0x0047 }
        r0 = new java.util.LinkedList;	 Catch:{ all -> 0x0047 }
        r0.<init>();	 Catch:{ all -> 0x0047 }
        r3.f751h = r0;	 Catch:{ all -> 0x0047 }
        r0 = new com.baidu.location.f.b$a;	 Catch:{ all -> 0x0047 }
        r0.<init>(r3);	 Catch:{ all -> 0x0047 }
        r3.f752i = r0;	 Catch:{ all -> 0x0047 }
        r3.m860i();	 Catch:{ all -> 0x0047 }
        r0 = r3.f747d;	 Catch:{ all -> 0x0047 }
        if (r0 == 0) goto L_0x0006;
    L_0x002f:
        r0 = r3.f752i;	 Catch:{ all -> 0x0047 }
        if (r0 == 0) goto L_0x0006;
    L_0x0033:
        r0 = r3.f747d;	 Catch:{ Exception -> 0x0076 }
        r1 = r3.f752i;	 Catch:{ Exception -> 0x0076 }
        r2 = 272; // 0x110 float:3.81E-43 double:1.344E-321;
        r0.listen(r1, r2);	 Catch:{ Exception -> 0x0076 }
    L_0x003c:
        r0 = r3.m866o();	 Catch:{ Throwable -> 0x0057 }
        switch(r0) {
            case 0: goto L_0x0069;
            case 1: goto L_0x004a;
            case 2: goto L_0x005c;
            default: goto L_0x0043;
        };
    L_0x0043:
        r0 = 1;
        r3.f753j = r0;	 Catch:{ all -> 0x0047 }
        goto L_0x0006;
    L_0x0047:
        r0 = move-exception;
        monitor-exit(r3);
        throw r0;
    L_0x004a:
        r0 = com.baidu.location.C0455f.getServiceContext();	 Catch:{ Throwable -> 0x0057 }
        r1 = "phone_msim";
        r0 = com.baidu.location.p006h.C0468j.m1010a(r0, r1);	 Catch:{ Throwable -> 0x0057 }
        r3.f748e = r0;	 Catch:{ Throwable -> 0x0057 }
        goto L_0x0043;
    L_0x0057:
        r0 = move-exception;
        r0 = 0;
        r3.f748e = r0;	 Catch:{ all -> 0x0047 }
        goto L_0x0043;
    L_0x005c:
        r0 = com.baidu.location.C0455f.getServiceContext();	 Catch:{ Throwable -> 0x0057 }
        r1 = "phone2";
        r0 = com.baidu.location.p006h.C0468j.m1010a(r0, r1);	 Catch:{ Throwable -> 0x0057 }
        r3.f748e = r0;	 Catch:{ Throwable -> 0x0057 }
        goto L_0x0043;
    L_0x0069:
        r0 = com.baidu.location.C0455f.getServiceContext();	 Catch:{ Throwable -> 0x0057 }
        r1 = "phone2";
        r0 = com.baidu.location.p006h.C0468j.m1010a(r0, r1);	 Catch:{ Throwable -> 0x0057 }
        r3.f748e = r0;	 Catch:{ Throwable -> 0x0057 }
        goto L_0x0043;
    L_0x0076:
        r0 = move-exception;
        goto L_0x003c;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.location.f.b.b():void");
    }

    public synchronized void m870c() {
        if (this.f753j) {
            if (!(this.f752i == null || this.f747d == null)) {
                this.f747d.listen(this.f752i, 0);
            }
            this.f752i = null;
            this.f747d = null;
            this.f751h.clear();
            this.f751h = null;
            m861j();
            this.f753j = false;
        }
    }

    public boolean m871d() {
        return this.f754q;
    }

    public int m872e() {
        int i = 0;
        if (this.f747d != null) {
            try {
                i = this.f747d.getNetworkType();
            } catch (Exception e) {
            }
        }
        return i;
    }

    public C0441a m873f() {
        if (!((this.f749f != null && this.f749f.m841a() && this.f749f.m843b()) || this.f747d == null)) {
            try {
                m862k();
            } catch (Exception e) {
            }
        }
        if (this.f749f.m846e()) {
            this.f750g = null;
            this.f750g = new C0441a(this.f749f.f727a, this.f749f.f728b, this.f749f.f729c, this.f749f.f730d, this.f749f.f734h, this.f749f.f735i);
        }
        if (this.f749f.m845d() && this.f750g != null && this.f749f.f735i == 'g') {
            this.f749f.f730d = this.f750g.f730d;
            this.f749f.f729c = this.f750g.f729c;
        }
        return this.f749f;
    }

    public String m874g() {
        int i = -1;
        try {
            if (this.f747d != null) {
                i = this.f747d.getSimState();
            }
        } catch (Exception e) {
        }
        return "&sim=" + i;
    }

    public int m875h() {
        String subscriberId;
        try {
            subscriberId = ((TelephonyManager) C0455f.getServiceContext().getSystemService("phone")).getSubscriberId();
        } catch (Exception e) {
            subscriberId = null;
        }
        if (subscriberId != null) {
            if (subscriberId.startsWith("46000") || subscriberId.startsWith("46002") || subscriberId.startsWith("46007")) {
                return 1;
            }
            if (subscriberId.startsWith("46001")) {
                return 2;
            }
            if (subscriberId.startsWith("46003")) {
                return 3;
            }
        }
        return 0;
    }
}
