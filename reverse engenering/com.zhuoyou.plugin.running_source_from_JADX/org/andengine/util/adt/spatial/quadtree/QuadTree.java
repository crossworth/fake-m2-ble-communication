package org.andengine.util.adt.spatial.quadtree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.andengine.util.IMatcher;
import org.andengine.util.adt.bounds.BoundsSplit;
import org.andengine.util.adt.bounds.BoundsSplit.BoundsSplitException;
import org.andengine.util.adt.bounds.IBounds;
import org.andengine.util.adt.spatial.ISpatialItem;
import org.andengine.util.call.ParameterCallable;
import org.andengine.util.debug.Debug;
import org.andengine.util.exception.AndEngineRuntimeException;

public abstract class QuadTree<B extends IBounds, T extends ISpatialItem<B>> implements IBounds {
    protected static final int LEVEL_MAX_DEFAULT = 8;
    protected static final int LEVEL_ROOT = 0;
    protected final B mBounds;
    protected final int mMaxLevel;
    protected final QuadTreeNode mRoot;

    public abstract class QuadTreeNode implements IBounds {
        protected QuadTreeNode mBottomLeftChild;
        protected QuadTreeNode mBottomRightChild;
        protected List<T> mItems;
        protected final int mLevel;
        protected QuadTreeNode mTopLeftChild;
        protected QuadTreeNode mTopRightChild;

        protected abstract void appendBoundsToString(StringBuilder stringBuilder);

        protected abstract boolean containedBy(B b);

        protected abstract boolean contains(BoundsSplit boundsSplit, B b);

        protected abstract boolean contains(B b);

        protected abstract boolean intersects(B b);

        protected abstract boolean intersects(B b, B b2);

        protected abstract QuadTreeNode split(BoundsSplit boundsSplit);

        protected QuadTreeNode(int pLevel) {
            this.mLevel = pLevel;
        }

        public boolean hasChildren() {
            return this.mTopLeftChild == null && this.mTopRightChild == null && this.mBottomLeftChild != null && this.mBottomRightChild != null;
        }

        public List<T> getItems() {
            return this.mItems;
        }

        public String toString() {
            return toString(0);
        }

        public String toString(int pIndent) {
            char[] indents = new char[pIndent];
            Arrays.fill(indents, '\t');
            StringBuilder sb = new StringBuilder().append(indents).append('[').append(" Class: ").append(getClass().getSimpleName()).append('\n').append(indents).append('\t').append("Level: ").append(this.mLevel).append(',').append('\n').append(indents).append('\t').append("Bounds: ");
            appendBoundsToString(sb);
            sb.append(',').append('\n').append(indents).append("\tItems: ");
            if (this.mItems != null) {
                sb.append(this.mItems.toString());
            } else {
                sb.append("[]");
            }
            sb.append('\n').append(indents).append('\t').append("Children: [");
            if (this.mTopLeftChild == null && this.mTopRightChild == null && this.mBottomLeftChild == null && this.mBottomRightChild == null) {
                sb.append(']');
            } else {
                if (this.mTopLeftChild != null) {
                    sb.append('\n');
                    sb.append(this.mTopLeftChild.toString(pIndent + 2));
                    if (!(this.mTopRightChild == null && this.mBottomLeftChild == null && this.mBottomRightChild == null)) {
                        sb.append(',');
                    }
                }
                if (this.mTopRightChild != null) {
                    sb.append('\n');
                    sb.append(this.mTopRightChild.toString(pIndent + 2));
                    if (!(this.mBottomLeftChild == null && this.mBottomRightChild == null)) {
                        sb.append(',');
                    }
                }
                if (this.mBottomLeftChild != null) {
                    sb.append('\n');
                    sb.append(this.mBottomLeftChild.toString(pIndent + 2));
                    if (this.mBottomRightChild != null) {
                        sb.append(',');
                    }
                }
                if (this.mBottomRightChild != null) {
                    sb.append('\n');
                    sb.append(this.mBottomRightChild.toString(pIndent + 2));
                }
                sb.append('\n').append(indents).append('\t').append(']');
            }
            sb.append('\n').append(indents).append(']');
            return sb.toString();
        }

