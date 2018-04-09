package com.baidu.mapapi.map;

import android.view.View;
import android.view.View.OnClickListener;
import com.baidu.platform.comapi.map.C0616D;

class C0503r implements OnClickListener {
    final /* synthetic */ TextureMapView f1444a;

    C0503r(TextureMapView textureMapView) {
        this.f1444a = textureMapView;
    }

    public void onClick(View view) {
        C0616D D = this.f1444a.f1329b.m1915b().m1953D();
        D.f1963a -= 1.0f;
        this.f1444a.f1329b.m1915b().m1975a(D, 300);
    }
}
