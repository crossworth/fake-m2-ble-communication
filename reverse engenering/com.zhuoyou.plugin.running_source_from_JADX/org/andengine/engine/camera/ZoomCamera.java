package org.andengine.engine.camera;

import org.andengine.input.touch.TouchEvent;
import org.andengine.util.math.MathUtils;

public class ZoomCamera extends BoundCamera {
    protected float mZoomFactor = 1.0f;

    public ZoomCamera(float pX, float pY, float pWidth, float pHeight) {
        super(pX, pY, pWidth, pHeight);
    }

    public float getZoomFactor() {
        return this.mZoomFactor;
    }

    public void setZoomFactor(float pZoomFactor) {
        this.mZoomFactor = pZoomFactor;
        if (this.mBoundsEnabled) {
            ensureInBounds();
        }
    }

    public float getXMin() {
        if (this.mZoomFactor == 1.0f) {
            return super.getXMin();
        }
        float centerX = getCenterX();
        return centerX - ((centerX - super.getXMin()) / this.mZoomFactor);
    }

    public float getXMax() {
        if (this.mZoomFactor == 1.0f) {
            return super.getXMax();
        }
        float centerX = getCenterX();
        return ((super.getXMax() - centerX) / this.mZoomFactor) + centerX;
    }

    public float getYMin() {
        if (this.mZoomFactor == 1.0f) {
            return super.getYMin();
        }
        float centerY = getCenterY();
        return centerY - ((centerY - super.getYMin()) / this.mZoomFactor);
    }

    public float getYMax() {
        if (this.mZoomFactor == 1.0f) {
            return super.getYMax();
        }
        float centerY = getCenterY();
        return ((super.getYMax() - centerY) / this.mZoomFactor) + centerY;
    }

    public float getWidth() {
        return super.getWidth() / this.mZoomFactor;
    }

    public float getHeight() {
        return super.getHeight() / this.mZoomFactor;
    }

    protected void applySceneToCameraSceneOffset(TouchEvent pSceneTouchEvent) {
        float zoomFactor = this.mZoomFactor;
        if (zoomFactor != 1.0f) {
            Camera.VERTICES_TMP[0] = pSceneTouchEvent.getX();
            Camera.VERTICES_TMP[1] = pSceneTouchEvent.getY();
            MathUtils.scaleAroundCenter(Camera.VERTICES_TMP, zoomFactor, zoomFactor, getCenterX(), getCenterY());
            pSceneTouchEvent.set(Camera.VERTICES_TMP[0], Camera.VERTICES_TMP[1]);
        }
        super.applySceneToCameraSceneOffset(pSceneTouchEvent);
    }

    protected void applySceneToCameraSceneOffset(float[] pSceneCoordinates) {
        float zoomFactor = this.mZoomFactor;
        if (zoomFactor != 1.0f) {
            MathUtils.scaleAroundCenter(pSceneCoordinates, zoomFactor, zoomFactor, getCenterX(), getCenterY());
        }
        super.applySceneToCameraSceneOffset(pSceneCoordinates);
    }

    protected void unapplySceneToCameraSceneOffset(TouchEvent pCameraSceneTouchEvent) {
        super.unapplySceneToCameraSceneOffset(pCameraSceneTouchEvent);
        float zoomFactor = this.mZoomFactor;
        if (zoomFactor != 1.0f) {
            Camera.VERTICES_TMP[0] = pCameraSceneTouchEvent.getX();
            Camera.VERTICES_TMP[1] = pCameraSceneTouchEvent.getY();
            MathUtils.revertScaleAroundCenter(Camera.VERTICES_TMP, zoomFactor, zoomFactor, getCenterX(), getCenterY());
            pCameraSceneTouchEvent.set(Camera.VERTICES_TMP[0], Camera.VERTICES_TMP[1]);
        }
    }

    protected void unapplySceneToCameraSceneOffset(float[] pCameraSceneCoordinates) {
        super.unapplySceneToCameraSceneOffset(pCameraSceneCoordinates);
        float zoomFactor = this.mZoomFactor;
        if (zoomFactor != 1.0f) {
            MathUtils.revertScaleAroundCenter(pCameraSceneCoordinates, zoomFactor, zoomFactor, getCenterX(), getCenterY());
        }
    }
}
