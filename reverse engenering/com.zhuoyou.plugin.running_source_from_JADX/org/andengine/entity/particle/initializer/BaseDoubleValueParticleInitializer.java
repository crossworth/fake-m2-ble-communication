package org.andengine.entity.particle.initializer;

import org.andengine.entity.IEntity;
import org.andengine.entity.particle.Particle;
import org.andengine.util.math.MathUtils;

public abstract class BaseDoubleValueParticleInitializer<T extends IEntity> extends BaseSingleValueParticleInitializer<T> {
    protected float mMaxValueB;
    protected float mMinValueB;

    protected abstract void onInitializeParticle(Particle<T> particle, float f, float f2);

    public BaseDoubleValueParticleInitializer(float pMinValueA, float pMaxValueA, float pMinValueB, float pMaxValueB) {
        super(pMinValueA, pMaxValueA);
        this.mMinValueB = pMinValueB;
        this.mMaxValueB = pMaxValueB;
    }

    protected final void onInitializeParticle(Particle<T> pParticle, float pValueA) {
        onInitializeParticle(pParticle, pValueA, getRandomValueB());
    }

    protected float getRandomValueB() {
        if (this.mMinValueB == this.mMaxValueB) {
            return this.mMaxValueB;
        }
        return MathUtils.random(this.mMinValueB, this.mMaxValueB);
    }
}
