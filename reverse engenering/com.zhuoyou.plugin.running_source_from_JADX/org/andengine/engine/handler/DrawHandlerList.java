package org.andengine.engine.handler;

import org.andengine.engine.camera.Camera;
import org.andengine.opengl.util.GLState;
import org.andengine.util.adt.list.SmartList;

public class DrawHandlerList extends SmartList<IDrawHandler> implements IDrawHandler {
    private static final long serialVersionUID = 1767324757143199934L;

    public DrawHandlerList(int pCapacity) {
        super(pCapacity);
    }

    public void onDraw(GLState pGLState, Camera pCamera) {
        for (int i = size() - 1; i >= 0; i--) {
            ((IDrawHandler) get(i)).onDraw(pGLState, pCamera);
        }
    }
}
