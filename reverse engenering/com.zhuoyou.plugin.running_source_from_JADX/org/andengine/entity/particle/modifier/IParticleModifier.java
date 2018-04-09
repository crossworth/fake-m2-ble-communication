package org.andengine.entity.particle.modifier;

import org.andengine.entity.IEntity;
import org.andengine.entity.particle.Particle;
import org.andengine.entity.particle.initializer.IParticleInitializer;

public interface IParticleModifier<T extends IEntity> extends IParticleInitializer<T> {
    void onUpdateParticle(Particle<T> particle);
}
