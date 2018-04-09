package org.andengine.entity.particle.initializer;

import org.andengine.entity.IEntity;
import org.andengine.entity.particle.Particle;

public class VelocityParticleInitializer<T extends IEntity> extends BaseDoubleValueParticleInitializer<T> {
    public VelocityParticleInitializer(float pVelocity) {
        this(pVelocity, pVelocity, pVelocity, pVelocity);
    }

    public VelocityParticleInitializer(float pVelocityX, float pVelocityY) {
        this(pVelocityX, pVelocityX, pVelocityY, pVelocityY);
    }

    public VelocityParticleInitializer(float pMinVelocityX, float pMaxVelocityX, float pMinVelocityY, float pMaxVelocityY) {
        super(pMinVelocityX, pMaxVelocityX, pMinVelocityY, pMaxVelocityY);
    }

    public float getMinVelocityX() {
        return this.mMinValue;
    }

    public float getMaxVelocityX() {
        return this.mMaxValue;
    }

    public float getMinVelocityY() {
        return this.mMinValueB;
    }

    public float getMaxVelocityY() {
        return this.mMaxValueB;
    }

    public void setVelocityX(float pVelocityX) {
        this.mMinValue = pVelocityX;
        this.mMaxValue = pVelocityX;
    }

    public void setVelocityY(float pVelocityY) {
        this.mMinValueB = pVelocityY;
        this.mMaxValueB = pVelocityY;
    }

    public void setVelocityX(float pMinVelocityX, float pMaxVelocityX) {
        this.mMinValue = pMinVelocityX;
        this.mMaxValue = pMaxVelocityX;
    }

    public void setVelocityY(float pMinVelocityY, float pMaxVelocityY) {
        this.mMinValueB = pMinVelocityY;
        this.mMaxValueB = pMaxVelocityY;
    }

    public void setVelocity(float pMinVelocityX, float pMaxVelocityX, float pMinVelocityY, float pMaxVelocityY) {
        this.mMinValue = pMinVelocityX;
        this.mMaxValue = pMaxVelocityX;
        this.mMinValueB = pMinVelocityY;
        this.mMaxValueB = pMaxVelocityY;
    }

    public void onInitializeParticle(Particle<T> pParticle, float pVelocityX, float pVelocityY) {
        pParticle.getPhysicsHandler().setVelocity(pVelocityX, pVelocityY);
    }
}
