package com.baidu.mapapi.map;

import android.util.Log;

class C0505t implements Runnable {
    final /* synthetic */ int f1446a;
    final /* synthetic */ int f1447b;
    final /* synthetic */ int f1448c;
    final /* synthetic */ String f1449d;
    final /* synthetic */ TileOverlay f1450e;

    C0505t(TileOverlay tileOverlay, int i, int i2, int i3, String str) {
        this.f1450e = tileOverlay;
        this.f1446a = i;
        this.f1447b = i2;
        this.f1448c = i3;
        this.f1449d = str;
    }

    public void run() {
        Tile tile = ((FileTileProvider) this.f1450e.f1360g).getTile(this.f1446a, this.f1447b, this.f1448c);
        if (tile == null) {
            Log.e(TileOverlay.f1354b, "FileTile pic is null");
        } else if (tile.width == 256 && tile.height == 256) {
            this.f1450e.m1209a(this.f1446a + "_" + this.f1447b + "_" + this.f1448c, tile);
        } else {
            Log.e(TileOverlay.f1354b, "FileTile pic must be 256 * 256");
        }
        this.f1450e.f1359e.remove(this.f1449d);
    }
}
