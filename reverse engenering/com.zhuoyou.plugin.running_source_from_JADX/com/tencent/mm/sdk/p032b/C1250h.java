package com.tencent.mm.sdk.p032b;

import java.util.TimeZone;

public final class C1250h {
    private static final TimeZone GMT = TimeZone.getTimeZone("GMT");
    public static final long[] bh = new long[]{300, 200, 300, 200};
    private static final long[] bi = new long[]{300, 50, 300, 50};
    private static final char[] bj = new char[]{'<', '>', '\"', '\'', '&', '\r', '\n', ' ', '\t'};
    private static final String[] bk = new String[]{"&lt;", "&gt;", "&quot;", "&apos;", "&amp;", "&#x0D;", "&#x0A;", "&#x20;", "&#x09;"};

    public static boolean m3682h(String str) {
        return str == null || str.length() <= 0;
    }

    public static C1248f m3683u() {
        return new C1248f();
    }
}
