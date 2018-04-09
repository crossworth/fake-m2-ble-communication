package org.andengine.entity.particle.modifier;

import org.andengine.entity.IEntity;
import org.andengine.entity.particle.Particle;
import org.andengine.util.modifier.ease.EaseLinear;
import org.andengine.util.modifier.ease.IEaseFunction;

public abstract class BaseDoubleValueSpanParticleModifier<T extends IEntity> extends BaseSingleValueSpanParticleModifier<T> {
    private float mFromValueB;
    private float mValueSpanB;

    protected abstract void onSetInitialValues(Particle<T> particle, float f, float f2);

    protected abstract void onSetValues(Particle<T> particle, float f, float f2, float f3);

    public BaseDoubleValueSpanParticleModifier(float pFromTime, float pToTime, float pFromValueA, float pToValueA, float pFromValueB, float pToValueB) {
        this(pFromTime, pToTime, pFromValueA, pToValueA, pFromValueB, pToValueB, EaseLinear.getInstance());
    }

    public BaseDoubleValueSpanParticleModifier(float pFromTime, float pToTime, float pFromValueA, float pToValueA, float pFromValueB, float pToValueB, IEaseFunction pEaseFunction) {
        super(pFromTime, pToTime, pFromValueA, pToValueA, pEaseFunction);
        this.mFromValueB = pFromValueB;
        this.mValueSpanB = pToValueB - pFromValueB;
    }

    public void onSetInitialValue(Particle<T> pParticle, float pValueA) {
        onSetInitialValues(pParticle, pValueA, this.mFromValueB);
    }

    protected void onSetValue(Particle<T> pParticle, float pPercentageDone, float pValueA) {
        onSetValues(pParticle, pPercentageDone, pValueA, this.mFromValueB + (this.mValueSpanB * pPercentageDone));
    }

    @Deprecated
    public void reset(float pFromValue, float pToValue, float pFromTime, float pToTime) {
        super.reset(pFromValue, pToValue, pFromTime, pToTime);
    }

    public void reset(float pFromValueA, float pToValueA, float pFromValueB, float pToValueB, float pFromTime, float pToTime) {
        super.reset(pFromValueA, pToValueA, pFromTime, pToTime);
        this.mFromValueB = pFromValueB;
        this.mValueSpanB = pToValueB - pFromValueB;
    }
}
