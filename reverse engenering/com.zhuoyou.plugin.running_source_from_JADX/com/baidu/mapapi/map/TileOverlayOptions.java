package com.baidu.mapapi.map;

import android.os.Bundle;
import android.util.Log;
import com.baidu.mapapi.model.CoordUtil;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.model.inner.GeoPoint;

public final class TileOverlayOptions {
    private static Bundle f1361c;
    private static final String f1362j = TileOverlayOptions.class.getSimpleName();
    private int f1363a = 20971520;
    private TileProvider f1364b;
    private int f1365d = 20;
    public int datasource;
    private int f1366e = 3;
    private int f1367f = 15786414;
    private int f1368g = -20037726;
    private int f1369h = -15786414;
    private int f1370i = 20037726;
    public String urlString;

    public TileOverlayOptions() {
        f1361c = new Bundle();
        f1361c.putInt("rectr", this.f1367f);
        f1361c.putInt("rectb", this.f1368g);
        f1361c.putInt("rectl", this.f1369h);
        f1361c.putInt("rectt", this.f1370i);
    }

    private TileOverlayOptions m1217a(int i, int i2) {
        this.f1365d = i;
        this.f1366e = i2;
        return this;
    }

    Bundle m1218a() {
        f1361c.putString("url", this.urlString);
        f1361c.putInt("datasource", this.datasource);
        f1361c.putInt("maxDisplay", this.f1365d);
        f1361c.putInt("minDisplay", this.f1366e);
        f1361c.putInt("sdktiletmpmax", this.f1363a);
        return f1361c;
    }

    TileOverlay m1219a(BaiduMap baiduMap) {
        return new TileOverlay(baiduMap, this.f1364b);
    }

    public TileOverlayOptions setMaxTileTmp(int i) {
        this.f1363a = i;
        return this;
    }

    public TileOverlayOptions setPositionFromBounds(LatLngBounds latLngBounds) {
        if (latLngBounds == null) {
            throw new IllegalArgumentException("bound can not be null");
        }
        GeoPoint ll2mc = CoordUtil.ll2mc(latLngBounds.northeast);
        GeoPoint ll2mc2 = CoordUtil.ll2mc(latLngBounds.southwest);
        double latitudeE6 = ll2mc.getLatitudeE6();
        double longitudeE6 = ll2mc2.getLongitudeE6();
        double latitudeE62 = ll2mc2.getLatitudeE6();
        double longitudeE62 = ll2mc.getLongitudeE6();
        if (latitudeE6 <= latitudeE62 || longitudeE62 <= longitudeE6) {
            Log.e(f1362j, "bounds is illegal, use default bounds");
        } else {
            f1361c.putInt("rectr", (int) longitudeE62);
            f1361c.putInt("rectb", (int) latitudeE62);
            f1361c.putInt("rectl", (int) longitudeE6);
            f1361c.putInt("rectt", (int) latitudeE6);
        }
        return this;
    }

    public TileOverlayOptions tileProvider(TileProvider tileProvider) {
        if (tileProvider == null) {
            return null;
        }
        if (tileProvider instanceof UrlTileProvider) {
            this.datasource = 1;
            String tileUrl = ((UrlTileProvider) tileProvider).getTileUrl();
            if (tileUrl != null && !"".equals(tileUrl) && tileUrl.contains("{x}") && tileUrl.contains("{y}") && tileUrl.contains("{z}")) {
                this.urlString = tileUrl;
            } else {
                Log.e(f1362j, "tile url template is illegal, must contains {x}、{y}、{z}");
                return null;
            }
        } else if (tileProvider instanceof FileTileProvider) {
            this.datasource = 0;
        } else {
            Log.e(f1362j, "tileProvider must be UrlTileProvider or FileTileProvider");
            return null;
        }
        this.f1364b = tileProvider;
        int maxDisLevel = tileProvider.getMaxDisLevel();
        int minDisLevel = tileProvider.getMinDisLevel();
        if (maxDisLevel > 21 || minDisLevel < 3) {
            Log.e(f1362j, "display level is illegal");
            return this;
        }
        m1217a(maxDisLevel, minDisLevel);
        return this;
    }
}
