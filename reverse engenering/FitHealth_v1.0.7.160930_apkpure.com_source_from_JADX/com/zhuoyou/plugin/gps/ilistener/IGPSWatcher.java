package com.zhuoyou.plugin.gps.ilistener;

import com.zhuoyou.plugin.gps.GuidePointModel;

public interface IGPSWatcher {
    void addWatcher(IGPSPointListener iGPSPointListener);

    void notifySumDistance(double d);

    void notifyWatchers(GuidePointModel guidePointModel);

    void removeWatcher(IGPSPointListener iGPSPointListener);
}
