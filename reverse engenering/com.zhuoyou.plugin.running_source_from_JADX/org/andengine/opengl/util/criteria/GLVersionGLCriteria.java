package org.andengine.opengl.util.criteria;

import org.andengine.opengl.util.GLState;
import org.andengine.util.adt.data.operator.StringOperator;

public class GLVersionGLCriteria extends StringGLCriteria {
    public GLVersionGLCriteria(StringOperator pStringOperator, String pExpectedGLVersion) {
        super(pStringOperator, pExpectedGLVersion);
    }

    protected String getActualCriteria(GLState pGLState) {
        return pGLState.getVersion();
    }
}
