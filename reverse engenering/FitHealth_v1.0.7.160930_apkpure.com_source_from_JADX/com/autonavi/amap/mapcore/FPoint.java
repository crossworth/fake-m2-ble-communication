package com.autonavi.amap.mapcore;

public class FPoint {
    public float f2028x;
    public float f2029y;

    public FPoint(float f, float f2) {
        this.f2028x = f;
        this.f2029y = f2;
    }

    public boolean equals(Object obj) {
        FPoint fPoint = (FPoint) obj;
        if (fPoint != null && this.f2028x == fPoint.f2028x && this.f2029y == fPoint.f2029y) {
            return true;
        }
        return false;
    }
}
