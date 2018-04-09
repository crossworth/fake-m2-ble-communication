package com.baidu.mapapi.map;

import android.os.Bundle;
import com.baidu.mapapi.model.CoordUtil;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.baidu.platform.comapi.map.C0636h;
import com.umeng.facebook.share.internal.ShareConstants;
import java.util.List;
import org.andengine.util.color.constants.ColorConstants;

public abstract class Overlay {
    protected C0477a listener;
    String f976p = (System.currentTimeMillis() + "_" + hashCode());
    C0636h f977q;
    int f978r;
    boolean f979s;
    Bundle f980t;

    interface C0477a {
        void mo1768a(Overlay overlay);

        void mo1769b(Overlay overlay);
    }

    protected Overlay() {
    }

    static void m1057a(int i, Bundle bundle) {
        Bundle bundle2 = new Bundle();
        int i2 = i >>> 24;
        int i3 = (i >> 8) & 255;
        int i4 = i & 255;
        bundle2.putFloat("red", ((float) ((i >> 16) & 255)) / ColorConstants.COLOR_FACTOR_INT_TO_FLOAT);
        bundle2.putFloat("green", ((float) i3) / ColorConstants.COLOR_FACTOR_INT_TO_FLOAT);
        bundle2.putFloat("blue", ((float) i4) / ColorConstants.COLOR_FACTOR_INT_TO_FLOAT);
        bundle2.putFloat("alpha", ((float) i2) / ColorConstants.COLOR_FACTOR_INT_TO_FLOAT);
        bundle.putBundle("color", bundle2);
    }

    static void m1058a(List<LatLng> list, Bundle bundle) {
        int size = list.size();
        double[] dArr = new double[size];
        double[] dArr2 = new double[size];
        for (int i = 0; i < size; i++) {
            GeoPoint ll2mc = CoordUtil.ll2mc((LatLng) list.get(i));
            dArr[i] = ll2mc.getLongitudeE6();
            dArr2[i] = ll2mc.getLatitudeE6();
        }
        bundle.putDoubleArray("x_array", dArr);
        bundle.putDoubleArray("y_array", dArr2);
    }

    Bundle mo1766a() {
        Bundle bundle = new Bundle();
        bundle.putString(ShareConstants.WEB_DIALOG_PARAM_ID, this.f976p);
        bundle.putInt("type", this.f977q.ordinal());
        return bundle;
    }

    Bundle mo1759a(Bundle bundle) {
        bundle.putString(ShareConstants.WEB_DIALOG_PARAM_ID, this.f976p);
        bundle.putInt("type", this.f977q.ordinal());
        bundle.putInt("visibility", this.f979s ? 1 : 0);
        bundle.putInt("z_index", this.f978r);
        return bundle;
    }

    public Bundle getExtraInfo() {
        return this.f980t;
    }

    public int getZIndex() {
        return this.f978r;
    }

    public boolean isVisible() {
        return this.f979s;
    }

    public void remove() {
        this.listener.mo1768a(this);
    }

    public void setExtraInfo(Bundle bundle) {
        this.f980t = bundle;
    }

    public void setVisible(boolean z) {
        this.f979s = z;
        this.listener.mo1769b(this);
    }

    public void setZIndex(int i) {
        this.f978r = i;
        this.listener.mo1769b(this);
    }
}
