package org.andengine.entity.modifier;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.util.modifier.ease.EaseLinear;
import org.andengine.util.modifier.ease.IEaseFunction;

public class CubicBezierCurveMoveModifier extends DurationEntityModifier {
    private final IEaseFunction mEaseFunction;
    private final float mX1;
    private final float mX2;
    private final float mX3;
    private final float mX4;
    private final float mY1;
    private final float mY2;
    private final float mY3;
    private final float mY4;

    public CubicBezierCurveMoveModifier(float pDuration, float pX1, float pY1, float pX2, float pY2, float pX3, float pY3, float pX4, float pY4) {
        this(pDuration, pX1, pY1, pX2, pY2, pX3, pY3, pX4, pY4, null, EaseLinear.getInstance());
    }

    public CubicBezierCurveMoveModifier(float pDuration, float pX1, float pY1, float pX2, float pY2, float pX3, float pY3, float pX4, float pY4, IEaseFunction pEaseFunction) {
        this(pDuration, pX1, pY1, pX2, pY2, pX3, pY3, pX4, pY4, null, pEaseFunction);
    }

    public CubicBezierCurveMoveModifier(float pDuration, float pX1, float pY1, float pX2, float pY2, float pX3, float pY3, float pX4, float pY4, IEntityModifierListener pEntityModifierListener) {
        this(pDuration, pX1, pY1, pX2, pY2, pX3, pY3, pX4, pY4, pEntityModifierListener, EaseLinear.getInstance());
    }

    public CubicBezierCurveMoveModifier(float pDuration, float pX1, float pY1, float pX2, float pY2, float pX3, float pY3, float pX4, float pY4, IEntityModifierListener pEntityModifierListener, IEaseFunction pEaseFunction) {
        super(pDuration, pEntityModifierListener);
        this.mX1 = pX1;
        this.mY1 = pY1;
        this.mX2 = pX2;
        this.mY2 = pY2;
        this.mX3 = pX3;
        this.mY3 = pY3;
        this.mX4 = pX4;
        this.mY4 = pY4;
        this.mEaseFunction = pEaseFunction;
    }

    public CubicBezierCurveMoveModifier deepCopy() {
        return new CubicBezierCurveMoveModifier(this.mDuration, this.mX1, this.mY1, this.mX2, this.mY2, this.mX3, this.mY3, this.mX4, this.mY4, this.mEaseFunction);
    }

    protected void onManagedInitialize(IEntity pEntity) {
    }

    protected void onManagedUpdate(float pSecondsElapsed, IEntity pEntity) {
        float percentageDone = this.mEaseFunction.getPercentage(getSecondsElapsed(), this.mDuration);
        float u = 1.0f - percentageDone;
        float tt = percentageDone * percentageDone;
        float uu = u * u;
        float uuu = uu * u;
        float ttt = tt * percentageDone;
        float ut3 = (3.0f * uu) * percentageDone;
        float utt3 = (3.0f * u) * tt;
        pEntity.setPosition((((this.mX1 * uuu) + (this.mX2 * ut3)) + (this.mX3 * utt3)) + (this.mX4 * ttt), (((this.mY1 * uuu) + (this.mY2 * ut3)) + (this.mY3 * utt3)) + (this.mY4 * ttt));
    }
}
