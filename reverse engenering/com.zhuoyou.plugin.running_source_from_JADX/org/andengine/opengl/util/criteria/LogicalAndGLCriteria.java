package org.andengine.opengl.util.criteria;

import org.andengine.opengl.util.GLState;

public class LogicalAndGLCriteria implements IGLCriteria {
    private final IGLCriteria[] mGLCriterias;

    public LogicalAndGLCriteria(IGLCriteria... pGLCriterias) {
        this.mGLCriterias = pGLCriterias;
    }

    public boolean isMet(GLState pGLState) {
        for (IGLCriteria gLCriteria : this.mGLCriterias) {
            if (!gLCriteria.isMet(pGLState)) {
                return false;
            }
        }
        return true;
    }
}
