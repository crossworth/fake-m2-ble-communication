package org.andengine.entity.scene;

import org.andengine.input.touch.TouchEvent;
import org.andengine.util.IMatcher;

public interface ITouchArea {

    public interface ITouchAreaMatcher extends IMatcher<ITouchArea> {
    }

    boolean contains(float f, float f2);

    float[] convertLocalToSceneCoordinates(float f, float f2);

    float[] convertSceneToLocalCoordinates(float f, float f2);

    boolean onAreaTouched(TouchEvent touchEvent, float f, float f2);
}
