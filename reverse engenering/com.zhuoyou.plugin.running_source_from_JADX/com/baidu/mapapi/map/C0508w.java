package com.baidu.mapapi.map;

import android.view.View;
import android.view.View.OnClickListener;
import com.baidu.platform.comapi.map.C0616D;

class C0508w implements OnClickListener {
    final /* synthetic */ WearMapView f1453a;

    C0508w(WearMapView wearMapView) {
        this.f1453a = wearMapView;
    }

    public void onClick(View view) {
        C0616D D = this.f1453a.f1391d.m2046a().m1953D();
        D.f1963a += 1.0f;
        this.f1453a.f1391d.m2046a().m1975a(D, 300);
    }
}
