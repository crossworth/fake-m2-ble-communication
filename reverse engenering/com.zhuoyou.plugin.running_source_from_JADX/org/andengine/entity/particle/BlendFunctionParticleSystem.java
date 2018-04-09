package org.andengine.entity.particle;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.IEntity;
import org.andengine.entity.IEntityFactory;
import org.andengine.entity.particle.emitter.IParticleEmitter;
import org.andengine.entity.shape.IShape;
import org.andengine.opengl.util.GLState;

public class BlendFunctionParticleSystem<T extends IEntity> extends ParticleSystem<T> {
    protected int mBlendFunctionDestination = 771;
    protected int mBlendFunctionSource = IShape.BLENDFUNCTION_SOURCE_DEFAULT;
    protected boolean mBlendingEnabled = true;

    public BlendFunctionParticleSystem(IEntityFactory<T> pEntityFactory, IParticleEmitter pParticleEmitter, float pRateMinimum, float pRateMaximum, int pParticlesMaximum) {
        super(pEntityFactory, pParticleEmitter, pRateMinimum, pRateMaximum, pParticlesMaximum);
    }

    public BlendFunctionParticleSystem(float pX, float pY, IEntityFactory<T> pEntityFactory, IParticleEmitter pParticleEmitter, float pRateMinimum, float pRateMaximum, int pParticlesMaximum) {
        super(pX, pY, pEntityFactory, pParticleEmitter, pRateMinimum, pRateMaximum, pParticlesMaximum);
    }

    public boolean isBlendingEnabled() {
        return this.mBlendingEnabled;
    }

    public void setBlendingEnabled(boolean pBlendingEnabled) {
        this.mBlendingEnabled = pBlendingEnabled;
    }

    public int getBlendFunctionSource() {
        return this.mBlendFunctionSource;
    }

    public void setBlendFunctionSource(int pBlendFunctionSource) {
        this.mBlendFunctionSource = pBlendFunctionSource;
    }

    public int getBlendFunctionDestination() {
        return this.mBlendFunctionDestination;
    }

    public void setBlendFunctionDestination(int pBlendFunctionDestination) {
        this.mBlendFunctionDestination = pBlendFunctionDestination;
    }

    public void setBlendFunction(int pBlendFunctionSource, int pBlendFunctionDestination) {
        this.mBlendFunctionSource = pBlendFunctionSource;
        this.mBlendFunctionDestination = pBlendFunctionDestination;
    }

    protected void preDraw(GLState pGLState, Camera pCamera) {
        if (this.mBlendingEnabled) {
            pGLState.enableBlend();
            pGLState.blendFunction(this.mBlendFunctionSource, this.mBlendFunctionDestination);
        }
    }

    protected void postDraw(GLState pGLState, Camera pCamera) {
        if (this.mBlendingEnabled) {
            pGLState.disableBlend();
        }
    }
}
