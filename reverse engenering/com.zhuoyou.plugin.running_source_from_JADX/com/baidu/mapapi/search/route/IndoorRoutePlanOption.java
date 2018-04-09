package com.baidu.mapapi.search.route;

public class IndoorRoutePlanOption {
    IndoorPlanNode f1712a = null;
    IndoorPlanNode f1713b = null;

    public IndoorRoutePlanOption from(IndoorPlanNode indoorPlanNode) {
        this.f1712a = indoorPlanNode;
        return this;
    }

    public IndoorRoutePlanOption to(IndoorPlanNode indoorPlanNode) {
        this.f1713b = indoorPlanNode;
        return this;
    }
}
