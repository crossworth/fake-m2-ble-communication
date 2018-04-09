package org.andengine.util.adt.spatial;

import org.andengine.util.adt.bounds.IBounds;

public interface ISpatialItem<B extends IBounds> {
    B getBounds();
}
