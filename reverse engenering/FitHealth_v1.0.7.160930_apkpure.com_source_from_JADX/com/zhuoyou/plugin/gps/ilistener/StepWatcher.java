package com.zhuoyou.plugin.gps.ilistener;

import java.util.ArrayList;
import java.util.List;

public class StepWatcher implements IStepWatcher {
    private static StepWatcher watcher;
    private List<IStepListener> list = new ArrayList();

    private StepWatcher() {
    }

    public static StepWatcher getInstance() {
        if (watcher == null) {
            watcher = new StepWatcher();
        }
        return watcher;
    }

    public void addWatcher(IStepListener watcher) {
        this.list.add(watcher);
    }

    public void removeWatcher(IStepListener watcher) {
        this.list.remove(watcher);
    }

    public void notifyStepCount(int stepCount) {
        for (IStepListener watcher : this.list) {
            watcher.onStepCount(stepCount);
        }
    }

    public void notifyStateChanged(int newState) {
        for (IStepListener watcher : this.list) {
            watcher.onStateChanged(newState);
        }
    }

    public void notifyHadRunStep(int hadRunStep) {
        for (IStepListener watcher : this.list) {
            watcher.onHadRunStep(hadRunStep);
        }
    }
}
