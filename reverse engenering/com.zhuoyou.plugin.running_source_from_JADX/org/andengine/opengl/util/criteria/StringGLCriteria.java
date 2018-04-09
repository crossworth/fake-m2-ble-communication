package org.andengine.opengl.util.criteria;

import org.andengine.opengl.util.GLState;
import org.andengine.util.adt.data.operator.StringOperator;

public abstract class StringGLCriteria implements IGLCriteria {
    private final String mCriteria;
    private final StringOperator mStringOperator;

    protected abstract String getActualCriteria(GLState gLState);

    public StringGLCriteria(StringOperator pStringOperator, String pCriteria) {
        this.mCriteria = pCriteria;
        this.mStringOperator = pStringOperator;
    }

    public boolean isMet(GLState pGLState) {
        return this.mStringOperator.check(getActualCriteria(pGLState), this.mCriteria);
    }
}
