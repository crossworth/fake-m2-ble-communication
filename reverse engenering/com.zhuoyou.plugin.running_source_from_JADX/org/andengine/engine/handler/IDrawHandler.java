package org.andengine.engine.handler;

import org.andengine.engine.camera.Camera;
import org.andengine.opengl.util.GLState;

public interface IDrawHandler {
    void onDraw(GLState gLState, Camera camera);
}
