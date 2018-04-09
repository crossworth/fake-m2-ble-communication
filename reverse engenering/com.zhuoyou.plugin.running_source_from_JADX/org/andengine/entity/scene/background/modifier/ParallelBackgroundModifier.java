package org.andengine.entity.scene.background.modifier;

import org.andengine.entity.scene.background.IBackground;
import org.andengine.entity.scene.background.modifier.IBackgroundModifier.IBackgroundModifierListener;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.IModifier.DeepCopyNotSupportedException;
import org.andengine.util.modifier.ParallelModifier;

public class ParallelBackgroundModifier extends ParallelModifier<IBackground> implements IBackgroundModifier {
    public ParallelBackgroundModifier(IBackgroundModifier... pBackgroundModifiers) throws IllegalArgumentException {
        super((IModifier[]) pBackgroundModifiers);
    }

    public ParallelBackgroundModifier(IBackgroundModifierListener pBackgroundModifierListener, IBackgroundModifier... pBackgroundModifiers) throws IllegalArgumentException {
        super(pBackgroundModifierListener, pBackgroundModifiers);
    }

    protected ParallelBackgroundModifier(ParallelBackgroundModifier pParallelBackgroundModifier) throws DeepCopyNotSupportedException {
        super((ParallelModifier) pParallelBackgroundModifier);
    }

    public ParallelBackgroundModifier deepCopy() throws DeepCopyNotSupportedException {
        return new ParallelBackgroundModifier(this);
    }
}
