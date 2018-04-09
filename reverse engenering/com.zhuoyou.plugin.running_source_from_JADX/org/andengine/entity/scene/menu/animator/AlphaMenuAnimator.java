package org.andengine.entity.scene.menu.animator;

import java.util.ArrayList;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.modifier.ease.IEaseFunction;

public class AlphaMenuAnimator extends BaseMenuAnimator {
    private static final float ALPHA_FROM = 0.0f;
    private static final float ALPHA_TO = 1.0f;

    public AlphaMenuAnimator(IEaseFunction pEaseFunction) {
        super(pEaseFunction);
    }

    public AlphaMenuAnimator(HorizontalAlign pHorizontalAlign) {
        super(pHorizontalAlign);
    }

    public AlphaMenuAnimator(HorizontalAlign pHorizontalAlign, IEaseFunction pEaseFunction) {
        super(pHorizontalAlign, pEaseFunction);
    }

    public AlphaMenuAnimator(float pMenuItemSpacing) {
        super(pMenuItemSpacing);
    }

    public AlphaMenuAnimator(float pMenuItemSpacing, IEaseFunction pEaseFunction) {
        super(pMenuItemSpacing, pEaseFunction);
    }

    public AlphaMenuAnimator(HorizontalAlign pHorizontalAlign, float pMenuItemSpacing) {
        super(pHorizontalAlign, pMenuItemSpacing);
    }

    public AlphaMenuAnimator(HorizontalAlign pHorizontalAlign, float pMenuItemSpacing, IEaseFunction pEaseFunction) {
        super(pHorizontalAlign, pMenuItemSpacing, pEaseFunction);
    }

    public void buildAnimations(ArrayList<IMenuItem> pMenuItems, float pCameraWidth, float pCameraHeight) {
        IEaseFunction easeFunction = this.mEaseFunction;
        for (int i = pMenuItems.size() - 1; i >= 0; i--) {
            AlphaModifier alphaModifier = new AlphaModifier(1.0f, 0.0f, 1.0f, easeFunction);
            alphaModifier.setAutoUnregisterWhenFinished(false);
            ((IMenuItem) pMenuItems.get(i)).registerEntityModifier(alphaModifier);
        }
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
            menuItem.setAlpha(0.0f);
            offsetY += menuItem.getHeight() + menuItemSpacing;
        }
    }
}
