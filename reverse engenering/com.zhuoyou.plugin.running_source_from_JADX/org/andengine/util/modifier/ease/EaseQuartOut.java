package org.andengine.util.modifier.ease;

public class EaseQuartOut implements IEaseFunction {
    private static EaseQuartOut INSTANCE;

    private EaseQuartOut() {
    }

    public static EaseQuartOut getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EaseQuartOut();
        }
        return INSTANCE;
    }

    public float getPercentage(float pSecondsElapsed, float pDuration) {
        return getValue(pSecondsElapsed / pDuration);
    }

    public static float getValue(float pPercentage) {
        float t = pPercentage - 1.0f;
        return 1.0f - (((t * t) * t) * t);
    }
}
