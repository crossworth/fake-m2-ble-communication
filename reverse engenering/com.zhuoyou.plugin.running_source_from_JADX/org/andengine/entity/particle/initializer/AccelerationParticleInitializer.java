package org.andengine.entity.particle.initializer;

import org.andengine.entity.IEntity;
import org.andengine.entity.particle.Particle;

public class AccelerationParticleInitializer<T extends IEntity> extends BaseDoubleValueParticleInitializer<T> {
    public AccelerationParticleInitializer(float pAcceleration) {
        this(pAcceleration, pAcceleration);
    }

    public AccelerationParticleInitializer(float pAccelerationX, float pAccelerationY) {
        this(pAccelerationX, pAccelerationX, pAccelerationY, pAccelerationY);
    }

    public AccelerationParticleInitializer(float pMinAccelerationX, float pMaxAccelerationX, float pMinAccelerationY, float pMaxAccelerationY) {
        super(pMinAccelerationX, pMaxAccelerationX, pMinAccelerationY, pMaxAccelerationY);
    }

    public float getMinAccelerationX() {
        return this.mMinValue;
    }

    public float getMaxAccelerationX() {
        return this.mMaxValue;
    }

    public float getMinAccelerationY() {
        return this.mMinValueB;
    }

    public float getMaxAccelerationY() {
        return this.mMaxValueB;
    }

    public void setAccelerationX(float pAccelerationX) {
        this.mMinValue = pAccelerationX;
        this.mMaxValue = pAccelerationX;
    }

    public void setAccelerationY(float pAccelerationY) {
        this.mMinValueB = pAccelerationY;
        this.mMaxValueB = pAccelerationY;
    }

    public void setAccelerationX(float pMinAccelerationX, float pMaxAccelerationX) {
        this.mMinValue = pMinAccelerationX;
        this.mMaxValue = pMaxAccelerationX;
    }

    public void setAccelerationY(float pMinAccelerationY, float pMaxAccelerationY) {
        this.mMinValueB = pMinAccelerationY;
        this.mMaxValueB = pMaxAccelerationY;
    }

    public void setAcceleration(float pMinAccelerationX, float pMaxAccelerationX, float pMinAccelerationY, float pMaxAccelerationY) {
        this.mMinValue = pMinAccelerationX;
        this.mMaxValue = pMaxAccelerationX;
        this.mMinValueB = pMinAccelerationY;
        this.mMaxValueB = pMaxAccelerationY;
    }

    public void onInitializeParticle(Particle<T> pParticle, float pAccelerationX, float pAccelerationY) {
        pParticle.getPhysicsHandler().accelerate(pAccelerationX, pAccelerationY);
    }
}
