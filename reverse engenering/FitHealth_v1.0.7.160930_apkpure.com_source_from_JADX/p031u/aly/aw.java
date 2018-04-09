package p031u.aly;

import android.content.Context;
import android.text.TextUtils;
import com.zhuoyi.system.util.constant.SeparatorConstants;
import p031u.aly.C1527x.C1526a;

/* compiled from: ABTest */
public class aw implements ao {
    private static aw f4936h = null;
    private boolean f4937a = false;
    private int f4938b = -1;
    private int f4939c = -1;
    private int f4940d = -1;
    private float f4941e = 0.0f;
    private float f4942f = 0.0f;
    private Context f4943g = null;

    public static synchronized aw m5107a(Context context) {
        aw awVar;
        synchronized (aw.class) {
            if (f4936h == null) {
                C1526a b = C1527x.m3942a(context).m3950b();
                f4936h = new aw(context, b.m3939d(null), b.m3938d(0));
            }
            awVar = f4936h;
        }
        return awVar;
    }

    private aw(Context context, String str, int i) {
        this.f4943g = context;
        m5112a(str, i);
    }

    private float m5109b(String str, int i) {
        int i2 = i * 2;
        if (str == null) {
            return 0.0f;
        }
        return ((float) Integer.valueOf(str.substring(i2, i2 + 5), 16).intValue()) / 1048576.0f;
    }

    public void m5112a(String str, int i) {
        this.f4939c = i;
        String a = C1523t.m3895a(this.f4943g);
        if (TextUtils.isEmpty(a) || TextUtils.isEmpty(str)) {
            this.f4937a = false;
            return;
        }
        try {
            this.f4941e = m5109b(a, 12);
            this.f4942f = m5109b(a, 6);
            if (str.startsWith("SIG7")) {
                m5110b(str);
            } else if (str.startsWith("FIXED")) {
                m5111c(str);
            }
        } catch (Throwable e) {
            this.f4937a = false;
            bl.m3596e("v:" + str, e);
        }
    }

    public static boolean m5108a(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        String[] split = str.split("\\|");
        if (split.length != 6) {
            return false;
        }
        if (split[0].startsWith("SIG7") && split[1].split(SeparatorConstants.SEPARATOR_ADS_ID).length == split[5].split(SeparatorConstants.SEPARATOR_ADS_ID).length) {
            return true;
        }
        if (!split[0].startsWith("FIXED")) {
            return false;
        }
        int length = split[5].split(SeparatorConstants.SEPARATOR_ADS_ID).length;
        int parseInt = Integer.parseInt(split[1]);
        if (length < parseInt || parseInt < 1) {
            return false;
        }
        return true;
    }

    private void m5110b(String str) {
        if (str != null) {
            float floatValue;
            String[] split = str.split("\\|");
            if (split[2].equals("SIG13")) {
                floatValue = Float.valueOf(split[3]).floatValue();
            } else {
                floatValue = 0.0f;
            }
            if (this.f4941e > floatValue) {
                this.f4937a = false;
                return;
            }
            String[] split2;
            float[] fArr = null;
            if (split[0].equals("SIG7")) {
                split2 = split[1].split(SeparatorConstants.SEPARATOR_ADS_ID);
                float[] fArr2 = new float[split2.length];
                for (int i = 0; i < split2.length; i++) {
                    fArr2[i] = Float.valueOf(split2[i]).floatValue();
                }
                fArr = fArr2;
            }
            int[] iArr = null;
            if (split[4].equals("RPT")) {
                split2 = split[5].split(SeparatorConstants.SEPARATOR_ADS_ID);
                int[] iArr2 = new int[split2.length];
                for (int i2 = 0; i2 < split2.length; i2++) {
                    iArr2[i2] = Integer.valueOf(split2[i2]).intValue();
                }
                iArr = iArr2;
            } else if (split[4].equals("DOM")) {
                if (bj.m3543q(this.f4943g)) {
                    this.f4937a = false;
                    return;
                }
                try {
                    split2 = split[5].split(SeparatorConstants.SEPARATOR_ADS_ID);
                    iArr = new int[split2.length];
                    for (int i3 = 0; i3 < split2.length; i3++) {
                        iArr[i3] = Integer.valueOf(split2[i3]).intValue();
                    }
                } catch (Exception e) {
                }
            }
            float f = 0.0f;
            int i4 = 0;
            while (i4 < fArr.length) {
                f += fArr[i4];
                if (this.f4942f < f) {
                    break;
                }
                i4++;
            }
            i4 = -1;
            if (i4 != -1) {
                this.f4937a = true;
                this.f4940d = i4 + 1;
                if (iArr != null) {
                    this.f4938b = iArr[i4];
                    return;
                }
                return;
            }
            this.f4937a = false;
        }
    }

    private void m5111c(String str) {
        if (str != null) {
            String[] split = str.split("\\|");
            float f = 0.0f;
            if (split[2].equals("SIG13")) {
                f = Float.valueOf(split[3]).floatValue();
            }
            if (this.f4941e > f) {
                this.f4937a = false;
                return;
            }
            int intValue;
            if (split[0].equals("FIXED")) {
                intValue = Integer.valueOf(split[1]).intValue();
            } else {
                intValue = -1;
            }
            int[] iArr = null;
            String[] split2;
            if (split[4].equals("RPT")) {
                split2 = split[5].split(SeparatorConstants.SEPARATOR_ADS_ID);
                int[] iArr2 = new int[split2.length];
                for (int i = 0; i < split2.length; i++) {
                    iArr2[i] = Integer.valueOf(split2[i]).intValue();
                }
                iArr = iArr2;
            } else if (split[4].equals("DOM")) {
                if (bj.m3543q(this.f4943g)) {
                    this.f4937a = false;
                    return;
                }
                try {
                    split2 = split[5].split(SeparatorConstants.SEPARATOR_ADS_ID);
                    iArr = new int[split2.length];
                    for (int i2 = 0; i2 < split2.length; i2++) {
                        iArr[i2] = Integer.valueOf(split2[i2]).intValue();
                    }
                } catch (Exception e) {
                }
            }
            if (intValue != -1) {
                this.f4937a = true;
                this.f4940d = intValue;
                if (iArr != null) {
                    this.f4938b = iArr[intValue - 1];
                    return;
                }
                return;
            }
            this.f4937a = false;
        }
    }

    public boolean m5115a() {
        return this.f4937a;
    }

    public int m5116b() {
        return this.f4938b;
    }

    public int m5117c() {
        return this.f4939c;
    }

    public int m5118d() {
        return this.f4940d;
    }

    public void m5113a(av avVar) {
        if (this.f4937a) {
            avVar.f3694b.f3641f.put("client_test", Integer.valueOf(this.f4940d));
        }
    }

    public void mo2749a(C1526a c1526a) {
        m5112a(c1526a.m3939d(null), c1526a.m3938d(0));
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" p13:");
        stringBuilder.append(this.f4941e);
        stringBuilder.append(" p07:");
        stringBuilder.append(this.f4942f);
        stringBuilder.append(" policy:");
        stringBuilder.append(this.f4938b);
        stringBuilder.append(" interval:");
        stringBuilder.append(this.f4939c);
        return stringBuilder.toString();
    }
}
