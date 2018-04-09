package org.andengine.entity.scene.background;

import java.util.ArrayList;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.shape.IAreaShape;
import org.andengine.opengl.util.GLState;

public class ParallaxBackground extends Background {
    private final ArrayList<ParallaxEntity> mParallaxEntities = new ArrayList();
    private int mParallaxEntityCount;
    protected float mParallaxValue;

    public static class ParallaxEntity {
        final IAreaShape mAreaShape;
        final float mParallaxFactor;

        public ParallaxEntity(float pParallaxFactor, IAreaShape pAreaShape) {
            this.mParallaxFactor = pParallaxFactor;
            this.mAreaShape = pAreaShape;
        }

        public void onDraw(GLState pGLState, Camera pCamera, float pParallaxValue) {
            pGLState.pushModelViewGLMatrix();
            float cameraWidth = pCamera.getWidth();
            float shapeWidthScaled = this.mAreaShape.getWidthScaled();
            float baseOffset = (this.mParallaxFactor * pParallaxValue) % shapeWidthScaled;
            while (baseOffset > 0.0f) {
                baseOffset -= shapeWidthScaled;
            }
            pGLState.translateModelViewGLMatrixf(baseOffset, 0.0f, 0.0f);
            float currentMaxX = baseOffset;
            do {
                this.mAreaShape.onDraw(pGLState, pCamera);
                pGLState.translateModelViewGLMatrixf(shapeWidthScaled, 0.0f, 0.0f);
                currentMaxX += shapeWidthScaled;
            } while (currentMaxX < cameraWidth);
            pGLState.popModelViewGLMatrix();
        }
    }

    public ParallaxBackground(float pRed, float pGreen, float pBlue) {
        super(pRed, pGreen, pBlue);
    }

    public void setParallaxValue(float pParallaxValue) {
        this.mParallaxValue = pParallaxValue;
    }

    public void onDraw(GLState pGLState, Camera pCamera) {
        super.onDraw(pGLState, pCamera);
        float parallaxValue = this.mParallaxValue;
        ArrayList<ParallaxEntity> parallaxEntities = this.mParallaxEntities;
        for (int i = 0; i < this.mParallaxEntityCount; i++) {
            ((ParallaxEntity) parallaxEntities.get(i)).onDraw(pGLState, pCamera, parallaxValue);
        }
    }

    public void attachParallaxEntity(ParallaxEntity pParallaxEntity) {
        this.mParallaxEntities.add(pParallaxEntity);
        this.mParallaxEntityCount++;
    }

    public boolean detachParallaxEntity(ParallaxEntity pParallaxEntity) {
        this.mParallaxEntityCount--;
        boolean success = this.mParallaxEntities.remove(pParallaxEntity);
        if (!success) {
            this.mParallaxEntityCount++;
        }
        return success;
    }
}