        public int getItemCount() {
            int count;
            if (this.mItems == null) {
                count = 0;
            } else {
                count = this.mItems.size();
            }
            if (this.mTopLeftChild != null) {
                count += this.mTopLeftChild.getItemCount();
            }
            if (this.mTopRightChild != null) {
                count += this.mTopRightChild.getItemCount();
            }
            if (this.mBottomLeftChild != null) {
                count += this.mBottomLeftChild.getItemCount();
            }
            if (this.mBottomRightChild != null) {
                return count + this.mBottomRightChild.getItemCount();
            }
            return count;
        }

        public void callItems(ParameterCallable<T> pParameterCallable) {
            if (this.mItems != null) {
                int itemCount = this.mItems.size();
                for (int i = 0; i < itemCount; i++) {
                    pParameterCallable.call((ISpatialItem) this.mItems.get(i));
                }
            }
            if (this.mTopLeftChild != null) {
                this.mTopLeftChild.callItems(pParameterCallable);
            }
            if (this.mTopRightChild != null) {
                this.mTopRightChild.callItems(pParameterCallable);
            }
            if (this.mBottomLeftChild != null) {
                this.mBottomLeftChild.callItems(pParameterCallable);
            }
            if (this.mBottomRightChild != null) {
                this.mBottomRightChild.callItems(pParameterCallable);
            }
        }

        public void callNodes(ParameterCallable<QuadTreeNode> pParameterCallable) {
            pParameterCallable.call(this);
            if (this.mTopLeftChild != null) {
                this.mTopLeftChild.callNodes(pParameterCallable);
            }
            if (this.mTopRightChild != null) {
                this.mTopRightChild.callNodes(pParameterCallable);
            }
            if (this.mBottomLeftChild != null) {
                this.mBottomLeftChild.callNodes(pParameterCallable);
            }
            if (this.mBottomRightChild != null) {
                this.mBottomRightChild.callNodes(pParameterCallable);
            }
        }

        public ArrayList<T> getItemsAndItemsBelow() {
            return (ArrayList) getItemsAndItemsBelow(new ArrayList());
        }

        public <L extends List<T>> L getItemsAndItemsBelow(L pResult) {
            if (this.mItems != null) {
                pResult.addAll(this.mItems);
            }
            if (this.mTopLeftChild != null) {
                this.mTopLeftChild.getItemsAndItemsBelow((List) pResult);
            }
            if (this.mTopRightChild != null) {
                this.mTopRightChild.getItemsAndItemsBelow((List) pResult);
            }
            if (this.mBottomLeftChild != null) {
                this.mBottomLeftChild.getItemsAndItemsBelow((List) pResult);
            }
            if (this.mBottomRightChild != null) {
                this.mBottomRightChild.getItemsAndItemsBelow((List) pResult);
            }
            return pResult;
        }

        public ArrayList<T> getItemsAndItemsBelow(IMatcher<T> pMatcher) {
            return (ArrayList) getItemsAndItemsBelow(pMatcher, new ArrayList());
        }

        public <L extends List<T>> L getItemsAndItemsBelow(IMatcher<T> pMatcher, L pResult) {
            if (this.mItems != null) {
                for (ISpatialItem item : this.mItems) {
                    if (pMatcher.matches(item)) {
                        pResult.add(item);
                    }
                }
            }
            if (this.mTopLeftChild != null) {
                this.mTopLeftChild.getItemsAndItemsBelow(pMatcher, pResult);
            }
            if (this.mTopRightChild != null) {
                this.mTopRightChild.getItemsAndItemsBelow(pMatcher, pResult);
            }
            if (this.mBottomLeftChild != null) {
                this.mBottomLeftChild.getItemsAndItemsBelow(pMatcher, pResult);
            }
            if (this.mBottomRightChild != null) {
                this.mBottomRightChild.getItemsAndItemsBelow(pMatcher, pResult);
            }
            return pResult;
        }

