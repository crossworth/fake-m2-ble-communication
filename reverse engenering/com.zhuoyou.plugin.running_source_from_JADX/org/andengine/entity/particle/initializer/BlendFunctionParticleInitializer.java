package org.andengine.entity.particle.initializer;

import org.andengine.entity.particle.Particle;
import org.andengine.entity.shape.IShape;

public class BlendFunctionParticleInitializer<T extends IShape> implements IParticleInitializer<T> {
    protected int mBlendFunctionDestination;
    protected int mBlendFunctionSource;

    public BlendFunctionParticleInitializer(int pBlendFunctionSource, int pBlendFunctionDestination) {
        this.mBlendFunctionSource = pBlendFunctionSource;
        this.mBlendFunctionDestination = pBlendFunctionDestination;
    }

    public void onInitializeParticle(Particle<T> pParticle) {
        ((IShape) pParticle.getEntity()).setBlendFunction(this.mBlendFunctionSource, this.mBlendFunctionDestination);
    }
}
