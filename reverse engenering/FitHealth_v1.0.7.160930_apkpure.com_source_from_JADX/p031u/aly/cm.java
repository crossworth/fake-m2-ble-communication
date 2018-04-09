package p031u.aly;

/* compiled from: TMessage */
public final class cm {
    public final String f3759a;
    public final byte f3760b;
    public final int f3761c;

    public cm() {
        this("", (byte) 0, 0);
    }

    public cm(String str, byte b, int i) {
        this.f3759a = str;
        this.f3760b = b;
        this.f3761c = i;
    }

    public String toString() {
        return "<TMessage name:'" + this.f3759a + "' type: " + this.f3760b + " seqid:" + this.f3761c + ">";
    }

    public boolean equals(Object obj) {
        if (obj instanceof cm) {
            return m3687a((cm) obj);
        }
        return false;
    }

    public boolean m3687a(cm cmVar) {
        return this.f3759a.equals(cmVar.f3759a) && this.f3760b == cmVar.f3760b && this.f3761c == cmVar.f3761c;
    }
}
