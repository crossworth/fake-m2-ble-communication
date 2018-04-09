package org.andengine.util.algorithm.path.astar;

import org.andengine.util.algorithm.path.IPathFinderMap;

public class NullHeuristic<T> implements IAStarHeuristic<T> {
    public float getExpectedRestCost(IPathFinderMap<T> iPathFinderMap, T t, int pFromX, int pFromY, int pToX, int pToY) {
        return 0.0f;
    }
}
