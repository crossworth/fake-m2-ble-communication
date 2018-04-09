package org.andengine.input.sensor.acceleration;

import org.andengine.input.sensor.SensorDelay;

public class AccelerationSensorOptions {
    final SensorDelay mSensorDelay;

    public AccelerationSensorOptions(SensorDelay pSensorDelay) {
        this.mSensorDelay = pSensorDelay;
    }

    public SensorDelay getSensorDelay() {
        return this.mSensorDelay;
    }
}
