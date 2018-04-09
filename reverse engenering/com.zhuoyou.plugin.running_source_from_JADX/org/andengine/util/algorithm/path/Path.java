package org.andengine.util.algorithm.path;

public class Path {
    private final int[] mXs;
    private final int[] mYs;

    public Path(int pLength) {
        this.mXs = new int[pLength];
        this.mYs = new int[pLength];
    }

    public int getLength() {
        return this.mXs.length;
    }

    public int getFromX() {
        return getX(0);
    }

    public int getFromY() {
        return getY(0);
    }

    public int getToX() {
        return getX(getLength() - 1);
    }

    public int getToY() {
        return getY(getLength() - 1);
    }

    public int getX(int pIndex) {
        return this.mXs[pIndex];
    }

    public int getY(int pIndex) {
        return this.mYs[pIndex];
    }

    public void set(int pIndex, int pX, int pY) {
        this.mXs[pIndex] = pX;
        this.mYs[pIndex] = pY;
    }

    public boolean contains(int pX, int pY) {
        int[] xs = this.mXs;
        int[] ys = this.mYs;
        int i = getLength() - 1;
        while (i >= 0) {
            if (xs[i] == pX && ys[i] == pY) {
                return true;
            }
            i--;
        }
        return false;
    }

    public Direction getDirectionToPreviousStep(int pIndex) {
        if (pIndex == 0) {
            return null;
        }
        return Direction.fromDelta(getX(pIndex - 1) - getX(pIndex), getY(pIndex - 1) - getY(pIndex));
    }

    public Direction getDirectionToNextStep(int pIndex) {
        if (pIndex == getLength() - 1) {
            return null;
        }
        return Direction.fromDelta(getX(pIndex + 1) - getX(pIndex), getY(pIndex + 1) - getY(pIndex));
    }
}
