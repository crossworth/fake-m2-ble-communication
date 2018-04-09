package org.andengine.util.adt.spatial.quadtree;

import java.util.ArrayList;
import java.util.List;
import org.andengine.util.IMatcher;
import org.andengine.util.adt.bounds.BoundsSplit;
import org.andengine.util.adt.bounds.FloatBounds;
import org.andengine.util.adt.bounds.IFloatBounds;
import org.andengine.util.adt.spatial.ISpatialItem;
import org.andengine.util.adt.spatial.bounds.util.FloatBoundsUtils;
import org.andengine.util.adt.spatial.quadtree.QuadTree.QuadTreeNode;

public class FloatQuadTree<T extends ISpatialItem<IFloatBounds>> extends QuadTree<IFloatBounds, T> implements IFloatBounds {
    private final FloatBounds mQueryFloatBounds = new FloatBounds(0.0f, 0.0f, 0.0f, 0.0f);

    public class FloatQuadTreeNode extends QuadTreeNode implements IFloatBounds {
        private final float mXMax;
        private final float mXMin;
        private final float mYMax;
        private final float mYMin;

        public FloatQuadTreeNode(FloatQuadTree this$0, int pLevel, IFloatBounds pFloatBounds) {
            this(pLevel, pFloatBounds.getXMin(), pFloatBounds.getYMin(), pFloatBounds.getXMax(), pFloatBounds.getYMax());
        }

