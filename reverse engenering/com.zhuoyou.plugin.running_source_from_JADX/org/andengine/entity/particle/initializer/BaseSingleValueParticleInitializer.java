package org.andengine.entity.particle.initializer;

import org.andengine.entity.IEntity;
import org.andengine.entity.particle.Particle;
import org.andengine.util.math.MathUtils;

public abstract class BaseSingleValueParticleInitializer<T extends IEntity> implements IParticleInitializer<T> {
    protected float mMaxValue;
    protected float mMinValue;

    protected abstract void onInitializeParticle(Particle<T> particle, float f);

    public BaseSingleValueParticleInitializer(float pMinValue, float pMaxValue) {
        this.mMinValue = pMinValue;
        this.mMaxValue = pMaxValue;
    }

    public final void onInitializeParticle(Particle<T> pParticle) {
        onInitializeParticle(pParticle, getRandomValue());
    }

    protected float getRandomValue() {
        if (this.mMinValue == this.mMaxValue) {
            return this.mMaxValue;
        }
        return MathUtils.random(this.mMinValue, this.mMaxValue);
    }
}
