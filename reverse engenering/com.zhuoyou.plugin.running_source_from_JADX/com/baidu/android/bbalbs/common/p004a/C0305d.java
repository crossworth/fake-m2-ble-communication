package com.baidu.android.bbalbs.common.p004a;

import java.security.MessageDigest;

public final class C0305d {
    public static byte[] m68a(byte[] bArr) {
        try {
            return MessageDigest.getInstance("SHA-1").digest(bArr);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
