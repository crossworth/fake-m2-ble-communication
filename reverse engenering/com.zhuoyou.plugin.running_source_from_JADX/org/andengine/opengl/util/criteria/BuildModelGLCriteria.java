package org.andengine.opengl.util.criteria;

import android.os.Build;
import org.andengine.opengl.util.GLState;
import org.andengine.util.adt.data.operator.StringOperator;

public class BuildModelGLCriteria extends StringGLCriteria {
    public BuildModelGLCriteria(StringOperator pStringOperator, String pBuildModel) {
        super(pStringOperator, pBuildModel);
    }

    protected String getActualCriteria(GLState pGLState) {
        return Build.MODEL;
    }
}
