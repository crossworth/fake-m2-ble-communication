package com.zhuoyou.plugin.gps.ilistener;

public interface IStepWatcher {
    void addWatcher(IStepListener iStepListener);

    void notifyHadRunStep(int i);

    void notifyStateChanged(int i);

    void notifyStepCount(int i);

    void removeWatcher(IStepListener iStepListener);
}
