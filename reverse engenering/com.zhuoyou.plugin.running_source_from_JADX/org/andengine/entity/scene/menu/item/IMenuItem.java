package org.andengine.entity.scene.menu.item;

import org.andengine.entity.shape.IAreaShape;

public interface IMenuItem extends IAreaShape {
    int getID();

    void onSelected();

    void onUnselected();
}
