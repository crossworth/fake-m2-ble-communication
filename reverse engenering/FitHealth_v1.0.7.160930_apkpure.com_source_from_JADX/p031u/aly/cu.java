package p031u.aly;

import java.util.BitSet;

/* compiled from: TTupleProtocol */
public final class cu extends ci {

    /* compiled from: TTupleProtocol */
    public static class C1949a implements cq {
        public co mo2770a(dc dcVar) {
            return new cu(dcVar);
        }
    }

    public cu(dc dcVar) {
        super(dcVar);
    }

    public Class<? extends cw> mo3683D() {
        return cz.class;
    }

    public void m6188a(BitSet bitSet, int i) throws bv {
        for (byte a : cu.m6186b(bitSet, i)) {
            mo2773a(a);
        }
    }

    public BitSet mo3684b(int i) throws bv {
        int ceil = (int) Math.ceil(((double) i) / 8.0d);
        byte[] bArr = new byte[ceil];
        for (int i2 = 0; i2 < ceil; i2++) {
            bArr[i2] = mo2806u();
        }
        return cu.m6185a(bArr);
    }

    public static BitSet m6185a(byte[] bArr) {
        BitSet bitSet = new BitSet();
        for (int i = 0; i < bArr.length * 8; i++) {
            if ((bArr[(bArr.length - (i / 8)) - 1] & (1 << (i % 8))) > 0) {
                bitSet.set(i);
            }
        }
        return bitSet;
    }

    public static byte[] m6186b(BitSet bitSet, int i) {
        byte[] bArr = new byte[((int) Math.ceil(((double) i) / 8.0d))];
        for (int i2 = 0; i2 < bitSet.length(); i2++) {
            if (bitSet.get(i2)) {
                int length = (bArr.length - (i2 / 8)) - 1;
                bArr[length] = (byte) (bArr[length] | (1 << (i2 % 8)));
            }
        }
        return bArr;
    }
}
