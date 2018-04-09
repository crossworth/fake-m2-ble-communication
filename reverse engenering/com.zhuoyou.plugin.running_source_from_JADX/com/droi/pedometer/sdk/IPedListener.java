package com.droi.pedometer.sdk;

public interface IPedListener {
    public static final int FAST_RUN = 4;
    public static final int FAST_WALK = 3;
    public static final int RUN = 2;
    public static final int STATIONARY = 0;
    public static final int WALK = 1;

    void onCadenceChanged(float f);

    void onStateChanged(int i);

    void onStepDetected(int i, int i2);
}
