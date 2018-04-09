package org.andengine.input.sensor.acceleration;

public interface IAccelerationListener {
    void onAccelerationAccuracyChanged(AccelerationData accelerationData);

    void onAccelerationChanged(AccelerationData accelerationData);
}
