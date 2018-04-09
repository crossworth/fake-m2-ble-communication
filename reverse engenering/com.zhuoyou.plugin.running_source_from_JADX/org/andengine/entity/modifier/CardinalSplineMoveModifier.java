package org.andengine.entity.modifier;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.util.adt.array.ArrayUtils;
import org.andengine.util.math.MathUtils;
import org.andengine.util.modifier.ease.EaseLinear;
import org.andengine.util.modifier.ease.IEaseFunction;

public class CardinalSplineMoveModifier extends DurationEntityModifier {
    private final CardinalSplineMoveModifierConfig mCardinalSplineMoveModifierConfig;
    private final int mControlSegmentCount;
    private final float mControlSegmentCountInverse;
    private final IEaseFunction mEaseFunction;

    public static class CardinalSplineMoveModifierConfig {
        private static final int CONTROLPOINT_COUNT_MINIMUM = 4;
        private final float[] mControlPointXs;
        private final float[] mControlPointYs;
        final float mTension;

        public CardinalSplineMoveModifierConfig(int pControlPointCount, float pTension) {
            if (pControlPointCount < 4) {
                throw new IllegalArgumentException("A " + CardinalSplineMoveModifierConfig.class.getSimpleName() + " needs at least " + 4 + " control points.");
            }
            this.mTension = pTension;
            this.mControlPointXs = new float[pControlPointCount];
            this.mControlPointYs = new float[pControlPointCount];
        }

        public CardinalSplineMoveModifierConfig deepCopy() {
            int controlPointCount = getControlPointCount();
            CardinalSplineMoveModifierConfig copy = new CardinalSplineMoveModifierConfig(controlPointCount, this.mTension);
            System.arraycopy(this.mControlPointXs, 0, copy.mControlPointXs, 0, controlPointCount);
            System.arraycopy(this.mControlPointYs, 0, copy.mControlPointYs, 0, controlPointCount);
            return copy;
        }

        public CardinalSplineMoveModifierConfig deepCopyReverse() {
            CardinalSplineMoveModifierConfig copy = deepCopy();
            ArrayUtils.reverse(copy.mControlPointXs);
            ArrayUtils.reverse(copy.mControlPointYs);
            return copy;
        }

        public int getControlPointCount() {
            return this.mControlPointXs.length;
        }

        public void setControlPoint(int pIndex, float pX, float pY) {
            this.mControlPointXs[pIndex] = pX;
            this.mControlPointYs[pIndex] = pY;
        }

        public float getControlPointX(int pIndex) {
            return this.mControlPointXs[pIndex];
        }

        public float getControlPointY(int pIndex) {
            return this.mControlPointYs[pIndex];
        }
    }

    public CardinalSplineMoveModifier(float pDuration, CardinalSplineMoveModifierConfig pCardinalSplineMoveModifierConfig) {
        this(pDuration, pCardinalSplineMoveModifierConfig, null, EaseLinear.getInstance());
    }

    public CardinalSplineMoveModifier(float pDuration, CardinalSplineMoveModifierConfig pCardinalSplineMoveModifierConfig, IEaseFunction pEaseFunction) {
        this(pDuration, pCardinalSplineMoveModifierConfig, null, pEaseFunction);
    }

    public CardinalSplineMoveModifier(float pDuration, CardinalSplineMoveModifierConfig pCardinalSplineMoveModifierConfig, IEntityModifierListener pEntityModifierListener) {
        this(pDuration, pCardinalSplineMoveModifierConfig, pEntityModifierListener, EaseLinear.getInstance());
    }

    public CardinalSplineMoveModifier(float pDuration, CardinalSplineMoveModifierConfig pCardinalSplineMoveModifierConfig, IEntityModifierListener pEntityModifierListener, IEaseFunction pEaseFunction) {
        super(pDuration, pEntityModifierListener);
        this.mCardinalSplineMoveModifierConfig = pCardinalSplineMoveModifierConfig;
        this.mEaseFunction = pEaseFunction;
        this.mControlSegmentCount = pCardinalSplineMoveModifierConfig.getControlPointCount() - 1;
        this.mControlSegmentCountInverse = 1.0f / ((float) this.mControlSegmentCount);
    }

