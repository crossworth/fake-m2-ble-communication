package org.andengine.entity.modifier;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.util.modifier.ease.EaseLinear;
import org.andengine.util.modifier.ease.IEaseFunction;

public class SkewXModifier extends SingleValueSpanEntityModifier {
    public SkewXModifier(float pDuration, float pFromSkewX, float pToSkewX) {
        this(pDuration, pFromSkewX, pToSkewX, null, EaseLinear.getInstance());
    }

    public SkewXModifier(float pDuration, float pFromSkewX, float pToSkewX, IEaseFunction pEaseFunction) {
        this(pDuration, pFromSkewX, pToSkewX, null, pEaseFunction);
    }

    public SkewXModifier(float pDuration, float pFromSkewX, float pToSkewX, IEntityModifierListener pEntityModifierListener) {
        super(pDuration, pFromSkewX, pToSkewX, pEntityModifierListener, EaseLinear.getInstance());
    }

    public SkewXModifier(float pDuration, float pFromSkewX, float pToSkewX, IEntityModifierListener pEntityModifierListener, IEaseFunction pEaseFunction) {
        super(pDuration, pFromSkewX, pToSkewX, pEntityModifierListener, pEaseFunction);
    }

    protected SkewXModifier(SkewXModifier pSkewXModifier) {
        super(pSkewXModifier);
    }

    public SkewXModifier deepCopy() {
        return new SkewXModifier(this);
    }

    protected void onSetInitialValue(IEntity pEntity, float pSkewX) {
        pEntity.setSkewX(pSkewX);
    }

    protected void onSetValue(IEntity pEntity, float pPercentageDone, float pSkewX) {
        pEntity.setSkewX(pSkewX);
    }
}
