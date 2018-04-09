package org.andengine.util.color;

import org.andengine.util.adt.pool.GenericPool;

public class ColorPool extends GenericPool<Color> {
    protected Color onAllocatePoolItem() {
        return new Color(Color.WHITE);
    }

    protected void onHandleRecycleItem(Color pColor) {
        pColor.setChecking(Color.WHITE);
        super.onHandleRecycleItem(pColor);
    }
}
