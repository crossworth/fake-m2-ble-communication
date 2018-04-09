package com.tencent.mm.sdk.p030a.p031a;

import com.tencent.mm.p029a.C1234a;

public final class C1238b {
    public static byte[] m3658a(String str, int i, String str2) {
        StringBuffer stringBuffer = new StringBuffer();
        if (str != null) {
            stringBuffer.append(str);
        }
        stringBuffer.append(i);
        stringBuffer.append(str2);
        stringBuffer.append("mMcShCsTr");
        return C1234a.m3656c(stringBuffer.toString().substring(1, 9).getBytes()).getBytes();
    }
}
