package org.andengine.util.adt.spatial.bounds.util;

import org.andengine.util.adt.bounds.IIntBounds;

public final class IntBoundsUtils {
    public static final boolean intersects(IIntBounds pIntBoundsA, IIntBounds pIntBoundsB) {
        return intersects(pIntBoundsA.getXMin(), pIntBoundsA.getYMin(), pIntBoundsA.getXMax(), pIntBoundsA.getYMax(), pIntBoundsB.getXMin(), pIntBoundsB.getYMin(), pIntBoundsB.getXMax(), pIntBoundsB.getYMax());
    }

    public static final boolean intersects(int pXMinA, int pYMinA, int pXMaxA, int pYMaxA, int pXMinB, int pYMinB, int pXMaxB, int pYMaxB) {
        return (pXMinA < pXMaxB && pXMinB < pXMaxA && pYMinA < pYMaxB && pYMinB < pYMaxA) || contains(pXMinA, pYMinA, pXMaxA, pYMaxA, pXMinB, pYMinB, pXMaxB, pYMaxB) || contains(pXMinB, pYMinB, pXMaxB, pYMaxB, pXMinA, pYMinA, pXMaxA, pYMaxA);
    }

    public static final boolean contains(IIntBounds pIntBoundsA, IIntBounds pIntBoundsB) {
        return contains(pIntBoundsA.getXMin(), pIntBoundsA.getYMin(), pIntBoundsA.getXMax(), pIntBoundsA.getYMax(), pIntBoundsB.getXMin(), pIntBoundsB.getYMin(), pIntBoundsB.getXMax(), pIntBoundsB.getYMax());
    }

    public static final boolean contains(IIntBounds pIntBounds, int pX, int pY) {
        return contains(pIntBounds.getXMin(), pIntBounds.getYMin(), pIntBounds.getXMax(), pIntBounds.getYMax(), pX, pY);
    }

    public static final boolean contains(IIntBounds pIntBounds, int pXMin, int pYMin, int pXMax, int pYMax) {
        return contains(pIntBounds.getXMin(), pIntBounds.getYMin(), pIntBounds.getXMax(), pIntBounds.getYMax(), pXMin, pYMin, pXMax, pYMax);
    }

    public static final boolean contains(int pXMin, int pYMin, int pXMax, int pYMax, int pX, int pY) {
        return pXMin <= pX && pYMin <= pY && pXMax >= pX && pYMax >= pY;
    }

    public static final boolean contains(int pXMinA, int pYMinA, int pXMaxA, int pYMaxA, int pXMinB, int pYMinB, int pXMaxB, int pYMaxB) {
        return pXMinA <= pXMinB && pYMinA <= pYMinB && pXMaxA >= pXMaxB && pYMaxA >= pYMaxB;
    }

    public static final boolean adjacent(IIntBounds pIntBoundsA, IIntBounds pIntBoundsB) {
        return adjacent(pIntBoundsA.getXMin(), pIntBoundsA.getYMin(), pIntBoundsA.getXMax(), pIntBoundsA.getYMax(), pIntBoundsB.getXMin(), pIntBoundsB.getYMin(), pIntBoundsB.getXMax(), pIntBoundsB.getYMax());
    }

    public static final boolean adjacent(int pXMinA, int pYMinA, int pXMaxA, int pYMaxA, int pXMinB, int pYMinB, int pXMaxB, int pYMaxB) {
        int width = Math.min(pXMaxA, pXMaxB) - Math.max(pXMinA, pXMinB);
        int height = Math.min(pYMaxA, pYMaxB) - Math.max(pYMinA, pYMinB);
        return (width == 0 && height > 0) || (height == 0 && width > 0);
    }
}
