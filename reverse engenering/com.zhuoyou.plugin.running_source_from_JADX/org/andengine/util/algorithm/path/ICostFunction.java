package org.andengine.util.algorithm.path;

public interface ICostFunction<T> {
    float getCost(IPathFinderMap<T> iPathFinderMap, int i, int i2, int i3, int i4, T t);
}
