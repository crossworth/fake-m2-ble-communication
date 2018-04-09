package org.andengine.util.adt.spatial.quadtree;

import java.util.ArrayList;
import java.util.List;
import org.andengine.util.IMatcher;
import org.andengine.util.adt.bounds.BoundsSplit;
import org.andengine.util.adt.bounds.BoundsSplit.BoundsSplitException;
import org.andengine.util.adt.bounds.IIntBounds;
import org.andengine.util.adt.bounds.IntBounds;
import org.andengine.util.adt.spatial.ISpatialItem;
import org.andengine.util.adt.spatial.bounds.util.IntBoundsUtils;
import org.andengine.util.adt.spatial.quadtree.QuadTree.QuadTreeNode;

public class IntQuadTree<T extends ISpatialItem<IIntBounds>> extends QuadTree<IIntBounds, T> implements IIntBounds {
    private final IntBounds mQueryIntBounds = new IntBounds(0, 0, 0, 0);

    public class IntQuadTreeNode extends QuadTreeNode implements IIntBounds {
        private final int mXMax;
        private final int mXMin;
        private final int mYMax;
        private final int mYMin;

        public IntQuadTreeNode(IntQuadTree this$0, int pLevel, IIntBounds pIntBounds) {
            this(pLevel, pIntBounds.getXMin(), pIntBounds.getYMin(), pIntBounds.getXMax(), pIntBounds.getYMax());
        }

