package com.droi.pedometer.sdk;

import java.util.ArrayList;
import java.util.List;

public class PedProcessor {
    private final List<IPedListener> mListeners = new ArrayList();

    private native boolean nativeDetectStep(long j, float f, float f2, float f3);

    public native boolean close();

    public native boolean isOpen();

    public native boolean open();

    static {
        System.loadLibrary("droi_lib_ped");
    }

    public void registerListener(IPedListener listener) {
        synchronized (this.mListeners) {
            if (!this.mListeners.contains(listener)) {
                this.mListeners.add(listener);
            }
        }
    }

    public void unregisterListener(IPedListener listener) {
        synchronized (this.mListeners) {
            if (this.mListeners.contains(listener)) {
                this.mListeners.remove(listener);
            }
        }
    }

    public boolean detectStep(long timestamp, float accX, float accY, float accZ) {
        return nativeDetectStep(timestamp, accX, accY, accZ);
    }

    private void onStepDetected(int count, int state) {
        synchronized (this.mListeners) {
            for (IPedListener listener : this.mListeners) {
                listener.onStepDetected(count, state);
            }
        }
    }

    private void onStateChanged(int newState) {
        synchronized (this.mListeners) {
            for (IPedListener listener : this.mListeners) {
                listener.onStateChanged(newState);
            }
        }
    }

    private void onCadenceChanged(float newCadence) {
        synchronized (this.mListeners) {
            for (IPedListener listener : this.mListeners) {
                listener.onCadenceChanged(newCadence);
            }
        }
    }
}
