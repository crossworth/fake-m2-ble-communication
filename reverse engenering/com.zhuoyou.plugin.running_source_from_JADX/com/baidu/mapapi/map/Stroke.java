package com.baidu.mapapi.map;

import android.os.Bundle;
import org.andengine.util.level.constants.LevelConstants;

public final class Stroke {
    public final int color;
    public final int strokeWidth;

    public Stroke(int i, int i2) {
        if (i <= 0) {
            i = 5;
        }
        this.strokeWidth = i;
        this.color = i2;
    }

    Bundle m1181a(Bundle bundle) {
        bundle.putInt(LevelConstants.TAG_LEVEL_ATTRIBUTE_WIDTH, this.strokeWidth);
        Overlay.m1057a(this.color, bundle);
        return bundle;
    }
}
