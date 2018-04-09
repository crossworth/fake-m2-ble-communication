package org.andengine.entity.scene;

import org.andengine.input.touch.TouchEvent;

public interface IOnAreaTouchListener {
    boolean onAreaTouched(TouchEvent touchEvent, ITouchArea iTouchArea, float f, float f2);
}
