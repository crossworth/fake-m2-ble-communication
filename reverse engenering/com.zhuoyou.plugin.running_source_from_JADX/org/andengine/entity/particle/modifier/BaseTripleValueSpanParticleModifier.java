package org.andengine.entity.particle.modifier;

import org.andengine.entity.IEntity;
import org.andengine.entity.particle.Particle;
import org.andengine.util.modifier.ease.EaseLinear;
import org.andengine.util.modifier.ease.IEaseFunction;

public abstract class BaseTripleValueSpanParticleModifier<T extends IEntity> extends BaseDoubleValueSpanParticleModifier<T> {
    private float mFromValueC;
    private float mValueSpanC;

    protected abstract void onSetInitialValues(Particle<T> particle, float f, float f2, float f3);

    protected abstract void onSetValues(Particle<T> particle, float f, float f2, float f3, float f4);

    public BaseTripleValueSpanParticleModifier(float pFromTime, float pToTime, float pFromValueA, float pToValueA, float pFromValueB, float pToValueB, float pFromValueC, float pToValueC) {
        this(pFromTime, pToTime, pFromValueA, pToValueA, pFromValueB, pToValueB, pFromValueC, pToValueC, EaseLinear.getInstance());
    }

    public BaseTripleValueSpanParticleModifier(float pFromTime, float pToTime, float pFromValueA, float pToValueA, float pFromValueB, float pToValueB, float pFromValueC, float pToValueC, IEaseFunction pEaseFunction) {
        super(pFromTime, pToTime, pFromValueA, pToValueA, pFromValueB, pToValueB, pEaseFunction);
        this.mFromValueC = pFromValueC;
        this.mValueSpanC = pToValueC - pFromValueC;
    }

    public void onSetInitialValues(Particle<T> pParticle, float pValueA, float pValueB) {
        onSetInitialValues(pParticle, pValueA, pValueB, this.mFromValueC);
    }

    protected void onSetValues(Particle<T> pParticle, float pPercentageDone, float pValueA, float pValueB) {
        onSetValues(pParticle, pPercentageDone, pValueA, pValueB, this.mFromValueC + (this.mValueSpanC * pPercentageDone));
    }

    @Deprecated
    public void reset(float pFromValueA, float pToValueA, float pFromValueB, float pToValueB, float pFromTime, float pToTime) {
        super.reset(pFromValueA, pToValueA, pFromValueB, pToValueB, pFromTime, pToTime);
    }

    public void reset(float pFromValueA, float pToValueA, float pFromValueB, float pToValueB, float pFromValueC, float pToValueC, float pFromTime, float pToTime) {
        super.reset(pFromValueA, pToValueA, pFromValueB, pToValueB, pFromTime, pToTime);
        this.mFromValueC = pFromValueC;
        this.mValueSpanC = pToValueC - pFromValueC;
    }
}
