package com.droi.sdk.analytics;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import org.json.JSONObject;

class C0778n {
    private static HashMap<String, C0778n> f2345a;
    private JSONObject f2346b = m2398a(this.f2347c);
    private File f2347c;

    private C0778n(File file) {
        this.f2347c = file;
        if (this.f2346b == null) {
            this.f2346b = new JSONObject();
            m2401a(this.f2347c, this.f2346b);
        }
    }

    public static C0778n m2397a(String str) {
        if (f2345a != null) {
            return (C0778n) f2345a.get(str);
        }
        throw new RuntimeException("Need to register first.");
    }

    private JSONObject m2398a(File file) {
        FileInputStream fileInputStream;
        if (file == null || !file.exists()) {
            return null;
        }
        try {
            fileInputStream = new FileInputStream(file);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bArr = new byte[4096];
            while (true) {
                int read = fileInputStream.read(bArr);
                if (read > 0) {
                    byteArrayOutputStream.write(bArr, 0, read);
                } else {
                    JSONObject jSONObject = new JSONObject(byteArrayOutputStream.toString());
                    fileInputStream.close();
                    return jSONObject;
                }
            }
        } catch (Exception e) {
            C0753a.m2313b("PerisitFlags", e.toString());
            return null;
        } catch (Throwable th) {
            fileInputStream.close();
        }
    }

    public static void m2399a() {
        try {
            C0778n.m2400a("global_flag", new File(new File(C0774j.m2382b()), "/flag.dat"));
        } catch (Exception e) {
            C0753a.m2311a("PerisitFlags", e);
        }
    }

    public static void m2400a(String str, File file) {
        if (f2345a == null) {
            f2345a = new HashMap();
        }
        if (!f2345a.containsKey(str)) {
            f2345a.put(str, new C0778n(file));
        }
    }

    private boolean m2401a(File file, JSONObject jSONObject) {
        if (file == null) {
            return false;
        }
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write((jSONObject == null ? "" : jSONObject.toString()).getBytes());
            fileOutputStream.close();
            return true;
        } catch (Exception e) {
            C0753a.m2313b("PerisitFlags", e.toString());
            return false;
        } catch (Throwable th) {
            fileOutputStream.close();
        }
    }

    public long m2402a(String str, long j) {
        try {
            j = this.f2346b.getLong(str);
        } catch (Exception e) {
        }
        return j;
    }

    public boolean m2403b(String str, long j) {
        try {
            this.f2346b.put(str, j);
            m2401a(this.f2347c, this.f2346b);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
