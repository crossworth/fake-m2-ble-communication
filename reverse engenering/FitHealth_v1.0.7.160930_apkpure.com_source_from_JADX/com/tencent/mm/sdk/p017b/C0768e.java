package com.tencent.mm.sdk.p017b;

import java.util.TimeZone;

public final class C0768e {
    private static final long[] f2655G = new long[]{300, 200, 300, 200};
    private static final TimeZone GMT = TimeZone.getTimeZone("GMT");
    private static final long[] f2656H = new long[]{300, 50, 300, 50};
    private static final char[] f2657I = new char[]{'<', '>', '\"', '\'', '&', '\r', '\n', ' ', '\t'};
    private static final String[] f2658J = new String[]{"&lt;", "&gt;", "&quot;", "&apos;", "&amp;", "&#x0D;", "&#x0A;", "&#x20;", "&#x09;"};

    public static boolean m2523j(String str) {
        return str == null || str.length() <= 0;
    }
}
