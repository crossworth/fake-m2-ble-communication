package com.baidu.location.p011e;

import android.database.sqlite.SQLiteDatabase;
import com.baidu.location.Jni;
import com.baidu.location.p006h.C0335e;
import com.baidu.location.p006h.C0459b;
import com.baidu.location.p006h.C0468j;
import com.tencent.stat.DeviceInfo;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;
import org.json.JSONObject;

final class C0422g {
    private final C0426h f638a;
    private final SQLiteDatabase f639b;
    private final C0421a f640c;
    private boolean f641d;
    private boolean f642e;
    private boolean f643f;
    private boolean f644g;
    private boolean f645h;
    private String[] f646i;
    private boolean f647j;
    private int f648k;
    private int f649l;
    private int f650m;
    private double f651n;
    private double f652o;
    private double f653p;
    private double f654q;
    private double f655r;
    private int f656s;
    private boolean f657t = true;
    private long f658u = 8000;
    private long f659v = 5000;
    private long f660w = 5000;
    private long f661x = 5000;
    private long f662y = 5000;

    private final class C0421a extends C0335e {
        final /* synthetic */ C0422g f632a;
        private int f633b;
        private long f634c;
        private long f635d;
        private boolean f636e;
        private final String f637f;

        private C0421a(C0422g c0422g) {
            this.f632a = c0422g;
            this.f633b = 0;
            this.f636e = false;
            this.f634c = -1;
            this.f635d = -1;
            this.k = new HashMap();
            this.f637f = Jni.encodeOfflineLocationUpdateRequest(String.format(Locale.US, "&ver=%s&cuid=%s&prod=%s:%s&sdk=%.2f", new Object[]{"1", C0459b.m980a().f841b, C0459b.f836e, C0459b.f835d, Float.valueOf(7.01f)}));
        }

        private void m700b() {
            if (!this.f636e) {
                boolean z = false;
                try {
                    File file = new File(this.f632a.f638a.m778c(), "ofl.config");
                    if (this.f635d == -1 && file.exists()) {
                        JSONObject jSONObject;
                        Scanner scanner = new Scanner(file);
                        String next = scanner.next();
                        scanner.close();
                        JSONObject jSONObject2 = new JSONObject(next);
                        this.f632a.f641d = jSONObject2.getBoolean("ol");
                        this.f632a.f642e = jSONObject2.getBoolean("fl");
                        this.f632a.f643f = jSONObject2.getBoolean("on");
                        this.f632a.f644g = jSONObject2.getBoolean("wn");
                        this.f632a.f645h = jSONObject2.getBoolean("oc");
                        this.f635d = jSONObject2.getLong("t");
                        if (jSONObject2.has("cplist")) {
                            this.f632a.f646i = jSONObject2.getString("cplist").split(";");
                        }
                        if (jSONObject2.has("rgcgp")) {
                            this.f632a.f648k = jSONObject2.getInt("rgcgp");
                        }
                        if (jSONObject2.has("rgcon")) {
                            this.f632a.f647j = jSONObject2.getBoolean("rgcon");
                        }
                        if (jSONObject2.has("addrup")) {
                            this.f632a.f650m = jSONObject2.getInt("addrup");
                        }
                        if (jSONObject2.has("poiup")) {
                            this.f632a.f649l = jSONObject2.getInt("poiup");
                        }
                        if (jSONObject2.has("oflp")) {
                            jSONObject = jSONObject2.getJSONObject("oflp");
                            if (jSONObject.has("0")) {
                                this.f632a.f651n = jSONObject.getDouble("0");
                            }
                            if (jSONObject.has("1")) {
                                this.f632a.f652o = jSONObject.getDouble("1");
                            }
                            if (jSONObject.has("2")) {
                                this.f632a.f653p = jSONObject.getDouble("2");
                            }
                            if (jSONObject.has("3")) {
                                this.f632a.f654q = jSONObject.getDouble("3");
                            }
                            if (jSONObject.has("4")) {
                                this.f632a.f655r = jSONObject.getDouble("4");
                            }
                        }
                        if (jSONObject2.has("onlt")) {
                            jSONObject = jSONObject2.getJSONObject("onlt");
                            if (jSONObject.has("0")) {
                                this.f632a.f662y = jSONObject.getLong("0");
                            }
                            if (jSONObject.has("1")) {
                                this.f632a.f661x = jSONObject.getLong("1");
                            }
                            if (jSONObject.has("2")) {
                                this.f632a.f658u = jSONObject.getLong("2");
                            }
                            if (jSONObject.has("3")) {
                                this.f632a.f659v = jSONObject.getLong("3");
                            }
                            if (jSONObject.has("4")) {
                                this.f632a.f660w = jSONObject.getLong("4");
                            }
                        }
                        if (jSONObject2.has("minapn")) {
                            this.f632a.f656s = jSONObject2.getInt("minapn");
                        }
                    }
                    if (this.f635d == -1 && file.exists()) {
                    }
                    if (this.f635d != -1 && this.f635d + LogBuilder.MAX_INTERVAL <= System.currentTimeMillis()) {
                        z = true;
                    }
                } catch (Exception e) {
                }
                if ((this.f635d == -1 || r0) && m701c() && C0468j.m1015a(this.f632a.f638a.m777b())) {
                    this.f636e = true;
                    m204e();
                }
            }
        }

