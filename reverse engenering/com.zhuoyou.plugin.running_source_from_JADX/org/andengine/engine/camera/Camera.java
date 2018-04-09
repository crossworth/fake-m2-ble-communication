package org.andengine.engine.camera;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.IUpdateHandler.IUpdateHandlerMatcher;
import org.andengine.engine.handler.UpdateHandlerList;
import org.andengine.entity.IEntity;
import org.andengine.entity.primitive.Line;
import org.andengine.entity.shape.RectangularShape;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.util.GLState;
import org.andengine.util.adt.transformation.Transformation;
import org.andengine.util.algorithm.collision.RectangularShapeCollisionChecker;
import org.andengine.util.math.MathUtils;

public class Camera implements IUpdateHandler {
    private static final int UPDATEHANDLERS_CAPACITY_DEFAULT = 4;
    static final float[] VERTICES_TMP = new float[2];
    protected float mCameraSceneRotation = 0.0f;
    private IEntity mChaseEntity;
    private HUD mHUD;
    protected boolean mResizeOnSurfaceSizeChanged;
    protected float mRotation = 0.0f;
    protected int mSurfaceHeight;
    protected int mSurfaceWidth;
    protected int mSurfaceX;
    protected int mSurfaceY;
    protected UpdateHandlerList mUpdateHandlers;
    protected float mXMax;
    protected float mXMin;
    protected float mYMax;
    protected float mYMin;
    private float mZFar = 1.0f;
    private float mZNear = -1.0f;

    public Camera(float pX, float pY, float pWidth, float pHeight) {
        set(pX, pY, pX + pWidth, pY + pHeight);
    }

    public float getXMin() {
        return this.mXMin;
    }

    public void setXMin(float pXMin) {
        this.mXMin = pXMin;
    }

    public float getXMax() {
        return this.mXMax;
    }

    public void setXMax(float pXMax) {
        this.mXMax = pXMax;
    }

    public float getYMin() {
        return this.mYMin;
    }

    public void setYMin(float pYMin) {
        this.mYMin = pYMin;
    }

    public float getYMax() {
        return this.mYMax;
    }

    public void setYMax(float pYMax) {
        this.mYMax = pYMax;
    }

    public void set(float pXMin, float pYMin, float pXMax, float pYMax) {
        this.mXMin = pXMin;
        this.mXMax = pXMax;
        this.mYMin = pYMin;
        this.mYMax = pYMax;
    }

    public float getZNear() {
        return this.mZNear;
    }

    public float getZFar() {
        return this.mZFar;
    }

    public void setZNear(float pZNear) {
        this.mZNear = pZNear;
    }

    public void setZFar(float pZFar) {
        this.mZFar = pZFar;
    }

    public void setZClippingPlanes(float pNearZClippingPlane, float pFarZClippingPlane) {
        this.mZNear = pNearZClippingPlane;
        this.mZFar = pFarZClippingPlane;
    }

    public float getWidth() {
        return this.mXMax - this.mXMin;
    }

    public float getHeight() {
        return this.mYMax - this.mYMin;
    }

    public float getWidthRaw() {
        return this.mXMax - this.mXMin;
    }

    public float getHeightRaw() {
        return this.mYMax - this.mYMin;
    }

    public float getCenterX() {
        return (this.mXMin + this.mXMax) * 0.5f;
    }

    public float getCenterY() {
        return (this.mYMin + this.mYMax) * 0.5f;
    }

    public void setCenter(float pCenterX, float pCenterY) {
        float dX = pCenterX - getCenterX();
        float dY = pCenterY - getCenterY();
        this.mXMin += dX;
        this.mXMax += dX;
        this.mYMin += dY;
        this.mYMax += dY;
    }

    public void offsetCenter(float pX, float pY) {
        setCenter(getCenterX() + pX, getCenterY() + pY);
    }

    public HUD getHUD() {
        return this.mHUD;
    }

    public void setHUD(HUD pHUD) {
        this.mHUD = pHUD;
        if (pHUD != null) {
            pHUD.setCamera(this);
        }
    }

    public boolean hasHUD() {
        return this.mHUD != null;
    }

    public void setChaseEntity(IEntity pChaseEntity) {
        this.mChaseEntity = pChaseEntity;
    }

    public boolean isRotated() {
        return this.mRotation != 0.0f;
    }

    public float getRotation() {
        return this.mRotation;
    }

    public void setRotation(float pRotation) {
        this.mRotation = pRotation;
    }

