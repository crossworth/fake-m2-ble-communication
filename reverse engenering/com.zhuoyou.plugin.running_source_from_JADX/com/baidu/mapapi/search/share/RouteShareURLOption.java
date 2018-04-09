package com.baidu.mapapi.search.share;

import com.baidu.mapapi.search.route.PlanNode;

public class RouteShareURLOption {
    PlanNode f1802a = null;
    PlanNode f1803b = null;
    RouteShareMode f1804c;
    int f1805d = 0;
    int f1806e = -1;

    public enum RouteShareMode {
        CAR_ROUTE_SHARE_MODE(0),
        FOOT_ROUTE_SHARE_MODE(1),
        CYCLE_ROUTE_SHARE_MODE(2),
        BUS_ROUTE_SHARE_MODE(3);
        
        private int f1801a;

        private RouteShareMode(int i) {
            this.f1801a = -1;
            this.f1801a = i;
        }

        public int getRouteShareMode() {
            return this.f1801a;
        }
    }

    public RouteShareURLOption cityCode(int i) {
        this.f1806e = i;
        return this;
    }

    public RouteShareURLOption from(PlanNode planNode) {
        this.f1802a = planNode;
        return this;
    }

    public RouteShareMode getmMode() {
        return this.f1804c;
    }

    public RouteShareURLOption pn(int i) {
        this.f1805d = i;
        return this;
    }

    public RouteShareURLOption routMode(RouteShareMode routeShareMode) {
        this.f1804c = routeShareMode;
        return this;
    }

    public RouteShareURLOption to(PlanNode planNode) {
        this.f1803b = planNode;
        return this;
    }
}
