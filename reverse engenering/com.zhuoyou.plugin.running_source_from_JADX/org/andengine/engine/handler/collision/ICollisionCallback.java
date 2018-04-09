package org.andengine.engine.handler.collision;

import org.andengine.entity.shape.IShape;

public interface ICollisionCallback {
    boolean onCollision(IShape iShape, IShape iShape2);
}
