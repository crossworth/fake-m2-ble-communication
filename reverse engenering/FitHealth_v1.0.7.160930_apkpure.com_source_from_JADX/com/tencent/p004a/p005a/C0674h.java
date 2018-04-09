package com.tencent.p004a.p005a;

import com.tencent.p004a.p009c.C0683a;
import com.tencent.p004a.p009c.C0684b;
import java.io.File;
import java.io.FileFilter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;

/* compiled from: ProGuard */
public class C0674h {
    private static SimpleDateFormat f2333a = C0684b.m2304a("yyyy-MM-dd");
    private static FileFilter f2334b = new C0675i();
    private String f2335c = "Tracer.File";
    private int f2336d = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
    private int f2337e = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
    private int f2338f = 4096;
    private long f2339g = 10000;
    private File f2340h;
    private int f2341i = 10;
    private String f2342j = ".log";
    private long f2343k = Long.MAX_VALUE;
    private FileFilter f2344l = new C0676j(this);
    private Comparator<? super File> f2345m = new C0677k(this);

    public static long m2261a(File file) {
        try {
            return f2333a.parse(file.getName()).getTime();
        } catch (Exception e) {
            return -1;
        }
    }

    public C0674h(File file, int i, int i2, int i3, String str, long j, int i4, String str2, long j2) {
        m2279c(file);
        m2272b(i);
        m2268a(i2);
        m2277c(i3);
        m2269a(str);
        m2273b(j);
        m2281d(i4);
        m2274b(str2);
        m2278c(j2);
    }

    public File m2266a() {
        return m2263d(System.currentTimeMillis());
    }

    private File m2263d(long j) {
        return m2264e(m2267a(j));
    }

    public File m2267a(long j) {
        File file = new File(m2285h(), f2333a.format(Long.valueOf(j)));
        file.mkdirs();
        return file;
    }

    private File m2264e(File file) {
        File[] b = m2275b(file);
        if (b == null || b.length == 0) {
            return new File(file, "1" + m2287j());
        }
        m2270a(b);
        File file2 = b[b.length - 1];
        int length = b.length - m2282e();
        if (((int) file2.length()) > m2280d()) {
            file2 = new File(file, (C0674h.m2265f(file2) + 1) + m2287j());
            length++;
        }
        for (int i = 0; i < length; i++) {
            b[i].delete();
        }
        return file2;
    }

    public File[] m2275b(File file) {
        return file.listFiles(this.f2344l);
    }

    public void m2271b() {
        if (m2285h() != null) {
            File[] listFiles = m2285h().listFiles(f2334b);
            if (listFiles != null) {
                for (File file : listFiles) {
                    if (System.currentTimeMillis() - C0674h.m2261a(file) > m2288k()) {
                        C0683a.m2303a(file);
                    }
                }
            }
        }
    }

    public File[] m2270a(File[] fileArr) {
        Arrays.sort(fileArr, this.f2345m);
        return fileArr;
    }

    private static int m2265f(File file) {
        try {
            String name = file.getName();
            return Integer.parseInt(name.substring(0, name.indexOf(46)));
        } catch (Exception e) {
            return -1;
        }
    }

    public String m2276c() {
        return this.f2335c;
    }

    public void m2269a(String str) {
        this.f2335c = str;
    }

    public int m2280d() {
        return this.f2336d;
    }

    public void m2268a(int i) {
        this.f2336d = i;
    }

    public int m2282e() {
        return this.f2337e;
    }

    public void m2272b(int i) {
        this.f2337e = i;
    }

    public int m2283f() {
        return this.f2338f;
    }

    public void m2277c(int i) {
        this.f2338f = i;
    }

    public long m2284g() {
        return this.f2339g;
    }

    public void m2273b(long j) {
        this.f2339g = j;
    }

    public File m2285h() {
        return this.f2340h;
    }

    public void m2279c(File file) {
        this.f2340h = file;
    }

    public int m2286i() {
        return this.f2341i;
    }

    public void m2281d(int i) {
        this.f2341i = i;
    }

    public String m2287j() {
        return this.f2342j;
    }

    public void m2274b(String str) {
        this.f2342j = str;
    }

    public long m2288k() {
        return this.f2343k;
    }

    public void m2278c(long j) {
        this.f2343k = j;
    }
}
