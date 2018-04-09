package org.andengine.util.modifier;

import org.andengine.util.modifier.IModifier.IModifierListener;
import org.andengine.util.modifier.ease.IEaseFunction;

public abstract class BaseQuadrupelValueSpanModifier<T> extends BaseTripleValueSpanModifier<T> {
    private float mFromValueD;
    private float mValueSpanD;

    protected abstract void onSetInitialValues(T t, float f, float f2, float f3, float f4);

    protected abstract void onSetValues(T t, float f, float f2, float f3, float f4, float f5);

    public BaseQuadrupelValueSpanModifier(float pDuration, float pFromValueA, float pToValueA, float pFromValueB, float pToValueB, float pFromValueC, float pToValueC, float pFromValueD, float pToValueD, IEaseFunction pEaseFunction) {
        this(pDuration, pFromValueA, pToValueA, pFromValueB, pToValueB, pFromValueC, pToValueC, pFromValueD, pToValueD, null, pEaseFunction);
    }

    public BaseQuadrupelValueSpanModifier(float pDuration, float pFromValueA, float pToValueA, float pFromValueB, float pToValueB, float pFromValueC, float pToValueC, float pFromValueD, float pToValueD, IModifierListener<T> pModifierListener, IEaseFunction pEaseFunction) {
        super(pDuration, pFromValueA, pToValueA, pFromValueB, pToValueB, pFromValueC, pToValueC, pModifierListener, pEaseFunction);
        this.mFromValueD = pFromValueD;
        this.mValueSpanD = pToValueD - pFromValueD;
    }

    protected BaseQuadrupelValueSpanModifier(BaseQuadrupelValueSpanModifier<T> pBaseTripleValueSpanModifier) {
        super(pBaseTripleValueSpanModifier);
        this.mFromValueD = pBaseTripleValueSpanModifier.mFromValueD;
        this.mValueSpanD = pBaseTripleValueSpanModifier.mValueSpanD;
    }

    protected void onSetInitialValues(T pItem, float pValueA, float pValueB, float pValueC) {
        onSetInitialValues(pItem, pValueA, pValueB, pValueC, this.mFromValueD);
    }

    protected void onSetValues(T pItem, float pPercentageDone, float pValueA, float pValueB, float pValueC) {
        onSetValues(pItem, pPercentageDone, pValueA, pValueB, pValueC, this.mFromValueD + (this.mValueSpanD * pPercentageDone));
    }

    @Deprecated
    public void reset(float pDuration, float pFromValueA, float pToValueA, float pFromValueB, float pToValueB, float pFromValueC, float pToValueC) {
        super.reset(pDuration, pFromValueA, pToValueA, pFromValueB, pToValueB, pFromValueC, pToValueC);
    }

    public void reset(float pDuration, float pFromValueA, float pToValueA, float pFromValueB, float pToValueB, float pFromValueC, float pToValueC, float pFromValueD, float pToValueD) {
        super.reset(pDuration, pFromValueA, pToValueA, pFromValueB, pToValueB, pFromValueC, pToValueC);
        this.mFromValueD = pFromValueD;
        this.mValueSpanD = pToValueD - pFromValueD;
    }
}
