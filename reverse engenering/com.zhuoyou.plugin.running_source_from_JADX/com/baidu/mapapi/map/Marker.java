package com.baidu.mapapi.map;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import com.baidu.mapapi.model.CoordUtil;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.ParcelItem;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.baidu.platform.comapi.map.C0636h;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;

public final class Marker extends Overlay {
    LatLng f1207a;
    BitmapDescriptor f1208b;
    float f1209c;
    float f1210d;
    boolean f1211e;
    boolean f1212f;
    float f1213g;
    String f1214h;
    int f1215i;
    boolean f1216j;
    boolean f1217k;
    float f1218l;
    int f1219m;
    ArrayList<BitmapDescriptor> f1220n;
    int f1221o;

    Marker() {
        this.f1216j = false;
        this.f1217k = false;
        this.f1221o = 20;
        this.q = C0636h.marker;
    }

    private void m1166a(ArrayList<BitmapDescriptor> arrayList, Bundle bundle) {
        int i = 0;
        ArrayList arrayList2 = new ArrayList();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            BitmapDescriptor bitmapDescriptor = (BitmapDescriptor) it.next();
            ParcelItem parcelItem = new ParcelItem();
            Bundle bundle2 = new Bundle();
            Bitmap bitmap = bitmapDescriptor.f1053a;
            Buffer allocate = ByteBuffer.allocate((bitmap.getWidth() * bitmap.getHeight()) * 4);
            bitmap.copyPixelsToBuffer(allocate);
            byte[] array = allocate.array();
            bundle2.putByteArray("image_data", array);
            bundle2.putInt("image_width", bitmap.getWidth());
            bundle2.putInt("image_height", bitmap.getHeight());
            MessageDigest messageDigest = null;
            try {
                messageDigest = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            messageDigest.update(array, 0, array.length);
            byte[] digest = messageDigest.digest();
            StringBuilder stringBuilder = new StringBuilder("");
            for (byte b : digest) {
                stringBuilder.append(Integer.toString((b & 255) + 256, 16).substring(1));
            }
            bundle2.putString("image_hashcode", stringBuilder.toString());
            parcelItem.setBundle(bundle2);
            arrayList2.add(parcelItem);
        }
        if (arrayList2.size() > 0) {
            Parcelable[] parcelableArr = new ParcelItem[arrayList2.size()];
            while (i < arrayList2.size()) {
                parcelableArr[i] = (ParcelItem) arrayList2.get(i);
                i++;
            }
            bundle.putParcelableArray("icons", parcelableArr);
        }
    }

    Bundle mo1759a(Bundle bundle) {
        int i = 1;
        super.mo1759a(bundle);
        Bundle bundle2 = new Bundle();
        if (this.f1208b != null) {
            bundle.putBundle("image_info", this.f1208b.m1105b());
        }
        GeoPoint ll2mc = CoordUtil.ll2mc(this.f1207a);
        bundle.putInt("animatetype", this.f1219m);
        bundle.putDouble("location_x", ll2mc.getLongitudeE6());
        bundle.putDouble("location_y", ll2mc.getLatitudeE6());
        bundle.putInt("perspective", this.f1211e ? 1 : 0);
        bundle.putFloat("anchor_x", this.f1209c);
        bundle.putFloat("anchor_y", this.f1210d);
        bundle.putFloat("rotate", this.f1213g);
        bundle.putInt("y_offset", this.f1215i);
        bundle.putInt("isflat", this.f1216j ? 1 : 0);
        String str = "istop";
        if (!this.f1217k) {
            i = 0;
        }
        bundle.putInt(str, i);
        bundle.putInt("period", this.f1221o);
        bundle.putFloat("alpha", this.f1218l);
        if (this.f1220n != null && this.f1220n.size() > 0) {
            m1166a(this.f1220n, bundle);
        }
        bundle2.putBundle("param", bundle);
        return bundle;
    }

    public float getAlpha() {
        return this.f1218l;
    }

    public float getAnchorX() {
        return this.f1209c;
    }

    public float getAnchorY() {
        return this.f1210d;
    }

    public BitmapDescriptor getIcon() {
        return this.f1208b;
    }

    public ArrayList<BitmapDescriptor> getIcons() {
        return this.f1220n;
    }

    public int getPeriod() {
        return this.f1221o;
    }

    public LatLng getPosition() {
        return this.f1207a;
    }

    public float getRotate() {
        return this.f1213g;
    }

    public String getTitle() {
        return this.f1214h;
    }

    public boolean isDraggable() {
        return this.f1212f;
    }

    public boolean isFlat() {
        return this.f1216j;
    }

    public boolean isPerspective() {
        return this.f1211e;
    }

    public void setAlpha(float f) {
        if (f < 0.0f || ((double) f) > WeightedLatLng.DEFAULT_INTENSITY) {
            this.f1218l = 1.0f;
            return;
        }
        this.f1218l = f;
        this.listener.mo1769b(this);
    }

    public void setAnchor(float f, float f2) {
        if (f >= 0.0f && f <= 1.0f && f2 >= 0.0f && f2 <= 1.0f) {
            this.f1209c = f;
            this.f1210d = f2;
            this.listener.mo1769b(this);
        }
    }

    public void setDraggable(boolean z) {
        this.f1212f = z;
        this.listener.mo1769b(this);
    }

    public void setFlat(boolean z) {
        this.f1216j = z;
        this.listener.mo1769b(this);
    }

    public void setIcon(BitmapDescriptor bitmapDescriptor) {
        if (bitmapDescriptor == null) {
            throw new IllegalArgumentException("marker's icon can not be null");
        }
        this.f1208b = bitmapDescriptor;
        this.listener.mo1769b(this);
    }

    public void setIcons(ArrayList<BitmapDescriptor> arrayList) {
        if (arrayList == null) {
            throw new IllegalArgumentException("marker's icons can not be null");
        } else if (arrayList.size() != 0) {
            int i = 0;
            while (i < arrayList.size()) {
                if (arrayList.get(i) != null && ((BitmapDescriptor) arrayList.get(i)).f1053a != null) {
                    i++;
                } else {
                    return;
                }
            }
            this.f1220n = arrayList;
            this.listener.mo1769b(this);
        }
    }

    public void setPeriod(int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("marker's period must be greater than zero ");
        }
        this.f1221o = i;
        this.listener.mo1769b(this);
    }

    public void setPerspective(boolean z) {
        this.f1211e = z;
        this.listener.mo1769b(this);
    }

    public void setPosition(LatLng latLng) {
        if (latLng == null) {
            throw new IllegalArgumentException("marker's position can not be null");
        }
        this.f1207a = latLng;
        this.listener.mo1769b(this);
    }

    public void setRotate(float f) {
        while (f < 0.0f) {
            f += 360.0f;
        }
        this.f1213g = f % 360.0f;
        this.listener.mo1769b(this);
    }

    public void setTitle(String str) {
        this.f1214h = str;
    }

    public void setToTop() {
        this.f1217k = true;
        this.listener.mo1769b(this);
    }
}
