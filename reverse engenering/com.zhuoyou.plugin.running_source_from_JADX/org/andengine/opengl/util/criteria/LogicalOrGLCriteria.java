package org.andengine.opengl.util.criteria;

import org.andengine.opengl.util.GLState;

public class LogicalOrGLCriteria implements IGLCriteria {
    private final IGLCriteria[] mGLCriterias;

    public LogicalOrGLCriteria(IGLCriteria... pGLCriterias) {
        this.mGLCriterias = pGLCriterias;
    }

    public boolean isMet(GLState pGLState) {
        for (IGLCriteria gLCriteria : this.mGLCriterias) {
            if (gLCriteria.isMet(pGLState)) {
                return true;
            }
        }
        return false;
    }
}
