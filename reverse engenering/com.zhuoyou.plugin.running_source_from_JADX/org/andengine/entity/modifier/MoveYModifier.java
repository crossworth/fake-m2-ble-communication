package org.andengine.entity.modifier;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.util.modifier.ease.EaseLinear;
import org.andengine.util.modifier.ease.IEaseFunction;

public class MoveYModifier extends SingleValueSpanEntityModifier {
    public MoveYModifier(float pDuration, float pFromY, float pToY) {
        this(pDuration, pFromY, pToY, null, EaseLinear.getInstance());
    }

    public MoveYModifier(float pDuration, float pFromY, float pToY, IEaseFunction pEaseFunction) {
        this(pDuration, pFromY, pToY, null, pEaseFunction);
    }

    public MoveYModifier(float pDuration, float pFromY, float pToY, IEntityModifierListener pEntityModifierListener) {
        super(pDuration, pFromY, pToY, pEntityModifierListener, EaseLinear.getInstance());
    }

    public MoveYModifier(float pDuration, float pFromY, float pToY, IEntityModifierListener pEntityModifierListener, IEaseFunction pEaseFunction) {
        super(pDuration, pFromY, pToY, pEntityModifierListener, pEaseFunction);
    }

    protected MoveYModifier(MoveYModifier pMoveYModifier) {
        super(pMoveYModifier);
    }

    public MoveYModifier deepCopy() {
        return new MoveYModifier(this);
    }

    protected void onSetInitialValue(IEntity pEntity, float pY) {
        pEntity.setY(pY);
    }

    protected void onSetValue(IEntity pEntity, float pPercentageDone, float pY) {
        pEntity.setY(pY);
    }
}
