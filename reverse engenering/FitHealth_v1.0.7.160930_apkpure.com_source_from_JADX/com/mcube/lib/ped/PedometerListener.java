package com.mcube.lib.ped;

public interface PedometerListener {
    void onStateChanged(int i);

    void onStepCount(int i);
}