    public float getCameraSceneRotation() {
        return this.mCameraSceneRotation;
    }

    public void setCameraSceneRotation(float pCameraSceneRotation) {
        this.mCameraSceneRotation = pCameraSceneRotation;
    }

    public int getSurfaceX() {
        return this.mSurfaceX;
    }

    public int getSurfaceY() {
        return this.mSurfaceY;
    }

    public int getSurfaceWidth() {
        return this.mSurfaceWidth;
    }

    public int getSurfaceHeight() {
        return this.mSurfaceHeight;
    }

    public void setSurfaceSize(int pSurfaceX, int pSurfaceY, int pSurfaceWidth, int pSurfaceHeight) {
        if (this.mSurfaceHeight == 0 && this.mSurfaceWidth == 0) {
            onSurfaceSizeInitialized(pSurfaceX, pSurfaceY, pSurfaceWidth, pSurfaceHeight);
        } else if (this.mSurfaceWidth != pSurfaceWidth || this.mSurfaceHeight != pSurfaceHeight) {
            onSurfaceSizeChanged(this.mSurfaceX, this.mSurfaceY, this.mSurfaceWidth, this.mSurfaceHeight, pSurfaceX, pSurfaceY, pSurfaceWidth, pSurfaceHeight);
        }
    }

    public boolean isResizeOnSurfaceSizeChanged() {
        return this.mResizeOnSurfaceSizeChanged;
    }

    public void setResizeOnSurfaceSizeChanged(boolean pResizeOnSurfaceSizeChanged) {
        this.mResizeOnSurfaceSizeChanged = pResizeOnSurfaceSizeChanged;
    }

    public void onUpdate(float pSecondsElapsed) {
        if (this.mUpdateHandlers != null) {
            this.mUpdateHandlers.onUpdate(pSecondsElapsed);
        }
        if (this.mHUD != null) {
            this.mHUD.onUpdate(pSecondsElapsed);
        }
        updateChaseEntity();
    }

    public void reset() {
    }

    public void onDrawHUD(GLState pGLState) {
        if (this.mHUD != null) {
            this.mHUD.onDraw(pGLState, this);
        }
    }

    public void updateChaseEntity() {
        if (this.mChaseEntity != null) {
            float[] centerCoordinates = this.mChaseEntity.getSceneCenterCoordinates();
            setCenter(centerCoordinates[0], centerCoordinates[1]);
        }
    }

    public boolean isLineVisible(Line pLine) {
        return RectangularShapeCollisionChecker.isVisible(this, pLine);
    }

    public boolean isRectangularShapeVisible(RectangularShape pRectangularShape) {
        return RectangularShapeCollisionChecker.isVisible(this, pRectangularShape);
    }

    public boolean isRectangularShapeVisible(float pX, float pY, float pWidth, float pHeight, Transformation pLocalToSceneTransformation) {
        return RectangularShapeCollisionChecker.isVisible(this, pX, pY, pWidth, pHeight, pLocalToSceneTransformation);
    }

    public void onApplySceneMatrix(GLState pGLState) {
        pGLState.orthoProjectionGLMatrixf(getXMin(), getXMax(), getYMax(), getYMin(), this.mZNear, this.mZFar);
        float rotation = this.mRotation;
        if (rotation != 0.0f) {
            applyRotation(pGLState, getCenterX(), getCenterY(), rotation);
        }
    }

    public void onApplySceneBackgroundMatrix(GLState pGLState) {
        float widthRaw = getWidthRaw();
        float heightRaw = getHeightRaw();
        pGLState.orthoProjectionGLMatrixf(0.0f, widthRaw, heightRaw, 0.0f, this.mZNear, this.mZFar);
        float rotation = this.mRotation;
        if (rotation != 0.0f) {
            applyRotation(pGLState, widthRaw * 0.5f, heightRaw * 0.5f, rotation);
        }
    }

    public void onApplyCameraSceneMatrix(GLState pGLState) {
        float widthRaw = getWidthRaw();
        float heightRaw = getHeightRaw();
        pGLState.orthoProjectionGLMatrixf(0.0f, widthRaw, heightRaw, 0.0f, this.mZNear, this.mZFar);
        float cameraSceneRotation = this.mCameraSceneRotation;
        if (cameraSceneRotation != 0.0f) {
            applyRotation(pGLState, widthRaw * 0.5f, heightRaw * 0.5f, cameraSceneRotation);
        }
    }

