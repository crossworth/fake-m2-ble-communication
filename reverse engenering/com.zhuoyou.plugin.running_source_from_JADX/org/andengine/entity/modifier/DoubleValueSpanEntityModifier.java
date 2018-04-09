package org.andengine.entity.modifier;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.util.modifier.BaseDoubleValueSpanModifier;
import org.andengine.util.modifier.IModifier.IModifierListener;
import org.andengine.util.modifier.ease.IEaseFunction;

public abstract class DoubleValueSpanEntityModifier extends BaseDoubleValueSpanModifier<IEntity> implements IEntityModifier {
    public DoubleValueSpanEntityModifier(float pDuration, float pFromValueA, float pToValueA, float pFromValueB, float pToValueB) {
        super(pDuration, pFromValueA, pToValueA, pFromValueB, pToValueB);
    }

    public DoubleValueSpanEntityModifier(float pDuration, float pFromValueA, float pToValueA, float pFromValueB, float pToValueB, IEaseFunction pEaseFunction) {
        super(pDuration, pFromValueA, pToValueA, pFromValueB, pToValueB, pEaseFunction);
    }

    public DoubleValueSpanEntityModifier(float pDuration, float pFromValueA, float pToValueA, float pFromValueB, float pToValueB, IEntityModifierListener pEntityModifierListener) {
        super(pDuration, pFromValueA, pToValueA, pFromValueB, pToValueB, (IModifierListener) pEntityModifierListener);
    }

    public DoubleValueSpanEntityModifier(float pDuration, float pFromValueA, float pToValueA, float pFromValueB, float pToValueB, IEntityModifierListener pEntityModifierListener, IEaseFunction pEaseFunction) {
        super(pDuration, pFromValueA, pToValueA, pFromValueB, pToValueB, pEntityModifierListener, pEaseFunction);
    }

    protected DoubleValueSpanEntityModifier(DoubleValueSpanEntityModifier pDoubleValueSpanEntityModifier) {
        super(pDoubleValueSpanEntityModifier);
    }
}
