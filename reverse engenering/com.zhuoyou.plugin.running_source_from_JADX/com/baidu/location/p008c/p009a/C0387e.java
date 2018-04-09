package com.baidu.location.p008c.p009a;

final class C0387e {
    double f431a;
    double f432b;
    int f433c = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
    int f434d = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
    int f435e = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;

    C0387e() {
    }

    private double m503a(double d, double d2) {
        return Math.sqrt((d * d) + (d2 * d2));
    }

    double m504a() {
        return m503a(this.f431a, this.f432b);
    }

    double m505a(C0387e c0387e) {
        return Math.sqrt(((this.f431a - c0387e.f431a) * (this.f431a - c0387e.f431a)) + ((this.f432b - c0387e.f432b) * (this.f432b - c0387e.f432b)));
    }
}
