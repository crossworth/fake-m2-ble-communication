package org.andengine.engine.options.resolutionpolicy;

import org.andengine.opengl.view.RenderSurfaceView;

public interface IResolutionPolicy {
    void onMeasure(RenderSurfaceView renderSurfaceView, int i, int i2);
}
