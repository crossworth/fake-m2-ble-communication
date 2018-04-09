package org.andengine.entity.particle.modifier;

import org.andengine.entity.IEntity;
import org.andengine.entity.particle.Particle;
import org.andengine.util.modifier.ease.EaseLinear;
import org.andengine.util.modifier.ease.IEaseFunction;

public abstract class BaseSingleValueSpanParticleModifier<T extends IEntity> implements IParticleModifier<T> {
    private float mDuration;
    private final IEaseFunction mEaseFunction;
    private float mFromTime;
    private float mFromValue;
    private float mToTime;
    private float mValueSpan;

    protected abstract void onSetInitialValue(Particle<T> particle, float f);

    protected abstract void onSetValue(Particle<T> particle, float f, float f2);

    public BaseSingleValueSpanParticleModifier(float pFromTime, float pToTime, float pFromValue, float pToValue) {
        this(pFromTime, pToTime, pFromValue, pToValue, EaseLinear.getInstance());
    }

    public BaseSingleValueSpanParticleModifier(float pFromTime, float pToTime, float pFromValue, float pToValue, IEaseFunction pEaseFunction) {
        this.mFromTime = pFromTime;
        this.mToTime = pToTime;
        this.mDuration = pToTime - pFromTime;
        this.mFromValue = pFromValue;
        this.mValueSpan = pToValue - pFromValue;
        this.mEaseFunction = pEaseFunction;
    }

    public void onInitializeParticle(Particle<T> pParticle) {
        onSetInitialValue(pParticle, this.mFromValue);
    }

    public void onUpdateParticle(Particle<T> pParticle) {
        float lifeTime = pParticle.getLifeTime();
        if (lifeTime > this.mFromTime && lifeTime < this.mToTime) {
            float percentageDone = this.mEaseFunction.getPercentage(lifeTime - this.mFromTime, this.mDuration);
            onSetValue(pParticle, percentageDone, this.mFromValue + (this.mValueSpan * percentageDone));
        }
    }

    public void reset(float pFromValue, float pToValue, float pFromTime, float pToTime) {
        this.mFromValue = pFromValue;
        this.mFromTime = pFromTime;
        this.mToTime = pToTime;
        this.mValueSpan = pToValue - pFromValue;
        this.mDuration = pToTime - pFromTime;
    }
}
