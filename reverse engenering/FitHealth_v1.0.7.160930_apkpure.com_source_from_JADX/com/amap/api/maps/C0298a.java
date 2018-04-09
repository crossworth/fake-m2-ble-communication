package com.amap.api.maps;

import com.amap.api.maps.CoordinateConverter.CoordType;

/* compiled from: CoordinateConverter */
/* synthetic */ class C0298a {
    static final /* synthetic */ int[] f818a = new int[CoordType.values().length];

    static {
        try {
            f818a[CoordType.BAIDU.ordinal()] = 1;
        } catch (NoSuchFieldError e) {
        }
        try {
            f818a[CoordType.MAPBAR.ordinal()] = 2;
        } catch (NoSuchFieldError e2) {
        }
        try {
            f818a[CoordType.MAPABC.ordinal()] = 3;
        } catch (NoSuchFieldError e3) {
        }
        try {
            f818a[CoordType.SOSOMAP.ordinal()] = 4;
        } catch (NoSuchFieldError e4) {
        }
        try {
            f818a[CoordType.ALIYUN.ordinal()] = 5;
        } catch (NoSuchFieldError e5) {
        }
        try {
            f818a[CoordType.GOOGLE.ordinal()] = 6;
        } catch (NoSuchFieldError e6) {
        }
        try {
            f818a[CoordType.GPS.ordinal()] = 7;
        } catch (NoSuchFieldError e7) {
        }
    }
}
