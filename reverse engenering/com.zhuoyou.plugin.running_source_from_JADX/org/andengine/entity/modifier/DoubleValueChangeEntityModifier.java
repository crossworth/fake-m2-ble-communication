package org.andengine.entity.modifier;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.util.modifier.BaseDoubleValueChangeModifier;

public abstract class DoubleValueChangeEntityModifier extends BaseDoubleValueChangeModifier<IEntity> implements IEntityModifier {
    public DoubleValueChangeEntityModifier(float pDuration, float pValueChangeA, float pValueChangeB) {
        super(pDuration, pValueChangeA, pValueChangeB);
    }

    public DoubleValueChangeEntityModifier(float pDuration, float pValueChangeA, float pValueChangeB, IEntityModifierListener pModifierListener) {
        super(pDuration, pValueChangeA, pValueChangeB, pModifierListener);
    }

    public DoubleValueChangeEntityModifier(DoubleValueChangeEntityModifier pDoubleValueChangeEntityModifier) {
        super(pDoubleValueChangeEntityModifier);
    }
}
