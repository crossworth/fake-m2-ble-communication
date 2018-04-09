package com.amap.api.services.proguard;

import java.util.HashMap;
import java.util.Map;
import no.nordicsemi.android.dfu.DfuBaseService;

/* compiled from: LogUpdateRequest */
public class bg extends cw {
    private byte[] f4347a;
    private String f4348b = "1";

    public bg(byte[] bArr) {
        this.f4347a = (byte[]) bArr.clone();
    }

    public bg(byte[] bArr, String str) {
        this.f4347a = (byte[]) bArr.clone();
        this.f4348b = str;
    }

    private String m4428a() {
        Object a = bb.m1321a(bd.f1395a);
        byte[] bArr = new byte[(a.length + 50)];
        System.arraycopy(this.f4347a, 0, bArr, 0, 50);
        System.arraycopy(a, 0, bArr, 50, a.length);
        return ay.m1280a(bArr);
    }

    public Map<String, String> mo1757c() {
        Map<String, String> hashMap = new HashMap();
        hashMap.put("Content-Type", DfuBaseService.MIME_TYPE_ZIP);
        hashMap.put("Content-Length", String.valueOf(this.f4347a.length));
        return hashMap;
    }

    public Map<String, String> mo1756b() {
        return null;
    }

    public String mo1759g() {
        return String.format(bd.f1396b, new Object[]{"1", this.f4348b, "1", "open", m4428a()});
    }

    public byte[] mo1758f() {
        return this.f4347a;
    }
}
