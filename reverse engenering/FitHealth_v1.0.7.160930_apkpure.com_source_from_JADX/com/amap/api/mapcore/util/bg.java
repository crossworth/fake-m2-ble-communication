package com.amap.api.mapcore.util;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import android.util.Log;
import com.amap.api.mapcore.util.bz.C0210a;
import com.amap.api.mapcore.util.ch.C0231a;
import com.amap.api.maps.offlinemap.OfflineMapCity;
import com.zhuoyou.plugin.database.DataBaseContants;
import java.io.File;

/* compiled from: CityObject */
public class bg extends OfflineMapCity implements bq, cg {
    public static final Creator<bg> f5340l = new bh();
    public cl f5341a;
    public cl f5342b;
    public cl f5343c;
    public cl f5344d;
    public cl f5345e;
    public cl f5346f;
    public cl f5347g;
    public cl f5348h;
    cl f5349i;
    Context f5350j;
    boolean f5351k;
    private String f5352m;
    private String f5353n;
    private long f5354o;

    public void m5703a(String str) {
        this.f5353n = str;
    }

    public String m5696a() {
        return this.f5353n;
    }

    public String mo2991b() {
        return getUrl();
    }

    public bg(Context context, OfflineMapCity offlineMapCity) {
        this(context, offlineMapCity.getState());
        setCity(offlineMapCity.getCity());
        setUrl(offlineMapCity.getUrl());
        setState(offlineMapCity.getState());
        setCompleteCode(offlineMapCity.getcompleteCode());
        setAdcode(offlineMapCity.getAdcode());
        setVersion(offlineMapCity.getVersion());
        setSize(offlineMapCity.getSize());
        setCode(offlineMapCity.getCode());
        setJianpin(offlineMapCity.getJianpin());
        setPinyin(offlineMapCity.getPinyin());
        m5722s();
    }

    public bg(Context context, int i) {
        this.f5341a = new cn(6, this);
        this.f5342b = new ct(2, this);
        this.f5343c = new cp(0, this);
        this.f5344d = new cr(3, this);
        this.f5345e = new cs(1, this);
        this.f5346f = new cm(4, this);
        this.f5347g = new cq(7, this);
        this.f5348h = new co(-1, this);
        this.f5352m = null;
        this.f5353n = "";
        this.f5351k = false;
        this.f5354o = 0;
        this.f5350j = context;
        m5697a(i);
    }

    public void m5697a(int i) {
        switch (i) {
            case -1:
                this.f5349i = this.f5348h;
                break;
            case 0:
                this.f5349i = this.f5343c;
                break;
            case 1:
                this.f5349i = this.f5345e;
                break;
            case 2:
                this.f5349i = this.f5342b;
                break;
            case 3:
                this.f5349i = this.f5344d;
                break;
            case 4:
                this.f5349i = this.f5346f;
                break;
            case 6:
                this.f5349i = this.f5341a;
                break;
            case 7:
                this.f5349i = this.f5347g;
                break;
            default:
                if (i < 0) {
                    this.f5349i = this.f5348h;
                    break;
                }
                break;
        }
        setState(i);
    }

    public void m5702a(cl clVar) {
        this.f5349i = clVar;
        setState(clVar.m439b());
    }

    public cl m5706c() {
        return this.f5349i;
    }

    public void m5707d() {
        bi a = bi.m277a(this.f5350j);
        if (a != null) {
            a.m298c(this);
        }
    }

    public void m5708e() {
        bi a = bi.m277a(this.f5350j);
        if (a != null) {
            a.m304e(this);
            m5707d();
        }
    }

    public void m5709f() {
        cf.m424a("CityOperation current State==>" + m5706c().m439b());
        if (this.f5349i.equals(this.f5344d)) {
            this.f5349i.mo3008e();
        } else if (this.f5349i.equals(this.f5343c)) {
            this.f5349i.mo3007f();
        } else if (this.f5349i.equals(this.f5347g) || this.f5349i.equals(this.f5348h)) {
            m5713j();
            this.f5351k = true;
        } else {
            m5706c().mo1634c();
        }
    }

    public void m5710g() {
        this.f5349i.mo1636g();
    }

    public void m5711h() {
        this.f5349i.mo1633a();
        if (this.f5351k) {
            this.f5349i.mo1634c();
        }
        this.f5351k = false;
    }

    public void m5712i() {
        if (this.f5349i.equals(this.f5346f)) {
            this.f5349i.mo3006h();
        } else {
            this.f5349i.mo3006h();
        }
    }

    public void m5713j() {
        bi a = bi.m277a(this.f5350j);
        if (a != null) {
            a.m290a(this);
        }
    }

    public void m5714k() {
        bi a = bi.m277a(this.f5350j);
        if (a != null) {
            a.m295b(this);
        }
    }

    public void m5715l() {
        bi a = bi.m277a(this.f5350j);
        if (a != null) {
            a.m301d(this);
        }
    }

    public void mo2993m() {
        this.f5354o = 0;
        if (!this.f5349i.equals(this.f5342b)) {
            Log.e(DataBaseContants.MSG_STATE, "state must be waiting when download onStart");
        }
        this.f5349i.mo1635d();
    }

    public void mo2989a(long j, long j2) {
        long j3 = (100 * j2) / j;
        if (((int) j3) != getcompleteCode()) {
            setCompleteCode((int) j3);
            m5707d();
        }
    }

