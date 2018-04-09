package org.andengine.input.sensor.orientation;

import org.andengine.input.sensor.SensorDelay;

public class OrientationSensorOptions {
    final SensorDelay mSensorDelay;

    public OrientationSensorOptions(SensorDelay pSensorDelay) {
        this.mSensorDelay = pSensorDelay;
    }

    public SensorDelay getSensorDelay() {
        return this.mSensorDelay;
    }
}
