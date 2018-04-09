package org.andengine.input.sensor.orientation;

import android.hardware.SensorManager;
import com.droi.btlib.service.BtManagerService;
import java.util.Arrays;
import org.andengine.input.sensor.BaseSensorData;
import org.andengine.util.math.MathConstants;

public class OrientationData extends BaseSensorData {
    private final float[] mAccelerationValues = new float[3];
    private int mMagneticFieldAccuracy;
    private final float[] mMagneticFieldValues = new float[3];
    private final float[] mRotationMatrix = new float[16];

    public OrientationData(int pDisplayRotation) {
        super(3, pDisplayRotation);
    }

    public float getRoll() {
        return this.mValues[2];
    }

    public float getPitch() {
        return this.mValues[1];
    }

    public float getYaw() {
        return this.mValues[0];
    }

    @Deprecated
    public void setValues(float[] pValues) {
        super.setValues(pValues);
    }

    @Deprecated
    public void setAccuracy(int pAccuracy) {
        super.setAccuracy(pAccuracy);
    }

    public void setAccelerationValues(float[] pValues) {
        System.arraycopy(pValues, 0, this.mAccelerationValues, 0, pValues.length);
        updateOrientation();
    }

    public void setMagneticFieldValues(float[] pValues) {
        System.arraycopy(pValues, 0, this.mMagneticFieldValues, 0, pValues.length);
        updateOrientation();
    }

    private void updateOrientation() {
        SensorManager.getRotationMatrix(this.mRotationMatrix, null, this.mAccelerationValues, this.mMagneticFieldValues);
        switch (this.mDisplayRotation) {
            case 1:
                SensorManager.remapCoordinateSystem(this.mRotationMatrix, 2, BtManagerService.CLASSIC_SYNC_SLEEP_MSG, this.mRotationMatrix);
                break;
        }
        float[] values = this.mValues;
        SensorManager.getOrientation(this.mRotationMatrix, values);
        for (int i = values.length - 1; i >= 0; i--) {
            values[i] = values[i] * MathConstants.RAD_TO_DEG;
        }
    }

    public int getAccelerationAccuracy() {
        return getAccuracy();
    }

    public void setAccelerationAccuracy(int pAccelerationAccuracy) {
        super.setAccuracy(pAccelerationAccuracy);
    }

    public int getMagneticFieldAccuracy() {
        return this.mMagneticFieldAccuracy;
    }

    public void setMagneticFieldAccuracy(int pMagneticFieldAccuracy) {
        this.mMagneticFieldAccuracy = pMagneticFieldAccuracy;
    }

    public String toString() {
        return "Orientation: " + Arrays.toString(this.mValues);
    }
}
