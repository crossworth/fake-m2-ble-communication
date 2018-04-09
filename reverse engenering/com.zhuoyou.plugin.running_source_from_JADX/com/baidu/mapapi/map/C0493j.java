package com.baidu.mapapi.map;

import android.view.View;
import android.view.View.OnClickListener;
import com.baidu.platform.comapi.map.C0616D;

class C0493j implements OnClickListener {
    final /* synthetic */ MapView f1426a;

    C0493j(MapView mapView) {
        this.f1426a = mapView;
    }

    public void onClick(View view) {
        C0616D D = this.f1426a.f1171c.m2046a().m1953D();
        D.f1963a -= 1.0f;
        this.f1426a.f1171c.m2046a().m1975a(D, 300);
    }
}
