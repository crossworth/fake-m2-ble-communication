package org.andengine.util.algorithm.path.astar;

import android.util.FloatMath;
import org.andengine.util.algorithm.path.IPathFinderMap;

public class EuclideanHeuristic<T> implements IAStarHeuristic<T> {
    public float getExpectedRestCost(IPathFinderMap<T> iPathFinderMap, T t, int pFromX, int pFromY, int pToX, int pToY) {
        float dX = (float) (pToX - pFromX);
        float dY = (float) (pToY - pFromY);
        return FloatMath.sqrt((dX * dX) + (dY * dY));
    }
}