        public FloatQuadTreeNode(int pLevel, float pXMin, float pYMin, float pXMax, float pYMax) {
            super(pLevel);
            this.mXMin = pXMin;
            this.mYMin = pYMin;
            this.mXMax = pXMax;
            this.mYMax = pYMax;
            if (pXMin > pXMax) {
                throw new IllegalArgumentException("pXMin must be smaller or equal to pXMax.");
            } else if (pYMin > pYMax) {
                throw new IllegalArgumentException("pYMin must be smaller or equal to pYMax.");
            }
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

        public float getWidth() {
            return this.mXMax - this.mXMin;
        }

        public float getHeight() {
            return this.mYMax - this.mYMin;
        }

        protected FloatQuadTreeNode split(BoundsSplit pBoundsSplit) {
            return new FloatQuadTreeNode(this.mLevel + 1, getXMin(pBoundsSplit), getYMin(pBoundsSplit), getXMax(pBoundsSplit), getYMax(pBoundsSplit));
        }

        protected boolean contains(IFloatBounds pFloatBounds) {
            return contains(pFloatBounds.getXMin(), pFloatBounds.getYMin(), pFloatBounds.getXMax(), pFloatBounds.getYMax());
        }

        protected boolean contains(BoundsSplit pBoundsSplit, IFloatBounds pFloatBounds) {
            return FloatBoundsUtils.contains(getXMin(pBoundsSplit), getYMin(pBoundsSplit), getXMax(pBoundsSplit), getYMax(pBoundsSplit), pFloatBounds.getXMin(), pFloatBounds.getYMin(), pFloatBounds.getXMax(), pFloatBounds.getYMax());
        }

        protected boolean intersects(IFloatBounds pFloatBounds) {
            return FloatBoundsUtils.intersects(this.mXMin, this.mYMin, this.mXMax, this.mYMax, pFloatBounds.getXMin(), pFloatBounds.getYMin(), pFloatBounds.getXMax(), pFloatBounds.getYMax());
        }

        protected boolean intersects(IFloatBounds pFloatBoundsA, IFloatBounds pFloatBoundsB) {
            return FloatBoundsUtils.intersects(pFloatBoundsA, pFloatBoundsB);
        }

        protected boolean containedBy(IFloatBounds pFloatBounds) {
            return FloatBoundsUtils.contains(pFloatBounds.getXMin(), pFloatBounds.getYMin(), pFloatBounds.getXMax(), pFloatBounds.getYMax(), this.mXMin, this.mYMin, this.mXMax, this.mYMax);
        }

        protected void appendBoundsToString(StringBuilder pStringBuilder) {
            pStringBuilder.append("[XMin: ").append(this.mXMin).append(", YMin: ").append(this.mYMin).append(", XMax: ").append(this.mXMax).append(", YMax: ").append(this.mYMax).append("]");
        }

        private float getXMin(BoundsSplit pBoundsSplit) {
            float halfWidth = getWidth() / 2.0f;
            switch (pBoundsSplit) {
                case TOP_LEFT:
                    return this.mXMin;
                case TOP_RIGHT:
                    return this.mXMin + halfWidth;
                case BOTTOM_LEFT:
                    return this.mXMin;
                case BOTTOM_RIGHT:
                    return this.mXMin + halfWidth;
                default:
                    throw new IllegalArgumentException("Unexpected " + BoundsSplit.class.getSimpleName() + ": '" + pBoundsSplit + "'.");
            }
        }

        private float getYMin(BoundsSplit pBoundsSplit) {
            float halfHeight = getHeight() / 2.0f;
            switch (pBoundsSplit) {
                case TOP_LEFT:
                    return this.mYMin;
                case TOP_RIGHT:
                    return this.mYMin;
                case BOTTOM_LEFT:
                    return this.mYMin + halfHeight;
                case BOTTOM_RIGHT:
                    return this.mYMin + halfHeight;
                default:
                    throw new IllegalArgumentException("Unexpected " + BoundsSplit.class.getSimpleName() + ": '" + pBoundsSplit + "'.");
            }
        }

        private float getXMax(BoundsSplit pBoundsSplit) {
            float halfWidth = getWidth() / 2.0f;
            switch (pBoundsSplit) {
                case TOP_LEFT:
                    return this.mXMin + halfWidth;
                case TOP_RIGHT:
                    return this.mXMax;
                case BOTTOM_LEFT:
                    return this.mXMin + halfWidth;
                case BOTTOM_RIGHT:
                    return this.mXMax;
                default:
                    throw new IllegalArgumentException("Unexpected " + BoundsSplit.class.getSimpleName() + ": '" + pBoundsSplit + "'.");
            }
        }

        private float getYMax(BoundsSplit pBoundsSplit) {
            float halfHeight = getHeight() / 2.0f;
            switch (pBoundsSplit) {
                case TOP_LEFT:
                    return this.mYMin + halfHeight;
                case TOP_RIGHT:
                    return this.mYMin + halfHeight;
                case BOTTOM_LEFT:
                    return this.mYMax;
                case BOTTOM_RIGHT:
                    return this.mYMax;
                default:
                    throw new IllegalArgumentException("Unexpected " + BoundsSplit.class.getSimpleName() + ": '" + pBoundsSplit + "'.");
            }
        }

        public boolean intersects(float pXMin, float pYMin, float pXMax, float pYMax) {
            return FloatBoundsUtils.intersects(this.mXMin, this.mYMin, this.mXMax, this.mYMax, pXMin, pYMin, pXMax, pYMax);
        }

        public boolean contains(float pXMin, float pYMin, float pXMax, float pYMax) {
            return FloatBoundsUtils.contains(this.mXMin, this.mYMin, this.mXMax, this.mYMax, pXMin, pYMin, pXMax, pYMax);
        }
    }

    public FloatQuadTree(IFloatBounds pFloatBounds) {
        super(pFloatBounds);
    }

    public FloatQuadTree(float pXMin, float pYMin, float pXMax, float pYMax) {
        super(new FloatBounds(pXMin, pYMin, pXMax, pYMax));
    }

    public FloatQuadTree(IFloatBounds pFloatBounds, int pMaxLevel) {
        super(pFloatBounds, pMaxLevel);
    }

    public FloatQuadTree(float pXMin, float pYMin, float pXMax, float pYMax, int pMaxLevel) {
        super(new FloatBounds(pXMin, pYMin, pXMax, pYMax), pMaxLevel);
    }

    protected FloatQuadTreeNode initRoot(IFloatBounds pFloatBounds) {
        return new FloatQuadTreeNode(this, 0, pFloatBounds);
    }

    public float getXMin() {
        return getRoot().getXMin();
    }

