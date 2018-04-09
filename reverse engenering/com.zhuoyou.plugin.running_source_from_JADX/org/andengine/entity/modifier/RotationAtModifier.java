package org.andengine.entity.modifier;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.util.modifier.ease.EaseLinear;
import org.andengine.util.modifier.ease.IEaseFunction;

public class RotationAtModifier extends RotationModifier {
    private final float mRotationCenterX;
    private final float mRotationCenterY;

    public RotationAtModifier(float pDuration, float pFromRotation, float pToRotation, float pRotationCenterX, float pRotationCenterY) {
        this(pDuration, pFromRotation, pToRotation, pRotationCenterX, pRotationCenterY, EaseLinear.getInstance());
    }

    public RotationAtModifier(float pDuration, float pFromRotation, float pToRotation, float pRotationCenterX, float pRotationCenterY, IEaseFunction pEaseFunction) {
        this(pDuration, pFromRotation, pToRotation, pRotationCenterX, pRotationCenterY, null, pEaseFunction);
    }

    public RotationAtModifier(float pDuration, float pFromRotation, float pToRotation, float pRotationCenterX, float pRotationCenterY, IEntityModifierListener pEntityModifierListener) {
        this(pDuration, pFromRotation, pToRotation, pRotationCenterX, pRotationCenterY, pEntityModifierListener, EaseLinear.getInstance());
    }

    public RotationAtModifier(float pDuration, float pFromRotation, float pToRotation, float pRotationCenterX, float pRotationCenterY, IEntityModifierListener pEntityModifierListener, IEaseFunction pEaseFunction) {
        super(pDuration, pFromRotation, pToRotation, pEntityModifierListener, pEaseFunction);
        this.mRotationCenterX = pRotationCenterX;
        this.mRotationCenterY = pRotationCenterY;
    }

    protected RotationAtModifier(RotationAtModifier pRotationAtModifier) {
        super(pRotationAtModifier);
        this.mRotationCenterX = pRotationAtModifier.mRotationCenterX;
        this.mRotationCenterY = pRotationAtModifier.mRotationCenterY;
    }

    public RotationAtModifier deepCopy() {
        return new RotationAtModifier(this);
    }

    protected void onManagedInitialize(IEntity pEntity) {
        super.onManagedInitialize(pEntity);
        pEntity.setRotationCenter(this.mRotationCenterX, this.mRotationCenterY);
    }
}
