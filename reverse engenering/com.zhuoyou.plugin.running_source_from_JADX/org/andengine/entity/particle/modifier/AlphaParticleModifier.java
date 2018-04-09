package org.andengine.entity.particle.modifier;

import org.andengine.entity.IEntity;
import org.andengine.entity.particle.Particle;
import org.andengine.util.modifier.ease.EaseLinear;
import org.andengine.util.modifier.ease.IEaseFunction;

public class AlphaParticleModifier<T extends IEntity> extends BaseSingleValueSpanParticleModifier<T> {
    public AlphaParticleModifier(float pFromTime, float pToTime, float pFromAlpha, float pToAlpha) {
        this(pFromTime, pToTime, pFromAlpha, pToAlpha, EaseLinear.getInstance());
    }

    public AlphaParticleModifier(float pFromTime, float pToTime, float pFromAlpha, float pToAlpha, IEaseFunction pEaseFunction) {
        super(pFromTime, pToTime, pFromAlpha, pToAlpha, pEaseFunction);
    }

    protected void onSetInitialValue(Particle<T> pParticle, float pAlpha) {
        pParticle.getEntity().setAlpha(pAlpha);
    }

    protected void onSetValue(Particle<T> pParticle, float pPercentageDone, float pAlpha) {
        pParticle.getEntity().setAlpha(pAlpha);
    }
}
