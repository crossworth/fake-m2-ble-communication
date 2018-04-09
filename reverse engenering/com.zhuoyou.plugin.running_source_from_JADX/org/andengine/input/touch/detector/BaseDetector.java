package org.andengine.input.touch.detector;

import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.TouchEvent;

public abstract class BaseDetector implements IOnSceneTouchListener {
    private boolean mEnabled = true;

    protected abstract boolean onManagedTouchEvent(TouchEvent touchEvent);

    public abstract void reset();

    public boolean isEnabled() {
        return this.mEnabled;
    }

    public void setEnabled(boolean pEnabled) {
        this.mEnabled = pEnabled;
    }

    public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
        return onTouchEvent(pSceneTouchEvent);
    }

    public final boolean onTouchEvent(TouchEvent pSceneTouchEvent) {
        if (this.mEnabled) {
            return onManagedTouchEvent(pSceneTouchEvent);
        }
        return false;
    }
}
