package com.tencent.mm.sdk.p015a.p016a;

import com.tencent.mm.p014a.C0758b;

public final class C0762b {
    public static byte[] m2507a(String str, int i, String str2) {
        StringBuffer stringBuffer = new StringBuffer();
        if (str != null) {
            stringBuffer.append(str);
        }
        stringBuffer.append(i);
        stringBuffer.append(str2);
        stringBuffer.append("mMcShCsTr");
        return C0758b.m2505a(stringBuffer.toString().substring(1, 9).getBytes()).getBytes();
    }
}