    private static void applyRotation(GLState pGLState, float pRotationCenterX, float pRotationCenterY, float pAngle) {
        pGLState.translateProjectionGLMatrixf(pRotationCenterX, pRotationCenterY, 0.0f);
        pGLState.rotateProjectionGLMatrixf(pAngle, 0.0f, 0.0f, 1.0f);
        pGLState.translateProjectionGLMatrixf(-pRotationCenterX, -pRotationCenterY, 0.0f);
    }

    public void convertSceneToCameraSceneTouchEvent(TouchEvent pSceneTouchEvent) {
        unapplySceneRotation(pSceneTouchEvent);
        applySceneToCameraSceneOffset(pSceneTouchEvent);
        applyCameraSceneRotation(pSceneTouchEvent);
    }

    public float[] getCameraSceneCoordinatesFromSceneCoordinates(float pSceneX, float pSceneY) {
        VERTICES_TMP[0] = pSceneX;
        VERTICES_TMP[1] = pSceneY;
        return getCameraSceneCoordinatesFromSceneCoordinates(VERTICES_TMP);
    }

    public float[] getCameraSceneCoordinatesFromSceneCoordinates(float[] pSceneCoordinates) {
        unapplySceneRotation(pSceneCoordinates);
        applySceneToCameraSceneOffset(pSceneCoordinates);
        applyCameraSceneRotation(pSceneCoordinates);
        return pSceneCoordinates;
    }

    public void convertCameraSceneToSceneTouchEvent(TouchEvent pCameraSceneTouchEvent) {
        unapplyCameraSceneRotation(pCameraSceneTouchEvent);
        unapplySceneToCameraSceneOffset(pCameraSceneTouchEvent);
        applySceneRotation(pCameraSceneTouchEvent);
    }

    public float[] getSceneCoordinatesFromCameraSceneCoordinates(float pCameraSceneX, float pCameraSceneY) {
        VERTICES_TMP[0] = pCameraSceneX;
        VERTICES_TMP[1] = pCameraSceneY;
        return getSceneCoordinatesFromCameraSceneCoordinates(VERTICES_TMP);
    }

    public float[] getSceneCoordinatesFromCameraSceneCoordinates(float[] pCameraSceneCoordinates) {
        unapplyCameraSceneRotation(pCameraSceneCoordinates);
        unapplySceneToCameraSceneOffset(pCameraSceneCoordinates);
        applySceneRotation(pCameraSceneCoordinates);
        return pCameraSceneCoordinates;
    }

    protected void applySceneToCameraSceneOffset(TouchEvent pSceneTouchEvent) {
        pSceneTouchEvent.offset(-this.mXMin, -this.mYMin);
    }

    protected void applySceneToCameraSceneOffset(float[] pSceneCoordinates) {
        pSceneCoordinates[0] = pSceneCoordinates[0] - this.mXMin;
        pSceneCoordinates[1] = pSceneCoordinates[1] - this.mYMin;
    }

    protected void unapplySceneToCameraSceneOffset(TouchEvent pCameraSceneTouchEvent) {
        pCameraSceneTouchEvent.offset(this.mXMin, this.mYMin);
    }

    protected void unapplySceneToCameraSceneOffset(float[] pCameraSceneCoordinates) {
        pCameraSceneCoordinates[0] = pCameraSceneCoordinates[0] + this.mXMin;
        pCameraSceneCoordinates[1] = pCameraSceneCoordinates[1] + this.mYMin;
    }

    private void applySceneRotation(float[] pCameraSceneCoordinates) {
        float rotation = this.mRotation;
        if (rotation != 0.0f) {
            MathUtils.rotateAroundCenter(pCameraSceneCoordinates, -rotation, getCenterX(), getCenterY());
        }
    }

    private void applySceneRotation(TouchEvent pCameraSceneTouchEvent) {
        float rotation = this.mRotation;
        if (rotation != 0.0f) {
            VERTICES_TMP[0] = pCameraSceneTouchEvent.getX();
            VERTICES_TMP[1] = pCameraSceneTouchEvent.getY();
            MathUtils.rotateAroundCenter(VERTICES_TMP, -rotation, getCenterX(), getCenterY());
            pCameraSceneTouchEvent.set(VERTICES_TMP[0], VERTICES_TMP[1]);
        }
    }

    private void unapplySceneRotation(float[] pSceneCoordinates) {
        float rotation = this.mRotation;
        if (rotation != 0.0f) {
            MathUtils.revertRotateAroundCenter(pSceneCoordinates, rotation, getCenterX(), getCenterY());
        }
    }