        private boolean m701c() {
            boolean z = true;
            if (this.f633b >= 2) {
                if (this.f634c + LogBuilder.MAX_INTERVAL < System.currentTimeMillis()) {
                    this.f633b = 0;
                    this.f634c = -1;
                } else {
                    z = false;
                }
            }
            return !z ? z : z;
        }

        public void mo1741a() {
            this.k.clear();
            this.k.put("qt", "conf");
            this.k.put("req", this.f637f);
            this.h = C0426h.f675a;
        }

        public void mo1742a(boolean z) {
            if (!z || this.j == null) {
                this.f633b++;
                this.f634c = System.currentTimeMillis();
            } else {
                try {
                    JSONObject jSONObject = new JSONObject(this.j);
                    Object obj = "1";
                    long j = 0;
                    if (jSONObject.has("ofl")) {
                        j = jSONObject.getLong("ofl");
                    }
                    if (jSONObject.has(DeviceInfo.TAG_VERSION)) {
                        obj = jSONObject.getString(DeviceInfo.TAG_VERSION);
                    }
                    if ((j & 1) == 1) {
                        this.f632a.f641d = true;
                    }
                    if ((j & 2) == 2) {
                        this.f632a.f642e = true;
                    }
                    if ((j & 4) == 4) {
                        this.f632a.f643f = true;
                    }
                    if ((j & 8) == 8) {
                        this.f632a.f644g = true;
                    }
                    if ((16 & j) == 16) {
                        this.f632a.f645h = true;
                    }
                    if ((j & 32) == 32) {
                        this.f632a.f647j = true;
                    }
                    JSONObject jSONObject2 = new JSONObject();
                    if (jSONObject.has("cplist")) {
                        this.f632a.f646i = jSONObject.getString("cplist").split(";");
                        jSONObject2.put("cplist", jSONObject.getString("cplist"));
                    }
                    if (jSONObject.has("bklist")) {
                        this.f632a.m748a(jSONObject.getString("bklist").split(";"));
                    }
                    if (jSONObject.has("para")) {
                        JSONObject jSONObject3;
                        jSONObject = jSONObject.getJSONObject("para");
                        if (jSONObject.has("rgcgp")) {
                            this.f632a.f648k = jSONObject.getInt("rgcgp");
                        }
                        if (jSONObject.has("addrup")) {
                            this.f632a.f650m = jSONObject.getInt("addrup");
                        }
                        if (jSONObject.has("poiup")) {
                            this.f632a.f649l = jSONObject.getInt("poiup");
                        }
                        if (jSONObject.has("oflp")) {
                            jSONObject3 = jSONObject.getJSONObject("oflp");
                            if (jSONObject3.has("0")) {
                                this.f632a.f651n = jSONObject3.getDouble("0");
                            }
                            if (jSONObject3.has("1")) {
                                this.f632a.f652o = jSONObject3.getDouble("1");
                            }
                            if (jSONObject3.has("2")) {
                                this.f632a.f653p = jSONObject3.getDouble("2");
                            }
                            if (jSONObject3.has("3")) {
                                this.f632a.f654q = jSONObject3.getDouble("3");
                            }
                            if (jSONObject3.has("4")) {
                                this.f632a.f655r = jSONObject3.getDouble("4");
                            }
                        }
                        if (jSONObject.has("onlt")) {
                            jSONObject3 = jSONObject.getJSONObject("onlt");
                            if (jSONObject3.has("0")) {
                                this.f632a.f662y = jSONObject3.getLong("0");
                            }
                            if (jSONObject3.has("1")) {
                                this.f632a.f661x = jSONObject3.getLong("1");
                            }
                            if (jSONObject3.has("2")) {
                                this.f632a.f658u = jSONObject3.getLong("2");
                            }
                            if (jSONObject3.has("3")) {
                                this.f632a.f659v = jSONObject3.getLong("3");
                            }
                            if (jSONObject3.has("4")) {
                                this.f632a.f660w = jSONObject3.getLong("4");
                            }
                        }
                        if (jSONObject.has("minapn")) {
                            this.f632a.f656s = jSONObject.getInt("minapn");
                        }
                    }
                    jSONObject2.put("ol", this.f632a.f641d);
                    jSONObject2.put("fl", this.f632a.f642e);
                    jSONObject2.put("on", this.f632a.f643f);
                    jSONObject2.put("wn", this.f632a.f644g);
                    jSONObject2.put("oc", this.f632a.f645h);
                    this.f635d = System.currentTimeMillis();
                    jSONObject2.put("t", this.f635d);
                    jSONObject2.put(DeviceInfo.TAG_VERSION, obj);
                    jSONObject2.put("rgcon", this.f632a.f647j);
                    jSONObject2.put("rgcgp", this.f632a.f648k);
                    JSONObject jSONObject4 = new JSONObject();
                    jSONObject4.put("0", this.f632a.f651n);
                    jSONObject4.put("1", this.f632a.f652o);
                    jSONObject4.put("2", this.f632a.f653p);
                    jSONObject4.put("3", this.f632a.f654q);
                    jSONObject4.put("4", this.f632a.f655r);
                    jSONObject2.put("oflp", jSONObject4);
                    jSONObject4 = new JSONObject();
                    jSONObject4.put("0", this.f632a.f662y);
                    jSONObject4.put("1", this.f632a.f661x);
                    jSONObject4.put("2", this.f632a.f658u);
                    jSONObject4.put("3", this.f632a.f659v);
                    jSONObject4.put("4", this.f632a.f660w);
                    jSONObject2.put("onlt", jSONObject4);
                    jSONObject2.put("addrup", this.f632a.f650m);
                    jSONObject2.put("poiup", this.f632a.f649l);
                    jSONObject2.put("minapn", this.f632a.f656s);
                    File file = new File(this.f632a.f638a.m778c(), "ofl.config");
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    FileWriter fileWriter = new FileWriter(file);
                    fileWriter.write(jSONObject2.toString());
                    fileWriter.close();
                } catch (Exception e) {
                    this.f633b++;
                    this.f634c = System.currentTimeMillis();
                }
            }
            this.f636e = false;
        }
    }

