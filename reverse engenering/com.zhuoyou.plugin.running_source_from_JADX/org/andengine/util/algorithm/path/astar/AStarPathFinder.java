package org.andengine.util.algorithm.path.astar;

import android.support.v4.widget.AutoScrollHelper;
import org.andengine.util.adt.list.ShiftList;
import org.andengine.util.adt.map.LongSparseArray;
import org.andengine.util.adt.queue.IQueue;
import org.andengine.util.adt.queue.SortedQueue;
import org.andengine.util.adt.spatial.bounds.util.IntBoundsUtils;
import org.andengine.util.algorithm.path.ICostFunction;
import org.andengine.util.algorithm.path.IPathFinder;
import org.andengine.util.algorithm.path.IPathFinder.IPathFinderListener;
import org.andengine.util.algorithm.path.IPathFinderMap;
import org.andengine.util.algorithm.path.Path;

public class AStarPathFinder<T> implements IPathFinder<T> {

    private static final class Node implements Comparable<Node> {
        float mCost;
        final float mExpectedRestCost;
        final long mID;
        Node mParent;
        float mTotalCost;
        final int mX;
        final int mY;

        public Node(int pX, int pY, float pExpectedRestCost) {
            this.mX = pX;
            this.mY = pY;
            this.mExpectedRestCost = pExpectedRestCost;
            this.mID = calculateID(pX, pY);
        }

        public void setParent(Node pParent, float pCost) {
            this.mParent = pParent;
            this.mCost = pCost;
            this.mTotalCost = this.mExpectedRestCost + pCost;
        }

        public int compareTo(Node pNode) {
            float diff = this.mTotalCost - pNode.mTotalCost;
            if (diff > 0.0f) {
                return 1;
            }
            if (diff < 0.0f) {
                return -1;
            }
            return 0;
        }

        public boolean equals(Object pOther) {
            if (this == pOther) {
                return true;
            }
            if (pOther == null || getClass() != pOther.getClass()) {
                return false;
            }
            return equals((Node) pOther);
        }

        public String toString() {
            return getClass().getSimpleName() + " [x=" + this.mX + ", y=" + this.mY + "]";
        }

        public static long calculateID(int pX, int pY) {
            return (((long) pX) << 32) | ((long) pY);
        }

        public boolean equals(Node pNode) {
            return this.mID == pNode.mID;
        }
    }

    public Path findPath(IPathFinderMap<T> pPathFinderMap, int pXMin, int pYMin, int pXMax, int pYMax, T pEntity, int pFromX, int pFromY, int pToX, int pToY, boolean pAllowDiagonal, IAStarHeuristic<T> pAStarHeuristic, ICostFunction<T> pCostFunction) {
        return findPath(pPathFinderMap, pXMin, pYMin, pXMax, pYMax, pEntity, pFromX, pFromY, pToX, pToY, pAllowDiagonal, pAStarHeuristic, pCostFunction, AutoScrollHelper.NO_MAX);
    }

    public Path findPath(IPathFinderMap<T> pPathFinderMap, int pXMin, int pYMin, int pXMax, int pYMax, T pEntity, int pFromX, int pFromY, int pToX, int pToY, boolean pAllowDiagonal, IAStarHeuristic<T> pAStarHeuristic, ICostFunction<T> pCostFunction, float pMaxCost) {
        return findPath(pPathFinderMap, pXMin, pYMin, pXMax, pYMax, pEntity, pFromX, pFromY, pToX, pToY, pAllowDiagonal, pAStarHeuristic, pCostFunction, pMaxCost, null);
    }

