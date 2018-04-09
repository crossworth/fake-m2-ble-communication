package com.zhuoyou.plugin.gps.ilistener;

import com.zhuoyou.plugin.gps.GuidePointModel;
import java.util.ArrayList;
import java.util.List;

public class MonitorWatcher implements IGPSWatcher {
    private static MonitorWatcher watcher;
    private List<IGPSPointListener> list = new ArrayList();

    private MonitorWatcher() {
    }

    public static MonitorWatcher getInstance() {
        if (watcher == null) {
            watcher = new MonitorWatcher();
        }
        return watcher;
    }

    public void addWatcher(IGPSPointListener watcher) {
        this.list.add(watcher);
    }

    public void removeWatcher(IGPSPointListener watcher) {
        this.list.remove(watcher);
    }

    public void notifyWatchers(GuidePointModel point) {
        for (IGPSPointListener watcher : this.list) {
            watcher.update(point);
        }
    }

    public void notifySumDistance(double distance) {
        for (IGPSPointListener watcher : this.list) {
            watcher.sumDisChanged(distance);
        }
    }
}
