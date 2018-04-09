package org.andengine.util.adt.bounds;

import org.andengine.util.adt.spatial.bounds.util.IntBoundsUtils;

public class IntBounds implements IIntBounds {
    private int mXMax;
    private int mXMin;
    private int mYMax;
    private int mYMin;

    public IntBounds(int pX, int pY) {
        set(pX, pY);
    }

    public IntBounds(int pXMin, int pYMin, int pXMax, int pYMax) {
        set(pXMin, pYMin, pXMax, pYMax);
    }

    public int getXMin() {
        return this.mXMin;
    }

    public int getYMin() {
        return this.mYMin;
    }

    public int getXMax() {
        return this.mXMax;
    }

    public int getYMax() {
        return this.mYMax;
    }

    public void set(int pX, int pY) {
        set(pX, pY, pX, pY);
    }

    public void set(int pXMin, int pYMin, int pXMax, int pYMax) {
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

    public boolean contains(int pX, int pY) {
        return IntBoundsUtils.contains(this.mXMin, this.mYMin, this.mXMax, this.mYMax, pX, pY);
    }
}
