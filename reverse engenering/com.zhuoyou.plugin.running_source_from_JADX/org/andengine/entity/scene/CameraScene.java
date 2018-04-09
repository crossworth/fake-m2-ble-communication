package org.andengine.entity.scene;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.shape.IAreaShape;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.util.GLState;

public class CameraScene extends Scene {
    protected Camera mCamera;

    public CameraScene() {
        this(null);
    }

    public CameraScene(Camera pCamera) {
        this.mCamera = pCamera;
    }

    public Camera getCamera() {
        return this.mCamera;
    }

    public void setCamera(Camera pCamera) {
        this.mCamera = pCamera;
    }

    public boolean onSceneTouchEvent(TouchEvent pSceneTouchEvent) {
        if (this.mCamera == null) {
            return false;
        }
        this.mCamera.convertSceneToCameraSceneTouchEvent(pSceneTouchEvent);
        if (super.onSceneTouchEvent(pSceneTouchEvent)) {
            return true;
        }
        this.mCamera.convertCameraSceneToSceneTouchEvent(pSceneTouchEvent);
        return false;
    }

    protected boolean onChildSceneTouchEvent(TouchEvent pSceneTouchEvent) {
        if (!(this.mChildScene instanceof CameraScene)) {
            return super.onChildSceneTouchEvent(pSceneTouchEvent);
        }
        this.mCamera.convertCameraSceneToSceneTouchEvent(pSceneTouchEvent);
        boolean result = super.onChildSceneTouchEvent(pSceneTouchEvent);
        this.mCamera.convertSceneToCameraSceneTouchEvent(pSceneTouchEvent);
        return result;
    }

    protected void onApplyMatrix(GLState pGLState, Camera pCamera) {
        this.mCamera.onApplyCameraSceneMatrix(pGLState);
    }

    public void centerShapeInCamera(IAreaShape pAreaShape) {
        Camera camera = this.mCamera;
        pAreaShape.setPosition((camera.getWidth() - pAreaShape.getWidth()) * 0.5f, (camera.getHeight() - pAreaShape.getHeight()) * 0.5f);
    }

    public void centerShapeInCameraHorizontally(IAreaShape pAreaShape) {
        pAreaShape.setPosition((this.mCamera.getWidth() - pAreaShape.getWidth()) * 0.5f, pAreaShape.getY());
    }

    public void centerShapeInCameraVertically(IAreaShape pAreaShape) {
        pAreaShape.setPosition(pAreaShape.getX(), (this.mCamera.getHeight() - pAreaShape.getHeight()) * 0.5f);
    }
}
