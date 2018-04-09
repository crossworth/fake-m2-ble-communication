package org.andengine.entity.particle.initializer;

import org.andengine.entity.IEntity;
import org.andengine.entity.particle.Particle;

public class RotationParticleInitializer<T extends IEntity> extends BaseSingleValueParticleInitializer<T> {
    public RotationParticleInitializer(float pRotation) {
        this(pRotation, pRotation);
    }

    public RotationParticleInitializer(float pMinRotation, float pMaxRotation) {
        super(pMinRotation, pMaxRotation);
    }

    public float getMinRotation() {
        return this.mMinValue;
    }

    public float getMaxRotation() {
        return this.mMaxValue;
    }

    public void setRotation(float pRotation) {
        this.mMinValue = pRotation;
        this.mMaxValue = pRotation;
    }

    public void setRotation(float pMinRotation, float pMaxRotation) {
        this.mMinValue = pMinRotation;
        this.mMaxValue = pMaxRotation;
    }

    public void onInitializeParticle(Particle<T> pParticle, float pRotation) {
        pParticle.getEntity().setRotation(pRotation);
    }
}
