package com.baidu.mapapi.search.route;

public class WalkingRoutePlanOption {
    PlanNode f1791a = null;
    PlanNode f1792b = null;

    public WalkingRoutePlanOption from(PlanNode planNode) {
        this.f1791a = planNode;
        return this;
    }

    public WalkingRoutePlanOption to(PlanNode planNode) {
        this.f1792b = planNode;
        return this;
    }
}