        public <L extends List<S>, S extends T> L getItemsAndItemsBelowForSubclass(IMatcher<T> pMatcher, L pResult) throws ClassCastException {
            if (this.mItems != null) {
                int itemCount = this.mItems.size();
                for (int i = 0; i < itemCount; i++) {
                    ISpatialItem item = (ISpatialItem) this.mItems.get(i);
                    if (pMatcher.matches(item)) {
                        pResult.add(item);
                    }
                }
            }
            if (this.mTopLeftChild != null) {
                this.mTopLeftChild.getItemsAndItemsBelowForSubclass(pMatcher, pResult);
            }
            if (this.mTopRightChild != null) {
                this.mTopRightChild.getItemsAndItemsBelowForSubclass(pMatcher, pResult);
            }
            if (this.mBottomLeftChild != null) {
                this.mBottomLeftChild.getItemsAndItemsBelowForSubclass(pMatcher, pResult);
            }
            if (this.mBottomRightChild != null) {
                this.mBottomRightChild.getItemsAndItemsBelowForSubclass(pMatcher, pResult);
            }
            return pResult;
        }

        public ArrayList<T> query(B pBounds) {
            return (ArrayList) query(pBounds, new ArrayList());
        }

        public <L extends List<T>> L query(B pBounds, L pResult) {
            if (this.mItems != null) {
                int itemCount = this.mItems.size();
                for (int i = 0; i < itemCount; i++) {
                    ISpatialItem item = (ISpatialItem) this.mItems.get(i);
                    if (intersects(pBounds, item.getBounds())) {
                        pResult.add(item);
                    }
                }
            }
            if (!(queryChild(pBounds, pResult, this.mTopLeftChild) || queryChild(pBounds, pResult, this.mTopRightChild) || queryChild(pBounds, pResult, this.mBottomLeftChild) || !queryChild(pBounds, pResult, this.mBottomRightChild))) {
            }
            return pResult;
        }

        public <L extends List<T>> L query(B pBounds, IMatcher<T> pMatcher, L pResult) {
            if (this.mItems != null) {
                for (ISpatialItem item : this.mItems) {
                    if (intersects(pBounds, item.getBounds()) && pMatcher.matches(item)) {
                        pResult.add(item);
                    }
                }
            }
            if (!(queryChild(pBounds, pMatcher, pResult, this.mTopLeftChild) || queryChild(pBounds, pMatcher, pResult, this.mTopRightChild) || queryChild(pBounds, pMatcher, pResult, this.mBottomLeftChild) || !queryChild(pBounds, pMatcher, pResult, this.mBottomRightChild))) {
            }
            return pResult;
        }

        public <L extends List<S>, S extends T> L queryForSubclass(B pBounds, IMatcher<T> pMatcher, L pResult) throws ClassCastException {
            if (this.mItems != null) {
                for (ISpatialItem item : this.mItems) {
                    if (intersects(pBounds, item.getBounds()) && pMatcher.matches(item)) {
                        pResult.add(item);
                    }
                }
            }
            if (!(queryChildForSubclass(pBounds, pMatcher, pResult, this.mTopLeftChild) || queryChildForSubclass(pBounds, pMatcher, pResult, this.mTopRightChild) || queryChildForSubclass(pBounds, pMatcher, pResult, this.mBottomLeftChild) || !queryChildForSubclass(pBounds, pMatcher, pResult, this.mBottomRightChild))) {
            }
            return pResult;
        }

        private <L extends List<T>> boolean queryChild(B pBounds, L pResult, QuadTreeNode pChild) {
            if (pChild == null) {
                return false;
            }
            if (pChild.contains(pBounds)) {
                pChild.query(pBounds, pResult);
                return true;
            } else if (pChild.containedBy(pBounds)) {
                pChild.getItemsAndItemsBelow((List) pResult);
                return false;
            } else if (!pChild.intersects(pBounds)) {
                return false;
            } else {
                pChild.query(pBounds, pResult);
                return false;
            }
        }

