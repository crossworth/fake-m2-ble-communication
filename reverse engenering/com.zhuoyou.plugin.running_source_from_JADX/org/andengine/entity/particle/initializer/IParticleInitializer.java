package org.andengine.entity.particle.initializer;

import org.andengine.entity.IEntity;
import org.andengine.entity.particle.Particle;

public interface IParticleInitializer<T extends IEntity> {
    void onInitializeParticle(Particle<T> particle);
}
