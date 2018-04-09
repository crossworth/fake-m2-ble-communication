package com.zhuoyou.plugin.view;

import com.zhuoyou.plugin.view.ViewFlow.ViewSwitchListener;

public interface FlowIndicator extends ViewSwitchListener {
    void onScrolled(int i, int i2, int i3, int i4);

    void setViewFlow(ViewFlow viewFlow);
}