        private <L extends List<T>> boolean queryChild(B pBounds, IMatcher<T> pMatcher, L pResult, QuadTreeNode pChild) {
            if (pChild == null) {
                return false;
            }
            if (pChild.contains(pBounds)) {
                pChild.query(pBounds, pMatcher, pResult);
                return true;
            } else if (pChild.containedBy(pBounds)) {
                pChild.getItemsAndItemsBelow(pMatcher, pResult);
                return false;
            } else if (!pChild.intersects(pBounds)) {
                return false;
            } else {
                pChild.query(pBounds, pMatcher, pResult);
                return false;
            }
        }

        private <L extends List<S>, S extends T> boolean queryChildForSubclass(B pBounds, IMatcher<T> pMatcher, L pResult, QuadTreeNode pChild) throws ClassCastException {
            if (pChild == null) {
                return false;
            }
            if (pChild.contains(pBounds)) {
                pChild.queryForSubclass(pBounds, pMatcher, pResult);
                return true;
            } else if (pChild.containedBy(pBounds)) {
                pChild.getItemsAndItemsBelowForSubclass(pMatcher, pResult);
                return false;
            } else if (!pChild.intersects(pBounds)) {
                return false;
            } else {
                pChild.queryForSubclass(pBounds, pMatcher, pResult);
                return false;
            }
        }

        public boolean containsAny(B pBounds, IMatcher<T> pMatcher) {
            if (this.mItems != null) {
                int itemCount = this.mItems.size();
                for (int i = 0; i < itemCount; i++) {
                    ISpatialItem item = (ISpatialItem) this.mItems.get(i);
                    if (intersects(pBounds, item.getBounds()) && pMatcher.matches(item)) {
                        return true;
                    }
                }
            }
            if (containsAnyChild(pBounds, pMatcher, this.mTopLeftChild) || containsAnyChild(pBounds, pMatcher, this.mTopRightChild) || containsAnyChild(pBounds, pMatcher, this.mBottomLeftChild) || containsAnyChild(pBounds, pMatcher, this.mBottomRightChild)) {
                return true;
            }
            return false;
        }

        public boolean containsAny(B pBounds) {
            if (this.mItems != null) {
                int itemCount = this.mItems.size();
                for (int i = 0; i < itemCount; i++) {
                    if (intersects(pBounds, ((ISpatialItem) this.mItems.get(i)).getBounds())) {
                        return true;
                    }
                }
            }
            if (containsAnyChild(pBounds, this.mTopLeftChild) || containsAnyChild(pBounds, this.mTopRightChild) || containsAnyChild(pBounds, this.mBottomLeftChild) || containsAnyChild(pBounds, this.mBottomRightChild)) {
                return true;
            }
            return false;
        }

        private boolean containsAnyChild(B pBounds, IMatcher<T> pMatcher, QuadTreeNode pChild) {
            if (pChild != null && pChild.intersects(pBounds) && pChild.containsAny(pBounds, pMatcher)) {
                return true;
            }
            return false;
        }

        private boolean containsAnyChild(B pBounds, QuadTreeNode pChild) {
            if (pChild != null && pChild.intersects(pBounds) && pChild.containsAny(pBounds)) {
                return true;
            }
            return false;
        }

