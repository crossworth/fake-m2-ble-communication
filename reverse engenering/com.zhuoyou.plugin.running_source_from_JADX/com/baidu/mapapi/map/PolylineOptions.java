package com.baidu.mapapi.map;

import android.os.Bundle;
import android.util.Log;
import com.baidu.mapapi.model.LatLng;
import java.util.ArrayList;
import java.util.List;

public final class PolylineOptions extends OverlayOptions {
    int f1266a;
    boolean f1267b = true;
    boolean f1268c = false;
    Bundle f1269d;
    private int f1270e = -16777216;
    private List<LatLng> f1271f;
    private List<Integer> f1272g;
    private List<Integer> f1273h;
    private int f1274i = 5;
    private BitmapDescriptor f1275j;
    private List<BitmapDescriptor> f1276k;
    private boolean f1277l = true;
    private boolean f1278m = false;

    Overlay mo1760a() {
        int i = 0;
        Overlay polyline = new Polyline();
        polyline.s = this.f1267b;
        polyline.f1261f = this.f1268c;
        polyline.r = this.f1266a;
        polyline.t = this.f1269d;
        if (this.f1271f == null || this.f1271f.size() < 2) {
            throw new IllegalStateException("when you add polyline, you must at least supply 2 points");
        }
        polyline.f1257b = this.f1271f;
        polyline.f1256a = this.f1270e;
        polyline.f1260e = this.f1274i;
        polyline.f1264i = this.f1275j;
        polyline.f1265j = this.f1276k;
        polyline.f1262g = this.f1277l;
        polyline.f1263h = this.f1278m;
        if (this.f1272g != null && this.f1272g.size() < this.f1271f.size() - 1) {
            this.f1272g.addAll(this.f1272g.size(), new ArrayList((this.f1271f.size() - 1) - this.f1272g.size()));
        }
        if (this.f1272g != null && this.f1272g.size() > 0) {
            int[] iArr = new int[this.f1272g.size()];
            int i2 = 0;
            for (Integer intValue : this.f1272g) {
                iArr[i2] = intValue.intValue();
                i2++;
            }
            polyline.f1258c = iArr;
        }
        if (this.f1273h != null && this.f1273h.size() < this.f1271f.size() - 1) {
            this.f1273h.addAll(this.f1273h.size(), new ArrayList((this.f1271f.size() - 1) - this.f1273h.size()));
        }
        if (this.f1273h != null && this.f1273h.size() > 0) {
            int[] iArr2 = new int[this.f1273h.size()];
            for (Integer intValue2 : this.f1273h) {
                iArr2[i] = intValue2.intValue();
                i++;
            }
            polyline.f1259d = iArr2;
        }
        return polyline;
    }

    public PolylineOptions color(int i) {
        this.f1270e = i;
        return this;
    }

    public PolylineOptions colorsValues(List<Integer> list) {
        if (list == null) {
            throw new IllegalArgumentException("colors list can not be null");
        } else if (list.contains(null)) {
            throw new IllegalArgumentException("colors list can not contains null");
        } else {
            this.f1273h = list;
            return this;
        }
    }

    public PolylineOptions customTexture(BitmapDescriptor bitmapDescriptor) {
        this.f1275j = bitmapDescriptor;
        return this;
    }

    public PolylineOptions customTextureList(List<BitmapDescriptor> list) {
        if (list == null) {
            throw new IllegalArgumentException("customTexture list can not be null");
        }
        if (list.size() == 0) {
            Log.e("baidumapsdk", "custom texture list is empty,the texture will not work");
        }
        for (BitmapDescriptor bitmapDescriptor : list) {
            if (bitmapDescriptor == null) {
                Log.e("baidumapsdk", "the custom texture item is null,it will be discard");
            }
        }
        this.f1276k = list;
        return this;
    }

    public PolylineOptions dottedLine(boolean z) {
        this.f1268c = z;
        return this;
    }

    public PolylineOptions extraInfo(Bundle bundle) {
        this.f1269d = bundle;
        return this;
    }

    public PolylineOptions focus(boolean z) {
        this.f1277l = z;
        return this;
    }

    public int getColor() {
        return this.f1270e;
    }

    public BitmapDescriptor getCustomTexture() {
        return this.f1275j;
    }

    public List<BitmapDescriptor> getCustomTextureList() {
        return this.f1276k;
    }

    public Bundle getExtraInfo() {
        return this.f1269d;
    }

    public List<LatLng> getPoints() {
        return this.f1271f;
    }

    public List<Integer> getTextureIndexs() {
        return this.f1272g;
    }

    public int getWidth() {
        return this.f1274i;
    }

    public int getZIndex() {
        return this.f1266a;
    }

    public boolean isDottedLine() {
        return this.f1268c;
    }

    public boolean isFocus() {
        return this.f1277l;
    }

    public boolean isVisible() {
        return this.f1267b;
    }

    public PolylineOptions keepScale(boolean z) {
        this.f1278m = z;
        return this;
    }

    public PolylineOptions points(List<LatLng> list) {
        if (list == null) {
            throw new IllegalArgumentException("points list can not be null");
        } else if (list.size() < 2) {
            throw new IllegalArgumentException("points count can not less than 2");
        } else if (list.contains(null)) {
            throw new IllegalArgumentException("points list can not contains null");
        } else {
            this.f1271f = list;
            return this;
        }
    }

    public PolylineOptions textureIndex(List<Integer> list) {
        if (list == null) {
            throw new IllegalArgumentException("indexs list can not be null");
        } else if (list.contains(null)) {
            throw new IllegalArgumentException("index list can not contains null");
        } else {
            this.f1272g = list;
            return this;
        }
    }

    public PolylineOptions visible(boolean z) {
        this.f1267b = z;
        return this;
    }

    public PolylineOptions width(int i) {
        if (i > 0) {
            this.f1274i = i;
        }
        return this;
    }

    public PolylineOptions zIndex(int i) {
        this.f1266a = i;
        return this;
    }
}
