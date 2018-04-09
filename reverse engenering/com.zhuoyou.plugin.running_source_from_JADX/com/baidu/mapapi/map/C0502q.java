package com.baidu.mapapi.map;

import android.graphics.Bitmap;
import android.view.MotionEvent;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.baidu.platform.comapi.map.C0482k;
import com.baidu.platform.comapi.map.C0616D;
import javax.microedition.khronos.opengles.GL10;

class C0502q implements C0482k {
    final /* synthetic */ TextureMapView f1443a;

    C0502q(TextureMapView textureMapView) {
        this.f1443a = textureMapView;
    }

    public void mo1770a() {
        if (this.f1443a.f1329b != null && this.f1443a.f1329b.m1915b() != null) {
            float f = this.f1443a.f1329b.m1915b().m1953D().f1963a;
            if (this.f1443a.f1340o != f) {
                CharSequence format;
                int intValue = ((Integer) TextureMapView.f1328n.get((int) f)).intValue();
                int i = (int) (((double) intValue) / this.f1443a.f1329b.m1915b().m1953D().f1975m);
                this.f1443a.f1339m.setPadding(i / 2, 0, i / 2, 0);
                if (intValue >= 1000) {
                    format = String.format(" %d公里 ", new Object[]{Integer.valueOf(intValue / 1000)});
                } else {
                    format = String.format(" %d米 ", new Object[]{Integer.valueOf(intValue)});
                }
                this.f1443a.f1337k.setText(format);
                this.f1443a.f1338l.setText(format);
                this.f1443a.f1340o = f;
            }
            this.f1443a.m1199b();
            this.f1443a.requestLayout();
        }
    }

    public void mo1771a(Bitmap bitmap) {
    }

    public void mo1772a(MotionEvent motionEvent) {
    }

    public void mo1773a(GeoPoint geoPoint) {
    }

    public void mo1774a(C0616D c0616d) {
    }

    public void mo1775a(String str) {
    }

    public void mo1776a(GL10 gl10, C0616D c0616d) {
    }

    public void mo1777a(boolean z) {
    }

    public void mo1778b() {
    }

    public void mo1779b(GeoPoint geoPoint) {
    }

    public void mo1780b(C0616D c0616d) {
    }

    public boolean mo1781b(String str) {
        return false;
    }

    public void mo1782c() {
    }

    public void mo1783c(GeoPoint geoPoint) {
    }

    public void mo1784c(C0616D c0616d) {
    }

    public void mo1785d() {
    }

    public void mo1786d(GeoPoint geoPoint) {
    }

    public void mo1787e() {
    }

    public void mo1788e(GeoPoint geoPoint) {
    }

    public void mo1789f() {
    }
}
