package com.baidu.location.p006h;

public class C0466h {
    private String f866a;
    private String f867b;
    private boolean f868c;

    public C0466h(String str, boolean z, String str2) {
        this.f867b = str;
        this.f868c = z;
        this.f866a = str2;
    }

    public String m1002a() {
        return this.f867b;
    }

    public String toString() {
        return "SDCardInfo [label=" + this.f866a + ", mountPoint=" + this.f867b + ", isRemoveable=" + this.f868c + "]";
    }
}
