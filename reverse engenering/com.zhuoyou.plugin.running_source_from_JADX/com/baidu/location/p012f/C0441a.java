package com.baidu.location.p012f;

import java.util.Locale;

public class C0441a {
    public int f727a;
    public int f728b;
    public int f729c;
    public int f730d;
    public int f731e;
    public int f732f;
    public long f733g;
    public int f734h;
    public char f735i;
    private boolean f736j;

    public C0441a() {
        this.f727a = -1;
        this.f728b = -1;
        this.f729c = -1;
        this.f730d = -1;
        this.f731e = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        this.f732f = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        this.f733g = 0;
        this.f734h = -1;
        this.f735i = '0';
        this.f736j = false;
        this.f733g = System.currentTimeMillis();
    }

    public C0441a(int i, int i2, int i3, int i4, int i5, char c) {
        this.f727a = -1;
        this.f728b = -1;
        this.f729c = -1;
        this.f730d = -1;
        this.f731e = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        this.f732f = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        this.f733g = 0;
        this.f734h = -1;
        this.f735i = '0';
        this.f736j = false;
        this.f727a = i;
        this.f728b = i2;
        this.f729c = i3;
        this.f730d = i4;
        this.f734h = i5;
        this.f735i = c;
        this.f733g = System.currentTimeMillis();
    }

    public C0441a(C0441a c0441a) {
        this(c0441a.f727a, c0441a.f728b, c0441a.f729c, c0441a.f730d, c0441a.f734h, c0441a.f735i);
        this.f733g = c0441a.f733g;
    }

    public boolean m841a() {
        long currentTimeMillis = System.currentTimeMillis();
        return currentTimeMillis - this.f733g > 0 && currentTimeMillis - this.f733g < 3000;
    }

    public boolean m842a(C0441a c0441a) {
        return this.f727a == c0441a.f727a && this.f728b == c0441a.f728b && this.f730d == c0441a.f730d && this.f729c == c0441a.f729c;
    }

    public boolean m843b() {
        return this.f727a > -1 && this.f728b > 0;
    }

    public boolean m844c() {
        return this.f727a == -1 && this.f728b == -1 && this.f730d == -1 && this.f729c == -1;
    }

    public boolean m845d() {
        return this.f727a > -1 && this.f728b > -1 && this.f730d == -1 && this.f729c == -1;
    }

    public boolean m846e() {
        return this.f727a > -1 && this.f728b > -1 && this.f730d > -1 && this.f729c > -1;
    }

    public void m847f() {
        this.f736j = true;
    }

    public String m848g() {
        StringBuffer stringBuffer = new StringBuffer(128);
        stringBuffer.append(this.f728b + 23);
        stringBuffer.append("H");
        stringBuffer.append(this.f727a + 45);
        stringBuffer.append("K");
        stringBuffer.append(this.f730d + 54);
        stringBuffer.append("Q");
        stringBuffer.append(this.f729c + 203);
        return stringBuffer.toString();
    }

    public String m849h() {
        StringBuffer stringBuffer = new StringBuffer(128);
        stringBuffer.append("&nw=");
        stringBuffer.append(this.f735i);
        stringBuffer.append(String.format(Locale.CHINA, "&cl=%d|%d|%d|%d&cl_s=%d", new Object[]{Integer.valueOf(this.f729c), Integer.valueOf(this.f730d), Integer.valueOf(this.f727a), Integer.valueOf(this.f728b), Integer.valueOf(this.f734h)}));
        if (this.f736j) {
            stringBuffer.append("&newcl=1");
        }
        return stringBuffer.toString();
    }
}
