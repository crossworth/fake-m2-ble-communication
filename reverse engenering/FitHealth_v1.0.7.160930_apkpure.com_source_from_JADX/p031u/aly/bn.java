package p031u.aly;

/* compiled from: ShortStack */
public class bn {
    private short[] f3716a;
    private int f3717b = -1;

    public bn(int i) {
        this.f3716a = new short[i];
    }

    public short m3617a() {
        short[] sArr = this.f3716a;
        int i = this.f3717b;
        this.f3717b = i - 1;
        return sArr[i];
    }

    public void m3618a(short s) {
        if (this.f3716a.length == this.f3717b + 1) {
            m3616d();
        }
        short[] sArr = this.f3716a;
        int i = this.f3717b + 1;
        this.f3717b = i;
        sArr[i] = s;
    }

    private void m3616d() {
        Object obj = new short[(this.f3716a.length * 2)];
        System.arraycopy(this.f3716a, 0, obj, 0, this.f3716a.length);
        this.f3716a = obj;
    }

    public short m3619b() {
        return this.f3716a[this.f3717b];
    }

    public void m3620c() {
        this.f3717b = -1;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<ShortStack vector:[");
        for (int i = 0; i < this.f3716a.length; i++) {
            if (i != 0) {
                stringBuilder.append(" ");
            }
            if (i == this.f3717b) {
                stringBuilder.append(">>");
            }
            stringBuilder.append(this.f3716a[i]);
            if (i == this.f3717b) {
                stringBuilder.append("<<");
            }
        }
        stringBuilder.append("]>");
        return stringBuilder.toString();
    }
}
