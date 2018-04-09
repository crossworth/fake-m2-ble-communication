package org.andengine.util.adt.spatial.bounds.util;

import org.andengine.util.adt.bounds.IFloatBounds;

public final class FloatBoundsUtils {
    public static final boolean intersects(IFloatBounds pFloatBoundsA, IFloatBounds pFloatBoundsB) {
        return intersects(pFloatBoundsA.getXMin(), pFloatBoundsA.getYMin(), pFloatBoundsA.getXMax(), pFloatBoundsA.getYMax(), pFloatBoundsB.getXMin(), pFloatBoundsB.getYMin(), pFloatBoundsB.getXMax(), pFloatBoundsB.getYMax());
    }

    public static final boolean intersects(float pXMinA, float pYMinA, float pXMaxA, float pYMaxA, float pXMinB, float pYMinB, float pXMaxB, float pYMaxB) {
        return (pXMinA < pXMaxB && pXMinB < pXMaxA && pYMinA < pYMaxB && pYMinB < pYMaxA) || contains(pXMinA, pYMinA, pXMaxA, pYMaxA, pXMinB, pYMinB, pXMaxB, pYMaxB) || contains(pXMinB, pYMinB, pXMaxB, pYMaxB, pXMinA, pYMinA, pXMaxA, pYMaxA);
    }

    public static final boolean contains(IFloatBounds pFloatBounds, float pX, float pY) {
        return contains(pFloatBounds.getXMin(), pFloatBounds.getYMin(), pFloatBounds.getXMax(), pFloatBounds.getYMax(), pX, pY);
    }

    public static final boolean contains(IFloatBounds pFloatBoundsA, IFloatBounds pFloatBoundsB) {
        return contains(pFloatBoundsA.getXMin(), pFloatBoundsA.getYMin(), pFloatBoundsA.getXMax(), pFloatBoundsA.getYMax(), pFloatBoundsB.getXMin(), pFloatBoundsB.getYMin(), pFloatBoundsB.getXMax(), pFloatBoundsB.getYMax());
    }

    public static final boolean contains(IFloatBounds pFloatBounds, float pXMin, float pYMin, float pXMax, float pYMax) {
        return contains(pFloatBounds.getXMin(), pFloatBounds.getYMin(), pFloatBounds.getXMax(), pFloatBounds.getYMax(), pXMin, pYMin, pXMax, pYMax);
    }

    public static final boolean contains(float pXMin, float pYMin, float pXMax, float pYMax, float pX, float pY) {
        return pXMin <= pX && pYMin <= pY && pXMax >= pX && pYMax >= pY;
    }

    public static final boolean contains(float pXMinA, float pYMinA, float pXMaxA, float pYMaxA, float pXMinB, float pYMinB, float pXMaxB, float pYMaxB) {
        return pXMinA <= pXMinB && pYMinA <= pYMinB && pXMaxA >= pXMaxB && pYMaxA >= pYMaxB;
    }
}
