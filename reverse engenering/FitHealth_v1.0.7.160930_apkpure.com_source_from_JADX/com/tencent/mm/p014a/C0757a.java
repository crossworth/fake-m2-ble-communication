package com.tencent.mm.p014a;

import android.util.Base64;
import javax.crypto.Cipher;

public final class C0757a {
    private Cipher f2634j;

    public final String m2504h(String str) {
        try {
            return new String(this.f2634j.doFinal(Base64.decode(str, 0)), "UTF8");
        } catch (Exception e) {
            return "[des]" + str + "|" + e.toString();
        }
    }
}