        public void add(T pItem, B pBounds) throws IllegalArgumentException {
            if (this.mLevel >= QuadTree.this.mMaxLevel) {
                addItemSafe(pItem);
            } else if (this.mTopLeftChild != null && this.mTopLeftChild.contains(pBounds)) {
                this.mTopLeftChild.add(pItem, pBounds);
            } else if (contains(BoundsSplit.TOP_LEFT, pBounds) && this.mTopLeftChild == null) {
                try {
                    this.mTopLeftChild = split(BoundsSplit.TOP_LEFT);
                    this.mTopLeftChild.add(pItem, pBounds);
                } catch (BoundsSplitException e) {
                    addItemSafe(pItem);
                }
            } else if (this.mTopRightChild != null && this.mTopRightChild.contains(pBounds)) {
                this.mTopRightChild.add(pItem, pBounds);
            } else if (contains(BoundsSplit.TOP_RIGHT, pBounds) && this.mTopRightChild == null) {
                try {
                    this.mTopRightChild = split(BoundsSplit.TOP_RIGHT);
                    this.mTopRightChild.add(pItem, pBounds);
                } catch (BoundsSplitException e2) {
                    addItemSafe(pItem);
                }
            } else if (this.mBottomLeftChild != null && this.mBottomLeftChild.contains(pBounds)) {
                this.mBottomLeftChild.add(pItem, pBounds);
            } else if (contains(BoundsSplit.BOTTOM_LEFT, pBounds) && this.mBottomLeftChild == null) {
                try {
                    this.mBottomLeftChild = split(BoundsSplit.BOTTOM_LEFT);
                    this.mBottomLeftChild.add(pItem, pBounds);
                } catch (BoundsSplitException e3) {
                    addItemSafe(pItem);
                }
            } else if (this.mBottomRightChild != null && this.mBottomRightChild.contains(pBounds)) {
                this.mBottomRightChild.add(pItem, pBounds);
            } else if (contains(BoundsSplit.BOTTOM_RIGHT, pBounds) && this.mBottomRightChild == null) {
                try {
                    this.mBottomRightChild = split(BoundsSplit.BOTTOM_RIGHT);
                    this.mBottomRightChild.add(pItem, pBounds);
                } catch (BoundsSplitException e4) {
                    addItemSafe(pItem);
                }
            } else {
                addItemSafe(pItem);
            }
        }

        public boolean remove(T pItem) throws IllegalArgumentException {
            return remove(pItem, pItem.getBounds());
        }

        public boolean remove(T pItem, B pBounds) throws IllegalArgumentException {
            if (!contains(pBounds)) {
                throw new IllegalArgumentException("pItem (" + pItem.toString() + ") is out of the bounds of this " + getClass().getSimpleName() + ".");
            } else if (this.mTopLeftChild != null && this.mTopLeftChild.contains(pBounds)) {
                return this.mTopLeftChild.remove(pItem, pBounds);
            } else {
                if (this.mTopRightChild != null && this.mTopRightChild.contains(pBounds)) {
                    return this.mTopRightChild.remove(pItem, pBounds);
                }
                if (this.mBottomLeftChild != null && this.mBottomLeftChild.contains(pBounds)) {
                    return this.mBottomLeftChild.remove(pItem, pBounds);
                }
                if (this.mBottomRightChild != null && this.mBottomRightChild.contains(pBounds)) {
                    return this.mBottomRightChild.remove(pItem, pBounds);
                }
                if (this.mItems == null) {
                    return false;
                }
                return this.mItems.remove(pItem);
            }
        }

        private void addItemSafe(T pItem) {
            if (this.mItems == null) {
                this.mItems = new ArrayList(1);
            }
            this.mItems.add(pItem);
        }

        protected void clear() {
            if (this.mBottomLeftChild != null) {
                this.mBottomLeftChild.clear();
                this.mBottomLeftChild = null;
            }
            if (this.mBottomRightChild != null) {
                this.mBottomRightChild.clear();
                this.mBottomRightChild = null;
            }
            if (this.mTopLeftChild != null) {
                this.mTopLeftChild.clear();
                this.mTopLeftChild = null;
            }
            if (this.mTopRightChild != null) {
                this.mTopRightChild.clear();
                this.mTopRightChild = null;
            }
            if (this.mItems != null) {
                this.mItems.clear();
                this.mItems = null;
            }
        }
    }

    protected abstract QuadTreeNode getRoot();

    protected abstract QuadTreeNode initRoot(B b);

    public QuadTree(B pBounds) {
        this(pBounds, 8);
    }

    protected QuadTree(B pBounds, int pMaxLevel) {
        this.mBounds = pBounds;
        this.mMaxLevel = pMaxLevel;
        this.mRoot = initRoot(pBounds);
    }

    public int getMaxLevel() {
        return this.mMaxLevel;
    }

