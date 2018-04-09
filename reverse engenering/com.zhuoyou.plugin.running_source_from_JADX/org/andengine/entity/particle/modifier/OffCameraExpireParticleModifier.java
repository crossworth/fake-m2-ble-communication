package org.andengine.entity.particle.modifier;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.particle.Particle;
import org.andengine.entity.shape.RectangularShape;

public class OffCameraExpireParticleModifier<T extends RectangularShape> implements IParticleModifier<T> {
    private final Camera mCamera;

    public OffCameraExpireParticleModifier(Camera pCamera) {
        this.mCamera = pCamera;
    }

    public Camera getCamera() {
        return this.mCamera;
    }

    public void onInitializeParticle(Particle<T> particle) {
    }

    public void onUpdateParticle(Particle<T> pParticle) {
        if (!this.mCamera.isRectangularShapeVisible((RectangularShape) pParticle.getEntity())) {
            pParticle.setExpired(true);
        }
    }
}
