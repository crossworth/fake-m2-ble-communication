package com.baidu.mapapi.map;

import android.util.Log;
import com.baidu.mapapi.common.Logger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

public final class TileOverlay {
    private static final String f1354b = TileOverlay.class.getSimpleName();
    private static int f1355f = 0;
    BaiduMap f1356a;
    private ExecutorService f1357c = Executors.newFixedThreadPool(1);
    private HashMap<String, Tile> f1358d = new HashMap();
    private HashSet<String> f1359e = new HashSet();
    private TileProvider f1360g;

    public TileOverlay(BaiduMap baiduMap, TileProvider tileProvider) {
        this.f1356a = baiduMap;
        this.f1360g = tileProvider;
    }

    private synchronized Tile m1206a(String str) {
        Tile tile;
        if (this.f1358d.containsKey(str)) {
            tile = (Tile) this.f1358d.get(str);
            this.f1358d.remove(str);
        } else {
            tile = null;
        }
        return tile;
    }

    private synchronized void m1209a(String str, Tile tile) {
        this.f1358d.put(str, tile);
    }

    private synchronized boolean m1211b(String str) {
        return this.f1359e.contains(str);
    }

    private synchronized void m1213c(String str) {
        this.f1359e.add(str);
    }

    Tile m1214a(int i, int i2, int i3) {
        String str = i + "_" + i2 + "_" + i3;
        Tile a = m1206a(str);
        if (a != null) {
            return a;
        }
        if (this.f1356a != null && f1355f == 0) {
            MapStatus mapStatus = this.f1356a.getMapStatus();
            f1355f = (((mapStatus.f1153a.f1972j.f1960d - mapStatus.f1153a.f1972j.f1959c) / 256) + 2) * (((mapStatus.f1153a.f1972j.f1958b - mapStatus.f1153a.f1972j.f1957a) / 256) + 2);
        }
        if (this.f1358d.size() > f1355f) {
            m1215a();
        }
        if (!(m1211b(str) || this.f1357c.isShutdown())) {
            try {
                m1213c(str);
                this.f1357c.execute(new C0505t(this, i, i2, i3, str));
            } catch (RejectedExecutionException e) {
                Log.e(f1354b, "ThreadPool excepiton");
            } catch (Exception e2) {
                Log.e(f1354b, "fileDir is not legal");
            }
        }
        return null;
    }

    synchronized void m1215a() {
        Logger.logE(f1354b, "clearTaskSet");
        this.f1359e.clear();
        this.f1358d.clear();
    }

    void m1216b() {
        this.f1357c.shutdownNow();
    }

    public boolean clearTileCache() {
        return this.f1356a.m1101b();
    }

    public void removeTileOverlay() {
        if (this.f1356a != null) {
            this.f1356a.m1100a(this);
        }
    }
}
