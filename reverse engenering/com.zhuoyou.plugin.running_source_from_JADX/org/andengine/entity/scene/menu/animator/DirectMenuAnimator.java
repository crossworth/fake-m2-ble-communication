package org.andengine.entity.scene.menu.animator;

import java.util.ArrayList;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.util.HorizontalAlign;

public class DirectMenuAnimator extends BaseMenuAnimator {
    public DirectMenuAnimator(HorizontalAlign pHorizontalAlign) {
        super(pHorizontalAlign);
    }

    public DirectMenuAnimator(float pMenuItemSpacing) {
        super(pMenuItemSpacing);
    }

    public DirectMenuAnimator(HorizontalAlign pHorizontalAlign, float pMenuItemSpacing) {
        super(pHorizontalAlign, pMenuItemSpacing);
    }

    public void buildAnimations(ArrayList<IMenuItem> arrayList, float pCameraWidth, float pCameraHeight) {
    }

    public void prepareAnimations(ArrayList<IMenuItem> pMenuItems, float pCameraWidth, float pCameraHeight) {
        float maximumWidth = getMaximumWidth(pMenuItems);
        float baseX = (pCameraWidth - maximumWidth) * 0.5f;
        float baseY = (pCameraHeight - getOverallHeight(pMenuItems)) * 0.5f;
        float menuItemSpacing = this.mMenuItemSpacing;
        float offsetY = 0.0f;
        int menuItemCount = pMenuItems.size();
        for (int i = 0; i < menuItemCount; i++) {
            float offsetX;
            IMenuItem menuItem = (IMenuItem) pMenuItems.get(i);
            switch (this.mHorizontalAlign) {
                case LEFT:
                    offsetX = 0.0f;
                    break;
                case RIGHT:
                    offsetX = maximumWidth - menuItem.getWidthScaled();
                    break;
                default:
                    offsetX = (maximumWidth - menuItem.getWidthScaled()) * 0.5f;
                    break;
            }
            menuItem.setPosition(baseX + offsetX, baseY + offsetY);
            offsetY += menuItem.getHeight() + menuItemSpacing;
        }
    }
}
