package p031u.aly;

import android.content.Context;
import android.content.SharedPreferences;
import com.umeng.analytics.C0920b;
import java.io.File;
import org.json.JSONObject;

/* compiled from: Envelope */
public class C1523t {
    private final byte[] f3879a = new byte[]{(byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0};
    private final int f3880b = 1;
    private final int f3881c = 0;
    private String f3882d = "1.0";
    private String f3883e = null;
    private byte[] f3884f = null;
    private byte[] f3885g = null;
    private byte[] f3886h = null;
    private int f3887i = 0;
    private int f3888j = 0;
    private int f3889k = 0;
    private byte[] f3890l = null;
    private byte[] f3891m = null;
    private boolean f3892n = false;

    private C1523t(byte[] bArr, String str, byte[] bArr2) throws Exception {
        if (bArr == null || bArr.length == 0) {
            throw new Exception("entity is null or empty");
        }
        this.f3883e = str;
        this.f3889k = bArr.length;
        this.f3890l = bi.m3498a(bArr);
        this.f3888j = (int) (System.currentTimeMillis() / 1000);
        this.f3891m = bArr2;
    }

    public static String m3895a(Context context) {
        SharedPreferences a = ap.m3451a(context);
        if (a == null) {
            return null;
        }
        return a.getString("signature", null);
    }

    public void m3903a(String str) {
        this.f3884f = C0920b.m3065a(str);
    }

    public String m3901a() {
        return C0920b.m3063a(this.f3884f);
    }

    public void m3902a(int i) {
        this.f3887i = i;
    }

    public void m3904a(boolean z) {
        this.f3892n = z;
    }

    public static C1523t m3896a(Context context, String str, byte[] bArr) {
        try {
            String u = bj.m3547u(context);
            String f = bj.m3531f(context);
            SharedPreferences a = ap.m3451a(context);
            String string = a.getString("signature", null);
            int i = a.getInt("serial", 1);
            C1523t c1523t = new C1523t(bArr, str, (f + u).getBytes());
            c1523t.m3903a(string);
            c1523t.m3902a(i);
            c1523t.m3905b();
            a.edit().putInt("serial", i + 1).putString("signature", c1523t.m3901a()).commit();
            c1523t.m3906b(context);
            return c1523t;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static C1523t m3898b(Context context, String str, byte[] bArr) {
        try {
            String u = bj.m3547u(context);
            String f = bj.m3531f(context);
            SharedPreferences a = ap.m3451a(context);
            String string = a.getString("signature", null);
            int i = a.getInt("serial", 1);
            C1523t c1523t = new C1523t(bArr, str, (f + u).getBytes());
            c1523t.m3904a(true);
            c1523t.m3903a(string);
            c1523t.m3902a(i);
            c1523t.m3905b();
            a.edit().putInt("serial", i + 1).putString("signature", c1523t.m3901a()).commit();
            c1523t.m3906b(context);
            return c1523t;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void m3905b() {
        if (this.f3884f == null) {
            this.f3884f = m3899d();
        }
        if (this.f3892n) {
            byte[] bArr = new byte[16];
            try {
                System.arraycopy(this.f3884f, 1, bArr, 0, 16);
                this.f3890l = C0920b.m3066a(this.f3890l, bArr);
            } catch (Exception e) {
            }
        }
        this.f3885g = m3897a(this.f3884f, this.f3888j);
        this.f3886h = m3900e();
    }

    private byte[] m3897a(byte[] bArr, int i) {
        int i2;
        int i3 = 0;
        byte[] b = C0920b.m3068b(this.f3891m);
        byte[] b2 = C0920b.m3068b(this.f3890l);
        int length = b.length;
        byte[] bArr2 = new byte[(length * 2)];
        for (i2 = 0; i2 < length; i2++) {
            bArr2[i2 * 2] = b2[i2];
            bArr2[(i2 * 2) + 1] = b[i2];
        }
        for (i2 = 0; i2 < 2; i2++) {
            bArr2[i2] = bArr[i2];
            bArr2[(bArr2.length - i2) - 1] = bArr[(bArr.length - i2) - 1];
        }
        byte[] bArr3 = new byte[]{(byte) (i & 255), (byte) ((i >> 8) & 255), (byte) ((i >> 16) & 255), (byte) (i >>> 24)};
        while (i3 < bArr2.length) {
            bArr2[i3] = (byte) (bArr2[i3] ^ bArr3[i3 % 4]);
            i3++;
        }
        return bArr2;
    }

    private byte[] m3899d() {
        return m3897a(this.f3879a, (int) (System.currentTimeMillis() / 1000));
    }

    private byte[] m3900e() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(C0920b.m3063a(this.f3884f));
        stringBuilder.append(this.f3887i);
        stringBuilder.append(this.f3888j);
        stringBuilder.append(this.f3889k);
        stringBuilder.append(C0920b.m3063a(this.f3885g));
        return C0920b.m3068b(stringBuilder.toString().getBytes());
    }

    public byte[] m3907c() {
        bp bgVar = new bg();
        bgVar.m5408a(this.f3882d);
        bgVar.m5413b(this.f3883e);
        bgVar.m5420c(C0920b.m3063a(this.f3884f));
        bgVar.m5407a(this.f3887i);
        bgVar.m5419c(this.f3888j);
        bgVar.m5422d(this.f3889k);
        bgVar.m5410a(this.f3890l);
        bgVar.m5426e(this.f3892n ? 1 : 0);
        bgVar.m5423d(C0920b.m3063a(this.f3885g));
        bgVar.m5427e(C0920b.m3063a(this.f3886h));
        try {
            return new by().m3669a(bgVar);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void m3906b(Context context) {
        String str = this.f3883e;
        String e = C1527x.m3942a(context).m3950b().m3940e(null);
        String a = C0920b.m3063a(this.f3884f);
        byte[] bArr = new byte[16];
        System.arraycopy(this.f3884f, 2, bArr, 0, 16);
        String a2 = C0920b.m3063a(C0920b.m3068b(bArr));
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("appkey", str);
            if (e != null) {
                jSONObject.put("umid", e);
            }
            jSONObject.put("signature", a);
            jSONObject.put("checksum", a2);
            str = jSONObject.toString();
            File file = new File(context.getFilesDir(), ".umeng");
            if (!file.exists()) {
                file.mkdir();
            }
            bk.m3560a(new File(file, "exchangeIdentity.json"), str);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public String toString() {
        int i = 1;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("version : %s\n", new Object[]{this.f3882d}));
        stringBuilder.append(String.format("address : %s\n", new Object[]{this.f3883e}));
        stringBuilder.append(String.format("signature : %s\n", new Object[]{C0920b.m3063a(this.f3884f)}));
        stringBuilder.append(String.format("serial : %s\n", new Object[]{Integer.valueOf(this.f3887i)}));
        stringBuilder.append(String.format("timestamp : %d\n", new Object[]{Integer.valueOf(this.f3888j)}));
        stringBuilder.append(String.format("length : %d\n", new Object[]{Integer.valueOf(this.f3889k)}));
        stringBuilder.append(String.format("guid : %s\n", new Object[]{C0920b.m3063a(this.f3885g)}));
        stringBuilder.append(String.format("checksum : %s ", new Object[]{C0920b.m3063a(this.f3886h)}));
        String str = "codex : %d";
        Object[] objArr = new Object[1];
        if (!this.f3892n) {
            i = 0;
        }
        objArr[0] = Integer.valueOf(i);
        stringBuilder.append(String.format(str, objArr));
        return stringBuilder.toString();
    }
}
