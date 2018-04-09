package com.amap.api.mapcore.util;

import java.util.HashMap;
import java.util.Map;
import no.nordicsemi.android.dfu.DfuBaseService;

/* compiled from: LogUpdateRequest */
public class ed extends fw {
    private byte[] f4184a;
    private String f4185b = "1";

    public ed(byte[] bArr) {
        this.f4184a = (byte[]) bArr.clone();
    }

    public ed(byte[] bArr, String str) {
        this.f4184a = (byte[]) bArr.clone();
        this.f4185b = str;
    }

    private String m4235e() {
        Object a = dx.m721a(ea.f533a);
        byte[] bArr = new byte[(a.length + 50)];
        System.arraycopy(this.f4184a, 0, bArr, 0, 50);
        System.arraycopy(a, 0, bArr, 50, a.length);
        return ds.m679a(bArr);
    }

    public Map<String, String> mo1632c() {
        Map<String, String> hashMap = new HashMap();
        hashMap.put("Content-Type", DfuBaseService.MIME_TYPE_ZIP);
        hashMap.put("Content-Length", String.valueOf(this.f4184a.length));
        return hashMap;
    }

    public Map<String, String> mo1631b() {
        return null;
    }

    public String mo1630a() {
        return String.format(ea.f534b, new Object[]{"1", this.f4185b, "1", "open", m4235e()});
    }

    public byte[] a_() {
        return this.f4184a;
    }
}
