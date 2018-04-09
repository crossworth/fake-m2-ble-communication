package org.andengine.engine.camera;

public class BoundCamera extends Camera {
    protected float mBoundsCenterX;
    protected float mBoundsCenterY;
    protected boolean mBoundsEnabled;
    protected float mBoundsHeight;
    protected float mBoundsWidth;
    protected float mBoundsXMax;
    protected float mBoundsXMin;
    protected float mBoundsYMax;
    protected float mBoundsYMin;

    public BoundCamera(float pX, float pY, float pWidth, float pHeight) {
        super(pX, pY, pWidth, pHeight);
    }

    public BoundCamera(float pX, float pY, float pWidth, float pHeight, float pBoundMinX, float pBoundMaxX, float pBoundMinY, float pBoundMaxY) {
        super(pX, pY, pWidth, pHeight);
        setBounds(pBoundMinX, pBoundMinY, pBoundMaxX, pBoundMaxY);
        this.mBoundsEnabled = true;
    }

    public boolean isBoundsEnabled() {
        return this.mBoundsEnabled;
    }

    public void setBoundsEnabled(boolean pBoundsEnabled) {
        this.mBoundsEnabled = pBoundsEnabled;
    }

    public void setBounds(float pBoundsXMin, float pBoundsYMin, float pBoundsXMax, float pBoundsYMax) {
        this.mBoundsXMin = pBoundsXMin;
        this.mBoundsXMax = pBoundsXMax;
        this.mBoundsYMin = pBoundsYMin;
        this.mBoundsYMax = pBoundsYMax;
        this.mBoundsWidth = this.mBoundsXMax - this.mBoundsXMin;
        this.mBoundsHeight = this.mBoundsYMax - this.mBoundsYMin;
        this.mBoundsCenterX = this.mBoundsXMin + (this.mBoundsWidth * 0.5f);
        this.mBoundsCenterY = this.mBoundsYMin + (this.mBoundsHeight * 0.5f);
    }

    public float getBoundsXMin() {
        return this.mBoundsXMin;
    }

    public float getBoundsXMax() {
        return this.mBoundsXMax;
    }

    public float getBoundsYMin() {
        return this.mBoundsYMin;
    }

    public float getBoundsYMax() {
        return this.mBoundsYMax;
    }

    public float getBoundsWidth() {
        return this.mBoundsWidth;
    }

    public float getBoundsHeight() {
        return this.mBoundsHeight;
    }

    public void setCenter(float pCenterX, float pCenterY) {
        super.setCenter(pCenterX, pCenterY);
        if (this.mBoundsEnabled) {
            ensureInBounds();
        }
    }

    protected void ensureInBounds() {
        float centerX;
        float centerY;
        if (this.mBoundsWidth < getWidth()) {
            centerX = this.mBoundsCenterX;
        } else {
            centerX = getBoundedX(getCenterX());
        }
        if (this.mBoundsHeight < getHeight()) {
            centerY = this.mBoundsCenterY;
        } else {
            centerY = getBoundedY(getCenterY());
        }
        super.setCenter(centerX, centerY);
    }

    protected float getBoundedX(float pX) {
        boolean minXBoundExceeded;
        boolean maxXBoundExceeded;
        float minXBoundExceededAmount = this.mBoundsXMin - getXMin();
        if (minXBoundExceededAmount > 0.0f) {
            minXBoundExceeded = true;
        } else {
            minXBoundExceeded = false;
        }
        float maxXBoundExceededAmount = getXMax() - this.mBoundsXMax;
        if (maxXBoundExceededAmount > 0.0f) {
            maxXBoundExceeded = true;
        } else {
            maxXBoundExceeded = false;
        }
        if (minXBoundExceeded) {
            if (maxXBoundExceeded) {
                return (pX - maxXBoundExceededAmount) + minXBoundExceededAmount;
            }
            return pX + minXBoundExceededAmount;
        } else if (maxXBoundExceeded) {
            return pX - maxXBoundExceededAmount;
        } else {
            return pX;
        }
    }

    protected float getBoundedY(float pY) {
        boolean minYBoundExceeded;
        boolean maxYBoundExceeded;
        float minYBoundExceededAmount = this.mBoundsYMin - getYMin();
        if (minYBoundExceededAmount > 0.0f) {
            minYBoundExceeded = true;
        } else {
            minYBoundExceeded = false;
        }
        float maxYBoundExceededAmount = getYMax() - this.mBoundsYMax;
        if (maxYBoundExceededAmount > 0.0f) {
            maxYBoundExceeded = true;
        } else {
            maxYBoundExceeded = false;
        }
        if (minYBoundExceeded) {
            if (maxYBoundExceeded) {
                return (pY - maxYBoundExceededAmount) + minYBoundExceededAmount;
            }
            return pY + minYBoundExceededAmount;
        } else if (maxYBoundExceeded) {
            return pY - maxYBoundExceededAmount;
        } else {
            return pY;
        }
    }
}
