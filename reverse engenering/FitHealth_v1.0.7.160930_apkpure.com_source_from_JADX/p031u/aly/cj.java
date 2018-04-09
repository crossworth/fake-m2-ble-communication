package p031u.aly;

/* compiled from: TField */
public class cj {
    public final String f3751a;
    public final byte f3752b;
    public final short f3753c;

    public cj() {
        this("", (byte) 0, (short) 0);
    }

    public cj(String str, byte b, short s) {
        this.f3751a = str;
        this.f3752b = b;
        this.f3753c = s;
    }

    public String toString() {
        return "<TField name:'" + this.f3751a + "' type:" + this.f3752b + " field-id:" + this.f3753c + ">";
    }

    public boolean m3686a(cj cjVar) {
        return this.f3752b == cjVar.f3752b && this.f3753c == cjVar.f3753c;
    }
}
