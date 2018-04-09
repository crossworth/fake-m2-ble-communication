package org.andengine.opengl.view;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import org.andengine.engine.Engine;
import org.andengine.engine.options.RenderOptions;
import org.andengine.opengl.util.GLState;
import org.andengine.util.debug.Debug;

public class EngineRenderer implements Renderer {
    final ConfigChooser mConfigChooser;
    final Engine mEngine;
    final GLState mGLState = new GLState();
    final boolean mMultiSampling = this.mEngine.getEngineOptions().getRenderOptions().isMultiSampling();
    final IRendererListener mRendererListener;

    public EngineRenderer(Engine pEngine, ConfigChooser pConfigChooser, IRendererListener pRendererListener) {
        this.mEngine = pEngine;
        this.mConfigChooser = pConfigChooser;
        this.mRendererListener = pRendererListener;
    }

    public void onSurfaceCreated(GL10 pGL, EGLConfig pEGLConfig) {
        synchronized (GLState.class) {
            RenderOptions renderOptions = this.mEngine.getEngineOptions().getRenderOptions();
            this.mGLState.reset(renderOptions, this.mConfigChooser, pEGLConfig);
            this.mGLState.disableDepthTest();
            this.mGLState.enableBlend();
            this.mGLState.setDitherEnabled(renderOptions.isDithering());
            if (this.mRendererListener != null) {
                this.mRendererListener.onSurfaceCreated(this.mGLState);
            }
        }
    }

    public void onSurfaceChanged(GL10 pGL, int pWidth, int pHeight) {
        this.mEngine.setSurfaceSize(pWidth, pHeight);
        GLES20.glViewport(0, 0, pWidth, pHeight);
        this.mGLState.loadProjectionGLMatrixIdentity();
        if (this.mRendererListener != null) {
            this.mRendererListener.onSurfaceChanged(this.mGLState, pWidth, pHeight);
        }
    }

    public void onDrawFrame(GL10 pGL) {
        synchronized (GLState.class) {
            if (this.mMultiSampling && this.mConfigChooser.isCoverageMultiSampling()) {
                GLES20.glClear(32768);
            }
            try {
                this.mEngine.onDrawFrame(this.mGLState);
            } catch (Throwable e) {
                Debug.m4591e("GLThread interrupted!", e);
            }
        }
    }
}
