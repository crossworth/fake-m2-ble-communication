package org.andengine.entity.particle.modifier;

import org.andengine.entity.IEntity;
import org.andengine.entity.particle.Particle;
import org.andengine.util.modifier.ease.EaseLinear;
import org.andengine.util.modifier.ease.IEaseFunction;

public class ScaleParticleModifier<T extends IEntity> extends BaseDoubleValueSpanParticleModifier<T> {
    public ScaleParticleModifier(float pFromTime, float pToTime, float pFromScale, float pToScale) {
        this(pFromTime, pToTime, pFromScale, pToScale, EaseLinear.getInstance());
    }

    public ScaleParticleModifier(float pFromTime, float pToTime, float pFromScale, float pToScale, IEaseFunction pEaseFunction) {
        this(pFromTime, pToTime, pFromScale, pToScale, pFromScale, pToScale, pEaseFunction);
    }

    public ScaleParticleModifier(float pFromTime, float pToTime, float pFromScaleX, float pToScaleX, float pFromScaleY, float pToScaleY) {
        this(pFromTime, pToTime, pFromScaleX, pToScaleX, pFromScaleY, pToScaleY, EaseLinear.getInstance());
    }

    public ScaleParticleModifier(float pFromTime, float pToTime, float pFromScaleX, float pToScaleX, float pFromScaleY, float pToScaleY, IEaseFunction pEaseFunction) {
        super(pFromTime, pToTime, pFromScaleX, pToScaleX, pFromScaleY, pToScaleY, pEaseFunction);
    }

    protected void onSetInitialValues(Particle<T> pParticle, float pScaleX, float pScaleY) {
        pParticle.getEntity().setScale(pScaleX, pScaleY);
    }

    protected void onSetValues(Particle<T> pParticle, float pPercentageDone, float pScaleX, float pScaleY) {
        pParticle.getEntity().setScale(pScaleX, pScaleY);
    }
}
