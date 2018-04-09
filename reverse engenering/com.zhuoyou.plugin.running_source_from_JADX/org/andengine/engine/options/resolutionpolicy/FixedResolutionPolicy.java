package org.andengine.engine.options.resolutionpolicy;

import org.andengine.opengl.view.RenderSurfaceView;

public class FixedResolutionPolicy extends BaseResolutionPolicy {
    private final int mHeight;
    private final int mWidth;

    public FixedResolutionPolicy(int pWidth, int pHeight) {
        this.mWidth = pWidth;
        this.mHeight = pHeight;
    }

    public void onMeasure(RenderSurfaceView pRenderSurfaceView, int pWidthMeasureSpec, int pHeightMeasureSpec) {
        pRenderSurfaceView.setMeasuredDimensionProxy(this.mWidth, this.mHeight);
    }
}
