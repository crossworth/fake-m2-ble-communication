package com.baidu.mapapi.map;

import android.graphics.Point;
import android.view.ViewGroup.LayoutParams;
import com.baidu.mapapi.model.LatLng;

public final class MapViewLayoutParams extends LayoutParams {
    public static final int ALIGN_BOTTOM = 16;
    public static final int ALIGN_CENTER_HORIZONTAL = 4;
    public static final int ALIGN_CENTER_VERTICAL = 32;
    public static final int ALIGN_LEFT = 1;
    public static final int ALIGN_RIGHT = 2;
    public static final int ALIGN_TOP = 8;
    LatLng f1201a;
    Point f1202b;
    ELayoutMode f1203c;
    float f1204d;
    float f1205e;
    int f1206f;

    public static final class Builder {
        private int f1193a;
        private int f1194b;
        private LatLng f1195c;
        private Point f1196d;
        private ELayoutMode f1197e = ELayoutMode.absoluteMode;
        private int f1198f = 4;
        private int f1199g = 16;
        private int f1200h;

        public Builder align(int i, int i2) {
            if (i == 1 || i == 2 || i == 4) {
                this.f1198f = i;
            }
            if (i2 == 8 || i2 == 16 || i2 == 32) {
                this.f1199g = i2;
            }
            return this;
        }

        public MapViewLayoutParams build() {
            Object obj = 1;
            if (this.f1197e != ELayoutMode.mapMode ? !(this.f1197e == ELayoutMode.absoluteMode && this.f1196d == null) : this.f1195c != null) {
                obj = null;
            }
            if (obj == null) {
                return new MapViewLayoutParams(this.f1193a, this.f1194b, this.f1195c, this.f1196d, this.f1197e, this.f1198f, this.f1199g, this.f1200h);
            }
            throw new IllegalStateException("if it is map mode, you must supply position info; else if it is absolute mode, you must supply the point info");
        }

        public Builder height(int i) {
            this.f1194b = i;
            return this;
        }

        public Builder layoutMode(ELayoutMode eLayoutMode) {
            this.f1197e = eLayoutMode;
            return this;
        }

        public Builder point(Point point) {
            this.f1196d = point;
            return this;
        }

        public Builder position(LatLng latLng) {
            this.f1195c = latLng;
            return this;
        }

        public Builder width(int i) {
            this.f1193a = i;
            return this;
        }

        public Builder yOffset(int i) {
            this.f1200h = i;
            return this;
        }
    }

    public enum ELayoutMode {
        mapMode,
        absoluteMode
    }

    MapViewLayoutParams(int i, int i2, LatLng latLng, Point point, ELayoutMode eLayoutMode, int i3, int i4, int i5) {
        super(i, i2);
        this.f1201a = latLng;
        this.f1202b = point;
        this.f1203c = eLayoutMode;
        switch (i3) {
            case 1:
                this.f1204d = 0.0f;
                break;
            case 2:
                this.f1204d = 1.0f;
                break;
            case 4:
                this.f1204d = 0.5f;
                break;
            default:
                this.f1204d = 0.5f;
                break;
        }
        switch (i4) {
            case 8:
                this.f1205e = 0.0f;
                break;
            case 16:
                this.f1205e = 1.0f;
                break;
            case 32:
                this.f1205e = 0.5f;
                break;
            default:
                this.f1205e = 1.0f;
                break;
        }
        this.f1206f = i5;
    }
}