    private void unapplySceneRotation(TouchEvent pSceneTouchEvent) {
        float rotation = this.mRotation;
        if (rotation != 0.0f) {
            VERTICES_TMP[0] = pSceneTouchEvent.getX();
            VERTICES_TMP[1] = pSceneTouchEvent.getY();
            MathUtils.revertRotateAroundCenter(VERTICES_TMP, rotation, getCenterX(), getCenterY());
            pSceneTouchEvent.set(VERTICES_TMP[0], VERTICES_TMP[1]);
        }
    }

    private void applyCameraSceneRotation(float[] pSceneCoordinates) {
        float cameraSceneRotation = -this.mCameraSceneRotation;
        if (cameraSceneRotation != 0.0f) {
            MathUtils.rotateAroundCenter(pSceneCoordinates, cameraSceneRotation, (this.mXMax - this.mXMin) * 0.5f, (this.mYMax - this.mYMin) * 0.5f);
        }
    }

    private void applyCameraSceneRotation(TouchEvent pSceneTouchEvent) {
        float cameraSceneRotation = -this.mCameraSceneRotation;
        if (cameraSceneRotation != 0.0f) {
            VERTICES_TMP[0] = pSceneTouchEvent.getX();
            VERTICES_TMP[1] = pSceneTouchEvent.getY();
            MathUtils.rotateAroundCenter(VERTICES_TMP, cameraSceneRotation, (this.mXMax - this.mXMin) * 0.5f, (this.mYMax - this.mYMin) * 0.5f);
            pSceneTouchEvent.set(VERTICES_TMP[0], VERTICES_TMP[1]);
        }
    }

    private void unapplyCameraSceneRotation(float[] pCameraSceneCoordinates) {
        float cameraSceneRotation = -this.mCameraSceneRotation;
        if (cameraSceneRotation != 0.0f) {
            MathUtils.revertRotateAroundCenter(pCameraSceneCoordinates, cameraSceneRotation, (this.mXMax - this.mXMin) * 0.5f, (this.mYMax - this.mYMin) * 0.5f);
        }
    }

    private void unapplyCameraSceneRotation(TouchEvent pCameraSceneTouchEvent) {
        float cameraSceneRotation = -this.mCameraSceneRotation;
        if (cameraSceneRotation != 0.0f) {
            VERTICES_TMP[0] = pCameraSceneTouchEvent.getX();
            VERTICES_TMP[1] = pCameraSceneTouchEvent.getY();
            MathUtils.revertRotateAroundCenter(VERTICES_TMP, cameraSceneRotation, (this.mXMax - this.mXMin) * 0.5f, (this.mYMax - this.mYMin) * 0.5f);
            pCameraSceneTouchEvent.set(VERTICES_TMP[0], VERTICES_TMP[1]);
        }
    }

    public void convertSurfaceToSceneTouchEvent(TouchEvent pSurfaceTouchEvent, int pSurfaceWidth, int pSurfaceHeight) {
        float relativeX;
        float relativeY;
        float surfaceTouchEventX = pSurfaceTouchEvent.getX();
        float surfaceTouchEventY = pSurfaceTouchEvent.getY();
        float rotation = this.mRotation;
        if (rotation == 0.0f) {
            relativeX = surfaceTouchEventX / ((float) pSurfaceWidth);
            relativeY = surfaceTouchEventY / ((float) pSurfaceHeight);
        } else if (rotation == 180.0f) {
            relativeX = 1.0f - (surfaceTouchEventX / ((float) pSurfaceWidth));
            relativeY = 1.0f - (surfaceTouchEventY / ((float) pSurfaceHeight));
        } else {
            VERTICES_TMP[0] = surfaceTouchEventX;
            VERTICES_TMP[1] = surfaceTouchEventY;
            MathUtils.rotateAroundCenter(VERTICES_TMP, rotation, (float) (pSurfaceWidth >> 1), (float) (pSurfaceHeight >> 1));
            relativeX = VERTICES_TMP[0] / ((float) pSurfaceWidth);
            relativeY = VERTICES_TMP[1] / ((float) pSurfaceHeight);
        }
        convertAxisAlignedSurfaceToSceneTouchEvent(pSurfaceTouchEvent, relativeX, relativeY);
    }

    private void convertAxisAlignedSurfaceToSceneTouchEvent(TouchEvent pSurfaceTouchEvent, float pRelativeX, float pRelativeY) {
        float xMin = getXMin();
        float xMax = getXMax();
        float yMin = getYMin();
        pSurfaceTouchEvent.set(xMin + ((xMax - xMin) * pRelativeX), yMin + ((getYMax() - yMin) * pRelativeY));
    }

