package com.amap.api.mapcore.util;

import android.content.Context;
import android.os.Bundle;
import com.amap.api.mapcore.util.cc.C0227a;
import com.amap.api.mapcore.util.ch.C0231a;
import com.amap.api.maps.AMap;
import java.io.IOException;

/* compiled from: OfflineMapDownloadTask */
public class bn extends gc implements C0227a {
    private cc f4051a;
    private ce f4052b;
    private cg f4053c;
    private Context f4054e;
    private Bundle f4055f;
    private AMap f4056g;
    private boolean f4057h;

    public bn(cg cgVar, Context context) {
        this.f4055f = new Bundle();
        this.f4057h = false;
        this.f4053c = cgVar;
        this.f4054e = context;
    }

    public bn(cg cgVar, Context context, AMap aMap) {
        this(cgVar, context);
        this.f4056g = aMap;
    }

    public void mo1473a() {
        if (this.f4053c.mo2999x()) {
            this.f4053c.mo2990a(C0231a.file_io_exception);
            return;
        }
        try {
            m4017g();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void m4019b() {
        this.f4057h = true;
        if (this.f4051a != null) {
            this.f4051a.m4171c();
        } else {
            m994e();
        }
        if (this.f4052b != null) {
            this.f4052b.m418a();
        }
    }

    private String m4016f() {
        return dj.m589b(this.f4054e);
    }

    private void m4017g() throws IOException {
        this.f4051a = new cc(new cd(this.f4053c.getUrl(), m4016f(), this.f4053c.mo3000y(), 1, this.f4053c.mo3001z()), this.f4053c.getUrl(), this.f4054e, this.f4053c);
        this.f4051a.m4167a((C0227a) this);
        this.f4052b = new ce(this.f4053c, this.f4053c);
        if (!this.f4057h) {
            this.f4051a.m4166a();
        }
    }

    public void m4020c() {
        this.f4056g = null;
        if (this.f4055f != null) {
            this.f4055f.clear();
            this.f4055f = null;
        }
    }

    public void mo1474d() {
        if (this.f4052b != null) {
            this.f4052b.m419b();
        }
    }
}
