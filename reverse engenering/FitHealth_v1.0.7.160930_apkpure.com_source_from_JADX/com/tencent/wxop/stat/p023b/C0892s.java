package com.tencent.wxop.stat.p023b;

import android.net.wifi.ScanResult;
import java.util.Comparator;

final class C0892s implements Comparator<ScanResult> {
    C0892s() {
    }

    public final /* synthetic */ int compare(Object obj, Object obj2) {
        ScanResult scanResult = (ScanResult) obj2;
        int abs = Math.abs(((ScanResult) obj).level);
        int abs2 = Math.abs(scanResult.level);
        return abs > abs2 ? 1 : abs == abs2 ? 0 : -1;
    }
}
