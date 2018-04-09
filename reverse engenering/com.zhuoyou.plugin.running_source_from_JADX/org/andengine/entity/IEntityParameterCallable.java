package org.andengine.entity;

import org.andengine.util.call.ParameterCallable;

public interface IEntityParameterCallable extends ParameterCallable<IEntity> {
    void call(IEntity iEntity);
}
