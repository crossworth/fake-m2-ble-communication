package org.andengine.opengl.util.criteria;

import org.andengine.opengl.util.GLState;

public interface IGLCriteria {
    boolean isMet(GLState gLState);
}
