package org.andengine.entity.scene.menu.item.decorator;

import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.util.color.Color;

public class ColorMenuItemDecorator extends BaseMenuItemDecorator {
    private final Color mSelectedColor;
    private final Color mUnselectedColor;

    public ColorMenuItemDecorator(IMenuItem pMenuItem, Color pSelectedColor, Color pUnselectedColor) {
        super(pMenuItem);
        this.mSelectedColor = pSelectedColor;
        this.mUnselectedColor = pUnselectedColor;
        pMenuItem.setColor(pUnselectedColor);
    }

    public void onMenuItemSelected(IMenuItem pMenuItem) {
        pMenuItem.setColor(this.mSelectedColor);
    }

    public void onMenuItemUnselected(IMenuItem pMenuItem) {
        pMenuItem.setColor(this.mUnselectedColor);
    }

    public void onMenuItemReset(IMenuItem pMenuItem) {
        pMenuItem.setColor(this.mUnselectedColor);
    }
}
