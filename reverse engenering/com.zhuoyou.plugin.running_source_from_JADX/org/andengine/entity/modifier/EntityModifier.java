package org.andengine.entity.modifier;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.util.modifier.BaseModifier;

public abstract class EntityModifier extends BaseModifier<IEntity> implements IEntityModifier {
    public EntityModifier(IEntityModifierListener pEntityModifierListener) {
        super(pEntityModifierListener);
    }
}
