package com.baidu.mapapi.map;

import android.graphics.Typeface;
import android.os.Bundle;
import com.baidu.mapapi.model.LatLng;

public final class TextOptions extends OverlayOptions {
    public static final int ALIGN_BOTTOM = 16;
    public static final int ALIGN_CENTER_HORIZONTAL = 4;
    public static final int ALIGN_CENTER_VERTICAL = 32;
    public static final int ALIGN_LEFT = 1;
    public static final int ALIGN_RIGHT = 2;
    public static final int ALIGN_TOP = 8;
    int f1311a;
    boolean f1312b = true;
    Bundle f1313c;
    private String f1314d;
    private LatLng f1315e;
    private int f1316f;
    private int f1317g = -16777216;
    private int f1318h = 12;
    private Typeface f1319i;
    private int f1320j = 4;
    private int f1321k = 32;
    private float f1322l;

    Overlay mo1760a() {
        Overlay text = new Text();
        text.s = this.f1312b;
        text.r = this.f1311a;
        text.t = this.f1313c;
        text.f1301a = this.f1314d;
        text.f1302b = this.f1315e;
        text.f1303c = this.f1316f;
        text.f1304d = this.f1317g;
        text.f1305e = this.f1318h;
        text.f1306f = this.f1319i;
        text.f1307g = this.f1320j;
        text.f1308h = this.f1321k;
        text.f1309i = this.f1322l;
        return text;
    }

    public TextOptions align(int i, int i2) {
        this.f1320j = i;
        this.f1321k = i2;
        return this;
    }

    public TextOptions bgColor(int i) {
        this.f1316f = i;
        return this;
    }

    public TextOptions extraInfo(Bundle bundle) {
        this.f1313c = bundle;
        return this;
    }

    public TextOptions fontColor(int i) {
        this.f1317g = i;
        return this;
    }

    public TextOptions fontSize(int i) {
        this.f1318h = i;
        return this;
    }

    public float getAlignX() {
        return (float) this.f1320j;
    }

    public float getAlignY() {
        return (float) this.f1321k;
    }

    public int getBgColor() {
        return this.f1316f;
    }

    public Bundle getExtraInfo() {
        return this.f1313c;
    }

    public int getFontColor() {
        return this.f1317g;
    }

    public int getFontSize() {
        return this.f1318h;
    }

    public LatLng getPosition() {
        return this.f1315e;
    }

    public float getRotate() {
        return this.f1322l;
    }

    public String getText() {
        return this.f1314d;
    }

    public Typeface getTypeface() {
        return this.f1319i;
    }

    public int getZIndex() {
        return this.f1311a;
    }

    public boolean isVisible() {
        return this.f1312b;
    }

    public TextOptions position(LatLng latLng) {
        if (latLng == null) {
            throw new IllegalArgumentException("position can not be null");
        }
        this.f1315e = latLng;
        return this;
    }

    public TextOptions rotate(float f) {
        this.f1322l = f;
        return this;
    }

    public TextOptions text(String str) {
        if (str == null || str.equals("")) {
            throw new IllegalArgumentException("text can not be null or empty");
        }
        this.f1314d = str;
        return this;
    }

    public TextOptions typeface(Typeface typeface) {
        this.f1319i = typeface;
        return this;
    }

    public TextOptions visible(boolean z) {
        this.f1312b = z;
        return this;
    }

    public TextOptions zIndex(int i) {
        this.f1311a = i;
        return this;
    }
}
