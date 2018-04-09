package org.andengine.opengl.util.criteria;

import org.andengine.opengl.util.GLState;
import org.andengine.util.adt.data.operator.StringOperator;

public class GLExtensionsGLCriteria extends StringGLCriteria {
    public GLExtensionsGLCriteria(StringOperator pStringOperator, String pGLExtensions) {
        super(pStringOperator, pGLExtensions);
    }

    protected String getActualCriteria(GLState pGLState) {
        return pGLState.getExtensions();
    }
}
