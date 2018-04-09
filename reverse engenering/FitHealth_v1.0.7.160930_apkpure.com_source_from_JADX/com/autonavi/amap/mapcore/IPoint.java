package com.autonavi.amap.mapcore;

public class IPoint {
    public int f2030x;
    public int f2031y;

    public IPoint(int i, int i2) {
        this.f2030x = i;
        this.f2031y = i2;
    }

    public Object clone() {
        try {
            return (IPoint) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
