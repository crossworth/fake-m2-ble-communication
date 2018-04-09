package org.andengine.util.modifier.ease;

import android.util.FloatMath;
import org.andengine.util.math.MathConstants;

public class EaseSineIn implements IEaseFunction {
    private static EaseSineIn INSTANCE;

    private EaseSineIn() {
    }

    public static EaseSineIn getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EaseSineIn();
        }
        return INSTANCE;
    }

    public float getPercentage(float pSecondsElapsed, float pDuration) {
        return getValue(pSecondsElapsed / pDuration);
    }

    public static float getValue(float pPercentage) {
        return (-FloatMath.cos(MathConstants.PI_HALF * pPercentage)) + 1.0f;
    }
}
