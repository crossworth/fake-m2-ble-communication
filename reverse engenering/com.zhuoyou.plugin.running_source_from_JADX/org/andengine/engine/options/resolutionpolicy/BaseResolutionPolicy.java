package org.andengine.engine.options.resolutionpolicy;

import android.view.View.MeasureSpec;
import org.andengine.util.adt.DataConstants;

public abstract class BaseResolutionPolicy implements IResolutionPolicy {
    protected static void throwOnNotMeasureSpecEXACTLY(int pWidthMeasureSpec, int pHeightMeasureSpec) {
        int specWidthMode = MeasureSpec.getMode(pWidthMeasureSpec);
        int specHeightMode = MeasureSpec.getMode(pHeightMeasureSpec);
        if (specWidthMode != DataConstants.BYTES_PER_GIGABYTE || specHeightMode != DataConstants.BYTES_PER_GIGABYTE) {
            throw new IllegalStateException("This IResolutionPolicy requires MeasureSpec.EXACTLY ! That means ");
        }
    }
}
