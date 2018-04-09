package com.baidu.mapapi.map;

import android.graphics.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class C0495l<T extends C0480a> {
    private final C0489f f1428a;
    private final int f1429b;
    private List<T> f1430c;
    private List<C0495l<T>> f1431d;

    static abstract class C0480a {
        C0480a() {
        }

        abstract Point mo1767a();
    }

    private C0495l(double d, double d2, double d3, double d4, int i) {
        this(new C0489f(d, d2, d3, d4), i);
    }

    public C0495l(C0489f c0489f) {
        this(c0489f, 0);
    }

    private C0495l(C0489f c0489f, int i) {
        this.f1431d = null;
        this.f1428a = c0489f;
        this.f1429b = i;
    }

    private void m1322a() {
        this.f1431d = new ArrayList(4);
        this.f1431d.add(new C0495l(this.f1428a.f1415a, this.f1428a.f1419e, this.f1428a.f1416b, this.f1428a.f1420f, this.f1429b + 1));
        this.f1431d.add(new C0495l(this.f1428a.f1419e, this.f1428a.f1417c, this.f1428a.f1416b, this.f1428a.f1420f, this.f1429b + 1));
        this.f1431d.add(new C0495l(this.f1428a.f1415a, this.f1428a.f1419e, this.f1428a.f1420f, this.f1428a.f1418d, this.f1429b + 1));
        this.f1431d.add(new C0495l(this.f1428a.f1419e, this.f1428a.f1417c, this.f1428a.f1420f, this.f1428a.f1418d, this.f1429b + 1));
        List<C0480a> list = this.f1430c;
        this.f1430c = null;
        for (C0480a c0480a : list) {
            m1323a((double) c0480a.mo1767a().x, (double) c0480a.mo1767a().y, c0480a);
        }
    }

    private void m1323a(double d, double d2, T t) {
        if (this.f1431d == null) {
            if (this.f1430c == null) {
                this.f1430c = new ArrayList();
            }
            this.f1430c.add(t);
            if (this.f1430c.size() > 40 && this.f1429b < 40) {
                m1322a();
            }
        } else if (d2 < this.f1428a.f1420f) {
            if (d < this.f1428a.f1419e) {
                ((C0495l) this.f1431d.get(0)).m1323a(d, d2, t);
            } else {
                ((C0495l) this.f1431d.get(1)).m1323a(d, d2, t);
            }
        } else if (d < this.f1428a.f1419e) {
            ((C0495l) this.f1431d.get(2)).m1323a(d, d2, t);
        } else {
            ((C0495l) this.f1431d.get(3)).m1323a(d, d2, t);
        }
    }

    private void m1324a(C0489f c0489f, Collection<T> collection) {
        if (!this.f1428a.m1298a(c0489f)) {
            return;
        }
        if (this.f1431d != null) {
            for (C0495l a : this.f1431d) {
                a.m1324a(c0489f, collection);
            }
        } else if (this.f1430c == null) {
        } else {
            if (c0489f.m1299b(this.f1428a)) {
                collection.addAll(this.f1430c);
                return;
            }
            for (C0480a c0480a : this.f1430c) {
                if (c0489f.m1297a(c0480a.mo1767a())) {
                    collection.add(c0480a);
                }
            }
        }
    }

    public Collection<T> m1325a(C0489f c0489f) {
        Collection<T> arrayList = new ArrayList();
        m1324a(c0489f, arrayList);
        return arrayList;
    }

    public void m1326a(T t) {
        Point a = t.mo1767a();
        if (this.f1428a.m1295a((double) a.x, (double) a.y)) {
            m1323a((double) a.x, (double) a.y, t);
        }
    }
}
