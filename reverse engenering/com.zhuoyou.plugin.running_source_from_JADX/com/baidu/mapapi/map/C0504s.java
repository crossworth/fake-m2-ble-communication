package com.baidu.mapapi.map;

import android.view.View;
import android.view.View.OnClickListener;
import com.baidu.platform.comapi.map.C0616D;

class C0504s implements OnClickListener {
    final /* synthetic */ TextureMapView f1445a;

    C0504s(TextureMapView textureMapView) {
        this.f1445a = textureMapView;
    }

    public void onClick(View view) {
        C0616D D = this.f1445a.f1329b.m1915b().m1953D();
        D.f1963a += 1.0f;
        this.f1445a.f1329b.m1915b().m1975a(D, 300);
    }
}
