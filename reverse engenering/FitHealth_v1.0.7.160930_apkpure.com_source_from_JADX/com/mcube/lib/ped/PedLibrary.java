package com.mcube.lib.ped;

import java.util.ArrayList;
import java.util.List;

public class PedLibrary {
    public static final int PED_STATE_RUNNING = 2;
    public static final int PED_STATE_STATIONARY = 0;
    public static final int PED_STATE_WALKING = 1;
    public static final int STEP_SENSITIVTY_HIGH = 2;
    public static final int STEP_SENSITIVTY_LOW = 0;
    public static final int STEP_SENSITIVTY_MEDIUM = 1;
    private List<PedListener> mListeners = new ArrayList();

    public native boolean Close();

    public native boolean IsOpened();

    public native boolean Open(int i);

    public native int PollVersion();

    public native boolean ProcessData(long j, float f, float f2, float f3);

    public void registerListener(PedListener listener) {
        synchronized (this.mListeners) {
            if (!this.mListeners.contains(listener)) {
                this.mListeners.add(listener);
            }
        }
    }

    public void unregisterListener(PedListener listener) {
        synchronized (this.mListeners) {
            if (this.mListeners.contains(listener)) {
                this.mListeners.remove(listener);
            }
        }
    }

    private void onStepDetected(int stepCount) {
        synchronized (this.mListeners) {
            for (PedListener listener : this.mListeners) {
                listener.onStepDetected(stepCount);
            }
        }
    }

    private void onStateChange(int newState) {
        synchronized (this.mListeners) {
            for (PedListener listener : this.mListeners) {
                listener.onStateChange(newState);
            }
        }
    }

    static {
        System.loadLibrary("mcube_lib_ped-jni");
    }
}
