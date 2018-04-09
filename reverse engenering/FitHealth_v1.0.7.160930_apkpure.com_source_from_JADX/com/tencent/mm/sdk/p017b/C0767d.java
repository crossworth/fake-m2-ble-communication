package com.tencent.mm.sdk.p017b;

import com.tencent.mm.p014a.C0757a;

public final class C0767d {
    private final C0757a f2653E;
    private C0766c<String, String> f2654F;

    public final String m2522i(String str) {
        try {
            if (str.startsWith("!")) {
                if (this.f2654F.m2521a(str)) {
                    return (String) this.f2654F.get(str);
                }
                String substring = str.substring(1);
                try {
                    String[] split = substring.split("@");
                    if (split.length > 1) {
                        String str2 = split[0];
                        int intValue = Integer.valueOf(split[0]).intValue();
                        String substring2 = substring.substring(str2.length() + 1, (str2.length() + 1) + intValue);
                        String str3 = this.f2653E.m2504h(substring2) + substring.substring(intValue + (str2.length() + 1));
                        this.f2654F.put(str, str3);
                        return str3;
                    }
                    str = substring;
                } catch (Exception e) {
                    str = substring;
                    Exception exception = e;
                    exception.printStackTrace();
                    str = "[td]" + str;
                    return str;
                }
            }
        } catch (Exception e2) {
            exception = e2;
            exception.printStackTrace();
            str = "[td]" + str;
            return str;
        }
        return str;
    }
}
