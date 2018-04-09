package com.droi.sdk.core.priv;

import com.droi.sdk.DroiError;
import com.droi.sdk.core.priv.C0939m.C0929o;
import com.droi.sdk.internal.DroiLog;

public class C0940n {
    private static final String f3065e = "ServerInfo";
    public int f3066a = -1;
    public int f3067b = 0;
    public int f3068c = 0;
    public int f3069d = 0;

    public static C0940n m2775a(C0940n c0940n, DroiError droiError) {
        C0940n c0940n2 = c0940n == null ? new C0940n() : c0940n;
        if (droiError == null) {
            droiError = new DroiError();
        }
        C0929o a = C0939m.m2761a(droiError);
        if (!droiError.isOk()) {
            DroiLog.m2870e(f3065e, droiError.toString());
            return null;
        } else if (a != null) {
            c0940n2.f3066a = a.f3021c;
            c0940n2.f3067b = a.f3022d;
            c0940n2.f3068c = a.f3023e;
            c0940n2.f3069d = a.f3024f;
            return c0940n2;
        } else {
            droiError.setCode(DroiError.ERROR);
            droiError.setAppendedMessage("Outupt null");
            return null;
        }
    }

    public boolean m2776a() {
        return this.f3066a == -1 || this.f3067b <= 0 || this.f3068c <= 0 || this.f3069d <= 0;
    }
}
