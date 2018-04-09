package org.andengine.opengl.util.criteria;

import org.andengine.opengl.util.GLState;
import org.andengine.util.adt.data.operator.IntOperator;

public abstract class IntGLCriteria implements IGLCriteria {
    private final int mCriteria;
    private final IntOperator mIntOperator;

    protected abstract int getActualCriteria(GLState gLState);

    public IntGLCriteria(IntOperator pIntOperator, int pCriteria) {
        this.mCriteria = pCriteria;
        this.mIntOperator = pIntOperator;
    }

    public boolean isMet(GLState pGLState) {
        return this.mIntOperator.check(getActualCriteria(pGLState), this.mCriteria);
    }
}