        public IntQuadTreeNode(int pLevel, int pXMin, int pYMin, int pXMax, int pYMax) {
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

        public int getWidth() {
            return (this.mXMax - this.mXMin) + 1;
        }

        public int getHeight() {
            return (this.mYMax - this.mYMin) + 1;
        }

        protected IntQuadTreeNode split(BoundsSplit pBoundsSplit) {
            int width = getWidth();
            int height = getHeight();
            if (width > 2 || height > 2) {
                return new IntQuadTreeNode(this.mLevel + 1, getXMin(pBoundsSplit), getYMin(pBoundsSplit), getXMax(pBoundsSplit), getYMax(pBoundsSplit));
            }
            throw new BoundsSplitException();
        }

        protected boolean contains(IIntBounds pIntBounds) {
            return contains(pIntBounds.getXMin(), pIntBounds.getYMin(), pIntBounds.getXMax(), pIntBounds.getYMax());
        }

        protected boolean contains(BoundsSplit pBoundsSplit, IIntBounds pIntBounds) {
            return IntBoundsUtils.contains(getXMin(pBoundsSplit), getYMin(pBoundsSplit), getXMax(pBoundsSplit), getYMax(pBoundsSplit), pIntBounds.getXMin(), pIntBounds.getYMin(), pIntBounds.getXMax(), pIntBounds.getYMax());
        }

        protected boolean intersects(IIntBounds pIntBounds) {
            return IntBoundsUtils.intersects(this.mXMin, this.mYMin, this.mXMax, this.mYMax, pIntBounds.getXMin(), pIntBounds.getYMin(), pIntBounds.getXMax(), pIntBounds.getYMax());
        }

        protected boolean intersects(IIntBounds pIntBoundsA, IIntBounds pIntBoundsB) {
            return IntBoundsUtils.intersects(pIntBoundsA, pIntBoundsB);
        }

        protected boolean containedBy(IIntBounds pBounds) {
            return IntBoundsUtils.contains(pBounds.getXMin(), pBounds.getYMin(), pBounds.getXMax(), pBounds.getYMax(), this.mXMin, this.mYMin, this.mXMax, this.mYMax);
        }

        protected void appendBoundsToString(StringBuilder pStringBuilder) {
            pStringBuilder.append("[XMin: ").append(this.mXMin).append(", YMin: ").append(this.mYMin).append(", XMax: ").append(this.mXMax).append(", YMax: ").append(this.mYMax).append("]");
        }

        private int getXMin(BoundsSplit pBoundsSplit) {
            int width = getWidth();
            int halfWidth = width / 2;
            if (width <= 2) {
                switch (pBoundsSplit) {
                    case TOP_LEFT:
                    case BOTTOM_LEFT:
                        return this.mXMin;
                    case BOTTOM_RIGHT:
                    case TOP_RIGHT:
                        throw new BoundsSplitException();
                    default:
                        throw new IllegalArgumentException("Unexpected " + BoundsSplit.class.getSimpleName() + ": '" + pBoundsSplit + "'.");
                }
            }
            switch (pBoundsSplit) {
                case TOP_LEFT:
                    return this.mXMin;
                case BOTTOM_LEFT:
                    return this.mXMin;
                case BOTTOM_RIGHT:
                    return this.mXMin + halfWidth;
                case TOP_RIGHT:
                    return this.mXMin + halfWidth;
                default:
                    throw new IllegalArgumentException("Unexpected " + BoundsSplit.class.getSimpleName() + ": '" + pBoundsSplit + "'.");
            }
        }

        private int getYMin(BoundsSplit pBoundsSplit) {
            int height = getHeight();
            int halfHeight = height / 2;
            if (height <= 2) {
                switch (pBoundsSplit) {
                    case TOP_LEFT:
                    case TOP_RIGHT:
                        return this.mYMin;
                    case BOTTOM_LEFT:
                    case BOTTOM_RIGHT:
                        throw new BoundsSplitException();
                    default:
                        throw new IllegalArgumentException("Unexpected " + BoundsSplit.class.getSimpleName() + ": '" + pBoundsSplit + "'.");
                }
            }
            switch (pBoundsSplit) {
                case TOP_LEFT:
                    return this.mYMin;
                case BOTTOM_LEFT:
                    return this.mYMin + halfHeight;
                case BOTTOM_RIGHT:
                    return this.mYMin + halfHeight;
                case TOP_RIGHT:
                    return this.mYMin;
                default:
                    throw new IllegalArgumentException("Unexpected " + BoundsSplit.class.getSimpleName() + ": '" + pBoundsSplit + "'.");
            }
        }

        private int getXMax(BoundsSplit pBoundsSplit) {
            int width = getWidth();
            int halfWidth = width / 2;
            if (width <= 2) {
                switch (pBoundsSplit) {
                    case TOP_LEFT:
                    case BOTTOM_LEFT:
                        return this.mXMax;
                    case BOTTOM_RIGHT:
                    case TOP_RIGHT:
                        throw new BoundsSplitException();
                    default:
                        throw new IllegalArgumentException("Unexpected " + BoundsSplit.class.getSimpleName() + ": '" + pBoundsSplit + "'.");
                }
            }
            switch (pBoundsSplit) {
                case TOP_LEFT:
                    return this.mXMin + halfWidth;
                case BOTTOM_LEFT:
                    return this.mXMin + halfWidth;
                case BOTTOM_RIGHT:
                    return this.mXMax;
                case TOP_RIGHT:
                    return this.mXMax;
                default:
                    throw new IllegalArgumentException("Unexpected " + BoundsSplit.class.getSimpleName() + ": '" + pBoundsSplit + "'.");
            }
        }

        private int getYMax(BoundsSplit pBoundsSplit) {
            int height = getHeight();
            int halfHeight = height / 2;
            if (height <= 2) {
                switch (pBoundsSplit) {
                    case TOP_LEFT:
                    case TOP_RIGHT:
                        return this.mYMax;
                    case BOTTOM_LEFT:
                    case BOTTOM_RIGHT:
                        throw new BoundsSplitException();
                    default:
                        throw new IllegalArgumentException("Unexpected " + BoundsSplit.class.getSimpleName() + ": '" + pBoundsSplit + "'.");
                }
            }
            switch (pBoundsSplit) {
                case TOP_LEFT:
                    return this.mYMin + halfHeight;
                case BOTTOM_LEFT:
                    return this.mYMax;
                case BOTTOM_RIGHT:
                    return this.mYMax;
                case TOP_RIGHT:
                    return this.mYMin + halfHeight;
                default:
                    throw new IllegalArgumentException("Unexpected " + BoundsSplit.class.getSimpleName() + ": '" + pBoundsSplit + "'.");
            }
        }

        public boolean intersects(int pXMin, int pYMin, int pXMax, int pYMax) {
            return IntBoundsUtils.intersects(this.mXMin, this.mYMin, this.mXMax, this.mYMax, pXMin, pYMin, pXMax, pYMax);
        }

        public boolean contains(int pXMin, int pYMin, int pXMax, int pYMax) {
            return IntBoundsUtils.contains(this.mXMin, this.mYMin, this.mXMax, this.mYMax, pXMin, pYMin, pXMax, pYMax);
        }
    }

    public IntQuadTree(IIntBounds pIntBounds) {
        super(pIntBounds);
    }

    public IntQuadTree(int pXMin, int pYMin, int pXMax, int pYMax) {
        super(new IntBounds(pXMin, pYMin, pXMax, pYMax));
    }

