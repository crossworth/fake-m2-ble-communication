package org.andengine.util.modifier.ease;

import android.util.FloatMath;
import com.tencent.open.yyb.TitleBar;
import org.andengine.util.math.MathConstants;

public class EaseElasticIn implements IEaseFunction {
    private static EaseElasticIn INSTANCE;

    private EaseElasticIn() {
    }

    public static EaseElasticIn getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EaseElasticIn();
        }
        return INSTANCE;
    }

    public float getPercentage(float pSecondsElapsed, float pDuration) {
        return getValue(pSecondsElapsed, pDuration, pSecondsElapsed / pDuration);
    }

    public static float getValue(float pSecondsElapsed, float pDuration, float pPercentage) {
        if (pSecondsElapsed == 0.0f) {
            return 0.0f;
        }
        if (pSecondsElapsed == pDuration) {
            return 1.0f;
        }
        float p = pDuration * 0.3f;
        float t = pPercentage - 1.0f;
        return (-((float) Math.pow(2.0d, (double) (TitleBar.SHAREBTN_RIGHT_MARGIN * t)))) * FloatMath.sin((((t * pDuration) - (p / 4.0f)) * MathConstants.PI_TWICE) / p);
    }
}
