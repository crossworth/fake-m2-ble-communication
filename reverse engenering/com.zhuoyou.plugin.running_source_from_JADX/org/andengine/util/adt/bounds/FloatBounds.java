package org.andengine.util.adt.bounds;

import org.andengine.util.adt.spatial.bounds.util.FloatBoundsUtils;

public class FloatBounds implements IFloatBounds {
    private float mXMax;
    private float mXMin;
    private float mYMax;
    private float mYMin;

    public FloatBounds(float pX, float pY) {
        set(pX, pY);
    }

    public FloatBounds(float pXMin, float pYMin, float pXMax, float pYMax) {
        set(pXMin, pYMin, pXMax, pYMax);
    }

    public float getXMin() {
        return this.mXMin;
    }

    public float getYMin() {
        return this.mYMin;
    }

    public float getXMax() {
        return this.mXMax;
    }

    public float getYMax() {
        return this.mYMax;
    }

    public void set(float pX, float pY) {
        set(pX, pY, pX, pY);
    }

    public void set(float pXMin, float pYMin, float pXMax, float pYMax) {
        this.mXMin = pXMin;
        this.mYMin = pYMin;
        this.mXMax = pXMax;
        this.mYMax = pYMax;
        if (pXMin > pXMax) {
            throw new IllegalArgumentException("pXMin: '" + pXMin + "' must be smaller or equal to pXMax: '" + pXMax + "'.");
        } else if (pYMin > pYMax) {
            throw new IllegalArgumentException("pYMin: '" + pYMin + "' must be smaller or equal to pYMax: '" + pYMax + "'.");
        }
    }

    public boolean contains(float pX, float pY) {
        return FloatBoundsUtils.contains(this.mXMin, this.mYMin, this.mXMax, this.mYMax, pX, pY);
    }
}