    public IntQuadTree(IIntBounds pIntBounds, int pMaxLevel) {
        super(pIntBounds, pMaxLevel);
    }

    public IntQuadTree(int pXMin, int pYMin, int pXMax, int pYMax, int pMaxLevel) {
        super(new IntBounds(pXMin, pYMin, pXMax, pYMax), pMaxLevel);
    }

    protected IntQuadTreeNode initRoot(IIntBounds pIntBounds) {
        return new IntQuadTreeNode(this, 0, pIntBounds);
    }

    public int getXMin() {
        return getRoot().getXMin();
    }

    public int getYMin() {
        return getRoot().getYMin();
    }

    public int getXMax() {
        return getRoot().getXMax();
    }

    public int getYMax() {
        return getRoot().getYMax();
    }

    protected IntQuadTreeNode getRoot() {
        return (IntQuadTreeNode) this.mRoot;
    }

    public synchronized ArrayList<T> query(int pX, int pY) {
        this.mQueryIntBounds.set(pX, pY);
        return query(this.mQueryIntBounds);
    }

    public synchronized <L extends List<T>> L query(int pX, int pY, L pResult) {
        this.mQueryIntBounds.set(pX, pY);
        return query(this.mQueryIntBounds, (List) pResult);
    }

    public synchronized ArrayList<T> query(int pX, int pY, IMatcher<T> pMatcher) {
        this.mQueryIntBounds.set(pX, pY);
        return query(this.mQueryIntBounds, (IMatcher) pMatcher);
    }

    public synchronized <L extends List<T>> L query(int pX, int pY, IMatcher<T> pMatcher, L pResult) {
        this.mQueryIntBounds.set(pX, pY);
        return query(this.mQueryIntBounds, pMatcher, pResult);
    }

    public synchronized ArrayList<T> query(int pXMin, int pYMin, int pXMax, int pYMax) {
        this.mQueryIntBounds.set(pXMin, pYMin, pXMax, pYMax);
        return query(this.mQueryIntBounds);
    }

    public synchronized <L extends List<T>> L query(int pXMin, int pYMin, int pXMax, int pYMax, L pResult) {
        this.mQueryIntBounds.set(pXMin, pYMin, pXMax, pYMax);
        return query(this.mQueryIntBounds, (List) pResult);
    }

    public synchronized ArrayList<T> query(int pXMin, int pYMin, int pXMax, int pYMax, IMatcher<T> pMatcher) {
        this.mQueryIntBounds.set(pXMin, pYMin, pXMax, pYMax);
        return query(this.mQueryIntBounds, (IMatcher) pMatcher);
    }

    public synchronized <L extends List<T>> L query(int pXMin, int pYMin, int pXMax, int pYMax, IMatcher<T> pMatcher, L pResult) {
        this.mQueryIntBounds.set(pXMin, pYMin, pXMax, pYMax);
        return query(this.mQueryIntBounds, pMatcher, pResult);
    }

    public synchronized <L extends List<S>, S extends T> L queryForSubclass(int pX, int pY, IMatcher<T> pMatcher, L pResult) throws ClassCastException {
        this.mQueryIntBounds.set(pX, pY);
        return queryForSubclass(this.mQueryIntBounds, pMatcher, pResult);
    }

    public synchronized <L extends List<S>, S extends T> L queryForSubclass(int pXMin, int pYMin, int pXMax, int pYMax, IMatcher<T> pMatcher, L pResult) throws ClassCastException {
        this.mQueryIntBounds.set(pXMin, pYMin, pXMax, pYMax);
        return queryForSubclass(this.mQueryIntBounds, pMatcher, pResult);
    }

    public synchronized boolean containsAny(int pX, int pY) {
        this.mQueryIntBounds.set(pX, pY);
        return containsAny(this.mQueryIntBounds);
    }

    public synchronized boolean containsAny(int pXMin, int pYMin, int pXMax, int pYMax) {
        this.mQueryIntBounds.set(pXMin, pYMin, pXMax, pYMax);
        return containsAny(this.mQueryIntBounds);
    }

    public synchronized boolean containsAny(int pX, int pY, IMatcher<T> pMatcher) {
        this.mQueryIntBounds.set(pX, pY);
        return containsAny(this.mQueryIntBounds, pMatcher);
    }

    public synchronized boolean containsAny(int pXMin, int pYMin, int pXMax, int pYMax, IMatcher<T> pMatcher) {
        this.mQueryIntBounds.set(pXMin, pYMin, pXMax, pYMax);
        return containsAny(this.mQueryIntBounds, pMatcher);
    }
}