    public CardinalSplineMoveModifier deepCopy() {
        return new CardinalSplineMoveModifier(this.mDuration, this.mCardinalSplineMoveModifierConfig.deepCopy(), this.mEaseFunction);
    }

    public CardinalSplineMoveModifier reverse() {
        return new CardinalSplineMoveModifier(this.mDuration, this.mCardinalSplineMoveModifierConfig.deepCopyReverse(), this.mEaseFunction);
    }

    protected void onManagedInitialize(IEntity pEntity) {
    }

    protected void onManagedUpdate(float pSecondsElapsed, IEntity pEntity) {
        int p;
        float percentageDone = this.mEaseFunction.getPercentage(getSecondsElapsed(), this.mDuration);
        if (percentageDone == 1.0f) {
            p = this.mControlSegmentCount;
        } else {
            p = (int) (percentageDone / this.mControlSegmentCountInverse);
        }
        int p0 = MathUtils.bringToBounds(0, this.mControlSegmentCount, p - 1);
        float pX0 = this.mCardinalSplineMoveModifierConfig.mControlPointXs[p0];
        float pY0 = this.mCardinalSplineMoveModifierConfig.mControlPointYs[p0];
        int p1 = MathUtils.bringToBounds(0, this.mControlSegmentCount, p);
        float pX1 = this.mCardinalSplineMoveModifierConfig.mControlPointXs[p1];
        float pY1 = this.mCardinalSplineMoveModifierConfig.mControlPointYs[p1];
        int p2 = MathUtils.bringToBounds(0, this.mControlSegmentCount, p + 1);
        float pX2 = this.mCardinalSplineMoveModifierConfig.mControlPointXs[p2];
        float pY2 = this.mCardinalSplineMoveModifierConfig.mControlPointYs[p2];
        int p3 = MathUtils.bringToBounds(0, this.mControlSegmentCount, p + 2);
        float pX3 = this.mCardinalSplineMoveModifierConfig.mControlPointXs[p3];
        float t = (percentageDone - (((float) p) * this.mControlSegmentCountInverse)) / this.mControlSegmentCountInverse;
        float tt = t * t;
        float ttt = tt * t;
        float s = (1.0f - this.mCardinalSplineMoveModifierConfig.mTension) / 2.0f;
        float b1 = s * (((-ttt) + (2.0f * tt)) - t);
        float b2 = (((-ttt) + tt) * s) + (((2.0f * ttt) - (3.0f * tt)) + 1.0f);
        float b3 = (((ttt - (2.0f * tt)) + t) * s) + ((-2.0f * ttt) + (3.0f * tt));
        float b4 = s * (ttt - tt);
        float x = (((pX0 * b1) + (pX1 * b2)) + (pX2 * b3)) + (pX3 * b4);
        pEntity.setPosition(x, (((pY0 * b1) + (pY1 * b2)) + (pY2 * b3)) + (this.mCardinalSplineMoveModifierConfig.mControlPointYs[p3] * b4));
    }

    public static final float cardinalSplineX(float pX0, float pX1, float pX2, float pX3, float pT, float pTension) {
        float t = pT;
        float tt = t * t;
        float ttt = tt * t;
        float s = (1.0f - pTension) / 2.0f;
        return (((pX0 * (s * (((-ttt) + (2.0f * tt)) - t))) + (pX1 * ((((-ttt) + tt) * s) + (((2.0f * ttt) - (3.0f * tt)) + 1.0f)))) + (pX2 * ((((ttt - (2.0f * tt)) + t) * s) + ((-2.0f * ttt) + (3.0f * tt))))) + (pX3 * (s * (ttt - tt)));
    }

    public static final float cardinalSplineY(float pY0, float pY1, float pY2, float pY3, float pT, float pTension) {
        float t = pT;
        float tt = t * t;
        float ttt = tt * t;
        float s = (1.0f - pTension) / 2.0f;
        return (((pY0 * (s * (((-ttt) + (2.0f * tt)) - t))) + (pY1 * ((((-ttt) + tt) * s) + (((2.0f * ttt) - (3.0f * tt)) + 1.0f)))) + (pY2 * ((((ttt - (2.0f * tt)) + t) * s) + ((-2.0f * ttt) + (3.0f * tt))))) + (pY3 * (s * (ttt - tt)));
    }
}
