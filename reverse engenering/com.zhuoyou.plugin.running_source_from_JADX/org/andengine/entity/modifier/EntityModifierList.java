package org.andengine.entity.modifier;

import org.andengine.entity.IEntity;
import org.andengine.util.modifier.ModifierList;

public class EntityModifierList extends ModifierList<IEntity> {
    private static final long serialVersionUID = 161652765736600082L;

    public EntityModifierList(IEntity pTarget) {
        super(pTarget);
    }

    public EntityModifierList(IEntity pTarget, int pCapacity) {
        super(pTarget, pCapacity);
    }
}
