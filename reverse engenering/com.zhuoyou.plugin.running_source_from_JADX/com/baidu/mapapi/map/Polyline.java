package com.baidu.mapapi.map;

import android.os.Bundle;
import android.util.Log;
import com.baidu.mapapi.model.CoordUtil;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.baidu.platform.comapi.map.C0636h;
import java.util.List;
import org.andengine.util.level.constants.LevelConstants;

public final class Polyline extends Overlay {
    int f1256a;
    List<LatLng> f1257b;
    int[] f1258c;
    int[] f1259d;
    int f1260e;
    boolean f1261f;
    boolean f1262g;
    boolean f1263h;
    BitmapDescriptor f1264i;
    List<BitmapDescriptor> f1265j;

    Polyline() {
        this.f1262g = false;
        this.f1263h = true;
        this.q = C0636h.polyline;
    }

    private Bundle m1175a(boolean z) {
        return z ? BitmapDescriptorFactory.fromAsset("lineDashTexture.png").m1105b() : this.f1264i.m1105b();
    }

    static void m1176a(int[] iArr, Bundle bundle) {
        if (iArr != null && iArr.length > 0) {
            bundle.putIntArray("traffic_array", iArr);
        }
    }

    private Bundle m1177b(boolean z) {
        if (z) {
            Bundle bundle = new Bundle();
            bundle.putInt("total", 1);
            bundle.putBundle("texture_0", BitmapDescriptorFactory.fromAsset("lineDashTexture.png").m1105b());
            return bundle;
        }
        Bundle bundle2 = new Bundle();
        int i = 0;
        for (int i2 = 0; i2 < this.f1265j.size(); i2++) {
            if (this.f1265j.get(i2) != null) {
                bundle2.putBundle("texture_" + String.valueOf(i), ((BitmapDescriptor) this.f1265j.get(i2)).m1105b());
                i++;
            }
        }
        bundle2.putInt("total", i);
        return bundle2;
    }

    static void m1178b(int[] iArr, Bundle bundle) {
        if (iArr != null && iArr.length > 0) {
            bundle.putIntArray("color_array", iArr);
            bundle.putInt("total", 1);
        }
    }

    Bundle mo1759a(Bundle bundle) {
        int i = 1;
        super.mo1759a(bundle);
        GeoPoint ll2mc = CoordUtil.ll2mc((LatLng) this.f1257b.get(0));
        bundle.putDouble("location_x", ll2mc.getLongitudeE6());
        bundle.putDouble("location_y", ll2mc.getLatitudeE6());
        bundle.putInt(LevelConstants.TAG_LEVEL_ATTRIBUTE_WIDTH, this.f1260e);
        Overlay.m1058a(this.f1257b, bundle);
        Overlay.m1057a(this.f1256a, bundle);
        m1176a(this.f1258c, bundle);
        m1178b(this.f1259d, bundle);
        if (this.f1258c != null && this.f1258c.length > 0 && this.f1258c.length > this.f1257b.size() - 1) {
            Log.e("baidumapsdk", "the size of textureIndexs is larger than the size of points");
        }
        if (this.f1261f) {
            bundle.putInt("dotline", 1);
        } else {
            bundle.putInt("dotline", 0);
        }
        bundle.putInt("focus", this.f1262g ? 1 : 0);
        try {
            if (this.f1264i != null) {
                bundle.putInt("custom", 1);
                bundle.putBundle("image_info", m1175a(false));
            } else {
                if (this.f1261f) {
                    bundle.putBundle("image_info", m1175a(true));
                }
                bundle.putInt("custom", 0);
            }
            if (this.f1265j != null) {
                bundle.putInt("customlist", 1);
                bundle.putBundle("image_info_list", m1177b(false));
            } else {
                if (this.f1261f && ((this.f1258c != null && this.f1258c.length > 0) || (this.f1259d != null && this.f1259d.length > 0))) {
                    bundle.putBundle("image_info_list", m1177b(true));
                }
                bundle.putInt("customlist", 0);
            }
            String str = "keep";
            if (!this.f1263h) {
                i = 0;
            }
            bundle.putInt(str, i);
        } catch (Exception e) {
            Log.e("baidumapsdk", "load texture resource failed!");
            bundle.putInt("dotline", 0);
        }
        return bundle;
    }

    public int getColor() {
        return this.f1256a;
    }

    public List<LatLng> getPoints() {
        return this.f1257b;
    }

    public int getWidth() {
        return this.f1260e;
    }

    public boolean isDottedLine() {
        return this.f1261f;
    }

    public boolean isFocus() {
        return this.f1262g;
    }

    public void setColor(int i) {
        this.f1256a = i;
        this.listener.mo1769b(this);
    }

    public void setDottedLine(boolean z) {
        this.f1261f = z;
        this.listener.mo1769b(this);
    }

    public void setFocus(boolean z) {
        this.f1262g = z;
        this.listener.mo1769b(this);
    }

    public void setPoints(List<LatLng> list) {
        if (list == null) {
            throw new IllegalArgumentException("points list can not be null");
        } else if (list.size() < 2) {
            throw new IllegalArgumentException("points count can not less than 2 or more than 10000");
        } else if (list.contains(null)) {
            throw new IllegalArgumentException("points list can not contains null");
        } else {
            this.f1257b = list;
            this.listener.mo1769b(this);
        }
    }

    public void setWidth(int i) {
        if (i > 0) {
            this.f1260e = i;
            this.listener.mo1769b(this);
        }
    }
}
