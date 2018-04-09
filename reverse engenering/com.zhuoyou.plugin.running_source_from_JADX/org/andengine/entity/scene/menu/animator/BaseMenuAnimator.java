package org.andengine.entity.scene.menu.animator;

import java.util.ArrayList;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.modifier.ease.EaseLinear;
import org.andengine.util.modifier.ease.IEaseFunction;

public abstract class BaseMenuAnimator implements IMenuAnimator {
    protected static final float DURATION = 1.0f;
    private static final HorizontalAlign HORIZONTALALIGN_DEFAULT = HorizontalAlign.CENTER;
    private static final float MENUITEMSPACING_DEFAULT = 1.0f;
    protected final IEaseFunction mEaseFunction;
    protected final HorizontalAlign mHorizontalAlign;
    protected final float mMenuItemSpacing;

    public BaseMenuAnimator() {
        this(1.0f);
    }

    public BaseMenuAnimator(IEaseFunction pEaseFunction) {
        this(1.0f, pEaseFunction);
    }

    public BaseMenuAnimator(float pMenuItemSpacing) {
        this(HORIZONTALALIGN_DEFAULT, pMenuItemSpacing);
    }

    public BaseMenuAnimator(float pMenuItemSpacing, IEaseFunction pEaseFunction) {
        this(HORIZONTALALIGN_DEFAULT, pMenuItemSpacing, pEaseFunction);
    }

    public BaseMenuAnimator(HorizontalAlign pHorizontalAlign) {
        this(pHorizontalAlign, 1.0f);
    }

    public BaseMenuAnimator(HorizontalAlign pHorizontalAlign, IEaseFunction pEaseFunction) {
        this(pHorizontalAlign, 1.0f, pEaseFunction);
    }

    public BaseMenuAnimator(HorizontalAlign pHorizontalAlign, float pMenuItemSpacing) {
        this(pHorizontalAlign, pMenuItemSpacing, EaseLinear.getInstance());
    }

    public BaseMenuAnimator(HorizontalAlign pHorizontalAlign, float pMenuItemSpacing, IEaseFunction pEaseFunction) {
        this.mHorizontalAlign = pHorizontalAlign;
        this.mMenuItemSpacing = pMenuItemSpacing;
        this.mEaseFunction = pEaseFunction;
    }

    protected float getMaximumWidth(ArrayList<IMenuItem> pMenuItems) {
        float maximumWidth = Float.MIN_VALUE;
        for (int i = pMenuItems.size() - 1; i >= 0; i--) {
            maximumWidth = Math.max(maximumWidth, ((IMenuItem) pMenuItems.get(i)).getWidthScaled());
        }
        return maximumWidth;
    }

    protected float getOverallHeight(ArrayList<IMenuItem> pMenuItems) {
        float overallHeight = 0.0f;
        for (int i = pMenuItems.size() - 1; i >= 0; i--) {
            overallHeight += ((IMenuItem) pMenuItems.get(i)).getHeight();
        }
        return overallHeight + (((float) (pMenuItems.size() - 1)) * this.mMenuItemSpacing);
    }
}
