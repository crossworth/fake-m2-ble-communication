package com.baidu.mapapi.map;

class C0490g implements Runnable {
    final /* synthetic */ int f1421a;
    final /* synthetic */ int f1422b;
    final /* synthetic */ int f1423c;
    final /* synthetic */ HeatMap f1424d;

    C0490g(HeatMap heatMap, int i, int i2, int i3) {
        this.f1424d = heatMap;
        this.f1421a = i;
        this.f1422b = i2;
        this.f1423c = i3;
    }

    public void run() {
        this.f1424d.m1133b(this.f1421a, this.f1422b, this.f1423c);
    }
}