    public void convertSceneToSurfaceTouchEvent(TouchEvent pSceneTouchEvent, int pSurfaceWidth, int pSurfaceHeight) {
        convertAxisAlignedSceneToSurfaceTouchEvent(pSceneTouchEvent, pSurfaceWidth, pSurfaceHeight);
        float rotation = this.mRotation;
        if (rotation != 0.0f) {
            if (rotation == 180.0f) {
                pSceneTouchEvent.set(((float) pSurfaceWidth) - pSceneTouchEvent.getX(), ((float) pSurfaceHeight) - pSceneTouchEvent.getY());
                return;
            }
            VERTICES_TMP[0] = pSceneTouchEvent.getX();
            VERTICES_TMP[1] = pSceneTouchEvent.getY();
            MathUtils.revertRotateAroundCenter(VERTICES_TMP, rotation, (float) (pSurfaceWidth >> 1), (float) (pSurfaceHeight >> 1));
            pSceneTouchEvent.set(VERTICES_TMP[0], VERTICES_TMP[1]);
        }
    }

    private void convertAxisAlignedSceneToSurfaceTouchEvent(TouchEvent pSceneTouchEvent, int pSurfaceWidth, int pSurfaceHeight) {
        float xMin = getXMin();
        float xMax = getXMax();
        float yMin = getYMin();
        pSceneTouchEvent.set(((float) pSurfaceWidth) * ((pSceneTouchEvent.getX() - xMin) / (xMax - xMin)), ((float) pSurfaceHeight) * ((pSceneTouchEvent.getY() - yMin) / (getYMax() - yMin)));
    }

    public void registerUpdateHandler(IUpdateHandler pUpdateHandler) {
        if (this.mUpdateHandlers == null) {
            allocateUpdateHandlers();
        }
        this.mUpdateHandlers.add(pUpdateHandler);
    }

    public boolean unregisterUpdateHandler(IUpdateHandler pUpdateHandler) {
        if (this.mUpdateHandlers == null) {
            return false;
        }
        return this.mUpdateHandlers.remove(pUpdateHandler);
    }

    public boolean unregisterUpdateHandlers(IUpdateHandlerMatcher pUpdateHandlerMatcher) {
        if (this.mUpdateHandlers == null) {
            return false;
        }
        return this.mUpdateHandlers.removeAll(pUpdateHandlerMatcher);
    }

    public void clearUpdateHandlers() {
        if (this.mUpdateHandlers != null) {
            this.mUpdateHandlers.clear();
        }
    }

    private void allocateUpdateHandlers() {
        this.mUpdateHandlers = new UpdateHandlerList(4);
    }

    protected void onSurfaceSizeInitialized(int pSurfaceX, int pSurfaceY, int pSurfaceWidth, int pSurfaceHeight) {
        this.mSurfaceX = pSurfaceX;
        this.mSurfaceY = pSurfaceY;
        this.mSurfaceWidth = pSurfaceWidth;
        this.mSurfaceHeight = pSurfaceHeight;
    }

    protected void onSurfaceSizeChanged(int pOldSurfaceX, int pOldSurfaceY, int pOldSurfaceWidth, int pOldSurfaceHeight, int pNewSurfaceX, int pNewSurfaceY, int pNewSurfaceWidth, int pNewSurfaceHeight) {
        if (this.mResizeOnSurfaceSizeChanged) {
            float surfaceWidthRatio = ((float) pNewSurfaceWidth) / ((float) pOldSurfaceWidth);
            float surfaceHeightRatio = ((float) pNewSurfaceHeight) / ((float) pOldSurfaceHeight);
            float centerX = getCenterX();
            float centerY = getCenterY();
            float newWidthRawHalf = (getWidthRaw() * surfaceWidthRatio) * 0.5f;
            float newHeightRawHalf = (getHeightRaw() * surfaceHeightRatio) * 0.5f;
            set(centerX - newWidthRawHalf, centerY - newHeightRawHalf, centerX + newWidthRawHalf, centerY + newHeightRawHalf);
        }
        this.mSurfaceX = pNewSurfaceX;
        this.mSurfaceY = pNewSurfaceY;
        this.mSurfaceWidth = pNewSurfaceWidth;
        this.mSurfaceHeight = pNewSurfaceHeight;
    }
}
