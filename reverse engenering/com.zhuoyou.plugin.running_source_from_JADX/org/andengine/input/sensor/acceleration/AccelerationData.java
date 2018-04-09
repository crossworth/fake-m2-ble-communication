package org.andengine.input.sensor.acceleration;

import java.util.Arrays;
import org.andengine.input.sensor.BaseSensorData;

public class AccelerationData extends BaseSensorData {
    private static final IAxisSwap[] AXISSWAPS = new IAxisSwap[4];

    private interface IAxisSwap {
        void swapAxis(float[] fArr);
    }

    static class C20601 implements IAxisSwap {
        C20601() {
        }

        public void swapAxis(float[] pValues) {
            float x = -pValues[0];
            float y = pValues[1];
            pValues[0] = x;
            pValues[1] = y;
        }
    }

    static class C20612 implements IAxisSwap {
        C20612() {
        }

        public void swapAxis(float[] pValues) {
            float x = pValues[1];
            float y = pValues[0];
            pValues[0] = x;
            pValues[1] = y;
        }
    }

    static class C20623 implements IAxisSwap {
        C20623() {
        }

        public void swapAxis(float[] pValues) {
            float y = -pValues[1];
            pValues[0] = pValues[0];
            pValues[1] = y;
        }
    }

    static class C20634 implements IAxisSwap {
        C20634() {
        }

        public void swapAxis(float[] pValues) {
            float y = -pValues[0];
            pValues[0] = -pValues[1];
            pValues[1] = y;
        }
    }

    static {
        AXISSWAPS[0] = new C20601();
        AXISSWAPS[1] = new C20612();
        AXISSWAPS[2] = new C20623();
        AXISSWAPS[3] = new C20634();
    }

    public AccelerationData(int pDisplayOrientation) {
        super(3, pDisplayOrientation);
    }

    public float getX() {
        return this.mValues[0];
    }

    public float getY() {
        return this.mValues[1];
    }

    public float getZ() {
        return this.mValues[2];
    }

    public void setX(float pX) {
        this.mValues[0] = pX;
    }

    public void setY(float pY) {
        this.mValues[1] = pY;
    }

    public void setZ(float pZ) {
        this.mValues[2] = pZ;
    }

    public void setValues(float[] pValues) {
        super.setValues(pValues);
        AXISSWAPS[this.mDisplayRotation].swapAxis(this.mValues);
    }

    public String toString() {
        return "Acceleration: " + Arrays.toString(this.mValues);
    }
}
