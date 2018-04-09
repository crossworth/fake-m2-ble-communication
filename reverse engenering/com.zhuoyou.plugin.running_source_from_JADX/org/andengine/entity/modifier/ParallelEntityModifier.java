package org.andengine.entity.modifier;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.IModifier.DeepCopyNotSupportedException;
import org.andengine.util.modifier.ParallelModifier;

public class ParallelEntityModifier extends ParallelModifier<IEntity> implements IEntityModifier {
    public ParallelEntityModifier(IEntityModifier... pEntityModifiers) throws IllegalArgumentException {
        super((IModifier[]) pEntityModifiers);
    }

    public ParallelEntityModifier(IEntityModifierListener pEntityModifierListener, IEntityModifier... pEntityModifiers) throws IllegalArgumentException {
        super(pEntityModifierListener, pEntityModifiers);
    }

    protected ParallelEntityModifier(ParallelEntityModifier pParallelShapeModifier) throws DeepCopyNotSupportedException {
        super((ParallelModifier) pParallelShapeModifier);
    }

    public ParallelEntityModifier deepCopy() throws DeepCopyNotSupportedException {
        return new ParallelEntityModifier(this);
    }
}
