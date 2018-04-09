package org.andengine.util.modifier;

import org.andengine.util.modifier.IModifier.IModifierListener;
import org.andengine.util.modifier.ease.IEaseFunction;

public abstract class BaseTripleValueSpanModifier<T> extends BaseDoubleValueSpanModifier<T> {
    private float mFromValueC;
    private float mValueSpanC;

    protected abstract void onSetInitialValues(T t, float f, float f2, float f3);

    protected abstract void onSetValues(T t, float f, float f2, float f3, float f4);

    public BaseTripleValueSpanModifier(float pDuration, float pFromValueA, float pToValueA, float pFromValueB, float pToValueB, float pFromValueC, float pToValueC, IEaseFunction pEaseFunction) {
        this(pDuration, pFromValueA, pToValueA, pFromValueB, pToValueB, pFromValueC, pToValueC, null, pEaseFunction);
    }

    public BaseTripleValueSpanModifier(float pDuration, float pFromValueA, float pToValueA, float pFromValueB, float pToValueB, float pFromValueC, float pToValueC, IModifierListener<T> pModifierListener, IEaseFunction pEaseFunction) {
        super(pDuration, pFromValueA, pToValueA, pFromValueB, pToValueB, pModifierListener, pEaseFunction);
        this.mFromValueC = pFromValueC;
        this.mValueSpanC = pToValueC - pFromValueC;
    }

    protected BaseTripleValueSpanModifier(BaseTripleValueSpanModifier<T> pBaseTripleValueSpanModifier) {
        super(pBaseTripleValueSpanModifier);
        this.mFromValueC = pBaseTripleValueSpanModifier.mFromValueC;
        this.mValueSpanC = pBaseTripleValueSpanModifier.mValueSpanC;
    }

    public float getFromValueC() {
        return this.mFromValueC;
    }

    public float getToValueC() {
        return this.mFromValueC + this.mValueSpanC;
    }

    protected void onSetInitialValues(T pItem, float pValueA, float pValueB) {
        onSetInitialValues(pItem, pValueA, pValueB, this.mFromValueC);
    }

    protected void onSetValues(T pItem, float pPercentageDone, float pValueA, float pValueB) {
        onSetValues(pItem, pPercentageDone, pValueA, pValueB, this.mFromValueC + (this.mValueSpanC * pPercentageDone));
    }

    @Deprecated
    public void reset(float pDuration, float pFromValueA, float pToValueA, float pFromValueB, float pToValueB) {
        super.reset(pDuration, pFromValueA, pToValueA, pFromValueB, pToValueB);
    }

    public void reset(float pDuration, float pFromValueA, float pToValueA, float pFromValueB, float pToValueB, float pFromValueC, float pToValueC) {
        super.reset(pDuration, pFromValueA, pToValueA, pFromValueB, pToValueB);
        this.mFromValueC = pFromValueC;
        this.mValueSpanC = pToValueC - pFromValueC;
    }
}
