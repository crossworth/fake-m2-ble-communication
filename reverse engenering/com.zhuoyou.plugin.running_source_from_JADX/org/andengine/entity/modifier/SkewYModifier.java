package org.andengine.entity.modifier;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.util.modifier.ease.EaseLinear;
import org.andengine.util.modifier.ease.IEaseFunction;

public class SkewYModifier extends SingleValueSpanEntityModifier {
    public SkewYModifier(float pDuration, float pFromSkewY, float pToSkewY) {
        this(pDuration, pFromSkewY, pToSkewY, null, EaseLinear.getInstance());
    }

    public SkewYModifier(float pDuration, float pFromSkewY, float pToSkewY, IEaseFunction pEaseFunction) {
        this(pDuration, pFromSkewY, pToSkewY, null, pEaseFunction);
    }

    public SkewYModifier(float pDuration, float pFromSkewY, float pToSkewY, IEntityModifierListener pEntityModifierListener) {
        super(pDuration, pFromSkewY, pToSkewY, pEntityModifierListener, EaseLinear.getInstance());
    }

    public SkewYModifier(float pDuration, float pFromSkewY, float pToSkewY, IEntityModifierListener pEntityModifierListener, IEaseFunction pEaseFunction) {
        super(pDuration, pFromSkewY, pToSkewY, pEntityModifierListener, pEaseFunction);
    }

    protected SkewYModifier(SkewYModifier pSkewYModifier) {
        super(pSkewYModifier);
    }

    public SkewYModifier deepCopy() {
        return new SkewYModifier(this);
    }

    protected void onSetInitialValue(IEntity pEntity, float pSkewY) {
        pEntity.setSkewY(pSkewY);
    }

    protected void onSetValue(IEntity pEntity, float pPercentageDone, float pSkewY) {
        pEntity.setSkewY(pSkewY);
    }
}
