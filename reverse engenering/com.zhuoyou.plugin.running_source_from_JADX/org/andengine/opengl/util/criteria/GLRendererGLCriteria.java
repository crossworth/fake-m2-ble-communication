package org.andengine.opengl.util.criteria;

import org.andengine.opengl.util.GLState;
import org.andengine.util.adt.data.operator.StringOperator;

public class GLRendererGLCriteria extends StringGLCriteria {
    public GLRendererGLCriteria(StringOperator pStringOperator, String pGLRenderer) {
        super(pStringOperator, pGLRenderer);
    }

    protected String getActualCriteria(GLState pGLState) {
        return pGLState.getRenderer();
    }
}
