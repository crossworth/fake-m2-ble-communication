package org.andengine.util.algorithm.path;

import org.andengine.util.algorithm.path.astar.IAStarHeuristic;

public interface IPathFinder<T> {

    public interface IPathFinderListener<T> {
        void onVisited(T t, int i, int i2);
    }

    Path findPath(IPathFinderMap<T> iPathFinderMap, int i, int i2, int i3, int i4, T t, int i5, int i6, int i7, int i8, boolean z, IAStarHeuristic<T> iAStarHeuristic, ICostFunction<T> iCostFunction);

    Path findPath(IPathFinderMap<T> iPathFinderMap, int i, int i2, int i3, int i4, T t, int i5, int i6, int i7, int i8, boolean z, IAStarHeuristic<T> iAStarHeuristic, ICostFunction<T> iCostFunction, float f);

    Path findPath(IPathFinderMap<T> iPathFinderMap, int i, int i2, int i3, int i4, T t, int i5, int i6, int i7, int i8, boolean z, IAStarHeuristic<T> iAStarHeuristic, ICostFunction<T> iCostFunction, float f, IPathFinderListener<T> iPathFinderListener);
}
