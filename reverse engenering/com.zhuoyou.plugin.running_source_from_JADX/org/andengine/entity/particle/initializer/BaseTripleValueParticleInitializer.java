package org.andengine.entity.particle.initializer;

import org.andengine.entity.IEntity;
import org.andengine.entity.particle.Particle;
import org.andengine.util.math.MathUtils;

public abstract class BaseTripleValueParticleInitializer<T extends IEntity> extends BaseDoubleValueParticleInitializer<T> {
    protected float mMaxValueC;
    protected float mMinValueC;

    protected abstract void onInitializeParticle(Particle<T> particle, float f, float f2, float f3);

    public BaseTripleValueParticleInitializer(float pMinValueA, float pMaxValueA, float pMinValueB, float pMaxValueB, float pMinValueC, float pMaxValueC) {
        super(pMinValueA, pMaxValueA, pMinValueB, pMaxValueB);
        this.mMinValueC = pMinValueC;
        this.mMaxValueC = pMaxValueC;
    }

    protected final void onInitializeParticle(Particle<T> pParticle, float pValueA, float pValueB) {
        onInitializeParticle(pParticle, pValueA, pValueB, getRandomValueC());
    }

    protected float getRandomValueC() {
        if (this.mMinValueC == this.mMaxValueC) {
            return this.mMaxValueC;
        }
        return MathUtils.random(this.mMinValueC, this.mMaxValueC);
    }
}
