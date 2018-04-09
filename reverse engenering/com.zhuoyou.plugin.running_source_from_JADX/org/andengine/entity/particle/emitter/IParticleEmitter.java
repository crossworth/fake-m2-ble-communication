package org.andengine.entity.particle.emitter;

import org.andengine.engine.handler.IUpdateHandler;

public interface IParticleEmitter extends IUpdateHandler {
    void getPositionOffset(float[] fArr);
}
