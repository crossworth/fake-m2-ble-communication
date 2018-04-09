package org.andengine.entity;

public interface IEntityFactory<T extends IEntity> {
    T create(float f, float f2);
}
