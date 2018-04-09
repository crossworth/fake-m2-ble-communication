package p031u.aly;

/* compiled from: TProtocolException */
public class cp extends bv {
    public static final int f5186a = 0;
    public static final int f5187b = 1;
    public static final int f5188c = 2;
    public static final int f5189d = 3;
    public static final int f5190e = 4;
    public static final int f5191f = 5;
    private static final long f5192h = 1;
    protected int f5193g = 0;

    public cp(int i) {
        this.f5193g = i;
    }

    public cp(int i, String str) {
        super(str);
        this.f5193g = i;
    }

    public cp(String str) {
        super(str);
    }

    public cp(int i, Throwable th) {
        super(th);
        this.f5193g = i;
    }

    public cp(Throwable th) {
        super(th);
    }

    public cp(String str, Throwable th) {
        super(str, th);
    }

    public cp(int i, String str, Throwable th) {
        super(str, th);
        this.f5193g = i;
    }

    public int m5597a() {
        return this.f5193g;
    }
}
