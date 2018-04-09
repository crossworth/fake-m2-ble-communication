package com.amap.api.mapcore.util;

/* compiled from: CityStateImp */
public abstract class cl {
    protected int f378a;
    protected bg f379b;

    public abstract void mo1634c();

    public cl(int i, bg bgVar) {
        this.f378a = i;
        this.f379b = bgVar;
    }

    public int m439b() {
        return this.f378a;
    }

    public boolean m438a(cl clVar) {
        return clVar.m439b() == m439b();
    }

    public void m440b(cl clVar) {
        cf.m424a(m439b() + " ==> " + clVar.m439b() + "   " + getClass() + "==>" + clVar.getClass());
    }

    public void mo1635d() {
        cf.m424a("Wrong call start()  State: " + m439b() + "  " + getClass());
    }

    public void mo3008e() {
        cf.m424a("Wrong call continueDownload()  State: " + m439b() + "  " + getClass());
    }

    public void mo3007f() {
        cf.m424a("Wrong call pause()  State: " + m439b() + "  " + getClass());
    }

    public void mo1633a() {
        cf.m424a("Wrong call delete()  State: " + m439b() + "  " + getClass());
    }

    public void mo1636g() {
        cf.m424a("Wrong call fail()  State: " + m439b() + "  " + getClass());
    }

    public void mo3006h() {
        cf.m424a("Wrong call hasNew()  State: " + m439b() + "  " + getClass());
    }

    public void mo1637i() {
        cf.m424a("Wrong call complete()  State: " + m439b() + "  " + getClass());
    }
}
