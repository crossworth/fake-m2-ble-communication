package org.andengine.entity.shape;

public interface IAreaShape extends IShape {
    float getHeight();

    float getHeightScaled();

    float getWidth();

    float getWidthScaled();

    void setHeight(float f);

    void setSize(float f, float f2);

    void setWidth(float f);
}
