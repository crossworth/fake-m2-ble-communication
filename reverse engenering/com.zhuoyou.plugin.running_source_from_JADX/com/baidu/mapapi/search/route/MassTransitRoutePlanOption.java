package com.baidu.mapapi.search.route;

public class MassTransitRoutePlanOption {
    PlanNode f1738a = null;
    PlanNode f1739b = null;
    String f1740c = "bd09ll";
    TacticsIncity f1741d = TacticsIncity.ETRANS_SUGGEST;
    TacticsIntercity f1742e = TacticsIntercity.ETRANS_LEAST_TIME;
    TransTypeIntercity f1743f = TransTypeIntercity.ETRANS_TRAIN_FIRST;
    int f1744g = 10;
    int f1745h = 1;

    public enum TacticsIncity {
        ETRANS_SUGGEST(0),
        ETRANS_LEAST_TRANSFER(1),
        ETRANS_LEAST_WALK(2),
        ETRANS_NO_SUBWAY(3),
        ETRANS_LEAST_TIME(4),
        ETRANS_SUBWAY_FIRST(5);
        
        private int f1733a;

        private TacticsIncity(int i) {
            this.f1733a = 0;
            this.f1733a = i;
        }

        public int getInt() {
            return this.f1733a;
        }
    }

    public enum TacticsIntercity {
        ETRANS_LEAST_TIME(0),
        ETRANS_START_EARLY(1),
        ETRANS_LEAST_PRICE(2);
        
        private int f1735a;

        private TacticsIntercity(int i) {
            this.f1735a = 0;
            this.f1735a = i;
        }

        public int getInt() {
            return this.f1735a;
        }
    }

    public enum TransTypeIntercity {
        ETRANS_TRAIN_FIRST(0),
        ETRANS_PLANE_FIRST(1),
        ETRANS_COACH_FIRST(2);
        
        private int f1737a;

        private TransTypeIntercity(int i) {
            this.f1737a = 0;
            this.f1737a = i;
        }

        public int getInt() {
            return this.f1737a;
        }
    }

    public MassTransitRoutePlanOption coordType(String str) {
        this.f1740c = str;
        return this;
    }

    public MassTransitRoutePlanOption from(PlanNode planNode) {
        this.f1738a = planNode;
        return this;
    }

    public MassTransitRoutePlanOption pageIndex(int i) {
        if (i >= 0 && i <= 2147483646) {
            this.f1745h = i + 1;
        }
        return this;
    }

    public MassTransitRoutePlanOption pageSize(int i) {
        if (i >= 1 && i <= 10) {
            this.f1744g = i;
        }
        return this;
    }

    public MassTransitRoutePlanOption tacticsIncity(TacticsIncity tacticsIncity) {
        this.f1741d = tacticsIncity;
        return this;
    }

    public MassTransitRoutePlanOption tacticsIntercity(TacticsIntercity tacticsIntercity) {
        this.f1742e = tacticsIntercity;
        return this;
    }

    public MassTransitRoutePlanOption to(PlanNode planNode) {
        this.f1739b = planNode;
        return this;
    }

    public MassTransitRoutePlanOption transtypeintercity(TransTypeIntercity transTypeIntercity) {
        this.f1743f = transTypeIntercity;
        return this;
    }
}
