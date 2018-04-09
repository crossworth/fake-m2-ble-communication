package com.baidu.mapapi.map;

import android.content.Context;
import android.os.Bundle;
import com.baidu.platform.comapi.map.C0486K;

class C0487d implements C0486K {
    final /* synthetic */ BaiduMap f1414a;

    C0487d(BaiduMap baiduMap) {
        this.f1414a = baiduMap;
    }

    public Bundle mo1791a(int i, int i2, int i3, Context context) {
        this.f1414a.f1004F.lock();
        try {
            if (this.f1414a.f1001C != null) {
                Tile a = this.f1414a.f1001C.m1214a(i, i2, i3);
                if (a != null) {
                    Bundle toBundle = a.toBundle();
                    return toBundle;
                }
            }
            this.f1414a.f1004F.unlock();
            return null;
        } finally {
            this.f1414a.f1004F.unlock();
        }
    }
}
