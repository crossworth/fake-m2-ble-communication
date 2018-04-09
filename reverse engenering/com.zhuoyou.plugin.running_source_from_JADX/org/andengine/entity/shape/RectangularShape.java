package org.andengine.entity.shape;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.primitive.Line;
import org.andengine.opengl.shader.ShaderProgram;
import org.andengine.util.algorithm.collision.RectangularShapeCollisionChecker;

public abstract class RectangularShape extends Shape implements IAreaShape {
    protected float mHeight;
    protected float mWidth;

    public RectangularShape(float pX, float pY, float pWidth, float pHeight, ShaderProgram pShaderProgram) {
        super(pX, pY, pShaderProgram);
        this.mWidth = pWidth;
        this.mHeight = pHeight;
        resetRotationCenter();
        resetScaleCenter();
        resetSkewCenter();
    }

    public float getWidth() {
        return this.mWidth;
    }

    public float getHeight() {
        return this.mHeight;
    }

    public void setWidth(float pWidth) {
        this.mWidth = pWidth;
        onUpdateVertices();
    }

    public void setHeight(float pHeight) {
        this.mHeight = pHeight;
        onUpdateVertices();
    }

    public void setSize(float pWidth, float pHeight) {
        this.mWidth = pWidth;
        this.mHeight = pHeight;
        onUpdateVertices();
    }

    public float getWidthScaled() {
        return getWidth() * this.mScaleX;
    }

    public float getHeightScaled() {
        return getHeight() * this.mScaleY;
    }

    public boolean isCulled(Camera pCamera) {
        return !RectangularShapeCollisionChecker.isVisible(pCamera, this);
    }

    public void reset() {
        super.reset();
        resetRotationCenter();
        resetSkewCenter();
        resetScaleCenter();
    }

    public boolean contains(float pX, float pY) {
        return RectangularShapeCollisionChecker.checkContains(this, pX, pY);
    }

    public float[] getSceneCenterCoordinates() {
        return convertLocalToSceneCoordinates(this.mWidth * 0.5f, this.mHeight * 0.5f);
    }

    public float[] getSceneCenterCoordinates(float[] pReuse) {
        return convertLocalToSceneCoordinates(this.mWidth * 0.5f, this.mHeight * 0.5f, pReuse);
    }

    public boolean collidesWith(IShape pOtherShape) {
        if (pOtherShape instanceof RectangularShape) {
            return RectangularShapeCollisionChecker.checkCollision(this, (RectangularShape) pOtherShape);
        }
        if (pOtherShape instanceof Line) {
            return RectangularShapeCollisionChecker.checkCollision(this, (Line) pOtherShape);
        }
        return false;
    }

    public void resetRotationCenter() {
        this.mRotationCenterX = this.mWidth * 0.5f;
        this.mRotationCenterY = this.mHeight * 0.5f;
    }

    public void resetScaleCenter() {
        this.mScaleCenterX = this.mWidth * 0.5f;
        this.mScaleCenterY = this.mHeight * 0.5f;
    }

    public void resetSkewCenter() {
        this.mSkewCenterX = this.mWidth * 0.5f;
        this.mSkewCenterY = this.mHeight * 0.5f;
    }
}
