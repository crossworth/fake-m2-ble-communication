package org.andengine.entity.modifier;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.util.modifier.ease.EaseLinear;
import org.andengine.util.modifier.ease.IEaseFunction;

public class RotationModifier extends SingleValueSpanEntityModifier {
    public RotationModifier(float pDuration, float pFromRotation, float pToRotation) {
        this(pDuration, pFromRotation, pToRotation, null, EaseLinear.getInstance());
    }

    public RotationModifier(float pDuration, float pFromRotation, float pToRotation, IEaseFunction pEaseFunction) {
        this(pDuration, pFromRotation, pToRotation, null, pEaseFunction);
    }

    public RotationModifier(float pDuration, float pFromRotation, float pToRotation, IEntityModifierListener pEntityModifierListener) {
        super(pDuration, pFromRotation, pToRotation, pEntityModifierListener, EaseLinear.getInstance());
    }

    public RotationModifier(float pDuration, float pFromRotation, float pToRotation, IEntityModifierListener pEntityModifierListener, IEaseFunction pEaseFunction) {
        super(pDuration, pFromRotation, pToRotation, pEntityModifierListener, pEaseFunction);
    }

    protected RotationModifier(RotationModifier pRotationModifier) {
        super(pRotationModifier);
    }

    public RotationModifier deepCopy() {
        return new RotationModifier(this);
    }

    protected void onSetInitialValue(IEntity pEntity, float pRotation) {
        pEntity.setRotation(pRotation);
    }

    protected void onSetValue(IEntity pEntity, float pPercentageDone, float pRotation) {
        pEntity.setRotation(pRotation);
    }
}
