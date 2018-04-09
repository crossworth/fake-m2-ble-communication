package com.amap.api.mapcore.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.amap.api.mapcore.util.av.C0199a;
import com.amap.api.maps.model.Tile;
import com.amap.api.maps.model.TileProvider;

/* compiled from: ImageFetcher */
public class db extends dc {
    private TileProvider f5357e = null;

    public db(Context context, int i, int i2) {
        super(context, i, i2);
        m5759a(context);
    }

    private void m5759a(Context context) {
        m5760b(context);
    }

    public void m5763a(TileProvider tileProvider) {
        this.f5357e = tileProvider;
    }

    private void m5760b(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting()) {
        }
    }

    private Bitmap m5761c(C0199a c0199a) {
        Bitmap bitmap = null;
        try {
            Tile tile = this.f5357e.getTile(c0199a.f188a, c0199a.f189b, c0199a.f190c);
            if (!(tile == null || tile == TileProvider.NO_TILE)) {
                bitmap = BitmapFactory.decodeByteArray(tile.data, 0, tile.data.length);
            }
        } catch (Throwable th) {
        }
        return bitmap;
    }

    protected Bitmap mo1640a(Object obj) {
        return m5761c((C0199a) obj);
    }
}
