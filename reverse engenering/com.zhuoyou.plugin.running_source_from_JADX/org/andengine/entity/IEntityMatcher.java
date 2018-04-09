package org.andengine.entity;

import org.andengine.util.IMatcher;

public interface IEntityMatcher extends IMatcher<IEntity> {
    boolean matches(IEntity iEntity);
}