    public B getBounds() {
        return this.mBounds;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder().append('[').append(" Class: ").append(getClass().getSimpleName()).append('\n').append('\t').append("MaxLevel: ").append(this.mMaxLevel).append(',').append('\n').append('\t').append("Bounds: ");
        this.mRoot.appendBoundsToString(sb);
        sb.append(',').append('\n').append('\t').append("Root:").append('\n').append(this.mRoot.toString(2)).append('\n').append(']');
        return sb.toString();
    }

    public synchronized int getItemCount() {
        return this.mRoot.getItemCount();
    }

    public synchronized boolean isEmpty() {
        return getItemCount() == 0;
    }

    public synchronized void add(T pItem) {
        add(pItem, pItem.getBounds());
    }

    public synchronized void addAll(T... pItems) {
        for (T item : pItems) {
            add(item);
        }
    }

    public synchronized void addAll(ArrayList<T> pItems) {
        int itemCount = pItems.size();
        for (int i = 0; i < itemCount; i++) {
            add((ISpatialItem) pItems.get(i));
        }
    }

    public synchronized void addAll(Collection<T> pItems) {
        for (T item : pItems) {
            add(item);
        }
    }

    @Deprecated
    public synchronized void add(T pItem, B pBounds) {
        if (this.mRoot.contains(pBounds)) {
            this.mRoot.add(pItem, pBounds);
        } else {
            Debug.m4601w("pBounds are out of the bounds of this " + getClass().getSimpleName() + ".");
            this.mRoot.addItemSafe(pItem);
        }
    }

    public synchronized void move(T pItem, B pBounds) throws AndEngineRuntimeException {
        if (remove(pItem, pBounds)) {
            add(pItem);
        } else {
            throw new AndEngineRuntimeException("Failed to remove item: '" + pItem.toString() + " from old bounds: '" + pBounds.toString() + "'.");
        }
    }

    @Deprecated
    public synchronized void move(T pItem, B pOldBounds, B pNewBounds) throws AndEngineRuntimeException {
        if (remove(pItem, pOldBounds)) {
            add(pItem, pNewBounds);
        } else {
            throw new AndEngineRuntimeException("Failed to remove item: '" + pItem.toString() + " from old bounds: '" + pOldBounds.toString() + "'.");
        }
    }

    public synchronized boolean remove(T pItem) {
        return remove(pItem, pItem.getBounds());
    }

    public synchronized boolean remove(T pItem, B pBounds) {
        return this.mRoot.remove(pItem, pBounds);
    }

    public synchronized ArrayList<T> query(B pBounds) {
        return (ArrayList) query((IBounds) pBounds, new ArrayList());
    }

    public synchronized <L extends List<T>> L query(B pBounds, L pResult) {
        return this.mRoot.query(pBounds, pResult);
    }

    public synchronized ArrayList<T> query(B pBounds, IMatcher<T> pMatcher) {
        return (ArrayList) query(pBounds, pMatcher, new ArrayList());
    }

    public synchronized <L extends List<T>> L query(B pBounds, IMatcher<T> pMatcher, L pResult) {
        return this.mRoot.query(pBounds, pMatcher, pResult);
    }

    public synchronized <L extends List<S>, S extends T> L queryForSubclass(B pBounds, IMatcher<T> pMatcher, L pResult) throws ClassCastException {
        return this.mRoot.queryForSubclass(pBounds, pMatcher, pResult);
    }

    public synchronized boolean containsAny(B pBounds) {
        return this.mRoot.containsAny(pBounds);
    }

    public synchronized boolean containsAny(B pBounds, IMatcher<T> pMatcher) {
        return this.mRoot.containsAny(pBounds, pMatcher);
    }

    public synchronized void callItems(ParameterCallable<T> pParameterCallable) {
        this.mRoot.callItems(pParameterCallable);
    }

    public synchronized void callNodes(ParameterCallable<QuadTreeNode> pParameterCallable) {
        this.mRoot.callNodes(pParameterCallable);
    }

    public synchronized void clear() {
        this.mRoot.clear();
    }
}
