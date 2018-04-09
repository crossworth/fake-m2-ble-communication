package org.andengine.entity.modifier;

import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.util.modifier.ease.EaseLinear;
import org.andengine.util.modifier.ease.IEaseFunction;

public class FadeInModifier extends AlphaModifier {
    public FadeInModifier(float pDuration) {
        super(pDuration, 0.0f, 1.0f, EaseLinear.getInstance());
    }

    public FadeInModifier(float pDuration, IEaseFunction pEaseFunction) {
        super(pDuration, 0.0f, 1.0f, pEaseFunction);
    }

    public FadeInModifier(float pDuration, IEntityModifierListener pEntityModifierListener) {
        super(pDuration, 0.0f, 1.0f, pEntityModifierListener, EaseLinear.getInstance());
    }

    public FadeInModifier(float pDuration, IEntityModifierListener pEntityModifierListener, IEaseFunction pEaseFunction) {
        super(pDuration, 0.0f, 1.0f, pEntityModifierListener, pEaseFunction);
    }

    protected FadeInModifier(FadeInModifier pFadeInModifier) {
        super(pFadeInModifier);
    }

    public FadeInModifier deepCopy() {
        return new FadeInModifier(this);
    }
}
