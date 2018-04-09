package org.andengine.entity.particle.modifier;

import org.andengine.entity.IEntity;
import org.andengine.entity.particle.Particle;
import org.andengine.util.modifier.ease.EaseLinear;
import org.andengine.util.modifier.ease.IEaseFunction;

public class ColorParticleModifier<T extends IEntity> extends BaseTripleValueSpanParticleModifier<T> {
    public ColorParticleModifier(float pFromTime, float pToTime, float pFromRed, float pToRed, float pFromGreen, float pToGreen, float pFromBlue, float pToBlue) {
        this(pFromTime, pToTime, pFromRed, pToRed, pFromGreen, pToGreen, pFromBlue, pToBlue, EaseLinear.getInstance());
    }

    public ColorParticleModifier(float pFromTime, float pToTime, float pFromRed, float pToRed, float pFromGreen, float pToGreen, float pFromBlue, float pToBlue, IEaseFunction pEaseFunction) {
        super(pFromTime, pToTime, pFromRed, pToRed, pFromGreen, pToGreen, pFromBlue, pToBlue, pEaseFunction);
    }

    protected void onSetInitialValues(Particle<T> pParticle, float pRed, float pGreen, float pBlue) {
        pParticle.getEntity().setColor(pRed, pGreen, pBlue);
    }

    protected void onSetValues(Particle<T> pParticle, float pPercentageDone, float pRed, float pGreen, float pBlue) {
        pParticle.getEntity().setColor(pRed, pGreen, pBlue);
    }
}
