package org.andengine.entity.modifier;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;

public class MoveByModifier extends DoubleValueChangeEntityModifier {
    public MoveByModifier(float pDuration, float pX, float pY) {
        super(pDuration, pX, pY);
    }

    public MoveByModifier(float pDuration, float pX, float pY, IEntityModifierListener pEntityModifierListener) {
        super(pDuration, pX, pY, pEntityModifierListener);
    }

    protected MoveByModifier(DoubleValueChangeEntityModifier pDoubleValueChangeEntityModifier) {
        super(pDoubleValueChangeEntityModifier);
    }

    public MoveByModifier deepCopy() {
        return new MoveByModifier(this);
    }

    protected void onChangeValues(float pSecondsElapsed, IEntity pEntity, float pX, float pY) {
        pEntity.setPosition(pEntity.getX() + pX, pEntity.getY() + pY);
    }
}
