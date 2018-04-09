package org.andengine.entity.particle.initializer;

import org.andengine.entity.IEntity;

public class GravityParticleInitializer<T extends IEntity> extends AccelerationParticleInitializer<T> {
    public GravityParticleInitializer() {
        super(0.0f, 9.80665f);
    }
}
