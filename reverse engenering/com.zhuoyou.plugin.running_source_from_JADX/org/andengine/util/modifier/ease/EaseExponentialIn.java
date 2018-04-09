package org.andengine.util.modifier.ease;

import com.tencent.open.yyb.TitleBar;

public class EaseExponentialIn implements IEaseFunction {
    private static EaseExponentialIn INSTANCE;

    private EaseExponentialIn() {
    }

    public static EaseExponentialIn getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EaseExponentialIn();
        }
        return INSTANCE;
    }

    public float getPercentage(float pSecondsElapsed, float pDuration) {
        return getValue(pSecondsElapsed / pDuration);
    }

    public static float getValue(float pPercentage) {
        return (float) (pPercentage == 0.0f ? 0.0d : Math.pow(2.0d, (double) (TitleBar.SHAREBTN_RIGHT_MARGIN * (pPercentage - 1.0f))) - 0.0010000000474974513d);
    }
}
