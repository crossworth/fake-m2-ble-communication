package com.baidu.mapapi.map;

public class MyLocationData {
    public final float accuracy;
    public final float direction;
    public final double latitude;
    public final double longitude;
    public final int satellitesNum;
    public final float speed;

    public static class Builder {
        private double f1241a;
        private double f1242b;
        private float f1243c;
        private float f1244d;
        private float f1245e;
        private int f1246f;

        public Builder accuracy(float f) {
            this.f1245e = f;
            return this;
        }

        public MyLocationData build() {
            return new MyLocationData(this.f1241a, this.f1242b, this.f1243c, this.f1244d, this.f1245e, this.f1246f);
        }

        public Builder direction(float f) {
            this.f1244d = f;
            return this;
        }

        public Builder latitude(double d) {
            this.f1241a = d;
            return this;
        }

        public Builder longitude(double d) {
            this.f1242b = d;
            return this;
        }

        public Builder satellitesNum(int i) {
            this.f1246f = i;
            return this;
        }

        public Builder speed(float f) {
            this.f1243c = f;
            return this;
        }
    }

    MyLocationData(double d, double d2, float f, float f2, float f3, int i) {
        this.latitude = d;
        this.longitude = d2;
        this.speed = f;
        this.direction = f2;
        this.accuracy = f3;
        this.satellitesNum = i;
    }
}
