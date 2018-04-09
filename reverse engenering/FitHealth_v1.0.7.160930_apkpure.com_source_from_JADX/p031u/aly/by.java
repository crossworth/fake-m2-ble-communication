package p031u.aly;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import p031u.aly.ci.C1948a;

/* compiled from: TSerializer */
public class by {
    private final ByteArrayOutputStream f3725a;
    private final da f3726b;
    private co f3727c;

    public by() {
        this(new C1948a());
    }

    public by(cq cqVar) {
        this.f3725a = new ByteArrayOutputStream();
        this.f3726b = new da(this.f3725a);
        this.f3727c = cqVar.mo2770a(this.f3726b);
    }

    public byte[] m3669a(bp bpVar) throws bv {
        this.f3725a.reset();
        bpVar.mo2768b(this.f3727c);
        return this.f3725a.toByteArray();
    }

    public String m3668a(bp bpVar, String str) throws bv {
        try {
            return new String(m3669a(bpVar), str);
        } catch (UnsupportedEncodingException e) {
            throw new bv("JVM DOES NOT SUPPORT ENCODING: " + str);
        }
    }

    public String m3670b(bp bpVar) throws bv {
        return new String(m3669a(bpVar));
    }
}
