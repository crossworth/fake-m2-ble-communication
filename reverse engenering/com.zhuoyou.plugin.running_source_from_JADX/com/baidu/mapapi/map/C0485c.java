package com.baidu.mapapi.map;

import android.os.Bundle;
import com.baidu.platform.comapi.map.C0484p;

class C0485c implements C0484p {
    final /* synthetic */ BaiduMap f1413a;

    C0485c(BaiduMap baiduMap) {
        this.f1413a = baiduMap;
    }

    public Bundle mo1790a(int i, int i2, int i3) {
        this.f1413a.f1003E.lock();
        try {
            if (this.f1413a.f1002D != null) {
                Tile a = this.f1413a.f1002D.m1140a(i, i2, i3);
                if (a != null) {
                    Bundle toBundle = a.toBundle();
                    return toBundle;
                }
            }
            this.f1413a.f1003E.unlock();
            return null;
        } finally {
            this.f1413a.f1003E.unlock();
        }
    }
}
