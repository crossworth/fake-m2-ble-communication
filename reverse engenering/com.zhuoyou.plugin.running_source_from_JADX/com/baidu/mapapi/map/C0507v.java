package com.baidu.mapapi.map;

import android.view.View;
import android.view.View.OnClickListener;
import com.baidu.platform.comapi.map.C0616D;

class C0507v implements OnClickListener {
    final /* synthetic */ WearMapView f1452a;

    C0507v(WearMapView wearMapView) {
        this.f1452a = wearMapView;
    }

    public void onClick(View view) {
        C0616D D = this.f1452a.f1391d.m2046a().m1953D();
        D.f1963a -= 1.0f;
        this.f1452a.f1391d.m2046a().m1975a(D, 300);
    }
}
