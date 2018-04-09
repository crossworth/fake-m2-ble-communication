package com.zhuoyou.plugin.gps.ilistener;

import java.util.ArrayList;
import java.util.List;

public class SignalWatcher implements GPSSignalWatcher {
    private static SignalWatcher watcher;
    private List<GPSSignalListener> list = new ArrayList();

    private SignalWatcher() {
    }

    public static SignalWatcher getInstance() {
        if (watcher == null) {
            watcher = new SignalWatcher();
        }
        return watcher;
    }

    public void addWatcher(GPSSignalListener watcher) {
        this.list.add(watcher);
    }

    public void removeWatcher(GPSSignalListener watcher) {
        this.list.remove(watcher);
    }

    public void notifyWatchers(int gpsState) {
        for (GPSSignalListener watcher : this.list) {
            watcher.update(gpsState);
        }
    }
}
