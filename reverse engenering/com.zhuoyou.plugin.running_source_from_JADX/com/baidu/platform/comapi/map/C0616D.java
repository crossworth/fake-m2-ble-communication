package com.baidu.platform.comapi.map;

import android.os.Bundle;
import com.baidu.mapapi.model.inner.Point;

public class C0616D {
    private static final String f1962t = C0616D.class.getSimpleName();
    public float f1963a = 12.0f;
    public int f1964b = 0;
    public int f1965c = 0;
    public double f1966d = 1.2958162E7d;
    public double f1967e = 4825907.0d;
    public int f1968f = -1;
    public int f1969g = -1;
    public long f1970h = 0;
    public long f1971i = 0;
    public C0615b f1972j = new C0615b(this);
    public C0614a f1973k = new C0614a(this);
    public boolean f1974l = false;
    public double f1975m;
    public double f1976n;
    public int f1977o;
    public String f1978p;
    public float f1979q;
    public boolean f1980r;
    public int f1981s;

    public class C0614a {
        public long f1948a = 0;
        public long f1949b = 0;
        public long f1950c = 0;
        public long f1951d = 0;
        public Point f1952e = new Point(0, 0);
        public Point f1953f = new Point(0, 0);
        public Point f1954g = new Point(0, 0);
        public Point f1955h = new Point(0, 0);
        final /* synthetic */ C0616D f1956i;

        public C0614a(C0616D c0616d) {
            this.f1956i = c0616d;
        }
    }

    public class C0615b {
        public int f1957a = 0;
        public int f1958b = 0;
        public int f1959c = 0;
        public int f1960d = 0;
        final /* synthetic */ C0616D f1961e;

        public C0615b(C0616D c0616d) {
            this.f1961e = c0616d;
        }
    }

    public Bundle m1905a(C0633e c0633e) {
        int i = 1;
        if (this.f1963a < c0633e.f2048b) {
            this.f1963a = c0633e.f2048b;
        }
        if (this.f1963a > c0633e.f2047a) {
            this.f1963a = c0633e.f2047a;
        }
        while (this.f1964b < 0) {
            this.f1964b += 360;
        }
        this.f1964b %= 360;
        if (this.f1965c > 0) {
            this.f1965c = 0;
        }
        if (this.f1965c < -45) {
            this.f1965c = -45;
        }
        Bundle bundle = new Bundle();
        bundle.putDouble("level", (double) this.f1963a);
        bundle.putDouble("rotation", (double) this.f1964b);
        bundle.putDouble("overlooking", (double) this.f1965c);
        bundle.putDouble("centerptx", this.f1966d);
        bundle.putDouble("centerpty", this.f1967e);
        bundle.putInt("left", this.f1972j.f1957a);
        bundle.putInt("right", this.f1972j.f1958b);
        bundle.putInt("top", this.f1972j.f1959c);
        bundle.putInt("bottom", this.f1972j.f1960d);
        if (this.f1968f >= 0 && this.f1969g >= 0 && this.f1968f <= this.f1972j.f1958b && this.f1969g <= this.f1972j.f1960d && this.f1972j.f1958b > 0 && this.f1972j.f1960d > 0) {
            int i2 = this.f1969g - ((this.f1972j.f1960d - this.f1972j.f1959c) / 2);
            this.f1970h = (long) (this.f1968f - ((this.f1972j.f1958b - this.f1972j.f1957a) / 2));
            this.f1971i = (long) (-i2);
            bundle.putLong("xoffset", this.f1970h);
            bundle.putLong("yoffset", this.f1971i);
        }
        bundle.putInt("lbx", this.f1973k.f1952e.f1465x);
        bundle.putInt("lby", this.f1973k.f1952e.f1466y);
        bundle.putInt("ltx", this.f1973k.f1953f.f1465x);
        bundle.putInt("lty", this.f1973k.f1953f.f1466y);
        bundle.putInt("rtx", this.f1973k.f1954g.f1465x);
        bundle.putInt("rty", this.f1973k.f1954g.f1466y);
        bundle.putInt("rbx", this.f1973k.f1955h.f1465x);
        bundle.putInt("rby", this.f1973k.f1955h.f1466y);
        bundle.putInt("bfpp", this.f1974l ? 1 : 0);
        bundle.putInt("animation", 1);
        bundle.putInt("animatime", this.f1977o);
        bundle.putString("panoid", this.f1978p);
        bundle.putInt("autolink", 0);
        bundle.putFloat("siangle", this.f1979q);
        String str = "isbirdeye";
        if (!this.f1980r) {
            i = 0;
        }
        bundle.putInt(str, i);
        bundle.putInt("ssext", this.f1981s);
        return bundle;
    }

    public void m1906a(Bundle bundle) {
        boolean z = true;
        this.f1963a = (float) bundle.getDouble("level");
        this.f1964b = (int) bundle.getDouble("rotation");
        this.f1965c = (int) bundle.getDouble("overlooking");
        this.f1966d = bundle.getDouble("centerptx");
        this.f1967e = bundle.getDouble("centerpty");
        this.f1972j.f1957a = bundle.getInt("left");
        this.f1972j.f1958b = bundle.getInt("right");
        this.f1972j.f1959c = bundle.getInt("top");
        this.f1972j.f1960d = bundle.getInt("bottom");
        this.f1970h = bundle.getLong("xoffset");
        this.f1971i = bundle.getLong("yoffset");
        if (!(this.f1972j.f1958b == 0 || this.f1972j.f1960d == 0)) {
            int i = (this.f1972j.f1960d - this.f1972j.f1959c) / 2;
            int i2 = (int) (-this.f1971i);
            this.f1968f = ((this.f1972j.f1958b - this.f1972j.f1957a) / 2) + ((int) this.f1970h);
            this.f1969g = i2 + i;
        }
        this.f1973k.f1948a = bundle.getLong("gleft");
        this.f1973k.f1949b = bundle.getLong("gright");
        this.f1973k.f1950c = bundle.getLong("gtop");
        this.f1973k.f1951d = bundle.getLong("gbottom");
        if (this.f1973k.f1948a <= -20037508) {
            this.f1973k.f1948a = -20037508;
        }
        if (this.f1973k.f1949b >= 20037508) {
            this.f1973k.f1949b = 20037508;
        }
        if (this.f1973k.f1950c >= 20037508) {
            this.f1973k.f1950c = 20037508;
        }
        if (this.f1973k.f1951d <= -20037508) {
            this.f1973k.f1951d = -20037508;
        }
        this.f1973k.f1952e.f1465x = bundle.getInt("lbx");
        this.f1973k.f1952e.f1466y = bundle.getInt("lby");
        this.f1973k.f1953f.f1465x = bundle.getInt("ltx");
        this.f1973k.f1953f.f1466y = bundle.getInt("lty");
        this.f1973k.f1954g.f1465x = bundle.getInt("rtx");
        this.f1973k.f1954g.f1466y = bundle.getInt("rty");
        this.f1973k.f1955h.f1465x = bundle.getInt("rbx");
        this.f1973k.f1955h.f1466y = bundle.getInt("rby");
        this.f1974l = bundle.getInt("bfpp") == 1;
        this.f1975m = bundle.getDouble("adapterzoomunit");
        this.f1976n = bundle.getDouble("zoomunit");
        this.f1978p = bundle.getString("panoid");
        this.f1979q = bundle.getFloat("siangle");
        if (bundle.getInt("isbirdeye") == 0) {
            z = false;
        }
        this.f1980r = z;
        this.f1981s = bundle.getInt("ssext");
    }
}
