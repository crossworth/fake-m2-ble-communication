package p031u.aly;

/* compiled from: Gender */
public enum az implements bt {
    MALE(0),
    FEMALE(1),
    UNKNOWN(2);
    
    private final int f4970d;

    private az(int i) {
        this.f4970d = i;
    }

    public int mo2761a() {
        return this.f4970d;
    }

    public static az m5132a(int i) {
        switch (i) {
            case 0:
                return MALE;
            case 1:
                return FEMALE;
            case 2:
                return UNKNOWN;
            default:
                return null;
        }
    }
}