    public float getYMin() {
        return getRoot().getYMin();
    }

    public float getXMax() {
        return getRoot().getXMax();
    }

    public float getYMax() {
        return getRoot().getYMax();
    }

    protected FloatQuadTreeNode getRoot() {
        return (FloatQuadTreeNode) this.mRoot;
    }

    public synchronized ArrayList<T> query(float pX, float pY) {
        this.mQueryFloatBounds.set(pX, pY);
        return query(this.mQueryFloatBounds);
    }

    public synchronized <L extends List<T>> L query(float pX, float pY, L pResult) {
        this.mQueryFloatBounds.set(pX, pY);
        return query(this.mQueryFloatBounds, (List) pResult);
    }

    public synchronized ArrayList<T> query(float pX, float pY, IMatcher<T> pMatcher) {
        this.mQueryFloatBounds.set(pX, pY);
        return query(this.mQueryFloatBounds, (IMatcher) pMatcher);
    }

    public synchronized <L extends List<T>> L query(float pX, float pY, IMatcher<T> pMatcher, L pResult) {
        this.mQueryFloatBounds.set(pX, pY);
        return query(this.mQueryFloatBounds, pMatcher, pResult);
    }

    public synchronized ArrayList<T> query(float pXMin, float pYMin, float pXMax, float pYMax) {
        this.mQueryFloatBounds.set(pXMin, pYMin, pXMax, pYMax);
        return query(this.mQueryFloatBounds);
    }

    public synchronized <L extends List<T>> L query(float pXMin, float pYMin, float pXMax, float pYMax, L pResult) {
        this.mQueryFloatBounds.set(pXMin, pYMin, pXMax, pYMax);
        return query(this.mQueryFloatBounds, (List) pResult);
    }

    public synchronized ArrayList<T> query(float pXMin, float pYMin, float pXMax, float pYMax, IMatcher<T> pMatcher) {
        this.mQueryFloatBounds.set(pXMin, pYMin, pXMax, pYMax);
        return query(this.mQueryFloatBounds, (IMatcher) pMatcher);
    }

    public synchronized <L extends List<T>> L query(float pXMin, float pYMin, float pXMax, float pYMax, IMatcher<T> pMatcher, L pResult) {
        this.mQueryFloatBounds.set(pXMin, pYMin, pXMax, pYMax);
        return query(this.mQueryFloatBounds, pMatcher, pResult);
    }

    public synchronized <S extends T> List<S> queryForSubclass(float pX, float pY, IMatcher<T> pMatcher, List<S> pResult) throws ClassCastException {
        this.mQueryFloatBounds.set(pX, pY);
        return queryForSubclass(this.mQueryFloatBounds, pMatcher, pResult);
    }

    public synchronized <S extends T> List<S> queryForSubclass(float pXMin, float pYMin, float pXMax, float pYMax, IMatcher<T> pMatcher, List<S> pResult) throws ClassCastException {
        this.mQueryFloatBounds.set(pXMin, pYMin, pXMax, pYMax);
        return queryForSubclass(this.mQueryFloatBounds, pMatcher, pResult);
    }

    public synchronized boolean containsAny(float pX, float pY) {
        this.mQueryFloatBounds.set(pX, pY);
        return containsAny(this.mQueryFloatBounds);
    }

    public synchronized boolean containsAny(float pXMin, float pYMin, float pXMax, float pYMax) {
        this.mQueryFloatBounds.set(pXMin, pYMin, pXMax, pYMax);
        return containsAny(this.mQueryFloatBounds);
    }

    public synchronized boolean containsAny(float pX, float pY, IMatcher<T> pMatcher) {
        this.mQueryFloatBounds.set(pX, pY);
        return containsAny(this.mQueryFloatBounds, pMatcher);
    }

    public synchronized boolean containsAny(float pXMin, float pYMin, float pXMax, float pYMax, IMatcher<T> pMatcher) {
        this.mQueryFloatBounds.set(pXMin, pYMin, pXMax, pYMax);
        return containsAny(this.mQueryFloatBounds, pMatcher);
    }
}
