package com.tencent.wxop.stat;

public final class C0897f {
    private String f3064a = null;
    private String f3065b = null;
    private boolean ba = false;
    private boolean bb = false;
    private String f3066c = null;

    public final boolean m3003R() {
        return this.ba;
    }

    public final String m3004S() {
        return this.f3064a;
    }

    public final String m3005T() {
        return this.f3065b;
    }

    public final boolean m3006U() {
        return this.bb;
    }

    public final String getVersion() {
        return this.f3066c;
    }

    public final void m3007s(String str) {
        this.f3064a = str;
    }

    public final String toString() {
        return "StatSpecifyReportedInfo [appKey=" + this.f3064a + ", installChannel=" + this.f3065b + ", version=" + this.f3066c + ", sendImmediately=" + this.ba + ", isImportant=" + this.bb + "]";
    }
}