    public void mo2994n() {
        if (!this.f5349i.equals(this.f5343c)) {
            Log.e(DataBaseContants.MSG_STATE, "state must be Loading when download onFinish");
        }
        this.f5349i.mo1637i();
    }

    public void mo2990a(C0231a c0231a) {
        if (this.f5349i.equals(this.f5343c) || this.f5349i.equals(this.f5342b)) {
            this.f5349i.mo1636g();
        }
    }

    public void mo2995o() {
        m5708e();
    }

    public void mo2996p() {
        this.f5354o = 0;
        setCompleteCode(0);
        if (this.f5349i.equals(this.f5345e)) {
            this.f5349i.mo1635d();
        } else {
            this.f5349i.mo1635d();
        }
    }

    public void mo2997q() {
        if (this.f5349i.equals(this.f5345e)) {
            this.f5349i.mo1636g();
        } else {
            this.f5349i.mo1636g();
        }
    }

    public void mo2988a(long j) {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - this.f5354o > 500) {
            if (((int) j) > getcompleteCode()) {
                setCompleteCode((int) j);
                m5707d();
            }
            this.f5354o = currentTimeMillis;
        }
    }

    public void mo2992b(String str) {
        Object t;
        Object u;
        if (this.f5349i.equals(this.f5345e)) {
            this.f5353n = str;
            t = m5723t();
            u = m5724u();
        } else {
            this.f5353n = str;
            t = m5723t();
            u = m5724u();
        }
        if (TextUtils.isEmpty(t) || TextUtils.isEmpty(u)) {
            mo2997q();
            return;
        }
        File file = new File(u + "/");
        File file2 = new File(dj.m570a(this.f5350j) + "vmap/");
        File file3 = new File(dj.m570a(this.f5350j));
        if (!file3.exists()) {
            file3.mkdir();
        }
        if (!file2.exists()) {
            file2.mkdir();
        }
        m5693a(file, file2, t);
    }

    public void mo2998r() {
        m5708e();
    }

    protected void m5722s() {
        this.f5352m = bi.f255a + getAdcode() + ".zip" + ".tmp";
    }

    public String m5723t() {
        if (TextUtils.isEmpty(this.f5352m)) {
            return null;
        }
        return this.f5352m.substring(0, this.f5352m.lastIndexOf("."));
    }

    public String m5724u() {
        if (TextUtils.isEmpty(this.f5352m)) {
            return null;
        }
        String t = m5723t();
        return t.substring(0, t.lastIndexOf(46));
    }

    private void m5693a(final File file, File file2, final String str) {
        new bz().m389a(file, file2, -1, cf.m421a(file), new C0210a(this) {
            final /* synthetic */ bg f4050c;

            public void mo1470a(String str, String str2, float f) {
                int i = (int) (60.0d + (((double) f) * 0.39d));
                if (i - this.f4050c.getcompleteCode() > 0 && System.currentTimeMillis() - this.f4050c.f5354o > 1000) {
                    this.f4050c.setCompleteCode(i);
                    this.f4050c.f5354o = System.currentTimeMillis();
                }
            }

            public void mo1469a(String str, String str2) {
            }

            public void mo1472b(String str, String str2) {
                try {
                    new File(str).delete();
                    cf.m428b(file);
                    this.f4050c.setCompleteCode(100);
                    this.f4050c.f5349i.mo1637i();
                } catch (Exception e) {
                    this.f4050c.f5349i.mo1636g();
                }
            }

            public void mo1471a(String str, String str2, int i) {
                this.f4050c.f5349i.mo1636g();
            }
        });
    }

    public boolean m5725v() {
        return ((double) cf.m420a()) < (((double) getSize()) * 2.5d) - ((double) (((long) getcompleteCode()) * getSize())) ? false : false;
    }

    public bs m5726w() {
        setState(this.f5349i.m439b());
        bs bsVar = new bs((OfflineMapCity) this, this.f5350j);
        bsVar.m4023a(m5696a());
        cf.m424a("vMapFileNames: " + m5696a());
        return bsVar;
    }

    public void m5700a(bs bsVar) {
        m5697a(bsVar.l);
        setCity(bsVar.m360e());
        setSize(bsVar.m364i());
        setVersion(bsVar.m361f());
        setCompleteCode(bsVar.m365j());
        setAdcode(bsVar.m362g());
        setUrl(bsVar.m363h());
        String c = bsVar.m4026c();
        if (c != null && c.length() > 0) {
            m5703a(c);
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(this.f5353n);
    }

    public bg(Parcel parcel) {
        super(parcel);
        this.f5341a = new cn(6, this);
        this.f5342b = new ct(2, this);
        this.f5343c = new cp(0, this);
        this.f5344d = new cr(3, this);
        this.f5345e = new cs(1, this);
        this.f5346f = new cm(4, this);
        this.f5347g = new cq(7, this);
        this.f5348h = new co(-1, this);
        this.f5352m = null;
        this.f5353n = "";
        this.f5351k = false;
        this.f5354o = 0;
        this.f5353n = parcel.readString();
    }

    public boolean mo2999x() {
        return m5725v();
    }

    public String mo3000y() {
        StringBuffer stringBuffer = new StringBuffer(getAdcode());
        stringBuffer.append(".zip");
        return stringBuffer.toString();
    }

    public String mo3001z() {
        return getAdcode();
    }

    public String mo2986A() {
        return m5723t();
    }

    public String mo2987B() {
        return m5724u();
    }
}
