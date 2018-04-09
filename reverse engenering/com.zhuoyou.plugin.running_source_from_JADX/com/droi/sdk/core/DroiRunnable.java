package com.droi.sdk.core;

public abstract class DroiRunnable {
    private DroiTask taskObject;

    public void cancel() {
        cancelTask();
    }

    void cancelTask() {
        this.taskObject.cancel();
    }

    DroiTask getTaskObject() {
        return this.taskObject;
    }

    public boolean isCancelled() {
        return this.taskObject.isCancelled();
    }

    public abstract void run();

    void setTaskObject(DroiTask droiTask) {
        this.taskObject = droiTask;
    }
}
