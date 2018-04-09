package com.tencent.wxop.stat.common;

import android.net.wifi.ScanResult;
import java.util.Comparator;

final class C1449r implements Comparator<ScanResult> {
    C1449r() {
    }

    public final int m4472a(ScanResult scanResult, ScanResult scanResult2) {
        int abs = Math.abs(scanResult.level);
        int abs2 = Math.abs(scanResult2.level);
        return abs > abs2 ? 1 : abs == abs2 ? 0 : -1;
    }

    public final /* synthetic */ int compare(Object obj, Object obj2) {
        return m4472a((ScanResult) obj, (ScanResult) obj2);
    }
}
