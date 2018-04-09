package org.andengine.util.modifier;

import org.andengine.util.modifier.IModifier.IModifierListener;
import org.andengine.util.modifier.ease.EaseLinear;
import org.andengine.util.modifier.ease.IEaseFunction;

public abstract class BaseDoubleValueSpanModifier<T> extends BaseSingleValueSpanModifier<T> {
    private float mFromValueB;
    private float mValueSpanB;

    protected abstract void onSetInitialValues(T t, float f, float f2);

    protected abstract void onSetValues(T t, float f, float f2, float f3);

    public BaseDoubleValueSpanModifier(float pDuration, float pFromValueA, float pToValueA, float pFromValueB, float pToValueB) {
        this(pDuration, pFromValueA, pToValueA, pFromValueB, pToValueB, null, EaseLinear.getInstance());
    }

    public BaseDoubleValueSpanModifier(float pDuration, float pFromValueA, float pToValueA, float pFromValueB, float pToValueB, IEaseFunction pEaseFunction) {
        this(pDuration, pFromValueA, pToValueA, pFromValueB, pToValueB, null, pEaseFunction);
    }

    public BaseDoubleValueSpanModifier(float pDuration, float pFromValueA, float pToValueA, float pFromValueB, float pToValueB, IModifierListener<T> pModifierListener) {
        this(pDuration, pFromValueA, pToValueA, pFromValueB, pToValueB, pModifierListener, EaseLinear.getInstance());
    }

    public BaseDoubleValueSpanModifier(float pDuration, float pFromValueA, float pToValueA, float pFromValueB, float pToValueB, IModifierListener<T> pModifierListener, IEaseFunction pEaseFunction) {
        super(pDuration, pFromValueA, pToValueA, pModifierListener, pEaseFunction);
        this.mFromValueB = pFromValueB;
        this.mValueSpanB = pToValueB - pFromValueB;
    }

    protected BaseDoubleValueSpanModifier(BaseDoubleValueSpanModifier<T> pBaseDoubleValueSpanModifier) {
        super(pBaseDoubleValueSpanModifier);
        this.mFromValueB = pBaseDoubleValueSpanModifier.mFromValueB;
        this.mValueSpanB = pBaseDoubleValueSpanModifier.mValueSpanB;
    }

    @Deprecated
    public float getFromValue() {
        return super.getFromValue();
    }

    @Deprecated
    public float getToValue() {
        return super.getToValue();
    }

    public float getFromValueA() {
        return super.getFromValue();
    }

    public float getToValueA() {
        return super.getToValue();
    }

    public float getFromValueB() {
        return this.mFromValueB;
    }

    public float getToValueB() {
        return this.mFromValueB + this.mValueSpanB;
    }

    protected void onSetInitialValue(T pItem, float pValueA) {
        onSetInitialValues(pItem, pValueA, this.mFromValueB);
    }

    protected void onSetValue(T pItem, float pPercentageDone, float pValueA) {
        onSetValues(pItem, pPercentageDone, pValueA, this.mFromValueB + (this.mValueSpanB * pPercentageDone));
    }

    @Deprecated
    public void reset(float pDuration, float pFromValue, float pToValue) {
        super.reset(pDuration, pFromValue, pToValue);
    }

    public void reset(float pDuration, float pFromValueA, float pToValueA, float pFromValueB, float pToValueB) {
        super.reset(pDuration, pFromValueA, pToValueA);
        this.mFromValueB = pFromValueB;
        this.mValueSpanB = pToValueB - pFromValueB;
    }
}