    C0422g(C0426h c0426h, SQLiteDatabase sQLiteDatabase) {
        this.f638a = c0426h;
        this.f641d = false;
        this.f642e = false;
        this.f643f = false;
        this.f644g = false;
        this.f645h = false;
        this.f647j = false;
        this.f648k = 6;
        this.f649l = 30;
        this.f650m = 30;
        this.f651n = 0.0d;
        this.f652o = 0.0d;
        this.f653p = 0.0d;
        this.f654q = 0.0d;
        this.f655r = 0.0d;
        this.f656s = 8;
        this.f646i = new String[0];
        this.f639b = sQLiteDatabase;
        this.f640c = new C0421a();
        if (this.f639b != null && this.f639b.isOpen()) {
            try {
                this.f639b.execSQL("CREATE TABLE IF NOT EXISTS BLACK (name VARCHAR(100) PRIMARY KEY);");
            } catch (Exception e) {
            }
        }
        m754g();
    }

    int m746a() {
        return this.f656s;
    }

    long m747a(String str) {
        return str.equals("2G") ? this.f658u : str.equals("3G") ? this.f659v : str.equals("4G") ? this.f660w : str.equals("WIFI") ? this.f661x : str.equals("unknown") ? this.f662y : 5000;
    }

    void m748a(String[] strArr) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < strArr.length; i++) {
            if (i > 0) {
                stringBuffer.append(",");
            }
            stringBuffer.append("(\"");
            stringBuffer.append(strArr[i]);
            stringBuffer.append("\")");
        }
        if (this.f639b != null && this.f639b.isOpen() && stringBuffer.length() > 0) {
            try {
                this.f639b.execSQL(String.format(Locale.US, "INSERT OR IGNORE INTO BLACK VALUES %s;", new Object[]{stringBuffer.toString()}));
            } catch (Exception e) {
            }
        }
    }

    double m749b() {
        return this.f651n;
    }

    double m750c() {
        return this.f652o;
    }

    double m751d() {
        return this.f653p;
    }

    double m752e() {
        return this.f654q;
    }

    double m753f() {
        return this.f655r;
    }

    void m754g() {
        this.f640c.m700b();
    }

    boolean m755h() {
        return this.f641d;
    }

    boolean m756i() {
        return this.f643f;
    }

    boolean m757j() {
        return this.f644g;
    }

    boolean m758k() {
        return this.f642e;
    }

    boolean m759l() {
        return this.f647j;
    }

    boolean m760m() {
        return this.f657t;
    }

    int m761n() {
        return this.f648k;
    }

    String[] m762o() {
        return this.f646i;
    }

    int m763p() {
        return this.f650m;
    }

    int m764q() {
        return this.f649l;
    }
}
