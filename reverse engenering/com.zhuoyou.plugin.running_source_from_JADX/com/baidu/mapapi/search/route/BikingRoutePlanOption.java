package com.baidu.mapapi.search.route;

public class BikingRoutePlanOption {
    PlanNode f1667a = null;
    PlanNode f1668b = null;

    public BikingRoutePlanOption from(PlanNode planNode) {
        this.f1667a = planNode;
        return this;
    }

    public BikingRoutePlanOption to(PlanNode planNode) {
        this.f1668b = planNode;
        return this;
    }
}
