package org.andengine.entity.modifier;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.util.modifier.BaseDurationModifier;

public abstract class DurationEntityModifier extends BaseDurationModifier<IEntity> implements IEntityModifier {
    public DurationEntityModifier(float pDuration) {
        super(pDuration);
    }

    public DurationEntityModifier(float pDuration, IEntityModifierListener pEntityModifierListener) {
        super(pDuration, pEntityModifierListener);
    }

    protected DurationEntityModifier(DurationEntityModifier pDurationEntityModifier) {
        super((BaseDurationModifier) pDurationEntityModifier);
    }
}
