package org.andengine.input.sensor.orientation;

public interface IOrientationListener {
    void onOrientationAccuracyChanged(OrientationData orientationData);

    void onOrientationChanged(OrientationData orientationData);
}
