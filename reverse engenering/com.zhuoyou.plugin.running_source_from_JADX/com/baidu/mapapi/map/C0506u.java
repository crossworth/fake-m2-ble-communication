package com.baidu.mapapi.map;

import android.graphics.Bitmap;
import android.view.MotionEvent;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.baidu.platform.comapi.map.C0482k;
import com.baidu.platform.comapi.map.C0616D;
import javax.microedition.khronos.opengles.GL10;

class C0506u implements C0482k {
    final /* synthetic */ WearMapView f1451a;

    C0506u(WearMapView wearMapView) {
        this.f1451a = wearMapView;
    }

    public void mo1770a() {
        if (this.f1451a.f1391d != null && this.f1451a.f1391d.m2046a() != null) {
            float f = this.f1451a.f1391d.m2046a().m1953D().f1963a;
            if (this.f1451a.f1407x != f) {
                CharSequence format;
                int intValue = ((Integer) WearMapView.f1384u.get((int) f)).intValue();
                int i = (int) (((double) intValue) / this.f1451a.f1391d.m2046a().m1953D().f1975m);
                this.f1451a.f1403p.setPadding(i / 2, 0, i / 2, 0);
                if (intValue >= 1000) {
                    format = String.format(" %d公里 ", new Object[]{Integer.valueOf(intValue / 1000)});
                } else {
                    format = String.format(" %d米 ", new Object[]{Integer.valueOf(intValue)});
                }
                this.f1451a.f1401n.setText(format);
                this.f1451a.f1402o.setText(format);
                this.f1451a.f1407x = f;
            }
            this.f1451a.requestLayout();
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
