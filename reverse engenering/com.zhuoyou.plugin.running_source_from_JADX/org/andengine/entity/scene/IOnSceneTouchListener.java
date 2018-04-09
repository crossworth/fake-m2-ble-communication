package org.andengine.entity.scene;

import org.andengine.input.touch.TouchEvent;

public interface IOnSceneTouchListener {
    boolean onSceneTouchEvent(Scene scene, TouchEvent touchEvent);
}
