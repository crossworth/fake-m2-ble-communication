package org.andengine.entity.particle.modifier;

import org.andengine.entity.IEntity;
import org.andengine.entity.particle.Particle;
import org.andengine.entity.particle.initializer.IParticleInitializer;
import org.andengine.util.math.MathUtils;

public class ExpireParticleInitializer<T extends IEntity> implements IParticleInitializer<T> {
    private float mMaxLifeTime;
    private float mMinLifeTime;

    public ExpireParticleInitializer(float pLifeTime) {
        this(pLifeTime, pLifeTime);
    }

    public ExpireParticleInitializer(float pMinLifeTime, float pMaxLifeTime) {
        this.mMinLifeTime = pMinLifeTime;
        this.mMaxLifeTime = pMaxLifeTime;
    }

    public float getMinLifeTime() {
        return this.mMinLifeTime;
    }

    public float getMaxLifeTime() {
        return this.mMaxLifeTime;
    }

    public void setLifeTime(float pLifeTime) {
        this.mMinLifeTime = pLifeTime;
        this.mMaxLifeTime = pLifeTime;
    }

    public void setLifeTime(float pMinLifeTime, float pMaxLifeTime) {
        this.mMinLifeTime = pMinLifeTime;
        this.mMaxLifeTime = pMaxLifeTime;
    }

    public void onInitializeParticle(Particle<T> pParticle) {
        pParticle.setExpireTime((MathUtils.RANDOM.nextFloat() * (this.mMaxLifeTime - this.mMinLifeTime)) + this.mMinLifeTime);
    }
}
