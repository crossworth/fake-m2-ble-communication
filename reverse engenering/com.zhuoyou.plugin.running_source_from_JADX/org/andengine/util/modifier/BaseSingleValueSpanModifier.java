package org.andengine.util.modifier;

import org.andengine.util.modifier.IModifier.IModifierListener;
import org.andengine.util.modifier.ease.EaseLinear;
import org.andengine.util.modifier.ease.IEaseFunction;

public abstract class BaseSingleValueSpanModifier<T> extends BaseDurationModifier<T> {
    protected final IEaseFunction mEaseFunction;
    private float mFromValue;
    private float mValueSpan;

    protected abstract void onSetInitialValue(T t, float f);

    protected abstract void onSetValue(T t, float f, float f2);

    public BaseSingleValueSpanModifier(float pDuration, float pFromValue, float pToValue) {
        this(pDuration, pFromValue, pToValue, null, EaseLinear.getInstance());
    }

    public BaseSingleValueSpanModifier(float pDuration, float pFromValue, float pToValue, IEaseFunction pEaseFunction) {
        this(pDuration, pFromValue, pToValue, null, pEaseFunction);
    }

    public BaseSingleValueSpanModifier(float pDuration, float pFromValue, float pToValue, IModifierListener<T> pModifierListener) {
        this(pDuration, pFromValue, pToValue, pModifierListener, EaseLinear.getInstance());
    }

    public BaseSingleValueSpanModifier(float pDuration, float pFromValue, float pToValue, IModifierListener<T> pModifierListener, IEaseFunction pEaseFunction) {
        super(pDuration, pModifierListener);
        this.mFromValue = pFromValue;
        this.mValueSpan = pToValue - pFromValue;
        this.mEaseFunction = pEaseFunction;
    }

    protected BaseSingleValueSpanModifier(BaseSingleValueSpanModifier<T> pBaseSingleValueSpanModifier) {
        super((BaseDurationModifier) pBaseSingleValueSpanModifier);
        this.mFromValue = pBaseSingleValueSpanModifier.mFromValue;
        this.mValueSpan = pBaseSingleValueSpanModifier.mValueSpan;
        this.mEaseFunction = pBaseSingleValueSpanModifier.mEaseFunction;
    }

    public float getFromValue() {
        return this.mFromValue;
    }

    public float getToValue() {
        return this.mFromValue + this.mValueSpan;
    }

    protected void onManagedInitialize(T pItem) {
        onSetInitialValue(pItem, this.mFromValue);
    }

    protected void onManagedUpdate(float pSecondsElapsed, T pItem) {
        float percentageDone = this.mEaseFunction.getPercentage(getSecondsElapsed(), this.mDuration);
        onSetValue(pItem, percentageDone, this.mFromValue + (this.mValueSpan * percentageDone));
    }

    public void reset(float pDuration, float pFromValue, float pToValue) {
        super.reset();
        this.mDuration = pDuration;
        this.mFromValue = pFromValue;
        this.mValueSpan = pToValue - pFromValue;
    }
}