    public Path findPath(IPathFinderMap<T> pPathFinderMap, int pXMin, int pYMin, int pXMax, int pYMax, T pEntity, int pFromX, int pFromY, int pToX, int pToY, boolean pAllowDiagonal, IAStarHeuristic<T> pAStarHeuristic, ICostFunction<T> pCostFunction, float pMaxCost, IPathFinderListener<T> pPathFinderListener) {
        if ((pFromX == pToX && pFromY == pToY) || pPathFinderMap.isBlocked(pFromX, pFromY, pEntity) || pPathFinderMap.isBlocked(pToX, pToY, pEntity)) {
            return null;
        }
        Node node = new Node(pFromX, pFromY, pAStarHeuristic.getExpectedRestCost(pPathFinderMap, pEntity, pFromX, pFromY, pToX, pToY));
        long fromNodeID = node.mID;
        long toNodeID = Node.calculateID(pToX, pToY);
        LongSparseArray<Node> visitedNodes = new LongSparseArray();
        LongSparseArray<Node> openNodes = new LongSparseArray();
        IQueue<Node> sortedQueue = new SortedQueue(new ShiftList());
        boolean allowDiagonalMovement = pAllowDiagonal;
        openNodes.put(fromNodeID, node);
        sortedQueue.enter(node);
        Node currentNode = null;
        while (openNodes.size() > 0) {
            currentNode = (Node) sortedQueue.poll();
            long currentNodeID = currentNode.mID;
            if (currentNodeID == toNodeID) {
                break;
            }
            visitedNodes.put(currentNodeID, currentNode);
            int dX = -1;
            while (dX <= 1) {
                int dY = -1;
                while (dY <= 1) {
                    if (!(dX == 0 && dY == 0) && (allowDiagonalMovement || dX == 0 || dY == 0)) {
                        int neighborNodeX = dX + currentNode.mX;
                        int neighborNodeY = dY + currentNode.mY;
                        long neighborNodeID = Node.calculateID(neighborNodeX, neighborNodeY);
                        if (IntBoundsUtils.contains(pXMin, pYMin, pXMax, pYMax, neighborNodeX, neighborNodeY) && !pPathFinderMap.isBlocked(neighborNodeX, neighborNodeY, pEntity) && visitedNodes.indexOfKey(neighborNodeID) < 0) {
                            boolean neighborNodeIsNew;
                            Node neighborNode = (Node) openNodes.get(neighborNodeID);
                            if (neighborNode == null) {
                                neighborNodeIsNew = true;
                                node = new Node(neighborNodeX, neighborNodeY, pAStarHeuristic.getExpectedRestCost(pPathFinderMap, pEntity, neighborNodeX, neighborNodeY, pToX, pToY));
                            } else {
                                neighborNodeIsNew = false;
                            }
                            float costFromCurrentToNeigbor = pCostFunction.getCost(pPathFinderMap, currentNode.mX, currentNode.mY, neighborNodeX, neighborNodeY, pEntity);
                            if (currentNode.mCost + costFromCurrentToNeigbor <= pMaxCost) {
                                neighborNode.setParent(currentNode, costFromCurrentToNeigbor);
                                if (neighborNodeIsNew) {
                                    openNodes.put(neighborNodeID, neighborNode);
                                } else {
                                    sortedQueue.remove((Object) neighborNode);
                                }
                                sortedQueue.enter(neighborNode);
                                if (pPathFinderListener != null) {
                                    pPathFinderListener.onVisited(pEntity, neighborNodeX, neighborNodeY);
                                }
                            } else if (!neighborNodeIsNew) {
                                openNodes.remove(neighborNodeID);
                            }
                        }
                    }
                    dY++;
                }
                dX++;
            }
        }
        visitedNodes.clear();
        openNodes.clear();
        sortedQueue.clear();
        if (currentNode.mID != toNodeID) {
            return null;
        }
        int length = 1;
        Node tmp = currentNode;
        while (tmp.mID != fromNodeID) {
            tmp = tmp.mParent;
            length++;
        }
        Path path = new Path(length);
        int index = length - 1;
        tmp = currentNode;
        while (tmp.mID != fromNodeID) {
            path.set(index, tmp.mX, tmp.mY);
            tmp = tmp.mParent;
            index--;
        }
        path.set(0, pFromX, pFromY);
        return path;
    }
}
