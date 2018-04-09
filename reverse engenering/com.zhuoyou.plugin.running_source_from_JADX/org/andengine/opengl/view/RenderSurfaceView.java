package org.andengine.opengl.view;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import org.andengine.engine.Engine;

public class RenderSurfaceView extends GLSurfaceView {
    private ConfigChooser mConfigChooser;
    private EngineRenderer mEngineRenderer;

    public RenderSurfaceView(Context pContext) {
        super(pContext);
        setEGLContextClientVersion(2);
    }

    public RenderSurfaceView(Context pContext, AttributeSet pAttrs) {
        super(pContext, pAttrs);
        setEGLContextClientVersion(2);
    }

    public ConfigChooser getConfigChooser() throws IllegalStateException {
        if (this.mConfigChooser != null) {
            return this.mConfigChooser;
        }
        throw new IllegalStateException(ConfigChooser.class.getSimpleName() + " not yet set.");
    }

    protected void onMeasure(int pWidthMeasureSpec, int pHeightMeasureSpec) {
        if (isInEditMode()) {
            super.onMeasure(pWidthMeasureSpec, pHeightMeasureSpec);
        } else {
            this.mEngineRenderer.mEngine.getEngineOptions().getResolutionPolicy().onMeasure(this, pWidthMeasureSpec, pHeightMeasureSpec);
        }
    }

    public void setMeasuredDimensionProxy(int pMeasuredWidth, int pMeasuredHeight) {
        setMeasuredDimension(pMeasuredWidth, pMeasuredHeight);
    }

    public void setRenderer(Engine pEngine, IRendererListener pRendererListener) {
        if (this.mConfigChooser == null) {
            this.mConfigChooser = new ConfigChooser(pEngine.getEngineOptions().getRenderOptions().isMultiSampling());
        }
        setEGLConfigChooser(this.mConfigChooser);
        setOnTouchListener(pEngine);
        this.mEngineRenderer = new EngineRenderer(pEngine, this.mConfigChooser, pRendererListener);
        setRenderer(this.mEngineRenderer);
    }
}
