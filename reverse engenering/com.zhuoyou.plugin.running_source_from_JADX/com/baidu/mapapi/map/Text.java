package com.baidu.mapapi.map;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import com.baidu.mapapi.model.CoordUtil;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.baidu.platform.comapi.map.C0636h;
import vi.com.gdi.bgl.android.java.EnvDrawText;

public final class Text extends Overlay {
    private static final String f1300k = Text.class.getSimpleName();
    String f1301a;
    LatLng f1302b;
    int f1303c;
    int f1304d;
    int f1305e;
    Typeface f1306f;
    int f1307g;
    int f1308h;
    float f1309i;
    int f1310j;

    Text() {
        this.q = C0636h.text;
    }

    Bundle mo1766a() {
        if (this.f1306f != null) {
            EnvDrawText.removeFontCache(this.f1306f.hashCode());
        }
        return super.mo1766a();
    }

    Bundle mo1759a(Bundle bundle) {
        float f = 0.5f;
        super.mo1759a(bundle);
        if (this.f1302b == null) {
            throw new IllegalStateException("when you add a text overlay, you must provide text and the position info.");
        }
        float f2;
        bundle.putString("text", this.f1301a);
        GeoPoint ll2mc = CoordUtil.ll2mc(this.f1302b);
        bundle.putDouble("location_x", ll2mc.getLongitudeE6());
        bundle.putDouble("location_y", ll2mc.getLatitudeE6());
        int i = (this.f1304d >> 8) & 255;
        bundle.putInt("font_color", Color.argb(this.f1304d >>> 24, this.f1304d & 255, i, (this.f1304d >> 16) & 255));
        i = (this.f1303c >> 8) & 255;
        bundle.putInt("bg_color", Color.argb(this.f1303c >>> 24, this.f1303c & 255, i, (this.f1303c >> 16) & 255));
        bundle.putInt("font_size", this.f1305e);
        if (this.f1306f != null) {
            EnvDrawText.registFontCache(this.f1306f.hashCode(), this.f1306f);
            bundle.putInt("type_face", this.f1306f.hashCode());
        }
        switch (this.f1307g) {
            case 1:
                f2 = 0.0f;
                break;
            case 2:
                f2 = 1.0f;
                break;
            case 4:
                f2 = 0.5f;
                break;
            default:
                f2 = 0.5f;
                break;
        }
        bundle.putFloat("align_x", f2);
        switch (this.f1308h) {
            case 8:
                f = 0.0f;
                break;
            case 16:
                f = 1.0f;
                break;
        }
        bundle.putFloat("align_y", f);
        bundle.putFloat("rotate", this.f1309i);
        bundle.putInt("update", this.f1310j);
        return bundle;
    }

    public float getAlignX() {
        return (float) this.f1307g;
    }

    public float getAlignY() {
        return (float) this.f1308h;
    }

    public int getBgColor() {
        return this.f1303c;
    }

    public int getFontColor() {
        return this.f1304d;
    }

    public int getFontSize() {
        return this.f1305e;
    }

    public LatLng getPosition() {
        return this.f1302b;
    }

    public float getRotate() {
        return this.f1309i;
    }

    public String getText() {
        return this.f1301a;
    }

    public Typeface getTypeface() {
        return this.f1306f;
    }

    public void setAlign(int i, int i2) {
        this.f1307g = i;
        this.f1308h = i2;
        this.f1310j = 1;
        this.listener.mo1769b(this);
    }

    public void setBgColor(int i) {
        this.f1303c = i;
        this.f1310j = 1;
        this.listener.mo1769b(this);
    }

    public void setFontColor(int i) {
        this.f1304d = i;
        this.f1310j = 1;
        this.listener.mo1769b(this);
    }

    public void setFontSize(int i) {
        this.f1305e = i;
        this.f1310j = 1;
        this.listener.mo1769b(this);
    }

    public void setPosition(LatLng latLng) {
        if (latLng == null) {
            throw new IllegalArgumentException("position can not be null");
        }
        this.f1302b = latLng;
        this.f1310j = 1;
        this.listener.mo1769b(this);
    }

    public void setRotate(float f) {
        this.f1309i = f;
        this.f1310j = 1;
        this.listener.mo1769b(this);
    }

    public void setText(String str) {
        if (str == null || str.equals("")) {
            throw new IllegalArgumentException("text can not be null or empty");
        }
        this.f1301a = str;
        this.f1310j = 1;
        this.listener.mo1769b(this);
    }

    public void setTypeface(Typeface typeface) {
        this.f1306f = typeface;
        this.f1310j = 1;
        this.listener.mo1769b(this);
    }
}
