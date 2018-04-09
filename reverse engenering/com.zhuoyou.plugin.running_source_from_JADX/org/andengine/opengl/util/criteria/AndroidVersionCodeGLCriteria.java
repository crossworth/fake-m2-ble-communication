package org.andengine.opengl.util.criteria;

import android.os.Build.VERSION;
import org.andengine.opengl.util.GLState;
import org.andengine.util.adt.data.operator.IntOperator;

public class AndroidVersionCodeGLCriteria extends IntGLCriteria {
    public AndroidVersionCodeGLCriteria(IntOperator pIntOperator, int pAndroidVersionCode) {
        super(pIntOperator, pAndroidVersionCode);
    }

    protected int getActualCriteria(GLState pGLState) {
        return VERSION.SDK_INT;
    }
}
