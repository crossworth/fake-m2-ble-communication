package org.andengine.ui.activity;

import org.andengine.opengl.view.RenderSurfaceView;

public abstract class LayoutGameActivity extends BaseGameActivity {
    protected abstract int getLayoutID();

    protected abstract int getRenderSurfaceViewID();

    protected void onSetContentView() {
        super.setContentView(getLayoutID());
        this.mRenderSurfaceView = (RenderSurfaceView) findViewById(getRenderSurfaceViewID());
        this.mRenderSurfaceView.setRenderer(this.mEngine, this);
    }
}
