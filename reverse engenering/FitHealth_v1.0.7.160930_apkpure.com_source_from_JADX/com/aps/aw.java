package com.aps;

import android.location.Location;
import android.net.wifi.ScanResult;
import android.telephony.CellLocation;
import java.util.List;

public final class aw {
    private static int f1782c = 10;
    private static int f1783d = 100;
    private static float f1784f = 0.5f;
    protected ba f1785a = new ba(this);
    protected ax f1786b = new ax(this);
    private ai f1787e;

    protected aw(ai aiVar) {
        this.f1787e = aiVar;
    }

    protected static void m1832a() {
    }

    protected static void m1833a(int i) {
        f1782c = i;
    }

    protected static void m1834b(int i) {
        f1783d = i;
    }

    protected final boolean m1835a(Location location) {
        boolean z = false;
        if (this.f1787e != null) {
            List j = this.f1787e.m1797j();
            if (!(j == null || location == null)) {
                "cell.list.size: " + j.size();
                ay ayVar = null;
                if (j.size() >= 2) {
                    ay ayVar2 = new ay((CellLocation) j.get(1));
                    if (this.f1786b.f1789b == null) {
                        ayVar = ayVar2;
                        z = true;
                    } else {
                        boolean z2 = location.distanceTo(this.f1786b.f1789b) > ((float) f1783d);
                        if (!z2) {
                            int i;
                            ayVar = this.f1786b.f1788a;
                            if (ayVar2.f1794e == ayVar.f1794e && ayVar2.f1793d == ayVar.f1793d && ayVar2.f1792c == ayVar.f1792c && ayVar2.f1791b == ayVar.f1791b && ayVar2.f1790a == ayVar.f1790a) {
                                i = 1;
                            } else {
                                z2 = false;
                            }
                            z2 = i == 0;
                        }
                        "collect cell?: " + z2;
                        z = z2;
                        ayVar = ayVar2;
                    }
                }
                if (z) {
                    this.f1786b.f1788a = ayVar;
                }
            }
        }
        return z;
    }

    protected final boolean m1836b(Location location) {
        int i = 0;
        if (this.f1787e == null) {
            return false;
        }
        boolean z;
        List list;
        List k = this.f1787e.m1798k();
        if (k.size() >= 2) {
            List list2 = (List) k.get(1);
            if (this.f1785a.f1799b == null) {
                z = true;
            } else if (list2 == null || list2.size() <= 0) {
                list = list2;
                z = false;
            } else {
                z = location.distanceTo(this.f1785a.f1799b) > ((float) f1782c);
                if (z) {
                    list = list2;
                } else {
                    int i2;
                    List list3 = this.f1785a.f1798a;
                    float f = f1784f;
                    if (list2 == null || list3 == null) {
                        i2 = 0;
                    } else if (list2 == null || list3 == null) {
                        i2 = 0;
                    } else {
                        int size = list2.size();
                        int size2 = list3.size();
                        float f2 = (float) (size + size2);
                        if (size == 0 && size2 == 0) {
                            i2 = 1;
                        } else if (size == 0 || size2 == 0) {
                            i2 = 0;
                        } else {
                            int i3 = 0;
                            int i4 = 0;
                            while (i3 < size) {
                                String str = ((ScanResult) list2.get(i3)).BSSID;
                                if (str != null) {
                                    for (int i5 = 0; i5 < size2; i5++) {
                                        if (str.equals(((az) list3.get(i5)).f1795a)) {
                                            i2 = i4 + 1;
                                            break;
                                        }
                                    }
                                }
                                i2 = i4;
                                i3++;
                                i4 = i2;
                            }
                            i2 = ((float) (i4 << 1)) >= f2 * f ? 1 : 0;
                        }
                    }
                    z = i2 == 0;
                }
            }
            list = list2;
        } else {
            list = null;
            z = false;
        }
        if (z) {
            this.f1785a.f1798a.clear();
            int size3 = list.size();
            while (i < size3) {
                this.f1785a.f1798a.add(new az(((ScanResult) list.get(i)).BSSID));
                i++;
            }
        }
        return z;
    }
}
