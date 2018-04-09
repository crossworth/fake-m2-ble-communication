package com.tencent.open.p036a;

import com.tencent.open.p036a.C1312d.C1311d;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/* compiled from: ProGuard */
public class C1306b {
    private static SimpleDateFormat f4096a = C1311d.m3861a("yy.MM.dd.HH");
    private String f4097b = "Tracer.File";
    private int f4098c = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
    private int f4099d = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
    private int f4100e = 4096;
    private long f4101f = 10000;
    private File f4102g;
    private int f4103h = 10;
    private String f4104i = ".log";
    private long f4105j = Long.MAX_VALUE;

    public C1306b(File file, int i, int i2, int i3, String str, long j, int i4, String str2, long j2) {
        m3839a(file);
        m3842b(i);
        m3837a(i2);
        m3846c(i3);
        m3840a(str);
        m3838a(j);
        m3848d(i4);
        m3844b(str2);
        m3843b(j2);
    }

    public File m3836a() {
        return m3833c(System.currentTimeMillis());
    }

    private File m3833c(long j) {
        File b = m3841b();
        String str = "";
        try {
            return new File(b, m3834c(m3835d(j)));
        } catch (Throwable th) {
            th.printStackTrace();
            return b;
        }
    }

    private String m3835d(long j) {
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(j);
        return new SimpleDateFormat("yy.MM.dd.HH").format(instance.getTime());
    }

    private String m3834c(String str) {
        return "com.tencent.mobileqq_connectSdk." + str + ".log";
    }

    public File m3841b() {
        File e = m3849e();
        e.mkdirs();
        return e;
    }

    public String m3845c() {
        return this.f4097b;
    }

    public void m3840a(String str) {
        this.f4097b = str;
    }

    public void m3837a(int i) {
        this.f4098c = i;
    }

    public void m3842b(int i) {
        this.f4099d = i;
    }

    public int m3847d() {
        return this.f4100e;
    }

    public void m3846c(int i) {
        this.f4100e = i;
    }

    public void m3838a(long j) {
        this.f4101f = j;
    }

    public File m3849e() {
        return this.f4102g;
    }

    public void m3839a(File file) {
        this.f4102g = file;
    }

    public int m3850f() {
        return this.f4103h;
    }

    public void m3848d(int i) {
        this.f4103h = i;
    }

    public void m3844b(String str) {
        this.f4104i = str;
    }

    public void m3843b(long j) {
        this.f4105j = j;
    }
}
