package p031u.aly;

/* compiled from: TTransportException */
public class dd extends bv {
    public static final int f5199a = 0;
    public static final int f5200b = 1;
    public static final int f5201c = 2;
    public static final int f5202d = 3;
    public static final int f5203e = 4;
    private static final long f5204g = 1;
    protected int f5205f = 0;

    public dd(int i) {
        this.f5205f = i;
    }

    public dd(int i, String str) {
        super(str);
        this.f5205f = i;
    }

    public dd(String str) {
        super(str);
    }

    public dd(int i, Throwable th) {
        super(th);
        this.f5205f = i;
    }

    public dd(Throwable th) {
        super(th);
    }

    public dd(String str, Throwable th) {
        super(str, th);
    }

    public dd(int i, String str, Throwable th) {
        super(str, th);
        this.f5205f = i;
    }

    public int m5617a() {
        return this.f5205f;
    }
}
