package org.andengine.entity.scene.background.modifier;

import org.andengine.entity.scene.background.IBackground;
import org.andengine.entity.scene.background.modifier.IBackgroundModifier.IBackgroundModifierListener;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.IModifier.DeepCopyNotSupportedException;
import org.andengine.util.modifier.IModifier.IModifierListener;
import org.andengine.util.modifier.SequenceModifier;
import org.andengine.util.modifier.SequenceModifier.ISubSequenceModifierListener;

public class SequenceBackgroundModifier extends SequenceModifier<IBackground> implements IBackgroundModifier {

    public interface ISubSequenceBackgroundModifierListener extends ISubSequenceModifierListener<IBackground> {
    }

    public SequenceBackgroundModifier(IBackgroundModifier... pBackgroundModifiers) throws IllegalArgumentException {
        super((IModifier[]) pBackgroundModifiers);
    }

    public SequenceBackgroundModifier(ISubSequenceBackgroundModifierListener pSubSequenceBackgroundModifierListener, IBackgroundModifier... pBackgroundModifiers) throws IllegalArgumentException {
        super((ISubSequenceModifierListener) pSubSequenceBackgroundModifierListener, (IModifier[]) pBackgroundModifiers);
    }

    public SequenceBackgroundModifier(IBackgroundModifierListener pBackgroundModifierListener, IBackgroundModifier... pBackgroundModifiers) throws IllegalArgumentException {
        super((IModifierListener) pBackgroundModifierListener, (IModifier[]) pBackgroundModifiers);
    }

    public SequenceBackgroundModifier(ISubSequenceBackgroundModifierListener pSubSequenceBackgroundModifierListener, IBackgroundModifierListener pBackgroundModifierListener, IBackgroundModifier... pBackgroundModifiers) throws IllegalArgumentException {
        super(pSubSequenceBackgroundModifierListener, pBackgroundModifierListener, pBackgroundModifiers);
    }

    protected SequenceBackgroundModifier(SequenceBackgroundModifier pSequenceBackgroundModifier) throws DeepCopyNotSupportedException {
        super((SequenceModifier) pSequenceBackgroundModifier);
    }

    public SequenceBackgroundModifier deepCopy() throws DeepCopyNotSupportedException {
        return new SequenceBackgroundModifier(this);
    }
}
