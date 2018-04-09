package org.andengine.entity.scene.background.modifier;

import org.andengine.entity.scene.background.IBackground;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.IModifier.DeepCopyNotSupportedException;
import org.andengine.util.modifier.IModifier.IModifierListener;

public interface IBackgroundModifier extends IModifier<IBackground> {

    public interface IBackgroundModifierListener extends IModifierListener<IBackground> {
    }

    IBackgroundModifier deepCopy() throws DeepCopyNotSupportedException;
}
