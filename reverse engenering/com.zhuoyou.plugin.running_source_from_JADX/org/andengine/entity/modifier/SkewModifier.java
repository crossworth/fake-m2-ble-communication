package org.andengine.entity.modifier;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.util.modifier.ease.EaseLinear;
import org.andengine.util.modifier.ease.IEaseFunction;

public class SkewModifier extends DoubleValueSpanEntityModifier {
    public SkewModifier(float pDuration, float pFromSkewX, float pToSkewX, float pFromSkewY, float pToSkewY) {
        this(pDuration, pFromSkewX, pToSkewX, pFromSkewY, pToSkewY, null, EaseLinear.getInstance());
    }

    public SkewModifier(float pDuration, float pFromSkewX, float pToSkewX, float pFromSkewY, float pToSkewY, IEaseFunction pEaseFunction) {
        this(pDuration, pFromSkewX, pToSkewX, pFromSkewY, pToSkewY, null, pEaseFunction);
    }

    public SkewModifier(float pDuration, float pFromSkewX, float pToSkewX, float pFromSkewY, float pToSkewY, IEntityModifierListener pEntityModifierListener) {
        super(pDuration, pFromSkewX, pToSkewX, pFromSkewY, pToSkewY, pEntityModifierListener, EaseLinear.getInstance());
    }

    public SkewModifier(float pDuration, float pFromSkewX, float pToSkewX, float pFromSkewY, float pToSkewY, IEntityModifierListener pEntityModifierListener, IEaseFunction pEaseFunction) {
        super(pDuration, pFromSkewX, pToSkewX, pFromSkewY, pToSkewY, pEntityModifierListener, pEaseFunction);
    }

    protected SkewModifier(SkewModifier pSkewXModifier) {
        super(pSkewXModifier);
    }

    public SkewModifier deepCopy() {
        return new SkewModifier(this);
    }

    protected void onSetInitialValues(IEntity pEntity, float pSkewX, float pSkewY) {
        pEntity.setSkew(pSkewX, pSkewY);
    }

    protected void onSetValues(IEntity pEntity, float pPercentageDone, float pSkewX, float pSkewY) {
        pEntity.setSkew(pSkewX, pSkewY);
    }
}
