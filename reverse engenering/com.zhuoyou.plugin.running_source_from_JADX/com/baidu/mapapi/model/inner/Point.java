package com.baidu.mapapi.model.inner;

import java.io.Serializable;

public class Point implements Serializable {
    public int f1465x;
    public int f1466y;

    public Point(int i, int i2) {
        this.f1465x = i;
        this.f1466y = i2;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Point point = (Point) obj;
        return this.f1465x != point.f1465x ? false : this.f1466y == point.f1466y;
    }

    public int getmPtx() {
        return this.f1465x;
    }

    public int getmPty() {
        return this.f1466y;
    }

    public int hashCode() {
        return ((this.f1465x + 31) * 31) + this.f1466y;
    }

    public void setmPtx(int i) {
        this.f1465x = i;
    }

    public void setmPty(int i) {
        this.f1466y = i;
    }

    public String toString() {
        return "Point [x=" + this.f1465x + ", y=" + this.f1466y + "]";
    }
}
