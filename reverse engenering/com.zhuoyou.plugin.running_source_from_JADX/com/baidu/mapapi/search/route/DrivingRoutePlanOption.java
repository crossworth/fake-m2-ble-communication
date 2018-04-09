package com.baidu.mapapi.search.route;

import java.util.List;

public class DrivingRoutePlanOption {
    PlanNode f1689a = null;
    PlanNode f1690b = null;
    String f1691c;
    DrivingPolicy f1692d = DrivingPolicy.ECAR_TIME_FIRST;
    List<PlanNode> f1693e = null;
    DrivingTrafficPolicy f1694f = DrivingTrafficPolicy.ROUTE_PATH;

    public enum DrivingPolicy {
        ECAR_AVOID_JAM(-1),
        ECAR_TIME_FIRST(0),
        ECAR_DIS_FIRST(1),
        ECAR_FEE_FIRST(2);
        
        private int f1686a;

        private DrivingPolicy(int i) {
            this.f1686a = i;
        }

        public int getInt() {
            return this.f1686a;
        }
    }

    public enum DrivingTrafficPolicy {
        ROUTE_PATH(0),
        ROUTE_PATH_AND_TRAFFIC(1);
        
        private int f1688a;

        private DrivingTrafficPolicy(int i) {
            this.f1688a = i;
        }

        public int getInt() {
            return this.f1688a;
        }
    }

    public DrivingRoutePlanOption currentCity(String str) {
        this.f1691c = str;
        return this;
    }

    public DrivingRoutePlanOption from(PlanNode planNode) {
        this.f1689a = planNode;
        return this;
    }

    public DrivingRoutePlanOption passBy(List<PlanNode> list) {
        this.f1693e = list;
        return this;
    }

    public DrivingRoutePlanOption policy(DrivingPolicy drivingPolicy) {
        this.f1692d = drivingPolicy;
        return this;
    }

    public DrivingRoutePlanOption to(PlanNode planNode) {
        this.f1690b = planNode;
        return this;
    }

    public DrivingRoutePlanOption trafficPolicy(DrivingTrafficPolicy drivingTrafficPolicy) {
        this.f1694f = drivingTrafficPolicy;
        return this;
    }
}
