package org.andengine.entity.modifier;

import org.andengine.entity.IEntity;
import org.andengine.util.IMatcher;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.IModifier.DeepCopyNotSupportedException;
import org.andengine.util.modifier.IModifier.IModifierListener;

public interface IEntityModifier extends IModifier<IEntity> {

    public interface IEntityModifierListener extends IModifierListener<IEntity> {
    }

    public interface IEntityModifierMatcher extends IMatcher<IModifier<IEntity>> {
    }

    IEntityModifier deepCopy() throws DeepCopyNotSupportedException;
}
