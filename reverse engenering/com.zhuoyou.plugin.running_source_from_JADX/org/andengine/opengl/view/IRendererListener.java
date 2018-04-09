package org.andengine.opengl.view;

import org.andengine.opengl.util.GLState;

public interface IRendererListener {
    void onSurfaceChanged(GLState gLState, int i, int i2);

    void onSurfaceCreated(GLState gLState);
}
