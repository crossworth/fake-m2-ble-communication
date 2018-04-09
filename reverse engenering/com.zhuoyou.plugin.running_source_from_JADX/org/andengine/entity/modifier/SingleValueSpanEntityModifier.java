package org.andengine.entity.modifier;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.util.modifier.BaseSingleValueSpanModifier;
import org.andengine.util.modifier.IModifier.IModifierListener;
import org.andengine.util.modifier.ease.IEaseFunction;

public abstract class SingleValueSpanEntityModifier extends BaseSingleValueSpanModifier<IEntity> implements IEntityModifier {
    public SingleValueSpanEntityModifier(float pDuration, float pFromValue, float pToValue) {
        super(pDuration, pFromValue, pToValue);
    }

    public SingleValueSpanEntityModifier(float pDuration, float pFromValue, float pToValue, IEaseFunction pEaseFunction) {
        super(pDuration, pFromValue, pToValue, pEaseFunction);
    }

    public SingleValueSpanEntityModifier(float pDuration, float pFromValue, float pToValue, IEntityModifierListener pEntityModifierListener) {
        super(pDuration, pFromValue, pToValue, (IModifierListener) pEntityModifierListener);
    }

    public SingleValueSpanEntityModifier(float pDuration, float pFromValue, float pToValue, IEntityModifierListener pEntityModifierListener, IEaseFunction pEaseFunction) {
        super(pDuration, pFromValue, pToValue, pEntityModifierListener, pEaseFunction);
    }

    protected SingleValueSpanEntityModifier(SingleValueSpanEntityModifier pSingleValueSpanEntityModifier) {
        super(pSingleValueSpanEntityModifier);
    }
}
