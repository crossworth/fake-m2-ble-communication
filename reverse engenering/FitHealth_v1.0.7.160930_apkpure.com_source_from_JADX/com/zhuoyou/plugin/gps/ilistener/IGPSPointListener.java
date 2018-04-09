package com.zhuoyou.plugin.gps.ilistener;

import com.zhuoyou.plugin.gps.GuidePointModel;

public interface IGPSPointListener {
    void sumDisChanged(double d);

    void update(GuidePointModel guidePointModel);
}
