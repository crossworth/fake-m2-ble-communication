package p031u.aly;

import java.io.Serializable;

/* compiled from: FieldValueMetaData */
public class cc implements Serializable {
    private final boolean f3735a;
    public final byte f3736b;
    private final String f3737c;
    private final boolean f3738d;

    public cc(byte b, boolean z) {
        this.f3736b = b;
        this.f3735a = false;
        this.f3737c = null;
        this.f3738d = z;
    }

    public cc(byte b) {
        this(b, false);
    }

    public cc(byte b, String str) {
        this.f3736b = b;
        this.f3735a = true;
        this.f3737c = str;
        this.f3738d = false;
    }

    public boolean m3681a() {
        return this.f3735a;
    }

    public String m3682b() {
        return this.f3737c;
    }

    public boolean m3683c() {
        return this.f3736b == (byte) 12;
    }

    public boolean m3684d() {
        return this.f3736b == cv.f3783m || this.f3736b == cv.f3781k || this.f3736b == cv.f3782l;
    }

    public boolean m3685e() {
        return this.f3738d;
    }
}
