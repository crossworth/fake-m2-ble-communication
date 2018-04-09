package org.andengine.util.algorithm.path.astar;

import org.andengine.util.algorithm.path.IPathFinderMap;

public interface IAStarHeuristic<T> {
    float getExpectedRestCost(IPathFinderMap<T> iPathFinderMap, T t, int i, int i2, int i3, int i4);
}
